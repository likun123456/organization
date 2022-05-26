package com.atcx.controller;


import com.atcx.pojo.*;
import com.atcx.service.ActivityService;
import com.atcx.service.ActivityTeacherService;
import com.atcx.service.UserService;
import com.atcx.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    private int UserId;

    private Result result;

    @Resource
    private ActivityTeacherService activityTeacherService;
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;


    //获取活动中每个类型活动数量
    @RequestMapping("getTypeReport")
    public Result getTypeReport() {
        List<ReportType> typeReport = activityService.getTypeReport();
        if (typeReport != null) {
            result = new Result(true, "查询活动信息成功", typeReport);
        }

        return result;
    }

    //获取每个专业发起的活动数量
    @RequestMapping("getCountReport")
    public Result getCountReport() {
        Map<String, Object> countReport = activityService.getCountReport();
        if (countReport != null) {
            result = new Result(true, "查询统计信息成功", countReport);
        } else {
            result = new Result(false, "查询统计信息失败");
        }
        return result;
    }

    //获取用户ID
    @RequestMapping("getUserId")
    public void getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                String value = cookie.getValue();
                UserId = Integer.parseInt(value);
            }
        }
    }

    @RequestMapping("getTeachers")
    public Result getTeachers(HttpServletRequest request) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        //在cookie中获取用户ID，从Major表中查询MajorID，根据MajorID查询系相关职工信息
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("userId")){
//                String value = cookie.getValue();
//                UserId = Integer.parseInt(value);
//            }
//        }
        List<MajorTeacher> teachers = activityService.getTeachers(UserId);
        if (teachers != null) {
            result = new Result(true, "查询职工信息成功", teachers);
        } else {
            result = new Result(false, "查询职工信息失败");
        }

        return result;
    }

    //添加活动信息:state为A表未知，B表参与，C表不参与
    @RequestMapping("addActivity")
    @Transactional(rollbackFor = Exception.class)
    public Result addActivity(@RequestBody Activity activity, Integer[] ids, HttpServletRequest request) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("userId")){
//                String value = cookie.getValue();
//                UserId = Integer.parseInt(value);
//            }
//        }
        User user = userService.getById(UserId);
        System.out.println(activity);
        activity.setPromoter(user.getAccountname());
        activity.setShouldnumber(ids.length);
        activity.setRealnumber(0);
        int i = activityService.addActivity(activity);
        Activity activityByName = activityService.selectActivityByName(activity.getActivityname());
        System.out.println("活动" + activityByName);
        System.out.println(ids[0]);
        int on = activityService.addActivityOn(activityByName, ids);
        if (i > 0) {
            result = new Result(true, "添加活动信息成功,邀请失败人数" + on);
            // 给管理员发邮件
            try {
                List<String> emailList = userService.listObjs(new LambdaQueryWrapper<User>()
                        .select(User::getEmail)
                        .eq(User::getRank, "C"), Object::toString);
                emailList = emailList.stream().filter(e -> e != null && !e.equals("")).collect(Collectors.toList());
                List<String> finalEmailList = emailList;
                new Thread(() -> finalEmailList.forEach(email -> {
                    String content = "您有新的活动待审核\n" +
                            "名称：" + activity.getActivityname() + "\n" +
                            "时间:" + activity.getStarttime().toString().replaceAll("T", " ") + "至" + activity.getEndtime().toString().replaceAll("T", " ") + "\n" +
                            "地点:" + activity.getPlace() + "\n" +
                            "内容:" + activity.getContent() + "\n" +
                            "发起人:" + activity.getPromoter() + "\n" +
                            "请进入系统查看";
                    EmailUtil.sendSimpleMail(new MailSendDTO(email, "活动审核", content));
                })).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            result = new Result(false, "添加活动信息失败");
        }

        return result;
    }


    //修改
    @RequestMapping("updateActivity")
    public Result updateActivity(@RequestBody Activity activity) {
        int i = activityService.updateActivity(activity);
        if (i > 0) {
            result = new Result(true, "修改活动信息成功");
        } else {
            result = new Result(false, "修改活动信息失败");
        }
        return result;
    }

    //删除
    @RequestMapping("deleteActivity")
    public Result deleteActivity(int id) {
        int i = activityService.deleteActivity(id);
        if (i > 0) {
            result = new Result(true, "删除活动信息成功");
        } else {
            result = new Result(false, "删除活动信息失败");
        }
        return result;
    }

    //分页
    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult page = activityService.findByPage(queryPageBean);
        if (page != null) {
            result = new Result(true, "查询活动信息成功", page);
        } else {
            result = new Result(true, "查询活动信息失败");
        }
        return result;
    }

    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody Activity activity) {
        Integer activityId = activity.getId();
        boolean updateResult = activityService.updateById(activity);
        try {
            if (ActivityStatusEnum.PASS.getCode().equals(activity.getStatus())) {
                this.sendTeachEmail(activityId);
                this.sendMasterEmail(activityId, ActivityStatusEnum.PASS);
            } else {
                this.sendMasterEmail(activityId, ActivityStatusEnum.REJECT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(updateResult, updateResult ? "操作成功" : "操作失败");
    }

    /**
     * 通过活动id给参加的老师发送邮件
     *
     * @param activityId
     */
    public void sendTeachEmail(Integer activityId) {
        List<Integer> userIdList = activityTeacherService.listObjs(new LambdaQueryWrapper<ActivityTeacher>()
                .select(ActivityTeacher::getUserid)
                .eq(ActivityTeacher::getActivityid, activityId), o -> Integer.parseInt(o.toString()));
        if (userIdList != null && userIdList.size() > 0) {
            List<String> finalEmailList = userService.listObjs(new LambdaQueryWrapper<User>()
                    .select(User::getEmail).in(User::getId, userIdList), String::valueOf);
            finalEmailList = finalEmailList.stream().filter(e -> !"".equals(e)).collect(Collectors.toList());
            Activity activity = activityService.getOne(new LambdaQueryWrapper<Activity>().eq(Activity::getId, activityId));
            List<String> finalEmailList1 = finalEmailList;
            new Thread(() -> finalEmailList1.forEach(email -> {
                String content = "您有活动待参加\n" +
                        "名称：" + activity.getActivityname() + "\n" +
                        "时间:" + activity.getStarttime().toString().replaceAll("T", " ") + "至" + activity.getEndtime().toString().replaceAll("T", " ") + "\n" + "地点:" + activity.getPlace() + "\n" +
                        "地点:" + activity.getPlace() + "\n" +
                        "内容:" + activity.getContent() + "\n" +
                        "发起人:" + activity.getPromoter() + "\n" +
                        "请进入系统查看";
                EmailUtil.sendSimpleMail(new MailSendDTO(email, "活动参加:" + activity.getActivityname(), content));
            })).start();
        }
    }

    /**
     * 通过活动id给发起人发送邮件
     *
     * @param activityId
     * @param activityStatusEnum
     */
    public void sendMasterEmail(Integer activityId, ActivityStatusEnum activityStatusEnum) {
        Activity activity = activityService.getOne(new LambdaQueryWrapper<Activity>().eq(Activity::getId, activityId));
        List<User> list = userService.list(new LambdaQueryWrapper<User>().eq(User::getAccountname, activity.getPromoter()));
        if (list != null && list.size() > 0) {
            User user = list.get(0);
            if (null == user.getEmail() || "".equalsIgnoreCase(user.getEmail())) {
                return;
            }
            new Thread(() -> {
                String statusContent;
                if (ActivityStatusEnum.PASS.getCode().equals(activityStatusEnum.getCode())) {
                    statusContent = "已通过";
                } else {
                    statusContent = "被驳回,原因：" + activity.getRejectDesc();
                }

                String content = user.getAccountname() + "您好,您发起的活动" + statusContent + "\n" +
                        "名称：" + activity.getActivityname() + "\n" +
                        "时间:" + activity.getStarttime().toString().replaceAll("T", " ") + "至" + activity.getEndtime().toString().replaceAll("T", " ") + "\n" + "地点:" + activity.getPlace() + "\n" +
                        "地点:" + activity.getPlace() + "\n" +
                        "内容:" + activity.getContent() + "\n" +
                        "发起人:" + activity.getPromoter() + "\n" +
                        "请进入系统查看";
                EmailUtil.sendSimpleMail(new MailSendDTO(user.getEmail(), "活动审核结果通知", content));
            }).start();

        }
    }
}


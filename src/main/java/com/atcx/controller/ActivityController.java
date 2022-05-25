package com.atcx.controller;


import com.atcx.mapper.ActivityMapper;
import com.atcx.pojo.Activity;
import com.atcx.pojo.ActivityTeacher;
import com.atcx.pojo.MajorTeacher;
import com.atcx.pojo.ReportType;
import com.atcx.pojo.User;
import com.atcx.service.ActivityService;
import com.atcx.service.ActivityTeacherService;
import com.atcx.service.MajorService;
import com.atcx.service.UserService;
import com.atcx.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
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
    public Result getTypeReport(){
        List<ReportType> typeReport = activityService.getTypeReport();
        if (typeReport!=null){
            result = new Result(true,"查询活动信息成功",typeReport);
        }

        return result;
    }
    //获取每个专业发起的活动数量
    @RequestMapping("getCountReport")
    public Result getCountReport(){
        Map<String, Object> countReport = activityService.getCountReport();
        if (countReport != null){
            result = new Result(true,"查询统计信息成功",countReport);
        }else {
            result = new Result(false,"查询统计信息失败");
        }
        return result;
    }
    //获取用户ID
    @RequestMapping("getUserId")
    public void getUserId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")){
                String value = cookie.getValue();
                UserId = Integer.parseInt(value);
            }
        }
    }
    @RequestMapping("getTeachers")
    public Result getTeachers(HttpServletRequest request){
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
        if (teachers != null){
            result = new Result(true,"查询职工信息成功",teachers);
        }else {
            result = new Result(false,"查询职工信息失败");
        }

        return  result;
    }
    //添加活动信息:state为A表未知，B表参与，C表不参与
    @RequestMapping("addActivity")
    @Transactional(rollbackFor = Exception.class)
    public Result addActivity(@RequestBody Activity activity,Integer[] ids,HttpServletRequest request){
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
        System.out.println("活动"+activityByName);
        System.out.println(ids[0]);
        int on = activityService.addActivityOn(activityByName, ids);
        if (i>0){
            result = new Result(true,"添加活动信息成功,邀请失败人数"+on);
            // 给管理员发邮件
            try {
                List<String> emailList = userService.listObjs(new LambdaQueryWrapper<User>()
                        .select(User::getEmail)
                        .eq(User::getRank, "C"), Object::toString);
                emailList = emailList.stream().filter(e -> e != null && !e.equals("")).collect(Collectors.toList());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
                List<String> finalEmailList = emailList;
                new Thread(() -> finalEmailList.forEach(email -> {
                    String content = null;
                    try {
                        content = "您有新的活动待审核\n" +
                                "名称：" + activity.getActivityname() + "\n" +
                                "时间:" + simpleDateFormat.format(DateFormat.getDateInstance().parse(activity.getStarttime().toString())) + "至" + simpleDateFormat.format(DateFormat.getInstance().parse(activity.getEndtime().toString())) + "\n" +
                                "地点:" + activity.getPlace() + "\n" +
                                "发起人:" + activity.getPromoter() + "\n" +
                                "请进入系统查看";
                    } catch (ParseException e) {
                        e.printStackTrace();
                        content = "您有新的活动待审核\n" +
                                "名称：" + activity.getActivityname() + "\n" +
                                "时间:" + activity.getStarttime() + "至" + activity.getEndtime() + "\n" +
                                "地点:" + activity.getPlace() + "\n" +
                                "发起人:" + activity.getPromoter() + "\n" +
                                "请进入系统查看";
                    }
                    EmailUtil.sendSimpleMail(new MailSendDTO(email, "活动审核", content));
                })).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            result = new Result(false,"添加活动信息失败");
        }

        return result;
    }


    //修改
    @RequestMapping("updateActivity")
    public Result updateActivity(@RequestBody Activity activity){
        int i = activityService.updateActivity(activity);
        if (i>0){
            result = new Result(true,"修改活动信息成功");
        }else {
            result = new Result(false,"修改活动信息失败");
        }
        return result;
    }
    //删除
    @RequestMapping("deleteActivity")
    public Result deleteActivity(int id){
        int i = activityService.deleteActivity(id);
        if (i>0){
            result = new Result(true,"删除活动信息成功");
        }else {
            result = new Result(false,"删除活动信息失败");
        }
        return result;
    }
    //分页
    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = activityService.findByPage(queryPageBean);
        if (page!=null){
            result = new Result(true,"查询活动信息成功",page);
        }else {
            result = new Result(true,"查询活动信息失败");
        }
        return  result;
    }

    @PostMapping("/changeStatus")
    public Result changeStatus(@RequestBody Activity activity) {
        boolean updateResult = activityService.updateById(activity);
        return new Result(updateResult, updateResult ? "操作成功" : "操作失败");
    }

}


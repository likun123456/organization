package com.atcx.controller;


import com.atcx.pojo.ActivityTeacher;
import com.atcx.service.ActivityService;
import com.atcx.service.ActivityTeacherService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.atcx.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/activity-teacher")
public class ActivityTeacherController {
    //参加活动

    private Result result;
    @Resource
    private ActivityTeacherService activityTeacherService;
    @Resource
    private ActivityService activityService;
    //参加活动
    @RequestMapping("joinActivity")
    public Result joinActivity(int id, String join, HttpServletRequest request){
        int UserId = 0;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")){
                String value = cookie.getValue();
                UserId = Integer.parseInt(value);
            }
        }
        //修改数据
        ActivityTeacher activityTeacher = new ActivityTeacher(id, UserId, join);
        System.out.println(activityTeacher);
        if ("B".equals(join)){
            int i = activityService.addNumber(id);
        }
        if ("C".equals(join)){

        }
        int i = activityTeacherService.joinActivity(activityTeacher);
        if (i>0){
            result = new Result(true,"处理成功");
        }else {
            result = new Result(false,"处理失败");
        }
        return result;
    }
    //分页查询
    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean ,HttpServletRequest request){
        //获取用户ID
        int UserId = 0;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")){
                String value = cookie.getValue();
                UserId = Integer.parseInt(value);
            }
        }
        System.out.println("当前用户ID"+UserId);
        PageResult page = activityTeacherService.findByPage(queryPageBean, UserId);
        if (page!=null){
            result = new Result(true,"活动信息查询成功",page);
        }else {
            result = new Result(false,"当前暂时没有活动信息，请稍后查看");
        }
        return result;
    }
    //
    @RequestMapping("activityMassage")
    public Result activityMassage(HttpServletRequest request){
        Integer id = 0;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")){
                String value = cookie.getValue();
                    id = Integer.parseInt(value);
            }
        }

        Boolean message = activityTeacherService.newMessage(id);
        if (message){
            result = new Result(true,"有新的活动邀请，请注意查看");
        }else {
            result = new Result(false,"暂时没有新的活动邀请");
        }
        return result;
    }
}


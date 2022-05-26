package com.atcx.controller;


import com.atcx.pojo.MajorTeacher;
import com.atcx.service.MajorTeacherService;
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
@RequestMapping("/major-teacher")
public class MajorTeacherController {


    private Result result;
    @Resource
    private MajorTeacherService majorTeacherService;
    @RequestMapping("addTeacher")
    public Result addTeacher(@RequestBody MajorTeacher majorTeacher){
        int i = majorTeacherService.addMajorTeacher(majorTeacher);
        if (i>0){
            result = new Result(true,"添加职工成功");
        }else {
            result = new Result(false,"添加职工失败");
        }
        return result;
    }

    @RequestMapping("deleteTeacher")
    public Result deleteTeacher(@RequestBody MajorTeacher majorTeacher){
        int i = majorTeacherService.deleteTeacher(majorTeacher);
        if (i>0){
            result = new Result(true,"删除职工成功");
        }else {
            result = new Result(false,"删除职工失败");
        }
        return result;
    }

    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
        Integer userId = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                String value = cookie.getValue();
                userId = Integer.parseInt(value);
            }
        }
        PageResult page = majorTeacherService.findByPage(queryPageBean, userId);
        if (page != null){
            result  = new Result(true,"查询职工信息成功",page);
        }else {
            result = new Result(false,"查询职工信息失败");
        }
        return result;
    }
    //获取所有职工用户ID
    @RequestMapping("getAllUserId")
    public Result getAllUserId(@RequestBody Integer[] checkitemIds){
        //查询所有用户ID
        int[] allUserId = majorTeacherService.getAllUserId(checkitemIds);
        if (allUserId != null){
            result = new Result(true,"查询所有职工ID成功",allUserId);
        }
        return result;
    }
}


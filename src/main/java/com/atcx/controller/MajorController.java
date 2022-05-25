package com.atcx.controller;


import com.atcx.pojo.Major;
import com.atcx.service.MajorService;
import com.atcx.service.MajorTeacherService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.atcx.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/major")
public class MajorController {


    private Result result;

    @Resource
    private MajorService majorService;
    @Resource
    private MajorTeacherService majorTeacherService;


    @RequestMapping("getAllMajor")
    public Result getAllMajor(){
        List<Major> allMajor = majorService.getAllMajor();
        result = new Result(true,"查询信息成功",allMajor);
        return result;
    }
    @RequestMapping("addMajor")
    public Result addMajor(@RequestBody Major major){
        major.setCraetetime(LocalDateTime.now());
        major.setNumbers(0);
        int i = majorService.addMajor(major);
        if (i>0){
            result = new Result(true,"添加系信息成功");
        }else {
            result = new Result(false,"添加系信息失败");
        }
        return result;

    }
    @RequestMapping("updateMajor")
    public Result updateMajor(@RequestBody Major major){
        int i = majorService.updateMajor(major);
        if (i>0){
            result = new Result(true,"修改系信息成功");
        }else {
            result = new Result(false,"修改系信息失败");
        }
        return result;
    }

    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = majorService.findByPage(queryPageBean);
        if (page != null){
            result = new Result(true,"查询系信息成功",page);
        }else {
            result = new Result(false,"查询系信息失败");

        }
        return result;
    }

    @RequestMapping("deleteMajor")
    public Result deleteMajor(int id){
        int i = majorService.deleteMajor(id);
        if (i>0){
            result = new Result(true,"删除系信息成功");
        }else {
            result = new Result(false,"删除系信息失败");
        }
        return result;
    }
}


package com.atcx.service;

import com.atcx.pojo.MajorTeacher;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *专业和老师的关联表
 * @author pangShu
 * @since 2022-02-24
 */
public interface MajorTeacherService extends IService<MajorTeacher> {
    //插入数据
    public int addMajorTeacher(MajorTeacher majorTeacher);
    //根据系ID查询所有职工
    public List<MajorTeacher> getListByMajorId(int majorId);
    //根据用户ID和专业ID删除老师
    public int deleteTeacher(MajorTeacher majorTeacher);
    //分页查询
    public PageResult findByPage(QueryPageBean queryPageBean, Integer userId);
    //查询所有职工ID
    public int[] getAllUserId(Integer[] ids);

}

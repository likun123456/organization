package com.atcx.service;

import com.atcx.pojo.Activity;
import com.atcx.pojo.MajorTeacher;
import com.atcx.pojo.ReportType;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
public interface ActivityService extends IService<Activity> {
    //添加
    public int addActivity(Activity activity);
    //添加活动和关联信息
    public int addActivityOn(Activity activity,Integer[] ids);
    //修改
    public int updateActivity(Activity activity);
    //删除
    public int deleteActivity(int id);
    //分页查找
    public PageResult findByPage(QueryPageBean queryPageBean);
    //查找系内所有职工信息
    public List<MajorTeacher> getTeachers(int userId);
    //通过活动名称查找活动信息
    public Activity selectActivityByName(String activityName);
    //参与人数加1
    public int addNumber(int activityId);
    //获取每个专业的活动个数
    public Map<String,Object> getCountReport();
    //获取活动中每个类型活动数量
    public List<ReportType> getTypeReport();

}

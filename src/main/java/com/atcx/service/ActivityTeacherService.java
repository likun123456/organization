package com.atcx.service;

import com.atcx.pojo.ActivityTeacher;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *活动和职工关联表
 * @author pangShu
 * @since 2022-02-24
 */
public interface ActivityTeacherService extends IService<ActivityTeacher> {
    //插入活动ID和参与用户ID和状态
    public int addActivityTeacher(ActivityTeacher activityTeacher);
    //根据用户ID查询活动
    public List<ActivityTeacher> getListByUserId(int id);
    //根据用户ID和活动ID修改参与状态
    public int joinActivity(ActivityTeacher activityTeacher);
    //分页查询，条件：1.根据用户ID；2.参与状态
    public PageResult findByPage(QueryPageBean queryPageBean,int  userId);
    //根据用户ID判断当前用户有没有新活动
    public Boolean newMessage(int userId);

}

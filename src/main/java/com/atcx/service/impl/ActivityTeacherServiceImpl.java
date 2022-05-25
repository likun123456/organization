package com.atcx.service.impl;

import com.atcx.mapper.ActivityMapper;
import com.atcx.mapper.ActivityTeacherMapper;
import com.atcx.pojo.Activity;
import com.atcx.pojo.ActivityTeacher;
import com.atcx.service.ActivityTeacherService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@Service
public class ActivityTeacherServiceImpl extends ServiceImpl<ActivityTeacherMapper, ActivityTeacher> implements ActivityTeacherService {


    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private ActivityTeacherMapper activityTeacherMapper;
    @Override
    public int addActivityTeacher(ActivityTeacher activityTeacher) {
        System.out.println(activityTeacher);
        int insert = activityTeacherMapper.insert(activityTeacher);
        return insert;
    }

    @Override
    public List<ActivityTeacher> getListByUserId(int id) {
        QueryWrapper<ActivityTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("userid",id);
        List<ActivityTeacher> activityTeacherList = activityTeacherMapper.selectList(wrapper);
        return activityTeacherList;
    }

    /**
     * @Author:pangshu
     * @Description: 根据用户ID和活动ID修改参与状态
     * @Date:10:11 2022/2/27
     * @Param:
     * @return: int
     **/
    @Override
    public int joinActivity(ActivityTeacher activityTeacher) {
        QueryWrapper<ActivityTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("activityid",activityTeacher.getActivityid()).eq("userid",activityTeacher.getUserid());
        int update = activityTeacherMapper.update(activityTeacher, wrapper);
        return update;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean, int userId) {
        PageResult pageResult;
        //先过滤活动
        List<ActivityTeacher> list = getListByUserId(userId);
        System.out.println(list);
        //再过滤应该未处理和已经参加的活动
        int[] arr = new int[list.size()];
        int i = 0;
        for (ActivityTeacher activityTeacher : list) {
            if (activityTeacher.getState().equals("A")||activityTeacher.getState().equals("C")){
                arr[i] = activityTeacher.getActivityid();
                i++;
            }
        }
        System.out.println("当前用户参加的活动ID" +  arr.toString());
        //分页查询活动返回数据
        Page<Activity> activityPage = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        if (arr.length>0){
            for (int i1 : arr) {
                wrapper.or().eq("id",i1);
            }

            Page<Activity> page= activityMapper.selectPage(activityPage, wrapper);
            pageResult = new PageResult(page.getTotal(), page.getRecords());
        }else {
            pageResult = null;
        }

        return pageResult;
    }

    @Override
    public Boolean newMessage(int userId) {
        QueryWrapper<ActivityTeacher> wrapper  = new QueryWrapper<>();
        wrapper.eq("userid",userId);
        List<ActivityTeacher> activityTeachers = activityTeacherMapper.selectList(wrapper);
        Boolean flag = false;//true:有新邀请
        for (ActivityTeacher activityTeacher : activityTeachers) {
            if (activityTeacher.getState().equals("A")){
                flag = true;
            }
        }
        return flag;
    }




}

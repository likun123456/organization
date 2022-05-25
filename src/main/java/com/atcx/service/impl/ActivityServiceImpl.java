package com.atcx.service.impl;

import com.atcx.mapper.ActivityMapper;
import com.atcx.mapper.ActivityTeacherMapper;
import com.atcx.mapper.MajorMapper;
import com.atcx.mapper.MajorTeacherMapper;
import com.atcx.mapper.UserMapper;
import com.atcx.pojo.Activity;
import com.atcx.pojo.ActivityTeacher;
import com.atcx.pojo.Major;
import com.atcx.pojo.MajorTeacher;
import com.atcx.pojo.ReportType;
import com.atcx.pojo.User;
import com.atcx.service.ActivityService;
import com.atcx.service.ActivityTeacherService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Resource
    private   ActivityMapper activityMapper;
    @Resource
    private MajorMapper majorMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MajorTeacherMapper majorTeacherMapper;
    @Resource
    private ActivityTeacherMapper activityTeacherMapper;
    @Resource
    private ActivityTeacherService activityTeacherService;

    @Override
    public int addActivity(Activity activity) {
        int insert = activityMapper.insert(activity);
        return insert;
    }

    @Override
    public int addActivityOn(Activity activity, Integer[] ids) {
        //过滤IDS,判断时间冲突
        int fail = 0;//邀请失败
        int i = 0;
        for (Integer id : ids) {
            //查询当前用户参与的活动，判断有没有时间冲突
            Boolean flag = true;//true,表示没有冲突
            List<ActivityTeacher> list = activityTeacherService.getListByUserId(id);

            if (list!=null){
                for (ActivityTeacher activityTeacher : list) {
                    Activity selectById = activityMapper.selectById(activityTeacher.getActivityid());
                    //当前活动的开始时间大于其他活动的结束时间，就不冲突,
                    int compareTo = activity.getStarttime().compareTo(selectById.getEndtime());//1：大，-1表示小
                    if (compareTo<0){
                        //
                        flag = false;
                        fail++;
                        break;
                    }
                }
            }

            if (flag){
                ActivityTeacher a = new ActivityTeacher(activity.getId(), id, "A");
                i += activityTeacherMapper.insert(a);
            }else {
                ActivityTeacher a = new ActivityTeacher(activity.getId(), id, "B");
                i += activityTeacherMapper.insert(a);
            }
        }
        return fail;
    }

    @Override
    public int updateActivity(Activity activity) {
        int i = activityMapper.updateById(activity);
        return i;
    }

    @Override
    public int deleteActivity(int id) {
        //删除活动信息之前先删除关联表信息
        int i = 0;
        QueryWrapper<ActivityTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("activityid",id);
        int delete = activityTeacherMapper.delete(wrapper);
        i= activityMapper.deleteById(id);
        return i;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        Page<Activity> activityPage = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null && queryPageBean.getQueryString().trim().length()!=0){
            wrapper.eq("activityname",queryPageBean.getQueryString());//设置查询条件为活动名称
        }
        wrapper.orderByDesc("id");
        Page<Activity> page = activityMapper.selectPage(activityPage, wrapper);
        PageResult pageResult = new PageResult(page.getTotal(), page.getRecords());
        return pageResult;
    }

    @Override
    public List<MajorTeacher> getTeachers(int userId) {
        //查majorId
        System.out.println("当前用户ID"+userId);
        QueryWrapper<Major> majorQueryWrapper = new QueryWrapper<>();
        majorQueryWrapper.eq("userid",userId);
        Major major = majorMapper.selectOne(majorQueryWrapper);
        System.out.println("专业信息"+major);
        //根据majorId 查相关职工
        QueryWrapper<MajorTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("majorid",major.getId());
        List<MajorTeacher> majorTeachers = majorTeacherMapper.selectList(wrapper);
        //加工数据，为职工名称赋值
        for (MajorTeacher teacher : majorTeachers) {
            //查询用户名称
            User user = userMapper.selectById(teacher.getUserid());
            teacher.setTeacherName(user.getAccountname());
        }

        //赋值完成
        return majorTeachers;
    }

    @Override
    public Activity selectActivityByName(String activityName) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("activityname",activityName);
        Activity activity = activityMapper.selectOne(wrapper);
        return activity;
    }

    @Override
    public int addNumber(int activityId) {
        System.out.println("addNumber被调用");
        Activity activity = activityMapper.selectById(activityId);
        int i = 0 ;
        activity.setRealnumber(i+1);
        int update = activityMapper.updateById(activity);

        return update;
    }

    @Override
    public Map<String, Object> getCountReport() {
        Map<String,Object> map = new HashMap<>();
        List<String> majors = new ArrayList<>();
        List<Integer> activityCount = new ArrayList<>();
        List<Major> majorList = majorMapper.selectList(new QueryWrapper<Major>());
        for (Major major : majorList) {
            majors.add(major.getMajorname());
            //查询活动个数
            QueryWrapper<Activity> wrapper = new QueryWrapper<>();
            wrapper.eq("promoter",major.getDean());
            Integer integer = activityMapper.selectCount(wrapper);
            activityCount.add(integer);
        }

        map.put("majors",majors);
        map.put("activityCount",activityCount);
        return map;
    }

    @Override
    public List<ReportType> getTypeReport() {
        List<ReportType> list  = new ArrayList<>();
        List<String> stringlist = new ArrayList<>();
        stringlist.add("教学");
        stringlist.add("科研");
        stringlist.add("知识竞赛");
        for (String s : stringlist) {
            ReportType reportType = new ReportType();
            reportType.setName(s);
            QueryWrapper<Activity> wrapper = new QueryWrapper<>();
            wrapper.eq("actype",s);
            Integer integer = activityMapper.selectCount(wrapper);
            reportType.setValue(integer);
            list.add(reportType);
        }
        return list;
    }


}

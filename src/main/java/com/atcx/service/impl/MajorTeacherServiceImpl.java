package com.atcx.service.impl;

import com.atcx.mapper.MajorMapper;
import com.atcx.mapper.MajorTeacherMapper;
import com.atcx.mapper.UserMapper;
import com.atcx.pojo.ActivityTeacher;
import com.atcx.pojo.Major;
import com.atcx.pojo.MajorTeacher;
import com.atcx.pojo.User;
import com.atcx.service.MajorTeacherService;
import com.atcx.service.UserService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class MajorTeacherServiceImpl extends ServiceImpl<MajorTeacherMapper, MajorTeacher> implements MajorTeacherService {

    @Resource
    private MajorTeacherMapper majorTeacherMapper;
    @Resource
    private MajorMapper majorMapper;
    @Resource
    private UserService userService;
    @Override
    public int addMajorTeacher(MajorTeacher majorTeacher) {
        int insert = majorTeacherMapper.insert(majorTeacher);
        return insert;
    }

    @Override
    public List<MajorTeacher> getListByMajorId(int majorId) {
        QueryWrapper<MajorTeacher> wrapper = new QueryWrapper<>();
        //设置查询条件
        wrapper.eq("majorid",majorId);
        List<MajorTeacher> majorTeacherList = majorTeacherMapper.selectList(wrapper);
        return majorTeacherList;
    }

    @Override
    public int deleteTeacher(MajorTeacher majorTeacher) {
        QueryWrapper<MajorTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq(" userid",majorTeacher.getUserid()).eq("majorid",majorTeacher.getMajorid());
        int delete = majorTeacherMapper.delete(wrapper);
        return delete;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        Page<MajorTeacher> teacherPage = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<MajorTeacher> wrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null&&queryPageBean.getQueryString().trim().length()!=0){
            //有查询条件就添加查询条件
            wrapper.eq("majorid",queryPageBean.getQueryString());
        }
        Page<MajorTeacher> page = majorTeacherMapper.selectPage(teacherPage,wrapper);
        System.out.println(page.getRecords());
        List<MajorTeacher> records = page.getRecords();
        for (MajorTeacher record : records) {
            //查询系名称
            Major major = majorMapper.selectById(record.getMajorid());
            record.setMajorName(major.getMajorname());

            //查询用户名称
            User user = userService.getUserById(record.getUserid());
            System.out.println("当前用户"+user);
            record.setTeacherName(user.getAccountname());
        }
        PageResult pageResult = new PageResult(page.getTotal(),page.getRecords());
        return pageResult;
    }

    @Override
    public int[] getAllUserId(Integer[] ids) {
        //ids是指专业ID
        List<Integer> listOne= new ArrayList<>();
        QueryWrapper<MajorTeacher> queryWrapper = new QueryWrapper<>();
            for (int i = 0; i < ids.length; i++) {
                System.out.println("专业ID" + ids[i]);
                queryWrapper.eq("majorid", ids[i]).or();
            }
            List<MajorTeacher> list = majorTeacherMapper.selectList(queryWrapper);
            for (MajorTeacher majorTeacher : list) {
                listOne.add(majorTeacher.getUserid());
                System.out.println(majorTeacher);
            }


        System.out.println("公共活动邀请人ID"+listOne.get(0).toString());

        int[] arr = new int[listOne.size()];
        for (int i = 0; i < listOne.size(); i++) {
            arr[i] = listOne.get(i);
        }
        return arr;
    }



}

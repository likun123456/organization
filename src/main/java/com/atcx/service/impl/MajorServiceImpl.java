package com.atcx.service.impl;

import com.atcx.mapper.MajorMapper;
import com.atcx.mapper.MajorTeacherMapper;
import com.atcx.pojo.Major;
import com.atcx.pojo.MajorTeacher;
import com.atcx.service.MajorService;
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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Resource
    private MajorMapper majorMapper;
    @Resource
    private MajorTeacherMapper majorTeacherMapper;
    @Override
    public int addMajor(Major major) {
        int insert = majorMapper.insert(major);
        return insert;
    }

    @Override
    public int updateMajor(Major major) {
        int i = majorMapper.updateById(major);
        return i;
    }

    @Override
    public int deleteMajor(int majorId) {
        //删除系信息时首先判断关联表数据
        QueryWrapper<MajorTeacher> wrapper = new QueryWrapper<>();
        //删除系首先删除系职工信息
        wrapper.eq("majorid",majorId);
        int delete = majorTeacherMapper.delete(wrapper);
        int i = majorMapper.deleteById(majorId);
        return i;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        QueryWrapper<Major> wrapper = new QueryWrapper<>();
        if (queryPageBean.getQueryString() != null&&queryPageBean.getQueryString().trim().length()!=0){
            //分页查询条件为专业名称
            wrapper.eq("majorname",queryPageBean.getQueryString());
        }
//        wrapper.orderByDesc("id");
        Page<Major> majorPage = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Major> page = majorMapper.selectPage(majorPage, wrapper);
        List<Major> records = page.getRecords();
        for (Major record : records) {
            QueryWrapper<MajorTeacher> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("majorid",record.getId());
            record.setNumbers(majorTeacherMapper.selectCount(wrapper1));
        }
        PageResult pageResult = new PageResult(page.getTotal(),page.getRecords());
        return pageResult;
    }

    @Override
    public List<Major> getAllMajor() {
        List<Major> majors = majorMapper.selectList(new QueryWrapper<Major>());
        return majors;
    }
}

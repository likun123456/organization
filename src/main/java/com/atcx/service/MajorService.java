package com.atcx.service;

import com.atcx.pojo.Major;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
public interface MajorService extends IService<Major> {
    //添加
    public int addMajor(Major major);
    //修改
    public int updateMajor(Major major);
    //删除
    public int deleteMajor(int majorId);
    //分页查找
    public PageResult findByPage(QueryPageBean queryPageBean);
    //查询所有系信息
    List<Major> getAllMajor();
}

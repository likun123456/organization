package com.atcx.service;

import com.atcx.pojo.User;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
public interface UserService extends IService<User> {
    //添加
    public int addUser(User user);
    //修改
    public int updateUser(User user);
    //删除
    public int deleteUser(int userId);
    //分页查询
    public PageResult findByPage(QueryPageBean queryPageBean);
    //通过账户密码查询单个
    public User getOneUserByUP(User user);
    //通过用户ID查询用户
    public User getUserById(int UserId);
}

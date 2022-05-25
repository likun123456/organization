package com.atcx.controller;


import com.atcx.pojo.User;
import com.atcx.service.UserService;
import com.atcx.util.PageResult;
import com.atcx.util.QueryPageBean;
import com.atcx.util.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/user")
public class UserController {


    private Result result;
    @Resource
    private UserService userService;
    //从cookie中获取用户ID查询当前用户信息
    @RequestMapping("getUser")
    public Result getUser(HttpServletRequest request){
        int UserId = 0;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")){
                String value = cookie.getValue();
                UserId = Integer.parseInt(value);
            }
        }
        User user = userService.getById(UserId);
        if (user != null){
            result = new Result(true,"查询当前用户信息成功",user);
        }else {
            result = new Result(false,"查询失败");
        }

      return result;
    }
    //退出
    @RequestMapping("exit")
    public void exit(HttpServletRequest request,HttpServletResponse response){
        System.out.println("退出被访问");
            //先删除cookie里边数据
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                cookie = new Cookie(cookie.getName(), null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

    }
    @RequestMapping("adminLogin")
    public void adminLogin(String username, String password, HttpServletResponse response){
        User oneUser = new User();
        oneUser.setUsername(username);
        oneUser.setPassword(password);
        User user = userService.getOneUserByUP(oneUser);
        System.out.println(user);
        if (user!= null){
            //管理员登录
            try {
                if (user.getRank().equals("A")){
                    Cookie cookie = new Cookie("userId", user.getId().toString());
                    cookie.setMaxAge(60*60);//单位是秒
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    response.sendRedirect("../pages/AdminPage.html");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.sendRedirect("../login/index.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("userLogin")
    public void userLogin(String username,String password,HttpServletResponse response){
        User oneUser = new User();
        oneUser.setUsername(username);
        oneUser.setPassword(password);
        User user = userService.getOneUserByUP(oneUser);
        System.out.println("当前用户"+user);
        try {
            if (user!=null){
                if (user.getRank().equals("B")){
                    Cookie cookie = new Cookie("userId", user.getId().toString());
                    cookie.setMaxAge(60*60);//单位是秒
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    response.sendRedirect("../pages/TeacherPage.html");
                }
            }else {
                response.sendRedirect("../login/index.html");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/sysLogin")
    public void sysLogin(String username,String password,HttpServletResponse response) {
        User oneUser = new User();
        oneUser.setUsername(username);
        oneUser.setPassword(password);
        User user = userService.getOneUserByUP(oneUser);
        System.out.println("当前用户"+user);
        try {
            if (user!=null){
                if (user.getRank().equals("C")){
                    Cookie cookie = new Cookie("userId", user.getId().toString());
                    cookie.setMaxAge(60*60);//单位是秒
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    response.sendRedirect("../pages/SysPage.html");
                }
            }else {
                response.sendRedirect("../login/index.html");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //添加用户
    @RequestMapping("addUser")
    public Result addUser(@RequestBody User user){
        int i = userService.addUser(user);
        if (i>0){
            result = new Result(true,"添加用户成功");
        }else {
            result = new Result(false,"添加用户失败");
        }
        return result;
    }
    //修改用户
    @RequestMapping("updateUser")
    public Result updateUser(@RequestBody User user){
        int i = userService.updateUser(user);
        if (i>0){
            result = new Result(true,"修改用户成功");
        }else {
            result = new Result(false,"修改用户失败");
        }
        return result;
    }
    //删除用户
    @RequestMapping("deleteUser")
    public Result deleteUser(int id){
        int i = userService.deleteUser(id);
        if (i>0){
            result = new Result(true,"删除用户成功");
        }else {
            result = new Result(false,"删除用户失败,请先删除关联信息");
        }
        return result;
    }
    //分页查询
    @RequestMapping("findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = userService.findByPage(queryPageBean);
        if (page != null){
            result = new Result(true,"查询用户信息成功",page);
        }else {
            result = new Result(false,"查询用户信息失败");
        }
        return result;
    }
}


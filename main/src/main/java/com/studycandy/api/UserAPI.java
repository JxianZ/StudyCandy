package com.studycandy.api;

import com.studycandy.core.BaseController;
import com.studycandy.model.User;
import com.studycandy.model.UserInfo;
import com.studycandy.service.PostService;
import com.studycandy.service.SchoolService;
import com.studycandy.service.UserInfoService;
import com.studycandy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.studycandy.constant.Constant.SESSION_CURRENT_USER;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Yxm on 2017/5/19.
 */
@Controller
@RequestMapping("/app/user")
public class UserAPI extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private PostService postService;
    //登录功能
    @RequestMapping(value = {"/login"} ,method = POST)
    public String login(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam String username,
                           @RequestParam String password){
        User entity = userService.loginGetObj(username, password);
        if (entity == null) {
            return ajaxReturn(response, null, "用户名或密码错误", -1);
        } else {
            //TODO 让手机获得登录用户信息
            return ajaxReturn(response, entity, "登陆成功", 0);
        }
    }
    //注册功能
    @RequestMapping(value = "/register",method = POST)
    public String register(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String nickname,
                           @RequestParam String email){
        if (userService.getUserByUsername(username) != null) {
            return ajaxReturn(response,null,"该用户名已被注册",-1);
        }
        User user = new User();
        user.setUserUsername(username);
        user.setUserPassword(password);
        user.setUserNickname(nickname);
        user.setUserEmail(email);
        try {
            userService.setUser(user);
            //创建userInfo表
            try {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(userService.getUserByUsername(username).getId());
                userInfo.setUserGender("male");
                userInfo.setUserIntegral(50);
                userInfo.setUserMoney(0);
                userInfoService.saveUserInfo(userInfo);
            }catch (Exception e){
                userService.deleteUser(userService.getUserByUsername(username).getId());
                return ajaxReturn(response, null, "注册失败", -1);
            }
            this.getHttpSession(request).setAttribute(SESSION_CURRENT_USER, user);
            return ajaxReturn(response, null, "注册成功", 0);
        } catch (Exception e) {
            return ajaxReturn(response, null, "注册失败", -1);
        }
    }
    //TODO 完善个人信息 修改账号信息
}
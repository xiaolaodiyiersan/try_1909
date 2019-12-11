package com.qf.contrller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sso")
public class SsoConroller {

    @Reference
    IUserService userService;


    /**
     * 进入登陆页面
     * @return
     */
    @RequestMapping("tologin")
    public String tologin(){
        return "login";
    }
    /**
     * 进入注册页面
     * @return
     */
    @RequestMapping("toregister")
    public String toregiest(){
        return "register";
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    public String register(User user, Model model){
       int result = userService.register(user);
       if(result == -1){
           model.addAttribute("msg","用户名已存在");
           return "register";
       }else if(result == -2){
           model.addAttribute("msg","邮箱已注册");
           return "register";
        }else {
           return "login";
       }
    }
}

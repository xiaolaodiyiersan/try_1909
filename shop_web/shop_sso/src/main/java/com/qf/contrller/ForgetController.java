package com.qf.contrller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/forget")
public class ForgetController {
    @Reference
    IUserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 进入忘记密码页面
     *
     * @return
     */
    @RequestMapping("/toforget")
    public String toforget() {
        return "forget";
    }

    @RequestMapping("/toSetPass")
    @ResponseBody
    public ResultData<Map<String, String>> toSetPass(String username) {

        User user = userService.queryUser(username);
        if (user == null) {
            return new ResultData<Map<String, String>>().setCode(ResultData.errorList.ERROR).setMsg("用户不存在");
        } else {

            //发送到redis
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uuid, username);
            redisTemplate.expire(uuid, 10, TimeUnit.SECONDS);

            String url = "http://localhost:8082/forget/goSetPass?token=" + uuid;

            Email email = new Email()
                    .setTo(user.getEmail())
                    .setContext("点击这<a href='" + url + "'>里找</a>回密码")
                    .setSendTime(new Date())
                    .setSubject("找回密码");
            rabbitTemplate.convertAndSend("mail_exchange", "", email);

            Map<String, String> map = new HashMap<>();
            String showemail = user.getEmail().replace(user.getEmail().substring(3, user.getEmail().lastIndexOf("@")), "xxxxxx");
            String toemail = "mail." + user.getEmail().substring(user.getEmail().lastIndexOf("@") + 1);
            map.put("showemail", showemail);
            map.put("toemail", toemail);
            return new ResultData<Map<String, String>>().setCode(ResultData.errorList.OK).setData(map).setMsg("发送成功！");

        }
    }

    @RequestMapping("/goSetPass")
    public String goSetPass() {

        return "setpass";
    }
@RequestMapping("/setpass")
    public String setpass(String password,String token , Model model) {
        String username =  redisTemplate.opsForValue().get(token);
        if(username != null){
            userService.setpass(password,username);
            redisTemplate.delete(token);
            model.addAttribute("msg","修改成功");
        }else{
            return "mailerror";
        }
        return "setpass";
    }

}

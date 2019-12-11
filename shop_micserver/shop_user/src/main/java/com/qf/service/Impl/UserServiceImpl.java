package com.qf.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public int register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        QueryWrapper<User> wrapper2 = new QueryWrapper<>();
        wrapper.eq("email", user.getEmail());
        User user1 = userMapper.selectOne(wrapper);
        User user2 = userMapper.selectOne(wrapper2);
        if (user1 != null) {
            return -1;
        } else if (user2 != null) {
            return -2;
        }
        userMapper.insert(user);
        return 0;
    }

    @Override
    public User queryUser(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public void setpass(String password, String username) {
       User user = this.queryUser(username);
       user.setPassword(password);
        userMapper.updateById(user);
    }
}

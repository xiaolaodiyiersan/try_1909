package com.qf.service;

import com.qf.entity.User;

public interface IUserService {
    int register(User user);

    User queryUser(String username);

    void setpass(String password, String username);
}

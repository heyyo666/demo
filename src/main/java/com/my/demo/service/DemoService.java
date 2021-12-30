package com.my.demo.service;

import com.my.demo.domain.Admin;
import com.my.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin login(String username,String password){
        return  adminMapper.login(username,password);
    }

    public void register(Admin admin){
        adminMapper.register(admin);
    }
}

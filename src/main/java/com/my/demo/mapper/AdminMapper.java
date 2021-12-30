package com.my.demo.mapper;

import com.my.demo.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    public Admin login(String username,String password);
    public void register(Admin admin);
}

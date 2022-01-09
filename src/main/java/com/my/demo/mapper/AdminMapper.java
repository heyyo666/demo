package com.my.demo.mapper;

import com.my.demo.domain.Admin;
import com.my.demo.domain.FileEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    public Admin login(String username,String password);
    public void register(Admin admin);
    public List<FileEntity> fileList();
    public FileEntity selectOne(String fileName);
    public void fileAdd(FileEntity fileEntity);
    public void updateCount(FileEntity fileEntity);
    public Admin findAdmin(String username);
    public void addPrivateKey(Admin admin);

}

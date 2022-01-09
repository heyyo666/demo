package com.my.demo.service;

import com.my.demo.Utils.FileWithSM2;
import com.my.demo.domain.Admin;
import com.my.demo.domain.FileEntity;
import com.my.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DemoService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin login(String username,String password){
        return  adminMapper.login(username,password);
    }

    public void register(Admin admin){
        admin.setPrivateKey("MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgsTwYNqhtSjlpYDQnaKaEQbn9kVdSW3RJnFejbKTYa5KgCgYIKoEcz1UBgi2hRANCAATr9mY1oFGdmeKyO1/HGHavkXsxeOvZGN/rCLn75aRSpTQA81p1Y36Nxll66nCuSk5pL56kGlgXBoeGUwYhtTMr");
        adminMapper.register(admin);
    }

    public List<FileEntity> fileList(){
        return adminMapper.fileList();
    }

    public void addFile(FileEntity fileEntity){
        adminMapper.fileAdd(fileEntity);
    }

    public void encryption(MultipartFile file, String out, String fileName) throws IOException {
            FileWithSM2.EncryptionTools.encryption(file,out,fileName);
        }

    public void decryption(String source,  String fileName) throws IOException {
        FileWithSM2.EncryptionTools.decryption(source,fileName);
    }
    public FileEntity selectOne(String fileName){
        return  adminMapper.selectOne(fileName);
    }

    public void updateCount(FileEntity fileEntity){
        adminMapper.updateCount(fileEntity);
    }
}

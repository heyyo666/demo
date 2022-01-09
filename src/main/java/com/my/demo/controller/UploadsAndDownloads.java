package com.my.demo.controller;

import com.my.demo.Utils.DateFormat;
import com.my.demo.domain.FileEntity;
import com.my.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/uploadsAndDownloads")
public class UploadsAndDownloads {

   private final String privateKeyStr = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgsTwYNqhtSjlpYDQnaKaEQbn9kVdSW3RJnFejbKTYa5KgCgYIKoEcz1UBgi2hRANCAATr9mY1oFGdmeKyO1/HGHavkXsxeOvZGN/rCLn75aRSpTQA81p1Y36Nxll66nCuSk5pL56kGlgXBoeGUwYhtTMr";
    @Autowired
    private DemoService demoService;

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Map map, Model model) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "D:/test_encryption/";
        File dest = new File(filePath + fileName);
        try {
            map.put("upload_msg","上传成功");
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setFilePath(filePath);
            fileEntity.setCount(0);
            Date date = new Date();
            String strDate = DateFormat.changeDateFormat( date);
            fileEntity.setChangeTime(strDate);
            demoService.encryption(file,filePath,fileName);
            demoService.addFile(fileEntity);

            List<FileEntity> fileLists = demoService.fileList();
            if(fileLists==null){
                FileEntity noFile = new FileEntity();
                noFile.setFileName("无");
                noFile.setFilePath("无");
                noFile.setCount(-1);
                noFile.setChangeTime("无");
                fileLists.add(noFile);
            }
            model.addAttribute("fileLists", fileLists);

            return "index2";
        } catch (IOException e) {
            System.out.println(e);
        }
        map.put("upload_msg","上传失败");
        return "index2";
    }

    @RequestMapping("/download")
    public String dowmload(@RequestParam("source") String source,
                        @RequestParam("fileName") String fileName,
                        Map map,Model model) {

        try {
            map.put("download_msg","下载成功");


            demoService.decryption(source,fileName);
            FileEntity fileEntity = demoService.selectOne(fileName);
            if(fileEntity!=null){
                Integer count = fileEntity.getCount() + 1;
                fileEntity.setCount(count);
                demoService.updateCount(fileEntity);
            }

            List<FileEntity> fileLists = demoService.fileList();
            if(fileLists==null){
                FileEntity noFile = new FileEntity();
                noFile.setFileName("无");
                noFile.setFilePath("无");
                noFile.setCount(-1);
                noFile.setChangeTime("无");
                fileLists.add(noFile);
            }
            model.addAttribute("fileLists", fileLists);

            return "index2";
        } catch (IOException e) {
            map.put("download_msg","上传失败");
        }

        return "index2";
    }

}






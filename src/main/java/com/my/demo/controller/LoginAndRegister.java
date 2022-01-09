package com.my.demo.controller;

import com.my.demo.domain.Admin;
import com.my.demo.domain.FileEntity;
import com.my.demo.service.DemoService;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/loginAndRegister")
public class LoginAndRegister {

    @Autowired
    private DemoService demoService;


    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("checkcode") String getcheckcode,
                        Map map, HttpSession session, Model model)  {
//      获取验证码数据
        String checkcode =getcheckcode ;
//        验证码效验
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//取保验证码一次性
        if(!checkcode_server.equalsIgnoreCase(checkcode)){
            map.put("login_msg","验证码错误!");
            return "login";
        }

//        调用Service查询

        Admin login = demoService.login(username, password);
        if(login!=null){
//            登入成功
//            将用户存入session
            session.setAttribute("login",login);
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
        }else {
            map.put("login_msg","用户名或密码错误");
            return "login";
        }
    }





    @RequestMapping("/register")
    public void register(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;character=utf-8");
        Map<String, String[]> map = req.getParameterMap();
        Admin admin = new Admin();
        DateConverter converter = new DateConverter();

        converter.setPattern(new String("yyyy-MM-dd"));
        ConvertUtils.register((Converter) converter, Date.class);
        try {
            BeanUtils.populate(admin,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(admin);
        demoService.register(admin);
        if(admin!=null){
            resp.sendRedirect(req.getContextPath() + "/login.html");;

        }else {
            resp.sendRedirect(req.getContextPath() + "/register.html");
        }

    }

}

package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 */
public class LoginServlet {
    public void service(HttpRequest request, HttpResponse response){
        System.out.println("LoginServlet:开始处理登录业务。。。。");
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if(username==null||password==null){
            File file = new File("./webapps/myweb/login_fail.html");
            response.setEntity(file);
            return;
        }
        try (
                RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
        ){
            byte[] data = new byte[49];
            for (int i = 0; i < raf.length() / 200; i++) {
                raf.seek(i*200);
                raf.read(data);
                String name = new String(data,"utf-8").trim();
                if(name.equals(username)){
                    raf.read(data);
                    name = new String(data,"utf-8").trim();
                    if (name.equals(password)){
                        File file = new File("./webapps/myweb/login_success.html");
                        response.setEntity(file);
                        return;
                    }
                    break;
                }
            }
            File file = new File("./webapps/myweb/login_fail.html");
            response.setEntity(file);
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("LoginServlet:处理登陆业务完毕。");
    }
}

package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.vo.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShowAllUserServlet {
    public void service(HttpRequest request, HttpResponse response){
        System.out.println("ShowAllUserServlet:开始处理用户列表页面。。。");
        //1.先将user.dat文件中的所有用户信息读取出来
        List<User> list = new ArrayList<>();//保存所有用户信息的集合
        try(
                RandomAccessFile raf = new RandomAccessFile("user.dat","r");
        ){
            for (int i = 0; i < raf.length() / 200; i++) {
                byte[] data = new byte[49];
                raf.read(data);
                String username = new String(data,"utf-8").trim();
                raf.read(data);
                String password = new String(data,"utf-8").trim();
                raf.read(data);
                String nickname = new String(data,"utf-8").trim();
                raf.read(data);
                String email = new String(data,"utf-8").trim();

                int age = raf.readInt();
                User user = new User(username,password,nickname,email,age);
                System.out.println(user.toString());
                list.add(user);

            }
        }catch(IOException e){e.printStackTrace();}
        //根据读取到的用户信息生成页面

            PrintWriter pw = response.getWriter();
            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>");
            pw.println("    <meta charset=\"UTF-8\">");
            pw.println("    <title>用户列表</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("    <center>");
            pw.println("        <h1>用户列表</h1>");
            pw.println("        <form action=\"./userList\"method=\"post\">");
            pw.println("            <table border=\"1\">");
            pw.println("                <tr>");
            pw.println("                    <td align=\"center\">用户名</td>");
            pw.println("                    <td align=\"center\">密码</td>");
            pw.println("                    <td align=\"center\">昵称</td>");
            pw.println("                    <td align=\"center\">邮箱</td>");
            pw.println("                    <td align=\"center\">年龄</td>");
            pw.println("                </tr>");
            for (User user:list) {
                pw.println("                <tr>");
                pw.println("                    <td align=\"center\">"+user.getUsername()+"</td>");
                pw.println("                    <td align=\"center\">"+user.getPassword()+"</td>");
                pw.println("                    <td align=\"center\">"+user.getNickname()+"</td>");
                pw.println("                    <td align=\"center\">"+user.getEmail()+"</td>");
                pw.println("                    <td align=\"center\">"+user.getAge()+"</td>");
                pw.println("                </tr>");
            }
            pw.println("            </table>");
            pw.println("        </form>");
            pw.println("        <a href=\"/myweb/index.html\" >返回</a>");
            pw.println("    </center>");
            pw.println("</body>");
            pw.println("</html>");
            //设置正文类型，告知浏览器这是一个页面
            response.setcontentType("text/html");


        System.out.println("ShowAllUserServlet:用户列表页面处理完毕！");
    }
}

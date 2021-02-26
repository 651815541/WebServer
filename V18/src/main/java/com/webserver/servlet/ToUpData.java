package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class ToUpData {
    public void service(HttpRequest request, HttpResponse response){
        String username = request.getParameter("username");
        System.out.println(username);

        try(
                RandomAccessFile raf = new RandomAccessFile("user.dat","r");
        ){
            byte[] data = new byte[49];
            for (int i = 0; i < raf.length() / 200; i++) {
                raf.seek(i*200);
                 raf.read(data);
                String name = new String(data,"utf-8").trim();
                if(name.equals(username)){
                    System.out.println(name);
                    raf.read(data);
                    String pwd = new String(data,"utf-8").trim();
                    System.out.println(pwd);
                    raf.read(data);
                    String nick = new String(data,"utf-8").trim();
                    System.out.println(nick);
                    raf.read(data);
                    String e_mail = new String(data,"utf-8").trim();
                    System.out.println(e_mail);

                    int age = raf.readInt();

                    PrintWriter pw = response.getWriter();
                    pw.println("<!DOCTYPE html>");
                    pw.println("<html lang=\"en\">");
                    pw.println("<head>");
                    pw.println("    <meta charset=\"UTF-8\">");
                    pw.println("    <title>修改信息</title>");
                    pw.println("</head>");
                    pw.println("<body>");
                    pw.println("    <center>");
                    pw.println("        <h1>修改信息</h1>");
                    pw.println("        <form action=\"./updata\"method=\"post\">");
                    pw.println("            <table border=\"1\">");
                    pw.println("                <tr>");
                    pw.println("                    <td>用户名</td>");
                    pw.println("                    <td><input name=\"username\" value=\""+name+"\" type=\"text\" style=\"border-radius: 5px\"></td>");
                    pw.println("                </tr>");
                    pw.println("                <tr>");
                    pw.println("                    <td>密码</td>");
                    pw.println("                    <td><input name=\"password\" value=\""+pwd+"\" type=\"password\" style=\"border-radius: 5px\"></td>");
                    pw.println("                </tr>");
                    pw.println("                <tr>");
                    pw.println("                    <td>昵称</td>");
                    pw.println("                    <td><input name=\"nickname\" value=\""+nick+"\" type=\"text\" style=\"border-radius: 5px\"></td>");
                    pw.println("                </tr>");
                    pw.println("                <tr>");
                    pw.println("                    <td>邮箱</td>");
                    pw.println("                    <td><input name=\"email\"  value=\""+e_mail+"\" type=\"email\" style=\"border-radius: 5px\"></td>");
                    pw.println("                </tr>");
                    pw.println("                <tr>");
                    pw.println("                    <td>年龄</td>");
                    pw.println("                    <td><input name=\"age\" value=\""+age+"\" type=\"text\" style=\"border-radius: 5px\"></td>");
                    pw.println("                </tr>");
                    pw.println("                <tr>");
                    pw.println("                    <td align=\"center\" colspan=\"2\">");
                    pw.println("                        <input type=\"submit\" value=\"修改\" style=\"border-radius: 5px\">");
                    pw.println("                    </td>");
                    pw.println("                </tr>");
                    pw.println("            </table>");
                    pw.println("        </form>");
                    pw.println("    </center>");
                    pw.println("</body>");
                    pw.println("</html>");

                    //设置正文类型，告知浏览器这是一个页面
                    response.setcontentType("text/html");
                    return;
                }
            }




        }catch(IOException e){
            e.printStackTrace();
        }

    }
}

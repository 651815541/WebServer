package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Servlet是JAVAEE标准中的一个接口，意思是运行在服务端的小程序
 * 我们用他来处理某个具体的请求
 *
 * 当前Servlet用于处理用户注册业务
 */
public class RegServlet {
    public void service(HttpRequest request, HttpResponse response){
        System.out.println("RegServlet:开始处理用户注册");
        /*
        1.通过request获取用户在注册页面上输入的注册信息（表单上的信息）
        2.将用户的注册信息写入文件user.dat中
        3.设置response给客户端响应注册结果页面
         */

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageString = request.getParameter("age");
        String email = request.getParameter("email");


        /*
        必要的验证工作，如果上述四项有空的，或者年龄不是一个数字时，直接响
        应给客户端一个注册错误的提示页面：reg_info_error.html，里面居中
        显示一行字：注册信息输入有误，请重新注册。
        注：该页面也放在webapps、myweb这个网络应用中
         */
        if(username==null  ||
           password==null  ||
           nickname==null  ||
           ageString==null||
           !ageString.matches("[0-9]{1,3}") ||
           email==null
        )
        {
            File file = new File("./webapps/myweb/reg_info_error.html");
            response.setEntity(file);
            return;
        }

            int age = Integer.parseInt(ageString);
            System.out.println(username + "," + password + "," + nickname + "," + age);
        /*
        2.每条用户信息占用200字节，其中用户名，密码，昵称，邮箱为字符串各占49字节，年龄为int值占4字节
         */
            try (
                    RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
            ) {

                /*
                验证是否为重复用户
                现读取user.dat文件中现有的所有用户的名字，并与本次注册的用户名比对，
                如果存在则直接响应页面：hava_user.html,居中显示一行字：该用户已存在，请重新注册
                如果不存在则进行注册操作。
                 */
                byte[] data = new byte[49];
                for (int i = 0; i < raf.length() / 200; i++) {
                    raf.seek(i*200);
                    raf.read(data);
                    String name = new String(data,"utf-8").trim();
                    if(name.equals(username)){
                        File file = new File("./webapps/myweb/have_user.html");
                        response.setEntity(file);
                        return;
                    }
                }
                raf.seek(raf.length());

                data = username.getBytes("utf-8");
                data = Arrays.copyOf(data, 49);
                raf.write(data);

                data = password.getBytes("utf-8");
                data = Arrays.copyOf(data, 49);
                raf.write(data);

                data = nickname.getBytes("utf-8");
                data = Arrays.copyOf(data, 49);
                raf.write(data);

                data = email.getBytes("utf-8");
                data = Arrays.copyOf(data, 49);
                raf.write(data);

                raf.writeInt(age);

                File file = new File("./webapps/myweb/reg_success.html");
                response.setEntity(file);

                System.out.println("RegServlet:用户注册处理完毕");



            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}

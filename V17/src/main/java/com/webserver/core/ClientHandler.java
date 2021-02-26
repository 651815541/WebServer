package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.LoginServlet;
import com.webserver.servlet.RegServlet;
import com.webserver.servlet.ShowAllUserServlet;

import java.io.*;
import java.net.Socket;

/**
 *  负责与指定客户端进行HTTP交互
 *  HTTP协议要求与客户端的交互规则采取一问一答的方式。因此，处理客户端交互以3步形式完成:
 *  1:解析请求(一问)
 *  2:处理请求
 *  3:发送响应(一答)
 */
public class ClientHandler implements Runnable{
    private Socket socket;
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run() {
        try{
            //1解析请求
            HttpRequest request = new HttpRequest(socket);
            HttpResponse response = new HttpResponse(socket);

            //2处理请求
            //获取抽象路径中的请求部分
            String path = request.getRequestURI();

            //判断本次请求是否为请求某个业务
            if("/myweb/regUser".equals(path)){
                RegServlet servlet = new RegServlet();
                servlet.service(request,response);
            }else if("/myweb/loginUser".equals(path)){
                LoginServlet servlet = new LoginServlet();
                servlet.service(request,response);
            }else if("/myweb/showAllUser".equals(path)){
                ShowAllUserServlet servlet = new ShowAllUserServlet();
                servlet.service(request,response);
            }else{

                File file = new File("./webapps" + path);
                if (file.exists() && file.isFile()) {

                    System.out.println("该资源已找到：" + file.getName());
                    response.setEntity(file);
                } else {
                    File notFound = new File("./webapps/root/404.html");
                    response.setStatusCode(404);
                    response.setStatusReason("NotFound");
                    response.setEntity(notFound);
                }
            }

            //统一设置其他响应头
            response.putHeader("Server","WebServer");//Server头是告知浏览器服务端是谁

            //3发送响应
            response.flush();

            System.out.println("响应发送完毕");
        }catch(EmptyRequestException e){
            //什么都不做
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //处理完毕后与客户端断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
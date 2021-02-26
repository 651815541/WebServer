package com.webserver.http;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 响应对象，当前类的每一个实例用于表示给客户端发送一个HTTP内容
 * 每个响应由三部分组成：
 * 状态行，响应头，响应正文（正文部分可以没有）
 */
public class HttpResponse {
    //状态行相关信息
    private int statusCode = 200;
    private String statusReason = "OK";
    //响应头相关信息
    private Map<String,String> headers = new HashMap<>();
    //响应正文相关信息
    private File entity;//响应正文对应的实体文件

    private Socket socket;
    public HttpResponse(Socket socket) {
        this.socket = socket;
    }

    public void flush(){
        //1.发送状态行
        sendStatusLine();
        //2.发送响应头
        sendHeader();
        //3.发送响应正文
        sendContent();
    }

    //状态行相关信息
    private void sendStatusLine(){
        System.out.println("HttpResponse:开始发送状态行。。。");
        try{
            OutputStream out = socket.getOutputStream();
            String line = "HTTP/1.1"+" "+statusCode+" "+statusReason;
            System.out.println("状态行："+line);
            println(line);
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("HttpResponse:状态行发送完毕");
    }
    //响应头相关信息
    private void sendHeader(){
        System.out.println("HttpResponse:开始发送响应头。。。");
        try{
//            Set<Map.Entry<String, String>> set = headers.entrySet();
//            for(Map.Entry<String,String> e :set){
//                String name = e.getKey();
//                String value = e.getValue();
//                String line = name + ": "+value;
//                System.out.println("响应头："+line);
//                println(line);
//            }
            //JDK之后Map也支持foreach，使用lambda表达式遍历
            headers.forEach(
                    (k,v)->{
                        try {
                            String line = k+": "+v;
                            System.out.println("响应头："+line);
                            println(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            println("");

        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("HttpResponse:响应头发送完毕");
    }
    //响应正文相关信息
    private void sendContent(){
        System.out.println("HttpResponse:开始发送响应正文。。。");
        if(entity!=null) {
            try (
                    FileInputStream fis = new FileInputStream(entity);
            ) {
                OutputStream out = socket.getOutputStream();
                int len;
                byte[] buf = new byte[1024 * 10];
                while ((len = fis.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("HttpResponse:响应正文发送完毕");
    }
    private void println(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes("ISO8859-1");
        out.write(data);
        out.write(13);//单独发送回车符
        out.write(10);//单独发送换行符
    }

    /**
     * 添加一个响头
     * @param name
     * @param value
     */
    public void putHeader(String name, String value){
        headers.put(name,value);
    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;

        int index=entity.getName().lastIndexOf(".");
        String ext = entity.getName().substring(index+1);
        String type = HttpContext.getMimeType(ext);
        putHeader("content",type);
        putHeader("Content-Length",entity.length()+"");
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}

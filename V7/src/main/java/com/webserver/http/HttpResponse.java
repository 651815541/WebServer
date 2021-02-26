package com.webserver.http;

import java.io.*;
import java.net.Socket;

public class HttpResponse {
    //现发送一个固定的页面，测试浏览器是否可以正常接收
    private File file = new File("./webapps");
    private Socket socket;
    public HttpResponse(Socket socket,String path){
        this.socket=socket;
        this.file = new File("./webapps"+path);
        if(state(file)){
            String line = "HTTP/1.1 200 OK";
            sendResponseline(line);
            line = "Content-Type: text/html";
            sendResponseContent(line);
            line = "Content-Length: "+file.length();
            sendResponseContent(line,true);
            sendRespinseData();
        }else{
            file = new File("./webapps/root/404.html");
            String line = "HTTP/1.1 404 NotFound";
            sendResponseline(line);
            line = "Content-Type: text/html";
            sendResponseContent(line);
            line = "Content-Length: "+file.length();
            sendResponseContent(line,true);
            sendRespinseData();
        }

    }
    private void sendResponseline(String line){
        try {
            write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendResponseContent(String line){

        try {
            write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendResponseContent(String line,boolean bool){

        try {
            write(line,bool);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendRespinseData() {
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String line) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes("ISO8859-1");
        out.write(data);
        out.write(13);//单独发送回车符
        out.write(10);//单独发送换行符
    }private void write(String line,boolean bool) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] data = line.getBytes("ISO8859-1");
        out.write(data);
        out.write(13);//单独发送回车符
        out.write(10);//单独发送换行符
        if(bool==true){
            out.write(13);//单独发送回车符
            out.write(10);
        }
    }
    private void write() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
        int len;
        byte[] buf=new byte[1024*10];
        while((len=fis.read(buf))!=-1){
            out.write(buf,0,len);
        }
    }
    private boolean state(File file){
        if(!file.isDirectory()){
            if(file.exists()){
                return false;
            }
        }
        return false;
    }


}

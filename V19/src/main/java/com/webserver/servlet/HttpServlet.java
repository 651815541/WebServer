package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个类是所有Servlet的超类
 */
public abstract class HttpServlet {

    public  void service(HttpRequest request, HttpResponse response){
        //首先得到本次的请求方式
        String method = request.getMethod();
        if("GET".equalsIgnoreCase(method)){
            doGet(request,response);
        }else if("POST".equalsIgnoreCase(method)){
            doPost(request,response);
        }
    }

    /**
     * 用来处理GET请求
     * @param request
     * @param response
     */
    public abstract void doGet(HttpRequest request, HttpResponse response);

    /**
     * 用来处理POST请求
     * @param request
     * @param response
     */
    public abstract void doPost(HttpRequest request, HttpResponse response);


}


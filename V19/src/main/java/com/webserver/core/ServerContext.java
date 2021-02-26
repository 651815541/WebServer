package com.webserver.core;

import com.webserver.servlet.HttpServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerContext {
    private static Map<String,String> servletMaping = new HashMap<>();
    private static Map<String, HttpServlet> HttpservletMaping = new HashMap<>();
    static{
        initservletMaping();
        initHttpservletMaping();
    }

    /**
     * 解析config.servlets.xml文件，将跟标签下所有名为<servlet>的标签获取到，并将其中
     * 属性path的值作为key
     * classname的值利用反射实例化对应的类并作为value
     * 保存到servlettMapping这个Map完成初始化操作。
     */
    private static void initservletMaping(){

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read("./config/servlets.xml");
            Element root = doc.getRootElement();
            List<Element> list = root.elements("servlets");
            for (Element webEle:list) {
                String path = webEle.elementText("path");
                String classname = webEle.elementText("classname");
                servletMaping.put(path,classname);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void initHttpservletMaping(){

        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read("./config/servlets.xml");
            Element root = doc.getRootElement();
            List<Element> list = root.elements("servlets");
            for (Element webEle:list) {
                String path = webEle.elementText("path");
                String classname = webEle.elementText("classname");
                Class cls = Class.forName(classname);
                HttpServlet o = (HttpServlet)cls.newInstance();
                HttpservletMaping.put(path, o);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getServletMaping(String key) {
        return servletMaping.get(key);
    }

    public static  HttpServlet getHttpservletMaping(String key) {
        return HttpservletMaping.get(key);
    }
}

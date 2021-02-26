package com.webserver.http;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前类用于保存所有与HTTP协议相关的规定内容以便于重用
 */
public class HttpContext {
    /**
     * 资源后缀名与响应头Content-Type值的对应关系
     * key:资源后缀名
     * value:Content-Type对应值
     */
    private static Map<String,String> mimeMapping = new HashMap<>();

    static{
        initMimeMapping();
    }

     private static void initMimeMapping(){
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read("./config/web.xml");
            Element root = doc.getRootElement();
            List<Element> list = root.elements("mime-mapping");
            for (Element webEle:list) {
                String extension = webEle.elementText("extension");
                String mime_type = webEle.elementText("mime-type");
                mimeMapping.put(extension,mime_type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据给定的资源后缀名获取对应的Content—Type的值
     * @param ext
     * @return
     */
    public static String getMimeType(String ext) {
        return mimeMapping.get(ext);
    }


}

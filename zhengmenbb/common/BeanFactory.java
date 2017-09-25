package com.zhengmenbb.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BeanFactory {
    private static Map<String, BeanConfig> beans = new HashMap<String, BeanConfig>();

    private static Map<String, Object> objects = new HashMap<String, Object>();

    private static BeanFactory beanFactory;

    public Object getBean(String id) {
        if (beans.containsKey(id)) {
            BeanConfig bean = beans.get(id);
            String scope = bean.getScope();
            if(scope == null || scope.equals("")) {
                scope = "singleton";
            }

            if (scope.equalsIgnoreCase("singleton")) {
                if (objects.containsKey(id)) {
                    return objects.get(id);
                }
            }

            String className = bean.getClassName();
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                Object object = clz.newInstance();


                if (scope.equalsIgnoreCase("singleton")) {
                    objects.put(id, object);
                }

                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private BeanFactory(){}

    public static BeanFactory getInstance() {
        if(beanFactory == null) {
            beanFactory = new BeanFactory();
            beanFactory.init();
        }
        return beanFactory;
    }

    private void init() {
        InputStream inputStream = null;
        try {
            inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("bean.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element element = document.getDocumentElement();

            NodeList beanNodes = element.getElementsByTagName("bean");
            if(beanNodes == null) {
                return;
            }
            int beanLength = beanNodes.getLength();
            for(int i = 0; i < beanLength; i++) {
                Element beanElement = (Element) beanNodes.item(i);
                BeanConfig bean = new BeanConfig();
                String id = beanElement.getAttribute("id");
                bean.setId(id);

                String className = beanElement.getAttribute("class");
                bean.setClassName(className);

                String scope = beanElement.getAttribute("scope");
                bean.setScope(scope);
                beans.put(id, bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}

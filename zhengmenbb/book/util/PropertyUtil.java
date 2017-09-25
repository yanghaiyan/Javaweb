package com.zhengmenbb.book.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static Properties p = null;
    static {
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
            p = new Properties();
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getProperty(String key) {
        return p.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(PropertyUtil.getProperty("jdbc.driver"));
    }

}

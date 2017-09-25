package com.javaweb.exam.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Nathan.Yang
 * Get Database Properties
 */
public class PropertiesUtil {
    private static Properties properties = null;

    static {
        InputStream inputStream = null;

        try {
            inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("app.properties");
            properties = new Properties();
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }

    public static String getStaticUrl() {
        return properties.getProperty("static_url");
    }
}
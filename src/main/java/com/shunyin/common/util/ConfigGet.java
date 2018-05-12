package com.shunyin.common.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/5/12 10:45
 */
public class ConfigGet {

    public static String get(String key) {
        String param1 = "";
        try {
            Properties prop = new Properties();
            ClassPathResource re = new ClassPathResource("config.properties");
            InputStream in = re.getInputStream();

            prop.load(in);
            param1 = prop.getProperty(key).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return param1;
    }
}

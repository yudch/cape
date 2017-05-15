package org.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    private static Properties prop = null;

    static {
        prop = new Properties();
        //InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        try {
            FileInputStream in = new FileInputStream(new File("/etc/ecconfig/application.properties"));
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByKey(String key) {
        String value = prop.getProperty(key);
        return StringUtils.isBlank(value) ? "" : value;
    }
}

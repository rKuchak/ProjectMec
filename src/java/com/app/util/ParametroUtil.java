package com.app.util;

import java.io.InputStream;
import java.util.Properties;

public class ParametroUtil {

    public static String IDR_HOME = "";
    public static String PROPERTIES = "com/app/app.properties";
    public static String MAIL_HOST = "MAIL.HOST";
    public static String MAIL_AUTH = "MAIL.AUTH";
    public static String MAIL_FROM = "MAIL.FROM";
    public static String MAIL_TLS = "MAIL.TLS";
    public static String MAIL_PORT = "MAIL.PORT";
    public static String JDBC_DRIVER = "JDBC.DRIVER";
    public static String URL_DB = "URL.DB";
    public static String USER_DB = "USER.DB";
    public static String PWD_DB = "PWD.DB";
    public static String PWD_EMAIL = "PWD.EMAIL";
    
    private static Properties prop = null;
    
    public static String getProperty(String key) {
        if (prop == null) {
            loadProperties();
        }
        return prop.getProperty(key);
    }
    
    private static void loadProperties() {
        prop = new Properties();
        try {
            prop.load(getResource());
        }
        catch (Exception e){
        }
    }
    
    private static InputStream getResource() {
        return new ParametroUtil().getClass().getClassLoader().getResourceAsStream(PROPERTIES);
    }
}

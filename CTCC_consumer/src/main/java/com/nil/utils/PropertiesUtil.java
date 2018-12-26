package com.nil.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author lianyou
 * @version 1.0
 */
public class PropertiesUtil {
  public static Properties properties = null;

  static {
    InputStream inputStream = ClassLoader.getSystemResourceAsStream("hbase_consumer.properties");
    properties = new Properties();
    try {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
}

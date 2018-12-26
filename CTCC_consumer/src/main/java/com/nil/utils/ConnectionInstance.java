package com.nil.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
/**
 * @author lianyou
 * @version 1.0
 */
public class ConnectionInstance {
  private static Connection conn;

  public static synchronized Connection getConnection(Configuration conf) {
    try {
      if (conn == null || conn.isClosed()) {
        conn = ConnectionFactory.createConnection(conf);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return conn;
  }
}

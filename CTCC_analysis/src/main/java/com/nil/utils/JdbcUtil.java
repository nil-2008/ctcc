package com.nil.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * @author lianyou
 * @version 1.0
 */
public class JdbcUtil {
  /** 定义JDBC连接器实例化所需要的固定参数 */
  private static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

  private static final String MYSQL_URL =
      "jdbc:mysql://localhost:3306/db_telecom?useUnicode=true&characterEncoding=UTF-8";
  private static final String MYSQL_USERNAME = "root";
  private static final String MYSQL_PASSWORD = "mac_2016";

  /** 实例化JDBC连接器对象 */
  public static Connection getConnection() {
    try {
      Class.forName(MYSQL_DRIVER_CLASS);
      return DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /** 释放连接器资源 */
  public static void close(Connection connection, Statement statement, ResultSet resultSet) {
    try {
      if (resultSet != null && !resultSet.isClosed()) {
        resultSet.close();
      }

      if (statement != null && !statement.isClosed()) {
        statement.close();
      }

      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

package com.nil.utils;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * @author lianyou
 * @version 1.0
 */
public class JdbcInstance {
  private static Connection connection = null;

  private JdbcInstance() {}

  public static Connection getInstance() {
    try {
      if (connection == null || connection.isClosed() || !connection.isValid(3)) {
        connection = JdbcUtil.getConnection();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }
}

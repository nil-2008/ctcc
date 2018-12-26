package com.nil.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 该类用于存放用户请求的数据
 *
 * @author lianyou
 * @version 1.0
 */
@Setter
@Getter
public class QueryInfo {
  private String telephone;
  private String year;
  private String month;
  private String day;

  public QueryInfo() {
    super();
  }

  public QueryInfo(String telephone, String year, String month, String day) {
    super();
    this.telephone = telephone;
    this.year = year;
    this.month = month;
    this.day = day;
  }
}

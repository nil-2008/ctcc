package com.nil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随机产生通话记录信息并保存到文件中
 *
 * @author lianyou
 * @version 1.0
 * @date 2018/12/11 08:45
 */
public class ProductLog {
  /** 开始时间 */
  private String startTime = "2018-01-01";
  /** 结束时间 */
  private String endTime = "2018-12-31";

  /** 存放生产的电话号码 "17078388295" */
  private List<String> mobileList = new ArrayList<String>();
  /** 存放生产的电话号码和姓名 : "17078388295", "李雁" */
  private Map<String, String> ownerMap = new HashMap<String, String>();;
  /** 随机通话时间日期格式 */
  SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");

  SimpleDateFormat sdf_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  /** 通话时间格式 */
  DecimalFormat df = new DecimalFormat("0000");
  /** 存放产生的通话记录 */
  StringBuilder sb = new StringBuilder();

  /**
   * 初始化信息
   *
   * <p>正常该部分信息应该来源于数据库
   */
  public void initPhone() {
    mobileList.add("17078388295");
    mobileList.add("13980337439");
    mobileList.add("14575535933");
    mobileList.add("19902496992");
    mobileList.add("18549641558");
    mobileList.add("17005930322");
    mobileList.add("18468618874");
    mobileList.add("18576581848");
    mobileList.add("15978226424");
    mobileList.add("15542823911");
    mobileList.add("17526304161");
    mobileList.add("15422018558");
    mobileList.add("17269452013");
    mobileList.add("17764278604");
    mobileList.add("15711910344");
    mobileList.add("15714728273");
    mobileList.add("16061028454");
    mobileList.add("16264433631");
    mobileList.add("17601615878");
    mobileList.add("15897468949");

    ownerMap.put("17078388295", "李雁");
    ownerMap.put("13980337439", "卫艺");
    ownerMap.put("14575535933", "仰莉");
    ownerMap.put("19902496992", "陶欣悦");
    ownerMap.put("18549641558", "施梅梅");
    ownerMap.put("17005930322", "金虹霖");
    ownerMap.put("18468618874", "魏明艳");
    ownerMap.put("18576581848", "华贞");
    ownerMap.put("15978226424", "华啟倩");
    ownerMap.put("15542823911", "仲采绿");
    ownerMap.put("17526304161", "卫丹");
    ownerMap.put("15422018558", "戚丽红");
    ownerMap.put("17269452013", "何翠柔");
    ownerMap.put("17764278604", "钱溶艳");
    ownerMap.put("15711910344", "钱琳");
    ownerMap.put("15714728273", "缪静欣");
    ownerMap.put("16061028454", "焦秋菊");
    ownerMap.put("16264433631", "吕访琴");
    ownerMap.put("17601615878", "沈丹");
    ownerMap.put("15897468949", "褚美丽");
  }

  /**
   * 生产数据:16264433631,15714728273,2018-12-21 15:55:45,1595
   *
   * <p>对应名称:caller,callee.buildTime,Duration
   *
   * <p>翻译:主叫,被叫,通话建立时间,通话持续时间
   *
   * @return
   */
  public String product() {
    String caller = null;
    String callee = null;
    String callerName = null;
    String calleeName = null;

    // 生成主叫的随机索引
    int callerIndex = (int) (Math.random() * mobileList.size());
    // 通过随机索引获得主叫电话号码
    caller = mobileList.get(callerIndex);
    // 通过主叫号码,获得主叫姓名
    callerName = ownerMap.get(caller);
    while (true) {
      // 生成被叫的随机索引
      int calleeIndex = (int) (Math.random() * mobileList.size());
      // 通过随机索引获得被叫电话号码
      callee = mobileList.get(calleeIndex);
      // 通过被叫号码,获得被叫姓名
      calleeName = ownerMap.get(callee);
      // 去重判断、防止自己给自己打电话
      if (!caller.equals(callee)) {
        break;
      }
    }
    // 随机产生通话建立时间
    String buildTime = randomBuildTime(startTime, endTime);
    // 随机产生通话持续时间
    DecimalFormat df = new DecimalFormat("0000");
    String duration = df.format((int) (30 * 60 * Math.random()));
    StringBuilder sb = new StringBuilder();
    sb.append(caller)
        .append(",")
        .append(callee)
        .append(",")
        .append(buildTime)
        .append(",")
        .append(duration)
        .append("\r\n");
    return sb.toString();
  }

  /**
   * 将日志写入文件
   *
   * @param filePath 日志文件存放目录
   */
  public void writeLog(String filePath) {
    try {
      OutputStreamWriter osw =
          new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8");

      while (true) {
        Thread.sleep(500);
        String log = product();
        System.out.println(log);
        osw.write(log);
        // 需要手动flush,确保每条数据都写入文件一次
        osw.flush();
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 随机产生时间
   *
   * <p>startTimeTS + (endTimeTs - startTimeTs) * Math.random();
   *
   * @param startTime 开始时间段
   * @param endTime 结束时间段
   * @return yyyy-MM-dd HH:mm:ss
   */
  private String randomBuildTime(String startTime, String endTime) {

    try {
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      Date startDate = sdf1.parse(startTime);
      Date endDate = sdf1.parse(endTime);

      if (endDate.getTime() <= startDate.getTime()) {
        return null;
      }
      long randomTS =
          startDate.getTime() + (long) ((endDate.getTime() - startDate.getTime()) * Math.random());
      Date resultDate = new Date(randomTS);
      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String resultTimeString = sdf2.format(resultDate);
      return resultTimeString;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}

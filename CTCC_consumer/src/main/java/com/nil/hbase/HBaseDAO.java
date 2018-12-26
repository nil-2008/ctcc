package com.nil.hbase;

import com.nil.utils.ConnectionInstance;
import com.nil.utils.HBaseUtil;
import com.nil.utils.PropertiesUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
/**
 * @author lianyou
 * @version 1.0
 */
public class HBaseDAO {
  private int regions;
  private String namespace;
  private String tableName;
  private static final Configuration conf;
  private HTable table;
  private Connection connection;
  private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

  private List<Put> cacheList = new ArrayList<>();

  static {
    conf = HBaseConfiguration.create();
  }

  public HBaseDAO() {
    try {
      regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.calllog.regions"));
      namespace = PropertiesUtil.getProperty("hbase.calllog.namespace");
      tableName = PropertiesUtil.getProperty("hbase.calllog.tablename");
      if (!HBaseUtil.isExistTable(conf, tableName)) {
        HBaseUtil.initNameSpace(conf, namespace);
        HBaseUtil.createTable(conf, tableName, regions, "f1", "f2");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * value格式 : 18576581848,18468618874,2018-07-02 07:30:49,0181 Rowkey格式 :
   * regionCode_caller_buildTime_callee_flag_duration
   *
   * @param value
   */
  public void put(String value) {
    try {

      // 优化代码，做批处理
      if (cacheList.size() == 0) {
        connection = ConnectionInstance.getConnection(conf);
        table = (HTable) connection.getTable(TableName.valueOf(tableName));
        table.setAutoFlushTo(false);
        table.setWriteBufferSize(2 * 1024 * 1024);
      }
      String[] splitOri = value.split(",");
      String caller = splitOri[0];
      String callee = splitOri[1];
      String buildTime = splitOri[2];
      String duration = splitOri[3];
      String regionCode = HBaseUtil.genRegionCode(caller, buildTime, regions);

      String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
      String buildTimeTS = String.valueOf(sdf1.parse(buildTime).getTime());

      // 生成rowkey
      String rowkey =
          HBaseUtil.genRowkey(regionCode, caller, buildTimeReplace, callee, "1", duration);
      // 向表中插入数据
      Put put = new Put(Bytes.toBytes(rowkey));
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("caller"), Bytes.toBytes(caller));
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("callee"), Bytes.toBytes(callee));
      put.addColumn(
          Bytes.toBytes("f1"), Bytes.toBytes("buildTimeReplace"), Bytes.toBytes(buildTimeReplace));
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("buildTimeTS"), Bytes.toBytes(buildTimeTS));
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("flag"), Bytes.toBytes("1"));
      put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("duration"), Bytes.toBytes(duration));

      cacheList.add(put);

      // 生成rowkey
      String rowkey2 =
          HBaseUtil.genRowkey(regionCode, callee, buildTimeReplace, caller, "0", duration);
      // 向表中插入数据
      Put put2 = new Put(Bytes.toBytes(rowkey2));
      put2.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("caller"), Bytes.toBytes(callee));
      put2.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("callee"), Bytes.toBytes(caller));
      put2.addColumn(
          Bytes.toBytes("f2"), Bytes.toBytes("buildTimeReplace"), Bytes.toBytes(buildTimeReplace));
      put2.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("buildTimeTS"), Bytes.toBytes(buildTimeTS));
      put2.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("flag"), Bytes.toBytes("0"));
      put2.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("duration"), Bytes.toBytes(duration));

      cacheList.add(put);
      cacheList.add(put2);
      if (cacheList.size() >= 30) {
        table.put(cacheList);
        table.flushCommits();
        table.close();
        cacheList.clear();
      }

    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

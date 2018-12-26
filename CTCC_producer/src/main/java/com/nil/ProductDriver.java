package com.nil;

/**
 * @author lianyou
 * @version 1.0
 * @date 2018/12/25 11:06
 */
public class ProductDriver {

  public static void main(String[] args) {
    // args = new String[] {"/Users/lianyou/training_data/xx.log"};
    if (args == null || args.length <= 0) {
      System.out.println("==========请输出文件输出路径==========");
      return;
    }

    ProductLog productLog = new ProductLog();
    productLog.initPhone();
    productLog.writeLog(args[0]);
  }
}

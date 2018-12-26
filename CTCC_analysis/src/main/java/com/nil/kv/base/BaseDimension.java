package com.nil.kv.base;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
 * @author lianyou
 * @version 1.0
 */
public abstract class BaseDimension implements WritableComparable<BaseDimension> {
  /**
   * 比较
   *
   * @param o
   * @return
   */
  @Override
  public abstract int compareTo(BaseDimension o);

  /**
   * 写数据
   *
   * @param out
   * @throws IOException
   */
  @Override
  public abstract void write(DataOutput out) throws IOException;

  /**
   * 读数据
   *
   * @param in
   * @throws IOException
   */
  @Override
  public abstract void readFields(DataInput in) throws IOException;
}

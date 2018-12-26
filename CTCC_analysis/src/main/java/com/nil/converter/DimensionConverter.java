package com.nil.converter;

import com.nil.kv.base.BaseDimension;
/**
 * @author lianyou
 * @version 1.0
 */
public interface DimensionConverter {
  /**
   * 基本维度定义
   *
   * @param dimension
   * @return
   */
  int getDimensionID(BaseDimension dimension);
}

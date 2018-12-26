package com.nil.utils;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author lianyou
 * @version 1.0
 */
public class LRUCache extends LinkedHashMap<String, Integer> {
  private static final long serialVersionUID = 1L;
  protected int maxElements;

  public LRUCache(int maxSize) {
    super(maxSize, 0.75F, true);
    this.maxElements = maxSize;
  }

  /**
   * (non-Javadoc)
   *
   * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
   */
  @Override
  protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
    return (size() > this.maxElements);
  }
}

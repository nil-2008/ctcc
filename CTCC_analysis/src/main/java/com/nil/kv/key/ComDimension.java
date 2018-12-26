package com.nil.kv.key;

import com.nil.kv.base.BaseDimension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/**
 * @author lianyou
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ComDimension extends BaseDimension {
  /** 注:不要用抽象类或者接口当做类型来使用 */
  private ContactDimension contactDimension = new ContactDimension();

  private DateDimension dateDimension = new DateDimension();

  @Override
  public int compareTo(BaseDimension o) {
    ComDimension anotherComDimension = (ComDimension) o;
    // 先年月日
    int result = this.dateDimension.compareTo(anotherComDimension.dateDimension);
    if (result != 0) return result;
    // 比较电话号
    result = this.contactDimension.compareTo(anotherComDimension.contactDimension);
    return result;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    contactDimension.write(out);
    dateDimension.write(out);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    contactDimension.readFields(in);
    dateDimension.readFields(in);
  }
}

package com.nil.reducer;

import com.nil.kv.key.ComDimension;
import com.nil.kv.value.CountDurationValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
/**
 * @author lianyou
 * @version 1.0
 */
public class CountDurationReducer
    extends Reducer<ComDimension, Text, ComDimension, CountDurationValue> {
  CountDurationValue countDurationValue = new CountDurationValue();

  @Override
  protected void reduce(ComDimension key, Iterable<Text> values, Context context)
      throws IOException, InterruptedException {
    int callSum = 0;
    int callDurationSum = 0;
    for (Text t : values) {
      callSum++;
      callDurationSum += Integer.valueOf(t.toString());
    }
    countDurationValue.setCallSum(String.valueOf(callSum));
    countDurationValue.setCallDurationSum(String.valueOf(callDurationSum));

    context.write(key, countDurationValue);
  }
}

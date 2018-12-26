package com.nil.kafka;

import com.nil.hbase.HBaseDAO;
import com.nil.utils.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
/**
 * @author lianyou
 * @version 1.0
 */
public class HBaseConsumer {
  public static void main(String[] args) {
    KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PropertiesUtil.properties);
    kafkaConsumer.subscribe(Arrays.asList(PropertiesUtil.getProperty("kafka.topics")));

    HBaseDAO hd = new HBaseDAO();
    while (true) {
      ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
      for (ConsumerRecord<String, String> cr : records) {
        String value = cr.value();
        // 18576581848,18468618874,2018-07-02 07:30:49,0181
        System.out.println(value);
        hd.put(value);
      }
    }
  }
}

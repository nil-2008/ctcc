package com.nil.controller;

import com.nil.bean.CallLog;
import com.nil.bean.QueryInfo;
import com.nil.dao.CallLogDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
/**
 * @author lianyou
 * @version 1.0
 */
@Controller
public class CallLogHandler {

  @RequestMapping("/queryCallLogList")
  public String queryCallLog(Model model, QueryInfo queryInfo) {
    ApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("applicationContext.xml");
    CallLogDAO callLogDAO = applicationContext.getBean(CallLogDAO.class);

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("telephone", queryInfo.getTelephone());
    hashMap.put("year", queryInfo.getYear());
    hashMap.put("month", queryInfo.getMonth());
    hashMap.put("day", queryInfo.getDay());

    List<CallLog> list = callLogDAO.getCallLogList(hashMap);

    StringBuilder dateSB = new StringBuilder();
    StringBuilder callSumSB = new StringBuilder();
    StringBuilder callDurationSumSB = new StringBuilder();

    for (int i = 0; i < list.size(); i++) {
      CallLog callLog = list.get(i);
      // 1月, 2月, ....12月,
      dateSB.append(callLog.getMonth() + "月,");
      callSumSB.append(callLog.getCall_sum() + ",");
      callDurationSumSB.append(callLog.getCall_duration_sum() + ",");
    }

    dateSB.deleteCharAt(dateSB.length() - 1);
    callSumSB.deleteCharAt(callSumSB.length() - 1);
    callDurationSumSB.deleteCharAt(callDurationSumSB.length() - 1);

    // 通过model返回数据
    model.addAttribute("telephone", list.get(0).getTelephone());
    model.addAttribute("name", list.get(0).getName());
    model.addAttribute("date", dateSB.toString());
    model.addAttribute("count", callSumSB.toString());
    model.addAttribute("duration", callDurationSumSB.toString());

    return "jsp/CallLogListEchart";
  }
}

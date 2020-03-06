package com.baizhi.wsx.evaluate.impl;

import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.EvaluateReport;
import com.baizhi.wsx.entity.Evaluateitem;
import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.evaluate.EvaluateChain;
import com.baizhi.wsx.evaluate.EvaluateOperator;

import java.util.Set;


/*  基于用户登录设备信息评估风险 */
public class LoginDeviceEvaluate implements EvaluateOperator {
    @Override
    public void invoke(EvaluateData evaluateData, HistoryData historyData, EvaluateReport evaluateReport, EvaluateChain chain) {
        Boolean value = evaluateLoginDevice(evaluateData.getUserAgent(), historyData.getHistoryDevices());

        Evaluateitem item = new Evaluateitem();
        item.setComponentName("device");
        item.setValue(value);

        evaluateReport.getItems().add(item);

        chain.doEvaluate(evaluateData,historyData,evaluateReport);

    }
    /**
     * 登录设备发生变化
     */
    public Boolean evaluateLoginDevice(String currentDevice, Set<String> historyDevices) {
        // 新用户 无历史的登录设备信息
        if (historyDevices == null || historyDevices.size() == 0) {
            return false;
        }
        //
        return !historyDevices.contains(currentDevice);
    }

}

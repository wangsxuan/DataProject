package com.baizhi.wsx.evaluate.impl;

import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.EvaluateReport;
import com.baizhi.wsx.entity.Evaluateitem;
import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.evaluate.EvaluateChain;
import com.baizhi.wsx.evaluate.EvaluateOperator;

import java.util.Set;

/*  基于用户登录城市信息 评估*/
public class LoginCitiesEvaluate implements EvaluateOperator {
    @Override
    public void invoke(EvaluateData evaluateData, HistoryData historyData, EvaluateReport evaluateReport, EvaluateChain chain) {
        Boolean value = evaluateLoginCity(evaluateData.getRegion(), historyData.getHistoryCities());
        /*
        * 评估结果的封装
        * */

        Evaluateitem item = new Evaluateitem();

        item.setComponentName("region");
        item.setValue(value);

        evaluateReport.getItems().add(item);

        chain.doEvaluate(evaluateData,historyData,evaluateReport);



    }
    public Boolean evaluateLoginCity(String currentCity, Set<String> historyCities) {
        /* 首次登录*/
        if (historyCities == null || historyCities.size() == 0) {
            return false;
        }
        // true 常用城市登录
        // false 头一次出现
        return !historyCities.contains(currentCity);
    }
}

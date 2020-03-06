package com.baizhi.wsx.evaluate.impl;

import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.EvaluateReport;
import com.baizhi.wsx.entity.Evaluateitem;
import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.evaluate.EvaluateChain;
import com.baizhi.wsx.evaluate.EvaluateOperator;


/*  评估用户频繁登录的风险 */
public class LoginCountEvaluate implements EvaluateOperator {
    private Integer threshold;

    public LoginCountEvaluate(Integer threshold) {
        this.threshold = threshold;
    }

    @Override
    public void invoke(EvaluateData evaluateData, HistoryData historyData, EvaluateReport evaluateReport, EvaluateChain chain) {

        /* 无论用户是否登录成功 ，都需要做登录次数的风险评估*/
        Integer dayCounts = historyData.getCurrentDayCounts();
        if (dayCounts == null){
            dayCounts = 0;
        }


        Boolean value = evaluateLoginCount(dayCounts + 1, threshold);

        Evaluateitem item = new Evaluateitem();

        item.setComponentName("count");
        item.setValue(value);

        evaluateReport.getItems().add(item);
        /*  调用 链用方法*/
        chain.doEvaluate(evaluateData,historyData,evaluateReport);

    }

    /**
     * 频繁登录（登录次数限定）
     * map(day,count)
     */
    public Boolean evaluateLoginCount(Integer currentDayCounts, Integer threshold) {
        if (currentDayCounts == null || currentDayCounts == 0) {
            return false;
        }
        // 实际登录次数大于要求阈值  风险
        return currentDayCounts + 1 > threshold;
    }
}

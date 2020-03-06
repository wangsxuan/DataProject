package com.baizhi.wsx.evaluate;


import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.EvaluateReport;
import com.baizhi.wsx.entity.HistoryData;

import java.util.List;

/* 评估链路对象 */
public class EvaluateChain {
    private List<EvaluateOperator> operator;
    private int position;

    public EvaluateChain(List<EvaluateOperator> operator) {
        this.operator = operator;

        position = 0;
    }

    public void doEvaluate(EvaluateData evaluateData, HistoryData historyData, EvaluateReport evaluateReport){


        if (operator != null && position <operator.size()){
            EvaluateOperator operator = this.operator.get(position);
            position ++;
            operator.invoke(evaluateData,historyData,evaluateReport,this);
        }else {
            evaluateReport.setCurrentTime(evaluateData.getCurrentTime());
            evaluateReport.setApplicationId(evaluateData.getApplicationId());
            evaluateReport.setLoginSpquence(evaluateData.getLoginSequence());
            evaluateReport.setUserId(evaluateData.getUserId());
            evaluateReport.setRegion(evaluateData.getRegion());
        }
    }

}

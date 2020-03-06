package com.baizhi.wsx.evaluate;

import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.EvaluateReport;
import com.baizhi.wsx.entity.HistoryData;

public interface EvaluateOperator {

    /**
     *
     * @param evaluateData  当前的数据
     * @param historyData   历史的数据
     * @param evaluateReport 评估报告的实体类
     * @param chain         评估的数据
     */
    public void invoke(EvaluateData evaluateData, HistoryData historyData,EvaluateReport evaluateReport, EvaluateChain chain);
}

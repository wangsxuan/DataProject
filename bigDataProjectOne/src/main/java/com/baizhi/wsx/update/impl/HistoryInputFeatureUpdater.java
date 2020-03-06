package com.baizhi.wsx.update.impl;

import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.entity.LoginSuccessData;
import com.baizhi.wsx.update.UpdateOperator;
import com.baizhi.wsx.update.UpdaterChain;

import java.util.ArrayList;
import java.util.List;


/* 更新用户登录特征  &&  保留用户10条最新的输入特征 */
public class HistoryInputFeatureUpdater implements UpdateOperator {

    @Override
    public void invoke(LoginSuccessData loginSuccessData, HistoryData historyData, UpdaterChain chain) {

        List<Double[]> historyvectors = historyData.getHistoryvectors();

        if (historyvectors ==  null){
            historyvectors = new ArrayList<>();
        }
        if (historyvectors.size() >10 ){
            historyvectors.remove(0);
        }
        historyvectors.add(loginSuccessData.getInputFeature());
        historyData.setHistoryvectors(historyvectors);
        chain.doUpdate(loginSuccessData,historyData);

    }
}

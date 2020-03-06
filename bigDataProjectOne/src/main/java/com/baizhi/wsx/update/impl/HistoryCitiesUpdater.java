package com.baizhi.wsx.update.impl;

import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.entity.LoginSuccessData;
import com.baizhi.wsx.update.UpdateOperator;
import com.baizhi.wsx.update.UpdaterChain;

import java.util.HashSet;
import java.util.Set;


/*  更新用户的历史登录城市  */
public class HistoryCitiesUpdater implements UpdateOperator {

    @Override
    public void invoke(LoginSuccessData loginSuccessData, HistoryData historyData, UpdaterChain chain) {

        Set<String> historyCities = historyData.getHistoryCities();
        if (historyCities == null ){
            historyCities = new HashSet<>();
        }
        historyCities.add(loginSuccessData.getRegion());
        historyData.setHistoryCities(historyCities);

        /* 调用链路下一个更新器*/

        chain.doUpdate(loginSuccessData,historyData);
    }
}

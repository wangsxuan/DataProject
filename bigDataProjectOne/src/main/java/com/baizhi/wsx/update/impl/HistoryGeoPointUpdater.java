package com.baizhi.wsx.update.impl;

import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.entity.LoginSuccessData;
import com.baizhi.wsx.update.UpdateOperator;
import com.baizhi.wsx.update.UpdaterChain;


/*  更新用户历史数据中的上一次的登录坐标和登录时间 */
public class HistoryGeoPointUpdater implements UpdateOperator {
    @Override
    public void invoke(LoginSuccessData loginSuccessData, HistoryData historyData, UpdaterChain chain) {
        historyData.setbPoint(loginSuccessData.getGeoPoint());
        historyData.setLastLoginTime(loginSuccessData.getCurrentTime());
        chain.doUpdate(loginSuccessData,historyData);
    }
}

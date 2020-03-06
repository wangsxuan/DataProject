package com.baizhi.wsx.update.impl;

import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.entity.LoginSuccessData;
import com.baizhi.wsx.update.UpdateOperator;
import com.baizhi.wsx.update.UpdaterChain;

import java.util.HashSet;
import java.util.Set;


/*   更新历史数据的乱序明文密码列表 */
public class HistoryPasswordUpdater implements UpdateOperator {

    @Override
    public void invoke(LoginSuccessData loginSuccessData, HistoryData historyData, UpdaterChain chain) {


        Set<String> historyPassowrds = historyData.getHistoryPassowrds();



        if (historyPassowrds == null){
            historyPassowrds = new HashSet<>();
        }





        historyPassowrds.add(loginSuccessData.getOrderlessPassword());
        historyData.setHistoryPassowrds(historyPassowrds);
         chain.doUpdate(loginSuccessData,historyData);

    }
}

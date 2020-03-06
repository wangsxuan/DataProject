package com.baizhi.wsx.update;

import com.baizhi.wsx.entity.HistoryData;
import com.baizhi.wsx.entity.LoginSuccessData;

public interface UpdateOperator {
    public void invoke(LoginSuccessData loginSuccessData, HistoryData historyData, UpdaterChain chain);
}

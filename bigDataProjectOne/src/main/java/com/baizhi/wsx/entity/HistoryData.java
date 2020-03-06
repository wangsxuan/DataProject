package com.baizhi.wsx.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*  用户历史集 \\44444444440.........................*/
public class HistoryData implements Serializable {
    /*历史登录城市列表*/
    private Set<String> historyCities;
    /* 上一次的登录时间 */
    private Long lastLoginTime;
    /* 上一次登录的坐标 */
    private Double[] bPoint;
    /* 用户登录次数 */
    private Integer currentDayCounts;
    /* 用户历史的登录设备列表 */
    private Set<String> historyDevices;
    /* 用户历史的登录明文乱序密码列表 */
    private Set<String> historyPassowrds;
    /* 用户历史的登录习惯 */
    private Map<String, Map<String,Long>> historyHabit;
    /* 用户历史的输入特征的向量列表 */
    private List<Double[]> historyvectors;

    public Set<String> getHistoryCities() {
        return historyCities;
    }

    public void setHistoryCities(Set<String> historyCities) {
        this.historyCities = historyCities;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Double[] getbPoint() {
        return bPoint;
    }

    public void setbPoint(Double[] bPoint) {
        this.bPoint = bPoint;
    }

    public Integer getCurrentDayCounts() {
        return currentDayCounts;
    }

    public void setCurrentDayCounts(Integer currentDayCounts) {
        this.currentDayCounts = currentDayCounts;
    }

    public Set<String> getHistoryDevices() {
        return historyDevices;
    }

    public void setHistoryDevices(Set<String> historyDevices) {
        this.historyDevices = historyDevices;
    }

    public Set<String> getHistoryPassowrds() {
        return historyPassowrds;
    }

    public void setHistoryPassowrds(Set<String> historyPassowrds) {
        this.historyPassowrds = historyPassowrds;
    }

    public Map<String, Map<String, Long>> getHistoryHabit() {
        return historyHabit;
    }

    public void setHistoryHabit(Map<String, Map<String, Long>> historyHabit) {
        this.historyHabit = historyHabit;
    }

    public List<Double[]> getHistoryvectors() {
        return historyvectors;
    }

    public void setHistoryvectors(List<Double[]> historyvectors) {
        this.historyvectors = historyvectors;
    }

    @Override
    public String toString() {
        return "HistoryDate{" +
                "historyCities=" + historyCities +
                ", lastLoginTime=" + lastLoginTime +
                ", bPoint=" + Arrays.toString(bPoint) +
                ", currentDayCounts=" + currentDayCounts +
                ", historyDevices=" + historyDevices +
                ", historyPassowrds=" + historyPassowrds +
                ", historyHabit=" + historyHabit +
                ", historyvectors=" + historyvectors +
                '}';
    }
}

package com.baizhi.wsx.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EvaluateReport implements Serializable {
    private Long currentTime;
    private String applicationId;
    private String userId;
    private String loginSpquence;
    private String region;


    /**
     * Evaluateitem  评估项
     */
    private List<Evaluateitem> items = new ArrayList<>();

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginSpquence() {
        return loginSpquence;
    }

    public void setLoginSpquence(String loginSpquence) {
        this.loginSpquence = loginSpquence;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Evaluateitem> getItems() {
        return items;
    }

    public void setItems(List<Evaluateitem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "EvaluateReport{" +
                "currentTime=" + currentTime +
                ", applicationId='" + applicationId + '\'' +
                ", userId='" + userId + '\'' +
                ", loginSpquence='" + loginSpquence + '\'' +
                ", region='" + region + '\'' +
                ", items=" + items +
                '}';
    }
}

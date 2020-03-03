package com.baizhi.wsx.entity;

import sun.plugin2.message.Serializer;

import java.io.Serializable;
import java.util.Arrays;

public class LoginSuccessData implements Serializable {
    private Long currentTime;
    private String applicationId;
    private String userId;
    private String loginSequence;
    // 乱序的明文密码
    private String orderlessPassword;
    private String region;
    private Double[] geoPoint;
    private Double[] inputFeature;
    private String userAgent;

    public LoginSuccessData() {
    }

    public LoginSuccessData(Long currentTime, String applicationId, String userId, String loginSequence, String orderlessPassword, String region, Double[] geoPoint, Double[] inputFeature, String userAgent) {
        this.currentTime = currentTime;
        this.applicationId = applicationId;
        this.userId = userId;
        this.loginSequence = loginSequence;
        this.orderlessPassword = orderlessPassword;
        this.region = region;
        this.geoPoint = geoPoint;
        this.inputFeature = inputFeature;
        this.userAgent = userAgent;
    }

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

    public String getLoginSequence() {
        return loginSequence;
    }

    public void setLoginSequence(String loginSequence) {
        this.loginSequence = loginSequence;
    }

    public String getOrderlessPassword() {
        return orderlessPassword;
    }

    public void setOrderlessPassword(String orderlessPassword) {
        this.orderlessPassword = orderlessPassword;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double[] getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(Double[] geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Double[] getInputFeature() {
        return inputFeature;
    }

    public void setInputFeature(Double[] inputFeature) {
        this.inputFeature = inputFeature;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "LoginSuccessData{" +
                "currentTime=" + currentTime +
                ", applicationId='" + applicationId + '\'' +
                ", userId='" + userId + '\'' +
                ", loginSequence='" + loginSequence + '\'' +
                ", orderlessPassword='" + orderlessPassword + '\'' +
                ", region='" + region + '\'' +
                ", geoPoint=" + Arrays.toString(geoPoint) +
                ", inputFeature=" + Arrays.toString(inputFeature) +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}

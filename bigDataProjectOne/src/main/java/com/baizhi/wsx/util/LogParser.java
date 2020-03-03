package com.baizhi.wsx.util;

import com.baizhi.wsx.entity.EvaluateData;
import com.baizhi.wsx.entity.LoginSuccessData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

    // 匹配提取 评估数据的正则表达式
    final static Pattern EVAL_PATTERN = Pattern.compile("^INFO\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s(.*)\\sEVALUATE\\s\\[(.+)\\]\\s([a-z0-9]{32})\\s\"(.*)\"\\s([a-z]+)\\s\"([0-9\\.,]+)\"\\s\\[([0-9\\.,]+)\\]\\s\"(.*)\"");

    final static Pattern SUCCESS_PATTERN = Pattern.compile("^INFO\\s(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})\\s(.*)\\sSUCCESS\\s\\[(.+)\\]\\s([a-z0-9]{32})\\s\"(.*)\"\\s([a-z]+)\\s\"([0-9\\.,]+)\"\\s\\[([0-9\\.,]+)\\]\\s\"(.*)\"");


    /**
     * 解析登录成功后的日志数据
     * @param loginSuccessLog
     * @return
     */
    public static LoginSuccessData parserLoginSuccessData(String loginSuccessLog) throws ParseException {
        Matcher matcher = SUCCESS_PATTERN.matcher(loginSuccessLog);
        /*  提取数据
        * */

        //时间格式转换
        long currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(matcher.group(1)).getTime();
        String applicationId = matcher.group(2);
        String userId = matcher.group(3);
        String loginSequence = matcher.group(4);
        // 乱序的明文密码
        String orderlessPassword = matcher.group(5);
        String region = matcher.group(6);
        String[] arrAddress = matcher.group(7).split(",");
        Double[] geoPoint = {Double.parseDouble(arrAddress[0]), Double.parseDouble(arrAddress[1])};
        String[] arrInputFeature = matcher.group(8).split(",");
        Double[] inputFeature = new Double[arrInputFeature.length];
        for (int i = 0; i < arrInputFeature.length; i++) {
            inputFeature[i] = Double.parseDouble(arrInputFeature[i]);
        }
        String userAgent = matcher.group(9);
        return new LoginSuccessData(currentTime, applicationId, userId, loginSequence, orderlessPassword, region, geoPoint, inputFeature, userAgent);
    }

    public static EvaluateData parserEvaluateData(String evaluateLog) throws ParseException {
        Matcher matcher = EVAL_PATTERN.matcher(evaluateLog);
        // 提取数据
        // long
        long currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(matcher.group(1)).getTime();
        String applicationId = matcher.group(2);
        String userId = matcher.group(3);
        String loginSequence = matcher.group(4);
        // 乱序的明文密码
        String orderlessPassword = matcher.group(5);
        String region = matcher.group(6);
        String[] arrAddress = matcher.group(7).split(",");
        Double[] geoPoint = {Double.parseDouble(arrAddress[0]), Double.parseDouble(arrAddress[1])};

        String[] arrInputFeature = matcher.group(8).split(",");
        Double[] inputFeature = new Double[arrInputFeature.length];
        for (int i = 0; i < arrInputFeature.length; i++) {
            inputFeature[i] = Double.parseDouble(arrInputFeature[i]);
        }
        String userAgent = matcher.group(9);
        return new EvaluateData(currentTime, applicationId, userId, loginSequence, orderlessPassword, region, geoPoint, inputFeature, userAgent);
    }
}

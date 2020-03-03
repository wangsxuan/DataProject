package com.baizhi.wsx;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评估模型的测试类
 */
public class EvaluateModelTests {
    /**
     *
     * 基于用户登录城市信息
     * @param currentCity  当前城市
     * @param historyCities  历史登录城市
     * @return  有风险为---true  没有风险----false
     */
    public Boolean evaluateLoginCity(String currentCity, Set<String> historyCities){
        /*首次登录*/
        if (historyCities == null || historyCities.size()==0){
            return false;
        }
        return !historyCities.contains(currentCity);
    }

    /**
     * 基于用户位移距离的风险判定
     *
     * @param currentLoginTime 当前登录时间
     * @param aPoint  当前登录位置坐标
     * @param lastLoginTime  历史登录时间
     * @param bPoint  上一次登录位置坐标
     * @param speedThreshold
     * @return
     */
    public Boolean evaluateLoginAddress(Long currentLoginTime,Double[] aPoint,long lastLoginTime,Double[] bPoint,Double speedThreshold){
        /* AB 两点弧长距离计算公式
         * R*arccos(cos(wA)*cos(wB)*cos(jB-jA)+sin(wB)*sin(wA))
         * 6371
         *
         * wA  wB  jB  jA
         * */

        // nπR/180  角度 ---> 弧度

        Double wA = aPoint[1] * Math.PI / 180;
        Double wB = bPoint[1] * Math.PI / 180;
        Double jA = aPoint[0] * Math.PI / 180;
        Double jB = aPoint[0] * Math.PI / 180;

        Double distance = 6371 * Math.acos(Math.cos(wA)*Math.cos(wB)*Math.cos(jB-jA)+Math.sin(wB)*Math.sin(wA));

        Double totalTime = distance /speedThreshold;
        System.out.println("A 点到B 点的距离："+distance +"\t 理论位移时长： "+totalTime);

        /*理论时间  >  实际时间*/

        if ((totalTime * 360 *1000) > (currentLoginTime - lastLoginTime)){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 基于用户频繁登录判定
     * */
    public Boolean evaluateLoginCount(Integer currentDayCounts,Integer threshold){
        if(currentDayCounts == null || currentDayCounts == 0){
            return false;
        }

        /*实际登录次数大于要求阈值   有风险*/
        return currentDayCounts +1 > threshold;
    }


    /*
     * 登录设备发生变化*/
    public Boolean evaluateLoginDevice(String currentDevice,Set<String> historyDevices){
        /*新用户 无历史的登录设备信息*/
        if (historyDevices == null || historyDevices.size() == 0){
            return false;
        }
        return !historyDevices.contains(currentDevice);
    }

    /*
     * 基于用户登录习惯发生变化*/

    public Boolean evaluateLoginHabit(long currentTime, Map<String, Map<String,Long>> historyHabit,Integer threshold){
        /* 1  没有任何的历史的登录习惯数据*/

        if (historyHabit == null || historyHabit.size() == 0){
            return false;
        }

        /*习惯数据的比对判断
         * 从当前登录时间中提取周几和用户的登录时段
         * */
        String weeks[] = {"周日","周一","周二","周三","周四","周五","周六"};

        /*日历对象*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);

        /*从日历对象中提取出周几信息，西方每一周第一天从周天开始*/
        int dayOfWeek = calendar.get(7);
        /*周一*/
        String strDaoOfWeek = weeks[dayOfWeek - 1];

        /*从日历对象中提取时间段信息*/

        String hourOfDay = calendar.get(11) + "";

        /*如：在当前时间 未出现登录*/
        if (!historyHabit.containsKey(strDaoOfWeek)){
            Long num = historyHabit
                    .entrySet()
                    .stream()
                    .map(kv -> kv.getValue()
                            .entrySet()
                            .stream()
                            .map(kv2 -> kv2.getValue())
                            .reduce((v1,v2) -> v1 + v2)
                            .get())
                    .reduce((v1,v2) -> v1 + v2)
                    .get();
            return num > threshold;
        }

        /*判定当前的登录时间是否在时段内*/

        Map<String,Long> map = historyHabit.get(strDaoOfWeek);

        /*如：当前登录时段为17 而历史习惯数据中只有12 和 20 时段结果*/

        if (!map.containsKey(hourOfDay)){
            return true;
        }else {
            /* 计算出用户的习惯数据和偶尔事件数据*/

            List<Long> sortedList = map
                    .entrySet()
                    .stream()
                    .map(kv -> kv.getValue())
                    .sorted()
                    .collect(Collectors.toList());

            /*假如计算结果为5 判定时段登录次数大于5  认定为是习惯数据
             *              判定时间登录次数小于5  认定为是偶然数据
             *
             * */

            int throesholeCount = sortedList.size() * 3 / 4;
            /*
             * 计算阈值
             * 假如计算结果为5，[习惯数据时段集合]
             *
             * */
            List<String> hourList = map
                    .entrySet()
                    .stream()
                    .filter( kv -> kv.getValue() >= throesholeCount)
                    .map(kv -> kv.getKey())
                    .collect(Collectors.toList());

            System.out.println(throesholeCount + "\t" + hourList );

            return !hourList.contains(hourOfDay);
        }

    }

    /**
     * 密码相似特征评估
     * @param currentPassword 当前登录密码
     * @param historyPasswords 用户的历史密码的集合
     * @param threshold   安全阈值
     * @return
     */

    public Boolean evaluateLoginPasswordSimilarity(String currentPassword,Set<String> historyPasswords,Double threshold){
        /*用户第一次登录，没有历史的登录记录*/
        if (historyPasswords == null || historyPasswords.size() == 0){
            return false;
        }

        /*建立词袋子模型（历史密码 + 当前密码）*/

        Set<Character> bagOfWords = new HashSet<>();
        char[] currentPasswordCharArray = currentPassword.toCharArray();

        for (int i = 0; i < currentPasswordCharArray.length; i++) {
            bagOfWords.add(currentPasswordCharArray[i]);
        }

        for (String historyPassword: historyPasswords) {
            char[] historyPasswordCharArray = historyPassword.toCharArray();
            for (int i = 0; i < historyPasswordCharArray.length; i++) {
                bagOfWords.add(historyPasswordCharArray[i]);
            }
        }

        /*建立最终的词袋子模型*/
        List<Character> bagOfWordsmodel = bagOfWords
                .stream()
                .sorted()
                .collect(Collectors.toList());
        bagOfWordsmodel
                .stream()
                .forEach(n -> System.out.print(n +"\t"));
        System.out.println();

        /*
         * 建立当前登录密码向量模型
         * map 记录 当前密码中字符的出现次数
         * */
        HashMap<Character,Integer> charMap = new HashMap<>();
        for (int i = 0; i < currentPasswordCharArray.length; i++) {
            char c = currentPasswordCharArray[i];
            if (charMap.containsKey(c)){
                charMap.put(c,charMap.get(c+1));
            }else {

                charMap.put(c,1);
            }
        }

        /*向量的纬度等价于词袋子长度*/

        Integer[] currentPasswordVactor = new Integer[bagOfWordsmodel.size()];
        for (int i = 0; i < bagOfWordsmodel.size(); i++) {
            if (charMap.containsKey(bagOfWordsmodel.get(i))){
                currentPasswordVactor[i] = charMap.get(bagOfWordsmodel.get(i));
            }else {
                currentPasswordVactor[i] = 0;
            }
        }
        Arrays.stream(currentPasswordVactor)
                .forEach(n -> System.out.println(n+"\t"));

        /*建立历史登录密码的向量*/

        ArrayList<Integer[]> historyPasswordVectors = new ArrayList<>();
        for (String historyPassword : historyPasswords){
            HashMap<Character,Integer> tempCharMap = new HashMap<>();
            char[] historyPasswordCharArray = historyPassword.toCharArray();
            for (int i = 0; i < historyPasswordCharArray.length; i++) {
                char c = historyPasswordCharArray[i];
                if (tempCharMap.containsKey(c)){
                    tempCharMap.put(c,tempCharMap.get(c) + 1);
                }else {
                    tempCharMap.put(c,1);
                }
            }
            /* 向量的纬度等价于词袋子长度*/

            Integer[] historyPasswordVector = new Integer[bagOfWordsmodel.size()];

            for (int i = 0 ; i < bagOfWordsmodel.size(); i++){
                if (tempCharMap.containsKey(bagOfWordsmodel.get(i))){
                    historyPasswordVector[i] = tempCharMap.get(bagOfWordsmodel.get(i));
                }else {
                    historyPasswordVector[i] = 0;
                }
            }
            historyPasswordVectors.add(historyPasswordVector);
        }

        /*求 向量 夹角*/

        for (Integer[] historyPasswordVector : historyPasswordVectors){
            Double fz = 0.0 ;
            for (int i = 0; i < historyPasswordVector.length; i++){
                fz += historyPasswordVector[i] * currentPasswordVactor[i];
            }

            Double fm = Math.sqrt(Arrays.stream(currentPasswordVactor)
                    .map(n -> n * n)
                    .reduce((v1,v2) -> v1 + v2)
                    .get());
            System.out.println("相似度：" + fz / fm);
            if ((fz / fm) > threshold){
                return false;
            }
        }
        return true;
    }


    /**
     * 基于用户输入特征的相似度的判定
     * @param currentVector  当前输入的特征
     * @param historyVectors  历史输入的特征
     * @return
     */
    public Boolean evaluateInputFeatures(Double[] currentVector,List<Double[]> historyVectors){
        if (historyVectors == null || historyVectors.size() == 0){
            return false;
        }

        /* 1. 首先计算出历史的输入特征向量的中心点
        *
        * 相同纬度的值相加 / 长度 = 中心点纬度值
        *
        * */

        Double[] sumVector = new Double[currentVector.length];
        for (Double[] historyVector : historyVectors) {
            for (int i = 0; i < historyVector.length; i++) {
                if (sumVector[i] == null) {
                    sumVector[i] = 0.0;
                }
                sumVector[i] += historyVector[i];
            }
        }

        Double[] centerPoint = new Double[currentVector.length];
        for (int i = 0; i < centerPoint.length; i++) {
            centerPoint[i] = sumVector[i] / historyVectors.size();
        }
        Arrays.stream(centerPoint).forEach(n -> System.out.println(n +" "));

        /* 2.  求出历史登录特征两两之间的距离
        *     等差数列  n*(n-1)/2 *1/3
        * */

        List<Double> distances = new ArrayList<>();

        for (int i = 0; i < historyVectors.size(); i++) {
            Double[] p1 = historyVectors.get(i);
            for (int j = 0; j < historyVectors.size(); j++) {
                Double[] p2 = historyVectors.get(j);
                /*
                *
                * 求俩点之间的距离
                *
                * */

                Double sum = 0.0;
                for (int k = 0; k < p1.length; k++) {
                    sum += Math.pow(p1[k] - p2[k],2);
                }
                Double distance = Math.sqrt(sum);
                distances.add(distance);
            }
        }
        /*打印出俩点之间的距离*/
        System.out.println( distances);


        /* 3.  对历史行为特征距离集合的样本数据，升序排列，取 1/3 位置的结果作为判定 阈值*/

        distances = distances.stream().sorted().collect(Collectors.toList());

        /*计算安全阈值*/

        Double threshold = distances.get(historyVectors.size() * (historyVectors.size() - 1 / 6));


        /* 4. 当前输入点和中心点距离*/

        Double sum = 0.0;

        for (int i = 0; i < currentVector.length; i++) {
            sum += Math.pow(currentVector[i] - centerPoint[i],2);
        }

        System.out.println("当前输入点和中心点距离："+ Math.sqrt(sum) +" 安全阈值："+threshold);

        return Math.sqrt(sum) > threshold;

    }












}

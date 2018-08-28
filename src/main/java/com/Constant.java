package com;

import com.jacob.activeX.ActiveXComponent;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 15:09
 */
public class Constant {

    public static  final int version = 1;

    static ActiveXComponent activeDm ;

    static long currentPid = 0;

    public static ActiveXComponent getDm() {
        if(null == activeDm){
            activeDm = new ActiveXComponent("dm.dmsoft");
            return activeDm;
        }
        else{
            return activeDm;
        }
    }

    public static long getCurrentPid() {
        return currentPid;
    }

    public static void setCurrentPid(long currentPid) {
        Constant.currentPid = currentPid;
    }
}

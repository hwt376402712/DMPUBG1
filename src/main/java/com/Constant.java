package com;

import com.jacob.activeX.ActiveXComponent;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 15:09
 */
public class Constant {

    public static final int version = 3;

    static ActiveXComponent activeDm;

    static Long currentPid = null;

    public static ActiveXComponent getDm() {
        if (null == activeDm) {
            activeDm = new ActiveXComponent("dm.dmsoft");
            return activeDm;
        } else {
            return activeDm;
        }
    }

    public static Long getCurrentPid() {
        return currentPid;
    }

    public static void setCurrentPid(Long currentPid) {
        Constant.currentPid = currentPid;
    }
}

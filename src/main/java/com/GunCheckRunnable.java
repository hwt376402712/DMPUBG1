package com;

import com.gun.*;


/**
 * @Author: huangwentao
 * @Date: 2018/8/16 17:44
 */
public class GunCheckRunnable  {

    private int gunIndex;

    private String gunName;

    public int getGunIndex() {
        return gunIndex;
    }

    public void setGunIndex(int gunIndex) {
        this.gunIndex = gunIndex;
    }



    public GunCheckRunnable(int gunIndex, String gunName) {

        this.gunIndex = gunIndex;
        this.gunName = gunName;

    }

    public void checkPeijian(){
        switch (gunName) {
            case GunConstantName.M416:
                // 检查M4配件
                new M4Gun(gunIndex);
                break;

            case GunConstantName.SCAR:
                // 检查scar配件
                new ScarGun(gunIndex);
                break;
            case GunConstantName.QBZ:
                // 检查scar配件
                new QbzGun(gunIndex);
                break;
            case GunConstantName.AKM:
                // 检查scar配件
                new AKgun(gunIndex);
                break;
            case GunConstantName.VECTOR:
                // 检查scar配件
                new VectorGun(gunIndex);
                break;


        }
    }
}

package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:47
 */
public class S12KGun {

    public long jimiaoFps =0;


    public S12KGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();

        new GunFpsCalculation(gunIndex, jimiaoFps,"S12K", miaoJinglist,headlist,wolist,taillist).start();
    }

}

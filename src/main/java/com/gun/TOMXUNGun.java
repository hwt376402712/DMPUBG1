package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:50
 */
public class TOMXUNGun {

    public long jimiaoFps = 6;


    public TOMXUNGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();



        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"TOMXUN", miaoJinglist, headlist, wolist, taillist);
    }

}

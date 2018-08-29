package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:49
 */
public class UZIGun {

    public long jimiaoFps = 5;


    public UZIGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();

        headlist.add(GunFpsEnum.CFQBCQ);
        headlist.add(GunFpsEnum.CFQXYAN);
        headlist.add(GunFpsEnum.CFQXYIN);

        new GunFpsCalculation(gunIndex, jimiaoFps,"UZI", miaoJinglist, headlist, wolist, taillist).start();
    }

}

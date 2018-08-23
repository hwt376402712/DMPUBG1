package com.gun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/17 11:59
 */
public class ScarGun {

    public long jimiaoFps = 8;


    public ScarGun(int index) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();
        miaoJinglist.add(GunFpsEnum.HD);
        miaoJinglist.add(GunFpsEnum.QUANXI);
        miaoJinglist.add(GunFpsEnum.EB);
        miaoJinglist.add(GunFpsEnum.SIBEI);

        headlist.add(GunFpsEnum.BQBCQ);
        headlist.add(GunFpsEnum.BQXYAN);
        headlist.add(GunFpsEnum.BQXYIN);

        wolist.add(GunFpsEnum.ZJWB);
        wolist.add(GunFpsEnum.CZWB);

        wolist.add(GunFpsEnum.BJSWB);
        new GunFpsCalculation(index, jimiaoFps,"SCAR", miaoJinglist, headlist, wolist, taillist).start();
    }

}

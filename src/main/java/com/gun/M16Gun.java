package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:45
 */
public class M16Gun {
    public long jimiaoFps = 0;


    public M16Gun(int gunIndex) {

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

        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"M16", miaoJinglist, headlist, wolist, taillist);
    }

}

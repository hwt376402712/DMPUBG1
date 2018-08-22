package com.gun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 12:12
 */
public class M4Gun {

    public long jimiaoFps = 8;


    public M4Gun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();
        miaoJinglist.add(GunFpsEnum.HD);
        miaoJinglist.add(GunFpsEnum.QUANXI);
        miaoJinglist.add(GunFpsEnum.EB);
        miaoJinglist.add(GunFpsEnum.SIBEI);
        miaoJinglist.add(GunFpsEnum.SANB);

        headlist.add(GunFpsEnum.BQBCQ);
        headlist.add(GunFpsEnum.BQXYAN);
        headlist.add(GunFpsEnum.BQXYIN);

        wolist.add(GunFpsEnum.ZJWB);
        wolist.add(GunFpsEnum.CZWB);
        wolist.add(GunFpsEnum.BJSWB);
        wolist.add(GunFpsEnum.MZWB);

        taillist.add(GunFpsEnum.ZSQT);
        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"M416", miaoJinglist, headlist, wolist, taillist);
    }

}

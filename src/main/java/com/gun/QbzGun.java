package com.gun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QbzGun {

    public long jimiaoFps =7;

    public QbzGun(int gunIndex) {


        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();
        miaoJinglist.add(GunFpsEnum.HD);
        miaoJinglist.add(GunFpsEnum.QUANXI);
        miaoJinglist.add(GunFpsEnum.EB);
        miaoJinglist.add(GunFpsEnum.SANB);
        miaoJinglist.add(GunFpsEnum.SIBEI);

        headlist.add(GunFpsEnum.BQBCQ);
        headlist.add(GunFpsEnum.BQXYAN);
        headlist.add(GunFpsEnum.BQXYIN);

        wolist.add(GunFpsEnum.ZJWB);
        wolist.add(GunFpsEnum.CZWB);
        wolist.add(GunFpsEnum.MZWB);
        wolist.add(GunFpsEnum.BJSWB);
        new GunFpsCalculation(gunIndex, jimiaoFps,"QBZ", miaoJinglist,headlist,wolist,taillist).start();
    }

}

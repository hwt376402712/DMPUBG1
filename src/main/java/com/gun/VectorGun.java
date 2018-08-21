package com.gun;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VectorGun {

    public long jimiaoFps = 6;


    public VectorGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();
        miaoJinglist.add(GunFpsEnum.HD);
        miaoJinglist.add(GunFpsEnum.QUANXI);
        miaoJinglist.add(GunFpsEnum.EB);

        headlist.add(GunFpsEnum.CFQBCQ);
        headlist.add(GunFpsEnum.CFQXYAN);
        headlist.add(GunFpsEnum.CFQXYIN);


        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"VECTOR", miaoJinglist, headlist, wolist, taillist);
    }

}

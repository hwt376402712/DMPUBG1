package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:48
 */
public class UMP9Gun {

    public long jimiaoFps = 6;


    public UMP9Gun(int gunIndex) {

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


        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"UMP9", miaoJinglist, headlist, wolist, taillist);
    }

}
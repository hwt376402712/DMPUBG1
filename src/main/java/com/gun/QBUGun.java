package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:52
 */
public class QBUGun {

    public long jimiaoFps = 0;


    public QBUGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();
        miaoJinglist.add(GunFpsEnum.HD);
        miaoJinglist.add(GunFpsEnum.QUANXI);
        miaoJinglist.add(GunFpsEnum.EB);
        miaoJinglist.add(GunFpsEnum.SIBEI);



        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"QBU", miaoJinglist, headlist, wolist, taillist);
    }

}

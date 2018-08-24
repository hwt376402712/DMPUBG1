package com.gun;

import java.util.ArrayList;
import java.util.List;

public class SKSGun {

    public long jimiaoFps =5;


    public SKSGun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();

        new GunFpsCalculation(gunIndex, jimiaoFps,"SKSGun", miaoJinglist,headlist,wolist,taillist).start();
    }

}

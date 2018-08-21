package com.gun;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/21 15:46
 */
public class S686Gun {

    public long jimiaoFps =0;


    public S686Gun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();

        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"S686", miaoJinglist,headlist,wolist,taillist);
    }
}

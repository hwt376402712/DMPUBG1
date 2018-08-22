package com.gun;

import java.util.ArrayList;
import java.util.List;

public class WIN94Gun {

    public long jimiaoFps = 0;


    public WIN94Gun(int gunIndex) {

        List<GunFpsEnum> miaoJinglist = new ArrayList<>();
        List<GunFpsEnum> headlist = new ArrayList<>();
        List<GunFpsEnum> wolist = new ArrayList<>();
        List<GunFpsEnum> taillist = new ArrayList<>();


        GunFpsCalculation.FpsCalculation(gunIndex, jimiaoFps,"WIN94", miaoJinglist, headlist, wolist, taillist);
    }

}

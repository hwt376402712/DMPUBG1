package com;

import com.google.common.base.Joiner;
import com.gun.FileConstant;
import com.gun.GunConstantName;
import com.gun.GunFpsEnum;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

import java.io.*;
import java.util.Properties;

/**
 * @Author: huangwentao
 * @Date: 2018/8/16 18:15
 */
public class TestConstructor {

    public TestConstructor() throws IOException {
        ActiveXComponent activeDm = new ActiveXComponent("dm.dmsoft");


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Variant[] shubiao = new Variant[5];
        shubiao[0] = new Variant(394378);
        shubiao[1] = new Variant("normal");
        shubiao[2] = new Variant("dx");
        shubiao[3] = new Variant("normal");
        shubiao[4] = new Variant(0);


        long dm = activeDm.invoke("GetForegroundFocus").getInt();

//        System.out.print(dm);


//

        long i = activeDm.invoke("BindWindow", shubiao).getInt();



        Variant[] screen = new Variant[2];
        screen[0] = new Variant(1);
        screen[1] = new Variant(7);

       System.out.print(activeDm.invoke("Beep", screen));

//
//
////
//        Variant[] shubiao1 = new Variant[8];
//        shubiao1[0] = new Variant(1588);
//        shubiao1[1] = new Variant(369);
//        shubiao1[2] = new Variant(1635);
//        shubiao1[3] = new Variant(416);
//        shubiao1[4] = new Variant(new File(this.getClass().getClassLoader().getResource("HD.bmp").getPath()).getPath());
//        shubiao1[5] = new Variant("000000");
//        shubiao1[6] = new Variant(1);
//        shubiao1[7] = new Variant(1);
//
//
//        String s = activeDm.invoke("FindPicEx", shubiao1).toString();
//        System.out.print(s);
////
////
//        Variant[] ziku = new Variant[2];
//        ziku[0] = new Variant(0);
//        ziku[1] = new Variant("C:\\Users\\win 10\\Desktop\\ziku.txt");
//        activeDm.invoke("SetDict", ziku).getInt();
//        Variant[] gun1Search = new Variant[7];
//        gun1Search[0] = new Variant(1349);
//        gun1Search[1] = new Variant(116);
//        gun1Search[2] = new Variant(1511);
//        gun1Search[3] = new Variant(185);
//        gun1Search[4] = new Variant("M16");
//        gun1Search[5] = new Variant("ffffff-000000");
//        gun1Search[6] = new Variant(0.7);
////
////
//        String s = activeDm.invoke("FindStrFastEx", gun1Search).toString();
//        System.out.print(s);
//
//
//
//
//
//        Variant[] capture = new Variant[5];
//        capture[0] = new Variant(1588);
//        capture[1] = new Variant(369);
//        capture[2] = new Variant(1635);
//        capture[3] = new Variant(416);
//        capture[4] = new Variant("D:\\7777.bmp");
//
//        activeDm.invoke("Capture", capture);



    }
}

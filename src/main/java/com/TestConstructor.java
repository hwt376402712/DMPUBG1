package com;

import com.google.common.base.Joiner;
import com.gun.GunConstantName;
import com.gun.GunFpsEnum;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

import java.io.File;

/**
 * @Author: huangwentao
 * @Date: 2018/8/16 18:15
 */
public class TestConstructor {

    public TestConstructor() {
        ActiveXComponent activeDm = new ActiveXComponent("dm.dmsoft");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Variant[] shubiao = new Variant[5];
        shubiao[0] = new Variant(459498);
        shubiao[1] = new Variant("normal");
        shubiao[2] = new Variant("dx");
        shubiao[3] = new Variant("normal");
        shubiao[4] = new Variant(0);


        long dm = activeDm.invoke("GetForegroundFocus").getInt();

        System.out.print(dm);


        long i = activeDm.invoke("BindWindow", shubiao).getInt();


//
        Variant[] shubiao1 = new Variant[8];
        shubiao1[0] = new Variant(1587);
        shubiao1[1] = new Variant(150);
        shubiao1[2] = new Variant(1633);
        shubiao1[3] = new Variant(195);
        shubiao1[4] = new Variant(new File(this.getClass().getClassLoader().getResource("HD.bmp").getPath()).getPath());
        shubiao1[5] = new Variant("000000");
        shubiao1[6] = new Variant(0.5);
        shubiao1[7] = new Variant(1);


        String s = activeDm.invoke("FindPicEx", shubiao1).toString();
        System.out.print(s);

//
//        Variant[] ziku = new Variant[2];
//        ziku[0] = new Variant(0);
//        ziku[1] = new Variant(new File(this.getClass().getClassLoader().getResource("ziku.txt").getPath()).getPath());
//        activeDm.invoke("SetDict", ziku).getInt();
//        Variant[] gunSearch = new Variant[7];
//        gunSearch[0] = new Variant(1669);
//        gunSearch[1] = new Variant(30);
//        gunSearch[2] = new Variant(1750);
//        gunSearch[3] = new Variant(67);
//        gunSearch[4] = new Variant("了");
//        gunSearch[5] = new Variant("ffffff-000000");
//        gunSearch[6] = new Variant(0.5);
////
////
//        String s = activeDm.invoke("FindStrFastEx", gunSearch).toString();
//        System.out.print(s);


////=
//
       Variant[] capture = new Variant[5];
        capture[0] = new Variant(1587);
        capture[1] = new Variant(150);
        capture[2] = new Variant(1633);
        capture[3] = new Variant(195);
        capture[4] = new Variant("D:\\8888.bmp");

       activeDm.invoke("Capture", capture);


    }
}

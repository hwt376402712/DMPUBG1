package com.gun;

import com.Constant;
import com.CurrentBody;
import com.KeyboardHook;
import com.google.common.base.Joiner;
import com.jacob.com.Variant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: huangwentao
 * @Date: 2018/8/17 10:45
 */
public class GunFpsCalculation {

    static void FpsCalculation(int gunIndex, long fps, String gunProperty, List<GunFpsEnum> miaoJinglist, List<GunFpsEnum> headList, List<GunFpsEnum> wobaList, List<GunFpsEnum> tailList) {
        List<String> miaoJingPath = new ArrayList<>();
        List<String> headPath = new ArrayList<>();
        List<String> wobaPath = new ArrayList<>();
        List<String> tailPath = new ArrayList<>();
        for (GunFpsEnum param : miaoJinglist) {
            miaoJingPath.add(param.getPath());
        }
        for (GunFpsEnum param : headList) {
            headPath.add(param.getPath());
        }
        for (GunFpsEnum param : wobaList) {
            wobaPath.add(param.getPath());
        }
        for (GunFpsEnum param : tailList) {
            tailPath.add(param.getPath());
        }

        Variant[] var = new Variant[8];


        // 分4个坐标检查配件
        boolean ifCheckPeijian = false;// 至少检索一个配件
        List<String> resultCode = new ArrayList<>();
        List<GunFpsEnum> currentCheckList = null;
        for (int i = 0; i <= 3; i++) {
            if (i == 0) {
                if (gunIndex == 1) {
                    var[0] = new Variant(1587);
                    var[1] = new Variant(150);
                    var[2] = new Variant(1633);
                    var[3] = new Variant(195);

                } else {

                    var[0] = new Variant(1588);
                    var[1] = new Variant(369);
                    var[2] = new Variant(1635);
                    var[3] = new Variant(416);

                }
                var[4] = new Variant(Joiner.on("|").join(miaoJingPath));
                currentCheckList = miaoJinglist;
            } else if (i == 1) {
                if (gunIndex == 1) {
                    var[0] = new Variant(1315);
                    var[1] = new Variant(285);
                    var[2] = new Variant(1362);
                    var[3] = new Variant(334);

                } else {
                    var[0] = new Variant(1315);
                    var[1] = new Variant(499);
                    var[2] = new Variant(1362);
                    var[3] = new Variant(549);

                }
                var[4] = new Variant(Joiner.on("|").join(headPath));
                currentCheckList = headList;
            } else if (i == 2) {
                if (gunIndex == 1) {
                    var[0] = new Variant(1418);
                    var[1] = new Variant(286);
                    var[2] = new Variant(1464);
                    var[3] = new Variant(335);
                } else {
                    var[0] = new Variant(1413);
                    var[1] = new Variant(495);
                    var[2] = new Variant(1468);
                    var[3] = new Variant(549);
                }

                var[4] = new Variant(Joiner.on("|").join(wobaPath));
                currentCheckList = wobaList;
            } else if (i == 3) {
                if (gunIndex == 1) {
                    var[0] = new Variant(1418);
                    var[1] = new Variant(286);
                    var[2] = new Variant(1875);
                    var[3] = new Variant(334);
                } else {
                    var[0] = new Variant(1418);
                    var[1] = new Variant(500);
                    var[2] = new Variant(1875);
                    var[3] = new Variant(549);
                }

                var[4] = new Variant(Joiner.on("|").join(tailPath));
                currentCheckList = tailList;
            }


            var[5] = new Variant("000000");
            var[6] = new Variant(0.8);
            var[7] = new Variant(0);

            String s = Constant.getDm().invoke("FindPicEx", var).toString();

            if (null != s && !"".equals(s)) {
                ifCheckPeijian = true;
                System.out.println("gun" + gunIndex + "检查到了配件!!~~~~~~~~~~~~" + s);
                int index = Integer.parseInt(s.split("\\,")[0]);
                GunFpsEnum currentEnum = currentCheckList.get(index);

                //组装配件编码，待查询
                resultCode.add(currentEnum.getCode());

            } else {
                resultCode.add("0");
            }

        }

        if (ifCheckPeijian) {
            //匹配配件字典fps
            try {
                String code = Joiner.on("/").join(resultCode);
                Properties properties = new Properties();
                InputStream in = GunFpsCalculation.class.getClassLoader().getResourceAsStream(gunProperty + ".properties");
                properties.load(in);
                Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();//返回的属性键值对实体
                boolean ifDict = false;
                for (Map.Entry<Object, Object> entry : entrySet) {

                    if (entry.getKey().equals(code)) {
                        ifDict = true;
                        if (gunIndex == 1) {
                            CurrentBody.gun1Fps = Long.valueOf(entry.getValue().toString().split("\\,")[0]);
                            CurrentBody.gun1FpsRise = new BigDecimal(entry.getValue().toString().split("\\,")[1]);
                            System.out.println("设置当前gun1的fps为：" + CurrentBody.gun1Fps + " 波动为" + CurrentBody.gun1FpsRise);

                        } else {
                            CurrentBody.gun2Fps = Long.valueOf(entry.getValue().toString().split("\\,")[0]);
                            CurrentBody.gun2FpsRise = new BigDecimal(entry.getValue().toString().split("\\,")[1]);
                            System.out.println("设置当前gun2的fps为：" + CurrentBody.gun1Fps + " 波动为" + CurrentBody.gun2FpsRise);
                        }
                    }
                }
                // 如果检测到配件但是没有匹配字段，设置默认
                if(!ifDict){
                    //设置当前枪的编码，枪名先设置机瞄,默认抖动
                    if (gunIndex == 1) {
                        CurrentBody.gun1Fps = fps;
                        CurrentBody.gun1FpsRise = new BigDecimal(1.4);
                        CurrentBody.gun1code = Joiner.on("/").join(resultCode);
                        CurrentBody.gun1Name = gunProperty;
                    } else {
                        CurrentBody.gun2Fps = fps;
                        CurrentBody.gun2FpsRise = new BigDecimal(1.4);
                        CurrentBody.gun2code = Joiner.on("/").join(resultCode);
                        CurrentBody.gun2Name = gunProperty;
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(gunIndex+"没检查到配件！");
            if (KeyboardHook.isInthePackage && KeyboardHook.resultIfvalid) {
                try {
                    String code = Joiner.on("/").join(resultCode);
                    Properties properties = new Properties();
                    InputStream in = GunFpsCalculation.class.getClassLoader().getResourceAsStream(gunProperty + ".properties");
                    properties.load(in);
                    Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();//返回的属性键值对实体
                    boolean ifDict = false;
                    for (Map.Entry<Object, Object> entry : entrySet) {

                        if (entry.getKey().equals(code)) {
                            ifDict = true;
                            if (gunIndex == 1) {
                                CurrentBody.gun1Fps = Long.valueOf(entry.getValue().toString().split("\\,")[0]);
                                CurrentBody.gun1FpsRise = new BigDecimal(entry.getValue().toString().split("\\,")[1]);
                                System.out.println("设置当前gun1的fps为：" + CurrentBody.gun1Fps + " 波动为" + CurrentBody.gun1FpsRise);

                            } else {
                                CurrentBody.gun2Fps = Long.valueOf(entry.getValue().toString().split("\\,")[0]);
                                CurrentBody.gun2FpsRise = new BigDecimal(entry.getValue().toString().split("\\,")[1]);
                                System.out.println("设置当前gun2的fps为：" + CurrentBody.gun1Fps + " 波动为" + CurrentBody.gun2FpsRise);
                            }
                        }
                    }
                    // 如果检测到配件但是机瞄也没有匹配字段，设置默认
                    if(!ifDict){
                        //设置当前枪的编码，枪名先设置机瞄,默认抖动
                        if (gunIndex == 1) {
                            CurrentBody.gun1Fps = fps;
                            CurrentBody.gun1FpsRise = new BigDecimal(1.4);
                            CurrentBody.gun1code = Joiner.on("/").join(resultCode);
                            CurrentBody.gun1Name = gunProperty;
                        } else {
                            CurrentBody.gun2Fps = fps;
                            CurrentBody.gun2FpsRise = new BigDecimal(1.4);
                            CurrentBody.gun2code = Joiner.on("/").join(resultCode);
                            CurrentBody.gun2Name = gunProperty;
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

        }


    }
}

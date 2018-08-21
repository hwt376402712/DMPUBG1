package com;

import java.math.BigDecimal;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 11:42
 */
public class CurrentBody {

    public static boolean ifDunXia = false;


    public volatile static long gun1Fps = 0;
    public volatile static BigDecimal gun1FpsRise = new BigDecimal(1);
    public volatile static String gun1code = "";
    public volatile static String gun1Name = "";
    public volatile static boolean gun1Exist = false;

    public volatile static long gun2Fps = 0;
    public volatile static BigDecimal gun2FpsRise = new BigDecimal(1);
    public volatile static String gun2code = "";
    public volatile static String gun2Name = "";
    public volatile static boolean gun2Exist = false;


    public volatile static Integer currentGun = 0;


    static void changeBody() {
        if (!ifDunXia) {
            ifDunXia = true;
        } else {
            ifDunXia = false;
        }
    }


    public static long getCurrentFps() {
        System.out.println("获取当前枪FPS");
        if (null == currentGun) {// 没有切换枪，
            if (0 != gun1Fps) {
                System.out.println("获取当前枪FPS，gun1Fps:" + gun1Fps);
                return gun1Fps;
            } else {
                return gun2Fps;
            }
        } else {
            if (currentGun != 1 && currentGun != 2) {// 其他操作，不压枪
                return 0;
            } else {
                if (1 == currentGun) {
                    if (0 != gun1Fps) {
                        return gun1Fps;
                    } else {
                        return gun2Fps;
                    }

                } else if (2 == currentGun) {
                    if (0 != gun2Fps) {
                        return gun2Fps;
                    } else {
                        return gun1Fps;
                    }
                }
            }
        }

        return 0;
    }

    public static BigDecimal getCurrentFpsRise() {
        if (null == currentGun) {
            // 没有切换枪，
            if (0 != gun1Fps) {
                System.out.println("获取当前枪gun1Fps波动:" + gun1FpsRise);
                return gun1FpsRise;
            } else {
                return gun2FpsRise;
            }
        } else {
            if (currentGun != 1 && currentGun != 2) {// 其他操作，不压枪
                return new BigDecimal(0);
            } else {
                if (1 == currentGun) {

                    return gun1FpsRise;


                } else if (2 == currentGun) {

                    return gun2FpsRise;

                }
            }
        }

        return new BigDecimal(0);
    }


}

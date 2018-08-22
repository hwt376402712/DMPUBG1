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
        if (0 == currentGun) {
            // 手上没枪
            if (gun1Exist) {
                System.out.println("获取当前枪FPS，gun1Fps:" + gun1Fps);
                currentGun = 1;
                return gun1Fps;
            } else if (gun2Exist) {
                System.out.println("获取当前枪FPS，gun2Fps:" + gun1Fps);
                currentGun = 2;
                return gun2Fps;
            } else {
                return 0;
            }
        } else {
            if (currentGun != 1 && currentGun != 2) {// 其他操作，不压枪
                return 0;
            } else {
                if (1 == currentGun) {
                    if (gun1Exist) {
                        return gun1Fps;
                    } else if (gun2Exist) {
                        currentGun = 2;
                        return gun2Fps;
                    } else {
                        currentGun = 0;
                        return 0;
                    }

                } else if (2 == currentGun) {
                    if (gun2Exist) {
                        return gun2Fps;
                    } else if (gun1Exist) {
                        currentGun = 1;
                        return gun1Fps;
                    } else {
                        currentGun = 0;
                        return 0;
                    }
                }
            }
        }

        return 0;
    }

    public static BigDecimal getCurrentFpsRise() {
        if (0 == currentGun) {
            // 手上没枪
            if (gun1Exist) {
                System.out.println("获取当前枪gun1Fps波动:" + gun1FpsRise);
                return gun1FpsRise;
            } else if (gun2Exist) {
                return gun2FpsRise;
            } else {
                return new BigDecimal(0);
            }
        } else {
            if (currentGun != 1 && currentGun != 2) {// 其他操作，不压枪
                return new BigDecimal(0);
            } else {
                if (1 == currentGun) {
                    if (gun1Exist) {
                        return gun1FpsRise;
                    } else if (gun2Exist) {
                        return gun2FpsRise;
                    } else {
                        return new BigDecimal(0);
                    }


                } else if (2 == currentGun) {
                    if (gun2Exist) {
                        return gun2FpsRise;
                    } else if (gun1Exist) {
                        return gun1FpsRise;
                    } else {
                        return new BigDecimal(0);
                    }

                }
            }
        }

        return new BigDecimal(0);
    }


}

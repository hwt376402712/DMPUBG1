package com;

import com.google.common.base.Joiner;
import com.myloader.FileConstant;
import com.gun.GunConstantName;
import com.inter.IFileEncryptAndDecrypt;
import com.jacob.com.Variant;
import com.myloader.MyloaderConstruct;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.ui.GameForm;


import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 16:40
 */
public class KeyboardHook implements Runnable {
    private WinUser.HHOOK hhk;


    public static volatile boolean isInthePackage = false;

    public static volatile boolean resultIfvalid = false;// 背包存在延迟，校验是否存在枪支时很可能已经关闭了背包(为isInthePackage加锁)

    public static volatile boolean ifInGameMain = false;//是否已经进入了游戏主画面，为准信的ocr取色作用

    public static volatile String zhunxinColor = null;//准心得颜色，来判断是否在腰射或者肩射


    //钩子回调函数
    private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {

            if (nCode >= 0) {
                // 系统开关
                if (event.vkCode == 121 && wParam.intValue() == 257) {
                    if ("已关闭".equals(GameForm.jLabel.getText())) {
                        GameForm.jLabel.setText("启动中...");
                        // 绑定当前窗口
                        long dm = Constant.getDm().invoke("GetForegroundFocus").getInt();
                        Variant[] var = new Variant[5];
                        var[0] = new Variant(dm);
                        var[1] = new Variant("normal");
                        var[2] = new Variant("dx");
                        var[3] = new Variant("dx");
                        var[4] = new Variant(0);

                        long i = Constant.getDm().invoke("BindWindow", var).getInt();
                        Constant.setCurrentPid(Constant.getDm().invoke("GetForegroundFocus").getInt());
                        MouseHook.startMouseListen();

                        java.awt.Toolkit.getDefaultToolkit().beep();
                    } else {
                        GameForm.jLabel.setText("已关闭");
                        MouseHook.stopMouseListen();
                        // 解绑
                        long dm = Constant.getDm().invoke("UnBindWindow").getInt();
                        Constant.setCurrentPid(0);

                    }
                } else {
                    // 是否在绑定窗口内
                    if (Constant.getDm().invoke("GetForegroundFocus").getInt() == Constant.getCurrentPid()) {

                        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                        System.out.println(time + " KEY: " + event.vkCode + "---" + wParam.intValue());

                        // 检测蹲下站立
                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.DUN.getKeyCode()) && wParam.intValue() == 257) {

                            CurrentBody.changeBody();
                        }

                        // 切换枪
                        if (event.vkCode == 49 && wParam.intValue() == 257) {
                            if (CurrentBody.gun1Exist) {
                                CurrentBody.currentGun = 1;
                                System.out.println("切换当前枪支1");
                            } else if (CurrentBody.gun2Exist) {
                                System.out.println("枪支1不存在，切换到枪支2");
                                CurrentBody.currentGun = 2;
                            } else {
                                CurrentBody.currentGun = 0;
                            }

                        }
                        if (event.vkCode == 50 && wParam.intValue() == 257) {
                            if (CurrentBody.gun2Exist) {
                                CurrentBody.currentGun = 2;
                                System.out.println("切换当前枪支2");
                            } else if (CurrentBody.gun1Exist) {
                                System.out.println("枪支2不存在，切换到枪支1");
                                CurrentBody.currentGun = 1;
                            } else {
                                CurrentBody.currentGun = 0;
                            }

                        }

                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.TAB.getKeyCode()) && wParam.intValue() == 256) {

                            resultIfvalid = false;


                        }
                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.TAB.getKeyCode()) && wParam.intValue() == 257) {

                            resultIfvalid = false;

                            new Thread() {
                                public void run() {
                                    try {
                                        Thread.sleep(300);
                                        checkPackage();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();


                        }
                        // pageup上调当前枪的fps
                        if (event.vkCode == 33 && wParam.intValue() == 257) {

                            upDownFps(1);


                        }
                        if (event.vkCode == 34 && wParam.intValue() == 257) {

                            upDownFps(0);


                        }
                        // 方向上上调当前枪的fps波动
                        if (event.vkCode == 38 && wParam.intValue() == 257) {

                            upDownFpsRise(1);


                        }
                        if (event.vkCode == 40 && wParam.intValue() == 257) {

                            upDownFpsRise(0);


                        }
                        // baocun
                        if (event.vkCode == 13 && wParam.intValue() == 257) {

                            saveGunFps();


                        }
                        // 调高
                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.TIAO.getKeyCode()) && wParam.intValue() == 257) {

                            CurrentBody.ifDunXia = false;


                        }
                        // 按下ESC退出
//                if(event.vkCode==27) KeyboardHook.this.setHookOff();
                    }


                }

            }


            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, new LPARAM());
        }


    };//MyBlog @See http://blog.csdn.net/shenpibaipao


    public void run() {
        setHookOn();
    }

    // 安装钩子
    public void setHookOn() {
        System.out.println("Hook On!");

        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        hhk = User32.INSTANCE.SetWindowsHookEx(User32.WH_KEYBOARD_LL, keyboardProc, hMod, 0);

        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
            if (result == -1) {
                setHookOff();
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }

    // 移除钩子并退出
    public void setHookOff() {
        System.out.println("Hook Off!");
        User32.INSTANCE.UnhookWindowsHookEx(hhk);
    }


    public void checkGun() {

        Variant[] ziku = new Variant[2];
        ziku[0] = new Variant(0);
        ziku[1] = new Variant(new FileConstant().getPath("ziku.txt"));
        Constant.getDm().invoke("SetDict", ziku).getInt();
        Variant[] gun1Search = new Variant[7];
        gun1Search[0] = new Variant(1349);
        gun1Search[1] = new Variant(116);
        gun1Search[2] = new Variant(1511);
        gun1Search[3] = new Variant(185);
        gun1Search[4] = new Variant(Joiner.on("|").join(GunConstantName.gunNameList));
        gun1Search[5] = new Variant("ffffff-000000");
        gun1Search[6] = new Variant(0.7);

        String gun1result = Constant.getDm().invoke("FindStrFastEx", gun1Search).toString();
        if (null != gun1result && !"".equals(gun1result)) {
            CurrentBody.gun1Exist = true;
        }

        gun1Search[0] = new Variant(1349);
        gun1Search[1] = new Variant(346);
        gun1Search[2] = new Variant(1511);
        gun1Search[3] = new Variant(391);

        String gun2result = Constant.getDm().invoke("FindStrFastEx", gun1Search).toString();
        if (null != gun2result && !"".equals(gun2result)) {
            CurrentBody.gun2Exist = true;
        }


        if ((null != gun1result && !"".equals(gun1result)) || (null != gun2result && !"".equals(gun2result))) {
            System.err.print("包裹中存在枪支！");
//            java.awt.Toolkit.getDefaultToolkit().beep();

            StringBuffer totalResult = new StringBuffer();
            if (null != gun1result && !"".equals(gun1result)) {
                totalResult.append(gun1result);
            }

            if (null != gun2result && !"".equals(gun2result)) {
                if (totalResult.length() != 0) {
                    totalResult.append("|" + gun2result);
                } else {
                    totalResult.append(gun2result);
                }
            }


            String names[] = totalResult.toString().split("\\|");
            if (names.length <= 2) {
                for (String name : names) {
                    String[] index = name.split("\\,");
                    switch (index[0]) {

                        case "0":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.SCAR).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.SCAR).checkPeijian();
                            }

                            break;

                        case "1":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.M416).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.M416).checkPeijian();
                            }

                            break;
                        case "2":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.M16).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.M16).checkPeijian();
                            }

                            break;
                        case "3":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.UMP9).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.UMP9).checkPeijian();
                            }

                            break;
                        case "4":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.VECTOR).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.VECTOR).checkPeijian();
                            }

                            break;

                        case "5":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.QBZ).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.QBZ).checkPeijian();
                            }

                            break;
                        case "6":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.AKM).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.AKM).checkPeijian();
                            }

                            break;
                        case "7":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.UZI).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.UZI).checkPeijian();
                            }

                            break;
                        case "8":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.TOM).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.TOM).checkPeijian();
                            }

                            break;
                        case "9":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.S686).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.S686).checkPeijian();
                            }

                            break;
                        case "10":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.ZIDONG).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.ZIDONG).checkPeijian();
                            }

                            break;
                        case "11":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.S12K).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.S12K).checkPeijian();
                            }

                            break;
                        case "12":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.QBU).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.QBU).checkPeijian();
                            }

                            break;
                        case "13":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.WIN94).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.WIN94).checkPeijian();
                            }

                            break;
                        case "14":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.BERY).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.BERY).checkPeijian();
                            }

                            break;
                        case "15":
                            if (Integer.parseInt(index[2]) < 250) {
                                new GunCheckRunnable(1, GunConstantName.SKS).checkPeijian();
                            } else {
                                new GunCheckRunnable(2, GunConstantName.SKS).checkPeijian();
                            }

                            break;


                    }

                }
            }


        }
        if (isInthePackage && resultIfvalid) {

            // 没检查到枪支需要清空fps

            if (null == gun1result || "".equals(gun1result)) {
                System.err.println("未检查到枪支1");
                CurrentBody.gun1Fps = 0;
                CurrentBody.gun1Name = "";
                CurrentBody.gun1code = "";
                CurrentBody.gun1Exist = false;

            }
            if (null == gun2result || "".equals(gun2result)) {
                System.err.println("未检查到枪支2");
                CurrentBody.gun2Fps = 0;
                CurrentBody.gun2Name = "";
                CurrentBody.gun2code = "";
                CurrentBody.gun2Exist = false;
            }
            if ((null == gun1result || "".equals(gun1result)) && (null == gun2result || "".equals(gun2result))) {
                CurrentBody.currentGun = 0;
            }
            if ((null == gun1result || "".equals(gun1result)) && (null != gun2result || !"".equals(gun2result))) {
                CurrentBody.currentGun = 2;
            }
            if ((null == gun2result || "".equals(gun2result)) && (null != gun1result || !"".equals(gun1result))) {
                CurrentBody.currentGun = 1;
            }

        }


    }


    public void checkPackage() {


        resultIfvalid = false;

        // 检查是否在背包界面
        Variant[] packageCheck = new Variant[8];
        packageCheck[0] = new Variant(466);
        packageCheck[1] = new Variant(95);
        packageCheck[2] = new Variant(517);
        packageCheck[3] = new Variant(153);
        packageCheck[4] = new Variant(new FileConstant().getPath("LEIXING.bmp"));
        packageCheck[5] = new Variant("000000");
        packageCheck[6] = new Variant(0.9);
        packageCheck[7] = new Variant(1);
        String gun1result = Constant.getDm().invoke("FindPicEx", packageCheck).toString();
        if (null != gun1result && !"".equals(gun1result)) {
            System.err.println("打开了背包");
            isInthePackage = true;
            resultIfvalid = true;
            ifInGameMain = true;
            new Thread() {
                public void run() {
                    while (isInthePackage && resultIfvalid) {

                        checkGun();

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }

                }
            }.start();
        } else {
            System.err.println("关闭了背包");
            isInthePackage = false;
            resultIfvalid = true;
            // 关闭了背包,并且有枪的情况下首次开始OCR取准心得rgb

            if (ifInGameMain && (null == zhunxinColor) && (CurrentBody.gun1Exist || CurrentBody.gun2Exist)) {
                Variant[] color = new Variant[2];
                color[0] = new Variant(959);
                color[1] = new Variant(539);
                String rgb = Constant.getDm().invoke("GetColor", color).toString();
                zhunxinColor = rgb;
            }
        }
    }


    public void upDownFps(int updown) {
        if (1 == updown) {
            // 判断当前枪支
            if (1 == CurrentBody.currentGun && CurrentBody.gun1Exist) {
                CurrentBody.gun1Fps++;

            } else if (2 == CurrentBody.currentGun && CurrentBody.gun2Exist) {
                CurrentBody.gun2Fps++;
            }
        } else {
            if (1 == CurrentBody.currentGun && CurrentBody.gun1Exist) {
                if (CurrentBody.gun1Fps > 0) {
                    CurrentBody.gun1Fps--;
                }


            } else if (2 == CurrentBody.currentGun && CurrentBody.gun2Exist) {
                if (CurrentBody.gun2Fps > 0) {
                    CurrentBody.gun2Fps--;
                }
            }
        }


    }

    public void upDownFpsRise(int updown) {
        if (1 == updown) {
            // 判断当前枪支
            if (1 == CurrentBody.currentGun && CurrentBody.gun1Exist) {
                CurrentBody.gun1FpsRise = CurrentBody.gun1FpsRise.add(new BigDecimal(0.1));

            } else if (2 == CurrentBody.currentGun && CurrentBody.gun2Exist) {
                CurrentBody.gun2FpsRise = CurrentBody.gun2FpsRise.add(new BigDecimal(0.1));
            }
        } else {
            if (1 == CurrentBody.currentGun && CurrentBody.gun1Exist) {
                if (CurrentBody.gun1FpsRise.compareTo(new BigDecimal(0)) > 0) {
                    CurrentBody.gun1FpsRise = CurrentBody.gun1FpsRise.subtract(new BigDecimal(0.1));
                }


            } else if (2 == CurrentBody.currentGun && CurrentBody.gun2Exist) {
                if (CurrentBody.gun2FpsRise.compareTo(new BigDecimal(0)) > 0) {
                    CurrentBody.gun2FpsRise = CurrentBody.gun2FpsRise.subtract(new BigDecimal(0.1));
                }
            }
        }


    }

    private void saveGunFps() {
        IFileEncryptAndDecrypt fileEncryptAndDecrypt = MyloaderConstruct.getFiFileEncryptAndDecrypt();


        if (CurrentBody.currentGun == 1 && CurrentBody.gun1Exist) {
            String gunName = CurrentBody.gun1Name;
            String gunCode = CurrentBody.gun1code;
            String fps = String.valueOf(CurrentBody.gun1Fps);
            String fpsRise = String.valueOf(CurrentBody.gun1FpsRise);
            FileReader reader = null;
            try {
                reader = new FileReader(new FileConstant().getPath(gunName + ".properties"));
                Properties p = new Properties();
                p.load(reader);
                p.setProperty(gunCode, fileEncryptAndDecrypt.encode(fps + "," + fpsRise));
                FileWriter writer = new FileWriter(new FileConstant().getPath(gunName + ".properties"));
                p.store(writer, "新增枪数据");
                reader.close();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (CurrentBody.currentGun == 2 && CurrentBody.gun2Exist) {
            String gunName = CurrentBody.gun2Name;
            String gunCode = CurrentBody.gun2code;
            String fps = String.valueOf(CurrentBody.gun2Fps);
            String fpsRise = String.valueOf(CurrentBody.gun2FpsRise);
            FileReader reader = null;
            try {
                reader = new FileReader(new FileConstant().getPath(gunName + ".properties"));
                Properties p = new Properties();
                p.load(reader);
                p.setProperty(gunCode, fileEncryptAndDecrypt.encode(fps + "," + fpsRise));
                FileWriter writer = new FileWriter(new FileConstant().getPath(gunName + ".properties"));
                p.store(writer, "新增枪数据");
                reader.close();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

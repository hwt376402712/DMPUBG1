package com;

import com.google.common.base.Joiner;
import com.gun.GunConstantName;
import com.jacob.com.Variant;
import com.sun.deploy.util.StringUtils;
import com.sun.jna.platform.win32.*;
import com.sun.jna.platform.win32.WinDef.*;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 16:40
 */
public class KeyboardHook implements Runnable {
    private WinUser.HHOOK hhk;


    private List<String> ignoreList = Arrays.asList("31");

    public static volatile boolean isInthePackage = false;

    public static volatile boolean resultIfvalid = false;// 背包存在延迟，校验是否存在枪支时很可能已经关闭了背包(为isInthePackage加锁)


    Thread thread1;

    Thread thread2;

    //钩子回调函数
    private WinUser.LowLevelKeyboardProc keyboardProc = new WinUser.LowLevelKeyboardProc() {
        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT event) {


            if (nCode >= 0) {
                // 系统开关
                if (event.vkCode == 121 && wParam.intValue() == 257) {
                    if ("开启".equals(StartF10Listen.f.getF10开启Button().getText())) {
                        StartF10Listen.f.getF10开启Button().setText("启动中...");
                        // 绑定当前窗口
                        long dm = Constant.getDm().invoke("GetForegroundFocus").getInt();
                        Variant[] var = new Variant[5];
                        var[0] = new Variant(dm);
                        var[1] = new Variant("normal");
                        var[2] = new Variant("dx");
                        var[3] = new Variant("normal");
                        var[4] = new Variant(0);

                        long i = Constant.getDm().invoke("BindWindow", var).getInt();
                        Constant.setCurrentPid(Constant.getDm().invoke("GetForegroundFocus").getInt());
                        MouseHook.startMouseListen();
                    } else {
                        StartF10Listen.f.getF10开启Button().setText("开启");
                        MouseHook.stopMouseListen();
                        // 解绑
                        long dm = Constant.getDm().invoke("UnBindWindow").getInt();
                        Constant.setCurrentPid(0);

                    }
                } else {
                    // 是否在绑定窗口内
                    if (Constant.getDm().invoke("GetForegroundFocus").getInt() == Constant.getCurrentPid()) {

                        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        System.out.println(time + " KEY: " + event.vkCode + "---" + wParam.intValue());

                        // 检测蹲下站立
                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.DUN.getKeyCode()) && wParam.intValue() == 257) {

                            CurrentBody.changeBody();
                        }

                        // 切换枪
                        if (event.vkCode == 49 && wParam.intValue() == 257) {
                            if(CurrentBody.gun1Fps != 0){
                                CurrentBody.currentGun = 1;
                                System.out.println("切换当前枪支1");
                            }

                        }
                        if (event.vkCode == 50 && wParam.intValue() == 257) {
                            if(CurrentBody.gun2Fps != 0){
                                CurrentBody.currentGun = 2;
                                System.out.println("切换当前枪支2");
                            }

                        }

                        if (event.vkCode == Integer.parseInt(KeyCodeEnuam.TAB.getKeyCode()) && wParam.intValue() == 256) {

                            resultIfvalid = false;
                            checkPackage();

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


    public  void checkGun() {
        System.out.println("开始检查枪支");
        Variant[] ziku = new Variant[2];
        ziku[0] = new Variant(0);
        ziku[1] = new Variant(new File(this.getClass().getClassLoader().getResource("ziku.txt").getPath()).getPath());
        Constant.getDm().invoke("SetDict", ziku).getInt();
        Variant[] gun1Search = new Variant[7];
        gun1Search[0] = new Variant(1349);
        gun1Search[1] = new Variant(116);
        gun1Search[2] = new Variant(1465);
        gun1Search[3] = new Variant(185);
        gun1Search[4] = new Variant(Joiner.on("|").join(GunConstantName.gunNameList));
        gun1Search[5] = new Variant("ffffff-000000");
        gun1Search[6] = new Variant(0.7);

        String gun1result = Constant.getDm().invoke("FindStrFastEx", gun1Search).toString();

        gun1Search[0] = new Variant(1349);
        gun1Search[1] = new Variant(346);
        gun1Search[2] = new Variant(1472);
        gun1Search[3] = new Variant(391);

        String gun2result = Constant.getDm().invoke("FindStrFastEx", gun1Search).toString();


        if ((null != gun1result && !"".equals(gun1result)) || (null != gun2result && !"".equals(gun2result))) {
            System.out.print("包裹中存在枪支！");
            java.awt.Toolkit.getDefaultToolkit().beep();

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


                    }

                }
            }


        } else {
            // 没检查到枪支需要清空fps
            if(isInthePackage && resultIfvalid){
                if(null == gun1result || "".equals(gun1result)){
                    System.out.println("未检查到枪支1");
                    CurrentBody.gun1Fps = 0;
                    CurrentBody.gun1Name = "";
                    CurrentBody.gun1code = "";

                }
                 if(null == gun2result || "".equals(gun2result)){
                    System.out.println("未检查到枪支2");
                    CurrentBody.gun2Fps = 0;
                    CurrentBody.gun2Name = "";
                    CurrentBody.gun2code = "";
                }
            }



        }


    }


    public void checkPackage() {


        resultIfvalid = false;
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 检查是否在背包界面
        Variant[] packageCheck = new Variant[8];
        packageCheck[0] = new Variant(1667);
        packageCheck[1] = new Variant(22);
        packageCheck[2] = new Variant(1766);
        packageCheck[3] = new Variant(75);
        packageCheck[4] = new Variant(new File(this.getClass().getClassLoader().getResource("KILL.bmp").getPath()).getPath());
        packageCheck[5] = new Variant("000000");
        packageCheck[6] = new Variant(0.9);
        packageCheck[7] = new Variant(1);
        String gun1result = Constant.getDm().invoke("FindPicEx", packageCheck).toString();

        if (null != gun1result && !"".equals(gun1result)) {
            System.out.println("打开了背包");
            isInthePackage = true;
            resultIfvalid = true;
            new Thread() {
                public void run() {
                    while (isInthePackage && resultIfvalid) {
                        checkGun();

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
        } else {
            System.out.println("关闭了背包");
            isInthePackage = false;
            resultIfvalid = true;
        }
    }
}

package com;

import com.jacob.com.Variant;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.ui.GameForm;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 16:21
 * <p>
 * import com.sun.jna.Platform;
 * import com.sun.jna.platform.win32.Kernel32;
 * import com.sun.jna.platform.win32.User32;
 * import com.sun.jna.platform.win32.WinDef.HMODULE;
 * import com.sun.jna.platform.win32.WinDef.LRESULT;
 * import com.sun.jna.platform.win32.WinDef.WPARAM;
 * import com.sun.jna.platform.win32.WinUser;
 * import com.sun.jna.platform.win32.WinUser.HHOOK;
 * import com.sun.jna.platform.win32.WinUser.MSG;
 * <p>
 * /**
 * 鼠标钩子
 */
public class MouseHook implements Runnable {
    //鼠标事件编码
    public static final int WM_MOUSEMOVE = 512;
    public static final int WM_LBUTTONDOWN = 513;
    public static final int WM_LBUTTONUP = 514;
    public static final int WM_RBUTTONDOWN = 516;
    public static final int WM_RBUTTONUP = 517;
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;
    public User32 lib;
    private static WinUser.HHOOK hhk;
    private MouseHookListener mouseHook;
    private WinDef.HMODULE hMod;
    private boolean isWindows = false;

    static Thread manThread;

    static boolean start = false;
    static volatile boolean leftBtn = true;
    static volatile boolean rightBtn = false;
    static volatile boolean middleBtn = false;
    static volatile boolean Pjian = false;

    static Long leftAnxia = 0L;
    static Long anxiaNow = 0L;

    static Long middleAnxia = 0L;


    static Long leftAnxia2 = 0L;


    static volatile long fps = 0;// 获取枪配件更改这个值
    static BigDecimal fpsRise = new BigDecimal(1);//枪的波动

    static volatile boolean gunLianflag = false;// 连发按下鼠标的冲突


    public MouseHook() {
        isWindows = Platform.isWindows();
        if (isWindows) {
            lib = User32.INSTANCE;
            hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        }

    }

    //添加钩子监听
    public void addMouseHookListener(MouseHookListener mouseHook) {
        this.mouseHook = mouseHook;
        this.mouseHook.lib = lib;
    }

    //启动
    public void startWindowsHookEx() {
        if (isWindows) {
            lib.SetWindowsHookEx(WinUser.WH_MOUSE_LL, mouseHook, hMod, 0);
            int result;
            WinUser.MSG msg = new WinUser.MSG();
            while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
                if (result == -1) {
                    System.err.println("error in get message");
                    break;
                } else {
                    System.err.println("got message");
                    lib.TranslateMessage(msg);
                    lib.DispatchMessage(msg);
                }
            }
        }

    }

    //关闭
    public void stopWindowsHookEx() {
        if (isWindows) {
            lib.UnhookWindowsHookEx(hhk);
        }

    }

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        try {

            MouseHook mouseHook = new MouseHook();
            mouseHook.addMouseHookListener(new MouseHookListener() {


                //回调监听
                @Override
                public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, MouseHookStruct lParam) {


                    if (nCode >= 0 && start) {

                        switch (wParam.intValue()) {
                            case MouseHook.WM_MOUSEMOVE:
//                                System.err.println("mouse move left button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);

                                break;
                            case MouseHook.WM_LBUTTONDOWN:
//                                System.err.println("mouseLeft down left button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);

                                leftBtn = true;


                                leftAnxia = System.currentTimeMillis();
                                leftAnxia2 = System.currentTimeMillis();

                                startMouserMove();

                                //一键肩射
                                startJianshe();


                                // 连发M16
                                gunLianfa();


                                break;


                            case MouseHook.WM_LBUTTONUP:
//                                System.err.println("mouseLeft up left button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                if (!gunLianflag) {
                                    stopJianshe();

                                    leftBtn = false;
                                    leftAnxia = 0L;
                                    anxiaNow = null;
                                    leftAnxia2 = 0L;
                                } else {
                                    gunLianflag = false;
                                }


                                break;
                            case MouseHook.WM_RBUTTONDOWN:
//                                System.err.println("mouseRight down right button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                rightBtn = true;
                                break;
                            case MouseHook.WM_RBUTTONUP:
//                                System.err.println("mouseRight up right button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                rightBtn = false;
                                leftAnxia = 0L;
                                anxiaNow = 0L;
                                break;
                            case MouseHook.WM_MBUTTONDOWN:
                                // 光速拾取物品
                                middleBtn = true;
                                middleAnxia = System.currentTimeMillis();
                                quickPick();
                                break;
                            case MouseHook.WM_MBUTTONUP:

                                middleAnxia = 0L;

                                middleBtn = false;
                                break;
                        }
                    }


                    //将钩子信息传递到当前钩子链中的下一个子程，一个钩子程序可以调用这个函数之前或之后处理钩子信息
                    //hhk：当前钩子的句柄
                    //nCode ：钩子代码; 就是给下一个钩子要交待的，钩传递给当前Hook过程的代码。下一个钩子程序使用此代码，以确定如何处理钩的信息。
                    //wParam：要传递的参数; 由钩子类型决定是什么参数，此参数的含义取决于当前的钩链与钩的类型。
                    //lParam：Param的值传递给当前Hook过程。此参数的含义取决于当前的钩链与钩的类型。
                    return lib.CallNextHookEx(hhk, nCode, wParam, lParam.getPointer());
                }
            });
            mouseHook.startWindowsHookEx();

//            Thread.sleep(20000);
//            mouseHook.stopWindowsHookEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void stopMouseListen() {
        start = false;
        leftBtn = false;
        leftAnxia = 0L;
        anxiaNow = 0L;
    }

    static void startMouseListen() {
        start = true;
        leftBtn = false;
        leftAnxia = 0L;
        anxiaNow = 0L;
    }


    public void startJianshe() {
        if (GameForm.leftYaoshe) {
            new Thread() {
                public void run() {
                    if (!KeyboardHook.isInthePackage && Constant.getDm().invoke("GetForegroundFocus").getInt() == Constant.getCurrentPid()) {
                        while (leftBtn && !rightBtn) {

                            // 左键按下间隔小于0.1秒，单点，不压枪
                            if (System.currentTimeMillis() - leftAnxia2 > 100) {

                                try {
                                    Robot myRobot = new Robot();

                                    myRobot.keyPress(KeyEvent.VK_P);
                                    Pjian = true;
                                    break;
                                } catch (Exception e) {

                                }

                            }


                        }
                    }

                }
            }.start();
        }


    }

    public void stopJianshe() {
        if (GameForm.leftYaoshe) {
            try {
                if (Pjian) {
                    Robot myRobot = new Robot();

                    myRobot.keyRelease(KeyEvent.VK_P);
                    Pjian = false;
                }

            } catch (Exception e) {

            }
        }

    }


    public void startMouserMove() {
        manThread = new Thread() {
            @Override
            public void run() {
                fps = CurrentBody.getCurrentFps();
                fpsRise = CurrentBody.getCurrentFpsRise();
                Variant[] shubiao = new Variant[2];
                shubiao[0] = new Variant(0);
                shubiao[1] = new Variant(fps);
                System.err.println("当前FPS：" + fps + " 波动" + fpsRise);
                if (ifFire()) {
                    while (leftBtn) {

                        // 左键按下间隔小于0.1秒，单点，不压枪
                        if (System.currentTimeMillis() - leftAnxia > 100) {

                            if (null == anxiaNow) {
                                anxiaNow = System.currentTimeMillis();
                            } else {
                                // 开枪的后半部分抖动递增，需要波动计算
                                if (System.currentTimeMillis() - anxiaNow > 30) {
                                    if (System.currentTimeMillis() - leftAnxia < 700) {
                                        shubiao[1] = shubiao[1];
                                    }
                                    if (System.currentTimeMillis() - leftAnxia >= 700 && System.currentTimeMillis() - leftAnxia < 1500) {
                                        shubiao[1] = new Variant(fpsRise.multiply(new BigDecimal(fps)).longValue());
                                    }
                                    if (System.currentTimeMillis() - leftAnxia >= 1500) {
                                        shubiao[1] = new Variant(fpsRise.multiply(new BigDecimal(fps)).add(new BigDecimal(0.4)).longValue());
                                    }
                                    Constant.getDm().invoke("MoveR", shubiao);
                                    anxiaNow = System.currentTimeMillis();
                                }
                            }


                        }


                    }


                }
            }


        };
        manThread.start();
    }

    // 判断是否开始压枪,在背包或者存在准心得情况下不压枪
    private boolean ifFire() {
        if (Constant.getDm().invoke("GetForegroundFocus").getInt() == Constant.getCurrentPid()) {
            if (KeyboardHook.isInthePackage) {
                return false;
            } else {
                if (null != KeyboardHook.zhunxinColor) {
                    // 判断准心的4个像素点的rgb
                    Variant[] color = new Variant[2];
                    color[0] = new Variant(959);
                    color[1] = new Variant(539);
                    String rgb1 = Constant.getDm().invoke("GetColor", color).toString();
                    color[0] = new Variant(960);
                    color[1] = new Variant(539);
                    String rgb2 = Constant.getDm().invoke("GetColor", color).toString();
                    color[0] = new Variant(959);
                    color[1] = new Variant(540);
                    String rgb3 = Constant.getDm().invoke("GetColor", color).toString();
                    color[0] = new Variant(960);
                    color[1] = new Variant(540);
                    String rgb4 = Constant.getDm().invoke("GetColor", color).toString();
                    if (rgb1.equals(rgb2) && rgb2.equals(rgb3) && rgb3.equals(rgb4) && rgb4.equals(KeyboardHook.zhunxinColor)) {
                        return false;
                    } else {
                        return true;
                    }

                } else {
                    return false;
                }

            }
        }
        return false;


    }

    public void quickPick() {

        if (GameForm.quickPick) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Variant[] lock = new Variant[4];
                        lock[0] = new Variant(208);
                        lock[1] = new Variant(0);
                        lock[2] = new Variant(854);
                        lock[3] = new Variant(1000);

                        Variant[] unlock = new Variant[4];
                        unlock[0] = new Variant(0);
                        unlock[1] = new Variant(0);
                        unlock[2] = new Variant(0);
                        unlock[3] = new Variant(0);


                        Robot robot = new Robot();
                        while (middleBtn) {


                            // 中键按下间隔小于0.1秒，单点
                            if (System.currentTimeMillis() - middleAnxia > 100) {


                                Constant.getDm().invoke("LockMouseRect", lock);


                                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);

                                Thread.sleep(30);
                                PointerInfo pinfo = MouseInfo.getPointerInfo();
                                Point p = pinfo.getLocation();
                                double mx = p.getX();
                                double my = p.getY();
                                robot.mouseMove((int) Math.ceil(mx) + 600, (int) Math.ceil(my));


                                Thread.sleep(30);

                                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

                                Thread.sleep(30);
                                PointerInfo pinfo1 = MouseInfo.getPointerInfo();
                                Point p1 = pinfo1.getLocation();
                                double mx1 = p1.getX();
                                double my1 = p1.getY();
                                robot.mouseMove((int) Math.ceil(mx - 600), (int) Math.ceil(my));
                                Thread.sleep(30);


                            }


                        }
                        Constant.getDm().invoke("LockMouseRect", unlock);

                    } catch (Exception e) {

                    }


                }
            }.start();

        }

    }

    public void gunLianfa() {

        if (GameForm.M16Lianfa) {
            new Thread() {
                @Override
                public void run() {

                    if (ifFire()) {
                        while (leftBtn) {
                            // 左键按下间隔小于0.1秒，单点，不压枪
                            if (System.currentTimeMillis() - leftAnxia > 100) {
                                if ((CurrentBody.currentGun == 1 && CurrentBody.gun1Exist && CurrentBody.gun1Name.equals("M16")) ||
                                        CurrentBody.currentGun == 2 && CurrentBody.gun2Exist && CurrentBody.gun2Name.equals("M16")) {

                                    try {
                                        gunLianflag = true;
                                        Constant.getDm().invoke("LeftUp");
                                        Robot myRobot = new Robot();
                                        while (leftBtn) {
                                            for (int i = 0; i <= 6; i++) {
                                                myRobot.keyPress(KeyEvent.VK_I);
                                                Thread.sleep(2);
                                                myRobot.keyRelease(KeyEvent.VK_I);
                                                Thread.sleep(2);
                                            }

                                            Variant[] moveDown = new Variant[2];
                                            moveDown[0] = new Variant(0);
                                            moveDown[1] = new Variant(1);
                                            Constant.getDm().invoke("MoveR", moveDown);
                                            Thread.sleep(1);


                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }

                            }


                        }

                    }
                }


            }.start();
        }


    }


}
package com;

import com.jacob.com.Variant;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.math.BigDecimal;

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
    static Long anxia = null;
    static Long anxiaNow = null;
    static volatile long fps = 0;// 获取枪配件更改这个值
    static BigDecimal fpsRise = new BigDecimal(1);//枪的波动


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
                                System.err.println("mouseLeft down left button down, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                leftBtn = true;
                                startMouserMove();


                                break;


                            case MouseHook.WM_LBUTTONUP:
                                System.err.println("mouseLeft up left button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                leftBtn = false;
                                anxia = null;
                                anxiaNow = null;

                                break;
                            case MouseHook.WM_RBUTTONDOWN:
                                System.err.println("mouseRight down right button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                rightBtn = true;
                                startMouserMove();
                                break;
                            case MouseHook.WM_RBUTTONUP:
                                System.err.println("mouseRight up right button up, x=" + lParam.pt.x + " y=" + lParam.pt.y);
                                rightBtn = false;
                                anxia = null;
                                anxiaNow = null;
                                break;
                            case MouseHook.WM_MBUTTONDOWN:
                                break;
                            case MouseHook.WM_MBUTTONUP:
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
        anxia = null;
        anxiaNow = null;
    }

    static void startMouseListen() {
        start = true;
        leftBtn = false;
        anxia = null;
        anxiaNow = null;
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
                System.out.println("当前FPS：" + fps+" 波动"+fpsRise);
                while (leftBtn && rightBtn) {

                    if (null == anxia) {
                        anxia = System.currentTimeMillis();
                    } else {
                        if (System.currentTimeMillis() - anxia > 100) {
                            if (null == anxiaNow) {
                                anxiaNow = System.currentTimeMillis();
                            } else {
                                if (System.currentTimeMillis() - anxiaNow > 30) {
                                    if (System.currentTimeMillis() - anxia < 700) {
                                        shubiao[1] = shubiao[1];
                                    }
                                    if (System.currentTimeMillis() - anxia >= 700 && System.currentTimeMillis() - anxia < 1500) {
                                        shubiao[1] = new Variant(fpsRise.multiply(new BigDecimal(fps)).longValue());
                                    }
                                    if (System.currentTimeMillis() - anxia >= 1500) {
                                        shubiao[1] = new Variant(fpsRise.multiply(new BigDecimal(fps)).add(new BigDecimal(0.4)).longValue());
                                    }
                                    Constant.getDm().invoke("MoveR", shubiao);
                                    anxiaNow = System.currentTimeMillis();
                                }
                            }

                        }


                    }


                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        manThread.start();
    }
}
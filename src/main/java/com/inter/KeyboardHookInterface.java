package com.inter;

import com.sun.jna.platform.win32.WinUser;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: huangwentao
 * @Date: 2018/8/24 10:02
 */
public class KeyboardHookInterface implements  Runnable{

     WinUser.HHOOK hhk = null;


     List<String> ignoreList = Arrays.asList("31");

    boolean isInthePackage = false;

     boolean resultIfvalid = false;// 背包存在延迟，校验是否存在枪支时很可能已经关闭了背包(为isInthePackage加锁)

     boolean ifInGameMain = false;//是否已经进入了游戏主画面，为准信的ocr取色作用

     String zhunxinColor = null;//准心得颜色，来判断是否在腰射或者肩射

    public void run(){

    }


}

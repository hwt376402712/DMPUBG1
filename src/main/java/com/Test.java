package com;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 19:31
 */
public class Test {

    public static void main(String[] args) throws IOException {

//        new TestConstructor();


        try
        {
            Thread.sleep(2000);
            Robot myRobot = new Robot();

            myRobot.keyPress(KeyEvent.VK_Q);		// 模拟键盘按下Q键（小写）
//            myRobot.keyRelease(KeyEvent.VK_Q);
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}

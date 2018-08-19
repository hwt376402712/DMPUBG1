package com;

import javax.swing.*;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 14:08
 */
public class StartF10Listen {

    static MainForm f;


    public StartF10Listen() {
        f = new MainForm();
        JFrame frame = new JFrame("form");
        frame.setContentPane(f.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        KeyboardHook keyboardHook = new KeyboardHook();
        MouseHook mouseHook = new MouseHook();
        Thread keyHookThread = new Thread(keyboardHook);
        keyHookThread.start();
        Thread mouseHookThread = new Thread(mouseHook);
        mouseHookThread.start();


    }



}

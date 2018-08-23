package com;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 14:08
 */
public class StartF10Listen {

    static MainForm f;


    public StartF10Listen() {
        createResourceFile();
        createVew();

        KeyboardHook keyboardHook = new KeyboardHook();
        MouseHook mouseHook = new MouseHook();
        Thread keyHookThread = new Thread(keyboardHook);
        keyHookThread.start();
        Thread mouseHookThread = new Thread(mouseHook);
        mouseHookThread.start();


    }

    private void createVew() {
        f = new MainForm();
        f.getPanel().setSize(1000,300);
        JFrame frame = new JFrame("幕后玩家");
        frame.setContentPane(f.getPanel());
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void copyFile(InputStream inStream, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();

        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }


    public void createResourceFile() {
        //运行前检查资源目录是否存在
        File file = new File("D:\\pubgResoueces");
        if (!file.exists()) {
            try {
                file.mkdir();
                for (String fileName : FileList.filName) {
                    InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fileName);
                    copyFile(resource, "D:\\pubgResoueces\\" + fileName);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

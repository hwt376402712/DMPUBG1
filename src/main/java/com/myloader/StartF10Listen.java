package com.myloader;

import com.FileList;
import com.KeyboardHook;
import com.MouseHook;
import com.inter.IStartF10Listen;
import com.ui.MainForm;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 14:08
 */
public class StartF10Listen implements IStartF10Listen {


    public void start() {
        createResourceFile();

        KeyboardHook keyboardHook = new KeyboardHook();
        MouseHook mouseHook = new MouseHook();
        Thread keyHookThread = new Thread(keyboardHook);
        keyHookThread.start();
        Thread mouseHookThread = new Thread(mouseHook);
        mouseHookThread.start();
    }


    public  void copyFile(InputStream inStream, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();

        } catch (Exception e) {
            System.err.println("复制单个文件操作出错" + newPath);
            e.printStackTrace();
        }
    }


    public void createResourceFile() {
        //运行前检查资源目录是否存在
        File file = new File("D:\\pubgResources");
        if (!file.exists()) {
            try {
                file.mkdir();
                for (String fileName : FileList.filName) {
                    InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fileName);
                    copyFile(resource, "D:\\pubgResources\\" + fileName);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

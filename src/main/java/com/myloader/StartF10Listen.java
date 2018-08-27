package com.myloader;

import com.Constant;
import com.FileList;
import com.KeyboardHook;
import com.MouseHook;
import com.alibaba.fastjson.JSON;
import com.inter.IStartF10Listen;
import com.ui.MainForm;
import com.util.HttpRequestUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 14:08
 */
public class StartF10Listen implements IStartF10Listen {


    @Override
    public void start(String username, String password) {

        if (login(username, password)) {
            createResourceFile();

            KeyboardHook keyboardHook = new KeyboardHook();
            MouseHook mouseHook = new MouseHook();
            Thread keyHookThread = new Thread(keyboardHook);
            keyHookThread.start();
            Thread mouseHookThread = new Thread(mouseHook);
            mouseHookThread.start();
        }


    }

    public boolean login(String username, String password) {

        String res = HttpRequestUtil.get("http://111.231.249.197:8090/user/userLogin?" +
                "username=" + username + "&password=" + password);
        if (null != res && !"".equals(res)) {
            Map<String, Object> map = JSON.parseObject(res, Map.class);

            if (0 == (Integer) map.get("status")) {
                JOptionPane.showMessageDialog(null, "用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE);

                System.exit(0);
            }
            else if(1 == (Integer) map.get("status")){

                Long outTime = Long.valueOf(map.get("outTime").toString());
                System.out.print(Constant.getDm().invoke("GetNetTime"));
                if(outTime > System.currentTimeMillis()){
                    return true;
                }
                else{
                    JOptionPane.showMessageDialog(null, "账号已经过期，功能将无法使用，请尽快充值！", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return false;

    }


    public void copyFile(InputStream inStream, String newPath) {
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

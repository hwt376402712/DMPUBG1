package com.myloader;

import com.inter.IFileEncryptAndDecrypt;
import com.inter.IStartF10Listen;

/**
 * @Author: huangwentao
 * @Date: 2018/8/24 16:44
 */
public class MyloaderConstruct {

    public static int flag = 1;//0 开发环境 1线上


    public static IFileEncryptAndDecrypt getFiFileEncryptAndDecrypt(){
        IFileEncryptAndDecrypt fileEncryptAndDecrypt = null;
        try {
            if (flag == 0) {
//                fileEncryptAndDecrypt = new FileEncryptAndDecrypt();
//                fileEncryptAndDecrypt.getfileEncryptAndDecrypt();
            } else {
                Class c = null;
                c = new MyClassLoader("/").loadClass("FileEncryptAndDecrypt.class");
                fileEncryptAndDecrypt = (IFileEncryptAndDecrypt) c.newInstance();
                fileEncryptAndDecrypt.getfileEncryptAndDecrypt();

            }
            return fileEncryptAndDecrypt;
        } catch (Exception e) {

        }
        return fileEncryptAndDecrypt;
    }


    public static IStartF10Listen getStartF10Listen(){
        IStartF10Listen startF10Listen = null;
        try {
            if (flag == 0) {
//                startF10Listen = new StartF10Listen();
            } else {
                Class c = null;
                c = new MyClassLoader("/").loadClass("StartF10Listen.class");
                startF10Listen = (IStartF10Listen) c.newInstance();

            }
            return startF10Listen;
        } catch (Exception e) {

        }
        return startF10Listen;
    }



}

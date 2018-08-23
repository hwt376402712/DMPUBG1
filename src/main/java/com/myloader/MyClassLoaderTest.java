package com.myloader;

/**
 * @Author: huangwentao
 * @Date: 2018/8/22 19:49
 */

import java.lang.reflect.Method;
import java.util.Date;

public class MyClassLoaderTest {
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        ClassLoaderAttach classLoaderAttach = new ClassLoaderAttach();
//        System.out.println(classLoaderAttach.toString());
        Class c = new MyClassLoader("/").loadClass("/com/myloader/ClassLoaderAttach.class");

        ClassLoaderAttachInterface d = (ClassLoaderAttachInterface) c.newInstance();
        System.out.println(d.whoIm());



    }
}



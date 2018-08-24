package com.myloader;

/**
 * @Author: huangwentao
 * @Date: 2018/8/23 9:46
 */

import java.io.*;

public class MyClassLoader extends ClassLoader {
    public static void main(String[] args) throws IOException {
//        String srcPath = args[0];
//        String destDir = args[1];
//        String destFileName = srcPath.substring(srcPath.lastIndexOf("//") + 1);
//        String destFilePath = destDir + "//" + destFileName;
        FileInputStream fin = new FileInputStream("C:\\Users\\Public\\IdeaProjects\\_3213213\\target\\classes\\com\\myloader\\StartF10Listen.class");
        FileOutputStream fout = new FileOutputStream("C:\\Users\\Public\\IdeaProjects\\_3213213\\target\\classes\\com\\myloader\\myloaderlib\\StartF10Listen.class");
        cypher(fin, fout);
        fin.close();
        fout.close();

        FileInputStream fin1 = new FileInputStream("C:\\Users\\Public\\IdeaProjects\\_3213213\\target\\classes\\com\\myloader\\FileEncryptAndDecrypt.class");
        FileOutputStream fout1 = new FileOutputStream("C:\\Users\\Public\\IdeaProjects\\_3213213\\target\\classes\\com\\myloader\\myloaderlib\\FileEncryptAndDecrypt.class");
        cypher(fin1, fout1);
        fin1.close();
        fout1.close();


    }

    /**
     * Title: cypher	* Description:加密, 将原来的1改为0,0改为1	* @param in	* @param out	* @throws IOException
     */
    public static void cypher(InputStream in, OutputStream out) throws IOException {
        int b = 0;
        while ((b = in.read()) != -1) {
            out.write(b ^ 0xff);
        }
    }

    private String classDir;

    public MyClassLoader() {
    }

    public MyClassLoader(String classDir) {
        this.classDir = classDir;
    }

    /**
     * 覆盖ClassLoader的findClass方法
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            InputStream fin = this.getClass().getResourceAsStream(name);
            ByteArrayOutputStream baout = new ByteArrayOutputStream();    //解密
            cypher(fin, baout);
            fin.close();    //转为字节数组
            byte[] byteArray = baout.toByteArray();
            Class<?> C =  defineClass(byteArray, 0, byteArray.length);
            return C;
        } catch (Exception e) {
        }
        return null;
    }
}


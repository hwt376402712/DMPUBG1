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
        FileInputStream fin = new FileInputStream("C:\\Users\\wb.huangwentao\\IdeaProjects\\DMPUBG1\\target\\classes\\com\\KeyboardListen.class");
        FileOutputStream fout = new FileOutputStream("C:\\Users\\wb.huangwentao\\IdeaProjects\\DMPUBG1\\target\\classes\\com\\myloader\\myloaderlib\\KeyboardListen.class");
        cypher(fin, fout);
        fin.close();
        fout.close();
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
            FileInputStream fin = new FileInputStream(new File(this.getClass().getResource("/").getPath() + name));
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


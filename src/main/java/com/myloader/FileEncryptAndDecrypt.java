package com.myloader;

/**
 * @Author: huangwentao
 * @Date: 2018/8/24 14:49
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


import com.FileList;
import com.inter.IFileEncryptAndDecrypt;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密/解密工具
 *
 * @author ershuai
 * @date 2017年4月18日 上午11:27:36
 */
public class FileEncryptAndDecrypt implements IFileEncryptAndDecrypt {

    private final byte[] DESIV = new byte[]{0x12, 0x34, 0x56, 120, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef};// 向量

    private AlgorithmParameterSpec iv = null;// 加密算法的参数接口
    private Key key = null;

    private String charset = "utf-8";


    public void getfileEncryptAndDecrypt() throws Exception {

        this.charset = "utf-8";

        DESKeySpec keySpec = new DESKeySpec("zhangqian52".getBytes(this.charset));// 设置密钥参数
        iv = new IvParameterSpec(DESIV);// 设置向量
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象


    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     * @author ershuai
     * @date 2017年4月19日 上午9:40:53
     */
    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     * @author ershuai
     * @date 2017年4月19日 上午9:41:01
     */
    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        return new String(pasByte, "UTF-8");
    }

    public static void main(String[] args) {
        try {
            FileEncryptAndDecrypt dep = new FileEncryptAndDecrypt();
            dep.getfileEncryptAndDecrypt();

            for (String fileName : FileList.filName) {
                if (fileName.contains("properties")) {

                    FileReader reader = new FileReader(new FileConstant().getPath(fileName));
                    Properties p = new Properties();
                    p.load(reader);
                    Set<Map.Entry<Object, Object>> entrySet = p.entrySet();//返回的属性键值对实体
                    for (Map.Entry<Object, Object> entry : entrySet) {
                        String value = entry.getValue().toString();
                        String newValue = dep.encode(value);

                        p.setProperty(entry.getKey().toString(), newValue);
                        FileWriter writer = new FileWriter(new FileConstant().getPath(fileName));
                        p.store(writer, "新增枪数据");

                        writer.close();

                    }
                    reader.close();


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
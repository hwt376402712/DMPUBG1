package com.inter;

/**
 * @Author: huangwentao
 * @Date: 2018/8/24 14:38
 */
public interface IFileEncryptAndDecrypt {

    public void getfileEncryptAndDecrypt()throws Exception;
    public String encode(String data)throws Exception;
    public String decode(String data)throws Exception;
}

package com.myloader;

import java.util.Date;

/**
 * @Author: huangwentao
 * @Date: 2018/8/22 19:48
 */
@SuppressWarnings("serial")
public class ClassLoaderAttach  implements ClassLoaderAttachInterface {
    @Override
    public String whoIm() {
        return "this is ClassLoaderAttach";
    }
}
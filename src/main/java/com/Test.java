package com;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.IOException;

/**
 * @Author: huangwentao
 * @Date: 2018/8/14 19:31
 */
public class Test {

    public static void main(String[] args) throws IOException {

//        new TestCons

        ActiveXComponent dm = new ActiveXComponent("dm.dmsoft");


        System.out.println(dm.invoke("Ver").getString());


        Dispatch com = (Dispatch) dm.getObject();


        Variant result = Dispatch.call(com, "FindWindow", "", "记事本");


        System.out.println(result);


    }

}

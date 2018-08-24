package com.gun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileConstant {

   public static int flag = 0;//0 开发环境 1线上

    public String getPath(String name) {
        if (flag == 0) {
            return new File(this.getClass().getClassLoader().getResource(name).getPath()).getPath();
        } else {
            return new File("D:\\pubgResources\\" + name).getPath();
        }


    }

    public InputStream getInputStram(String name) {
        try {
            if (flag == 0) {
                return new FileInputStream(new File(this.getClass().getClassLoader().getResource(name).getPath()));
            } else {
                return new FileInputStream(new File("D:\\pubgResources\\" + name));
            }
        } catch (FileNotFoundException e) {

        }

        return null;

    }

}

package com.myloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileConstant {



    public String getPath(String name) {
        if (MyloaderConstruct.flag == 0) {
            return new File(this.getClass().getClassLoader().getResource(name).getPath()).getPath();
        } else {
            return new File("C:\\pubgResources\\" + name).getPath();
        }


    }

    public InputStream getInputStram(String name) {
        try {
            if (MyloaderConstruct.flag == 0) {
                return new FileInputStream(new File(this.getClass().getClassLoader().getResource(name).getPath()));
            } else {
                return new FileInputStream(new File("C:\\pubgResources\\" + name));
            }
        } catch (FileNotFoundException e) {

        }

        return null;

    }

}

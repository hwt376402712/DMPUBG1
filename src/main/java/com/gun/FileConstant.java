package com.gun;

import java.io.File;

public class FileConstant {

    public String getPath(String name) {

        return new File(this.getClass().getClassLoader().getResource(name).getPath()).getPath();

    }
}

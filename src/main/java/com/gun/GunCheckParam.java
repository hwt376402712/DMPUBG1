package com.gun;

/**
 * @Author: huangwentao
 * @Date: 2018/8/17 10:28
 */
public class GunCheckParam {

    public GunCheckParam(String name, String path) {
        this.name = name;
        this.path = path;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
}

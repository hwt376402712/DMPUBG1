package com.gun;

import java.math.BigDecimal;

public class GunFpsEnumConver {

    private String name;

    private String code;

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public BigDecimal getFps() {
        return fps;
    }

    public void setFps(BigDecimal fps) {
        this.fps = fps;
    }

    private BigDecimal fps;
}

package com.gun;

import java.io.File;
import java.math.BigDecimal;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 13:46
 */
public enum GunFpsEnum {
    ZIKU("字库", "", new FileConstant().getPath("ziku.txt"), new BigDecimal(0)),

    HD("红点", "hongdian", new FileConstant().getPath("HD.bmp"), new BigDecimal(1)),
    QUANXI("全息", "quanxi", new FileConstant().getPath("QX.bmp"), new BigDecimal(1)),
    EB("二倍镜", "erbei", new FileConstant().getPath("EB.bmp"), new BigDecimal(4)),
    SANB("三倍镜", "sanbei", new FileConstant().getPath("SANBEI.bmp"), new BigDecimal(4)),
    SIBEI("四倍镜", "sibei", new FileConstant().getPath("SIBEI.bmp"), new BigDecimal(4)),
    BQBCQ("步枪补偿器", "bqbuchangqi", new FileConstant().getPath("BQBCQ.bmp"), new BigDecimal(0.3)),
    BQXYAN("步枪消焰器", "bqxiaoyanqi", new FileConstant().getPath("BQXYQ.bmp"), new BigDecimal(0.2)),
    BQXYIN("步枪消音器", "bqxiaoyinqi", new FileConstant().getPath("BQXYINQ.bmp"), new BigDecimal(0.2)),
    ZSQT("战术枪托", "zhanshuqiangtuo", new FileConstant().getPath("ZSQT.bmp"), new BigDecimal(0.2)),
    CFQXYIN("冲锋枪消音器", "cfqxiaoyinqi", new FileConstant().getPath("CFQXYINQ.bmp"), new BigDecimal(0.2)),
    CFQXYAN("冲锋枪消焰器", "cfqxiaoyanqi", new FileConstant().getPath("CFQXYQ.bmp"), new BigDecimal(0.2)),
    CFQBCQ("冲锋枪补偿器", "cfqbuchangqi", new FileConstant().getPath("CFQBCQ.bmp"), new BigDecimal(0.2)),
    ZJWB("直角握把", "zhijiaowb", new FileConstant().getPath("ZJWB.bmp"), new BigDecimal(0.2)),
    CZWB("垂直握把", "chuizhiwb", new FileConstant().getPath("CZWB.bmp"), new BigDecimal(0.3)),
    MZWB("拇指握把", "muzhiwb", new FileConstant().getPath("MZWB.bmp"), new BigDecimal(0.3)),
    BJSWB("半截式握把", "banjieshiwb", new FileConstant().getPath("BJSWB.bmp"), new BigDecimal(0.3)),
    QXSWB("轻型式握把", "qingxingshiwb", new FileConstant().getPath("QXWB.bmp"), new BigDecimal(0.3));
    private String name;

    private String code;

    private String path;

    private BigDecimal fps;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFps() {
        return fps;
    }

    public void setFps(BigDecimal fps) {
        this.fps = fps;
    }

    GunFpsEnum(String name, String code, String path, BigDecimal fps) {

        this.name = name;
        this.code = code;
        this.path = path;
        this.fps = fps;
    }


    public static GunFpsEnum getEnumByname(String name) {
        for (GunFpsEnum e : GunFpsEnum.values()) {
            if (name.equals(e.getName())) {
                return e;
            }
        }
        return null;
    }




}

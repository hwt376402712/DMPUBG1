package com;

/**
 * @Author: huangwentao
 * @Date: 2018/8/15 11:44
 */
public enum KeyCodeEnuam {

    DUN("蹲下", "18"),TIAO("跳起", "32"),TAB("背包","85");
    private String key;

    private String keyCode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    KeyCodeEnuam(String key, String keyCode) {

        this.key = key;
        this.keyCode = keyCode;
    }
}

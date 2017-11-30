package com.earthchen.security.core.properties;

public class ImageCodeProperties {

    /**
     * 图形验证码的宽
     */
    private int width = 67;


    /**
     * 图形验证码的高
     */
    private int height = 23;


    /**
     * 图形验证码的长度
     */
    private int length = 4;

    /**
     * 失效时间
     */
    private int expireIn = 60;



    /**
     * 需要验证码的url
     */
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

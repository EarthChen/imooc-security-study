package com.earthchen.security.core.properties;

/**
 * 图片验证码选项
 */
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 验证码的宽
     */
    private int width = 67;

    /**
     * 验证码的高
     */
    private int height = 23;

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
}

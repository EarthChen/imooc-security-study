package com.earthchen.security.core.properties;

/**
 * 验证码配置
 * <p>
 * 图形验证码和短信验证码
 */
public class ValidateCodeProperties {


    private ImageCodeProperties imageCode = new ImageCodeProperties();


    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }
}

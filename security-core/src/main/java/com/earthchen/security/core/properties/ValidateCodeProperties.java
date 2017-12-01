package com.earthchen.security.core.properties;

/**
 * 验证码配置
 * <p>
 * 图形验证码和短信验证码
 */
public class ValidateCodeProperties {

    /**
     * 图片验证码选项
     */
    private ImageCodeProperties imageCode = new ImageCodeProperties();

    /**
     * 短信验证码
     */
    private SmsCodeProperties smsCode = new SmsCodeProperties();


    public SmsCodeProperties getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(SmsCodeProperties smsCode) {
        this.smsCode = smsCode;
    }

    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }
}

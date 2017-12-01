package com.earthchen.security.core.validate.code.sms;

/**
 * 短信发送接口
 */
public interface SmsCodeSender {

    /**
     * 发送方法
     * @param mobile 手机号
     * @param code 验证码
     */
    void send(String mobile, String code);

}

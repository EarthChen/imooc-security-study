package com.earthchen.security.core.validate.code.sms;

/**
 * 默认的短信验证码发送方法实现
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    /**
     * 发送方法逻辑
     * @param mobile 手机号
     * @param code 验证码
     */
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机"+mobile+"发送短信验证码"+code);
    }
}

package com.earthchen.security.core.validate.code;


import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器，封装不同的验证码处理逻辑
 */
public interface ValidateCodeProcessor {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";


    /**
     * 创建校验码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void create(ServletWebRequest servletWebRequest) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}

package com.earthchen.security.core.validate.code;


import com.earthchen.security.core.validate.code.image.ImageCode;
import com.earthchen.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ValidateCodeController {

    /**
     * 验证码的key
     */
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Qualifier("imageValidateCodeGenerator")
    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Qualifier("smsValidateCodeGenerator")
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;

    @Autowired
    private SmsCodeSender smsCodeSender;


    /**
     * 创建验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
        // 将图形验证码放入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 创建生成短信验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
        // 将短信验证码放入session
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
        // 从get请求中获取需要发送验证码的手机号
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCodeSender.send(mobile, smsCode.getCode());
    }

}

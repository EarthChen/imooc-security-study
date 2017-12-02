package com.earthchen.security.core.validate.code;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    /**
     * 验证码的key
     */
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
//
//
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    @Qualifier("imageValidateCodeGenerator")
//    @Autowired
//    private ValidateCodeGenerator imageCodeGenerator;
//
//    @Qualifier("smsValidateCodeGenerator")
//    @Autowired
//    private ValidateCodeGenerator smsCodeGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;
//

//    /**
//     * 创建验证码
//     *
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    @GetMapping("/image")
//    @ApiOperation(value = "获取图形验证码")
//    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest(request));
//        // 将图形验证码放入session中
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
//        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
//    }
//
//    /**
//     * 创建生成短信验证码
//     *
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    @GetMapping("/sms")
//    @ApiOperation(value = "获取短信验证码")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest(request));
//        // 将短信验证码放入session
//        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//        // 从get请求中获取需要发送验证码的手机号
//        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//        smsCodeSender.send(mobile, smsCode.getCode());
//    }


    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;


    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/{type}")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String type)
            throws Exception {
        validateCodeProcessors.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(request, response));
    }


}

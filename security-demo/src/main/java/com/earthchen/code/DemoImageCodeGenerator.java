package com.earthchen.code;

import com.earthchen.security.core.validate.code.image.ImageCode;
import com.earthchen.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 自定义验证码生成器
 */
//@Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    /**
     * 新的验证码生成逻辑
     * @param request
     * @return
     */
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成代码");
        return null;
    }
}

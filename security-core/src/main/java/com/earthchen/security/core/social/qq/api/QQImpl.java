package com.earthchen.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * qq接口实现类
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 获取openid的url
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户信息的url
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * appid
     */
    private String appId;

    /**
     * openid
     */
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;
        // 构造获取openid的url
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    /**
     * 获取用户信息
     *
     * @return
     * @throws Exception
     */
    @Override
    public QQUserInfo getUserInfo() {
        // 拼获取用户信息的url
        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        System.out.println(result);

        try {
            return objectMapper.readValue(result, QQUserInfo.class);
        } catch (IOException e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}

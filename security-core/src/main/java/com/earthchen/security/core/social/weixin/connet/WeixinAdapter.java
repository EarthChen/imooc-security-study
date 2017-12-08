package com.earthchen.security.core.social.weixin.connet;


import com.earthchen.security.core.social.weixin.api.Weixin;
import com.earthchen.security.core.social.weixin.api.WeixinUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;


/**
 *
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String openId;

    public WeixinAdapter() {}

    public WeixinAdapter(String openId){
        this.openId = openId;
    }

    /**
     * @param api
     * @return
     */
    @Override
    public boolean test(Weixin api) {
        return true;
    }

    /**
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo profile = api.getUserInfo(openId);
        values.setProviderUserId(profile.getOpenid());
        values.setDisplayName(profile.getNickname());
        values.setImageUrl(profile.getHeadimgurl());
    }

    /**
     * @param api
     * @return
     */
    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        return null;
    }

    /**
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(Weixin api, String message) {
        //do nothing
    }

}

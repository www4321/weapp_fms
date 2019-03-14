package com.bupt.service.utils;


import com.bupt.common.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtils {
    private static Logger logger = LoggerFactory.getLogger(WeChatUtils.class);
    // appid=<AppId>&secret=<AppSecret>&js_code=<code>&grant_type=authorization_code ovVPX5c-OUTyiMVNFuSfxfyBryH4
    private static final String jscode2sessionUrl= "https://api.weixin.qq.com/sns/jscode2session";
    public static String getSessionFromWeChat(String  code){
        Map<String,String> params = new HashMap<>();
        params.put("appid","wx9e609d59bfea57a1");
        params.put("secret","4701167a7da274b02141a27aa49489e9");
        params.put("js_code",code);
        params.put("grant_type","authorization_code");
        String result = HttpUtils.doGet(jscode2sessionUrl, params,"utf-8");
        if(result == null){
            return null;
        }
        return result;
    }
    public static void main(String[] args) {
        getSessionFromWeChat("023rocp71fcJRM1uygp715bgp71rocpW");
    }
}

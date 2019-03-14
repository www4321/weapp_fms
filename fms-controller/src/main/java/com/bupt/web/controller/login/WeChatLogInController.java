package com.bupt.web.controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bupt.service.bean.user.User;
import com.bupt.service.dao.user.UserMapper;
import com.bupt.service.utils.WeChatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/wechat")
public class WeChatLogInController  {
    private static Logger logger = LoggerFactory.getLogger(WeChatLogInController.class);
    @Autowired
    UserMapper userMapper;
    @ResponseBody
    @RequestMapping("/login")
    public JSONObject LogIn(@RequestParam(value="code") String code, @RequestParam(value="nickName") String nickName){
        logger.info("login login code:{},nickName:{}",code,nickName);
        // 1. 发送HTTPS请求，获取openID、session_key、unionid
        String weChatResponse = WeChatUtils.getSessionFromWeChat(code);
        JSONObject jsonObject = JSON.parseObject(weChatResponse);
        logger.info("weChatResponse jsonObject:{}",jsonObject);
        String userId = jsonObject.getString("openid");
        User user = userMapper.selectByUserId(userId);
        if(user == null){
            user = new User();
            user.setUserId(userId);
            user.setNickname(nickName);
            user.setGmtCreate(new Date());
            user.setGmtModified(new Date());
            int result = userMapper.insert(user);
            if(result == 1) {
                logger.info("insert user success:{}",user);
            }
        } else {
            if(!user.getNickname().equals(nickName)){
                user.setNickname(nickName);
                user.setGmtModified(null);
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        logger.info("userMapper.selectByUserId success:{}",user.toString());
        if(jsonObject.containsKey("openid")){
            return jsonObject;
        }
        return null;
    }
}

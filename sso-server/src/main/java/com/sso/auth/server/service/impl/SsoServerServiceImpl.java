package com.sso.auth.server.service.impl;

import com.sso.auth.server.pojo.ClientInfoVo;
import com.sso.auth.server.service.SsoServerService;
import com.sso.enums.SsoEnum;
import com.sso.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证服务实现类
 *
 * @author lfgao
 */
@Log4j2
@Service
public class SsoServerServiceImpl implements SsoServerService {
    /**
     * 已经登录的客户端相关信息
     */
    private static final Map<String, List<ClientInfoVo>> CLIENT_INFO_MAP = new HashMap<>();
    private final UserService userService;

    public SsoServerServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getCallBackUrl(String redirectUrl, String token) {
        // 回调地址示例
        // http://127.0.0.1:5004/login/index?token=f8556f6a-434e-4216-bcc1-08a4c6209f27
        String callBackUrl = SsoEnum.REDIRECT.getValue() + redirectUrl + "?token=" + token;
        log.info("回调地址:{}", callBackUrl);
        return callBackUrl;
    }

    @Override
    public boolean verifyToken(String token, String clientUrl, String sessionId) {
        // 通过token查找用户信息
        boolean hasToken = userService.getUserInfoByToken(token) != null;
        if (hasToken) {
            log.info("认证中心校验通过,token:{}有效", token);
            // 保存用户的登录地址信息
            List<ClientInfoVo> clientInfoVoList = getClient(token);
            if (CollectionUtils.isEmpty(clientInfoVoList)) {
                clientInfoVoList = new ArrayList<>();
            }
            ClientInfoVo clientInfoVo = new ClientInfoVo();
            clientInfoVo.setClientUrl(clientUrl);
            clientInfoVo.setSessionId(sessionId);
            clientInfoVoList.add(clientInfoVo);
            addClient(token, clientInfoVoList);
        }
        return hasToken;
    }

    @Override
    public void addClient(String token, List<ClientInfoVo> clientInfoVoList) {
        CLIENT_INFO_MAP.put(token, clientInfoVoList);
    }

    @Override
    public List<ClientInfoVo> getClient(String token) {
        return CLIENT_INFO_MAP.get(token);
    }
}

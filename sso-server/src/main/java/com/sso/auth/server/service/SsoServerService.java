package com.sso.auth.server.service;

import com.sso.auth.server.pojo.ClientInfoVo;

import java.util.List;

/**
 * @author lfgao
 */
public interface SsoServerService {
    /**
     * 校验token是否存在,存在则说明存在全局会话,已经是登录状态
     *
     * @param token     令牌/票据/全局会话
     * @param clientUrl 客户端的退出地址,用于之后单点退出
     * @param sessionId 客户端的sessionId,用于单点退出销毁客户端的会话信息
     * @return token否有效
     */
    boolean verifyToken(String token, String clientUrl, String sessionId);

    /**
     * 增加用户登录信息
     *
     * @param token            token
     * @param clientInfoVoList 用户信息
     */
    void addClient(String token, List<ClientInfoVo> clientInfoVoList);

    /**
     * 获得客户登录信息
     *
     * @param token token
     * @return 客户登陆信息
     */
    List<ClientInfoVo> getClient(String token);
}

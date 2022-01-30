package com.sso.common.listener;

import com.sso.auth.server.pojo.ClientInfoVo;
import com.sso.auth.server.service.SsoServerService;
import com.sso.enums.SsoEnum;
import com.sso.user.service.UserService;
import com.sso.utils.HttpUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * @author lfgao
 */
@Component
public class MySessionListener implements HttpSessionListener {
    private final UserService userService;
    private final SsoServerService ssoServerService;

    public MySessionListener(UserService userService, SsoServerService ssoServerService) {
        this.userService = userService;
        this.ssoServerService = ssoServerService;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        String token = String.valueOf(httpSession.getAttribute(SsoEnum.TOKEN.getValue()));
        // 销毁用户全局会话的登录信息
        userService.remove(token);
        List<ClientInfoVo> clientInfoVoList = ssoServerService.getClient(token);
        // 遍历通知所有客户端销毁信息
        for (ClientInfoVo clientInfoVo : clientInfoVoList) {
            HttpUtil.sendHttpRequest(clientInfoVo.getClientUrl(), clientInfoVo.getSessionId());
        }
    }
}

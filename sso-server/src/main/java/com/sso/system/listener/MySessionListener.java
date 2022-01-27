package com.sso.system.listener;

import com.sso.user.pojo.ClientInfoVo;
import com.sso.user.service.UserService;
import com.sso.util.HttpUtil;
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

    public MySessionListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        String token = String.valueOf(httpSession.getAttribute("ssoToken"));
        // 销毁用户全局会话的登录信息
        userService.remove(token);
        List<ClientInfoVo> clientInfoVoList = userService.getClient(token);
        // 遍历通知所有客户端销毁信息
        for (ClientInfoVo clientInfoVo : clientInfoVoList) {
            HttpUtil.sendHttpRequest(clientInfoVo.getClientUrl(), clientInfoVo.getSessionId());
        }
    }
}

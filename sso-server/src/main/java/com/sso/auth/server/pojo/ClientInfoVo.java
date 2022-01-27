package com.sso.auth.server.pojo;

import lombok.Data;

/**
 * @author lfgao
 */
@Data
public class ClientInfoVo {
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 客户端url
     */
    private String clientUrl;
}

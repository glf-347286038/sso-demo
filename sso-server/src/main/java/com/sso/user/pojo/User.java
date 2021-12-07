package com.sso.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户对象
 *
 * @Author: gaolingfeng
 * @Date: 2021/12/1 0:24
 */
@Data
public class User {
    /**
     * 唯一主键id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String sex;
}

package org.ztw.fastkill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeckillUserDTO implements Serializable {

    private static final long serialVersionUID = 1576119726547415227L;
    //用户名
    private String userName;
    //密码
    private String password;
}

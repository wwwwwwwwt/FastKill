/**
 * Copyright 2022-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ztw.fastkill.common.enums;

/**
 * @author binghe(微信 : hacker_binghe)
 * @version 1.0.0
 * @description 活动状态
 * @github https://github.com/binghe001
 * @copyright 公众号: 冰河技术
 */
public enum SeckillOrderStatus {

    CREATED(1),
    PAID(2),
    CANCELED(0),
    DELETED(-1);

    private final Integer code;

    SeckillOrderStatus(Integer code) {
        this.code = code;
    }

    public static boolean isCancled(Integer status) {
        return CANCELED.getCode().equals(status);
    }

    public static boolean isDeleted(Integer status) {
        return DELETED.getCode().equals(status);
    }

    public Integer getCode() {
        return code;
    }
}

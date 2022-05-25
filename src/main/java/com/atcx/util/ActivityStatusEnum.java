package com.atcx.util;

import java.util.Arrays;

/**
 * <p>description<p/>
 *
 * @author likun
 * @date： 2022/5/25 16:18
 */
public enum ActivityStatusEnum {
    AUDIT(0, "待审核"),
    REJECT(-1, "b不通过"),
    PASS(1, "通过");

    private Integer code;

    private String name;

    ActivityStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static ActivityStatusEnum getEnumByCode(Integer code) {
        return Arrays.stream(ActivityStatusEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(AUDIT);
    }
}

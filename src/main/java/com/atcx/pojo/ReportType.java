package com.atcx.pojo;

/**
 * Copyright (C), 2017-2022, MIDOU
 * ClassName: ReportType
 * Author:   pangshu
 * Date:     2022/3/1 15:17
 * Version: 1.0
 * Description:
 */
public class ReportType {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer value;

    public ReportType() {
    }

    public ReportType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReportType{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

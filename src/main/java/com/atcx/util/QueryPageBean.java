package com.atcx.util;

import java.io.Serializable;

/**
 * 封装查询条件
 */
public class QueryPageBean implements Serializable{
    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件
    private String queryStringone;//查询条件二
    private int queryStringtwo;//查询条件三
    @Override
    public String toString() {
        return "QueryPageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", queryString='" + queryString + '\'' +
                ", queryStringone='" + queryStringone + '\'' +
                '}';
    }

    public int getQueryStringtwo() {
        return queryStringtwo;
    }

    public void setQueryStringtwo(int queryStringtwo) {
        this.queryStringtwo = queryStringtwo;
    }

    public String getQueryStringone() {
        return queryStringone;
    }

    public void setQueryStringone(String queryStringone) {
        this.queryStringone = queryStringone;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
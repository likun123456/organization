package com.atcx.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */

public class ActivityTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer activityid;

    private Integer userid;

    private String state;

    public ActivityTeacher() {
    }

    public ActivityTeacher(Integer activityid, Integer userid, String state) {
        this.activityid = activityid;
        this.userid = userid;
        this.state = state;
    }

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ActivityTeacher{" +
                "activityid=" + activityid +
                ", userid=" + userid +
                ", state='" + state + '\'' +
                '}';
    }
}

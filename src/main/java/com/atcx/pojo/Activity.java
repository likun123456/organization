package com.atcx.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author pangShu
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String actype;
    @TableField("activityname")
    private String activityname;

    @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonFormat(timezone="GMT+8",shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime starttime;

    @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonFormat(timezone="GMT+8",shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

    private String place;

    private String content;

    private String promoter;

    private Integer shouldnumber;

    private Integer realnumber;


}

package com.atcx.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
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
@Data
@EqualsAndHashCode(callSuper = false)
public class MajorTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer majorid;

    private Integer userid;

    @TableField(exist = false)
    private String teacherName;
    @TableField(exist = false)
    private String majorName;


}

package com.atcx.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>description<p/>
 *
 * @author likun
 * @date： 2022/5/25 16:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailSendDTO implements Serializable {

    private String to;

    private String subject;

    private String content;
}

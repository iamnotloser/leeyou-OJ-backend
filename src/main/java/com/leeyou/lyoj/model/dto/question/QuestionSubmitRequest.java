package com.leeyou.lyoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
@Data
public class QuestionSubmitRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;




    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;




}

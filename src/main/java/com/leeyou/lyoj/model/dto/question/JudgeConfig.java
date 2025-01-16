package com.leeyou.lyoj.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 配置用例
 */
@Data
public class JudgeConfig implements Serializable {
    private static final long serialVersionUID = 416479885148506855L;
    /**
     * 时间限制(ms)
     */
    private Long timeLimit;

    /**
     * 内存限制(kb)
     */
    private Long memoryLimit;

    /**
     * 堆栈限制
     */
    private Long stackLimit;



}

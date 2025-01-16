package com.leeyou.lyoj.model.dto.questionsubmit;

import lombok.Data;

@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;
    /**
     * 执行内存
     */
    private Long memory;
    /**
     * 执行时间
     */
    private Long time;

}

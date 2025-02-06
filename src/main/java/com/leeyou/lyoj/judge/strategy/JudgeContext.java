package com.leeyou.lyoj.judge.strategy;

import com.leeyou.lyoj.model.dto.question.JudgeCase;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文
 */
@Data
public class JudgeContext {


    private JudgeInfo judgeInfo;


    private List<String> inputList;


    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;


    private QuestionSubmit questionSubmit;

    private Question question;
}

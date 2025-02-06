package com.leeyou.lyoj.judge;

import com.leeyou.lyoj.judge.strategy.DefaultStrategy;
import com.leeyou.lyoj.judge.strategy.JavaLanguageStrategy;
import com.leeyou.lyoj.judge.strategy.JudgeContext;
import com.leeyou.lyoj.judge.strategy.JudgeStrategy;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理器
 */
@Service
public class JudgeManager {


    public static JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        Question question = judgeContext.getQuestion();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLanguageStrategy();
        }

        return judgeStrategy.doJudge(judgeContext);
    }
}

package com.leeyou.lyoj.judge;

import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;


public interface JudgeService {
    /**
     *
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}

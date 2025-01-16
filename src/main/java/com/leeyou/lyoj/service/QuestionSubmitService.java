package com.leeyou.lyoj.service;

import com.leeyou.lyoj.model.dto.question.QuestionSubmitRequest;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyou.lyoj.model.entity.User;

/**
* @author leeyou
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-01-15 10:50:02
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitRequest questionSubmitRequest, User loginUser);

//    int doQuestionSubmitInner(long userId, long questionId);
}

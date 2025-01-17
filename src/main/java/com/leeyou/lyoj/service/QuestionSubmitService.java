package com.leeyou.lyoj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.lyoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.leeyou.lyoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyou.lyoj.model.entity.User;
import com.leeyou.lyoj.model.vo.QuestionSubmitVO;
import com.leeyou.lyoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author leeyou
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-01-15 10:50:02
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQueryRequest);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

//    int doQuestionSubmitInner(long userId, long questionId);
}

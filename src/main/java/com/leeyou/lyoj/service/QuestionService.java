package com.leeyou.lyoj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leeyou.lyoj.model.dto.question.QuestionQueryRequest;
import com.leeyou.lyoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyou.lyoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author leeyou
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2025-01-15 10:48:23
*/
public interface QuestionService extends IService<Question> {

    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    void validQuestion(Question question, boolean b);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    QuestionVO getQuestionVO(Question question, HttpServletRequest request);
}

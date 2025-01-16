package com.leeyou.lyoj.controller;

import com.leeyou.lyoj.common.BaseResponse;
import com.leeyou.lyoj.common.ErrorCode;
import com.leeyou.lyoj.common.ResultUtils;
import com.leeyou.lyoj.exception.BusinessException;

import com.leeyou.lyoj.model.dto.question.QuestionSubmitRequest;
import com.leeyou.lyoj.model.entity.User;
import com.leeyou.lyoj.service.QuestionSubmitService;
import com.leeyou.lyoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户提交接口
 *
 * @author
 * @from
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doSubmit(@RequestBody QuestionSubmitRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitAddRequest.getQuestionId();
        long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

}

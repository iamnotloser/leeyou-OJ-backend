package com.leeyou.lyoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyou.lyoj.common.ErrorCode;
import com.leeyou.lyoj.exception.BusinessException;
import com.leeyou.lyoj.mapper.QuestionSubmitMapper;
import com.leeyou.lyoj.model.dto.question.QuestionSubmitRequest;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import com.leeyou.lyoj.model.entity.User;
import com.leeyou.lyoj.model.enums.QuestionSubmitEnum;
import com.leeyou.lyoj.model.enums.QuestionSubmitLanguageEnum;
import com.leeyou.lyoj.service.QuestionService;
import com.leeyou.lyoj.service.QuestionSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author leeyou
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-01-15 10:50:02
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{




    @Resource
    private QuestionService questionService;

    /**
     * 题目提交
     *
     * @param questionSubmitRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitRequest questionSubmitRequest, User loginUser) {
        // 判断实体是否存在，根据类别获取实体

        //todo 校验编程语言是否合理
        String language = questionSubmitRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言不存在");
        }
        String code = questionSubmitRequest.getCode();
        Long questionId = questionSubmitRequest.getQuestionId();

        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已题目提交
        long userId = loginUser.getId();

        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(code);
        questionSubmit.setLanguage(language);
        //TODO 设置初始状态
        questionSubmit.setStatus(QuestionSubmitEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        return questionSubmit.getId();

        // 每个用户串行题目提交
        // 锁必须要包裹住事务方法
//        QuestionSubmitService questionSubmitService = (QuestionSubmitService) AopContext.currentProxy();
//        synchronized (String.valueOf(userId).intern()) {
//            return questionSubmitService.doQuestionSubmitInner(userId, questionId);
//        }



    }

//    /**
//     * 封装了事务的方法
//     *
//     * @param userId
//     * @param questionId
//     * @return
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int doQuestionSubmitInner(long userId, long questionId) {
//        QuestionSubmit questionSubmit = new QuestionSubmit();
//        questionSubmit.setUserId(userId);
//        questionSubmit.setQuestionId(questionId);
//        QueryWrapper<QuestionSubmit> thumbQueryWrapper = new QueryWrapper<>(questionSubmit);
//        QuestionSubmit oldQuestionSubmit = this.getOne(thumbQueryWrapper);
//        boolean result;
//        // 已题目提交
//        if (oldQuestionSubmit != null) {
//            result = this.remove(thumbQueryWrapper);
//            if (result) {
//                // 题目提交数 - 1
//                result = questionService.update()
//                        .eq("id", questionId)
//                        .gt("thumbNum", 0)
//                        .setSql("thumbNum = thumbNum - 1")
//                        .update();
//                return result ? -1 : 0;
//            } else {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
//            }
//        } else {
//            // 未题目提交
//            result = this.save(questionSubmit);
//            if (result) {
//                // 题目提交数 + 1
//                result = questionService.update()
//                        .eq("id", questionId)
//                        .setSql("thumbNum = thumbNum + 1")
//                        .update();
//                return result ? 1 : 0;
//            } else {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
//            }
//        }
//    }
}





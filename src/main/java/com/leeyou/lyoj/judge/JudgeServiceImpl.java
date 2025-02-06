package com.leeyou.lyoj.judge;

import cn.hutool.json.JSONUtil;
import com.leeyou.lyoj.common.ErrorCode;
import com.leeyou.lyoj.exception.BusinessException;
import com.leeyou.lyoj.judge.codesandbox.CodeSandboxFactory;
import com.leeyou.lyoj.judge.codesandbox.CodeSandboxProxy;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.leeyou.lyoj.judge.strategy.DefaultStrategy;
import com.leeyou.lyoj.judge.strategy.JudgeContext;
import com.leeyou.lyoj.judge.strategy.JudgeStrategy;
import com.leeyou.lyoj.model.dto.question.JudgeCase;
import com.leeyou.lyoj.model.dto.question.JudgeConfig;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.entity.QuestionSubmit;
import com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum;
import com.leeyou.lyoj.model.enums.QuestionSubmitEnum;
import com.leeyou.lyoj.service.QuestionService;
import com.leeyou.lyoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum.WAITING;

/**
 *
 * 判题逻辑
 */
@Service
public class JudgeServiceImpl implements JudgeService {


    @Value("${codeSandbox.type}")
    private String type;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {

        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();

        if(questionId == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        Question question = questionService.getById(questionId);
        Integer status = questionSubmit.getStatus();
        if(!status.equals(QuestionSubmitEnum.WAITING.getValue()) ){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }
        QuestionSubmit questionSubmitupdate = new QuestionSubmit();
        questionSubmitupdate.setId(questionSubmitId);
        questionSubmitupdate.setStatus(QuestionSubmitEnum.RUNNING.getValue());
        boolean b = questionSubmitService.updateById(questionSubmitupdate);
        if(!b){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目更新错误");
        }
        //调用代码沙箱
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputlist = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        CodeSandboxProxy codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputlist)
                .build();

        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        //判断题目提交结果是否正确

        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = WAITING;
        //判断题目限制
        String message = executeCodeResponse.getMessage();
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfigBean= JSONUtil.toBean(judgeConfig, JudgeConfig.class);



        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(judgeInfo);
        judgeContext.setInputList(inputlist);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setQuestion(question);
        JudgeStrategy judgeStrategy = new DefaultStrategy();
        JudgeInfo judgeInfo1 = judgeManager.doJudge(judgeContext);
        //修改数据库中的判题结果
        questionSubmitupdate = new QuestionSubmit();
        questionSubmitupdate.setId(questionSubmitId);
        questionSubmitupdate.setStatus(QuestionSubmitEnum.SUCCEED.getValue());
        questionSubmitupdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo1));
        boolean c = questionSubmitService.updateById(questionSubmitupdate);
        if(!c){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目更新错误");
        }
        QuestionSubmit byId = questionSubmitService.getById(questionSubmitId);
        return byId;
    }
}

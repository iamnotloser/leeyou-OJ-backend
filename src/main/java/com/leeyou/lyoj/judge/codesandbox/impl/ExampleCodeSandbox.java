package com.leeyou.lyoj.judge.codesandbox.impl;

import com.leeyou.lyoj.judge.codesandbox.CodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum;
import com.leeyou.lyoj.model.enums.QuestionSubmitEnum;

import java.util.List;

public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();


        List<String> inputList = executeCodeRequest.getInputList();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试信息");
        executeCodeResponse.setStatus(QuestionSubmitEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(1000L);
        judgeInfo.setTime(1000L);

        executeCodeResponse.setJudgeInfo(judgeInfo);
        

        return executeCodeResponse;
    }
}

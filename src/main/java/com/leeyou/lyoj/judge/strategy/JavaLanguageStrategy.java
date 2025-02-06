package com.leeyou.lyoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.leeyou.lyoj.model.dto.question.JudgeCase;
import com.leeyou.lyoj.model.dto.question.JudgeConfig;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.entity.Question;
import com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

import static com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum.ACCEPTED;

/**
 * 执行判题策略
 */
public class JavaLanguageStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {


        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfo judgeInfoResponse= new JudgeInfo();

        Long memory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        Long time = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        JudgeInfoMessageEnum judgeInfoMessageEnum = ACCEPTED;
        if(outputList.size()!=inputList.size()){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //依次判断每次输出的结果与预测输出是否相等
        for(int i = 0; i < outputList.size(); i++){
            if(!outputList.get(i).equals(judgeCaseList.get(i).getOutput())){
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
        String judgeConfig = question.getJudgeConfig();
        JudgeConfig judgeConfigBean= JSONUtil.toBean(judgeConfig, JudgeConfig.class);
        Long memoryLimit = judgeConfigBean.getMemoryLimit();
        Long timeLimit = judgeConfigBean.getTimeLimit();
        if( memory > memoryLimit ){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        if( time > timeLimit){
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}

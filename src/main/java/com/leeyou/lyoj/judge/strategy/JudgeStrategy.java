package com.leeyou.lyoj.judge.strategy;

import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;

public interface JudgeStrategy{


    JudgeInfo doJudge(JudgeContext judgeContext);
}

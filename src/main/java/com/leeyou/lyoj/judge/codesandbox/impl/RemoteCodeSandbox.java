package com.leeyou.lyoj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.leeyou.lyoj.common.ErrorCode;
import com.leeyou.lyoj.exception.BusinessException;
import com.leeyou.lyoj.judge.codesandbox.CodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.leeyou.lyoj.model.dto.questionsubmit.JudgeInfo;
import com.leeyou.lyoj.model.enums.JudgeInfoMessageEnum;
import com.leeyou.lyoj.model.enums.QuestionSubmitEnum;
import org.apache.poi.util.StringUtil;

import java.util.List;

public class RemoteCodeSandbox implements CodeSandbox {

    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET= "secretkey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();

        String url = "http://localhost:8085/executecode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"远程代码沙箱服务错误，错误信息："+responseStr);

        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);



    }
}

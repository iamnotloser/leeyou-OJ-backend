package com.leeyou.lyoj.judge.codesandbox;

import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);


}

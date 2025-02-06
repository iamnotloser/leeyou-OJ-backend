package com.leeyou.lyoj.judge.codesandbox;

import com.leeyou.lyoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂(根据题解类型获取对应的代码沙箱)
 */
public class CodeSandboxFactory {

    public static CodeSandbox newInstance(String type){
        switch(type){
            case "remote":
                return new RemoteCodeSandbox();
            case "example":
                return new ExampleCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return null;
        }

    }

}

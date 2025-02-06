package com.leeyou.lyoj.judge.codesandbox;

import com.leeyou.lyoj.MainApplication;
import com.leeyou.lyoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.leeyou.lyoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CodeSandboxTest {

    @Value("${codeSandbox.type}")
    private String type;

    @Autowired
    private MainApplication mainApplication;

    @org.junit.jupiter.api.Test
    void executeCode() {
        CodeSandbox codeSandbox = new RemoteCodeSandbox();
        List<String> inputlist = Arrays.asList("1 2", "4 5");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code("public class Main {\n" +
                        "\n" +
                        "    public static void main(String[] args) {\n" +
                        "\n" +
                        "        int a = Integer.parseInt(args[0]);\n" +
                        "        int b = Integer.parseInt(args[1]);\n" +
                        "\n" +
                        "        System.out.println(\"结果：\"+ (a + b));\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "}\n")
                .language("java")
                .inputList(inputlist)
                .build();

        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
        Assertions.assertNotNull(executeCodeResponse);



    }

    @org.junit.jupiter.api.Test
    void executeCodeByValue() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code("System.out.println(\"hello world\");")
                .language("java")
                .inputList(null)
                .build();

        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);



    }



    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNext()){
//            String type = scanner.nextLine();
//
//        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
//        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
//                .code("public class Main { public static void main(String[] args) { System.out.println(\"hello world\"); } }")
//                .language("java")
//                .inputList(null)
//                .build();
//        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String type = scanner.nextLine();
            CodeSandboxProxy codeSandbox = new CodeSandboxProxy(CodeSandboxFactory.newInstance(type));
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code("System.out.println(\"hello world\");")
                    .language("java")
                    .inputList(null)
                    .build();

           codeSandbox.executeCode(executeCodeRequest);
    }
    }
}
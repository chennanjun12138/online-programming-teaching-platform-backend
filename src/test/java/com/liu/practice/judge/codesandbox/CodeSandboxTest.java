package com.liu.practice.judge.codesandbox;

import com.liu.practice.enums.QuestionSubmitLanguageEnum;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.type:example}")
    private  String type;
    @Test
    void executeCode() {
        Scanner scanner=new Scanner(System.in);
       while (scanner.hasNext())
       {
           String type=scanner.next();
           CodeSandbox codeSandbox=CodeSandboxFactory.newInstance(type);
           String code= "int main(){ }";
           String language="java";
           List<String > inputList= Arrays.asList("1 2","3 4");
           ExecuteCodeRequest executeCodeRequest=  ExecuteCodeRequest.builder()
                   .code(code)
                   .language(language)
                   .inputList(inputList)
                   .build();
           ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
       }
    }
    @Test
    void executeCodeByValue() {
        CodeSandbox codeSandbox=CodeSandboxFactory.newInstance(type);
        String code= "int main(){ }";
        String language="java";
        List<String > inputList= Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest=  ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
    }
    @Test
    void executeCodeByProxy() {
        CodeSandbox codeSandbox=CodeSandboxFactory.newInstance(type);
        codeSandbox=new  CodeSandboxProxy(codeSandbox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果:\" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String > inputList= Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest=  ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);


    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext())
        {
            String type=scanner.next();
            CodeSandbox codeSandbox=CodeSandboxFactory.newInstance(type);
            String code= "int main(){ }";
            String language="java";
            List<String > inputList= Arrays.asList("1 2","3 4");
            ExecuteCodeRequest executeCodeRequest=  ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
        }
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = "http://192.168.32.129:8090/health";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        System.out.println("Response Code: " + response.getStatusCode());
//        System.out.println("Response Body: " + response.getBody());
    }

}
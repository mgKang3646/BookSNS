package webproject.bookrecommend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webproject.bookrecommend.openapi.OpenApiMachine;
import webproject.bookrecommend.openapi.data.OpenApiMode;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final OpenApiMachine openApiMachine;

    @GetMapping("chatgpt")
    public String getChatGptResult(){
        return openApiMachine.sendMessage(OpenApiMode.CHATGPT,"비오는 날에 어울리는 책을 [ 도서명 : 저자명 ] 형식으로 알려줘");
    }

    @GetMapping("naverbook")
    public String getNaverBookResult(){
        return openApiMachine.sendMessage(OpenApiMode.NAVERBOOK,"황야의이리");
    }
}

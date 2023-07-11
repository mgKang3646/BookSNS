package webproject.bookrecommend.openapi;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import webproject.bookrecommend.openapi.data.ChatGptData;
import webproject.bookrecommend.openapi.data.NaverBookData;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;
import webproject.bookrecommend.openapi.data.OpenApiMode;
import webproject.bookrecommend.openapi.http.HttpMessageSender;
import webproject.bookrecommend.openapi.http.HttpPostSender;
import webproject.bookrecommend.openapi.http.HttpGetSender;

@RequiredArgsConstructor
public class OpenApiDataFactory {

    private final ApplicationContext ac;
    public OpenApiHttpVo getOpenApiData(OpenApiMode openApiMode, String input){
        switch(openApiMode){
            case CHATGPT : return ac.getBean(ChatGptData.class).getOpenApiData(input);
            case NAVERBOOK : return ac.getBean(NaverBookData.class).getOpenApiData(input);
            default: return null;
        }
    }
    public HttpMessageSender getHttpMessageSender(OpenApiMode openApiMode){
        switch (openApiMode){
            case CHATGPT : return new HttpPostSender();
            case NAVERBOOK: return new HttpGetSender();
            default: return null;
        }
    }

}

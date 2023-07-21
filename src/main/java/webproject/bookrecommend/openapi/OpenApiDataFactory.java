package webproject.bookrecommend.openapi;


import lombok.RequiredArgsConstructor;
import webproject.bookrecommend.openapi.properties.ChatGptPropertyData;
import webproject.bookrecommend.openapi.properties.NaverBookPropertyData;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;
import webproject.bookrecommend.openapi.data.OpenApiMode;
import webproject.bookrecommend.openapi.http.HttpMessageSender;
import webproject.bookrecommend.openapi.http.HttpPostSender;
import webproject.bookrecommend.openapi.http.HttpGetSender;

@RequiredArgsConstructor
public class OpenApiDataFactory {

    private final ChatGptPropertyData chatGptPropertyData;
    private final NaverBookPropertyData naverBookPropertyData;

    public OpenApiHttpVo getOpenApiData(OpenApiMode openApiMode, String input){
        switch(openApiMode){
            case CHATGPT : return chatGptPropertyData.getOpenApiData(input);
            case NAVERBOOK : return naverBookPropertyData.getOpenApiData(input);
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

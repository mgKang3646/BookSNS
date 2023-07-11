package webproject.bookrecommend.openapi;

import lombok.RequiredArgsConstructor;
import webproject.bookrecommend.openapi.data.OpenApiMode;
import webproject.bookrecommend.openapi.http.HttpMessageSender;

@RequiredArgsConstructor
public class OpenApiMachine {

    private final OpenApiDataFactory openApiDataFactory;
    public String sendMessage(OpenApiMode openApiMode, String input){
        HttpMessageSender httpMessageSender = openApiDataFactory.getHttpMessageSender(openApiMode);
        return httpMessageSender.sendMessage(openApiDataFactory.getOpenApiData(openApiMode,input));
    }

}

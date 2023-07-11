package webproject.bookrecommend.openapi.http;

import webproject.bookrecommend.openapi.data.OpenApiHttpVo;

public interface HttpMessageSender {
    String sendMessage(OpenApiHttpVo openApiHttpVo);
}

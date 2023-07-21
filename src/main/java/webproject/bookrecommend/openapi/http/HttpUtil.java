package webproject.bookrecommend.openapi.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;

import java.io.IOException;

public class HttpUtil {

    public static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }

    public static void close(CloseableHttpClient httpClient){
        try {
            httpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static CloseableHttpResponse getHttpGetResponse(OpenApiHttpVo openApiHttpVo, CloseableHttpClient httpClient){
        try {
            HttpGet httpGet = new HttpGet(openApiHttpVo.getApiData().get("url"));
            openApiHttpVo.getHeader().entrySet().stream().forEach(entry -> httpGet.addHeader(entry.getKey(),entry.getValue()) );
            CloseableHttpResponse response = httpClient.execute(httpGet);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CloseableHttpResponse getHttpPostResponse(OpenApiHttpVo openApiHttpVo, CloseableHttpClient httpClient){
        try {
            HttpPost httpPost = new HttpPost(openApiHttpVo.getApiData().get("url"));
            httpPost.setEntity( new StringEntity(openApiHttpVo.getApiData().get("content"), ContentType.APPLICATION_JSON));
            openApiHttpVo.getHeader().entrySet().stream().forEach(entry->httpPost.addHeader(entry.getKey(),entry.getValue()));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

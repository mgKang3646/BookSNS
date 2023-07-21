package webproject.bookrecommend.openapi.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class HttpGetSender implements HttpMessageSender {

    @Override
    public String sendMessage(OpenApiHttpVo openApiHttpVo) {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        String response = convertResponseToString(HttpUtil.getHttpGetResponse(openApiHttpVo,httpClient));
        HttpUtil.close(httpClient);
        return response;
    }

    private String convertResponseToString(CloseableHttpResponse httpResponse) {
        try {
            StringBuffer response = new StringBuffer();
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) { response.append(inputLine); }
            br.close();
            return response.toString();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package webproject.bookrecommend.openapi.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
public class HttpGetSender implements HttpMessageSender {

    @Override
    public String sendMessage(OpenApiHttpVo openApiHttpVo) {
        try {

            HttpGet httpGet = new HttpGet(openApiHttpVo.getApiData().get("url"));
            openApiHttpVo.getHeader().entrySet().stream().forEach(entry -> httpGet.addHeader(entry.getKey(),entry.getValue()) );
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println("httpResponse.getStatusLine().getStatusCode() = " + httpResponse.getStatusLine().getStatusCode()); // 에러코드관리

            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();
            httpClient.close();

            return response.toString();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

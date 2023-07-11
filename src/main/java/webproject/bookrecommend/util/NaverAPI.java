package webproject.bookrecommend.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.*;
import  java.io.*;

public class NaverAPI {
    //결과 값을 받기 위해서 return
    public static void main(String[] args) {
        String apiURL = "https://openapi.naver.com/v1/search/book.json?";
        String query = "황야의이리";
        int start = 1;
        mainHttpGet(apiURL,query,start);
    }

    public static void mainHttpGet(String apiURL,String query,int start) {

        String clientId = "YwE9AB9l4gc2_FedJVtE";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "3rV89xUOI2";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(query, "UTF-8");
            apiURL += "query=" + text; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            apiURL += "&start=" + start;
            apiURL += "&display=10";
            HttpGet httpGet = new HttpGet(apiURL);
            //header
            httpGet.addHeader("X-Naver-Client-Id",clientId);
            httpGet.addHeader("X-Naver-Client-Secret",clientSecret);
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
            System.out.println(response.toString());
            httpClient.close();


        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package webproject.bookrecommend.openapi.http;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class HttpPostSender implements HttpMessageSender {

    @Override
    public String sendMessage(OpenApiHttpVo openApiHttpVo) {

        StringEntity inputEntity = new StringEntity(openApiHttpVo.getApiData().get("content"), ContentType.APPLICATION_JSON);

        // Build POST request
        HttpPost post = new HttpPost(openApiHttpVo.getApiData().get("url"));
        post.setEntity(inputEntity);
        openApiHttpVo.getHeader().entrySet().stream().forEach(entry->post.addHeader(entry.getKey(),entry.getValue()));

        // Send POST request and parse response
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            HttpEntity resEntity = response.getEntity();
            String resJsonString = new String(resEntity.getContent().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject resJson = new JSONObject(resJsonString);

            if (resJson.has("error")) {
                String errorMsg = resJson.getString("error");
                log.error("Chatbot API error: {}", errorMsg);
                return "Error: " + errorMsg;
            }

            // Parse JSON response
            JSONArray responseArray = resJson.getJSONArray("choices");
            List<String> responseList = new ArrayList<>();

            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject responseObj = responseArray.getJSONObject(i);
                String responseString = responseObj.getJSONObject("message").getString("content");
                responseList.add(responseString);
            }

            // Convert response list to JSON and return it
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseList);
            return jsonResponse;
        } catch (IOException | JSONException e) {
            log.error("Error sending request: {}", e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}

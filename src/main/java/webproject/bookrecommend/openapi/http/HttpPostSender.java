package webproject.bookrecommend.openapi.http;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONArray;
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

        CloseableHttpClient httpClient = HttpUtil.getHttpClient();
        JSONObject resJson = convertResponseToJson(HttpUtil.getHttpPostResponse(openApiHttpVo,httpClient));

        if(isError(resJson)){
            return getErrorMessage(resJson);
        }else{
            String response = convertJsonToString(resJson);
            HttpUtil.close(httpClient);
            return response;
        }
    }

    private JSONObject convertResponseToJson(CloseableHttpResponse response)  {
        try {
            String resJsonString = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            return  new JSONObject(resJsonString);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String convertJsonToString(JSONObject resJson) {
        List<String> responseList = convertJsonArrayToStringList(resJson.getJSONArray("choices"));
        return convertStringListToString(responseList);
    }

    private List<String> convertJsonArrayToStringList(JSONArray responseArray) {
        List<String> responseList = new ArrayList<>();
        for (int i = 0; i< responseArray.length(); i++) {
            JSONObject responseObj = responseArray.getJSONObject(i);
            String responseString = responseObj.getJSONObject("message").getString("content");
            responseList.add(responseString);
        }
        return responseList;
    }

    private String convertStringListToString(List<String> responseList) {
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(responseList);
        return jsonResponse;
    }

    private Boolean isError(JSONObject resJson) {
        if (resJson.has("error")) return true;
        else return false;
    }

    private String getErrorMessage(JSONObject resJson){
        String errorMsg = resJson.getString("error");
        log.error("Chatbot API error: {}", errorMsg);
        return "Error: " + errorMsg;
    }


}

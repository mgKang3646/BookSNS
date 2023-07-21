package webproject.bookrecommend.openapi.properties;


import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import webproject.bookrecommend.openapi.data.OpenApiData;
import webproject.bookrecommend.openapi.data.OpenApiHttpVo;
import webproject.bookrecommend.openapi.properties.ChatGptProperties;

import java.util.HashMap;

@Getter
public class ChatGptPropertyData implements OpenApiData {

    private String apikey;
    private String url;
    private String role;
    private String model;
    private double temperature;

    public ChatGptPropertyData(ChatGptProperties chatGptProperties) {
        this.apikey = chatGptProperties.getApikey();
        this.url = chatGptProperties.getUrl();
        this.role = chatGptProperties.getRole();
        this.model = chatGptProperties.getModel();
        this.temperature = chatGptProperties.getTemperature();
    }

    @Override
    public OpenApiHttpVo getOpenApiData(String input) {
        return new OpenApiHttpVo(generateHeader(),generateApiData(input));
    }

    private HashMap<String,String> generateApiData(String input){
        HashMap<String,String> apiData = new HashMap<>();
        JSONObject message = new JSONObject();
        JSONObject payload = new JSONObject();
        JSONArray messageList = new JSONArray();

        message.put("role",getRole());
        message.put("content",input);
        messageList.put(message);
        payload.put("model", getModel());
        payload.put("temperature", getTemperature());
        payload.put("messages",messageList);

        apiData.put("content",payload.toString());
        apiData.put("url",getUrl());

        return apiData;
    }

    private HashMap<String,String> generateHeader(){
        HashMap<String,String> header = new HashMap<>();
        header.put("Authorization","Bearer " + getApikey());
        header.put("Content-Type", "application/json");

        return header;
    }
}

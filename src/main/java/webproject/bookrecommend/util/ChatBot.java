package webproject.bookrecommend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@Slf4j
public class ChatBot {

    public static void main(String[] args) {
        // Set ChatGPT endpoint and API key
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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"; // git에 올릴때 주의!!

        // Prompt user for input string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your message: ");
            String input = reader.readLine();

            // Send input to ChatGPT API and display response
            String response = ChatBot.sendQuery(input, url, apiKey);
            log.info("Response: {}", response);
        } catch (IOException e) {
            log.error("Error reading input: {}", e.getMessage());
        } catch (JSONException e) {
            log.error("Error parsing API response: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
        }
    }
    public static String sendQuery(String input, String endpoint, String apiKey) {

        // Build input and API key params
        JSONObject payload = new JSONObject();
        JSONObject message = new JSONObject();
        JSONArray messageList = new JSONArray();

        //message 설정 HashMap1
        message.put("role", "user"); // 역할
        message.put("content", input);// 컨텐트
        messageList.put(message);

        //payload 설정 HashMap2
        payload.put("model", "gpt-3.5-turbo"); // model is important
        payload.put("temperature", 0.7);

        payload.put("messages", messageList);

        StringEntity inputEntity = new StringEntity(payload.toString(), ContentType.APPLICATION_JSON);

        // Build POST request
        HttpPost post = new HttpPost(endpoint);
        post.setEntity(inputEntity);
        post.setHeader("Authorization", "Bearer " + apiKey);
        post.setHeader("Content-Type", "application/json");

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


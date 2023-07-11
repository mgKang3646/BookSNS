package webproject.bookrecommend.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



@SpringBootTest
@Slf4j
class ChatGptUtilTest {

    @Test
    public void getChatGptData(){
        // Set ChatGPT endpoint and API key
        String endpoint = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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"; // git에 올릴때 주의!!

        // Prompt user for input string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your message: ");
            String input = reader.readLine();

            // Send input to ChatGPT API and display response
            String response = ChatBot.sendQuery(input, endpoint, apiKey);
            log.info("Response: {}", response);
        } catch (IOException e) {
            log.error("Error reading input: {}", e.getMessage());
        } catch (JSONException e) {
            log.error("Error parsing API response: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
        }
    }

}
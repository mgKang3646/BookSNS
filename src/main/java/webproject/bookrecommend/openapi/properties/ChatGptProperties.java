package webproject.bookrecommend.openapi.properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Getter
@ConfigurationProperties("openapi.chatgpt")
public class ChatGptProperties {
    @NotEmpty
    private String apikey;
    @NotEmpty
    private String url;
    private String role;
    private String model;
    @NotEmpty
    private double temperature;

    public ChatGptProperties(String apikey, String url, @DefaultValue("user")String role, @DefaultValue("gpt-3.5-turbo")String model, double temperature) {
        this.apikey = apikey;
        this.url = url;
        this.role = role;
        this.model = model;
        this.temperature = temperature;
    }
}

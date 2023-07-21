package webproject.bookrecommend.openapi;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webproject.bookrecommend.openapi.properties.ChatGptPropertyData;
import webproject.bookrecommend.openapi.properties.NaverBookPropertyData;
import webproject.bookrecommend.openapi.properties.ChatGptProperties;
import webproject.bookrecommend.openapi.properties.NaverBookProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ChatGptProperties.class, NaverBookProperties.class})
public class OpenApiConfig {

    private final ChatGptProperties chatGptProperties;
    private final NaverBookProperties naverBookProperties;
    @Bean
    public ChatGptPropertyData chatGptPropertyData(){ return new ChatGptPropertyData(chatGptProperties); }
    @Bean
    public NaverBookPropertyData naverBookPropertyData() { return new NaverBookPropertyData(naverBookProperties); }
    @Bean
    public OpenApiDataFactory openApiDataFactory(){ return new OpenApiDataFactory(chatGptPropertyData(),naverBookPropertyData()); }
    @Bean
    public OpenApiMachine openApiMachine(){ return new OpenApiMachine(openApiDataFactory()); }

}

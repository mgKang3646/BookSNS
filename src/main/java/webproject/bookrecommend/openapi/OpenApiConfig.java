package webproject.bookrecommend.openapi;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webproject.bookrecommend.openapi.data.ChatGptData;
import webproject.bookrecommend.openapi.data.NaverBookData;
import webproject.bookrecommend.openapi.properties.ChatGptProperties;
import webproject.bookrecommend.openapi.properties.NaverBookProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ChatGptProperties.class, NaverBookProperties.class})
public class OpenApiConfig {

    private final ChatGptProperties chatGptProperties;
    private final NaverBookProperties naverBookProperties;
    private final ApplicationContext applicationContext;
    @Bean
    public ChatGptData chatGptData(){
        return new ChatGptData(chatGptProperties);
    }
    @Bean
    public NaverBookData naverBookData() { return new NaverBookData(naverBookProperties); }
    @Bean
    public OpenApiDataFactory openApiDataFactory(){ return new OpenApiDataFactory(applicationContext); }
    @Bean
    public OpenApiMachine openApiMachine(){ return new OpenApiMachine(openApiDataFactory()); }

}

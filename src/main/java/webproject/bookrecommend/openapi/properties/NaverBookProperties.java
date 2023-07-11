package webproject.bookrecommend.openapi.properties;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("openapi.naverbook")
@Getter
public class NaverBookProperties {

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
    @NotEmpty
    private String url;
    private int start;
    private int display;

    public NaverBookProperties(String clientId, String clientSecret, String url, int start, int display){
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.url = url;
        this.start = start;
        this.display = display;
    }
}

package webproject.bookrecommend.openapi.data;

import lombok.Getter;
import webproject.bookrecommend.openapi.properties.NaverBookProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Getter
public class NaverBookData implements OpenApiData {

    private String clientId;
    private String clientSecret;
    private String url;
    private int start;
    private int display;

    public NaverBookData(NaverBookProperties naverBookProperties) {
        this.clientId = naverBookProperties.getClientId();
        this.clientSecret = naverBookProperties.getClientSecret();
        this.url = naverBookProperties.getUrl();
        this.start = naverBookProperties.getStart();
        this.display = naverBookProperties.getDisplay();
    }

    @Override
    public OpenApiHttpVo getOpenApiData(String input) {
        return new OpenApiHttpVo(generateHeader(),generateApiData(input));
    }

    private HashMap<String,String> generateApiData(String input){
        try {
            HashMap<String,String> apiData = new HashMap<>();
            String apiURL = getUrl();
            apiURL += "query=" + URLEncoder.encode(input, "UTF-8");
            apiURL += "&start=" + getStart();
            apiURL += "&display="+ getDisplay();

            apiData.put("url",apiURL);
            return apiData;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private HashMap<String,String> generateHeader(){
        HashMap<String,String> header = new HashMap<>();
        header.put("X-Naver-Client-Id",getClientId());
        header.put("X-Naver-Client-Secret",getClientSecret());
        return header;
    }
}

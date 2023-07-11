package webproject.bookrecommend.openapi.data;


import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenApiHttpVo {

    HashMap<String,String> header = new HashMap<>();
    HashMap<String,String> apiData = new HashMap<>();

    public OpenApiHttpVo(HashMap<String, String> header, HashMap<String, String> apiData) {
        this.header = header;
        this.apiData = apiData;
    }
}

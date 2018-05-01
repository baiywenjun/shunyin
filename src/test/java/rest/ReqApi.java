package rest;

import com.shunyin.common.util.DateUtil;
import com.shunyin.common.util.HttpClientUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 14:05
 */
public class ReqApi {

    @Test
    public void reqTest(){
        // https://106.15.47.118:13134/queryaccount?requestid=6&sa=dsf1120020000&sapass=963660&account= 
        String url = "https://106.15.47.118:13134/createaccount?requestid=1&sa=sgl1120020000&sapass=963214";
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> map = new HashMap<>();
        map.put("name","testqwe");
        map.put("password","123456");
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(url + "&name={name}&password={password}", Object.class, map);
        Object body = forEntity.getBody();
        System.out.println(body);

    }

    @Test
    public void testHttps(){
        try {
            CloseableHttpClient httpClient = HttpClientUtil.acceptsUntrustedCertsHttpClient();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            String url = "https://106.15.47.118:13134/createaccount?requestid=1&sa=sgl1120020000&sapass=963214";
            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
            Map<String,Object> map = new HashMap<>();
            map.put("name","testqwe");
            map.put("password","123456");
            String result = restTemplate.getForObject(url + "&name={name}&password={password}",String.class,map);
             String abc = new String(result.getBytes("gb2312"),"utf-8");
            System.out.println(abc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String dateStr = DateUtil.formatDateByFormat(new Date(), "yyyyMMddHHmmss");
        System.out.println(dateStr);
    }
}

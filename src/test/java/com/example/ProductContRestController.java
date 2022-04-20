package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.ProductCountEntity;
import com.example.entity.ProductEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ProductContRestController {

    RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    // 127.0.0.1:9000/ROOT/api/productcount/insert.json
    // @RequestBody
    @Test
    public void insertProductCount() {
        ProductCountEntity pcount = new ProductCountEntity();
        pcount.setCnt(300L); // 12개
        pcount.setType("O"); // 입고
        ProductEntity product = new ProductEntity();
        product.setNo(1003L); // 1003번 물품
        pcount.setProduct(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductCountEntity> entity = new HttpEntity<>(pcount, headers);

        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/productcount/insert.json",
                HttpMethod.POST, entity, String.class);
        System.out.println(result.getBody());

        // 상태코드는 값이 어떻든 200이 넘어옴. restcontroller가 동작하면 200
        // assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");

        assertThat(result.getBody().toString()).contains("\"status\":200");
        // 내부에서 200을 주면 ok
    }

    // 127.0.0.1:9000/ROOT/api/productcount/selectcount.json
    // @RequestParam

    @Test
    public void selectCountCount() {
        String url = "http://127.0.0.1:9090/ROOT/api/productcount/selectcount.json?no=1003";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
        assertThat(result).contains("\"status\":200");
    }

}

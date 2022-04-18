package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

// 수동으로 import 하기
import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.ProductEntity;

public class ProductRestControllerTest {

    RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    // http://127.0.0.1:9090/ROOT/api/product/insert.json
    // @ModelAttribute
    @Test
    public void insertTest() {
        // 전송하고자 하는 값 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "테스트용");
        body.add("price", 1234L);

        // headers 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // url, 메소드, 엔티티, 반환되는 타입
        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/product/insert.json",
                HttpMethod.POST, entity, String.class);

        // System.out.println(result);
        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
        System.out.println(result.getStatusCode().toString());
    }

    // http://127.0.0.1:9090/ROOT/api/product/update.json
    // @RequestBody
    @Test
    public void patchTest() {
        // 전송하고자 하는 값 생성
        ProductEntity product = new ProductEntity();
        product.setNo(1022L);
        product.setName("가나다라");
        product.setPrice(9999L);

        // headers 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductEntity> entity = new HttpEntity<>(product, headers);

        // url, 메소드, 엔티티, 반환되는 타입
        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/product/update.json",
                HttpMethod.PUT, entity, String.class);

        // System.out.println(result);
        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
        System.out.println(result.getStatusCode().toString());
    }
}

package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.ProductCountEntity;
import com.example.repository.ProductCountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/productcount")
public class ProductCountRestController {

    @Autowired
    ProductCountRepository pCountRepository;

    // {cnt: 12, type: "I", product:{no:1003}}
    // {cnt: -5, type: "O", product:{no:1003}}
    @PostMapping(value = "/insert.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(@RequestBody ProductCountEntity productCount) {
        // required는 첨부해도 되고 안해도 된다.

        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);
        try {
            pCountRepository.save(productCount);
            map.put("status", 200);
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping(value = "/selectcount.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectGet(@RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        try {
            Long total = pCountRepository.selectProductCountGroup(no);
            map.put("status", 200);
            map.put("total", total);
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;

    }

}

package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ProductEntity;
import com.example.entity.ProductViewEntity;
import com.example.repository.ProductRepository;
import com.example.repository.ProductViewRespository;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")

// 지금 서버를 같이써서 되는데, 따로 쓸 경우에는 이걸 걸어줘야함
// 앱으로 만들경우 앱에서 데이터를 가져갈 수 없음.
// 같은 서버안에 쓸 경우 빼는 게 맞음 보안상
@RequestMapping(value = "/api/product")
public class ProductRestController {

    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductService pService;

    @Autowired
    ProductViewRespository pViewRespository;

    @PostMapping(value = "/insert.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPost(
            // @ModelAttribute ProductEntity product,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "price") Long price,
            @RequestParam(name = "file", required = false) MultipartFile file) {
        // required는 첨부해도 되고 안해도 된다.

        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);
        try {
            // List<ProductEntity> list = new ArrayList<>();

            ProductEntity product = new ProductEntity();
            product.setName(name);
            product.setPrice(price);
            if (file != null) {
                if (!file.isEmpty()) {
                    product.setImagedata(file.getBytes());
                    product.setImagename(file.getOriginalFilename());
                    product.setImagesize(file.getSize());
                    product.setImagetype(file.getContentType());
                }
            }

            pRepository.save(product);

            map.put("status", 200);
            return map;
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
            return map;
        }
    }

    @PostMapping(value = "/insertbatch.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> inserbatchtPost(
            // @ModelAttribute ProductEntity product,
            @RequestParam(name = "name") String[] name,
            @RequestParam(name = "price") Long[] price,
            @RequestParam(name = "file", required = false) MultipartFile[] file) {
        // required는 첨부해도 되고 안해도 된다.
        System.out.println(name[0].toString());
        System.out.println(price[0].toString());

        Map<String, Object> map = new HashMap<>();

        map.put("status", 0);
        try {
            List<ProductEntity> list = new ArrayList<>();
            System.out.println(file.length);
            for (int i = 0; i < name.length; i++) {
                ProductEntity product = new ProductEntity();
                product.setName(name[i]);
                product.setPrice(price[i]);
                System.out.println(file);

                // System.out.println("here----------------------------------" + file[i]);
                if (file != null) {
                    if (!file[i].isEmpty()) {
                        product.setImagedata(file[i].getBytes());
                        product.setImagename(file[i].getOriginalFilename());
                        product.setImagesize(file[i].getSize());
                        product.setImagetype(file[i].getContentType());
                        // } else {
                        // product.setImagedata(null);
                        // product.setImagename(null);
                        // product.setImagesize(0L);
                        // product.setImagetype(null);
                    }
                }
                list.add(product);
            }
            int ret = pService.insertBatch(list);
            if (ret == 1) {
                map.put("status", 200);
            }
        }

        catch (Exception e) {
            map.put("status", -1);
            e.printStackTrace();
        }
        return map;
    }

    // 일부만 수정할 때는 patch
    @PutMapping(value = "/update.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> productPatch(
            @RequestBody ProductEntity product) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            ProductEntity product1 = pRepository.findById(product.getNo()).orElse(null);
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            pRepository.save(product1);
            map.put("status", 200);
        }

        catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;

    }

    // http://127.0.0.1:9090/ROOT/api/product/selectProductView.json?no=1001
    @GetMapping(value = "/selectProductView.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> productSelect(
            @RequestParam(name = "no") Long no) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        try {
            ProductViewEntity proView = pViewRespository.findById(no).orElse(null);
            map.put("status", 200);
            map.put("result", proView);
        }

        catch (Exception e) {
            e.printStackTrace();
            map.put("status", -1);
        }
        return map;

    }

}

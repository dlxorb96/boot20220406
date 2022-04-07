package com.example.controller;

import java.io.IOException;
import java.io.InputStream;

import com.example.dto.ItemDTO;
import com.example.mapper.ItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    ResourceLoader resLoader;

    @Value("${default.image}")
    String defaultImage;

    @Autowired
    ItemMapper iMapper;

    @GetMapping(value = { "/", "/home", "/main" })
    public String getHome(
            Model model,
            @AuthenticationPrincipal User user) {
        System.out.println("Homecontroleerp000000000000000000000000000" + user);
        if (user != null) {
            model.addAttribute("useremail", user.getUsername());
            model.addAttribute("userrole", user.getAuthorities().toArray()[0]);
            System.out.println(user.getAuthorities().toArray()[0]);
            return "home";
        }

        return "home";
        // /를 붙여도 되고 안붙여도 됨 근데 안붙이는 게 좋은듯
    }

    @GetMapping(value = { "/403page" })
    public @ResponseBody String get403() {
        return "접근할 수 없는 페이지입니다";

    }

    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "icode") long icode) throws IOException {
        ItemDTO retItemImg = iMapper.selectItemImage(icode);

        if (retItemImg != null) { // 물품정보가 존재하면
            if (retItemImg.getIimagesize() > 0) { // 첨부한 파일 존재
                HttpHeaders headers = new HttpHeaders();

                if (retItemImg.getIimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (retItemImg.getIimagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (retItemImg.getIimagetype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 이미지 byte[], headers, HttpStatus.Ok
                ResponseEntity<byte[]> response = new ResponseEntity<>(retItemImg.getIimage(),
                        headers, HttpStatus.OK);
                return response;
            } else {
                InputStream is = resLoader
                        .getResource(defaultImage)
                        .getInputStream();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
                        headers, HttpStatus.OK);

                return response;
            }
        }
        return null;
    }
}

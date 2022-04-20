package com.example.schedule;

import java.io.IOException;
import java.util.Date;

import com.example.repository.ProductCountRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class Myschedule {

    @Autowired
    ProductCountRepository productCountRepository;

    // @Scheduled(cron = "*/2 * * * * *")
    public void printData() {
        Date date = new Date();
        System.out.println(date.toString());
    }

    // @Scheduled(cron = "*/4 * * * * *")
    public void printData1() throws IOException {
        final String URL = "http://127.0.0.1:9090/ROOT/api/buy/selectOne?bno=10";
        OkHttpClient clinet = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();

        Response response = clinet.newCall(request).execute();
        String msg = response.body().string();
        System.out.println(msg);

        // {"ret": "y", "data": "123"}
        JSONObject jobj = new JSONObject(msg);
        JSONObject jobj2 = jobj.getJSONObject("result");
        System.out.println(jobj2.getString("itemIname"));
        System.out.println(jobj2);
        // System.out.println(jobj.getString("ret"));
        // System.out.println(jobj.getInt("data"));
        // System.out.println(jobj.getJSONObject("result"));
        // System.out.println(jobj.getLong("itemIprice"));
        // System.out.println(jobj.getString("result.itemIname"));

        // [{"ret": "y", "data": "123"},{"ret": "y", "data": "123"}]
    }
}

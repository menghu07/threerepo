package com.apeny.httprpc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ahu on 2017年09月16日.
 */
@WebServlet(name = "consumer", value = {"/sayhello.do"})
public class Consumer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = "http://localhost:9802/DistributeSite/provider/sayhelloprovider.do?"
                + "serviceName=HelloService&args=2";
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        byte[] responseBytes = EntityUtils.toByteArray(entity);
        String jsonString = new String(responseBytes, "utf-8");
        JsonResult result = JsonUtil.toObject(jsonString, JsonResult.class);
        resp.getWriter().write(result.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

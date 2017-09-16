package com.apeny.httprpc;

import com.apeny.httprpc.domain.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahu on 2017年09月16日.
 */
@WebServlet(name = "provider", value = {"/provider/sayhelloprovider.do"})
public class Provider extends HttpServlet {

    private Map<String, Object> serviceMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        serviceMap.put("HelloService", new HelloService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> argMap = req.getParameterMap();
        String serviceName = argMap.get("serviceName")[0];
        String id = argMap.get("args")[0];
        BaseService service = (BaseService) serviceMap.get(serviceName);
        Person person = service.getPerson(Integer.valueOf(id));
        JsonResult result = new JsonResult();
        result.setCode("2002");
        result.setMessage("ok");
        result.setResult(person);
        String responseString = JsonUtil.toJson(result);
        resp.getWriter().write(responseString);
    }
}
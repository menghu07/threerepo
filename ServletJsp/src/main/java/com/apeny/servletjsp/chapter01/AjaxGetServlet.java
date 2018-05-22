package com.apeny.servletjsp.chapter01;

import com.apeny.servletjsp.util.JdbcUtil;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * filter和servlet都要支持异步操作
 * Created by apeny on 2017年09月15日.
 */
@WebServlet(name = "AjaxgetServlet", value = "/AjaxgetServlet/ajaxget.do", asyncSupported = true)
public class AjaxGetServlet extends HttpServlet {
    static {
        System.out.println("from ServletJsp.... Ajax.");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final PrintWriter writer = resp.getWriter();
        PrintWriter pw = resp.getWriter();
        writer.print("<script>function ffy() {var xi = 1; console.log(888)}</script>");
        writer.print("<div>iiiii</div>");
        writer.flush();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            writer.println("<div>div" + i +"</div>");
            writer.flush();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writer.println("老板检查工作");
        writer.flush();
        List<String> jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            jobs.add("jobs" + i);
        }
        final AsyncContext asyncContext = req.startAsync();
        doWork(asyncContext, jobs);
        writer.println("老板布置完工作走了");
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void doWork(final AsyncContext ac, final List<String> jobs) {
        System.out.println("get asyncTimeout: " + ac.getTimeout());
        ac.setTimeout(1 * 60 * 1000 * 1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter pw = ac.getResponse().getWriter();
                    for (String job : jobs) {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                            pw.write(job + "job");
                            pw.flush();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ac.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

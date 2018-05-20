package com.apeny.servletjsp.dispatcher;

import com.apeny.servletjsp.defobj.ExpressionLanguageOutput;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 1. forward 之前执行flush会引起forwar执行抛出异常，PrintWrite.flush()会提交Response，但还是可以在flush的
 * 2. !!! response.getWriter()和response.getOutputStream()不能同时用
 * 3 在调试时，默认是useOutputStream=false,偶然出现问题啦。。。。，调试时报getOutputStream()已经用了
 */
@WebServlet(name = "dispatcherservlet", value = "*.do")
public class DispatcherJspServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6230022484059652855L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        servletPath = servletPath.replace(".do", ".jsp");
        req.setAttribute("ActionLabel", "new Action");
        req.setAttribute("els", new ExpressionLanguageOutput());
        //forward验证原理
//        PrintWriter pw = resp.getWriter();
//        pw.write("the response from resp: " + " " + req.getServletPath());
//        pw.flush();
//        pw.write("<br/>write again data");
//        pw.flush();
//        //据说会报错，下面是抛出的异常,提交之后不能forward，提交之后可以include
//        //Cannot forward after response has been committed
//        /*throw new IllegalStateException
//                (sm.getString("applicationDispatcher.forward.ise"));
//        */
//        //forword之后就可以认为是结束了，虽然可以在forward之后继续执行代码，但不能在对Response做些操作了
//        req.getRequestDispatcher("/WEB-INF" + servletPath).forward(req, resp);
//		//forward之后执行一些语句
//        PrintWriter pwAfter = resp.getWriter();
//        pwAfter.write("<br/> after forward....");
//        pwAfter.flush();
//

        //这样写会错！！！outputStream之后在include会报错
        //java.lang.IllegalStateException: getOutputStream() has already been called for this response
///!!!       ServletOutputStream outputStream = resp.getOutputStream();
//        outputStream.println("i suspect output stream later.");
//        outputStream.flush();

        //include之后还可以继续执行当前请求的代码，但第二的页面或servlet不能刚改Response头信息
//        req.getRequestDispatcher("/form/formdo/get.do").include(req, resp);
        req.getRequestDispatcher("/WEB-INF" + servletPath).include(req, resp);
        resp.getWriter().write("<br/> include after second servlet: ......");
        resp.getWriter().flush();

        resp.flushBuffer();
        PrintWriter pwAfterInclude = resp.getWriter();
        pwAfterInclude.write("<br/> you can write still write...");
//!!!       ServletOutputStream outputStream = resp.getOutputStream();
//!!!    outputStream.println("<br/>会冲突吗");
//!!!        outputStream.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        servletPath = servletPath.replace(".do", ".jsp");
        req.getRequestDispatcher("/WEB-INF" + servletPath).forward(req, resp);
    }
}
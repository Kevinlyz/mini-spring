package com.qcby.web.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author kevinlyz
 * @ClassName TestServlet
 * @Description
 * @Date 2019-06-05 13:28
 **/
public class TestServlet implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.getWriter().write("test");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}

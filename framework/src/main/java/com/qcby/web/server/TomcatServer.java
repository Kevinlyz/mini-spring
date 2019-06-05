package com.qcby.web.server;

import com.qcby.web.servlet.TestServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * @author kevinlyz
 * @ClassName TomcatServer
 * @Description 集成Tomcat服务器
 * @Date 2019-06-05 13:10
 **/
public class TomcatServer {
    private Tomcat tomcat;
    private String[] agrs;

    public TomcatServer(String[] agrs) {
        this.agrs = agrs;
    }

    public void startServer() throws LifecycleException {
        //实例化tomcat
        tomcat = new Tomcat();
        tomcat.setPort(8899);
        tomcat.start();
        //实例化context容器
        Context context = new StandardContext();
        context.setPath("");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        TestServlet testServlet = new TestServlet();
        Tomcat.addServlet(context,"testServlet",testServlet).setAsyncSupported(true);
        //添加映射
        context.addServletMappingDecoded("/test.json","testServlet");
        tomcat.getHost().addChild(context);

        //设置常驻线程防止tomcat中途退出
        Thread awaitThread = new Thread("tomcat_await_thread."){
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        //设置为非守护线程
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}

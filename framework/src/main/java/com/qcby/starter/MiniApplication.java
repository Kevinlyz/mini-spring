package com.qcby.starter;

import com.qcby.beans.BeanFactory;
import com.qcby.core.ClassScanner;
import com.qcby.web.handler.HandlerManagger;
import com.qcby.web.server.TomcatServer;

import java.util.List;

/**
 * @author kevinlyz
 * @ClassName MiniApplication
 * @Description 框架的入口类
 * @Date 2019-06-04 19:21
 **/
public class MiniApplication {
    public static void run(Class<?> cls,String[] args){
        System.out.println("Hello mini-spring application！");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classList = ClassScanner.scannClasses(cls.getPackage().getName());
            BeanFactory.initBean(classList);
            classList.forEach(it->System.out.println(it.getName()));
            HandlerManagger.resolveMappingHandler(classList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

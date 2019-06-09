package com.qcby.web.handler;


import com.qcby.beans.Bean;
import com.qcby.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author kevinlyz
 * @ClassName MappingHandler
 * @Description 包含uri，类的方法信息，类信息和参数
 * @Date 2019-06-08 18:32
 **/
public class MappingHandler {

    private String uri;
    private Method method;
    private Class<?> controller;
    private String[] args;

    public MappingHandler(String uri, Method method, Class<?> controller, String[] args) {
        this.uri = uri;
        this.method = method;
        this.controller = controller;
        this.args = args;
    }

    public boolean handle(ServletRequest req, ServletResponse res) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        String reqUri = ((HttpServletRequest)req).getRequestURI();
        if (!this.uri.equals(reqUri))
            return false;

        //相等则调用Handler的resolveMappingHandler方法,实例化并返回
        Object[] parameters = new Object[args.length];
        for(int i=0;i<args.length;i++){
            parameters[i] = req.getParameter(args[i]);
        }

        Object ctl = BeanFactory.getBean(controller);
        Object response = method.invoke(ctl,parameters);
        res.getWriter().println(response.toString());
        return true;
    }
}

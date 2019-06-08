package com.qcby.web.handler;

import com.qcby.web.mvc.Controller;
import com.qcby.web.mvc.RequestMapping;
import com.qcby.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kevinlyz
 * @ClassName HandlerManagger
 * @Description 反射获取类信息
 * @Date 2019-06-08 18:34
 **/
public class HandlerManagger {

    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>>  classList){
        for (Class<?> cls : classList){
            if (cls.isAnnotationPresent(Controller.class)){
                presentHandlerFromController(cls);
            }
        }
    }

    private static void presentHandlerFromController(Class<?> cls) {
        //获取方法
        Method[] methods= cls.getDeclaredMethods();
        for (Method method : methods){
           if(!method.isAnnotationPresent(RequestMapping.class))
               continue;
           //获取uri路径
           String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
           List<String> paramList = new ArrayList<>();
           //获取参数值
           for (Parameter parameter : method.getParameters()){
               if (parameter.isAnnotationPresent(RequestParam.class)){
                   paramList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
               }
           }
           String[] params= paramList.toArray(new String[paramList.size()]);
           MappingHandler mappingHandler = new MappingHandler(uri,method,cls,params);
           HandlerManagger.mappingHandlerList.add(mappingHandler);
        }

    }

}

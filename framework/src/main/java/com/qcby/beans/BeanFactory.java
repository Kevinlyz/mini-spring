package com.qcby.beans;

import com.qcby.web.mvc.Controller;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kevinlyz
 * @ClassName BeanFactory
 * @Description bean工厂
 * @Date 2019-06-09 11:09
 **/
public class BeanFactory {

    //ConcurrentHashMap保存bean
    public static Map<Class<?>,Object> classToBean = new ConcurrentHashMap<>();

    //获取bean
    public static Object getBean(Class<?> cls){
        return classToBean.get(cls);
    }

   //初始化
    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
//        System.out.println("remainSize>>>>>"+toCreate);
        //循环实例化bean，判断是否存在循环依赖
        while (toCreate.size() != 0){
            System.out.println("执行一次while循环");
            int remainSize = toCreate.size();
            for (int i=0;i<toCreate.size();i++){
                System.out.println(toCreate.size());
                System.err.println(i);
                System.out.println(toCreate.get(i));
                if (finishCreate(toCreate.get(i))){
                    System.err.println("remove  "+toCreate.get(i));
                    toCreate.remove(i);
                }
                System.out.println("removed size:"+toCreate.size()+"\n");
            }
            if (toCreate.size() == remainSize){         //陷入死循环，抛出异常
                System.out.println(toCreate.size());
                System.out.println(toCreate.toString());
                throw new Exception("循环依赖异常");
            }
        }
        System.out.println("执行完毕后的size>>>>>>"+toCreate.size());
    }

    //创建bean
    private static boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {

        //不是bean直接返回true并删除
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)){
            return true;
        }

        //创建对象
        Object bean = cls.newInstance();

        //解决存在的依赖；获取域并判断是否被Autowired注解
        for (Field field : cls.getDeclaredFields()){
            if (field.isAnnotationPresent(Autowired.class)){        //存在Autowired注解
                Class<?> fieldType = field.getType();
                Object reliantBean = BeanFactory.getBean(fieldType);        //从Bean工厂内获取被依赖的bean
                if (reliantBean == null){
                    return false;
                }
                field.setAccessible(true);
                field.set(bean,reliantBean);            //为bean设置依赖域
            }
        }
        classToBean.put(cls,bean);
        return true;
    }
}

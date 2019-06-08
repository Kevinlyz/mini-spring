package com.qcby.web.mvc;


import java.lang.annotation.*;

/**
 * @Author kevinlyz
 * @Description 映射注解，添加在方法上
 * @Date 17:11 2019-06-08

 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String value();
}

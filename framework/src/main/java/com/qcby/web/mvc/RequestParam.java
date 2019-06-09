package com.qcby.web.mvc;

import java.lang.annotation.*;

/**
 * @Author kevinlyz
 * @Description 参数注解，添加在方法参数上
 * @Date 17:10 2019-06-08
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value();
}

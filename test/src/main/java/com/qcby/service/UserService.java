package com.qcby.service;

import com.qcby.beans.Bean;

/**
 * @author kevinlyz
 * @ClassName UserService
 * @Description TODO
 * @Date 2019-06-09 13:37
 **/
@Bean
public class UserService {

    public String getInfo(String name){
        return name+" is handsome!!";
    }
}

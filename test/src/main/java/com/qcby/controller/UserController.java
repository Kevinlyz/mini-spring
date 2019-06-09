package com.qcby.controller;

import com.qcby.beans.Autowired;
import com.qcby.service.UserService;
import com.qcby.web.mvc.Controller;
import com.qcby.web.mvc.RequestMapping;
import com.qcby.web.mvc.RequestParam;

/**
 * @author kevinlyz
 * @ClassName UserController
 * @Description TODO
 * @Date 2019-06-08 17:14
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(@RequestParam("name") String name,@RequestParam("desc") String desc){
        System.out.println("test访问了！");
        System.out.println("name>>>>>>>"+name);
        return userService.getInfo(name);
    }
}

package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;

@Controller   //直接添加注解即可
public class MainController {

    @RequestMapping("/test")   //直接填写访问路径
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.getModel().put("name", "啊这");
        return modelAndView;
    }
}

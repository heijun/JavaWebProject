package com.test5.example1.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BrokenComponent {

    @Autowired
    private TolenComponent tolenComponent;
    @Resource
    private TubunComponent tubunComponent;
}

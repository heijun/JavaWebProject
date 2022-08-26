package com.test5.example1.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Component
public class TolenComponent {

    @Resource
    private TubunComponent tubunComponent;

}
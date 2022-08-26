package com.test5.example1.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TubunComponent {

    private DenpaComponent denpaComponent;
    @Autowired
    public TubunComponent( DenpaComponent denpaComponent) {
        this.denpaComponent = denpaComponent;
    }
}
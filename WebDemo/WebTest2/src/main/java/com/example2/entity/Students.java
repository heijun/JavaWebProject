package com.example2.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Students {
    private int sid;
    private String name;
    private String sex;
    private Teacher teacher;
}


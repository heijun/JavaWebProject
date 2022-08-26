package com.example2.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

@Alias("lbwnb")
@Data
@Accessors(chain = true)
public class Student {
    Integer sid;
    String name;
    String sex;
    Integer grade;
}
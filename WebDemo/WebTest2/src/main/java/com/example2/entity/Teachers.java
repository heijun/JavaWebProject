package com.example2.entity;

import lombok.Data;

import java.util.List;
@Data
public class Teachers {
    int tid;
    String name;
    List<Student> studentList;
}

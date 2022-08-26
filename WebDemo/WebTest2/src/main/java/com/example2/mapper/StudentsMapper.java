package com.example2.mapper;

import com.example2.entity.Students;

import java.util.List;

public interface StudentsMapper {
    List<Students> selectStudent(int tid);
}

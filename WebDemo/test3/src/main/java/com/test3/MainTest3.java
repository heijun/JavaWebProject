package com.test3;

public class MainTest3 {
    public static void main(String[] args){
        Object student=new Student(1,"小明","男",2019);
        System.out.println(Student.builder().sid(1).name("小明").sex("男").grade(2019).build().getName()+"\n");
        System.out.println(Student.builder().sid(1).name("小明").sex("男").grade(2019).build().toString());
        Student student1=Student.builder().sid(1).name("小明").sex("男").grade(2019).build();
        Student student2=Student.builder().sid(1).name("小明").sex("男").grade(2019).build();
        System.out.println(Integer.toHexString(student1.hashCode()));
        System.out.println(Integer.toHexString(student2.hashCode()));
        System.out.println(student1.equals(student2)+"\n");
        System.out.println(student.equals(student1)+"\n");
        System.out.println(student1 == student2);
    }

}

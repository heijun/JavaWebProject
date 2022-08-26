package com.test3;

import lombok.*;

/*@Getter
@Setter
@AllArgsConstructor*/
@EqualsAndHashCode
@Getter
@ToString
@Builder
public class Student {
    Integer sid;
    String name;
    String sex;
    Integer grade;
}

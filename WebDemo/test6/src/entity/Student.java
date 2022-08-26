package entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Student {
    Integer sid;
    String name;
    String sex;
    Integer grade;
}

public class Student {
    Integer sid;
    String name;
    String sex;
    Integer grade;

    Student(Integer sid, String name, String sex, Integer grade) {
        this.sid = sid;
        this.name = name;
        this.sex = sex;
        this.grade = grade;
    }

    public static Student.StudentBuilder builder() {
        return new Student.StudentBuilder();
    }

    public Integer getSid() {
        return this.sid;
    }

    public String getName() {
        return this.name;
    }

    public String getSex() {
        return this.sex;
    }

    public Integer getGrade() {
        return this.grade;
    }

    public String toString() {
        return "Student(sid=" + this.getSid() + ", name=" + this.getName() + ", sex=" + this.getSex() + ", grade=" + this.getGrade() + ")";
    }

    public static class StudentBuilder {
        private Integer sid;
        private String name;
        private String sex;
        private Integer grade;

        StudentBuilder() {
        }

        public Student.StudentBuilder sid(Integer sid) {
            this.sid = sid;
            return this;
        }

        public Student.StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Student.StudentBuilder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public Student.StudentBuilder grade(Integer grade) {
            this.grade = grade;
            return this;
        }

        public Student build() {
            return new Student(this.sid, this.name, this.sex, this.grade);
        }


        public String toString() {
            return "Student.StudentBuilder(sid=" + this.sid + ", name=" + this.name + ", sex=" + this.sex + ", grade=" + this.grade + ")";
        }
    }
}

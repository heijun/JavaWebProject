public class MainTest4 {
    public static void main(String[] args) {
        System.out.println(Student.builder().sid(1).name("小明").sex("男").grade(2019).build().toString());
    }
}

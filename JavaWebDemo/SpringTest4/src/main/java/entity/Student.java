package entity;

public class Student {
    private Card card;
    private String name;
    private int sid;
    private String sex;
    private int grade;



    public Student(Integer sid, String name, String sex,Integer grade) {
        this.sid = sid;
        this.name = name;
        this.sex = sex;
        this.grade=grade;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setCard(Card card) {
        this.card=card;
    }

    public void say(){
        System.out.println("学生的名字是："+name+"；卡："+card);
    }


    public int test(String str){
        System.out.println("我被调用了:"+str);
        return str.length();
    }

}

import java.math.BigDecimal;

public class Book {
    Integer bid;
    String title;
    String desc;
    BigDecimal price;



    public Book(Integer bid, String title, String desc, BigDecimal price) {
        this.bid = bid;
        this.title = title;
        this.desc = desc;
        this.price = price;
    }
    public void print() {
        System.out.println("书名：" + title + ",描述是:" + desc + ",价格为:" + price);
    }
}

package book;

public class BookService {
    // 1. 定义一个书架，最多放100本书
    public Book[] books=new Book[100];
    // 2. 记录当前书架上实际存放的书籍数量
    private int usedSize;
    public BookService(){
        // 构造方法里预初始化几本书，方便你待会运行测试
        books[0] = new Book("Java从入门到精通", "作者A", "编程", 2023, java.time.LocalDate.now());
        books[1] = new Book("西游记", "吴承恩", "名著", 1990, java.time.LocalDate.now());
        books[2] = new Book("三体", "刘慈欣", "科幻", 2008, java.time.LocalDate.now());
        // 给每本书设置 ID
        books[0].setBookId(1);
        books[1].setBookId(2);
        books[2].setBookId(3);
        this.usedSize = 3;
    }

    // 获取特定位置的书
    public Book getPos(int pos) {
        return books[pos];
    }

    // 在特定位置放置或更新一本书
    public void setBooks(int pos, Book book) {
        books[pos] = book;
    }

    // 获取当前书籍总数
    public int getUsedSize() {
        return usedSize;
    }

    // 更新书籍总数（比如新增或删除后需要修改）
    public void setUsedSize(int usedSize) {
        this.usedSize = usedSize;
    }
}

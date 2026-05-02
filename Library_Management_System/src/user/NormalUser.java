package user;

public class NormalUser extends User {
    //如果是普通⽤⼾，这⾥写死
    public NormalUser(String name, int userID) {
        super(name, userID, " 普通⽤⼾ ");

    }

    private void loadBorrowedBook() {

    }

    private void storeBorrowedBook() {

    }


    @Override
    public int display() {
        System.out.println(" 用户 " + this.getName() + " 的操作菜单:");
        System.out.println("1. 查找图书 ");
        System.out.println("2. 打印所有的图书 ");
        System.out.println("3. 退出系统 ");
        System.out.println("4. 借阅图书 ");
        System.out.println("5. 归还图书 ");
        System.out.println("6. 查看当前个人借阅情况 ");
        System.out.println("请选择你的操作: ");
        return scanner.nextInt();
    }
     //借阅图书
    public void borrowBook() {

    }
    //归还图书
    public void returnBook() {

    }
    //查看个⼈借阅情况
public void viewBorrowBooks() {
    }
}


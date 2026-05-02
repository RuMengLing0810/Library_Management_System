import book.BookService;
import user.AdminUser;
import user.NormalUser;
import user.User;
import java.util.Scanner;

public class LibrarySystem {
    public static void main(String[] args) {
        //直接创建管理员⽤⼾
        User adminUser = new AdminUser(" 刘备 ", 1);
        //直接创建普通⽤⼾
        User normalUser1 = new NormalUser(" 关⽻ ", 2);
        User normalUser2 = new NormalUser(" 张⻜ ", 3);
        //初始化书架
        BookService bookService = new BookService();
        //登录逻辑
        User user = login();
        //进入循环直到用户退出
        while (true) {
            int choice = user.display();
            handleChoice(user, choice, bookService);
        }
    }

    public static User login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入姓名：");
        String name = scanner.next();
        System.out.println("请选择身份：1. 管理员  2. 普通用户");
        int identity = scanner.nextInt();

        if (identity == 1) {
            return new AdminUser(name, 1); // 这里的 role 在构造函数里写死
        } else {
            return new NormalUser(name, 2);
        }
    }

    public static void handleChoice(User user, int choice, BookService bookService) {
        // 你可以在这里根据 choice 调用你之前在 AdminUser/NormalUser 里的方法
        // 比如 choice == 2 是打印所有图书
        if (choice == 1) {
            System.out.println("--- 查找图书 ---");
            System.out.println("请输入你要查找的书名");
            String name=user.scanner.next();
            boolean isFound=false;// 立一个 Flag，记录找没找到
            for (int i = 0; i < bookService.getUsedSize(); i++) {
                book.Book b = bookService.getPos(i);
                if (b.getTitle().equals(name)) {
                    System.out.println("查到了，信息如下");
                    System.out.println(b);
                    isFound = true;
                    break;
                }
            }
            if (!isFound){ // 如果循环结束 Flag 还是 false，说明没找到
                System.out.println("抱歉，书库中没有名为《" + name + "》的图书。");
            }
        } else if (choice == 2) {
            System.out.println("正在为你展示所有图书...");
            for (int i = 0; i < bookService.getUsedSize(); i++) {
                System.out.println(bookService.getPos(i));
            }
        } else if (choice == 3) {
            System.out.println("退出系统，再见！");
            System.exit(0);
        }
        //========================身份特有操作区===============================
        else {
            // 管理员专属操作
            if (user instanceof user.AdminUser) {
                if (choice == 4) {
                    System.out.println("--- 新增图书 ---");
                    System.out.print("请输入书名：");
                    String title = user.scanner.next();
                    System.out.print("请输入作者：");
                    String author = user.scanner.next();
                    System.out.print("请输入类型：");
                    String category = user.scanner.next();
                    System.out.print("请输入出版年份：");
                    int year = user.scanner.nextInt();

                    // 1. 制造一本新书
                    // 这里的 LocalDate.now() 会自动记录今天的日期作为上架日期
                    book.Book newBook = new book.Book(title, author, category, year, java.time.LocalDate.now());
                    // 2. 获取当前已经有多少本书
                    int pos = bookService.getUsedSize();

                    // 3.在实例化后，手动设置一下 ID
                    newBook.setBookId(pos + 1); // 既然已经有 3 本了，这一本就是 4 号

                    // 4.找到当前书架放到了哪里
                    int currentSize = bookService.getUsedSize();

                    // 5.把新书放进去
                    bookService.setBooks(currentSize, newBook);

                    // 6. 更新书架计数器（这一步最重要，否则打印时看不到新书）
                    bookService.setUsedSize(currentSize + 1);

                    System.out.println("成功上架新书：《" + title + "》！");
                }
                else if (choice == 5) {
                    System.out.println("--- 修改图书 ---");
                    System.out.print("请输入要修改的图书名字：");
                    String name = user.scanner.next();

                    // 1. 查找图书是否存在
                    int index = -1;
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        if (bookService.getPos(i).getTitle().equals(name)) {
                            index = i;
                            break;
                        }
                    }

                    if (index == -1) {
                        System.out.println("未找到名为《" + name + "》的图书，无法修改。");
                    } else {
                        // 2. 找到书了，提示输入新信息
                        System.out.println("已找到该图书，请输入修改后的信息：");

                        System.out.print("新书名：");
                        String newTitle = user.scanner.next();
                        System.out.print("新作者：");
                        String newAuthor = user.scanner.next();
                        System.out.print("新类型：");
                        String newCategory = user.scanner.next();
                        System.out.print("新出版年份：");
                        int newYear = user.scanner.nextInt();

                        // 3. 获取原来的对象，利用 Setter 修改属性
                        book.Book b = bookService.getPos(index);
                        b.setTitle(newTitle);
                        b.setAuthor(newAuthor);
                        b.setCategory(newCategory);
                        b.setPublishYear(newYear);

                        System.out.println("图书信息修改成功！");
                    }
                }
                else if (choice == 6) {
                    System.out.println("--- 下架图书 ---");
                    System.out.print("请输入要下架的书名：");
                    String name = user.scanner.next();
                    int index = -1; // 用来记录找到的书在哪个位置

                    // 1. 查找要下架的书在哪
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        if (bookService.getPos(i).getTitle().equals(name)) {
                            index = i;
                            break;
                        }
                    }

                    if (index == -1) {
                        System.out.println("未找到该图书，无法下架。");
                    } else {
                        // 2. 【核心】数据搬运
                        // 从被删除的位置开始，把后面的每一本书都往前移动一格
                        for (int i = index; i < bookService.getUsedSize() - 1; i++) {
                            // 取出后一格的书
                            book.Book nextBook = bookService.getPos(i + 1);
                            // 放到当前位置（覆盖掉旧的或已经被往前挪过的书）
                            bookService.setBooks(i, nextBook);
                        }

                        // 3. 收尾工作
                        // 现在的最后一格和倒数第二格内容是一样的，把最后一格设为 null（清理内存）
                        bookService.setBooks(bookService.getUsedSize() - 1, null);

                        // 更新书架已使用的数量
                        bookService.setUsedSize(bookService.getUsedSize() - 1);

                        System.out.println("图书《" + name + "》已成功从系统下架。");
                    }
                }
                else if (choice == 7) {
                    System.out.println("--- 统计借阅次数 ---");
                    int totalBorrowCount = 0; // 定义一个累加器

                    // 1. 遍历当前书架上所有的图书
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        book.Book b = bookService.getPos(i);

                        // 2. 将每本书的借阅次数加到总和中
                        totalBorrowCount += b.getBorrowCount();

                        // 可选：打印每本书的具体借阅情况，让报表更详细
                        System.out.println("《" + b.getTitle() + "》 已被借阅 " + b.getBorrowCount() + " 次");
                    }

                    // 3. 输出最终结果
                    System.out.println("================================");
                    System.out.println("全馆图书总借阅次数合计为：" + totalBorrowCount + " 次");
                    System.out.println("================================");
                }
                else if (choice == 8){
                    System.out.println("--- 查看最受欢迎的前 K 本书 ---");
                    System.out.print("你想查看排名前几的书？（输入 K 的值）：");
                    int k = user.scanner.nextInt();

                    // 1. 准备数据：创建一个临时数组，只装入当前已有的书
                    // 这样做是为了排序时不影响原书架的原始顺序
                    int currentSize = bookService.getUsedSize();
                    book.Book[] sortedBooks = new book.Book[currentSize];
                    for (int i = 0; i < currentSize; i++) {
                        sortedBooks[i] = bookService.getPos(i);
                    }

                    // 2. 核心算法：冒泡排序 (根据借阅次数 borrowCount 降序排列)
                    // 借阅次数多的排在前面
                    for (int i = 0; i < sortedBooks.length - 1; i++) {
                        for (int j = 0; j < sortedBooks.length - 1 - i; j++) {
                            if (sortedBooks[j].getBorrowCount() < sortedBooks[j + 1].getBorrowCount()) {
                                // 交换位置
                                book.Book temp = sortedBooks[j];
                                sortedBooks[j] = sortedBooks[j + 1];
                                sortedBooks[j + 1] = temp;
                            }
                        }
                    }

                    // 3. 输出结果：如果 K 大于实际书量，就只打印实际有的数量
                    int actualPrintCount = Math.min(k, currentSize);
                    System.out.println("🔥 借阅排行榜 Top " + actualPrintCount + "：");
                    for (int i = 0; i < actualPrintCount; i++) {
                        book.Book b = sortedBooks[i];
                        System.out.println("Top " + (i + 1) + ": 《" + b.getTitle() + "》 - 借阅次数: " + b.getBorrowCount());
                    }
            }
                else if (choice == 9) {
                    System.out.println("--- 查看库存状态 ---");

                    // 1. 获取基础数据
                    int used = bookService.getUsedSize(); // 已用容量
                    // 假设你的 BookService 数组定义的长度就是总容量，比如 10
                    // 如果你没有 getBooks().length，可以根据你定义的数组长度来写
                    int total = 10;
                    int available = total - used; // 剩余容量

                    // 2. 计算占比
                    double usageRate = (double) used / total * 100;

                    // 3. 统计借出情况
                    int borrowedCount = 0;
                    for (int i = 0; i < used; i++) {
                        if (bookService.getPos(i).getIsBorrowed()) {
                            borrowedCount++;
                        }
                    }

                    // 4. 打印可视化报告
                    System.out.println("📊 图书馆库存简报：");
                    System.out.println("--------------------------------");
                    System.out.println("总存储空间：" + total + " 格");
                    System.out.println("已占用空间：" + used + " 格");
                    System.out.println("剩余可用空间：" + available + " 格");
                    System.out.println("当前空间使用率：" + usageRate + "%");
                    System.out.println("--------------------------------");
                    System.out.println("在馆图书数量：" + (used - borrowedCount) + " 本");
                    System.out.println("已借出图书数量：" + borrowedCount + " 本");
                    System.out.println("--------------------------------");
                }
                else if (choice == 10) {
                    System.out.println("--- 检查超过一年未下架的陈旧图书 ---");
                    java.time.LocalDate today = java.time.LocalDate.now(); // 获取今天的日期
                    boolean found = false;

                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        book.Book b = bookService.getPos(i);
                        java.time.LocalDate shelfDate = b.getShelfDate(); // 获取上架日期

                        // 计算两个日期之间的年数差
                        long years = java.time.temporal.ChronoUnit.YEARS.between(shelfDate, today);

                        if (years >= 1) {
                            System.out.println("【预警】图书《" + b.getTitle() + "》已上架 " + years + " 年，建议评估是否下架。");
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("目前馆内所有图书均在上架有效期内（不满一年）。");
                    }
                }
            } else {
                // 普通用户专属操作
                if (choice == 4) {
                    System.out.println("请输入你要借阅的图书名字：");
                    String name = user.scanner.next();
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        book.Book b = bookService.getPos(i);
                        if (b.getTitle().equals(name)) {
                            if (b.getIsBorrowed()) { // 注意：这里的 getIsBorrowed 是你 Book 类里的 getter
                                System.out.println("抱歉，这本书已经被借走了。");
                            } else {
                                b.setIsBorrowed(true); // 修改属性
                                System.out.println("借阅成功！");
                            }
                            return; // 找到并处理完就结束方法
                        }
                    }
                    System.out.println("没找到这本书。");
                } else if (choice == 5) {
                    System.out.println("请输入你要归还的图书名字：");
                    String name = user.scanner.next();
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        book.Book b = bookService.getPos(i);
                        if (b.getTitle().equals(name)) {
                            if (!b.getIsBorrowed()) { // 如果是 false，说明本来就在馆里
                                System.out.println("这本书并没有被借出，无需归还。");
                            } else {
                                b.setIsBorrowed(false); // 改回 false
                                System.out.println("归还成功！感谢您的爱护。");
                            }
                            return; //结束方法
                        }
                    }
                    System.out.println("没找到这本书的记录");
                } else if (choice == 6) {
                    System.out.println("--- 📖 我的借阅清单 ---");
                    boolean hasBorrowed = false;
                    int count = 0;

                    // 1. 遍历所有图书
                    for (int i = 0; i < bookService.getUsedSize(); i++) {
                        book.Book b = bookService.getPos(i);

                        // 2. 检查这本书是否处于被借出状态
                        if (b.getIsBorrowed()) {
                            count++;
                            System.out.println(count + ". 《" + b.getTitle() + "》 [作者：" + b.getAuthor() + " | 借阅日期：" + java.time.LocalDate.now() + "]");
                            hasBorrowed = true;
                        }
                    }

                    // 3. 如果一本书都没借，给出友好提示
                    if (!hasBorrowed) {
                        System.out.println("你目前没有借阅任何图书，去书架看看吧！");
                    } else {
                        System.out.println("--------------------------------");
                        System.out.println("合计：你当前共借阅了 " + count + " 本书。");
                    }
                } else {
                    System.out.println("功能正在开发中，你选择了操作：" + choice);
                }
            }
        }
    }
}














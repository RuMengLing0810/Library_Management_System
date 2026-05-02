package user;

import java.util.Scanner;

public abstract class User {
    public Scanner scanner;
    private String name;
    private int userID;
    private String role;

    public User(String name, int userID, String role) {
        this.name = name;
        this.userID = userID;
        this.role = role;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userID=" + userID +
                ", role='" + role + '\'' +
                '}';
    }
    public abstract int display();
}

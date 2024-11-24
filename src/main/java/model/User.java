package model;

public class User {

    private String name;
    private boolean isStudentOrRetiree;

    public User(String name, boolean isStudentOrRetiree) {
        this.name = name;
        this.isStudentOrRetiree = isStudentOrRetiree;
    }

    public String getName() {
        return name;
    }

    public boolean isStudentOrRetiree() {
        return isStudentOrRetiree;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentOrRetiree(boolean studentOrRetiree) {
        isStudentOrRetiree = studentOrRetiree;
    }
}

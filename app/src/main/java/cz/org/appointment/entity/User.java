package cz.org.appointment.entity;


import java.util.Date;


public class User {

    private int id;
    protected String name;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(int id, String name, String userName, String password, String tel, int gender, int userType, Date createTime) {

        this.userName = userName;
        this.password = password;
        this.tel = tel;
        this.gender = gender;
        this.userType = userType;
        this.createTime = createTime;
    }

    protected String userName;

    protected String password;


    protected String tel;

    protected int gender;

    //用户类别（学生，或教师）
    protected int userType;


    protected Date createTime;


}

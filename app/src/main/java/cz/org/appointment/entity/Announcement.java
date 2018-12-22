package cz.org.appointment.entity;


import java.util.Date;

public class Announcement   {


    private  int id;

    private String title;

    private String content;

    private String pushMan;

    private Date pushDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushMan() {
        return pushMan;
    }

    public void setPushMan(String pushMan) {
        this.pushMan = pushMan;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }
}

package cz.org.appointment.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable {


    private int id;

    private Laboratory laboratory;


    private User user;


    public Comment(){}

    public Comment(int id, Laboratory laboratory, User user, String commentContent, Date time) {
        this.id = id;
        this.laboratory = laboratory;
        this.user = user;
        this.commentContent = commentContent;
        this.time = time;
    }


    //    private Comment parentComment;


    //评论内容
    private String commentContent;
    //评论时间
    private Date time;

//    //改评论的回复
//    private List<Comment> subComment = new ArrayList<>();




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }



    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
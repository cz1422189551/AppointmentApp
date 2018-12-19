package cz.org.appointment.entity;


import java.io.Serializable;
import java.util.Date;


public class Appointment implements Serializable {

    private int id;

    private User user;


    private Laboratory laboratory;

    //创建时间
    private Date createDate;

    //预约的时间
    private Date appointmentDate;

    //结束时间
    private Date endDate;


    private Date date;

    //分钟
    private int minute = 30;


    //预约状态，1 预约中 ， 0 取消预约
    private int enable = 1;

    public Appointment() {
    }

    public Appointment(User user, Laboratory laboratory, Date createDate, Date appointmentDate, Date endDate, Date date, int minute, int enable) {
        this.user = user;
        this.laboratory = laboratory;
        this.createDate = createDate;
        this.appointmentDate = appointmentDate;
        this.endDate = endDate;
        this.date = date;
        this.minute = minute;
        this.enable = enable;
    }

    public Appointment(int id, User user, Laboratory laboratory, Date createDate, Date appointmentDate, Date endDate, Date date, int minute, int enable) {
        this.id = id;
        this.user = user;
        this.laboratory = laboratory;
        this.createDate = createDate;
        this.appointmentDate = appointmentDate;
        this.endDate = endDate;
        this.date = date;
        this.minute = minute;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", user=" + user +
                ", laboratory=" + laboratory +
                ", createDate=" + createDate +
                ", appointmentDate=" + appointmentDate +
                ", endDate=" + endDate +
                ", date=" + date +
                ", minute=" + minute +
                ", enable=" + enable +
                '}';
    }
}

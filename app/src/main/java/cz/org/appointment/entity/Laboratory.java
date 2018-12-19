package cz.org.appointment.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Laboratory implements Serializable {


    private int id;

    private User user;

    private String name;

    //排
    private int row;
    //列
    private int col;
    //座位数量
    private int seatCount;

    //是否启用

    private boolean enable;

    //开放时间

    private Date openDate;

    //关闭时间

    private Date closeDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public LaboratoryType getLaboratoryType() {
        return laboratoryType;
    }

    public void setLaboratoryType(LaboratoryType laboratoryType) {
        this.laboratoryType = laboratoryType;
    }

    private LaboratoryType laboratoryType;

    public Laboratory() {
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public Laboratory(int id, User user, String name,int row, int col, boolean enable, Date openDate, Date closeDate, LaboratoryType laboratoryType) {
        this.id = id;
        this.user = user;
        this.name = name;

        this.row = row;
        this.col = col;
        this.enable = enable;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.laboratoryType = laboratoryType;
    }
}

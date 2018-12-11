package cz.org.appointment.entity;



import java.util.Date;
import java.util.List;


public class Laboratory {


    private int id;

    private User user;

    private String name;


    private List<Seat> seatList;



    //排

    private int row;
    //列

    private int col;


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

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
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

    public Laboratory(){}

    public Laboratory(int id, User user, String name, List<Seat> seatList, int row, int col, boolean enable, Date openDate, Date closeDate, LaboratoryType laboratoryType) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.seatList = seatList;
        this.row = row;
        this.col = col;
        this.enable = enable;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.laboratoryType = laboratoryType;
    }
}

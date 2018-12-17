package cz.org.appointment.entity;


import java.time.LocalDateTime;
import java.util.Date;

/**
 * 实验室里的每一个座位
 */

public class LaboratorySeat {


    //状态是否被占用， -1 未启用 0 空位 1被占用

    private Integer stateType;


    private Laboratory laboratory;
    private Seat seat;


    private Date appointmentDate;

    public Date getLocalDateTime() {
        return appointmentDate;
    }

    public void setLocalDateTime(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }


}

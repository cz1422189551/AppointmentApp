package cz.org.appointment.entity;


import java.util.Date;


public class Appointment {


    private User user;


    private LaboratorySeat laboratorySeat;

    //创建时间
    private Date createDate;

    //预约的时间
    private Date appointmentDate;

    //结束时间
    private Date endDate;

    //预约状态，1 预约中 ， 0 取消预约
    private int enable = 1;

}

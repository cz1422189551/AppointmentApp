package cz.org.appointment.entity.core;


import java.util.Date;
import java.util.List;

import cz.org.appointment.entity.Laboratory;
import cz.org.appointment.entity.LaboratorySeat;
import cz.org.appointment.entity.LaboratoryType;
import cz.org.appointment.entity.User;


public class LaboratoryEntity extends Laboratory {

    private List<LaboratorySeat> seatInfoList;

    public LaboratoryEntity(int id, User user, String name, List<LaboratorySeat> seatInfoList, int row, int col, boolean enable, Date openDate, Date closeDate, LaboratoryType laboratoryType) {
        super(id, user, name, null, row, col, enable, openDate, closeDate, laboratoryType);
        this.seatInfoList = seatInfoList;
    }


    public LaboratoryEntity(Laboratory l, List<LaboratorySeat> seatInfoList) {
        this(l.getId(), l.getUser(), l.getName(), seatInfoList, l.getRow(), l.getCol(), l.isEnable(), l.getOpenDate(), l.getCloseDate(), l.getLaboratoryType());
    }
}

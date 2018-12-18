//package cz.org.appointment.ui;
//
//import com.qfdqc.views.seattable.SeatTable;
//
//import java.util.List;
//
//import cz.org.appointment.entity.core.LaboratoryEntity;
//
////选座实现
//public class SeatCheckerImpl implements SeatTable.SeatChecker {
//
//    private String TAG = "SeatChecker";
//
//    private LaboratoryEntity currentEntity;
//
//    public SeatCheckerImpl(LaboratoryEntity currentEntity) {
//        this.currentEntity = currentEntity;
//    }
//
//
//    public LaboratoryEntity getCurrentEntity() {
//        return currentEntity;
//    }
//
//    public void setCurrentEntity(LaboratoryEntity currentEntity) {
//        this.currentEntity = currentEntity;
//    }
//
//    @Override
//    public boolean isValidSeat(int row, int column) {
//        return true;
//    }
//
//    @Override
//    public boolean isSold(int row, int column) {
//        List<LaboratorySeat> laboratorySeats = currentEntity.getSeatInfoList();
//        for (LaboratorySeat laboratorySeat : laboratorySeats) {
//            if (laboratorySeat.getSeat().getColIndex()-1 == column && laboratorySeat.getSeat().getRowIndex()-1 == row) {
//                return laboratorySeat.getStateType() > 0;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void checked(int row, int column) {
////        Log.d(TAG, "checked: " +row+","+column);
//    }
//
//    @Override
//    public void unCheck(int row, int column) {
//
//    }
//
//    @Override
//    public String[] checkedSeatTxt(int row, int column) {
//        return null;
//    }
//}

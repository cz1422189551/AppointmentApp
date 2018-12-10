package cz.org.appointment.entity;




public class Seat {


    private String name;
    //第几排

    private int rowIndex;
    //第几列

    private int colIndex;

    //别名

    private String rowName;


    public Seat(int rowIndex, int colIndex) {
    }

    public Seat(String name, int rowIndex, int colIndex) {
        this.name = name;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }


}

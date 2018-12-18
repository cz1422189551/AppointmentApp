package cz.org.appointment.entity;//package com.lq.laboratory.entity;


import java.util.List;


public class LaboratoryType {

    private int id;

    private String name;

    private List<Laboratory> laboratoryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Laboratory> getLaboratoryList() {
        return laboratoryList;
    }

    public void setLaboratoryList(List<Laboratory> laboratoryList) {
        this.laboratoryList = laboratoryList;
    }
}

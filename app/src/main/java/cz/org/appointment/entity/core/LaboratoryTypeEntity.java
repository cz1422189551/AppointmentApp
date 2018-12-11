package cz.org.appointment.entity.core;


import java.util.List;

//客户端使用的类型实体
public class LaboratoryTypeEntity {

    private int id;

    private String name;

    private List<LaboratoryEntity> entities;

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

    public List<LaboratoryEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<LaboratoryEntity> entities) {
        this.entities = entities;
    }
}

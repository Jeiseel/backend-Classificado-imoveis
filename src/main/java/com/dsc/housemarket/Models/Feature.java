package com.dsc.housemarket.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Feature extends AbstractEntity {

    @Id
    private long id;

    @Size(max = 256)
    @NotEmpty
    @Column
    private String type;

    @Column
    private float area;

    @Column
    private int rooms;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}


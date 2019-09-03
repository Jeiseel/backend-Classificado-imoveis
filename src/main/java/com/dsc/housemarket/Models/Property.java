package com.dsc.housemarket.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Property extends AbstractEntity {

    @Id
    private long id;

    @Size(max = 256)
    @NotEmpty
    @Column
    private String name;

    @Column
    private float value;

    @Size(max = 256)
    @NotEmpty
    @Column
    private String description;

    @Size(max = 300)
    @NotEmpty
    @Column
    private String superDescription;

    @Column
    private Feature features;

    @Column
    @NotEmpty
    private String photos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuperdescription() {
        return superDescription;
    }

    public void setSuperdescription(String superdescription) {
        this.superDescription = superdescription;
    }

    public Feature getFeatures() {
        return features;
    }

    public void setFeatures(Feature features) {
        this.features = features;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getSuperDescription() {
        return superDescription;
    }

    public void setSuperDescription(String superDescription) {
        this.superDescription = superDescription;
    }

}

package com.dsc.housemarket.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Property extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 256)
    @NotEmpty
    @Column
    private String name;

    @NotEmpty
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

    @NotEmpty
    @Column
    private Feature features;

    @NotEmpty
    @Column
    private Photo photos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Photo getPhotos() {
        return photos;
    }

    public void setPhotos(Photo photos) {
        this.photos = photos;
    }
}

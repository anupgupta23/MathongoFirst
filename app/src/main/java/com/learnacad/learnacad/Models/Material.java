package com.learnacad.learnacad.Models;

/**
 * Created by Sahil Malhotra on 10-11-2017.
 */

public class Material {

    private Long id;

    String name;
    int minicourseId;

    public Material(String name, int minicourseId) {
        this.name = name;
        this.minicourseId = minicourseId;
    }

    public Material() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinicourseId() {
        return minicourseId;
    }

    public void setMinicourseId(int minicourseId) {
        this.minicourseId = minicourseId;
    }
}

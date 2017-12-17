package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by Sahil Malhotra on 17-07-2017.
 */
@Table
public class Tutor implements Serializable{

    private Long id;
    String processId;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTutor_id() {
        return tutor_id;
    }

    public void setTutor_id(int tutor_id) {
        this.tutor_id = tutor_id;
    }

    public Tutor(){

    }

    public Tutor(int tutor_id,String name,String description){

        this.tutor_id = tutor_id;
        this.name = name;
        this.description = description;
    }

    private int tutor_id;
    private String imgUrl;
    private String name;
    private String description;



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

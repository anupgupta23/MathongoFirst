package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

import java.util.ArrayList;

/**
 * Created by Sahil Malhotra on 22-07-2017.
 */
@Table
public class LectureListMapping {

    public LectureListMapping(String processId, ArrayList<Lecture> lessons) {
        this.processId = processId;
        this.lessons = lessons;
    }

    public LectureListMapping() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public ArrayList<Lecture> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lecture> lessons) {
        this.lessons = lessons;
    }

    private Long id;
    String processId;
    ArrayList<Lecture> lessons;
}

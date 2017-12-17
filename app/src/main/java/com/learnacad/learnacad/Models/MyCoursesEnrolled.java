package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

/**
 * Created by Sahil Malhotra on 20-08-2017.
 */
@Table
public class MyCoursesEnrolled {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    String mycourses;

    public String getMycourses() {
        return mycourses;
    }

    public void setMycourses(String mycourses) {
        this.mycourses = mycourses;
    }

    public MyCoursesEnrolled(){


    }


}

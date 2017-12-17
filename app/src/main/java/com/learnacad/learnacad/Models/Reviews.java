package com.learnacad.learnacad.Models;

/**
 * Created by Sahil Malhotra on 27-08-2017.
 */

public class Reviews {

    int rating;
    String description;
    int studentId;
    String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Reviews(int rating, String description, int studentId,String name) {
        this.rating = rating;
        this.description = description;
        this.studentId = studentId;
        this.studentName = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}

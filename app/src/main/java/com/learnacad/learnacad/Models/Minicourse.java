package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

/**
 * Created by Sahil Malhotra on 17-07-2017.
 */
@Table
public class Minicourse  {

    private Long id;

    private String processId;

    private Boolean isEnrolled;

    private String tutorName;
    private String tutorImageUrl;

    public String getTutorImageUrl() {
        return tutorImageUrl;
    }

    public void setTutorImageUrl(String tutorImageUrl) {
        this.tutorImageUrl = tutorImageUrl;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public Boolean getEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        isEnrolled = enrolled;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Minicourse(int course_id, String name, String description, String level, String medium, String subject, String courseName, String relevance, float rating, String duration) {
        this.course_id = course_id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.medium = medium;
        this.subject = subject;
        this.courseName = courseName;
        this.relevance = relevance;
        this.rating = rating;
        this.duration = duration;
    }

    String duration;

    public Minicourse(){


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

     int course_id;
     String name;
     String description;
     int noOfLectures;
     String level;
     String medium;
     String subject;
     String courseName;
     String relevance;
     float rating;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    String className;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
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

    public int getNoOfLectures() {
        return noOfLectures;
    }

    public void setNoOfLectures(int noOfLectures) {
        this.noOfLectures = noOfLectures;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


}

package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by Sahil Malhotra on 17-07-2017.
 */
@Table
public class Lecture implements Serializable{
    private Long id;

    public Lecture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    private int lecture_id;

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    boolean isUpVoted;

    public boolean isUpVoted() {
        return isUpVoted;
    }

    public void setUpVoted(boolean upVoted) {
        isUpVoted = upVoted;
    }

    boolean isBookmarked;

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public Lecture(int lecture_id, String name, String url, String duration, String description, int upvotes, boolean isUpVoted,boolean isBookmarked) {
        this.name = name;
        this.lecture_id = lecture_id;
        this.url = url;
        this.duration = duration;
        this.description = description;
        this.upvotes = upvotes;
        this.isUpVoted = isUpVoted;
        this.isBookmarked = isBookmarked;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String name;
    private String url;
    private String duration;
    private String description;
    private int upvotes;
}

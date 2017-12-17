package com.learnacad.learnacad.Models;

import com.orm.dsl.Table;

/**
 * Created by Sahil Malhotra on 16-10-2017.
 */
@Table
public class Notifications {

    private String title;
    private String description;
    private String extraMessage;
    private Long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraMessage() {
        return extraMessage;
    }

    public void setExtraMessage(String extraMessage) {
        this.extraMessage = extraMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notifications() {
    }

    public Notifications(String title, String description) {

        this.title = title;
        this.description = description;
    }
}

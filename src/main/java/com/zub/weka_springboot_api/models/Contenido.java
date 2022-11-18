package com.zub.weka_springboot_api.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("contenido")
public class Contenido {

    private String _id;
    private String type;
    private String title;
    private Integer release_year;
    private String rating;
    private String description;
    private String listed_in;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListed_in() {
        return listed_in;
    }

    public void setListed_in(String listed_in) {
        this.listed_in = listed_in;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }
}

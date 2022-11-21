package com.zub.weka_springboot_api.models;

import java.util.List;

public class RepContenido  {

    private String _id;
    private Integer popular_rank;
    private Integer certificate;
    private Integer startYear;
    private Integer endYear;
    private Integer episodes;
    private Integer runtime;
    private String type;
    private String orign_country;
    private String language;
    private Integer rating;
    private Integer numVotes;
    private List<String> genres;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getPopular_rank() {
        return popular_rank;
    }

    public void setPopular_rank(Integer popular_rank) {
        this.popular_rank = popular_rank;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void setCertificate(Integer certificate) {
        this.certificate = certificate;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrign_country() {
        return orign_country;
    }

    public void setOrign_country(String orign_country) {
        this.orign_country = orign_country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}

package com.filmpolis.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.filmpolis.BR;

import java.io.Serializable;

/**
 * Created by Miguel on 15/09/2017.
 */

public class Movie extends BaseObservable implements Serializable {
    private String title;
    private String imdbId;
    private String released;
    private String rated;
    private String studio;
    private String description;
    private String genre;
    private String language;
    private String runtime;
    private String imageURL;
    private String imdbRating;
    private String actors;
    private String directors;

    public Movie(String title, String imdbId) {
        this.title = title;
        this.imdbId = imdbId;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getImdbId() {
        return imdbId;
    }

    @Bindable
    public String getReleased() {
        return released;
    }

    @Bindable
    public String getRated() {
        return rated;
    }

    @Bindable
    public String getStudio() {
        return studio;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getGenre() {
        return genre;
    }

    @Bindable
    public String getLanguage() {
        return language;
    }

    @Bindable
    public String getRuntime() {
        return runtime;
    }

    @Bindable
    public String getImageURL() {
        return imageURL;
    }

    @Bindable
    public String getImdbRating() {
        return imdbRating;
    }

    @Bindable
    public String getActors() {
        return actors;
    }

    @Bindable
    public String getDirectors() {
        return directors;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
        notifyPropertyChanged(BR.imdbId);
    }

    public void setReleased(String released) {
        this.released = released;
        notifyPropertyChanged(BR.released);
    }

    public void setRated(String rated) {
        this.rated = rated;
        notifyPropertyChanged(BR.rated);
    }

    public void setStudio(String studio) {
        this.studio = studio;
        notifyPropertyChanged(BR.studio);
    }

    public void setDescription(String description) {
        this.description = "  " + description;
        notifyPropertyChanged(BR.description);
    }

    public void setGenre(String genre) {
        this.genre = genre;
        notifyPropertyChanged(BR.genre);
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyPropertyChanged(BR.language);
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
        notifyPropertyChanged(BR.runtime);
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    public void setActors(String actors) {
        this.actors = "  " + actors;
        notifyPropertyChanged(BR.actors);
    }

    public void setDirectors(String directors) {
        this.directors = "  " + directors;
        notifyPropertyChanged(BR.directors);
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
        notifyPropertyChanged(BR.imdbRating);
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + ", imdbId: " + getImdbId() + ", released: " + getReleased()
                + ", rated: " + getRated() + ", studio: " + getStudio() + ", description: "
                + getDescription() + ", genre: " + getGenre() + ", language: " + getLanguage()
                + ", runtime: " + getRuntime() + ", imdbRating: "
                + getImdbRating();
    }
}

package com.filmpolis.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.BinderThread;

import com.filmpolis.BR;

import java.io.Serializable;

/**
 * Created by Miguel on 15/09/2017.
 */

public class Actor extends BaseObservable implements Serializable {
    private String name;
    private String birthDay;
    private String deathDay;
    private String birthPlace;
    private String gender;
    private String biography;
    private String imageURL;
    private String imdbId;
    private String age;

    public Actor(String name, String imdbId) {
        this.name = name;
        this.imdbId = imdbId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getBirthDay() {
        return birthDay;
    }

    @Bindable
    public String getDeathDay() {
        return deathDay;
    }

    @Bindable
    public String getBirthPlace() {
        return birthPlace;
    }

    @Bindable
    public String getGender() {
        return gender;
    }

    @Bindable
    public String getBiography() {
        return biography;
    }

    @Bindable
    public String getImageURL() {
        return imageURL;
    }

    @Bindable
    public String getImdbId() {
        return imdbId;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
        notifyPropertyChanged(BR.birthDay);
    }

    public void setDeathDay(String deathDay) {
        this.deathDay = deathDay;
        notifyPropertyChanged(BR.deathDay);
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        notifyPropertyChanged(BR.birthPlace);
    }

    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    public void setBiography(String biography) {
        this.biography = biography;
        notifyPropertyChanged(BR.biography);
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
        notifyPropertyChanged(BR.imdbId);
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", imdbId: " + getImdbId() + ", birthday: " + getBirthDay()
                + ", deathday: " + getDeathDay() + ", birthplace: " + getBirthPlace() + ", gemder: "
                + getGender() + ", biography: " + getBiography();
    }
}

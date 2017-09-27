package com.filmpolis.models;

/**
 * Created by Miguel on 22/09/2017.
 */

public class Suggestion {
    private String url_icon;
    private String text;

    public Suggestion() {
        text = "";
        url_icon = "";
    }

    public String getText() {
        return text;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }
}

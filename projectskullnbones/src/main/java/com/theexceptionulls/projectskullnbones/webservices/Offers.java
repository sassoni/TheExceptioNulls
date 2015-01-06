package com.theexceptionulls.projectskullnbones.webservices;

import java.io.Serializable;

/**
 * Created by rohithavatapally on 8/28/14.
 */
public class Offers implements Serializable {

    private static final long serialVersionUID = 564971444;

    private int id;
    private String heading;
    private String description;
    private String expiration;
    private boolean isClipped = false;
    private boolean isDisliked = false;
    private boolean isLiked = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public boolean isClipped() {
        return isClipped;
    }

    public void setClipped(boolean isClipped) {
        this.isClipped = isClipped;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isDisliked() {
        return isDisliked;
    }

    public void setDisliked(boolean isDisliked) {
        this.isDisliked = isDisliked;
    }
}

package com.amarnath.deliverytest.utils.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Delivery extends RealmObject {

    @PrimaryKey
    int id;
    String description;
    String imageUrl;
    Location location;

    public Delivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

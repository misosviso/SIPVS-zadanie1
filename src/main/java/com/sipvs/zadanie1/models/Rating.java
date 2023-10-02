package com.sipvs.zadanie1.models;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.sipvs.zadanie1.xml.DateAdapter;

public class Rating {
    private String restaurantAddress = "";
    private int restaurantRating = 5;
    private String restaurantComment = "";
    private Date visitDate = new Date(System.currentTimeMillis());
    private int id = 0;

    public int getId() {
        return id;
    }

    @XmlAttribute(name = "review_id")
    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    @XmlElement(name = "restaurant-address")
    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public int getRestaurantRating() {
        return restaurantRating;
    }

    @XmlElement(name = "restaurant-rating")
    public void setRestaurantRating(int restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantComment() {
        return restaurantComment;
    }

    @XmlElement(name = "restaurant-comment")
    public void setRestaurantComment(String restaurantComment) {
        this.restaurantComment = restaurantComment;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    @XmlElement(name = "visit-date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getContent() {
        return "Restaurant address: " + restaurantAddress + "\n" +
                "Restaurant rating: " + restaurantRating + "\n" +
                "Restaurant comment: " + restaurantComment + "\n" +
                "Visit date: " + visitDate + "\n";
    }
}

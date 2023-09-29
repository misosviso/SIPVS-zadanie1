package com.sipvs.zadanie1.models;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "form")
public class Form {
  private String firstName;
  private String lastName;
  private String emailAddreess;
  private String restaurantAddress;
  private int restaurantRating;
  private String restaurantComment;
  private Date visitDate;

  public String getFirstName() {
    return firstName;
  }

  @XmlElement(name = "first-name")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @XmlElement(name = "last-name")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddreess() {
    return emailAddreess;
  }

  @XmlElement(name = "email-address")
  public void setEmailAddreess(String emailAddreess) {
    this.emailAddreess = emailAddreess;
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
  public void setVisitDate(Date visitDate) {
    this.visitDate = visitDate;
  }

  public String getContent() {
    return "First name: " + firstName + "\n" +
        "Last name: " + lastName + "\n" +
        "Email address: " + emailAddreess + "\n" +
        "Restaurant address: " + restaurantAddress + "\n" +
        "Restaurant rating: " + restaurantRating + "\n" +
        "Restaurant comment: " + restaurantComment + "\n" +
        "Visit date: " + visitDate + "\n";
  }

}

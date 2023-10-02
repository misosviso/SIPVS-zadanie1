package com.sipvs.zadanie1.models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "form")
public class Form {
  private String firstName;
  private String lastName;
  private String emailAddreess;
  private String namespace = "http://sipvs-projekt.com/sipvs-namespace";

  private List<Rating> ratings;

  @XmlAttribute(name = "xmlns")
  public String getNamespace() {
    return namespace;
  }

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

  public List<Rating> getRatings() {
    return ratings;
  }

  @XmlElementWrapper(name = "ratings")
  @XmlElement(name = "rating")
  public void setRatings(List<Rating> ratings) {
    this.ratings = ratings;
  }

  public String getContent() {
    String ratingsText = "";
    for (Rating r : ratings) {
      ratingsText += r.getContent();
    }

    return "First name: " + firstName + "\n" +
        "Last name: " + lastName + "\n" +
        "Email address: " + emailAddreess + "\n" +
        "Repeating section: \n" + ratingsText + "\n";
  }

}

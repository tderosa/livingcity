package edu.brown.cs.tderosa.livingcity;

import java.util.List;

public class Place {
  private LatLng location;
  private String id, name, intro, picture;
  private List<Story> stories;

  public Place(String id, String name, String intro, String picture, LatLng location) {
    this.id = id;
    this.name = name;
    this.intro = intro;
    this.picture = picture;
    this.location = location;
    stories = null;
  }
  
  public void setStories(List<Story> newStories) {
    stories = newStories;
  }
  
  public List<Story> stories() {
    return stories;
  }
  
  public String name() {
    return name;
  }
  
  public String intro() {
    return intro;
  }
  
  public String picture() {
    return picture;
  }
}

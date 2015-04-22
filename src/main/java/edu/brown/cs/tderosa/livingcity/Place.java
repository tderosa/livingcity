package edu.brown.cs.tderosa.livingcity;

import java.util.List;

public class Place {
  private LatLng location;
  private String id, name, intro, picture;
  private List<Comment> comments;

  public Place(String id, String name, String intro, String picture, LatLng location) {
    this.id = id;
    this.name = name;
    this.intro = intro;
    this.picture = picture;
    this.location = location;
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

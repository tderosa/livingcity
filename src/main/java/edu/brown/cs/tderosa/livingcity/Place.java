package edu.brown.cs.tderosa.livingcity;

import java.util.List;

public class Place {
  private LatLng location;
  private String id, name, intro, pic_path;
  private List<Comment> comments;

  public Place(String id, String name, String intro, String pic_path, LatLng location) {
    this.id = id;
    this.name = name;
    this.intro = intro;
    this.pic_path = pic_path;
    this.location = location;
  }
}

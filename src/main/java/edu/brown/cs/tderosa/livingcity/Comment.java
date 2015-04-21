package edu.brown.cs.tderosa.livingcity;

public class Comment {
  private String id, authorName, authorAbt, text;
  private Place place;

  public Comment(String id, String authorName, String authorAbt, String text) {
    this.id = id;
    this.authorName = authorName;
    this.authorAbt = authorAbt;
    this.text = text;
  }
}

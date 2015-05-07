package edu.brown.cs.tderosa.livingcity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Story {
  private String id, authorName, authorAbt, text, audioPath;
  private Place place;
  private Calendar date;
  private boolean yearOnly;

  public Story(String id, String[] date, String authorName, String authorAbt, String text, String audioPath) {
    this.id = id;
    this.authorName = authorName;
    this.authorAbt = authorAbt;
    this.text = text;
    this.date = stringToCal(date);
    this.audioPath = audioPath;
  }
  
  public void setPlace(Place p) {
    place = p;
  }
  
  public String audioPath() {
    return audioPath;
  }
  
  public Calendar stringToCal(String[] date) {
    Calendar newDate = new GregorianCalendar();
    if (date.length == 1) {
      yearOnly = true;
      newDate.set(Calendar.YEAR, Integer.parseInt(date[0]));
    } else {
      yearOnly = false;
      newDate.set(Calendar.MONTH, Integer.parseInt(date[0])-1);
      newDate.set(Calendar.YEAR, Integer.parseInt(date[1]));
    }
    return newDate;
  }
  
  public String text() {
    return text;
  }
  
  public String author() {
    return authorName;
  }
  public String authorAbt() {
    return authorAbt;
  }

  public Calendar date() {
    return date;
  }
  
  public boolean yearOnly() {
    return yearOnly;
  }
}

package edu.brown.cs.tderosa.livingcity;

import edu.brown.cs.tderosa.utils.Location;

public class LatLng implements Location {
  private Double lat, lng;

  public LatLng(Double lat, Double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  @Override
  public Double lat() {
    return lat;
  }

  @Override
  public Double lng() {
    return lng;
  }

  @Override
  public void setLat(Double lat) {
    this.lat = lat;
  }

  @Override
  public void setLng(Double lng) {
    this.lng = lng;
  }

}

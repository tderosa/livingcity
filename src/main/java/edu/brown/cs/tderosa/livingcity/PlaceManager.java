package edu.brown.cs.tderosa.livingcity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import edu.brown.cs.tderosa.db.DBManager;

public class PlaceManager {
  private DBManager db;
  private Map<String, Place> places;

  public PlaceManager(DBManager db) {
    this.db = db;
    places = new HashMap<String, Place>();
  }

  public Place getPlaceById(String id) throws SQLException {
    Place p = places.get(id);
    if (p == null) {
      p = db.getPlaceById(id);
      places.put(id, p);
    }
    return p;
  }
}

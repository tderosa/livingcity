package edu.brown.cs.tderosa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.tderosa.livingcity.LatLng;
import edu.brown.cs.tderosa.livingcity.Place;
import edu.brown.cs.tderosa.livingcity.Story;

public class DBManager {
  private String db;
  private Connection conn;
  private Map<String, Place> placeCache;

  /**
   * Constructor for database manager.
   * 
   * @param db Path to the database.
   * @throws ClassNotFoundException throws a class not found exception
   * @throws SQLException throws a sql exception
   */
  public DBManager(String db) throws ClassNotFoundException, SQLException {
    this.db = db;
    setUpDB();
    placeCache = new HashMap<String, Place>();
  }

  /**
   * Accesses the database.
   * 
   * @throws ClassNotFoundException throws a class not found exception
   * @throws SQLException throws a sql exception
   */
  public void setUpDB() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.close();
  }

  /**
   * Closes the connection.
   * 
   * @throws SQLException throws a sql exception
   */
  public void close() throws SQLException {
    conn.close();
  }

  public Place getPlaceById(String id) throws SQLException {
    Place place = placeCache.get(id);
    
    if (place == null) {
      String query = "SELECT * FROM place WHERE id = ?;";
      PreparedStatement prep = conn.prepareStatement(query);
      prep.setString(1, id);
      
      String name, intro, pic_path;
      Double lat, lng;
      ResultSet rs = prep.executeQuery();
      List<Place> places = new ArrayList<Place>();
      if (rs == null) {
        return null;
      }

      while (rs.next()) {
        name = rs.getString(2);
        intro = rs.getString(3);
        pic_path = rs.getString(4);
        LatLng point = new LatLng(rs.getDouble(5), rs.getDouble(6));
        Place p = new Place(id, name, intro, pic_path, point);
        places.add(p);
        placeCache.put(id, p);
      }

      rs.close();
      prep.close();
    }
    
    return place;
  }

  public List<Place> getPlaceByLatLng(LatLng northEast, LatLng southWest) throws SQLException {
    String query =
        "SELECT * FROM place WHERE" + " lat <= ? AND lat >= ? AND lng <= ? " + "AND lng >= ?;";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setDouble(1, northEast.lat());
    prep.setDouble(2, southWest.lat());
    prep.setDouble(3, northEast.lng());
    prep.setDouble(4, southWest.lng());

    String id, name, intro, pic_path;
    Double lat, lng;
    ResultSet rs = prep.executeQuery();
    List<Place> places = new ArrayList<Place>();
    if (rs == null) {
      return null;
    }

    while (rs.next()) {
      id = rs.getString(1);
      name = rs.getString(2);
      intro = rs.getString(3);
      pic_path = rs.getString(4);
      LatLng point = new LatLng(rs.getDouble(5), rs.getDouble(6));
      places.add(new Place(id, name, intro, pic_path, point));
    }

    rs.close();
    prep.close();

    return places;
  }

  public List<Place> getPlaces() throws SQLException {
    String query = "SELECT * FROM place;";
    PreparedStatement prep = conn.prepareStatement(query);

    String id, name, intro, pic_path;
    Double lat, lng;
    ResultSet rs = prep.executeQuery();
    List<Place> places = new ArrayList<Place>();
    if (rs == null) {
      return null;
    }

    while (rs.next()) {
      id = rs.getString(1);
      name = rs.getString(2);
      intro = rs.getString(3);
      pic_path = rs.getString(4);
      LatLng point = new LatLng(rs.getDouble(5), rs.getDouble(6));
      Place p = new Place(id, name, intro, pic_path, point);
      places.add(p);
      placeCache.put(id, p);
    }

    rs.close();
    prep.close();

    return places;
  }
  
  public List<Story> getStories(String placeID) throws SQLException {
    Place p = getPlaceById(placeID);
    String query = "SELECT * FROM story WHERE place = ?;";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setString(1, placeID);
    
    String id, authorName, authorAbt, text, date;
    ResultSet rs = prep.executeQuery();
    List<Story> stories = new ArrayList<Story>();
    if (rs == null) {
      return null;
    }

    while (rs.next()) {
      id = rs.getString(1);
      date = rs.getString(2);
      String[] dateArray = date.split("-");
      authorName = rs.getString(3);
      authorAbt = rs.getString(4);
      text = rs.getString(5);
      Story s = new Story(id, dateArray, authorName, authorAbt, text);
      stories.add(s);
    }

    rs.close();
    prep.close();
   
    p.setStories(stories);    
    return stories;
  }

  public void addPicture(byte[] picture) throws SQLException {
    String query = "INSERT OR IGNORE INTO 'picture' VALUES ('p1', ?);";
    PreparedStatement prep = conn.prepareStatement(query);
    prep.setBytes(1, picture);
    prep.executeBatch();
    prep.close();
  }
}

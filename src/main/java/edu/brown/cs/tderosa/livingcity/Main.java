package edu.brown.cs.tderosa.livingcity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.tderosa.db.DBManager;
import freemarker.template.Configuration;

public class Main {
  private DBManager db;
  private final static Gson GSON = new Gson();

  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
    new Main(args).run();
  }

  private Main(String[] args) {}

  private void run() throws IOException, ClassNotFoundException, SQLException {
    this.db = new DBManager("db2.sqlite3");
    runSparkServer();
  }

  /**
   * Creates a free marker engine.
   * 
   * @return The created free marker engine.
   */
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Runs the server.
   */
  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    // Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(3456);
    // Setup Spark Routes
    Spark.get("/", new FrontHandler(), freeMarker);
    Spark.post("/getPlaces", new GetPlaces());
    Spark.post("/getAllPlaces", new SendAllPlaces());
    Spark.get("/:placeID", new PlaceHandler(), freeMarker);
    Spark.get("/:placeID/add", new StoryFormView(), freeMarker);
    Spark.post("/add", new AddStory(), freeMarker);
  }

  private class PlaceHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String id = req.params(":placeID");
      
      Place p = null;
      try {
        p = db.getPlaceById(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      
      System.out.println(p);
      String picturePath = "'../assets/" + p.picture() + "'";
      String storyHTML = "";
      List<Story> stories = null;
      try {
        stories = db.getStories(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      
      for (Story s: stories) {
        String dateString = dateToString(s);
        storyHTML += "<div class='story'><p class='date'>" + dateString + "</p><p class='story-text'>- "+s.text()+"</p><p class='author'> - "+s.author()+"</p><p class='author-abt'>"+s.authorAbt()+"</p></div>";
      }
      
      String addLink = "'/" + id + "/add'";
      Map<String, Object> variables = ImmutableMap.of("name", p.name(), "addLink", addLink,"intro", p.intro(), "picture", picturePath, "stories", storyHTML);
      return new ModelAndView(variables, "place.ftl");
    }
  }
  
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Living City");
      return new ModelAndView(variables, "main.ftl");
    }
  }
  
  private class StoryFormView implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String id = req.params(":placeID");
      
      Place p = null;
      try {
        p = db.getPlaceById(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      String link = "/" + id;
      Map<String, Object> variables = ImmutableMap.of("title", "Living City", "name", p.name(), "id", id, "placeLink", link);
      return new ModelAndView(variables, "add.ftl");
    }
  }

  private class AddStory implements TemplateViewRoute {
    @Override
    public ModelAndView handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      Place p = null;
      try {
        p = db.getPlaceById(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      
      String author = qm.value("author");
      String authorAbt = qm.value("authorAbt");
      String month = qm.value("month");
      String date;
      if (month == null) {
        date = qm.value("year");
      } else {
        date = month + "-" + qm.value("year");
      }
      String text = qm.value("storyText");
      
      Story s = null;
      try {
        s = db.insertStory(p, date, author, authorAbt, text);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      
      String addLink = "'/" + id + "/add'";
      String picturePath = "'../assets/" + p.picture() + "'";
      String storyHTML = "";
      
      List<Story> stories = null;
      try {
        stories = db.getStories(id);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      
      for (Story story: stories) {
        String dateString = dateToString(story);
        storyHTML += "<div class='story'><p class='date'>" + dateString + "</p><p class='story-text'>- "+story.text()+"</p><p class='author'> - "+story.author()+"</p><p class='author-abt'>"+story.authorAbt()+"</p></div>";
      }
      
      res.redirect("/" + id);
      Map<String, Object> variables = ImmutableMap.of("name", p.name(), "addLink", addLink,"intro", p.intro(), "picture", picturePath, "stories", storyHTML);
      return new ModelAndView(variables, "place.ftl");
    }
  }
  
  private class GetPlaces implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();
      Double latNE = Double.parseDouble(qm.value("latNE"));
      Double lngNE = Double.parseDouble(qm.value("lngNE"));
      Double latSW = Double.parseDouble(qm.value("latSW"));
      Double lngSW = Double.parseDouble(qm.value("lngSW"));

      LatLng northEast = new LatLng(latNE, lngNE);
      LatLng southWest = new LatLng(latSW, lngSW);

      List<Place> places = null;

      try {
        places = db.getPlaceByLatLng(northEast, southWest);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      return GSON.toJson(places);
    }
  }

  private class SendAllPlaces implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();

      Collection<Place> places = null;

      places = db.places();
      return GSON.toJson(places);
    }
  }
  
  private String dateToString(Story s) {
    String date;
    if (s.yearOnly()) {
      date = Integer.toString(s.date().get(Calendar.YEAR));
    } else {
      String month;
      switch (s.date().get(Calendar.MONTH)) {
        case 0:
          month = "January";
          break;
        case 1:
          month = "February";
          break;
        case 2:
          month = "March";
          break;
        case 3:
          month = "April";
          break;
        case 4:
          month = "May";
          break;
        case 5:
          month = "June";
          break;
        case 6:
          month = "July";
          break;
        case 7:
          month = "August";
          break;
        case 8:
          month = "September";
          break;
        case 9:
          month = "October";
          break;
        case 10:
          month = "November";
          break;
        default:
          month = "December";
      }
      date = month + " " + Integer.toString(s.date().get(Calendar.YEAR));
    }
    return date;
  }
}

package edu.brown.cs.tderosa.livingcity;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
  }

  /**
   * Handles main page template.
   * 
   * @author tderosa
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("title", "Living City");
      return new ModelAndView(variables, "main.ftl");
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

      System.out.println("(" + latNE + ", " + lngNE + ")     " + latSW + ", " + lngSW + ")");
      LatLng northEast = new LatLng(latNE, lngNE);
      LatLng southWest = new LatLng(latSW, lngSW);

      List<Place> places = null;

      try {
        places = db.getPlaceByLatLng(northEast, southWest);
      } catch (SQLException e) {
        e.printStackTrace();
      }

      System.out.println(places.size());
      System.out.println();
      return GSON.toJson(places);
    }
  }

  private class SendAllPlaces implements Route {
    @Override
    public Object handle(final Request req, final Response res) {
      QueryParamsMap qm = req.queryMap();

      List<Place> places = null;

      try {
        places = db.getPlaces();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      System.out.println(places.size());
      System.out.println();
      return GSON.toJson(places);
    }
  }
}

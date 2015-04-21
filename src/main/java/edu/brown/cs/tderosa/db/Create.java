package edu.brown.cs.tderosa.db;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class Create {

  private Connection conn;

  public Create(String db) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + db;

    conn = DriverManager.getConnection(urlToDB);

    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.execute("DROP TABLE IF EXISTS picture");
    // stat.execute("DROP TABLE IF EXISTS place");
    // stat.execute("DROP TABLE IF EXISTS comment");
    stat.close();

    String schema =
        "CREATE TABLE picture(" + "id TEXT," + "picture_data BLOB," + "PRIMARY KEY ('id') );";
    buildTable(schema);

    // schema = "CREATE TABLE place("
    // + "id TEXT,"
    // + "name TEXT,"
    // + "intro TEXT,"
    // + "picture TEXT,"
    // + "PRIMARY KEY ('id'),"
    // + "FOREIGN KEY ('picture') REFERENCES picture(id) "
    // + "ON DELETE CASCADE ON UPDATE CASCADE);";
    // buildTable(schema);
    //
    // schema = "CREATE TABLE comment("
    // + "id TEXT,"
    // + "author TEXT,"
    // + "author_abt TEXT,"
    // + "comment_txt TEXT,"
    // + "place TEXT,"
    // + "PRIMARY KEY ('id'),"
    // + "FOREIGN KEY ('place') REFERENCES place(id) "
    // + "ON DELETE CASCADE ON UPDATE CASCADE);";
    // buildTable(schema);
  }

  public void addPicture(String id, String path) throws IOException, SQLException {
    byte[] picture = extractBytes(path);
    String query = "INSERT INTO picture VALUES (?,?)";
    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1, id);
    ps.setBytes(2, picture);

    ps.executeBatch();
    ps.close();
  }

  public byte[] extractBytes(String ImageName) throws IOException {
    // open image
    File imgPath = new File(ImageName);
    BufferedImage bufferedImage = ImageIO.read(imgPath);

    // get DataBufferBytes from Raster
    WritableRaster raster = bufferedImage.getRaster();
    DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

    return (data.getData());
  }

  // public void fillPlace(String path) throws IOException, SQLException {
  // CSVReader cReader = new CSVReader(new FileReader(path));
  //
  // // The first line is the header
  // String[] nextLine;
  // List<String> header;
  // header = Arrays.asList(cReader.readNext());
  //
  // // determining the indexes of each attribute
  // int course_idx = header.indexOf("course");
  // int sem_idx = header.indexOf("sem");
  // int prof_idx = header.indexOf("prof");
  //
  // /*
  // * prepare an all purpose insert statement; note the use of question marks.
  // * Each question mark corresponds to an attribute. Essentially we are
  // * building a tuple to insert into the table. This allows us to use the Same
  // * PreparedStatement without having to create a new one for each insertion.
  // */
  // String query = "INSERT INTO course VALUES (?,?,?)";
  // PreparedStatement ps = conn.prepareStatement(query);
  //
  // while ((nextLine = cReader.readNext()) != null) {
  // /*
  // * These lines set each of the question marks to the appropriate entry.
  // * Notice that in order to create confusion, the setString methods are not
  // * 0 indexed. So the first element is actually in the 1st index, not the
  // * 0th index. Don't ask why it's like that.
  // */
  // ps.setString(1, nextLine[course_idx]);
  // ps.setString(2, nextLine[sem_idx]);
  // ps.setString(3, nextLine[prof_idx]);
  //
  // /*
  // * Writing to a database is expensive. If we were to parse csv files with
  // * millions of entries and write each tuple to disk as we built them, then
  // * it would take a lot of days to build the table. Instead, we call
  // * addBatch which will store all the tuples in memory and then after we
  // * call executeBatch, the driver will write all of the tuples at once to
  // * the disk
  // */
  // ps.addBatch();
  // }
  //
  // /*
  // * Now we tell the PreparedStatement to write all the tuples to disk
  // */
  // ps.executeBatch();
  //
  // /*
  // * Make sure to close all of your resources. Most JDBC classes need to be
  // * closed.
  // */
  // cReader.close();
  // ps.close();
  // }

  // public void fillStudent(String path) throws IOException, SQLException {
  // // TODO: Fill student
  // // TODO: Fill course. This will be very similar to fillEnrollment()
  // CSVReader cReader = new CSVReader(new FileReader(path));
  //
  // // The first line is the header
  // String[] nextLine;
  // List<String> header;
  // header = Arrays.asList(cReader.readNext());
  //
  // // determining the indexes of each attribute
  // int name_idx = header.indexOf("name");
  // int gradyear_idx = header.indexOf("grad_year");
  // int gpa_idx = header.indexOf("gpa");
  //
  // /*
  // * prepare an all purpose insert statement; note the use of question marks.
  // * Each question mark corresponds to an attribute. Essentially we are
  // * building a tuple to insert into the table. This allows us to use the Same
  // * PreparedStatement without having to create a new one for each insertion.
  // */
  // String query = "INSERT INTO student VALUES (?,?,?)";
  // PreparedStatement ps = conn.prepareStatement(query);
  //
  // while ((nextLine = cReader.readNext()) != null) {
  // /*
  // * These lines set each of the question marks to the appropriate entry.
  // * Notice that in order to create confusion, the setString methods are not
  // * 0 indexed. So the first element is actually in the 1st index, not the
  // * 0th index. Don't ask why it's like that.
  // */
  // ps.setString(1, nextLine[name_idx]);
  // ps.setString(2, nextLine[gradyear_idx]);
  // ps.setString(3, nextLine[gpa_idx]);
  //
  // /*
  // * Writing to a database is expensive. If we were to parse csv files with
  // * millions of entries and write each tuple to disk as we built them, then
  // * it would take a lot of days to build the table. Instead, we call
  // * addBatch which will store all the tuples in memory and then after we
  // * call executeBatch, the driver will write all of the tuples at once to
  // * the disk
  // */
  // ps.addBatch();
  // }
  //
  // /*
  // * Now we tell the PreparedStatement to write all the tuples to disk
  // */
  // ps.executeBatch();
  //
  // /*
  // * Make sure to close all of your resources. Most JDBC classes need to be
  // * closed.
  // */
  // cReader.close();
  // ps.close();
  // }

  /**
   * Creates a new table according to the schema
   * 
   * @param schema
   * @throws SQLException
   */
  private void buildTable(String schema) throws SQLException {
    // TODO(1): Use a PreparedStatement to execute the command in
    // the argument schema. Since it will build a table, we do
    // not care about the results.
    PreparedStatement prep;
    prep = conn.prepareStatement(schema);
    // TODO(2): Close the PreparedStatement
    prep.executeUpdate();
  }

  /**
   * Closes any associated resources with the table
   * 
   * @throws SQLException
   */
  public void close() throws SQLException {
    // TODO: Close the Connection
    conn.close();
  }
}

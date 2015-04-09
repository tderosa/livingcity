package edu.brown.cs.tderosa.db;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs.tderosa.utils.CSV;

/**
 * This class creates a database from sample csv files
 */
public class Create {

  private Connection conn;

  /**
   * @param db
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public Create(String db) throws ClassNotFoundException,
      SQLException {
    // Load the driver class
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + db;

    // et up a connection to the db
    conn = DriverManager.getConnection(urlToDB);

    // These delete the tables if they already exist
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys = ON;");
    stat.execute("DROP TABLE IF EXISTS enrollment");
    stat.execute("DROP TABLE IF EXISTS course");
    stat.execute("DROP TABLE IF EXISTS student");
    stat.close();

    String schema = "CREATE TABLE enrollment("
        + "name TEXT,"
        + "course TEXT,"
        + "sem TEXT,"
        + "grade TEXT);";
    buildTable(schema);
  }

  public void fillEnrollment(String path) throws IOException, SQLException {
    CSV cReader = new CSV(path);

//    // The first line is the header
//    String[] nextLine;
//    List<String> header;
//    header = Arrays.asList(cReader.readNext());
//
//    // determining the indexes of each attribute
//    int name_idx = header.indexOf("name");
//    int course_idx = header.indexOf("course");
//    int sem_idx = header.indexOf("sem");
//    int grade_idx = header.indexOf("grade");

    /*
     * prepare an all purpose insert statement; note the use of question marks.
     * Each question mark corresponds to an attribute. Essentially we are
     * building a tuple to insert into the table. This allows us to use the Same
     * Statement without having to create a new one for each insertion.
     */
//    String query = "INSERT INTO enrollment VALUES (?,?,?,?)";
//    PreparedStatement ps = conn.prepareStatement(query);
//
//    while ((nextLine = cReader.readNext()) != null) {
      /*
       * These lines set each of the question marks to the appropriate entry.
       * Notice that in order to create confusion, the setString methods are not
       * 0 indexed. So the first element is actually in the 1st index, not the
       * 0th index. Don't ask why it's like that.
       */
//      ps.setString(1, nextLine[name_idx]);
//      ps.setString(2, nextLine[course_idx]);
//      ps.setString(3, nextLine[sem_idx]);
//      ps.setString(4, nextLine[grade_idx]);

      /*
       * Writing to a database is expensive. If we were to parse csv files with
       * millions of entries and write each tuple to disk as we built them, then
       * it would take a lot of days to build the table. Instead, we call
       * addBatch which will store all the tuples in memory and then after we
       * call executeBatch, the driver will write all of the tuples at once to
       * the disk
       */
//      ps.addBatch();
//    }

    /*
     * Now we tell the PreparedStatement to write all the tuples to disk
     */
//    ps.executeBatch();

    /*
     * Make sure to close all of your resources. Most JDBC classes need to be
     * closed.
     */
//    cReader.close();
//    ps.close();
  }

  /**
   * Creates a new table according to the schema
   * @param schema
   * @throws SQLException
   */
  private void buildTable(String schema) throws SQLException {
    PreparedStatement prep;
    prep = conn.prepareStatement(schema);
    prep.executeUpdate();
  }

  /**
   * Closes any associated resources with the table
   * @throws SQLException
   */
  public void close() throws SQLException {
    conn.close();
  }
}

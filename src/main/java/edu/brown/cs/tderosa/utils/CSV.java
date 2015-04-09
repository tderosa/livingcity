package edu.brown.cs.tderosa.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

/**
 * CSV parser class. Takes in a filename and parses.
 * @author tderosa
 */
public class CSV {
  private String filename;

  /*
   * Creates a CSV parser.
   * @param file: name of csv file.
   */
  public CSV(String file) {
    filename = file;
  }

  /**
   * Parses file
   * @return Arraylist of strings representing parsed text.
   */
  public ArrayList<String[]> parse() {
    ArrayList<String[]> parsedText = new ArrayList<String[]>();
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(filename));
      String line = "";
      String[] data = null;
      while ((line = reader.readLine()) != null) {
        data = line.split(",");
        parsedText.add(data);
      }
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: " + e);
    } catch (IOException e) {
      System.out.println("ERROR: " + e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          System.out.println("ERROR: " + e);
        }
      }
    }
    return parsedText;
  }
}

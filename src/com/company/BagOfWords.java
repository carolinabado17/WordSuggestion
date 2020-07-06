package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This class reads the file.
 *
 * @author Carolina Bado
 */
public class BagOfWords {

  private File dataFile = new File(
      "/Users/carolinabado/Desktop/Spring 2020/WordSuggestion/src/com/company/messages.txt");

  private Map<String, Integer> bow = new HashMap<>();
  private NavigableMap<String, Integer> allWordByKey = new TreeMap<>();

  /**
   * Function that loads the file to analyze it.
   *
   * @throws IOException - if there is a failure when reading the file it will throw this *
   *                     exception.
   */
  protected void loadFile() throws IOException {
    String line = null;

    try {
      BufferedReader reader = new BufferedReader(new FileReader(dataFile));
      while ((line = reader.readLine()) != null) {
        createTreeMap(line);

      }
      reader.close();
    } catch (Exception io) {
      io.printStackTrace();
    }
    buildBag();

  }

  /**
   * Build allWordsByKey Map based on line input from file
   *
   * @param cLine = the current line to process from file
   */
  private void createTreeMap(String cLine) {

    String[] allTokens = cLine.trim().split("\\s+");
    List<String> pairs = new ArrayList<String>();
    for (int i = 0; i < allTokens.length - 1; ++i) {
      pairs.add(allTokens[i] + ", " + allTokens[i + 1]);
    }
    for (String token : pairs) {
      allWordByKey.merge(token, 1, Integer::sum);
    }
  }

  /**
   * This function sorts the values using stream from highest to lowest.
   */
  private void buildBag() {

    bow = allWordByKey.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new));

  }

  /**
   * This functions compares the input and it then will determine the best suggestions for your
   * word.
   *
   * @param input - the user's input that is being comapred.
   */
  protected void compare(String input) {
    double totalOccurrence = 0;
    boolean found = false;

    for (Map.Entry<String, Integer> entry : allWordByKey.entrySet()) {
      String pairsOfWords = entry.getKey();

      try {

        String initial = pairsOfWords.substring(0, input.length());

        if (initial.equals(input)) {
          int valueOfOccurrences = entry.getValue();
          totalOccurrence += valueOfOccurrences;

        } else {

        }

      } catch (StringIndexOutOfBoundsException e) {

      }

    }
    for (Map.Entry<String, Integer> entry : allWordByKey.entrySet()) {
      String pairsOfWords = entry.getKey();

      try {

        String initial = pairsOfWords.substring(0, input.length());

        if (initial.equals(input)) {
          int valueOfOccurrences = entry.getValue();
          double support = entry.getValue() / totalOccurrence;

          if (support > .65) {
            System.out
                .println("Your next word might be " + entry.getKey().substring(input.length() + 1));
            found = true;

          }
        } else {

        }

      } catch (StringIndexOutOfBoundsException e) {

      }

    }
    if (found) {
      System.out.println("Your next word could be 'the' ");
      System.out.println("Your next word could be 'this' ");
    } else {
      System.out.println("Your next word could be 'the'.");
      System.out.println("Your next word could be 'this' .");
      System.out.println("Your next word could be 'of'.");
    }

  }
}
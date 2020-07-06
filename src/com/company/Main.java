package com.company;

import java.io.IOException;
import java.util.Scanner;

/**
 * This program is a word suggesting engine. Similar to a phone when you type a word it gives you
 * three suggestions of a word to follow.
 *
 * @author Carolina Bado
 */
public class Main {

  /**
   * Main function, it takes user's input and provides the three suggested words.
   *
   * @param args - string of arrays that stores arguments when passed in the command line.
   * @throws IOException - if there is a failure when reading the file it will throw this
   *                     exception.
   */

  public static void main(String[] args) throws IOException {

    Scanner scan = new Scanner(System.in);
    System.out.println("Type your word: ");
    String inputWord = scan.nextLine();
    BagOfWords bow = new BagOfWords();
    bow.loadFile();
    bow.compare(inputWord);

  }
}

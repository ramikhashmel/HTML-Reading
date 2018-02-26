import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class HTMLSummarizerTest {
  private static String testInput0 = "";
  private static String [] testOutput0 = {  };

  private static String testInput1 = "<b> Bold me </b>";
  private static String [] testOutput1 = { "b 0" };

  private static String testInput2 = "<br> Bold me";
  private static String [] testOutput2 = { "br" };

  private static String testInput3 = "<p>Try this: <b>Bold me</b> or not.</p>";
  private static String [] testOutput3 = { "p 1",
                                           " b 0" };

  private static String testInput4 = 
                          "<p>Try this: <br> <b>Bold <hr> me</b> or not.</p>";
  private static String [] testOutput4 = { "p 2",
                                           " br",
                                           " b 1",
                                           "  hr" };

  private static String testInput5 = 
                          "<p>Try this: <br> <b>Bold <hr> me</b> or not." +
                          "<i>italicize this: <img></i></p>";
  private static String [] testOutput5 = { "p 3",
                                           " br",
                                           " b 1",
                                           "  hr",
                                           " i 1",
                                           "  img"
 };

  private static String testInput6 = "<!DOCTYPE html>" +
                                     "<html>" +
                                     "<body>" +
                                     "<h1>My First Heading</h1>" +
                                     "<p>My first paragraph." +
                                     "<hr>" +
                                     "Another ruler..." +
                                     "<hr>" +
                                     "No more rulers" +
                                     "</p>" +
                                     "<br>" +
                                     "<!-- I should be ignored -->" +
                                     "<p>Bold Example: <b> bold </b></p>" +
                                     "</body>" +
                                     "</html>";
  private static String [] testOutput6 = { "html 1",
                                           " body 4",
                                           "  h1 0",
                                           "  p 2",
                                           "   hr",
                                           "   hr",
                                           "  br",
                                           "  p 1",
                                           "   b 0" };

  private static Scanner mkTest( String input ) {
    return new Scanner( input );
  }
     
  private static ArrayList<String> mkOutput( String [] output ) {
    ArrayList<String> al = new ArrayList<String>();

    for( String s : output ) {
      al.add( s );
    }
    return al;
  }
     
  private static boolean doTest( String input, String [] output ) {
    Tester t = new HTMLSummarizer();
    ArrayList<String> al = t.compute( mkTest( input ) );
    System.out.println( "Input: " );
    System.out.println( input );
    System.out.println( "Generated output" );
    for( String s : al ) {
      System.out.println( s );
    }
    System.out.println( "Expected output" );
    for( String s : output ) {
    	  System.out.println( s );
    }
    System.out.println( "---------------------------------------------------" );
    return al != null && al.equals( mkOutput( output ) );
  }

  @Test
  void testEmpty() {
    assertTrue( doTest( testInput0, testOutput0 ), "Empty Test" );
  }

  @Test
  void test1() {
    assertTrue( doTest( testInput1, testOutput1 ), "Single element tag" );
  }

  @Test
  void test2() {
    assertTrue( doTest( testInput2, testOutput2 ), "Single void tag" );
  }

  @Test
  void test3() {
    assertTrue( doTest( testInput3, testOutput3 ), "Single nesting test" );
  }

  @Test
  void test4() {
    assertTrue( doTest( testInput4, testOutput4 ), "Multiple nestings" );
  }

  @Test
  void test5() {
    assertTrue( doTest( testInput5, testOutput5 ), 
                "Multiple nestings and levels" );
  }

  @Test
  void test6() {
    assertTrue( doTest( testInput6, testOutput6 ), "Assignment example" );
  }

}

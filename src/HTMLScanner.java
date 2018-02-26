import java.util.Scanner;

/* This is a simple HTML Scanner that breaks up HTML into tags.  
 * It's main function is to extract the tag names from an HTML document.
 * For example, given the tags 
 *    <a href="http://www.cs.dal.ca"> Computer Science </a>
 * it will return the tags "a" and "/a"
 * The scanner discards everything but the tag names.
 * The scanner provides a constructor and two methods:
 *  HTMLScanner( Scanner s ) : takes a regular Scanner object, and parses its
 *                             contents
 *  boolean hasNextTag()     : returns true if and only if a tag is available
 *  String nextTag()         : returns the name of the next tag
 *
 * As a convenience, the class also provides a list of all the void or null
 * tags, which are tags that do not need to be closed.
 */

public class HTMLScanner {
  public static final String [] voidTags = { "area", "base", "br", "col", 
                                             "command", "embed", "hr", "img",
                                             "input", "keygen", "link", "meta",
                                             "param", "source", "track", "wbr"};

  private Scanner stream;        // Scanner object whose contents is parsed
  private String tagBody;        // Body of tag, beyond the name (for debugging)

  /* This constructor takes a Scanner object, whose contents is to be parsed
   * and configures it for parsing.
   * Param: s : Scanner, the the contents of which is to be parsed
   */
  public HTMLScanner( Scanner s ) {
    stream = s;
    stream.useDelimiter( "[<>]" );
  }

  /* Returns true if there is another tag to be parsed in the stream.
   */
  public boolean hasNextTag() {
    return stream.hasNext();
  }

  /* Returns the name of the next tag in the stream and moves the 
   * stream pointer beyond the tag.  The body of the tag and the 
   * content between tags is ignored.
   * Returns: String: the name of the current tag.
   */
  public String nextTag() {
    Scanner tag = new Scanner( stream.next() );
    String tagName = tag.next();
    if( tag.hasNext() ) {
      tagBody = tag.nextLine();
    } else {
      tagBody = "";
    }

    if( stream.hasNext() ) {
      stream.next();
    }
    tag.close();
    return tagName.toLowerCase();
  }
  
  /* Returns the rest of the content of the current tag.
   * This is used strictly for debugging
   */
  public String getTagBody() {
    return tagBody;
  }
}


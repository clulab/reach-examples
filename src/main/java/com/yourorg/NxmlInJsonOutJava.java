package com.yourorg;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Map;

import edu.arizona.sista.reach.*;
import edu.arizona.sista.reach.apis.ApiRuler;

/**
 * Example Java program to call into the Reach system, providing NXML input and
 * receiving a JSON string in a result <tt>java.util.Map</tt>.
 */
class NxmlInJsonOutJava {

  public static void main (String[] args) throws Exception {
    // read the example NXML file whose content is to be passed to Reach:
    String nxml = readResource("inputs/nxml/PMC1240239.nxml");

    // The return value is a java.util.Map with information about the status of the call.
    // Valid JSON output formats are 'fries' and 'indexcard'.
    Map resultMap = ApiRuler.annotateText(nxml, "fries");

    // Check for a processing error on return
    if ((boolean)resultMap.get("hasError"))
      // if there was a processing error, retrieve the error message
      System.err.println(resultMap.get("errorMessage"));
    else
      // if there was not an error, then the result JSON is available
      System.out.println(resultMap.get("resultJson"));
  }


  /** Read the contents of a file on the classpath, returning it as a String. */
  private static String readResource (String path) throws Exception {
    URI resource = NxmlInJsonOutJava.class.getClassLoader().getResource(path).toURI();
    byte[] bytes = Files.readAllBytes(Paths.get(resource));
    return new String(bytes, Charset.defaultCharset());
  }

}

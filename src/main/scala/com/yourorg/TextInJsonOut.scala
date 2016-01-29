package com.yourorg

import java.io.{File, PrintWriter}

import scala.io.Source

import edu.arizona.sista.reach._
import edu.arizona.sista.reach.apis.ApiRuler

/**
  * Process input text to FRIES Json output.
  *   Example usage: sbt 'run-main com.yourog.TextInJsonOut outfile.json' < infile.txt
  *
  *   Author: Tom Hicks. 1/29/2016.
  *   Last Modified: Initial creation.
  */
object TextInJsonOut extends App {

  // gather the text to be passed to Reach:
  val text = io.Source.stdin.getLines.mkString

  // The return value is a java.util.Map[String,Any] with information about the status of the call.
  // Valid JSON output formats are 'fries' and 'indexcard'.
  val resultMap = ApiRuler.annotateText(text, "fries")

  // Check for a processing error on return
  if (resultMap.get("hasError").asInstanceOf[Boolean])
    System.err.println(Option(resultMap.get("errorMessage")).getOrElse("Something went wrong"))
  else {
    val jsonStr:String = resultMap.get("resultJson").asInstanceOf[String]
    if (!args.isEmpty)                      // if file name argument specified
      new PrintWriter(args(0)) { write(jsonStr); close } // write JSON to that file
    else                                    // else write JSON to standard output
      println(jsonStr)
  }
}

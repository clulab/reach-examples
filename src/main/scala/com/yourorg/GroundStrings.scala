package com.yourorg

import scala.io.Source

import edu.arizona.sista.reach._
import edu.arizona.sista.reach.grounding._

/**
 * Lookup grounding information for each string in a list of strings.
 * Author: Tom Hicks. 12/13/2015.
 * Last Modified: Update to optionally read model or use strings from standard input.
 */
object GroundStrings extends App {

  /** Specify the lookup tables to be loaded and the order in which they are searched. */
  val searchSequence =
    Seq(
      new StaticProteinFamilyKBLookup,
      new StaticProteinKBLookup,
      new StaticChemicalKBLookup,
      new StaticMetaboliteKBLookup,
      new StaticCellLocationKBLookup
    )

  /** A sample set of strings to be resolved by the grounding machinery. */
  val model = List("erk1", "RAS", "vanadocene", "eta-Tocopherol", "cell body", "NOT-IN-KB")

  /** Search the sequence of KBs for the given string, returning an optional tuple
      of KB namespace and reference ID, if a KB entry with the given key was found. */
  def resolveString (aString: String): Option[Tuple2[String, String]] = {
    searchSequence.foreach { kbLookup =>     // for each KB in the sequence
      val refID = kbLookup.resolve(aString)  // lookup the given string key
      if (!refID.isEmpty)                    // if an entry with that key is found in a KB
        return Some(Tuple2(kbLookup.namespace, refID.get)) // then return KB namespace and ID value
    }
    return None                             // else signal failure to find key string in any KB
  }

  /** Try to resolve the given string and print the result of attempt. */
  def resolveAndPrint (str: String): Unit = {
    val resolved = resolveString(str)
    if (resolved.isEmpty)
      println(s"String '${str}' => NOT FOUND")
    else
      println(s"String '${str}' => ${resolved.get._1}(${resolved.get._2})")
  }

  /** Try to resolve each of the strings in the sample model. */
  def doSampleModel (): Unit = {
    model.foreach(resolveAndPrint(_))
  }

  // Main method: resolve model strings or resolve each line of standard input
  var lineCount = 0
  val start = java.lang.System.currentTimeMillis()
  if (!args.isEmpty && args(0) == "model") {
    lineCount = model.size
    doSampleModel
  }
  else
    for (line <- io.Source.stdin.getLines) {
      resolveAndPrint(line)
      lineCount += 1
    }
  val stop = java.lang.System.currentTimeMillis()
  println(s"Resolution time for ${lineCount} lines: ${stop - start}ms")
}

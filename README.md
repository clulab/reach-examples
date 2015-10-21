# Reach Examples

This project provides some examples of how to interface with the CLULab Reach system in both Scala and Java.

Please see the [Reach GitHub](https://github.com/clulab/reach.git) for information on Reach itself.

## What you'll need...
  1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
  2. [sbt](http://www.scala-sbt.org/release/tutorial/Setup.html)
  3. [`reach`](https://github.com/clulab/reach.git).

Run any of the examples with `sbt run` or `sbt run-main`:

```
  > sbt 'run-main com.yourorg.TextInJsonOutJava'
```

Because Reach loads and uses several large NLP data models, we recommend that you allocate a sufficient amount of working memory for the process. We have observed no issues running with at least 5 gigabytes of heap:

  `JAVA_OPTS=-server -Xms3g -Xmx6g`

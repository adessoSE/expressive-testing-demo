# Expressive Testing Demo

Examples for demonstrating how to implement expressive tests using the following technologies:

* Java 8
* [JUnit 4](http://junit.org/junit4/)
* [Junit 5](http://junit.org/junit5/)
* [AssertJ](http://joel-costigliola.github.io/assertj/)

## Setup

1. Make sure Java 8 is installed:   
    `java -version`   
   should yield something like: java version "1.8.0_..."
2. Build the modules:   
    `gradlew build`
3. [Optional] Configure eclipse IDE:   
    `gradlew eclipse`


## Modules

* __system-under-test__   
    Implementation of an example system that is being tested.

* __tests-standard__   
    Demonstration of guidelines for implementing tests using JUnit 4.

* __tests-assertj__
    Demonstration of guidelines for implementing tests using AssertJ.

* __tests-junit5__   
    Demonstration of guidelines for implementing tests using JUnit 5.

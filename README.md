Simple Genetic Algorithm Visualization Tool
=================

A Java program for visualizing an implementation of the "Simple Genetic Algorithm."

http://en.wikipedia.org/wiki/Genetic_algorithm

The program runs on JRE 1.7+ and uses Swing.
The user can parameterize and view genetic algorithms as text, images, or animations.
###Usage
Compile:
```Shell
javac -d bin ./src/gaViz/*/*.java
```

Run:
```Shell
java -cp ./bin gaViz/main/Init
```

To experiment with visualizing the GA in 3D space using JavaFX (JRE 1.8+).
###Usage
Compile:
```Shell
javac -d bin ./src/*/*/*.java
```

Run:
```Shell
java -cp ./bin javafxDriver.Init
```
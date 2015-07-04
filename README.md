Simple Genetic Algorithm Visualization Tool
=================

A Java program for visualizing an implementation of the "Simple Genetic Algorithm."

http://en.wikipedia.org/wiki/Genetic_algorithm

Dependencies: JRE 1.8+

There are currently two different user interfaces, one for visualizing the algorithm in 2D space (made in swing), and another for visualizing it in 3D space (JavaFX).

To view genetic algorithms as text, 2D images, or 2D animations:
###Usage
Compile:
```Shell
javac -d bin ./src/gaViz/*/*.java
```

Run:
```Shell
java -cp ./bin gaViz/main/Init
```

To view the 3D version:
###Usage
Compile:
```Shell
javac -d bin ./src/*/*/*.java
```

Run:
```Shell
java -cp ./bin javafxDriver.Init
```
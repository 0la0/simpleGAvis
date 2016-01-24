Genetic Algorithm Visualization Tool
=================
A Java program for visualizing implementations of the "Simple Genetic Algorithm."

[http://en.wikipedia.org/wiki/Genetic_algorithm](http://en.wikipedia.org/wiki/Genetic_algorithm)

![screenshot](screenshots/visuallize-3d-5.png)
![screenshot](screenshots/visuallize-3d-4.png)
![screenshot](screenshots/visuallize-2d-animation-0.png)
![screenshot](screenshots/options-0.png)

### Dependencies:
JRE 1.8+

### Usage
Compile:
```sh
javac -d bin src/gaViz/**/*.java src/swingUiDriver/*.java src/javafxDriver/*.java
```

To view genetic algorithms as text, 2D images, or 2D animations:
```sh
java -cp ./bin swingUiDriver.Init
```

To visualize the algorithm in 3D space:
```sh
java -cp ./bin javafxDriver.Init
```

### About

More project documentation: [http://0la0.github.io/#!/projects/gaViz](http://0la0.github.io/#!/projects/gaViz)

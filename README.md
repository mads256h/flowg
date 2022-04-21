# flowg
FlowG is a language that greatly simplifies manual g-code programming.
It is a high-level language that supports functions, for loops, if statements etc.

## Building
We use maven to build this project.
To build this project run
```
mvn package
```
TODO: This should generate a jar file that can be run.


## Usage
To run the compiler use the following commandline:
```
java -jar flowgc.jar <input file> <output file>
```
The input file should contain the flowg source code you want to compile.
The output file will contain the gcode that the flowg source compiles to, if no errors occured.

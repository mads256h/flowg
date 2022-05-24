# flowg
FlowG is a language that greatly simplifies manual g-code programming.
It is a high-level language that supports functions, for loops, if statements etc.

## Prerequisites

### Windows 
Download and install both "Java JDK 17" and "Maven version 3.8.5."


### Ubuntu
```
sudo apt-get install openjdk-17-jdk
wget https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz -P /tmp
sudo tar xzvf /tmp/apache-maven-3.8.5-bin.tar.gz -C /opt/
sudo ln -s /opt/apache-maven-3.8.5/ /opt/maven
sudo bash -c 'echo "export M2_HOME=/opt/maven" > /etc/profile.d/maven.sh'
sudo bash -c 'echo "export MAVEN_HOME=/opt/maven" >> /etc/profile.d/maven.sh'
sudo bash -c 'echo "export PATH=\${M2_HOME}/bin:\${PATH}" >> /etc/profile.d/maven.sh'
sudo chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
```

### Arch Linux
```
sudo pacman -Suy maven
```

## Building
We use Maven to build this project.
To build this project run
```
mvn package
```
This creates an executable jar file named `flowg-1.0-SNAPSHOT-jar-with-dependencies.jar` in the `target/` directory.

## Usage
To run the compiler use the following commandline:
```
java -jar target/flowg-1.0-SNAPSHOT-jar-with-dependencies.jar <input file> <output file>
```

The input file should contain the flowg source code you want to compile.
The output file will contain the gcode, which the flowg source compiles to, if no errors occured.

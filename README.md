# JobAppli
Light weighted application written in Java, to keep record of applied job during job search. You can use it to add entry when you apply a new job, or update when hear back from a previous application.\
Please feel free to use it if you want to keep track of what job opportunity you have applied. Good luck on searching jobs.

## How to Use
### GUI Application
1. Compile GUI classes with command `javac src/gui/*.java`
2. Build JAR (Java Archive) using command `jar -cmf JobAppli.jar /META-INF/MANINFEST.MF *.class`
3. Run application with `java -jar JobAppli.jar`\
You can also compile/build jar with IDE or other build tools.
   
Note:\
1. Depending on java environment set up, user might need to specify JavaFX library.\
In the case of Java 11+, user can download JavaFX SDK from [Here](https://gluonhq.com/products/javafx/).
\
Put the unarchived directories at the path you want and set environmental variable with `export PATH_TO_FX=<path_to_sdk>/lib/`\
Then run the jar with `java --module-path $PATH_TO_FX --add-modules javafx.controls -jar Java_Playground.jar`
2. For Java 11+, JAXB is removed from the JDK completely. To allow support for JDXB, please refer to [These Solutions](https://www.jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/) to add dependency.
3. **I included pom file for maven build. Please use it to create jar file with needed dependencies!**

### Command Line Application
1. Compile main classes with command `javac src/main/*.java`
2. Run JobAppli with `java src/main/JobAppli.class`\
You can also compile with IDE or other build tools.

## Patch Note
### V 2.2
- Fixed bug for export function
- Fixed bug for delete function
- Added support to load data from exported txt file

### V 2.1
- Added textPrompt to the searchBar
- Updated searchBar user interaction

### V 2.0
- Added GUI for JobAppli
- Fixed bug

### V 1.0
- Uploaded first version of JobAppli
- Supported CLI based operation
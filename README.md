LangExec
===================
Programming Language compile and run library


## Get Started
Add maven dependancy
```xml
<dependency>
  <groupId>kr.devdogs</groupId>
  <artifactId>langexec</artifactId>
  <version>0.1</version>
</dependency>
```

## Available project
- Algorithm traning site
- Student assignment system

## Provided
- Source code compile & receive compile result message
- Running source code program & get output of std output
- Interactive shell of programs using standard std input

## Example
### Live Shell
Compile and Run a Java program that outputs the input as it is
```java
// program that outputs the input as it is
File sourceFile = new File("/Users/daniel/Test.java");
LanguageLiveShell shell = LiveShellFactory.getJavaLiveShell(sourceFile);

shell.addOnOutputListener(new CustomOnOutputListener() {
  @Override
  public void onOutput(String output) {
    System.out.println(output);
  }
});

try (Scanner scan = new Scanner(System.in)) {
  while (true) {
    String input = scan.nextLine();
    shell.writeLine(input);
    // input "Hi!" => print "Hi!"
  }
}
```


## Class Diagram

![alt text](https://github.com/JSDanielPark/langexec/blob/master/langexec.jpg?raw=true)

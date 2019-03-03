LangExec
===================
Programming Language compile and run library

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

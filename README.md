keeps-characterization-rtf
==========================

Characterization tool for rtf files, made by KEEP SOLUTIONS.


## Build & Use

To build the application simply clone the project and execute the following Maven command: `mvn clean package`  
The binary will appear at `target/rtf-characterization-tool-1.0-SNAPSHOT-jar-with-dependencies.jar`

To see the usage options execute the command:

```bash
$ java -jar target/rtf-characterization-tool-1.0-SNAPSHOT-jar-with-dependencies.jar -h
usage: java -jar [jarFile]
 -f <arg> file to analyze
 -h       print this message
 -v       print this tool version
```

## Tool Output Example
```bash
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<rtfCharacterizationResult>
    <validationInfo>
        <valid>true</valid>
    </validationInfo>
</rtfCharacterizationResult>



<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<rtfCharacterizationResult>
    <validationInfo>
        <valid>false</valid>
        <validationError>Unknown command c</validationError>
    </validationInfo>
</rtfCharacterizationResult>
```

## License

This software is available under the [LGPL version 3 license](LICENSE).


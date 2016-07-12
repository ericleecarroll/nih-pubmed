### Results

 - William Bensinger : 28
 - Leif Bergsagel : 5
 - Adam Cohen : 2
 - Hermann Einsele : 88
 - Amrita Krishnan : 21
 - Edward Libby : 2
 - Sagar Lonial : 153
 - Heinz Ludwig : 82
 - María-Victoria Mateos : 46
 - Philip McCarthy : 5
 - Vincent Rajkumar : 8
 - Paul Richardson : 126
 - Jatin Shah : 16
 - Saad Usmani : 13
 - David Vesole : 20
 - Ravi Vij : 63

### Compile
This is a maven project. From the project's root folder type:

> _mvn clean install_

This will build a JAR file, named _nih-pubmed-0.0.1-SNAPSHOT.jar_, in the _target_ folder.

### Run
Download the XML file you want to parse.  And then run the jar file, with the downloaded file as a single argument.
For example, with the XML file downloaded to the project's root folder:

> java -jar .\target\nih-pubmed-0.0.1-SNAPSHOT.jar .\pubmed_result.xml

I did not include the downloaded XML file in my zip.
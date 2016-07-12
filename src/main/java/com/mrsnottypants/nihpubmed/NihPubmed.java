package com.mrsnottypants.nihpubmed;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Usage: java -jar .\target\nih-pubmed-0.0.1-SNAPSHOT.jar .\pubmed_result.xml
 *
 * Created by Eric on 7/11/2016.
 */
public class NihPubmed {

    // authors we are interested in
    // could be refactored into a separate command-line input
    static final List<Author> AUTHORS = Arrays.asList(
            Author.of("William", "Bensinger"),
            Author.of("Leif", "Bergsagel"),
            Author.of("Adam", "Cohen"),
            Author.of("Hermann", "Einsele"),
            Author.of("Amrita", "Krishnan"),
            Author.of("Edward", "Libby"),
            Author.of("Sagar", "Lonial"),
            Author.of("Heinz", "Ludwig"),
            Author.of("María-Victoria", "Mateos"),
            Author.of("Philip", "McCarthy"),
            Author.of("Vincent", "Rajkumar"),
            Author.of("Paul", "Richardson"),
            Author.of("Jatin", "Shah"),
            Author.of("Saad", "Usmani"),
            Author.of("David", "Vesole"),
            Author.of("Ravi", "Vij")
    );

    /**
     * Usage: java -jar .\target\nih-pubmed-0.0.1-SNAPSHOT.jar .\pubmed_result.xml
     * @param args expected to have one entry, the input file
     */
	public static void main(String[] args) {

        // sanity check
        if (args.length != 1) {
            usage();
            return;
        }

        // open an auto-closing stream on the file contents
        Path path = Paths.get(args[0]);
        try (InputStream inputStream = Files.newInputStream(path)) {

            // get a handler that counts how often authors are listed in the author list of an article
            PubMedAuthorCountHandler handler = PubMedAuthorCountHandler.newInstance();

            // pass the input stream and handler to the parser
            PubMedResult.parse(inputStream, handler);

            // display count for each author of interest
            AUTHORS.stream().forEach(
                    author -> System.out.println(String.format("%s : %s", author, handler.getAuthorCount(author))));

        } catch (IOException ex) {
            System.out.println(
                    String.format("Cannot access %s : %s", path, ex.getMessage()));
        }
	}

    /**
     * Display usage
     */
    private static void usage() {
        System.out.println("usage: nih-pubmed-0.0.1-SNAPSHOT pubmed_result.xml");
    }
}

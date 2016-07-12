package com.mrsnottypants.nihpubmed;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Eric on 7/11/2016.
 */
public class PubMedAuthorCountHandlerTest {

    // test xml, with some elements we don't care about thrown in
    private final String INPUT =
            "<Test>" +
            "<Article>" +
            "<DateCreated>not important</DateCreated>" +
            "<AuthorList>" +
            "<Author>" +
            "<ForeName>Amy</ForeName>" +
            "<LastName>Brown</LastName>" +
            "</Author>" +
            "<Author>" +
            "<ForeName>Bob</ForeName>" +
            "<LastName>Green</LastName>" +
            "<Initials>BG</Initials>" +
            "</Author>" +
            "</AuthorList>" +
            "</Article>" +
            "<Article>" +
            "<AuthorList>" +
            "<Author>" +
            "<ForeName>Carol</ForeName>" +
            "<LastName>Grey</LastName>" +
            "</Author>" +
            "<Author>" +
            "<ForeName>Bob</ForeName>" +
            "<LastName>Green</LastName>" +
            "</Author>" +
            "</AuthorList>" +
            "</Article>" +
            "</Test>";

    @Test
    public void testPubMedResult() throws Exception {

        // turn the test XML into an auto-closing stream
        try (InputStream inputStream = new ByteArrayInputStream(INPUT.getBytes())) {

            // get a handler for counting authors
            PubMedAuthorCountHandler handler = PubMedAuthorCountHandler.newInstance();

            // parse!
            PubMedResult.parse(inputStream, handler);

            // confirm Amy Brown, Bob Green and Carol Grey
            assertEquals(1, handler.getAuthorCount(Author.of("Amy", "Brown")));
            assertEquals(2, handler.getAuthorCount(Author.of("Bob", "Green")));
            assertEquals(1, handler.getAuthorCount(Author.of("Carol", "Grey")));

            // confirm an unknown author doesn't bust anything
            assertEquals(0, handler.getAuthorCount(Author.of("David", "Blue")));
        }
    }
}
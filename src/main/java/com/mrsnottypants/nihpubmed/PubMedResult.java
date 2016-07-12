package com.mrsnottypants.nihpubmed;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * Exposes the static parse method, for parsing the contents provided by an input stream.
 *
 * Created by Eric on 7/11/2016.
 */
public class PubMedResult {

    /**
     * Use the given handler to parse the contents provided by the input stream
     * @param inputStream provides contents to parse
     * @param handler parses contents
     */
    public static void parse(InputStream inputStream, DefaultHandler handler) {
        parse(getParser(), inputStream, handler);
    }

    /**
     * Returns a new SAXParser
     * @return SAXParser
     */
    private static SAXParser getParser() {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            return saxParserFactory.newSAXParser();
        } catch (SAXException | ParserConfigurationException ex) {
            throw new PubMedResultException("cannot get a parser", ex);
        }
    }

    /**
     * Has the handler parse the contents provided by an input stream
     * @param parser parser
     * @param inputStream provides contents to parse
     * @param handler parses the contents
     */
    private static void parse(SAXParser parser, InputStream inputStream, DefaultHandler handler) {
        try {
            parser.parse(inputStream, handler);
        } catch (SAXException | IOException ex) {
            throw new PubMedResultException("cannot parse", ex);
        }
    }

    // no reason to instantiate this class
    private PubMedResult() {}
}

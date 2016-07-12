package com.mrsnottypants.nihpubmed;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * This handler counts how often each author is referenced in an article's author list.
 * Authors outside an article's author list (if there are any) are ignored.
 *
 * This handler uses the state pattern.
 * We start in the waiting-for-article state - when we see the 'Article' element start we transition to the
 * waiting-for-author-list state.  From there we transition to waiting-for-author, get-author, and so on.
 * See the inner HandlerState enum for the details.
 *
 * The author names and counts are collected by an instance of the inner Authors class.
 * The get-first-name and get-last-name states collect character data in the Authors instance.
 * When we see the 'Author' element end, we increment the count for the collected author.
 *
 * This handler could be further refactored to handle other parsing tasks.
 * The enum states and Authors instance would need to be refactored into a separate factory class.
 *
 * Created by Eric on 7/11/2016.
 */
public class PubMedAuthorCountHandler extends DefaultHandler {

    /**
     * Return a new instance of the PubMedResultHandler.
     * Note - we don't return a DefaultHandler since we expect the caller will need the getAuthorCount method.
     * @return new instance
     */
    public static PubMedAuthorCountHandler newInstance() {
        return new PubMedAuthorCountHandler();
    }

    /**
     * Return a count of how often this author appears in an article's author list.
     * @param author author of interest
     * @return count of how often author appears in an article's author list
     */
    public int getAuthorCount(Author author) {
        return authors.getAuthorCount(author);
    }

    // this is how we keep track of authors
    //
    private static class Authors {

        // map from author to how many times we called add() on the author
        private final Map<Author, Integer> counts;

        // the most recent first and last name we've seen
        private String firstName;
        private String lastName;

        // instantiates our map, resets the most recent first and last names
        //
        private Authors() {
            counts = new HashMap<>();
            reset();
        }

        // sets our most recent first name
        //
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        // sets our most recent last name
        //
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        // resets our most recent first and last names
        //
        private void reset() {
            firstName = null;
            lastName = null;
        }

        // creates an author object for the most recent first and last names
        // increments this author's count in our map
        // resets the most recent first and last name
        //
        public void add() {

            // increment author's count
            Author author = Author.of(firstName, lastName);
            counts.put(author, getAuthorCount(author) + 1);

            // prepare for next author
            reset();
        }

        // returns the count of how often we called add() on this author
        //
        public int getAuthorCount(Author author) {
            return counts.containsKey(author) ? counts.get(author) : 0;
        }
    }

    // common interface for the enum that describes our current state
    //
    private interface State {
        default Optional<State> startElement(String element, Authors authors) { return Optional.empty(); }
        default Optional<State> endElement(String element, Authors authors) { return Optional.empty(); }
        default boolean wantsCharacters() { return false; }
        default void characters(String value, Authors authors) { }
    }

    // states the handler can be in
    //
    private enum HandlerState implements State {

        // We are waiting for an article to start
        // When it does, we transition to waiting for an author list to start
        WAITING_FOR_ARTICLE {
            @Override
            public Optional<State> startElement(String element, Authors authors) {
                return Element.ARTICLE.isName(element) ? Optional.of(WAITING_FOR_AUTHOR_LIST) : Optional.empty();
            }
        },
        // We are waiting for an author list to start
        // When it does, we transition to waiting for an author to start
        // If we see the end of the article, we transition to waiting for another article
        WAITING_FOR_AUTHOR_LIST {
            @Override
            public Optional<State> startElement(String element, Authors authors) {
                return Element.AUTHOR_LIST.isName(element) ? Optional.of(WAITING_FOR_AUTHOR) : Optional.empty();
            }
            @Override
            public Optional<State> endElement(String element, Authors authors) {
                return Element.ARTICLE.isName(element) ? Optional.of(WAITING_FOR_ARTICLE) : Optional.empty();
            }
        },
        // We are waiting for an author to start
        // When it does, we transition to getting the author
        // If we see the end of the author list, we transition to waiting for another author list
        WAITING_FOR_AUTHOR {
            @Override
            public Optional<State> startElement(String element, Authors authors) {
                return Element.AUTHOR.isName(element) ? Optional.of(GET_AUTHOR) : Optional.empty();
            }
            @Override
            public Optional<State> endElement(String element, Authors authors) {
                return Element.AUTHOR_LIST.isName(element) ? Optional.of(WAITING_FOR_AUTHOR_LIST) : Optional.empty();
            }
        },
        // We are getting an author
        // If we see the first name start, we transition to getting the first name
        // If we see the last name start, we transition to getting the last name
        // If we see the end of the author, we add it to the authors count, and transition to waiting for another author
        GET_AUTHOR {
            @Override
            public Optional<State> startElement(String element, Authors authors) {
                if (Element.FIRST_NAME.isName(element)) {
                    return Optional.of(GET_FIRST_NAME);
                }
                else if (Element.LAST_NAME.isName(element)) {
                    return Optional.of(GET_LAST_NAME);
                }
                return Optional.empty();
            }
            @Override
            public Optional<State> endElement(String element, Authors authors) {
                if (Element.AUTHOR.isName(element)) {
                    authors.add();
                    return Optional.of(WAITING_FOR_AUTHOR);
                }
                return Optional.empty();
            }
        },
        // We are getting a first name
        // When we get it, we set it as the most recent author first name
        // If we see the end of the first name, we transition back to getting the author
        GET_FIRST_NAME {
            @Override
            public boolean wantsCharacters() {
                return true;
            }
            @Override
            public void characters(String value, Authors authors) {
                authors.setFirstName(value);
            }
            @Override
            public Optional<State> endElement(String element, Authors authors) {
                return Optional.of(GET_AUTHOR);
            }
        },
        // We are getting a last name
        // When we get it, we set it as the most recent author last name
        // If we see the end of the last name, we transition back to getting the author
        GET_LAST_NAME {
            @Override
            public boolean wantsCharacters() {
                return true;
            }
            @Override
            public void characters(String value, Authors authors) {
                authors.setLastName(value);
            }
            @Override
            public Optional<State> endElement(String element, Authors authors) {
                return Optional.of(GET_AUTHOR);
            }
        }
    }

    /**
     * Called each time an element starts.
     * We pass it to the current state object for processing.
     * If that returns a new state object, we transition to that state.
     *
     * @param uri not used
     * @param localName not used
     * @param qualifiedName element name
     * @param attributes not used
     */
    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) {

        // let state object look at the qualified name
        // if state object returns a non-empty state, transition to that state
        Optional<State> transition = state.startElement(qualifiedName, authors);
        transition.ifPresent(s -> state = s);
    }

    /**
     * Called each time an element ends.
     * We pass it to the current state object for processing.
     * If that returns a new state object, we transition to that state.
     *
     * @param uri not used
     * @param localName not used
     * @param qualifiedName element name
     */
    @Override
    public void endElement(String uri, String localName, String qualifiedName) {

        // let state object look at the qualified name
        // if state object returns a non-empty state, transition to that state
        Optional<State> transition = state.endElement(qualifiedName, authors);
        transition.ifPresent(s -> state = s);
    }

    /**
     * Called when there is character data available.
     * If the current state object indicates it wants character data, be pass the character data to the state object.
     *
     * @param chars character data
     * @param start index within chars where our data starts
     * @param length length of our data
     */
    @Override
    public void characters(char[] chars, int start, int length) {

        // ask the state object if it cares about character data
        // if so, form the string and pass it to the state object
        if (state.wantsCharacters()) {
            state.characters(new String(chars, start, length).trim(), authors);
        }
    }

    // holds authors and a count of how often they appear in an author's list
    private final Authors authors;

    // holds our current state object
    // as we find elements of interest we change our state object to handle the element
    private State state;

    // construct a new handler
    //
    private PubMedAuthorCountHandler() {
        authors = new Authors();
        state = HandlerState.WAITING_FOR_ARTICLE;
    }
}

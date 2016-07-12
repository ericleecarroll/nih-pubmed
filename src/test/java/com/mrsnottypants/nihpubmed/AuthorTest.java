package com.mrsnottypants.nihpubmed;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Eric on 7/11/2016.
 */
public class AuthorTest {

    @Test
    public void testAuthor() {

        Author amy1 = Author.of("Amy", "Brown");
        Author amy2 = Author.of("Amy", "Brown");
        Author bob = Author.of("Bob", "Green");

        // getters
        assertEquals("Amy", amy1.getFirstName());
        assertEquals("Brown", amy1.getLastName());

        // equals
        assertEquals(amy1, amy2);
        assertNotEquals(amy1, bob);

        // hash codes
        assertEquals(amy1.hashCode(), amy2.hashCode());
        assertNotEquals(amy1.hashCode(), bob.hashCode());

        // to string
        assertEquals("Amy Brown", amy1.toString());
    }

    @Test
    public void testNoFirstName() {
        Author amy1 = Author.of(null, "Brown");
        Author amy2 = Author.of(null, "Brown");
        Author bob = Author.of(null, "Green");

        // getters
        assertEquals("", amy1.getFirstName());
        assertEquals("Brown", amy1.getLastName());

        // equals
        assertEquals(amy1, amy2);
        assertNotEquals(amy1, bob);

        // hash codes
        assertEquals(amy1.hashCode(), amy2.hashCode());
        assertNotEquals(amy1.hashCode(), bob.hashCode());

        // to string
        assertEquals("Brown", amy1.toString());
    }

    @Test
    public void testNoLastName() {

        Author amy1 = Author.of("Amy", null);
        Author amy2 = Author.of("Amy", null);
        Author bob = Author.of("Bob", null);

        // getters
        assertEquals("Amy", amy1.getFirstName());
        assertEquals("", amy1.getLastName());

        // equals
        assertEquals(amy1, amy2);
        assertNotEquals(amy1, bob);

        // hash codes
        assertEquals(amy1.hashCode(), amy2.hashCode());
        assertNotEquals(amy1.hashCode(), bob.hashCode());

        // to string
        assertEquals("Amy", amy1.toString());
    }



    @Test
    public void testNoName() {

        Author amy1 = Author.of(null, null);
        Author amy2 = Author.of(null, null);
        Author bob = Author.of("Bob", "Green");

        // getters
        assertEquals("", amy1.getFirstName());
        assertEquals("", amy1.getLastName());

        // equals
        assertEquals(amy1, amy2);
        assertNotEquals(amy1, bob);

        // hash codes
        assertEquals(amy1.hashCode(), amy2.hashCode());
        assertNotEquals(amy1.hashCode(), bob.hashCode());

        // to string
        assertEquals("", amy1.toString());
    }
}
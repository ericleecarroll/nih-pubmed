package com.mrsnottypants.nihpubmed;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Eric on 7/11/2016.
 */
public class ElementTest {

    @Test
    public void testElement() {

        // get name
        assertEquals("Article", Element.ARTICLE.getName());

        // is name
        assertTrue(Element.ARTICLE.isName("Article"));
        assertFalse(Element.ARTICLE.isName("Author"));
    }
}
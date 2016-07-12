package com.mrsnottypants.nihpubmed;

/**
 * XML element names we care about
 *
 * Created by Eric on 7/11/2016.
 */
public enum Element {

    ARTICLE("Article"),
    AUTHOR_LIST("AuthorList"),
    AUTHOR("Author"),
    LAST_NAME("LastName"),
    FIRST_NAME("ForeName")
    ;

    private final String name;

    // Construct element
    //
    Element(String name) {
        this.name = name;
    }

    /**
     * Return the element name
     * @return element name
     */
    public String getName() {
        return name;
    }

    /**
     * Return true if the given name matches our name
     * @param name to match
     * @return true if it matches
     */
    public boolean isName(String name) {
        return this.name.equals(name);
    }
}

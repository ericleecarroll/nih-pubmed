package com.mrsnottypants.nihpubmed;

import java.util.Optional;

/**
 * Describes a single author
 *
 * Created by Eric on 7/11/2016.
 */
class Author {

    /**
     * Return an author for these names
     * @param firstName first name
     * @param lastName last name
     * @return author
     */
    public static Author of(String firstName, String lastName) {
        return new Author(firstName, lastName);
    }

    private final Optional<String> firstName;
    private final Optional<String> lastName;

    // construct an author
    //
    private Author(String firstName, String lastName) {

        this.firstName = Optional.ofNullable(firstName);
        this.lastName = Optional.ofNullable(lastName);
    }

    /**
     * Get the author's first name
     * @return first name
     */
    public String getFirstName() {
        return firstName.orElse("");
    }

    /**
     * Get the author's last name
     * @return last name
     */
    public String getLastName() {
        return lastName.orElse("");
    }

    /**
     * Return true if other author has the same first and last names
     * @param o other author
     * @return true if same names
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Author)) {
            return false;
        }
        Author a = (Author)o;
        return a.getFirstName().equals(getFirstName()) && a.getLastName().equals(getLastName());
    }

    /**
     * Return a hash code for this author
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 + result + getFirstName().hashCode();
        result = 31 + result + getLastName().hashCode();
        return result;
    }

    /**
     * Return human-readable author name: "first last"
     * @return human-readable author name
     */
    @Override
    public String toString() {
        return String.format("%s %s", getFirstName(), getLastName()).trim();
    }
}

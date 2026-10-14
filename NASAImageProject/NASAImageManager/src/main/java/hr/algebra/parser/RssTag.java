/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hr.algebra.parser;

import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author vitoz
 */
public enum RssTag {
    ITEM("item"),
    TITLE("title"),
    LINK("link"),
    DESCRIPTION("description"),
    ENCLOSURE("enclosure"),
    PUB_DATE("pubDate");

    private final String tagName;

    private RssTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public static Optional<RssTag> from(String tagName) {
        return Arrays.stream(values())
                .filter(tag -> tag.getTagName().equalsIgnoreCase(tagName))
                .findFirst();
    }
}

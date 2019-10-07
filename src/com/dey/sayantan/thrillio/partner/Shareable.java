package com.dey.sayantan.thrillio.partner;

/**
 * This class represent the extra feature to be able to share bookmarks
 */
public interface Shareable {

    /**
     * implementing class should use this method to generate bookmark item Data in XML/JSON format
     * @return  String: bookmark data in xml/json format
     */
    String getItemData();
}

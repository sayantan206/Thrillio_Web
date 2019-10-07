package com.dey.sayantan.thrillio.entities;

import com.dey.sayantan.thrillio.partner.Shareable;
import org.apache.commons.lang3.StringUtils;

public class WebLink extends Bookmark implements Shareable {
    private String url;
    private String host;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    /**
     * method returns false if "porn" in {url, title} or "adult" in {host}
     * else true
     */
    public boolean isKidFriendlyEligible() {
        if(StringUtils.containsIgnoreCase(url,"porn") || StringUtils.containsIgnoreCase(getTitle(), "porn")
            || StringUtils.containsIgnoreCase(host, "adult"))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WebLink{" +
                super.toString()+
                ",url='" + url + '\'' +
                ", host='" + host + '\'' +
                '}';
    }

    @Override
    public String getItemData() {
        StringBuilder itemData = new StringBuilder();

        itemData.append("<item>");
        itemData.append("<type>WebLink</type>");
        itemData.append("<title>").append(getTitle()).append("</title>");
        itemData.append("<url>").append(url).append("</url>");
        itemData.append("<host>").append(host).append("</host>");
        itemData.append("</item>");

        return itemData.toString();
    }
}

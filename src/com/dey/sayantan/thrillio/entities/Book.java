package com.dey.sayantan.thrillio.entities;

import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.partner.Shareable;

import java.util.Arrays;

public class Book extends Bookmark implements Shareable {
    private int publicationYear;
    private String publisher;
    private String[] authors;
    private BookGenre genre;
    private double amazonRating;
    private String imageUrl;

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public double getAmazonRating() {
        return amazonRating;
    }

    public void setAmazonRating(double amazonRating) {
        this.amazonRating = amazonRating;
    }

    /**
     * @return false if Genre is Philosophy or self help, else return true
     */
    @Override
    public boolean isKidFriendlyEligible() {
        if (genre == BookGenre.PHILOSOPHY ||
                genre == BookGenre.SELF_HELP)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + super.toString() +
                ",publicationYear=" + publicationYear +
                ", publisher='" + publisher + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", genre='" + genre + '\'' +
                ", amazonRating=" + amazonRating +
                '}';
    }

    @Override
    public String getItemData() {
        StringBuilder itemData = new StringBuilder();

        itemData.append("<item>");
        itemData.append("<type>Book</type>");
        itemData.append("<title>").append(getTitle()).append("</title>");
        itemData.append("<publisher>").append(publisher).append("</publisher>");
        itemData.append("<publicationYear>").append(publicationYear).append("</publicationYear>");
        itemData.append("<array name= \"authors\">");
        for (String author : authors)
            itemData.append("<author>").append(author).append("</author>");
        itemData.append("</array>");
        itemData.append("<genre>").append(genre).append("</genre>");
        itemData.append("<amazonRating>").append(amazonRating).append("</amazonRating>");
        itemData.append("</item>");

        return itemData.toString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

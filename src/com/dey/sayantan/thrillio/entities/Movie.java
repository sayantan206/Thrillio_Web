package com.dey.sayantan.thrillio.entities;

import com.dey.sayantan.thrillio.constants.MovieGenre;

import java.util.Arrays;

public class Movie extends Bookmark {
    private int releaseYear;
    private String[] cast;
    private String[] directors;
    private MovieGenre genre;
    private double imdbRating;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     * @return false if movie is of Horror or Thriller genre, else return true
     */
    @Override
    public boolean isKidFriendlyEligible() {
        if(genre == MovieGenre.HORROR || genre == MovieGenre.THRILLERS)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Movie{"+ super.toString() +
                ",releaseYear=" + releaseYear +
                ", cast=" + Arrays.toString(cast) +
                ", directors=" + Arrays.toString(directors) +
                ", genre='" + genre + '\'' +
                ", imdbRating=" + imdbRating +
                '}';
    }
}

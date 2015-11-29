package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.os.Parcel;
import android.os.Parcelable;
import org.joda.time.DateTime;
/**
 * Created by kokotpica on 9.11.15.
 */
public class Film {
    private String poster_path, backdrop_path, overview, title;
    private String release_date;
    private double popularity;
    private Long id;

    public Film(String poster_path, String backdrop_path, String overview, String title, double popularity,String dati,Long id) {
        this.poster_path = poster_path;
        this.id=id;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.title = title;
        this.release_date=dati;
        this.popularity = popularity;
    }

    public Film() {

    }

    public String getDati() {
        return release_date;
    }

    public void setDati(String dati) {
        this.release_date = dati;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }


}
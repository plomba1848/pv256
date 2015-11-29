package cz.muni.fi.pv256.movio.uco410502.filmmanager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410502.filmmanager.FilmContract.FilmEntry;

/**
 * Created by kokotpica on 22.11.15.
 */
public class FilmDbManager {
    public static final int COL_ID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_BPATH = 2;
    public static final int COL_PPATH = 3;
    public static final int COL_TITLE = 4;
    public static final int COL_OVERVIEW = 5;
    public static final int COL_POP = 6;
    private static final String[] COLUMNS = {
            FilmEntry._ID,
            FilmEntry.COLUMN_DATE_TEXT,
            FilmEntry.COLUMN_BPATH,
            FilmEntry.COLUMN_PPATH,
            FilmEntry.COLUMN_TITLE_TEXT,
            FilmEntry.COLUMN_OVERVIEW_TEXT,
            FilmEntry.COLUMN_POPULARITY
    };

    private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

    private static final String WHERE_ID = FilmEntry._ID + " = ?";

    private Context mContext;

    public FilmDbManager(Context context) {
        mContext = context.getApplicationContext();
    }


    public void createFilm(Film film) {
        if (film == null) {
            throw new NullPointerException("film == null");
        }



        film.setId(ContentUris.parseId(mContext.getContentResolver().insert(FilmEntry.CONTENT_URI, prepareFilmValues(film))));
    }


    public ArrayList<Film> getAll(){
        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, COLUMNS, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<Film> films = new ArrayList<Film>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    films.add(getFilm(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return films;
        }

        return new ArrayList<Film>();
    }


    public void updateFilm(Film film) {
        if (film == null) {
            throw new NullPointerException("film == null");
        }



        mContext.getContentResolver().update(FilmEntry.CONTENT_URI, prepareFilmValues(film), WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    public void deleteFilm(Film film) {
        if (film == null) {
            throw new NullPointerException("film == null");
        }
        if (film.getId() == null) {
            throw new IllegalStateException("film id cannot be null");
        }

        mContext.getContentResolver().delete(FilmEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    private ContentValues prepareFilmValues(Film film) {
        ContentValues values = new ContentValues();
        values.put(FilmEntry._ID,film.getId());
        values.put(FilmEntry.COLUMN_DATE_TEXT, film.getDati());
        values.put(FilmEntry.COLUMN_BPATH, film.getBackdrop_path());
        values.put(FilmEntry.COLUMN_PPATH, film.getPoster_path());
        values.put(FilmEntry.COLUMN_TITLE_TEXT, film.getTitle());
        values.put(FilmEntry.COLUMN_OVERVIEW_TEXT, film.getOverview());
        values.put(FilmEntry.COLUMN_POPULARITY, film.getPopularity());
        return values;
    }

    private Film getFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(COL_ID));
        film.setOverview(cursor.getString(COL_OVERVIEW));
        film.setBackdrop_path(cursor.getString(COL_BPATH));
        film.setPoster_path(cursor.getString(COL_PPATH));
        film.setTitle(cursor.getString(COL_TITLE));
        film.setPopularity(cursor.getDouble(COL_POP));
        film.setDati(cursor.getString(COL_DATE));

        return film;
    }
}

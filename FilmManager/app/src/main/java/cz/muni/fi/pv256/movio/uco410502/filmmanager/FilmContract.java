package cz.muni.fi.pv256.movio.uco410502.filmmanager;

/**
 * Created by kokotpica on 22.11.15.
 */
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class FilmContract {
    public static final String CONTENT_AUTHORITY = "sk.last.films";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WORK_TIME = "movies";

    public static final String DATE_FORMAT = "yyyyMMddHHmm";

    /**
     * Converts Date class to a string representation, used for easy comparison and database
     * lookup.
     *
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(DateTime date) {
        return date.toString(DATE_FORMAT);
    }

    /**
     * Converts a dateText to a long Unix time representation
     *
     * @param dateText the input date string
     * @return the Date object
     */
    public static DateTime getDateFromDb(String dateText) {
        return DateTime.parse(dateText, DateTimeFormat.forPattern(DATE_FORMAT).withOffsetParsed());
    }

    public static final class FilmEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_TIME).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;


        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_DATE_TEXT = "dati";
        public static final String COLUMN_BPATH = "bpath";
        public static final String COLUMN_PPATH = "ppath";
        public static final String COLUMN_TITLE_TEXT = "title";
        public static final String COLUMN_OVERVIEW_TEXT = "overview";
        public static final String COLUMN_POPULARITY = "popularity";

        public static Uri buildFilmUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}


package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cz.muni.fi.pv256.movio.uco410502.filmmanager.FilmContract.FilmEntry;
/**
 * Created by kokotpica on 22.11.15.
 */
public class FilmHelper extends SQLiteOpenHelper {
    public FilmHelper(Context c){
        super(c,"filmdb",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmEntry.TABLE_NAME + " (" +
                FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmEntry.COLUMN_DATE_TEXT + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_BPATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_PPATH + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_TITLE_TEXT + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_OVERVIEW_TEXT + " TEXT NOT NULL, " +
                FilmEntry.COLUMN_POPULARITY + " INTEGER )";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FilmEntry.TABLE_NAME);
        onCreate(db);
    }
}

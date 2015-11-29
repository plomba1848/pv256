package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kokotpica on 29.11.15.
 */
public class FilmSyncService extends Service {

    private static final Object LOCK = new Object();
    private static FilmSyncAdapter filmSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (LOCK) {
            if (filmSyncAdapter == null) {
                filmSyncAdapter = new FilmSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

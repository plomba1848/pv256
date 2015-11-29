package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by kokotpica on 29.11.15.
 */
public class FilmAuthenticatorService extends Service {
    private FilmAuthenticator mauth;

    @Override
    public void onCreate(){
        mauth=new FilmAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

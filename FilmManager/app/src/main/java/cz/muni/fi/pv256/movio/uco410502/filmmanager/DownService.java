package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by kokotpica on 22.11.15.
 */
public class DownService extends IntentService {
    public DownService(){
        super("DownService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(new CFactory())
                .build();

        ArrayList<Film> fls=null;
        MovieInterface service = retrofit.create(MovieInterface.class);
        try {
            fls=service.list().execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        FilmAdapterParser fp=new FilmAdapterParser();
        Bundle b=new Bundle();
        ArrayList<String> strs=new ArrayList<>();
        for(Film f:fls){
            strs.add(fp.tojson(f));
        }
        b.putStringArrayList("res",strs);
        receiver.send(0,b);
        //System.out.println(fls.get(0).getTitle());

    }
}

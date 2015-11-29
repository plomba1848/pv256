package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by kokotpica on 22.11.15.
 */
public interface MovieInterface {
    @GET("/3/discover/movie?api_key=4f3739d48a296391ab908486880ebbdd")
    Call<ArrayList<Film>> list();

}

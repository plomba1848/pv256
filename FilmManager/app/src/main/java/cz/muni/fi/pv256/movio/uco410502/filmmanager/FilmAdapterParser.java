package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by kokotpica on 9.11.15.
 */
public class FilmAdapterParser extends AdapterParser<Film> {


    public FilmAdapterParser(){
        this(null,0,null);
    }
    public FilmAdapterParser(Film[] films, int layid, Context c) {
        super(films, layid, c);
    }

    @Override
    public Film fromjson(String s) {
      Gson g=new Gson();
        return g.fromJson(s,Film.class);


       /* try {
            JSONObject js =new JSONObject(s);
            Film f=new Film(js.getString("poster_path"),js.getString("backdrop_path"),
                    js.getString("overview"),js.getString("title"),js.getDouble("popularity"));
            return f;
        } catch (JSONException e) {
            return new Film("a","b","c","d",5);
        }*/

    }



    @Override
    public String tojson(Film oo) {
        Gson g=new Gson();
        String s=g.toJson(oo);

        return s;
       // return new String("{\"poster_path\":\""+oo.getSmallimg()+ "\",\"backdrop_path\":\""
         //       +oo.getWideimg()+"\",\"overview\":\""+oo.getOverview()+"\"}")
    }

    @Override
    public View xgetview(int position, View convertView, ViewGroup parent) {
        ImageView tx=(ImageView)convertView.findViewById(R.id.grimg);
        Picasso.with(c).load("https://image.tmdb.org/t/p/w185"+os[position].getPoster_path()).into(tx);
        return convertView;
    }
}

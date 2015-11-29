package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit.Converter;

/**
 * Created by kokotpica on 22.11.15.
 */
public class CFactory extends Converter.Factory {
    @Override
    public Converter<ResponseBody, ArrayList<Film>> fromResponseBody(Type type, Annotation[] annotations) {
        Converter<ResponseBody,ArrayList<Film>> cv=new Converter<ResponseBody, ArrayList<Film>>() {
            @Override
            public ArrayList<Film> convert(ResponseBody value) throws IOException {
                String str=value.string();
                ArrayList<Film> zoznam=new ArrayList<>();
                FilmAdapterParser fp=new FilmAdapterParser();
                try {
                    JSONObject js=new JSONObject(str);
                    JSONArray ar=js.getJSONArray("results");
                    for(int i=0;i<ar.length();i++){
                        zoznam.add(fp.fromjson(ar.getJSONObject(i).toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return zoznam;
            }
        };
                return cv;
    }
}

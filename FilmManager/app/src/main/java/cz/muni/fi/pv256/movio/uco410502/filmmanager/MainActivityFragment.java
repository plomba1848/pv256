package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410502.filmmanager.DownReceiver.Receiver;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements Receiver,OnSwapListener {

    GridView grid;
    private FilmAdapterParser fp;
    List<Film> zoznam;
    boolean dis;
    OnClickGridListener listener;
    private FilmDbManager man;

    public MainActivityFragment() {
        dis=true;
    }

    public static MainActivityFragment newInstance(ArrayList<Film> datas){
        MainActivityFragment fr=new MainActivityFragment();
        fr.setDis(false);
        Bundle args = new Bundle();
        FilmAdapterParser fp=new FilmAdapterParser();
        ArrayList<String> strs=new ArrayList<>();
        for(Film f:datas){
            strs.add(fp.tojson(f));
        }
        args.putStringArrayList("datas", strs);
        fr.setArguments(args);
        return fr;
    }

    public void setListener(OnClickGridListener listener){
        this.listener=listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_main, container, false);

        grid =(GridView)v.findViewById(R.id.gridw);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onClick(fp.getItem(position));
            }
        });
        if(fp!=null){
            grid.setAdapter(fp);
        }

        return v;
    }

    public void onSwap(){
        dis=!dis;
        if (dis) {
            Intent i = new Intent((Activity) listener, DownService.class);
            DownReceiver dr = new DownReceiver(new Handler());
            dr.setReceiver(this);
            i.putExtra("receiver", dr);
            ((Activity) listener).startService(i);
        }
        else {
            zoznam=man.getAll();
            Film f[]=new Film[zoznam.size()];
            zoznam.toArray(f);
            fp=new FilmAdapterParser(f,R.layout.griditem,getContext());
            grid.setAdapter(fp);
        }
    }

    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);

        man=new FilmDbManager((Activity)listener);


          /*new ApiCalls("",""){

            @Override
            public void doAfter(String s) {
                try {
                    JSONObject js=new JSONObject(s);
                    JSONArray ja=js.getJSONArray("results");
                    zoznam=new ArrayList<Film>();
                    for(int i=0;i<ja.length();i++){

                        zoznam.add(fp.fromjson(ja.getJSONObject(i).toString()));
                    }
                    Film f[]=new Film[zoznam.size()];
                    zoznam.toArray(f);
                    fp=new FilmAdapterParser(f,R.layout.griditem,getContext());
                    grid.setAdapter(fp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.go();*/

        if (dis) {
            Intent i = new Intent((Activity) listener, DownService.class);
            DownReceiver dr = new DownReceiver(new Handler());
            dr.setReceiver(this);
            i.putExtra("receiver", dr);
            ((Activity) listener).startService(i);
        }
        else {
            zoznam=man.getAll();
            Film f[]=new Film[zoznam.size()];
            zoznam.toArray(f);
            fp=new FilmAdapterParser(f,R.layout.griditem,getContext());
        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setListener((OnClickGridListener) activity);
        MainActivity ma=(MainActivity)activity;
        ma.setOnSwapListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;

    }

    public boolean isDis() {
        return dis;
    }

    public void setDis(boolean dis) {
        this.dis = dis;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        FilmAdapterParser fap=new FilmAdapterParser();
        ArrayList<String> strs=resultData.getStringArrayList("res");
        zoznam=new ArrayList<>();
        for(String s:strs){
            zoznam.add(fap.fromjson(s));
        }
        Film f[]=new Film[zoznam.size()];
        zoznam.toArray(f);
        fp=new FilmAdapterParser(f,R.layout.griditem,getContext());
        if(grid!=null) {
            grid.setAdapter(fp);
        }
    }

    public interface OnClickGridListener{
        public void onClick(Film f);
    }
}

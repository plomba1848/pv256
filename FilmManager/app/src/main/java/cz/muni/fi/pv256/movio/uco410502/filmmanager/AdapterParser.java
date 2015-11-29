package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.gson.Gson;

/**
 * Created by kokotpica on 9.11.15.
 */
public abstract class AdapterParser<o> extends BaseAdapter {


    public abstract o fromjson(String s);
    public abstract String tojson(o oo);

    public abstract View xgetview(int position,View convertView,ViewGroup parent);

    o os[];
    int layid;
    Context c;


    public AdapterParser(o os[], int layid, Context c) {
        this.os = os;
        this.layid = layid;
        this.c = c;
    }


    @Override
    public int getCount() {
        return os.length;
    }

    @Override
    public o getItem(int position) {
        return os[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            v=inflater.inflate( layid, parent, false);
        }else{
            v=convertView;
        }
        xgetview(position,v,parent);
        return v;
    }
}

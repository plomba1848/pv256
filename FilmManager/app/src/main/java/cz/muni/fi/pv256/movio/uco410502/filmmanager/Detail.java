package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Detail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Detail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean is;
    private OnFragmentInteractionListener ac;
    Film film;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Detail.
     */
    // TODO: Rename and change types and number of parameters
    public static Detail newInstance(String param1, String param2) {
        Detail fragment = new Detail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    public Detail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            FilmAdapterParser fp=new FilmAdapterParser();
            film=fp.fromjson(mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.detail, container, false);

        TextView txt=(TextView)v.findViewById(R.id.dettitle);
        txt.setText(film.getTitle());

        TextView rok=(TextView)v.findViewById(R.id.dati);
        rok.setText(film.getDati());

        TextView over=(TextView)v.findViewById(R.id.over);
        over.setText(film.getOverview());

        TextView pop=(TextView)v.findViewById(R.id.pop);
        pop.setText(film.getPopularity()+"");

        ImageView top=(ImageView)v.findViewById(R.id.topimg);
        ImageView small=(ImageView)v.findViewById(R.id.smallimg);

        Picasso.with((Activity)ac).load("https://image.tmdb.org/t/p/w185" + film.getPoster_path()).into(small);
        Picasso.with((Activity)ac).load("https://image.tmdb.org/t/p/w780" + film.getBackdrop_path()).into(top);

        final Button but=(Button)v.findViewById(R.id.buttondaco);
        if(Stat.ids.contains(film.getId())){
            but.setText("ODOBRAT");
            is=true;
        }
        else {
            is=false;
            but.setText("PRIDAT");
        }
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is){
                    ac.onRemoveFilm(film);
                    is=false;
                    but.setText("PRIDAT");
                }
                else{
                    ac.onAddFilm(film);
                    is=true;
                    but.setText("ODOBRAT");
                }
            }
        });

        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         ac=(OnFragmentInteractionListener)activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ac=null;

    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onAddFilm(Film film);
        public void onRemoveFilm(Film film);
    }

}

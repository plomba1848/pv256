package cz.muni.fi.pv256.movio.uco410502.filmmanager;



import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnClickGridListener,Detail.OnFragmentInteractionListener {
    MainActivityFragment frag;
    OnSwapListener swaper;
    private FilmDbManager man;
    private boolean disc;
    private boolean tablet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        man=new FilmDbManager(this);
        Stat.ids=new ArrayList<>();
        ArrayList<Film> films=man.getAll();
        for(Film f:films){
            Stat.ids.add(f.getId());
        }
        tablet=getResources().getBoolean(R.bool.isTablet);
        if(tablet){
            setContentView(R.layout.main2);
        }
        else {
            setContentView(R.layout.activity_main);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(savedInstanceState==null){


            frag = new MainActivityFragment();
            // frag.setListener(this);
            fragmentTransaction.add(R.id.frametop, frag, "UUU");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // fragmentTransaction.addToBackStack("WII");

            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();

        }
        FilmSyncAdapter.initializeSyncAdapter(this);
        FilmSyncAdapter.syncImmediately(this);

    }

    public void setOnSwapListener(OnSwapListener lis){
        this.swaper=lis;
    }


    @Override
    public void onSaveInstanceState(Bundle bun){
        super.onSaveInstanceState(bun);
      bun.putBoolean("disc", disc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_swap){
            swaper.onSwap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Film f) {
       
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Detail fragment = Detail.newInstance(new FilmAdapterParser().tojson(f),null);
        System.out.println("IIII");
        if(tablet) {
            fragmentTransaction.replace(R.id.detail, fragment, "UUU");
        }
        else{
            fragmentTransaction.replace(R.id.frametop, fragment, "UUU");
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack("WII");
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }




    @Override
    public void onAddFilm(Film film) {
        man.createFilm(film);
        Stat.ids.add(film.getId());
    }

    @Override
    public void onRemoveFilm(Film film) {
        man.deleteFilm(film);
        Stat.ids.remove(film.getId());
    }

}

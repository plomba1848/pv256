package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Retrofit;

/**
 * Created by kokotpica on 29.11.15.
 */
public class FilmSyncAdapter extends AbstractThreadedSyncAdapter {

    public static final int SYNC_INTERVAL = 30;//60 * 60 * 24; //day
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final String CONTENT_AUTHORITY = "sk.last.films";

    public FilmSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public FilmSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
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
        System.out.println("pena");

    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getAccount();
        String authority = "sk.last.films";

            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);

    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getAccount(), "sk.last.films", bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    public static Account getAccount(){
        final String what="FALngdfLAniLA";
        return new Account(what, "sk.last.films");
    }


    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one if the
     * fake account doesn't exist yet.  If we make a new account, we call the onAccountCreated
     * method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account account = getAccount();

        if (accountManager.addAccountExplicitly(account, null, null)) {
            // Inform the system that this account supports sync
            ContentResolver.setIsSyncable(account, CONTENT_AUTHORITY, 1);
            // Inform the system that this account is eligible for auto sync when the network is up
            ContentResolver.setSyncAutomatically(account, CONTENT_AUTHORITY, true);
            // Recommend a schedule for automatic synchronization. The system may modify this based
            // on other scheduled syncs and network utilization.
            ContentResolver.addPeriodicSync(
                    account, CONTENT_AUTHORITY, new Bundle(), 10);

        }
        // If the password doesn't exist, the account doesn't exist


        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */


        return account;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        FilmSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, "sk.last.films", true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }
}

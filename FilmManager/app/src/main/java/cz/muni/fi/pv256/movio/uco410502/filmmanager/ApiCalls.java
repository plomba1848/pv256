package cz.muni.fi.pv256.movio.uco410502.filmmanager;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

/**
 * Created by kokotpica on 25.10.15.
 */
public abstract class ApiCalls {
    String json,url;
    public static String token="4f3739d48a296391ab908486880ebbdd";
    public static String host="http://api.themoviedb.org/3/discover/movie?api_key=4f3739d48a296391ab908486880ebbdd";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static boolean logged=false;
    public ApiCalls(String json, String url){
        this.json=json;
        this.url=url;
    }
    public void go(){
        new PostRequest().execute();
    }
    class PostRequest extends AsyncTask<String,Void,String>{




        @Override
        protected String doInBackground(String... params) {
            RequestBody body = RequestBody.create(JSON, json.toString());
            OkHttpClient client=new OkHttpClient();
            Log.v("boja", json.toString());

            Request.Builder requestb = new Request.Builder()
                    .url(host)

                    .addHeader("Accept","*/*");

            if(logged){
                requestb.header("Authorization","Token "+token);

            }

            Request request=requestb.build();
    String str=null;
            try {
                Response response = client.newCall(request).execute();

               str=response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(str);
            return  str;
        }

        @Override
        protected void onPostExecute(String s) {
            doAfter(s);
        }
    }
    public abstract void doAfter(String s);

    public static void photoAjson(String url,File f,String  json) throws IOException {
        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)

                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"filee\"; filename=\"pniiik.jpg\""),
                        (f==null)?RequestBody.create(JSON,"nist"):RequestBody.create(MediaType.parse("image/jpg"), f))

                .addPart(Headers.of("Content-Disposition", "form-data; name=\"json\""),
                        RequestBody.create(JSON,json))
                .build();
        OkHttpClient client=new OkHttpClient();

        Request.Builder requestb = new Request.Builder()
                .url(host+url)
                .addHeader("Connection","Keep-Alive")
                .addHeader("User-agent", "AndroidMeetApp");




        if(logged){
            requestb.header("Authorization","Token "+token);

        }
        requestb.post(body);
        Request request=requestb.build();
        System.out.println(request.headers().toString());
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public static void postImage(String url,File f) throws IOException {
        RequestBody body = new MultipartBuilder()
                .type(MultipartBuilder.FORM)

                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"filee\"; filename=\"pniiik.jpg\""),
                        RequestBody.create(MediaType.parse("image/jpg"), f))


                .build();
        OkHttpClient client=new OkHttpClient();

        Request.Builder requestb = new Request.Builder()
                .url(host+url)
                .addHeader("Connection","Keep-Alive")
                .addHeader("User-agent", "AndroidMeetApp");




        if(logged){
            requestb.header("Authorization","Token "+token);

        }
        requestb.post(body);
        Request request=requestb.build();
        System.out.println(request.headers().toString());
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}

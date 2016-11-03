package mx.uv.varappmiento.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * Created by VarAppmiento on 20/05/2016.
 */
public class NetworkConnection {

    public static Integer NO_CONNECTED = 0;
    public static Integer CONNECTED = 1;
    public static Integer WIFI = 2;
    public static Integer DATA = 3;
    private String response = null;
    private Context context = null;
    public static String baseUrl = "https://www.google.com.mx/";


    public NetworkConnection(Context context)
    {
        this.context = context;
    }
    public Integer typeConnection()
    {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if(isWifiConn )
            return WIFI;
        if(isMobileConn)
            return DATA;
        return NO_CONNECTED;
    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

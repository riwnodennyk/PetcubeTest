package kulku.ua.petcubetest.server;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.net.ConnectException;
import java.net.UnknownHostException;

import kulku.ua.petcubetest.BuildConfig;
import kulku.ua.petcubetest.R;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

/**
 * Created by aindrias on 12.03.2015.
 */
public class Backend {
    public static final Api API = new RestAdapter.Builder()
            .setEndpoint("http://162.243.129.214")
            .setConverter(new GsonConverter(
                    new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()))
            .build()
            .create(Api.class);
    public static final String LOG_TAG = Backend.class.getSimpleName();
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static int getErrorMessage(RetrofitError error) {
        if (DEBUG)
            Log.d(LOG_TAG, error.toString());
        Throwable cause = error.getCause();
        if (cause != null) {
            if (cause instanceof ConnectException || cause instanceof UnknownHostException) {
                return R.string.no_server_connection;
            }
        }
        return R.string.loading_error;
    }
}

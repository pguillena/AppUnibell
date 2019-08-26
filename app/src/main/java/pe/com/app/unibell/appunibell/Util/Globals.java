package pe.com.app.unibell.appunibell.Util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Globals extends Application {
    public Object IntentCobranzaCab;
    public Object IntentAdapterMenuString;
    private static Globals instance = new Globals();

    public static void setInstance(Globals instance) {
        Globals.instance = instance;
    }

    public static Globals getInstance() {
        return instance;
    }

    public Object getIntentCobranzaCab() {
        return IntentCobranzaCab;
    }

    public void setIntentCobranzaCab(Object intentCobranzaCab) {
        IntentCobranzaCab = intentCobranzaCab;
    }

    public Object getIntentAdapterMenuString() {
        return IntentAdapterMenuString;
    }

    public void setIntentAdapterMenuString(Object intentAdapterMenuString) {
        IntentAdapterMenuString = intentAdapterMenuString;
    }

    public static boolean isConnectingToInternet(Context _context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}

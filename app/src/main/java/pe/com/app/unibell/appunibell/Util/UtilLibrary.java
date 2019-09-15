package pe.com.app.unibell.appunibell.Util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import pe.com.app.unibell.appunibell.Main.Activity_Login;

/**
 * Created by RENAN on 18/08/2016.
 */
public class UtilLibrary {
    public static String fnBattery(Context paramContext){
        Intent localIntent = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int i = Math.round(localIntent.getIntExtra("level", 0) / localIntent.getIntExtra("scale", 0));
        return String.valueOf(i);
    }

    public static int fnBattery(Context paramContext, int paramInt){
        Intent localIntent = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        float f1 = localIntent.getIntExtra("level", 0);
        float f2 = localIntent.getIntExtra("scale", 0);
        int i = Math.round(f1 * paramInt / f2);
        return i;
    }

    public static String fnModeloEquipo(Context paramContext){
        return Build.BRAND + "-" + Build.MODEL;
    }
    //<uses-permission android:name="android.permission.READ_PHONE_STATE" />


    public static String fnNumEquipo(Context paramContext){
        String sValor="";
        try {
            sValor= ((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
        }catch (SecurityException e){
        }
       return sValor;
    }

    public static String fnNumIMEI(Context paramContext){
        String sValor="";
        try {
            sValor=((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }catch (SecurityException e){
        }
        return sValor;
    }

    public static String fnNumSim(Context paramContext){
        String sValor="";
        try {
            sValor=((TelephonyManager)paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
        }catch (SecurityException e){
        }
        return sValor;
    }


    public static int getApiVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getAndroidRelease(){
        return Build.VERSION.RELEASE;
    }

    public static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion + " (" + release +")";
    }
}

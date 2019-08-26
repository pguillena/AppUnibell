package pe.com.app.unibell.appunibell.Servicio;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import java.util.Random;
import pe.com.app.unibell.appunibell.R;

public class GpsTrackerBootReceiver extends BroadcastReceiver {
    private static final String TAG = "GpsTrackerBootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), Context.MODE_PRIVATE);

        int tarea_gps=Integer.valueOf(sharedPreferences.getString("tarea_gps", "0"));
        //10
        int vtiempotracking = Integer.valueOf(sharedPreferences.getString("vtiempotracking", "10"));

        //if (tarea_gps == 0) {
            Intent alarm = new Intent(context, AlarmReceiver.class);
            boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
            if (alarmRunning == false) {
                //TODO RANDOM
                Random random = new Random();
                int iRandom = random.nextInt(999 - 100) + 100;
                sharedPreferences.edit().putInt("random", iRandom).commit();
                sharedPreferences.edit().putString("tarea_gps", "1").commit();

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime(),60000 * vtiempotracking, pendingIntent);

            }
        //}
    }
}

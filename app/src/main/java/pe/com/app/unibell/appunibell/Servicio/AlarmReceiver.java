package pe.com.app.unibell.appunibell.Servicio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, ServiceBackground.class);
        context.startService(background);
    }

}
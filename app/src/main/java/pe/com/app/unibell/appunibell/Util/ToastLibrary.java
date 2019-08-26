package pe.com.app.unibell.appunibell.Util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.app.unibell.appunibell.R;


/**
 * Created by RENAN on 18/08/2016.
 */
public class ToastLibrary {
    private Activity activity;
    private String mensaje;

    public ToastLibrary() {
    }

    public ToastLibrary(Activity activity, String mensaje) {
        this.activity = activity;
        this.mensaje = mensaje;
    }

    public void Show(){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View ToastView =inflater.inflate(R.layout.model_toast, (ViewGroup) null);

        TextView label1 =(TextView)ToastView.findViewById(R.id.ToastTxvMessage);
        label1.setText(this.mensaje);

        Toast alert=new Toast(activity.getApplicationContext());
        alert.setView(ToastView);
        alert.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0, 0);
        alert.setDuration(Toast.LENGTH_LONG);
        alert.show();
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}

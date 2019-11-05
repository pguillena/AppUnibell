package pe.com.app.unibell.appunibell.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import pe.com.app.unibell.appunibell.R;


/**
 * Created by RENAN on 18/08/2016.
 */
public class Dialog_Fragment_Progress extends DialogFragment {
    public final static String TAG = "Dialog_Fragment_Progress";
    private TextView tvMensaje;
    private String Mensaje = "";

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_progress, container, false);
        tvMensaje = (TextView) view.findViewById(R.id.tvMensaje);
        tvMensaje.setText(String.valueOf(Mensaje));
      /*getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);*/
        setCancelable(false);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}

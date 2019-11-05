package pe.com.app.unibell.appunibell.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import pe.com.app.unibell.appunibell.R;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Dialog_Fragment_Aceptar extends DialogFragment {
    public final static String TAG = "AceptarDialogfragment";
    private TextView tvConfirmacionOK, tvMensaje;
    private DialogFragmentAceptarListener mAceptarDialogfragmentListener;
    private String Mensaje;

    public String getMensaje() {
        return Mensaje;
    }
    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public interface DialogFragmentAceptarListener {
        void onAceptar();
    };

    public void setAceptarDialogfragmentListener(DialogFragmentAceptarListener mAceptarDialogfragmentListener) {
        this.mAceptarDialogfragmentListener = mAceptarDialogfragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_aceptar, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        tvConfirmacionOK = (TextView) view.findViewById(R.id.ss_btnaceptar);
        tvMensaje = (TextView) view.findViewById(R.id.ss_lbltitulo);
        tvMensaje.setText(Mensaje);
        tvConfirmacionOK.setOnClickListener(tvConfirmacionOKOnClickListener);
        return view;
    }

    View.OnClickListener tvConfirmacionOKOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mAceptarDialogfragmentListener != null)
                mAceptarDialogfragmentListener.onAceptar();
            dismissAllowingStateLoss();
        }
    };
}
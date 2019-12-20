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
public class Dialog_Fragment_Confirmar extends DialogFragment {
    public final static String TAG = "Dialog_Fragment_Confirmar";
    private TextView tvConfirmacionSI;
    private TextView tvConfirmacionNO;
    private TextView tvMessage;
    private Dialog_Fragment_ConfirmarListener mDialog_Fragment_ConfirmarListener;
    private String sMensaje;


    public interface Dialog_Fragment_ConfirmarListener {
        void onConfirmacionSI();
        void onConfirmacionNO();
    }

    public void setmConfirmarDialogfragmentListener(Dialog_Fragment_ConfirmarListener mDialog_Fragment_ConfirmarListener, String sMensaje) {
        this.mDialog_Fragment_ConfirmarListener = mDialog_Fragment_ConfirmarListener;
        this.sMensaje=sMensaje;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_confirmar, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setCancelable(false);

        tvConfirmacionSI = (TextView) view.findViewById(R.id.tvYes);
        tvConfirmacionNO = (TextView) view.findViewById(R.id.tvNo);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);

        tvConfirmacionSI.setOnClickListener(tvYesOnClickListener);
        tvConfirmacionNO.setOnClickListener(tvNoOnClickListener);

        tvMessage.setText(this.sMensaje);

        return view;
    }

    View.OnClickListener tvYesOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mDialog_Fragment_ConfirmarListener != null) {
                mDialog_Fragment_ConfirmarListener.onConfirmacionSI();
            }
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvNoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDialog_Fragment_ConfirmarListener != null) {
                mDialog_Fragment_ConfirmarListener.onConfirmacionNO();
            }
            dismissAllowingStateLoss();
        }
    };


}
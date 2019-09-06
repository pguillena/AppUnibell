package pe.com.app.unibell.appunibell.Dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.app.unibell.appunibell.R;

import static android.content.Context.MODE_PRIVATE;

public class Dialog_Fragment_Amortizar extends DialogFragment {
    public final static String TAG = "Dialog_Fragment_Amortizar";
    private TextView am_lbldocumento,am_lblsaldo,am_lblcancelar,am_lblconfirmar;
    private EditText am_lblmonto;
    private Dialog_Fragment_AmortizarListener dialog_fragment_amortizarListener;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private String message;

    public interface Dialog_Fragment_AmortizarListener {
        void onAmortizarSI(String Precio);
    }
    public void setPrecioDialogfragmentListener(Dialog_Fragment_AmortizarListener dialog_fragment_amortizarListener) {
        this.dialog_fragment_amortizarListener = dialog_fragment_amortizarListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_amortizar, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        sharedSettings =getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

        am_lbldocumento = (TextView) view.findViewById(R.id.am_lbldocumento);
        am_lblsaldo = (TextView) view.findViewById(R.id.am_lblsaldo);
        am_lblcancelar = (TextView) view.findViewById(R.id.am_lblcancelar);
        am_lblconfirmar = (TextView) view.findViewById(R.id.am_lblconfirmar);
        am_lblmonto = (EditText) view.findViewById(R.id.am_lblmonto);

        am_lbldocumento.setText(sharedSettings.getString("aDOCUMENTO", "").toString());
        am_lblsaldo.setText(sharedSettings.getString("aSALDO", "0.00").toString());
        am_lblmonto.setText(sharedSettings.getString("aSALDO", "0.00").toString());

        if(Double.valueOf(sharedSettings.getString("aSALDO", "0.00").toString()) > Double.valueOf(sharedSettings.getString("SALDO_CABECERA", "0.00").toString()))
        {
            am_lblmonto.setText(sharedSettings.getString("SALDO_CABECERA", "0.00").toString());

        }








        am_lblconfirmar.setOnClickListener(tvYesOnClickListener);
        am_lblcancelar.setOnClickListener(tvNoOnClickListener);

        am_lblmonto.setOnFocusChangeListener(onFocusChangeListener);

        return view;
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                ((EditText) view).setSelection(((EditText) view).getText().length());
            }
        }
    };


    View.OnClickListener tvYesOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            if(am_lblmonto.getText().toString().trim().equals("")){
                Toast toastCodigo = Toast.makeText(getActivity(),"Ingrese Monto.",Toast.LENGTH_LONG);
                toastCodigo.show();
                vibrator.vibrate(200);
                return;
            }
            if(Double.valueOf(am_lblmonto.getText().toString().replace(",",""))<=0.0){
                Toast toastCodigo = Toast.makeText(getActivity(),"Monto no es Válido.",Toast.LENGTH_LONG);
                toastCodigo.show();
                vibrator.vibrate(200);
                return;
            }

            if(Double.valueOf(am_lblmonto.getText().toString().replace(",","")) > Double.valueOf(am_lblsaldo.getText().toString().replace(",",""))){
                Toast toastCodigo = Toast.makeText(getActivity(),"Monto Ingresado es mayor al Saldo.",Toast.LENGTH_LONG);
                toastCodigo.show();
                vibrator.vibrate(200);
                return;
            }

            if (dialog_fragment_amortizarListener != null) {
                dialog_fragment_amortizarListener.onAmortizarSI(am_lblmonto.getText().toString().trim().replace(",",""));
            }
            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvNoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog_fragment_amortizarListener != null) {
                //dialog_fragment_precioListener.onPrecioNO();
            }
            dismissAllowingStateLoss();
        }
    };

    public void setMessage(String message) {
        this.message = message;
    }

   /* @Override
    public void onStart() {
        super.onStart();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }*/

}
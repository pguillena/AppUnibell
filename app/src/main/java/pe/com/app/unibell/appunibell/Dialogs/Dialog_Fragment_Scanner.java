package pe.com.app.unibell.appunibell.Dialogs;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.DialogFragment;

import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;


public class Dialog_Fragment_Scanner extends DialogFragment {

    public final static String TAG = "Dialog_Fragment_Scanner";
    private Dialog_Fragment_ScannerListener dialog_fragment_scannerListener;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private  EditText edtcantidadScan, edtUbicacionScan;
    private  TextView lblUbicacionScan, lblCantidadScan;
    private TextView btncancelarScan, btnConfirmarScan;
    private TextView lblTituloScan;
    public S_Inv_InventarioBE inventarioBE;
    private Integer _Cantidad=1;

    private String message;

    public Dialog_Fragment_Scanner() {

    }

    public Dialog_Fragment_Scanner(Integer cantidad) {
        _Cantidad = cantidad;
    }

    public interface Dialog_Fragment_ScannerListener {
        void onCantidadSI(Integer Cantidad);
        void onCantidadNO();
        void onUbicacionSI(String Ubicacion);
        void onUbicacionNO();
        void onActualizarSI(S_Inv_InventarioBE objBE);
    }



    public void setCantidadDialogfragmentListener(Dialog_Fragment_ScannerListener dialog_fragment_scannerListener) {
        this.dialog_fragment_scannerListener = dialog_fragment_scannerListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_scanner, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);

        sharedSettings =getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

        edtcantidadScan = (EditText) view.findViewById(R.id.edtcantidadScan);
        lblTituloScan = (TextView) view.findViewById(R.id.lblTituloScan);

        btncancelarScan = (TextView) view.findViewById(R.id.btncancelarScan);
        btnConfirmarScan = (TextView) view.findViewById(R.id.btnConfirmarScan);


        edtUbicacionScan = (EditText) view.findViewById(R.id.edtUbicacionScan);
        lblUbicacionScan = (TextView) view.findViewById(R.id.lblUbicacionScan);
        lblCantidadScan = (TextView) view.findViewById(R.id.lblCantidadScan);


        lblTituloScan.setText(sharedSettings.getString("aTituloScan", "").toString());

        btncancelarScan.setOnClickListener(tvNoOnClickListener);
        btnConfirmarScan.setOnClickListener(tvYesOnClickListener);



        if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("NUEVO"))
        {
            lblTituloScan.setText("INGRESE CODIGO DE UBICACIÓN");
            lblCantidadScan.setVisibility(View.GONE);
            edtcantidadScan.setVisibility(View.GONE);
            lblUbicacionScan.setVisibility(View.VISIBLE);
            edtUbicacionScan.setVisibility(View.VISIBLE);

        }
        else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("SCAN"))
        {
            lblCantidadScan.setVisibility(View.VISIBLE);
            lblTituloScan.setVisibility(View.VISIBLE);
            lblUbicacionScan.setVisibility(View.GONE);
            edtUbicacionScan.setVisibility(View.GONE);

            lblTituloScan.setText(sharedSettings.getString("iCODIGO_BARRA", "").toString());

            if (inventarioBE!=null)
            {
                if (inventarioBE.getDESCRIPCION()!=null && !inventarioBE.getDESCRIPCION().equals("null") && !inventarioBE.getDESCRIPCION().equals(""))
                    lblTituloScan.setText( inventarioBE.getCODIGO_BARRA() + "\n" + inventarioBE.getDESCRIPCION());
            }

            edtcantidadScan.requestFocus();

        }
        else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("EDITAR"))
        {
            lblCantidadScan.setVisibility(View.VISIBLE);
            lblTituloScan.setVisibility(View.VISIBLE);
            lblUbicacionScan.setVisibility(View.GONE);
            edtUbicacionScan.setVisibility(View.GONE);

            if (inventarioBE!=null)
            {
                if (inventarioBE.getDESCRIPCION()!=null && !inventarioBE.getDESCRIPCION().equals("null") && !inventarioBE.getDESCRIPCION().equals(""))
                    lblTituloScan.setText(inventarioBE.getUBICACION()+ "\n"+ inventarioBE.getDESCRIPCION() + "\n" +  inventarioBE.getCODIGO_BARRA());
            }
            edtcantidadScan.requestFocus();
            edtcantidadScan.setText(_Cantidad.toString());
            edtcantidadScan.selectAll();

        }


        edtcantidadScan.setOnFocusChangeListener(onFocusChangeListener);

        return view;
    }



    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
                if ((hasFocus)){
                    ((EditText)v).selectAll();
                }
        }
    };





    View.OnClickListener tvYesOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("NUEVO")) {

                editor_Shared.putString("iUBICACION", edtUbicacionScan.getText().toString().trim().toUpperCase());
                editor_Shared.commit();

                if (dialog_fragment_scannerListener != null) {
                    dialog_fragment_scannerListener.onUbicacionSI(edtUbicacionScan.getText().toString().trim().toUpperCase());
                }
            }
            else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("SCAN"))
            {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (edtcantidadScan.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese cantidad.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }
                if (Double.valueOf(edtcantidadScan.getText().toString().replace(",", "")) <= 0.0) {
                    Toast toastCodigo = Toast.makeText(getActivity(), "cantidad no es válida.", Toast.LENGTH_LONG);
                    toastCodigo.show();
                    vibrator.vibrate(200);
                    return;
                }


                if (dialog_fragment_scannerListener != null) {
                    dialog_fragment_scannerListener.onCantidadSI(Integer.parseInt(edtcantidadScan.getText().toString().trim().replace(",", "")));
                }

            }
             else  if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("EDITAR")) {

                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (edtcantidadScan.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese cantidad.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }
                if (Double.valueOf(edtcantidadScan.getText().toString().replace(",", "")) <= 0.0) {
                    Toast toastCodigo = Toast.makeText(getActivity(), "cantidad no es válida.", Toast.LENGTH_LONG);
                    toastCodigo.show();
                    vibrator.vibrate(200);
                    return;
                }


                if (dialog_fragment_scannerListener != null) {
                    inventarioBE.setCANTIDAD(Integer.parseInt(edtcantidadScan.getText().toString().trim().replace(",", "")));
                    dialog_fragment_scannerListener.onActualizarSI(inventarioBE);
                }

             }

            dismissAllowingStateLoss();
        }
    };

    View.OnClickListener tvNoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog_fragment_scannerListener != null) {
                if(sharedSettings.getString("iCODIGO_BARRA", "XXX").toString().equals("XXX")) {
                    dialog_fragment_scannerListener.onUbicacionNO();
                }
                else {
                    dialog_fragment_scannerListener.onCantidadNO();
                }

            }
            dismissAllowingStateLoss();
        }
    };

    public void setMessage(String message) {
        this.message = message;
    }




}

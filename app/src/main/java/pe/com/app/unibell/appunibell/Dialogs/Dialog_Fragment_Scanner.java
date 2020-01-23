package pe.com.app.unibell.appunibell.Dialogs;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import pe.com.app.unibell.appunibell.DAO.M_ArticulosDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Context.MODE_PRIVATE;


public class Dialog_Fragment_Scanner extends DialogFragment {

    public final static String TAG = "Dialog_Fragment_Scanner";
    private Dialog_Fragment_ScannerListener dialog_fragment_scannerListener;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private  EditText edtcantidadScan, edtUbicacionScan, edtMaster,    edtMasterCantidad, edtCodAlmScan;
    private  TextView lblUbicacionScan, lblCantidadScan, lblCodAlmScan;
    private TextView btncancelarScan, btnConfirmarScan;
    private TextView lblTituloScan, lblTotalCantidadxArticulo;
    public S_Inv_InventarioBE inventarioBE;
    private Integer _Cantidad=1;
    private Funciones funciones = new Funciones();
    private AppCompatAutoCompleteTextView edtCodArt, edtCodigoBarra;
    private M_ArticulosDAO marticulosDAO  = new   M_ArticulosDAO();
    private String message;
    private ArrayList<String> listaArticulos;
    private ArrayList<String> listaArticulosCodigoBarra;
    private LinearLayout lyArticulo, lyLblMaster,    lyEdtMaster;


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
        void onCodigoSI(Integer Cantidad, String CodArt);
        void onCodigoNO();
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
        lyArticulo = (LinearLayout) view.findViewById(R.id.lyArticulo);
        lyLblMaster = (LinearLayout) view.findViewById(R.id.lyLblMaster);
        lyEdtMaster = (LinearLayout) view.findViewById(R.id.lyEdtMaster);
        lblTotalCantidadxArticulo = (TextView) view.findViewById(R.id.lblTotalCantidadxArticulo);
        lblCodAlmScan = (TextView) view.findViewById(R.id.lblCodAlmScan);
        edtCodAlmScan = (EditText) view.findViewById(R.id.edtCodAlmScan);

        edtMaster = (EditText) view.findViewById(R.id.edtMaster);
        edtMasterCantidad = (EditText) view.findViewById(R.id.edtMasterCantidad);

        edtCodArt = (AppCompatAutoCompleteTextView)view.findViewById(R.id.edtCodArt);
        edtCodigoBarra = (AppCompatAutoCompleteTextView)view.findViewById(R.id.edtCodigoBarra);


        funciones.addTextChangedListener(edtCodArt, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(edtCodigoBarra, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);

        funciones.addTextChangedListener(edtMaster, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(edtMasterCantidad, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(edtcantidadScan, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);


        lblTituloScan.setText(sharedSettings.getString("aTituloScan", "").toString());

        btncancelarScan.setOnClickListener(tvNoOnClickListener);
        btnConfirmarScan.setOnClickListener(tvYesOnClickListener);

        if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("NUEVO"))
        {
            lblTituloScan.setText("INGRESE CODIGO DE UBICACIÓN");
            lyArticulo.setVisibility(View.GONE);
            lblCantidadScan.setVisibility(View.GONE);
            edtcantidadScan.setVisibility(View.GONE);
            lblUbicacionScan.setVisibility(View.VISIBLE);
            edtUbicacionScan.setVisibility(View.VISIBLE);
            lblCodAlmScan.setVisibility(View.VISIBLE);
            edtCodAlmScan.setVisibility(View.VISIBLE);
            lyLblMaster.setVisibility(View.GONE);
            lyEdtMaster.setVisibility(View.GONE);


        }
        else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("SCAN"))
        {   lyArticulo.setVisibility(View.GONE);
            lyLblMaster.setVisibility(View.VISIBLE);
            lyEdtMaster.setVisibility(View.VISIBLE);
            lblCantidadScan.setVisibility(View.VISIBLE);
            lblTituloScan.setVisibility(View.VISIBLE);
            lblUbicacionScan.setVisibility(View.GONE);
            lblCodAlmScan.setVisibility(View.GONE);
            edtUbicacionScan.setVisibility(View.GONE);
            edtCodAlmScan.setVisibility(View.GONE);

            lblTituloScan.setText(sharedSettings.getString("iCOD_ART", "").toString());

            if (inventarioBE!=null)
            {
                if (inventarioBE.getDESCRIPCION()!=null && !inventarioBE.getDESCRIPCION().equals("null") && !inventarioBE.getDESCRIPCION().equals(""))
                    lblTituloScan.setText( inventarioBE.getCOD_ART() + "\n" + inventarioBE.getDESCRIPCION());
            }

            edtcantidadScan.requestFocus();

        }
        else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("EDITAR"))
        {
            lyArticulo.setVisibility(View.GONE);
            lblCantidadScan.setVisibility(View.VISIBLE);
            lyLblMaster.setVisibility(View.GONE);
            lyEdtMaster.setVisibility(View.GONE);
            lblTituloScan.setVisibility(View.VISIBLE);
            lblUbicacionScan.setVisibility(View.GONE);
            edtUbicacionScan.setVisibility(View.GONE);
            lblCodAlmScan.setVisibility(View.GONE);
            edtCodAlmScan.setVisibility(View.GONE);

            if (inventarioBE!=null)
            {
                if (inventarioBE.getDESCRIPCION()!=null && !inventarioBE.getDESCRIPCION().equals("null") && !inventarioBE.getDESCRIPCION().equals(""))
                    lblTituloScan.setText(inventarioBE.getUBICACION()+ "\n"+ inventarioBE.getDESCRIPCION() + "\n" +  inventarioBE.getCOD_ART());
            }
            edtcantidadScan.requestFocus();
            edtcantidadScan.setText(_Cantidad.toString());
            edtcantidadScan.selectAll();

        }
        else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("CODIGO"))
        {
            lyArticulo.setVisibility(View.VISIBLE);
            lblCantidadScan.setVisibility(View.VISIBLE);
            lyLblMaster.setVisibility(View.VISIBLE);
            lyEdtMaster.setVisibility(View.VISIBLE);
            lblTituloScan.setVisibility(View.VISIBLE);
            lblUbicacionScan.setVisibility(View.GONE);
            edtUbicacionScan.setVisibility(View.GONE);
            lblCodAlmScan.setVisibility(View.GONE);
            edtCodAlmScan.setVisibility(View.GONE);
            lblTituloScan.setText("POR CODIGO DE ARTICULO MANUAL");

            edtCodArt.requestFocus();
            AutoComplete();
        }


        edtcantidadScan.setOnFocusChangeListener(onFocusChangeListener);
        edtMaster.setOnFocusChangeListener(onFocusChangeListener_edtMaster);
        edtMasterCantidad.setOnFocusChangeListener(onFocusChangeListener_edtMaster);



        // handle click event and set desc on textview
        edtCodigoBarra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String codigoBarra = (String) adapterView.getItemAtPosition(i);

                if(marticulosDAO.lst!=null && marticulosDAO.lst.size()>0)
                {
                    for (int j = 0; j<marticulosDAO.lst.size(); j++)
                    {
                        if(marticulosDAO.lst.get(j).getC_ARTICULO().equals(codigoBarra))
                        {
                            edtCodArt.setText(marticulosDAO.lst.get(j).getCOD_ART());
                            break;
                        }
                    }
                }

            }
        });



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

    View.OnFocusChangeListener onFocusChangeListener_edtMaster = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            CalcularTotalxMaster();

        }
    };

    private void CalcularTotalxMaster() {
        String iMaster =  edtMaster.getText().toString();
        String iMasterCantidad =  edtMasterCantidad.getText().toString();
        Integer iCantidad = 0;
        Integer iTotal = 0;

        lblTotalCantidadxArticulo.setText("0");

        if(edtcantidadScan.getText().toString()!=null && !edtcantidadScan.getText().toString().equals("")) {
            iCantidad = Integer.valueOf(edtcantidadScan.getText().toString());
            iTotal = iCantidad;
        }

        if(iMaster!=null && !iMaster.equals("") && iMasterCantidad!=null && !iMasterCantidad.equals(""))
        {
            iTotal  = Integer.valueOf(iMaster) * Integer.valueOf(iMasterCantidad);

            if(iTotal>0)
            {
                iTotal = iTotal + iCantidad;
            }
        }

        lblTotalCantidadxArticulo.setText(iTotal.toString());
    }


    View.OnClickListener tvYesOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            CalcularTotalxMaster();
            //edtcantidadScan.setText(lblTotalCantidadxArticulo.getText());

            if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("NUEVO")) {

                editor_Shared.putString("iUBICACION", edtUbicacionScan.getText().toString().trim().toUpperCase().replace(" ",""));
                editor_Shared.putString("iCOD_ALM", edtCodAlmScan.getText().toString().trim().toUpperCase().replace(" ",""));
                editor_Shared.commit();

                if (dialog_fragment_scannerListener != null) {
                    dialog_fragment_scannerListener.onUbicacionSI(edtUbicacionScan.getText().toString().trim().toUpperCase());
                }
            }
            else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("SCAN"))
            {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (lblTotalCantidadxArticulo.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese cantidad.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }
                if (Double.valueOf(lblTotalCantidadxArticulo.getText().toString().replace(",", "")) <= 0.0) {
                    Toast toastCodigo = Toast.makeText(getActivity(), "cantidad no es válida.", Toast.LENGTH_LONG);
                    toastCodigo.show();
                    vibrator.vibrate(200);
                    return;
                }


                if (dialog_fragment_scannerListener != null) {
                    dialog_fragment_scannerListener.onCantidadSI(Integer.parseInt(lblTotalCantidadxArticulo.getText().toString().trim().replace(",", "")));
                }

            }
             else  if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("EDITAR")) {

                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (lblTotalCantidadxArticulo.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese cantidad.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }
                if (Double.valueOf(lblTotalCantidadxArticulo.getText().toString().replace(",", "")) <= 0.0) {
                    Toast toastCodigo = Toast.makeText(getActivity(), "cantidad no es válida.", Toast.LENGTH_LONG);
                    toastCodigo.show();
                    vibrator.vibrate(200);
                    return;
                }


                if (dialog_fragment_scannerListener != null) {

                    M_ArticulosDAO articuloDao = new M_ArticulosDAO();

                    articuloDao.getAllByCodArt(inventarioBE.getCOD_ART());

                    if(articuloDao.lst!=null && articuloDao.lst.size()>0)
                    {
                        inventarioBE.setDESCRIPCION(articuloDao.lst.get(0).getDESCRIPCION());
                    }

                    inventarioBE.setCANTIDAD(Integer.parseInt(lblTotalCantidadxArticulo.getText().toString().trim().replace(",", "")));
                    dialog_fragment_scannerListener.onActualizarSI(inventarioBE);
                }

             }
            else if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("CODIGO"))
            {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                if(!edtCodigoBarra.getText().toString().equals(""))
                {
                    if(marticulosDAO.lst!=null && marticulosDAO.lst.size()>0)
                    {
                        for (int i = 0; i<marticulosDAO.lst.size(); i++)
                        {
                            if(marticulosDAO.lst.get(i).getC_ARTICULO().equals(edtCodigoBarra.getText().toString().trim()))
                            {
                                edtCodArt.setText(marticulosDAO.lst.get(i).getCOD_ART());
                                break;
                            }
                        }
                    }

                }


                if (edtCodArt.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese codigo articulo.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }

                if (lblTotalCantidadxArticulo.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Ingrese cantidad.", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(200);
                    return;
                }

                if (Double.valueOf(lblTotalCantidadxArticulo.getText().toString().replace(",", "")) <= 0.0) {
                    Toast toastCodigo = Toast.makeText(getActivity(), "cantidad no es válida.", Toast.LENGTH_LONG);
                    toastCodigo.show();
                    vibrator.vibrate(200);
                    return;
                }


                if (dialog_fragment_scannerListener != null) {
                    dialog_fragment_scannerListener.onCodigoSI(Integer.parseInt(lblTotalCantidadxArticulo.getText().toString().trim().replace(",", "")),edtCodArt.getText().toString().trim().toUpperCase());
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

                 if(sharedSettings.getString("iACCION_INVENTARIO", "").toString().equals("CODIGO"))
                {

                    dialog_fragment_scannerListener.onCodigoNO();
                }

            }
            dismissAllowingStateLoss();
        }
    };

    public void setMessage(String message) {
        this.message = message;
    }


    private void AutoComplete() {
        try {
            new LoadMArticulosSQLite_AsyncTask().execute("XXX");

        } catch (Exception ex){

        }

    }



    private class LoadMArticulosSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                marticulosDAO.getAllByCodArt(p[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected void onPostExecute(String restResult) {
            super.onPostExecute(restResult);
            try {

                if (marticulosDAO.lst != null && marticulosDAO.lst.size() > 0) {
                    listaArticulos = new ArrayList<>();
                    listaArticulosCodigoBarra = new ArrayList<>();
                    for (int i = 0; i < marticulosDAO.lst.size(); i++) {
                        listaArticulos.add(marticulosDAO.lst.get(i).getCOD_ART());
                        if ( marticulosDAO.lst.get(i).getC_ARTICULO() != null && !marticulosDAO.lst.get(i).getC_ARTICULO().equals("") && !marticulosDAO.lst.get(i).getC_ARTICULO().equals("null") ) {
                            listaArticulosCodigoBarra.add(marticulosDAO.lst.get(i).getC_ARTICULO());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_list_item, listaArticulos);
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.my_list_item, listaArticulosCodigoBarra);
                    edtCodArt.setThreshold(1); //will start working from first character
                    edtCodigoBarra.setThreshold(1); //will start working from first character

                    edtCodArt.setAdapter(adapter);
                    edtCodigoBarra.setAdapter(adapter2);

                }

                } catch(Exception ex){
                    Toast.makeText(getActivity().getApplicationContext(), "No existen registros!!", Toast.LENGTH_LONG).show();
                }

        }
    }


}

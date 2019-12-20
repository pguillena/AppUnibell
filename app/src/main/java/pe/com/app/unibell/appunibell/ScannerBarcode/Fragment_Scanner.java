package pe.com.app.unibell.appunibell.ScannerBarcode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import androidx.fragment.app.Fragment;
import pe.com.app.unibell.appunibell.AD.Cobranza_Cabecera_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Detalle_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Detalle_Adapter_Edit;
import pe.com.app.unibell.appunibell.AD.Scanner_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import pe.com.app.unibell.appunibell.BE.S_Vem_CorrelativoBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_DetBL;
import pe.com.app.unibell.appunibell.BL.S_Inv_InventarioBL;
import pe.com.app.unibell.appunibell.BL.S_Vem_CorrelativoBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_DetDAO;
import pe.com.app.unibell.appunibell.DAO.FactCobDAO;
import pe.com.app.unibell.appunibell.DAO.Recibos_CcobranzaDAO;
import pe.com.app.unibell.appunibell.DAO.S_Inv_InventarioDAO;
import pe.com.app.unibell.appunibell.DAO.S_Vem_CorrelativoDAO;
import pe.com.app.unibell.appunibell.DAO.S_gem_TipoCambioDAO;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Amortizar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Scanner;
import pe.com.app.unibell.appunibell.Flujo_Seguimiento.Fragment_Seguimiento;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Recibo_Rep;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.Globals;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class Fragment_Scanner extends Fragment implements
        Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Scanner.Dialog_Fragment_ScannerListener {

    private  S_Inv_InventarioDAO inventarioDAO;
    private ListView  listScan;
    private Scanner_Adapter scan_adapter = null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar;
    private Dialog_Fragment_Scanner dialog_fragment_scanner;
    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Funciones funciones = new Funciones();
    private S_Inv_InventarioBE inventarioBE;
    public Comunicator comunicator;
    Integer iAccion = 0;


    @Override
    public void onCantidadSI(Integer Cantidad) {


    }

    @Override
    public void onCantidadNO() {

    }

    @Override
    public void onUbicacionSI(String Ubicacion) {

    }

    @Override
    public void onUbicacionNO() {

    }

    @Override
    public void onActualizarSI(S_Inv_InventarioBE objBE) {
        objBE.setFECHA_MODIFICACION(new Funciones().FechaActualNow());
        objBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
        objBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
        objBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
        new S_Inv_InventarioDAO().update(objBE);
        Cargar();
    }


    public interface Comunicator {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comunicator = (Comunicator) activity;
    }

    public Fragment_Scanner() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        try {
            String myTag = getTag();
            ((Activity_Scanner) getActivity()).setigetiFragmentScanner(myTag);

            sharedSettings = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE);
            editor_Shared = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE).edit();

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            listScan=(ListView)view.findViewById(R.id.listScan);
            Cargar();

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }


    //INSERTAR  Y ACTUALIZAR INVENTARIO
    private class InsertInventarioAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;

        @Override
        protected JSONObject doInBackground(String... p) {
            return new S_Inv_InventarioBL().InsertRest(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {

                if (result.getString("status").equals("0") || result.getString("status").equals("false")) {

                    if (result.getString("status").equals("false") && result.getString("message").contains("reset"))
                    {
                        Mensaje("Error, verifique su conexion a internet.");
                        return;
                    }
                    else
                    {
                        Mensaje("Error al ENVIAR los registros.");
                        return;
                    }


                } else {
                    if (!result.getString("MSG").toString().trim().equals("-")) {
                        Mensaje(result.getString("MSG").toString().trim());
                        return;
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }


    private void Mensaje(String msj) {
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Fragment_Scanner.this);
        log_dialogaceptar.show(getFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }


    @Override
    public void onAceptar() {

    }

    @Override
    public void onConfirmacionSI() {

        //Accion de Eliminar Inventario
        if (iAccion==1){

                if(inventarioBE!=null && !inventarioBE.getCODIGO_BARRA().equals("")) {
                    new S_Inv_InventarioDAO().delete(inventarioBE);
                    Cargar();
                }
        }//Accion de Enviar inventario a la BD mediante el Servicio
        else if(iAccion==2)
        {
            new InsertInventarioAsyncTask().execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blinventario_insert);
        }


    }

    @Override
    public void onConfirmacionNO() {

    }

    public void Cargar(){
        new LoadInventarioSQLite_AsyncTask().execute();
    }

    private class LoadInventarioSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                inventarioDAO =  new S_Inv_InventarioDAO();

                inventarioDAO.getAll(sharedSettings.getString("iCONTEO", "0").toString(), "XXX","XXX", funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));



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

            try{
                scan_adapter = new Scanner_Adapter(getActivity(), 0, inventarioDAO.lst);
                scan_adapter.notifyDataSetChanged();
                listScan.setAdapter(scan_adapter);

            }
            catch (Exception ex) {
                Toast toastCodigo = Toast.makeText(getActivity(),ex.getMessage(), Toast.LENGTH_SHORT);
                toastCodigo.show();
            }
        }
    }




    public void EliminarInventario(S_Inv_InventarioBE objBE) {
        iAccion = 1;
        inventarioBE = objBE;
        String sMensaje = "¿Desea eliminar el registro?";
                sMensaje =  sMensaje + "\n"+ objBE.getUBICACION() + "\n"+ objBE.getCOD_ART() +  "\n" + objBE.getDESCRIPCION();
        dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
        dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_Scanner.this, sMensaje);
        dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
        dialog_fragment_confirmar.isCancelable();



    }


    public void EditarInventario(S_Inv_InventarioBE objBE) {
        inventarioBE = objBE;
        editor_Shared.putString("iACCION_INVENTARIO", "EDITAR");
        editor_Shared.commit();
        dialog_fragment_scanner = new Dialog_Fragment_Scanner(inventarioBE.getCANTIDAD());
        dialog_fragment_scanner.inventarioBE = inventarioBE;
        dialog_fragment_scanner.setCantidadDialogfragmentListener(Fragment_Scanner.this);
        dialog_fragment_scanner.show(getFragmentManager(), dialog_fragment_scanner.TAG);
        dialog_fragment_scanner.isCancelable();



    }

    public void EnviarInventario() {
        iAccion = 2;
        String sMensaje = "¿Seguro que desea enviar el inventario a la Base de Datos?";
        dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
        dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_Scanner.this, sMensaje);
        dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
        dialog_fragment_confirmar.isCancelable();


    }



        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                if(listScan.getAdapter()!=null) {
                    Scanner_Adapter ca = (Scanner_Adapter) listScan.getAdapter();
                    ca.getFilter().filter(newText);
                    ca.notifyDataSetChanged();
                }
            } else {
                Scanner_Adapter ca = (Scanner_Adapter) listScan.getAdapter();
                ca.getFilter().filter(newText);
                ca.notifyDataSetChanged();
            }

            return true;
        }



}

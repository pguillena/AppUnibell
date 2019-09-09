package pe.com.app.unibell.appunibell.Cobranza;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import pe.com.app.unibell.appunibell.AD.Cobranza_Recibo_Adapter;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.AsyncTask_Recibo;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ItextLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Fragment_Cobranza_Recibo extends Fragment
        implements  Dialogo_Fragment_Fecha.NoticeDialogoListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private TextView rc_lblcliente,rc_txtffecha,rc_lblborrar;
    private DialogFragment dialogFragmentFecha;
    private ListView rc_lscobranza;
    private Cobranza_Recibo_Adapter cobranza_recibo_adapter = null;
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();
    private Funciones funciones=new Funciones();
    public Comunicator comunicator;

    //Recibo
    private Document document;
    private  String nombre_completo="";
    private static String NOMBRE_CARPETA_APP = "UNIBELL_REPORT";
    private static String GENERADOS = "PDF";
    private Dialog_Fragment_Aceptar log_dialogaceptar;

    public interface Comunicator {
        public void Finalizar();
        public void Fecha();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comunicator = (Comunicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cobranza_recibo, container, false);
        try {
            String myTag = getTag();
            ((Activity_Cobranza_Recibo) getActivity()).setiFragmentRecibo(myTag);

            sharedSettings = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE);
            editor_Shared = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE).edit();

            rc_lblcliente=(TextView)view.findViewById(R.id.rc_lblcliente);
            rc_txtffecha=(TextView)view.findViewById(R.id.rc_txtffecha);
            rc_lblborrar=(TextView)view.findViewById(R.id.rc_lblborrar);

            rc_lscobranza=(ListView)view.findViewById(R.id.rc_lscobranza);
            rc_lblcliente.setText(funciones.LetraCapital(sharedSettings.getString("RAZON_SOCIAL", "").toString()));
            rc_txtffecha.setOnClickListener(OnClickListenercl_rc_txtffecha);
            rc_lblborrar.setOnClickListener(OnClickListenercl_rc_lblborrar);

            rc_lscobranza.setOnItemClickListener(cl_ec_lsdet_OnItemClickListener);
            //Permite abrir el archivo PDF
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            Cargar();
        } catch (Exception ex) {
        }
        return view;
    }





    View.OnClickListener OnClickListenercl_rc_txtffecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                comunicator.Fecha();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_rc_lblborrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                rc_txtffecha.setText("");
                Cargar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public void setFecha(String fecha){
        try {
            rc_txtffecha.setText("  " + fecha);
            Cargar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                comunicator.Finalizar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Cargar(){
        try {
            String sFecha="";
            if (!rc_txtffecha.getText().toString().trim().equals("")){
                sFecha = rc_txtffecha.getText().toString().trim().substring(6)
                        + rc_txtffecha.getText().toString().trim().substring(3, 5)
                        + rc_txtffecha.getText().toString().trim().substring(0, 2);
            }
            new LoadSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_LOCAL", "0").toString(),
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    sharedSettings.getString("CODIGO_ANTIGUO", "").toString(),
                    sFecha.toString().trim()
            );
        } catch (Exception ex){

        }
    }

    public void CargarRecibo(){
        try {
            new LoadReciboElectronicoSQLite_AsyncTask().execute( );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    AbsListView.OnItemClickListener cl_ec_lsdet_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public void setearFecha(String fecha) {
        rc_txtffecha.setText(fecha);
    }

    @Override
    public void onAceptar() {
    }

    private class LoadSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getRecibo(p[0],p[1],p[2],p[3],p[4]);
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
                cobranza_recibo_adapter = new Cobranza_Recibo_Adapter(getActivity(), 0, documentos_cobra_cabDAO.lst);
                cobranza_recibo_adapter.notifyDataSetChanged();
                rc_lscobranza.setAdapter(cobranza_recibo_adapter);

            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class LoadReciboElectronicoSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                //documentos_cobra_cabDAO.getReciboElectronico(p[0],p[1],p[2],p[3]);
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
                //new ReciboAsyncTask().execute("");
                String NOMBRE_CARPETA_APP="UNIBELL_REPORT";

                //  String NOMBRE_ARCHIVO ="Recibo_Cobranza.pdf";
                String NOMBRE_ARCHIVO =UUID.randomUUID().toString()+".pdf";
                String TITLE="Recibo";
                String OPCION=sharedSettings.getString("IOPCION_REPORTE", "0").toString();
                new AsyncTask_Recibo(getActivity()).execute(NOMBRE_CARPETA_APP,NOMBRE_ARCHIVO,TITLE,OPCION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void Mensaje(String msj){
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Fragment_Cobranza_Recibo.this);
        log_dialogaceptar.show(getFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

}





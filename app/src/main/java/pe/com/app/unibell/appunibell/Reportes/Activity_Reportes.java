package pe.com.app.unibell.appunibell.Reportes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Cobranza_Aprobacion_Planilla_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Reporte_Adapter;
import pe.com.app.unibell.appunibell.BL.Cobranzas_ReporteBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.Planilla.Fragment_AprobacionPlanilla;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_Reportes extends AppCompatActivity {

    private ListView cr_lsdetalle;
    private FloatingActionButton cr_fabbuscar;
    private TextView cr_btnverdetalle,cr_lbltotalg;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Cobranzas_ReporteBL cobranzas_reporteBL = new Cobranzas_ReporteBL();
    private Cobranza_Reporte_Adapter cobranza_reporte_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_reportes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("REPORTES");
        getSupportActionBar().setSubtitle("");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();


        cr_lsdetalle=(ListView)findViewById(R.id.cr_lsdetalle);
        cr_fabbuscar=(FloatingActionButton)findViewById(R.id.cr_fabbuscar);
        cr_btnverdetalle=(TextView)findViewById(R.id.cr_btnverdetalle);
        cr_lbltotalg=(TextView)findViewById(R.id.cr_lbltotalg);

        cr_btnverdetalle.setOnClickListener(OnClickListener_cr_btnverdetalle);
        cr_fabbuscar.setOnClickListener(OnClickListener_cr_fabbuscar);

        Cargar();
    }

    View.OnClickListener OnClickListener_cr_btnverdetalle = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    View.OnClickListener OnClickListener_cr_fabbuscar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Activity_Reportes.this, Activity_Reporte_Filtro.class);
            startActivityForResult(intent,1);

        }
    };


    public void Cargar(){
        try{
           // http://172.16.1.78/ServiceUnibell/bldocumentos_cobra_cab_RptCobranza/17530101/17530101/XXX/0/172/0/0/0/1/1/XXX/XXX/XXX/XXX/0/0
                    String iID_VENDEDOR= sharedSettings.getString("iID_VENDEDOR", "0").toString();
                    new Load_AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_RptCobranza + "/" +
                            sharedSettings.getString("plb_lblinicio", "17530101").toString()+ "/" +
                            sharedSettings.getString("plb_lblfin", "17530101").toString()+ "/" +
                            sharedSettings.getString("plb_txtcodigo", "XXX").toString()+ "/" +
                            sharedSettings.getString("plb_lblfpago", "0").toString()+ "/" +
                            sharedSettings.getString("iID_VENDEDOR", "172").toString()+ "/" +
                            sharedSettings.getString("plb_txtseriep", "0").toString()+ "/" +
                            sharedSettings.getString("plb_txtnumerop", "0").toString()+ "/" +
                            sharedSettings.getString("plb_lblestado", "0").toString()+ "/" +
                            sharedSettings.getString("iID_EMPRESA", "1").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "1").toString() + "/" +
                            sharedSettings.getString("plb_lbltipodoc", "XXX").toString()+ "/" +
                            sharedSettings.getString("plb_txtserief", "XXX").toString()+ "/" +
                            sharedSettings.getString("plb_txtfnumerof", "XXX").toString()+ "/" +
                            sharedSettings.getString("plb_lblmoneda", "XXX").toString()+ "/" +
                            sharedSettings.getString("plb_txtoserie", "0").toString()+ "/" +
                            sharedSettings.getString("plb_txtonumero", "0").toString()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public class Load_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return cobranzas_reporteBL.getAllRest(p[0]);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }


        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Reportes.this, result.getString("message")).Show();
                } else {
                    cobranza_reporte_adapter = new Cobranza_Reporte_Adapter(Activity_Reportes.this, 0, cobranzas_reporteBL.lst);
                    cobranza_reporte_adapter.notifyDataSetChanged();
                    cr_lsdetalle.setAdapter(cobranza_reporte_adapter);

                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }




}

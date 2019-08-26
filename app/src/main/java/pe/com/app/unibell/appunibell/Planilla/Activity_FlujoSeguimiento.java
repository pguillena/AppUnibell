package pe.com.app.unibell.appunibell.Planilla;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;
import pe.com.app.unibell.appunibell.AD.Cobranza_Flujo1_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_FlujoResumen_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Flujo2_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Flujo3_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_FlujoSeguimiento extends AppCompatActivity {

    private ListView fp_lvuno,fp_lvdos,fp_lvtres;
    private GridView fp_lvflujoresumen;
    private TextView fp_lblnplanilla;

    private Cobranza_FlujoResumen_Seguimiento_Adapter cobranza_flujo_Resumen_seguimiento_adapter = null;
    private Cobranza_Flujo1_Seguimiento_Adapter cobranza_flujo_1SeguimientoAdapter = null;
    private Cobranza_Flujo2_Seguimiento_Adapter cobranza_flujo_2SeguimientoAdapter = null;
    private Cobranza_Flujo3_Seguimiento_Adapter cobranza_flujo_3SeguimientoAdapter = null;
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flujoseguimiento);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
            getSupportActionBar().setSubtitle("FLUJO SEGUIMIENTO");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            fp_lvuno=(ListView)findViewById(R.id.fp_lvuno);
            fp_lvdos=(ListView)findViewById(R.id.fp_lvdos);
            fp_lvtres=(ListView)findViewById(R.id.fp_lvtres);
            fp_lvflujoresumen=(GridView)findViewById(R.id.fp_lvflujoresumen);

            fp_lblnplanilla=(TextView)findViewById(R.id.fp_lblnplanilla);

            LayoutInflater inflater1 = getLayoutInflater();
            ViewGroup header1 = (ViewGroup)inflater1.inflate(R.layout.item_cobranza_flujo1_header,fp_lvuno,false);
            fp_lvuno.addHeaderView(header1);

            LayoutInflater inflater2 = getLayoutInflater();
            ViewGroup header2 = (ViewGroup)inflater2.inflate(R.layout.item_cobranza_flujo2_header,fp_lvdos,false);
            fp_lvdos.addHeaderView(header2);

            LayoutInflater inflater3 = getLayoutInflater();
            ViewGroup header3 = (ViewGroup)inflater3.inflate(R.layout.item_cobranza_flujo3_header,fp_lvtres,false);
            fp_lvtres.addHeaderView(header3);


            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplication());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            Cargar();

        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Cargar(){
        try {
            String fInicio="17530101",fFin="17530101";

            fp_lblnplanilla.setText("Seguimiento de planilla: "  +
                    sharedSettings.getString("SERIE_PLANILLA", "0").toString() +"-"+
                    sharedSettings.getString("N_PLANILLA", "0").toString());

            new Load_Flujo1AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL +
                    ConstantsLibrary.bldocumentos_cobra_cab_flujo1 + "/"+
                    "0/0/0/0/0/XXX/"+
                      fInicio+ "/"+
                      fFin+ "/XXX/XXX/XXX/XXX/"+
                      sharedSettings.getString("iID_EMPRESA", "0").toString()+ "/"+
                      sharedSettings.getString("iID_LOCAL", "0").toString()+ "/"+
                      sharedSettings.getString("ROL", "0").toString()+ "/"+
                      sharedSettings.getString("N_PLANILLA", "0").toString()+ "/0"
            );


            new Load_FlujoResumenAsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL +
                    ConstantsLibrary.bldocumentos_cobra_mov_flujoresumen + "/"+
                    sharedSettings.getString("SERIE_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("N_PLANILLA", "0").toString()
            );

        /*
            new Load_Flujo2AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL +
                    ConstantsLibrary.bldocumentos_cobra_mov_flujo2 + "/"+
                    sharedSettings.getString("SERIE_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("iID_EMPRESA", "0").toString()
            );
            */


            new Load_Flujo3AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL +
                    ConstantsLibrary.bldocumentos_cobra_mov_flujo3 + "/"+
                    "0/10014/"+
                    sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("SERIE_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                    sharedSettings.getString("iID_EMPRESA", "0").toString()+ "/"+
                    sharedSettings.getString("iID_LOCAL", "0").toString()
            );


        } catch (Exception ex){

        }
    }

    public class Load_Flujo1AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getFlujo1(p[0]);
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
                    new ToastLibrary(Activity_FlujoSeguimiento.this, result.getString("message")).Show();

                } else {
                    cobranza_flujo_1SeguimientoAdapter = new Cobranza_Flujo1_Seguimiento_Adapter(Activity_FlujoSeguimiento.this, 0, documentos_cobra_cabBL.lst);
                    cobranza_flujo_1SeguimientoAdapter.notifyDataSetChanged();
                    fp_lvuno.setAdapter(cobranza_flujo_1SeguimientoAdapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class Load_FlujoResumenAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getFlujoResumen(p[0]);
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
                    new ToastLibrary(Activity_FlujoSeguimiento.this, result.getString("message")).Show();

                } else {
                    cobranza_flujo_Resumen_seguimiento_adapter = new Cobranza_FlujoResumen_Seguimiento_Adapter(getBaseContext(), 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_Resumen_seguimiento_adapter.notifyDataSetChanged();
                    fp_lvflujoresumen.setAdapter(cobranza_flujo_Resumen_seguimiento_adapter);

                    cobranza_flujo_2SeguimientoAdapter = new Cobranza_Flujo2_Seguimiento_Adapter(Activity_FlujoSeguimiento.this, 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_2SeguimientoAdapter.notifyDataSetChanged();
                    fp_lvdos.setAdapter(cobranza_flujo_2SeguimientoAdapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class Load_Flujo2AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getFlujoResumen(p[0]);
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
                    new ToastLibrary(Activity_FlujoSeguimiento.this, result.getString("message")).Show();

                } else {
                    cobranza_flujo_2SeguimientoAdapter = new Cobranza_Flujo2_Seguimiento_Adapter(Activity_FlujoSeguimiento.this, 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_2SeguimientoAdapter.notifyDataSetChanged();
                    fp_lvdos.setAdapter(cobranza_flujo_2SeguimientoAdapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class Load_Flujo3AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getFlujo3(p[0]);
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
                    new ToastLibrary(Activity_FlujoSeguimiento.this, result.getString("message")).Show();

                } else {
                    cobranza_flujo_3SeguimientoAdapter = new Cobranza_Flujo3_Seguimiento_Adapter(Activity_FlujoSeguimiento.this, 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_3SeguimientoAdapter.notifyDataSetChanged();
                    fp_lvtres.setAdapter(cobranza_flujo_3SeguimientoAdapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}

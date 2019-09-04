package pe.com.app.unibell.appunibell.Reportes;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.AsyncTask_Liquidacion;

public class Activity_Cobranza_Liquidacion_Rep extends AppCompatActivity {
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_liquidacion_rep);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("REPORTE");
            getSupportActionBar().setSubtitle("");

            //Permite abrir el archivo PDF
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());


            sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            CargarRecibo();

        } catch (Exception ex) {

        }finally {
            finish();
        }
    }

    public void CargarRecibo(){
        try {
            new LoadReciboLiquidacionSQLite_AsyncTask().execute( );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class LoadReciboLiquidacionSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {

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
                String NOMBRE_ARCHIVO = UUID.randomUUID().toString()+".pdf";
                //String NOMBRE_ARCHIVO ="Recibo_Cobranza.pdf";
                String TITLE="Recibo";
                String OPCION=sharedSettings.getString("IOPCION_RECIBO", "0").toString();
                new AsyncTask_Liquidacion(Activity_Cobranza_Liquidacion_Rep.this).execute(NOMBRE_CARPETA_APP,NOMBRE_ARCHIVO,TITLE,OPCION);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }


}

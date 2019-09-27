package pe.com.app.unibell.appunibell.Reportes;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.UUID;

import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.AsyncTask_Liquidacion;

public class Activity_Cobranza_Liquidacion_Rep extends AppCompatActivity {
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private String NOMBRE_CARPETA_APP="UNIBELL_REPORT";
    private String NOMBRE_ARCHIVO = UUID.randomUUID().toString()+".pdf";
    private String TITLE="Recibo";
    private String OPCION="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_liquidacion_rep);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("REPORTE");
            getSupportActionBar().setSubtitle("");
            getSupportActionBar().hide();

            //Permite abrir el archivo PDF
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

             OPCION=sharedSettings.getString("IOPCION_REPORTE", "0").toString();
            new AsyncTask_Liquidacion(Activity_Cobranza_Liquidacion_Rep.this).execute(NOMBRE_CARPETA_APP,NOMBRE_ARCHIVO,TITLE,OPCION);

        } catch (Exception ex) {

        }finally {

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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }






}

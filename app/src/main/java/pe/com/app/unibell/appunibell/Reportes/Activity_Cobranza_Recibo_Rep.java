package pe.com.app.unibell.appunibell.Reportes;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import java.util.UUID;

import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.AsyncTask_Recibo;

public class Activity_Cobranza_Recibo_Rep extends AppCompatActivity {
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private String NOMBRE_CARPETA_APP = "UNIBELL_REPORT";
    private String NOMBRE_ARCHIVO;
    private String TITLE = "Recibo";
    private String OPCION = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_liquidacion_rep);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("REPORTE-RECIBO DE COBRANZA");
            getSupportActionBar().setSubtitle("");
            getSupportActionBar().hide();

            //Permite abrir el archivo PDF
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
            OPCION = sharedSettings.getString("IOPCION_REPORTE", "0").toString();
            NOMBRE_ARCHIVO = UUID.randomUUID().toString() + ".pdf";
            new AsyncTask_Recibo(Activity_Cobranza_Recibo_Rep.this).execute(NOMBRE_CARPETA_APP, NOMBRE_ARCHIVO, TITLE, OPCION);


        } catch (Exception ex) {

        } finally {
            // finish();
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

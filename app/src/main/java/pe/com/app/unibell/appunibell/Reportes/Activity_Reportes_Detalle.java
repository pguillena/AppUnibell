package pe.com.app.unibell.appunibell.Reportes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.AD.Cobranza_Reporte_Adapter;
import pe.com.app.unibell.appunibell.BL.Cobranzas_ReporteBL;
import pe.com.app.unibell.appunibell.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Activity_Reportes_Detalle extends AppCompatActivity {

    private TextView cr_lblrazonsocial,cr_lblcodigo,cr_lblmonto,cr_lbldocumento,cr_lblnumero,cr_lblfpago;
    private TextView cr_lblentidad,cr_lblnumopecheque,cr_lblfechacobranza,cr_lblmoneda,cr_lblrepxrecibo,cr_lblrecibo;
    private TextView cr_lblrepxplanilla,cr_lblplanilla;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Cobranzas_ReporteBL cobranzas_reporteBL = new Cobranzas_ReporteBL();
    private Cobranza_Reporte_Adapter cobranza_reporte_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_reportes_detalle);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reporte de cobranzas-Detalle");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

        cr_lblrazonsocial=(TextView)findViewById(R.id.cr_lblrazonsocial);
        cr_lblcodigo=(TextView)findViewById(R.id.cr_lblcodigo);
        cr_lblmonto=(TextView)findViewById(R.id.cr_lblmonto);
        cr_lbldocumento=(TextView)findViewById(R.id.cr_lbldocumento);
        cr_lblnumero=(TextView)findViewById(R.id.cr_lblnumero);
        cr_lblfpago=(TextView)findViewById(R.id.cr_lblfpago);
        cr_lblentidad=(TextView)findViewById(R.id.cr_lblentidad);
        cr_lblnumopecheque=(TextView)findViewById(R.id.cr_lblnumopecheque);
        cr_lblfechacobranza=(TextView)findViewById(R.id.cr_lblfechacobranza);
        cr_lblmoneda=(TextView)findViewById(R.id.cr_lblmoneda);
        cr_lblrepxrecibo=(TextView)findViewById(R.id.cr_lblrepxrecibo);
        cr_lblrecibo=(TextView)findViewById(R.id.cr_lblrecibo);
        cr_lblrepxplanilla=(TextView)findViewById(R.id.cr_lblrepxplanilla);
        cr_lblplanilla=(TextView)findViewById(R.id.cr_lblplanilla);

        cr_lblrepxrecibo.setOnClickListener(OnClickListener_cr_lblrepxrecibo);
        cr_lblrepxplanilla.setOnClickListener(OnClickListener_cr_lblrepxplanilla);

        Cargar();
    }

    View.OnClickListener OnClickListener_cr_lblrepxrecibo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String[] parts = sharedSettings.getString("rep_recibo", "0").toString().split("-");
            String SERIE ="0";
            String NUMERO ="0";
            if(parts.length>0){
                 SERIE = parts[0];
                 NUMERO = parts[1];
            }

            editor_Shared.putString("REP_SER_RECIBO",SERIE);
            editor_Shared.putString("REP_NUM_RECIBO",NUMERO);
            editor_Shared.putString("IOPCION_REPORTE","0");
            editor_Shared.commit();

            Intent intent = new Intent(getApplicationContext(), Activity_Cobranza_Recibo_Rep.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    View.OnClickListener OnClickListener_cr_lblrepxplanilla = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String[] parts = sharedSettings.getString("rep_planilla", "0").toString().split("-");
            String SERIE ="0";
            String NUMERO ="0";
            if(parts.length>0){
                SERIE = parts[0];
                NUMERO = parts[1];
            }

            editor_Shared.putString("REP_SER_PLANILLA", SERIE);
            editor_Shared.putString("REP_NUM_PLANILLA", NUMERO);
            editor_Shared.putString("IOPCION_REPORTE", "0");
            editor_Shared.commit();

            Intent intent = new Intent(getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    };

    public void Cargar(){
        try{
            cr_lblrazonsocial.setText(sharedSettings.getString("rep_razonsocial", ""));
            cr_lblcodigo.setText(sharedSettings.getString("rep_codigo", ""));
            cr_lblmonto.setText(sharedSettings.getString("rep_monto", ""));
            cr_lbldocumento.setText(sharedSettings.getString("rep_documento", ""));
            cr_lblnumero.setText(sharedSettings.getString("rep_numero", ""));
            cr_lblfpago.setText(sharedSettings.getString("rep_fpago", ""));
            cr_lblentidad.setText(sharedSettings.getString("rep_entidad", ""));
            cr_lblnumopecheque.setText(sharedSettings.getString("rep_numcheque", ""));
            cr_lblfechacobranza.setText(sharedSettings.getString("rep_fcobranza", ""));
            cr_lblmoneda.setText(sharedSettings.getString("rep_moneda", ""));
            cr_lblrecibo.setText(sharedSettings.getString("rep_recibo", "0"));
            cr_lblplanilla.setText(sharedSettings.getString("rep_planilla", "0"));

        } catch (Exception ex) {
            ex.printStackTrace();
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






}

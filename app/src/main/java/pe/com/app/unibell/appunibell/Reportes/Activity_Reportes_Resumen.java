package pe.com.app.unibell.appunibell.Reportes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.R;

public class Activity_Reportes_Resumen extends AppCompatActivity {

    private TextView lblmontocheque,lblmontochequepost,lbltotalcheque,lblmontotarjeta,lblmontotarjetapost,lbltotaltarjeta;
    private TextView lblmontodeposito,lblmondepositopost,lbltotaldeposito,lblmontoefectivo,lbltotalefectivo,lblmontotalbancarizado;
    private TextView lblmontobancarizadopost,lbltotalbancarizado;
    private TextView lbltotal,lbltotalpost,lbltotalgeneral;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_resumen);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resumen");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();


        lblmontocheque=(TextView)findViewById(R.id.lblmontocheque);
        lblmontochequepost=(TextView)findViewById(R.id.lblmontochequepost);
        lbltotalcheque=(TextView)findViewById(R.id.lbltotalcheque);
        lblmontotarjeta=(TextView)findViewById(R.id.lblmontotarjeta);
        lblmontotarjetapost=(TextView)findViewById(R.id.lblmontotarjetapost);
        lbltotaltarjeta=(TextView)findViewById(R.id.lbltotaltarjeta);
        lblmontodeposito=(TextView)findViewById(R.id.lblmontodeposito);
        lblmondepositopost=(TextView)findViewById(R.id.lblmondepositopost);
        lbltotaldeposito=(TextView)findViewById(R.id.lbltotaldeposito);
        lblmontoefectivo=(TextView)findViewById(R.id.lblmontoefectivo);
        lbltotalefectivo=(TextView)findViewById(R.id.lbltotalefectivo);
        lblmontotalbancarizado=(TextView)findViewById(R.id.lblmontotalbancarizado);
        lblmontobancarizadopost=(TextView)findViewById(R.id.lblmontobancarizadopost);
        lbltotalbancarizado=(TextView)findViewById(R.id.lbltotalbancarizado);
        lbltotal=(TextView)findViewById(R.id.lbltotal);
        lbltotalpost=(TextView)findViewById(R.id.lbltotalpost);
        lbltotalgeneral=(TextView)findViewById(R.id.lbltotalgeneral);

        Cargar();
    }





    public void Cargar(){
        try{
            lblmontocheque.setText(sharedSettings.getString("lblmontocheque", ""));
            lblmontochequepost.setText(sharedSettings.getString("lblmontochequepost", ""));
            lbltotalcheque.setText(sharedSettings.getString("lbltotalcheque", ""));
            lblmontotarjeta.setText(sharedSettings.getString("lblmontotarjeta", ""));
            lblmontotarjetapost.setText(sharedSettings.getString("lblmontotarjetapost", ""));
            lbltotaltarjeta.setText(sharedSettings.getString("lbltotaltarjeta", ""));
            lblmontodeposito.setText(sharedSettings.getString("lblmontodeposito", ""));
            lblmondepositopost.setText(sharedSettings.getString("lblmondepositopost", ""));
            lbltotaldeposito.setText(sharedSettings.getString("lbltotaldeposito", ""));
            lblmontoefectivo.setText(sharedSettings.getString("lblmontoefectivo", ""));
            lbltotalefectivo.setText(sharedSettings.getString("lbltotalefectivo", ""));
            lblmontotalbancarizado.setText(sharedSettings.getString("lblmontotalbancarizado", ""));
            lblmontobancarizadopost.setText(sharedSettings.getString("lblmontobancarizadopost", ""));
            lbltotalbancarizado.setText(sharedSettings.getString("lbltotalbancarizado", ""));
            lbltotal.setText(sharedSettings.getString("lbltotal", ""));
            lbltotalpost.setText(sharedSettings.getString("lbltotalpost", ""));
            lbltotalgeneral.setText(sharedSettings.getString("lbltotalgeneral", ""));

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

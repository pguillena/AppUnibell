package pe.com.app.unibell.appunibell.Planilla;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_Aprobacion_Totales  extends AppCompatActivity {

    private  Funciones funciones=new Funciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobacion_totales);

        TextView lblmontocheque =(TextView)findViewById(R.id.lblmontocheque);
        TextView lblmontotarjeta =(TextView)findViewById(R.id.lblmontotarjeta);
        TextView lblmontoefectivo =(TextView)findViewById(R.id.lblmontoefectivo);
        TextView lblmontobancarizado =(TextView)findViewById(R.id.lblmontobancarizado);
        TextView lblmontodepocito =(TextView)findViewById(R.id.lblmontodepocito);
        TextView lbltotalgeneral =(TextView)findViewById(R.id.lbltotalgeneral);

        Bundle   parametros = getIntent().getExtras();
        if(parametros !=null){
            lblmontocheque.setText(funciones.FormatSoles(parametros.getString("dMontoCheque")));
            lblmontotarjeta.setText(funciones.FormatSoles(parametros.getString("dMontoVisa")));
            lblmontoefectivo.setText(funciones.FormatSoles(parametros.getString("dMontoEfectivo")));
            lblmontobancarizado.setText(funciones.FormatSoles(parametros.getString("dMontoBancarizado")));
            lblmontodepocito.setText(funciones.FormatSoles(parametros.getString("dMontoDeposito")));
            lbltotalgeneral.setText(funciones.FormatSoles(parametros.getString("dTotalGeneral")));
        }

    }


}

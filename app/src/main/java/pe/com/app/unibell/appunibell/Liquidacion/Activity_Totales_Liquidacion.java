package pe.com.app.unibell.appunibell.Liquidacion;

import android.os.Bundle;
import android.view.Display;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.AD.Totales_Liquidacion_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.R;

public class Activity_Totales_Liquidacion  extends AppCompatActivity {

    private Totales_Liquidacion_Adapter totales_liquidacion_adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totales_liquidacion);

        TextView txtMontoCheque =(TextView)findViewById(R.id.txtMontoCheque);
        TextView txtMontoTarjeta =(TextView)findViewById(R.id.txtMontoTarjeta);
        TextView txtMontoDeposito =(TextView)findViewById(R.id.txtMontoDeposito);
        TextView txtMontoEfectivo =(TextView)findViewById(R.id.txtMontoEfectivo);
        TextView txtMontoBancarizado =(TextView)findViewById(R.id.txtMontoBancarizado);
        TextView txtMontoTotalGeneral =(TextView)findViewById(R.id.txtMontoTotalGeneral);
        ListView lstDepositosLiquidacion =(ListView)findViewById(R.id.lstDepositosLiquidacion);
        ArrayList<Documentos_Cobra_CabBE> listaDepositos = (ArrayList<Documentos_Cobra_CabBE> ) getIntent().getSerializableExtra("listaDepositos");


        //MEDIMOS LA PANTALLA
        Display display = getWindowManager().getDefaultDisplay();
        int ancho = display.getWidth();
        int  alto= display.getHeight();

        Bundle   parametros = getIntent().getExtras();

        if(parametros !=null){
            txtMontoTotalGeneral.setText(parametros.getString("txtMontoTotalGeneral"));
            txtMontoCheque.setText(parametros.getString("txtMontoCheque"));
            txtMontoTarjeta.setText(parametros.getString("txtMontoTarjeta"));
            txtMontoDeposito.setText(parametros.getString("txtMontoDeposito"));
            txtMontoEfectivo.setText(parametros.getString("txtMontoEfectivo"));
            txtMontoBancarizado.setText(parametros.getString("txtMontoBancarizado"));
            txtMontoEfectivo.setText(parametros.getString("txtMontoEfectivo"));
        }

        if(listaDepositos!=null && listaDepositos.size()>0){
            totales_liquidacion_adapter = new Totales_Liquidacion_Adapter(getBaseContext(), 0, listaDepositos);
            totales_liquidacion_adapter.notifyDataSetChanged();
            lstDepositosLiquidacion.setAdapter(totales_liquidacion_adapter);

            if (listaDepositos.size()>6){
                lstDepositosLiquidacion.getLayoutParams().height=alto/3;

            }

        }








    }


        }

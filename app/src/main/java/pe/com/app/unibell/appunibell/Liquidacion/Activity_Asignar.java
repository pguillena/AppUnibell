package pe.com.app.unibell.appunibell.Liquidacion;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pe.com.app.unibell.appunibell.AD.Cobranza_Liquidacion_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;

public class Activity_Asignar extends AppCompatActivity implements Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener,  Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener {

    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private SharedPreferences sharedSettings;
    private TextView lq_txttipopago,lq_txtccbanco,lq_txtnroope,lq_txtfecha ;
    private Button lq_btnasignar;
    private DialogFragment dialogFragmentFecha;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private Integer iAuxiliar=0;
    private Integer iTabla=0;
    private Integer iOpcionFecha=0;
    private TextView txtEstado, txtFechaFiltro;
    private Integer iEvento=0;
    private Documentos_Cobra_CabBE documentos_cobra_cabBE;
    private Cobranza_Liquidacion_Adapter cobranza_liquidacion_adapter = null;
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__asignar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Asignar forma de pago");
        getSupportActionBar().setSubtitle("");



        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);

        lq_btnasignar=(Button) findViewById(R.id.lq_btnasignar);

        lq_txttipopago=(TextView)findViewById(R.id.lq_txttipopago);
        lq_txtccbanco=(TextView)findViewById(R.id.lq_txtccbanco);
        lq_txtnroope=(TextView)findViewById(R.id.lq_txtnroope);
        lq_txtfecha=(TextView)findViewById(R.id.lq_txtfecha);

        lq_txttipopago.setOnClickListener(OnClickListenercl_lq_txttipopago);
        lq_txtccbanco.setOnClickListener(OnClickListenercl_lq_txtccbanco);
        lq_txtfecha.setOnClickListener(OnClickListenercl_lq_txtfecha);


        lq_btnasignar.setOnClickListener(OnClickListener_lq_btnasignar);

    }

    View.OnClickListener OnClickListener_lq_btnasignar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {


            String sMensaje = "¿Desea reasignar los documentos?";
            dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
            dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_Asignar.this, sMensaje);
            dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
            dialog_fragment_confirmar.isCancelable();




        }


    };



    View.OnClickListener OnClickListenercl_lq_txttipopago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=14;
                iAuxiliar=2;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Asignar.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_lq_txtccbanco = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=200;
                iAuxiliar=3;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Asignar.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_lq_txtfecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iOpcionFecha=2;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    @Override
    public void onTablaAuxiliarSI() {
        switch (iAuxiliar) {
            //ESTADO
            case 1:
             /*   lq_txtfestado.setTag(sharedSettings.getString("ICODTABAUX", "").toString());
                lq_txtfestado.setText(sharedSettings.getString("IDESTABAUX", "").toString());*/
                break;
            //FORMA DE PAGO
            case 2:
                lq_txttipopago.setTag(sharedSettings.getString("ICODTABAUX", "").toString());
                lq_txttipopago.setText(sharedSettings.getString("IDESTABAUX", "").toString());
                //limpiamos
                lq_txtccbanco.setTag("0");
                lq_txtccbanco.setText("");
                lq_txtnroope.setText("");
                if (lq_txttipopago.getTag().toString().trim().equals("E")|| lq_txttipopago.getTag().toString().trim().equals("Z")) {
                    lq_txtccbanco.setEnabled(false);
                }else{
                    lq_txtccbanco.setEnabled(true);
                }
                break;
            //BANCO
            case 3:
                lq_txtccbanco.setTag(sharedSettings.getString("ICODTABAUX", "").toString());
                lq_txtccbanco.setText(sharedSettings.getString("IDESTABAUX", "").toString());
                break;
        }
    }


    @Override
    public void setearFecha(String fecha) {
        try {
                lq_txtfecha.setText(fecha);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onConfirmacionSI() {
        try {

            // TODO Auto-generated method stub
            Intent data = new Intent();

            data.putExtra("hdfTipoPago", lq_txttipopago.getTag().toString());
            data.putExtra("hdfCCBanco", lq_txtccbanco.getTag().toString());
            data.putExtra("hdfNroOp", lq_txtnroope.getText().toString());
            data.putExtra("hdfFecha", lq_txtfecha.getText().toString());

            setResult(RESULT_OK, data);
            finish();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

package pe.com.app.unibell.appunibell.Liquidacion;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_FiltroLiquidacion extends AppCompatActivity implements Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener {

    private SharedPreferences sharedSettings;
    private TextView txtEstado, txtFechaFiltro, lblBuscarLiquidacion4;
    private EditText txtNroPlanilla, txtCpacking;
    private Button btnBuscarLiquidacion;
    private Integer iTabla=0;
    private Integer iAuxiliar=0;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private Integer iOpcionFecha=0,iOpcionHora=0;
    private DialogFragment dialogFragmentFecha;
    Funciones funciones = new Funciones();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__filtro_liquidacion);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtro liquidación");
        getSupportActionBar().setSubtitle("");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);

        txtEstado = (TextView)findViewById(R.id.lq_txtfestado);
        txtFechaFiltro = (TextView)findViewById(R.id.lq_txtffechaFiltro);
        txtNroPlanilla = (EditText)findViewById(R.id.lq_txtfplan);
        txtCpacking = (EditText)findViewById(R.id.lq_txtpaking);
        lblBuscarLiquidacion4 = (TextView)findViewById(R.id.lblBuscarLiquidacion4);


        funciones.addTextChangedListener(txtEstado, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtFechaFiltro, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtNroPlanilla, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtCpacking, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);


        lblBuscarLiquidacion4.setVisibility(View.GONE);
        txtCpacking.setVisibility(View.GONE);
        //(usuario.ROL == (int)EnumRoles.LiquidadorCobranzaDespacho || usuario.ROL == (int)EnumRoles.RegistradorPedidos)
        if(sharedSettings.getString("ROL", "").toString().equals("130019") || sharedSettings.getString("ROL", "").toString().equals("130008"))
        {
            lblBuscarLiquidacion4.setVisibility(View.VISIBLE);
            txtCpacking.setVisibility(View.VISIBLE);
        }


        txtEstado.setOnClickListener(OnClickListener_txtEstado);

        Bundle   parametros = getIntent().getExtras();

        if(parametros !=null){

            txtEstado.setTag(parametros.getString("txtEstado"));

            if(parametros.getString("txtEstado").equals("40003")){

                txtEstado.setText("REGISTRADO");
            }
            else if(parametros.getString("txtEstado").equals("40007")){

                txtEstado.setText("EN PROCESO");

            }
            else if(parametros.getString("txtEstado").equals("40005")){

                txtEstado.setText("APROBADO");
            }
            else if(parametros.getString("txtEstado").equals("40002")){

                txtEstado.setText("ANULADO");
            }
        }
        else {
            txtEstado.setTag("40003");
            txtEstado.setText("REGISTRADO");
        }

        txtFechaFiltro.setOnClickListener(OnClickListener_txtFechaFiltro);

        btnBuscarLiquidacion = (Button)findViewById(R.id.lq_lblbuscar);
        btnBuscarLiquidacion.setOnClickListener(OnClickListener_btnBuscarLiquidacion);

    }

    View.OnClickListener OnClickListener_txtFechaFiltro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iOpcionFecha=1;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };


    View.OnClickListener OnClickListener_txtEstado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla estado
                iTabla=100;
                iAuxiliar=1;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_FiltroLiquidacion.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListener_btnBuscarLiquidacion = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            try {

                // TODO Auto-generated method stub

                Intent data = new Intent();
                data.putExtra("txtNroPlanilla", txtNroPlanilla.getText().toString());
                data.putExtra("txtCpacking", txtCpacking.getText().toString());
                data.putExtra("lq_txtfestado",txtEstado.getTag().toString().trim());
                data.putExtra("lq_txtffechaFiltro", txtFechaFiltro.getText().toString());

                setResult(RESULT_OK, data);
                finish();


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
                txtEstado.setTag(sharedSettings.getString("ICODTABAUX", "").toString());
                txtEstado.setText(sharedSettings.getString("IDESTABAUX", "").toString());
                break;
            //FORMA DE PAGO
            /*case 2:
                lq_txttipopago.setTag(sharedSettings.getString("ICODTABAUX", "").toString());
                lq_txttipopago.setText(sharedSettings.getString("IDESTABAUX", "").toString());
                //limpiamos
                lq_txtccbanco.setTag("0");
                lq_txtccbanco.setText("");
                lq_txtnroope.setText("");
                lq_chktodos.setChecked(false);
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
                break;*/
        }
    }


    @Override
    public void setearFecha(String fecha) {
        try {
            if(iOpcionFecha==1){
                txtFechaFiltro.setText(fecha);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

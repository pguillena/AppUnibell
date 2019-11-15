package pe.com.app.unibell.appunibell.Liquidacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Tablas_AuxiliaresDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_FiltroLiquidacion extends AppCompatActivity implements Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener {

    private SharedPreferences sharedSettings;
    private TextView txtEstado, txtFechaFiltro, lblBuscarLiquidacion4, txtFormaPagoFiltroLiquidacion;
    private AppCompatAutoCompleteTextView txtClienteFiltroLiquidacion;
    private EditText txtNroPlanilla, txtCpacking;
    private Button btnBuscarLiquidacion;
    private Integer iTabla=0;
    private Integer iAuxiliar=0;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private Integer iOpcionFecha=0,iOpcionHora=0;
    private DialogFragment dialogFragmentFecha;
    private String vplb_lblfpago="XXX";
    Funciones funciones = new Funciones();
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes_Adapter clientes_adapter = null;
    private ArrayList<String> RazonSocialArray;
    private  Tablas_AuxiliaresDAO tablas_auxiliaresDAO = new Tablas_AuxiliaresDAO();


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
        txtFormaPagoFiltroLiquidacion  = (TextView)findViewById(R.id.txtFormaPagoFiltroLiquidacion);
        txtClienteFiltroLiquidacion = (AppCompatAutoCompleteTextView)findViewById(R.id.txtClienteFiltroLiquidacion);

        funciones.addTextChangedListener(txtEstado, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtFechaFiltro, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtNroPlanilla, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtCpacking, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtClienteFiltroLiquidacion, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);


        lblBuscarLiquidacion4.setVisibility(View.GONE);
        txtCpacking.setVisibility(View.GONE);
        //(usuario.ROL == (int)EnumRoles.LiquidadorCobranzaDespacho || usuario.ROL == (int)EnumRoles.RegistradorPedidos)
        if(sharedSettings.getString("ROL", "").toString().equals("130019") || sharedSettings.getString("ROL", "").toString().equals("130008"))
        {
            lblBuscarLiquidacion4.setVisibility(View.VISIBLE);
            txtCpacking.setVisibility(View.VISIBLE);
        }


        txtEstado.setOnClickListener(OnClickListener_txtEstado);
        txtFormaPagoFiltroLiquidacion.setOnClickListener(OnClickListener_txtFormaPagoFiltroLiquidacion);

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

            txtFechaFiltro.setText(parametros.getString("txtFechaFiltro"));
            txtNroPlanilla.setText(parametros.getString("txtNroPlanilla"));
            txtCpacking.setText(parametros.getString("txtCpacking"));
            txtClienteFiltroLiquidacion.setText(parametros.getString("txtClienteFiltroLiquidacion"));
            //txtFormaPagoFiltroLiquidacion.setTag(parametros.getString("txtFormaPagoFiltroLiquidacion"));
            CargarFiltroDefault("14", "0", parametros.getString("txtFormaPagoFiltroLiquidacion"), txtFormaPagoFiltroLiquidacion);




        }
        else {
            txtEstado.setTag("40003");
            txtEstado.setText("REGISTRADO");
        }

        txtFechaFiltro.setOnClickListener(OnClickListener_txtFechaFiltro);

        btnBuscarLiquidacion = (Button)findViewById(R.id.lq_lblbuscar);
        btnBuscarLiquidacion.setOnClickListener(OnClickListener_btnBuscarLiquidacion);
        AutoComplete();
    }

    private void CargarFiltroDefault(String tipo, String rol, String dato, TextView txtView) {

        tablas_auxiliaresDAO.getAll(tipo,rol);

        if(tablas_auxiliaresDAO.lst!=null && tablas_auxiliaresDAO.lst.size()>0)
        {
            for(int i =0; i< tablas_auxiliaresDAO.lst.size(); i++)
            {
                if(tablas_auxiliaresDAO.lst.get(i).getCODIGO().equals(dato))
                {
                    txtView.setText(tablas_auxiliaresDAO.lst.get(i).getDESCRIPCION());
                    txtView.setTag(tablas_auxiliaresDAO.lst.get(i).getCODIGO());
                }
            }
        }
    }


    View.OnClickListener OnClickListener_txtFormaPagoFiltroLiquidacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar = 2;
                iTabla = 14;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_FiltroLiquidacion.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception e) {
            }
        }
    };



    View.OnClickListener OnClickListener_txtFechaFiltro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iOpcionFecha=1;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getSupportFragmentManager(), "");

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
                if(txtFormaPagoFiltroLiquidacion.getText().toString().trim().equals("")) {
                    vplb_lblfpago = "XXX";
                }else{
                    vplb_lblfpago=  txtFormaPagoFiltroLiquidacion.getTag().toString();
                }


                Intent data = new Intent();
                data.putExtra("txtNroPlanilla", txtNroPlanilla.getText().toString());
                data.putExtra("txtCpacking", txtCpacking.getText().toString());
                data.putExtra("lq_txtfestado",txtEstado.getTag().toString().trim());
                data.putExtra("lq_txtffechaFiltro", txtFechaFiltro.getText().toString());
                data.putExtra("lq_txtfpagoFiltro", vplb_lblfpago);
                data.putExtra("txtClienteFiltroLiquidacion", txtClienteFiltroLiquidacion.getText().toString());

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
            case 2:
                txtFormaPagoFiltroLiquidacion.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                txtFormaPagoFiltroLiquidacion.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
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


    private class LoadClientesSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                clientesDAO.getAllBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8],p[9]);
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
                clientes_adapter = new Clientes_Adapter(getApplication(), 0, clientesDAO.lst);

                RazonSocialArray = new ArrayList<>();
                for (int i =0; i<clientes_adapter.getCount(); i++ )
                {
                    RazonSocialArray.add(((ClientesBE)clientes_adapter.getItem(i)).getRAZON_SOCIAL());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.my_list_item, RazonSocialArray);
                txtClienteFiltroLiquidacion.setThreshold(1); //will start working from first character
                txtClienteFiltroLiquidacion.setAdapter(adapter);

            } catch (Exception ex) {
                Toast.makeText(getApplication(),"No existen registros!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void AutoComplete() {
        try {
            new LoadClientesSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    sharedSettings.getString("ROL", "0").toString(),
                    "",
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_LOCAL", "0").toString()

            );

        } catch (Exception ex){

        }

    }

}

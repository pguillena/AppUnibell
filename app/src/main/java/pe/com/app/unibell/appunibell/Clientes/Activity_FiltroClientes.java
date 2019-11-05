package pe.com.app.unibell.appunibell.Clientes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_FiltroClientes extends AppCompatActivity {

    private EditText txtRUC, txtCodigoCliente, txtDNI, txtCpacking, txtGrupo;
    private TextView lblBuscarCliente6;
    private Button btnBuscarCientes;
    private AppCompatAutoCompleteTextView txtRazonSocial;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes_Adapter clientes_adapter = null;
    private SharedPreferences sharedSettings;
    private ArrayList<String> RazonSocialArray;
    Funciones funciones = new Funciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__filtro_clientes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtro de clientes");
        getSupportActionBar().setSubtitle("");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);


        txtRUC = (EditText)findViewById(R.id.cl_txtruc);
        txtCodigoCliente = (EditText)findViewById(R.id.cl_txtcodigo);
        txtRazonSocial = (AppCompatAutoCompleteTextView)findViewById(R.id.cl_txtcliente);
        txtDNI = (EditText)findViewById(R.id.cl_txtdni);
        txtCpacking = (EditText)findViewById(R.id.cl_txtpdespacho);
        txtGrupo = (EditText)findViewById(R.id.cl_txtgrupo);
        lblBuscarCliente6 = (TextView)findViewById(R.id.lblBuscarCliente6);


        funciones.addTextChangedListener(txtRUC, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtCodigoCliente, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtRazonSocial, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtDNI, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtCpacking, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtGrupo, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);


        lblBuscarCliente6.setVisibility(View.GONE);
        txtCpacking.setVisibility(View.GONE);

        //(usuario.ROL == (int)EnumRoles.LiquidadorCobranzaDespacho || usuario.ROL == (int)EnumRoles.RegistradorPedidos)
        if(sharedSettings.getString("ROL", "").toString().equals("130019") || sharedSettings.getString("ROL", "").toString().equals("130008"))
        {
            lblBuscarCliente6.setVisibility(View.VISIBLE);
            txtCpacking.setVisibility(View.VISIBLE);
        }



        Bundle parametros = getIntent().getExtras();

        if(parametros !=null){
            txtRazonSocial.setText(parametros.getString("txtRazonSocial"));
            txtRUC.setText(parametros.getString("txtRUC"));
            txtDNI.setText(parametros.getString("txtDNI"));
            txtCodigoCliente.setText(parametros.getString("txtCodigoCliente"));
            txtGrupo.setText(parametros.getString("txtGrupo"));
            txtCpacking.setText(parametros.getString("txtCpacking"));
        }
        AutoComplete();
        btnBuscarCientes = (Button)findViewById(R.id.cl_lblbuscarClientes);
        btnBuscarCientes.setOnClickListener(OnClickListener_btnBuscarCientes);



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


    View.OnClickListener OnClickListener_btnBuscarCientes = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            try {

                // TODO Auto-generated method stub
                Intent data = new Intent();

                data.putExtra("hdfRazonSocial", txtRazonSocial.getText().toString());
                data.putExtra("hdfRUC", txtRUC.getText().toString());
                data.putExtra("hdfDNI", txtDNI.getText().toString());
                data.putExtra("hdfCodigoCliente", txtCodigoCliente.getText().toString());
                data.putExtra("hdfGrupo", txtGrupo.getText().toString());
                data.putExtra("hdfCPacking", txtCpacking.getText().toString());

               // data.setData(Uri.parse(cad));
                setResult(RESULT_OK, data);
                finish();


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    };

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
                txtRazonSocial.setThreshold(1); //will start working from first character
                txtRazonSocial.setAdapter(adapter);

            } catch (Exception ex) {
                Toast.makeText(getApplication(),"No existen registros!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



}

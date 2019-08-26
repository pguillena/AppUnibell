package pe.com.app.unibell.appunibell.Clientes;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.R;

public class Activity_FiltroClientes extends AppCompatActivity {

    private EditText txtRUC, txtCodigoCliente, txtDNI, txtCpacking, txtGrupo;
    private Button btnBuscarCientes;
    private AppCompatAutoCompleteTextView txtRazonSocial;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes_Adapter clientes_adapter = null;
    private SharedPreferences sharedSettings;
    private ArrayList<String> RazonSocialArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__filtro_clientes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);


        txtRUC = (EditText)findViewById(R.id.cl_txtruc);
        txtCodigoCliente = (EditText)findViewById(R.id.cl_txtcodigo);
        txtRazonSocial = (AppCompatAutoCompleteTextView)findViewById(R.id.cl_txtcliente);
        txtDNI = (EditText)findViewById(R.id.cl_txtdni);
        txtCpacking = (EditText)findViewById(R.id.cl_txtpdespacho);
        txtGrupo = (EditText)findViewById(R.id.cl_txtgrupo);

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

    private void AutoComplete() {

        try {
            new Activity_FiltroClientes.LoadClientesSQLite_AsyncTask().execute(
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

package pe.com.app.unibell.appunibell.Clientes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.R;

public class Activity_clientes extends AppCompatActivity  {

   public String hdfCodigoCliente="", hdfRazonSocial="", hdfRUC="", hdfDNI="", hdfGrupo="", hdfCPacking="0";
   private TextView cl_lblregistros;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private ListView  cl_lvclientes;
    private Clientes_Adapter clientes_adapter = null;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private TextView cl_codbar;
    private Typeface script;
    int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_list);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
            getSupportActionBar().setSubtitle("LISTA DE CLIENTES");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            cl_lblregistros=(TextView) findViewById(R.id.cl_lblregistros);
            cl_lvclientes=(ListView)findViewById(R.id.cl_lvclientes);
            cl_lvclientes.setOnItemClickListener(cl_lvclientes_OnItemClickListener);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(OnClickListener_fab);

            this.Cargar();

        } catch (Exception ex) {

        }
    }


    View.OnClickListener OnClickListener_fab = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            try {
                Intent intent = new Intent(Activity_clientes.this, Activity_FiltroClientes.class);

                intent.putExtra("txtRazonSocial",hdfRazonSocial);
                intent.putExtra("txtRUC",hdfRUC);
                intent.putExtra("txtDNI",hdfDNI);
                intent.putExtra("txtCodigoCliente",hdfCodigoCliente);
                intent.putExtra("txtGrupo",hdfGrupo);
                intent.putExtra("txtCpacking",hdfCPacking);
                startActivityForResult(intent,request_code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            Bundle parametros = data.getExtras();
            if(parametros !=null){
                hdfRazonSocial =  parametros.getString("hdfRazonSocial");
                hdfRUC =  parametros.getString("hdfRUC");
                hdfDNI =  parametros.getString("hdfDNI");
                hdfCodigoCliente =  parametros.getString("hdfCodigoCliente");
                hdfGrupo =  parametros.getString("hdfGrupo");
                hdfCPacking =  parametros.getString("hdfCPacking");
                Cargar();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_filtro_list, menu);
        final MenuItem ic_action_refresh = menu.findItem(R.id.ic_action_refresh);
        final MenuItem ic_action_buscar = menu.findItem(R.id.ic_action_buscar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(ic_action_buscar);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        searchView.setQueryHint("Buscar clientes");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if(cl_lvclientes.getAdapter()!=null) {
                        Clientes_Adapter ca = (Clientes_Adapter) cl_lvclientes.getAdapter();
                        ca.getFilter().filter(newText);
                    }
                } else {
                    Clientes_Adapter ca = (Clientes_Adapter) cl_lvclientes.getAdapter();
                    ca.getFilter().filter(newText);
                }
                return true;
            }
        });

        ic_action_refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Cargar();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    private void Cargar(){
        try {
            new LoadClientesSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    hdfRazonSocial.trim(),
                    hdfRUC.trim(),
                    hdfDNI.trim(),
                    hdfCodigoCliente.trim(),
                    hdfGrupo.trim(),
                    sharedSettings.getString("ROL", "0").toString(),
                    hdfCPacking.trim(),
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_LOCAL", "0").toString()
                    );

    } catch (Exception ex){
       ex.printStackTrace();
    }
    }


    AbsListView.OnItemClickListener cl_lvclientes_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                clientes_adapter.notifyDataSetChanged();
                cl_lvclientes.setAdapter(clientes_adapter);
                cl_lblregistros.setText("Registro(s) " + String.valueOf(clientes_adapter.getCount()));
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
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

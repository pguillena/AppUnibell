package pe.com.app.unibell.appunibell.Clientes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Cliente_Visita_Adapter;
import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.BE.VisitaDetBE;
import pe.com.app.unibell.appunibell.DAO.VisitaDetDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_Visita_Cliente extends AppCompatActivity {

    private  ListView listVisitaClientes;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private VisitaDetDAO visitaDetDAO = new VisitaDetDAO();
    private Cliente_Visita_Adapter cliente_Visita_Adapter ;
    private String    hdfRazonSocial,   hdfCodigoCliente,   hdfFechaVisita, hdfDistritoVisita;

    int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_list);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Recorrido");
            getSupportActionBar().setSubtitle("");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            listVisitaClientes=(ListView)findViewById(R.id.listVisitaClientes);
            FloatingActionButton fabVisitaFiltro = (FloatingActionButton) findViewById(R.id.fabVisitaFiltro);
            fabVisitaFiltro.setOnClickListener(OnClickListener_fabVisitaFiltro);

            Cargar();



        } catch (Exception ex) {

        }
    }

    View.OnClickListener OnClickListener_fabVisitaFiltro = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            BuscarVisita();
        }


    };

    private void BuscarVisita() {



        try {
            Intent intent = new Intent(Activity_Visita_Cliente.this, Activity_Filtro_Visita.class);
      /*
            intent.putExtra("txtRazonSocial",hdfRazonSocial);
            intent.putExtra("txtRUC",hdfRUC);
            intent.putExtra("txtDNI",hdfDNI);
            intent.putExtra("txtCodigoCliente",hdfCodigoCliente);
            intent.putExtra("txtGrupo",hdfGrupo);
            intent.putExtra("txtCpacking",hdfCPacking);

            */
            startActivityForResult(intent,request_code);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            Bundle parametros = data.getExtras();
            if(parametros !=null){

                hdfRazonSocial =  parametros.getString("hdfRazonSocial","XXX");
                hdfCodigoCliente =  parametros.getString("hdfCodigoCliente","XXX");
                hdfFechaVisita =  parametros.getString("hdfFechaVisita","XXX");
                hdfDistritoVisita =  parametros.getString("hdfDistritoVisita","XXX");

                Cargar();
            }

        }
    }


    private class LoadVisitaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                 visitaDetDAO.getRecorrido(p[0],p[1],p[2],p[3],p[4],p[5]);

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
                ArrayList<VisitaDetBE> lst  = visitaDetDAO.lst;
                cliente_Visita_Adapter = new Cliente_Visita_Adapter(getApplication(), 0, lst);
                cliente_Visita_Adapter.notifyDataSetChanged();
                listVisitaClientes.setAdapter(cliente_Visita_Adapter);
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void Cargar(){

        String fecha = new Funciones().FechaActual();

        if(hdfFechaVisita!=null && !hdfFechaVisita.equals("XXX") && !hdfFechaVisita.equals("") )
        {
            fecha = hdfFechaVisita;
        }

        String fechaFormateada = fecha.substring(6,10) + fecha.substring(3,5)+ fecha.substring(0,2);

        if(hdfRazonSocial == null || hdfRazonSocial.equals(""))
        {
            hdfRazonSocial ="XXX";
        }

        if(hdfCodigoCliente == null || hdfCodigoCliente.equals(""))
        {
            hdfCodigoCliente ="XXX";
        }

        if(hdfDistritoVisita == null || hdfDistritoVisita.equals(""))
        {
            hdfDistritoVisita ="XXX";
        }


        try {


            new LoadVisitaSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    fechaFormateada,
                    hdfCodigoCliente,
                    hdfRazonSocial,
                    hdfDistritoVisita
            );

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }



    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                    if(listVisitaClientes.getAdapter()!=null) {
                        Cliente_Visita_Adapter ca = (Cliente_Visita_Adapter) listVisitaClientes.getAdapter();
                        ca.getFilter().filter(newText);
                    }
                } else {
                    Cliente_Visita_Adapter ca = (Cliente_Visita_Adapter) listVisitaClientes.getAdapter();
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


}

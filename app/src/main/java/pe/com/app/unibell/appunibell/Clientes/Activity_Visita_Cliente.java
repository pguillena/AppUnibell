package pe.com.app.unibell.appunibell.Clientes;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Cliente_Visita_Adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita_list);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Listado de clientes");
            getSupportActionBar().setSubtitle("");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            listVisitaClientes=(ListView)findViewById(R.id.listVisitaClientes);
            FloatingActionButton fabVisitaFiltro = (FloatingActionButton) findViewById(R.id.fabVisitaFiltro);
            fabVisitaFiltro.setOnClickListener(OnClickListener_fabVisitaFiltro);

            BuscarVisita();



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

       /* try {
            Intent intent = new Intent(Activity_Visita_Cliente.this, Activity_FiltroClientes.class);

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
        */
    }


    private class LoadVisitaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                 visitaDetDAO.getRecorrido(p[0],p[1],p[2],p[3]);

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
        String fechaFormateada = fecha.substring(7,10) + fecha.substring(4,6)+ fecha.substring(1,2);


        try {


            new LoadVisitaSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    fechaFormateada,
                    "XXX"
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


}

package pe.com.app.unibell.appunibell.Clientes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.VisitaDetDAO;
import pe.com.app.unibell.appunibell.R;

public class Activity_clientes extends AppCompatActivity  {

   public String hdfCodigoCliente="", hdfRazonSocial="", hdfRUC="", hdfDNI="", hdfGrupo="", hdfCPacking="0";
   private TextView cl_lblregistros, txtPAE, TxtNroPacking;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private ListView  cl_lvclientes;
    private Clientes_Adapter clientes_adapter = null;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private TextView cl_codbar;
    private Typeface script;
    int request_code = 1;
    int colorOjito=0;


    LinearLayout lyPlanillaDespacho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_list);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Listado de clientes");
            getSupportActionBar().setSubtitle("");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            cl_lblregistros=(TextView) findViewById(R.id.cl_lblregistros);
            cl_lvclientes=(ListView)findViewById(R.id.cl_lvclientes);
            lyPlanillaDespacho = (LinearLayout)findViewById(R.id.lyPlanillaDespacho);
            TxtNroPacking = (TextView)findViewById(R.id.TxtNroPacking);
            txtPAE= (TextView)findViewById(R.id.txtPAE);
            cl_lvclientes.setOnItemClickListener(cl_lvclientes_OnItemClickListener);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(OnClickListener_fab);
            txtPAE.setOnClickListener(OnClickListener_txtPAE);

            lyPlanillaDespacho.setVisibility(View.GONE);
            txtPAE.setVisibility(View.GONE);
            BuscarCliente();



        } catch (Exception ex) {

        }
    }

    View.OnClickListener OnClickListener_txtPAE = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if(colorOjito == 0) {
                txtPAE.setTextColor(getResources().getColor(R.color.Button_login_unibell));
                txtPAE.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.eye_active),null);
                colorOjito = 1;
                Cargar();
            }
            else if(colorOjito == 1)
            {
                txtPAE.setTextColor(getResources().getColor(R.color.label_login_unibell));
                txtPAE.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(getApplicationContext(), R.drawable.eye_off),null);
                colorOjito = 0;
                Cargar();
            }
        }

    };


    View.OnClickListener OnClickListener_fab = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
          BuscarCliente();
        }


    };

    private void BuscarCliente() {
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

            editor_Shared.putString("C_PACKING", hdfCPacking.trim());
            editor_Shared.commit();

            if(hdfCPacking.trim()!=null && !hdfCPacking.trim().equals("")  && Integer.parseInt(hdfCPacking.trim())>0)
            {
                TxtNroPacking.setText("Planilla de despacho: N° "+hdfCPacking.trim());
                lyPlanillaDespacho.setVisibility(View.VISIBLE);
                txtPAE.setVisibility(View.VISIBLE);
            }
            else
            {
                lyPlanillaDespacho.setVisibility(View.GONE);
                txtPAE.setVisibility(View.GONE);
            }

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
                ArrayList<ClientesBE> lst  = new ArrayList<ClientesBE>();
                if (colorOjito==1)
                {
                    for(int i=0; i<clientesDAO.lst.size(); i++)
                    {
                        if ( Double.valueOf(clientesDAO.lst.get(i).getM_PAE() )>0.0)
                        {
                            lst.add(clientesDAO.lst.get(i));

                        }

                    }
                }
                else
                {
                    lst =clientesDAO.lst;

                }

                clientes_adapter = new Clientes_Adapter(getApplication(), 0, lst);
                clientes_adapter.notifyDataSetChanged();
                cl_lvclientes.setAdapter(clientes_adapter);
                cl_lblregistros.setText("Registro(s) " + String.valueOf(lst.size()));
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

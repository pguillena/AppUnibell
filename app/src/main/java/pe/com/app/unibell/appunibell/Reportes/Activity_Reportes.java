package pe.com.app.unibell.appunibell.Reportes;

import android.arch.core.util.Function;
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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Aprobacion_Planilla_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Reporte_Adapter;
import pe.com.app.unibell.appunibell.BL.Cobranzas_ReporteBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.Planilla.Fragment_AprobacionPlanilla;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_Reportes extends AppCompatActivity {

    private ListView cr_lsdetalle;
    private FloatingActionButton cr_fabbuscar;
    private TextView cr_btnverdetalle,cr_lbltotalg;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Cobranzas_ReporteBL cobranzas_reporteBL = new Cobranzas_ReporteBL();
    private Cobranza_Reporte_Adapter cobranza_reporte_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_reportes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reporte de cobranzas");


        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();


        cr_lsdetalle=(ListView)findViewById(R.id.cr_lsdetalle);
        cr_fabbuscar=(FloatingActionButton)findViewById(R.id.cr_fabbuscar);
        cr_btnverdetalle=(TextView)findViewById(R.id.cr_btnverdetalle);
        cr_lbltotalg=(TextView)findViewById(R.id.cr_lbltotalg);

        cr_btnverdetalle.setOnClickListener(OnClickListener_cr_btnverdetalle);
        cr_fabbuscar.setOnClickListener(OnClickListener_cr_fabbuscar);

        Cargar();
    }

    View.OnClickListener OnClickListener_cr_btnverdetalle = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    View.OnClickListener OnClickListener_cr_fabbuscar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Activity_Reportes.this, Activity_Reporte_Filtro.class);
            startActivityForResult(intent,1);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_filtro_list, menu);
        final MenuItem ic_action_refresh = menu.findItem(R.id.ic_action_refresh);
        final MenuItem ic_action_buscar = menu.findItem(R.id.ic_action_buscar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(ic_action_buscar);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        searchView.setQueryHint("Ingrese dato a buscar");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if(cr_lsdetalle.getAdapter()!=null) {
                        Cobranza_Reporte_Adapter ca = (Cobranza_Reporte_Adapter) cr_lsdetalle.getAdapter();
                        ca.getFilter().filter(newText);
                    }
                } else {
                    Cobranza_Reporte_Adapter ca = (Cobranza_Reporte_Adapter) cr_lsdetalle.getAdapter();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
         if (resultCode == RESULT_OK)  {
                try {
                    Cargar();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
    }



    public void Cargar(){
        try{

            String fechainicio="",fechafin="";

            String finicio = sharedSettings.getString("plb_lblinicio", "").toString();
            String ffin = sharedSettings.getString("plb_lblfin", "").toString();

            if (!finicio.toString().trim().equals("")) {
                fechainicio = finicio.toString().substring(6)
                        + finicio.toString().substring(3, 5)
                        + finicio.toString().substring(0, 2);
            }else{
                fechainicio="17530101";
            }

            if (!ffin.toString().trim().equals("")) {
                fechafin = ffin.toString().substring(6)
                        + ffin.toString().substring(3, 5)
                        + ffin.toString().substring(0, 2);
            }else{
                fechafin="17530101";
            }

                new Load_AsyncTask().execute(
                ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_RptCobranza + "/" +
                        fechainicio+ "/" +
                        fechafin+ "/" +
                        sharedSettings.getString("plb_txtcodigo", "XXX").toString()+ "/" +
                        sharedSettings.getString("plb_lblfpago", "0").toString()+ "/" +
                        sharedSettings.getString("iID_VENDEDOR", "0").toString()+ "/" +
                        sharedSettings.getString("plb_txtseriep", "0").toString()+ "/" +
                        sharedSettings.getString("plb_txtnumerop", "0").toString()+ "/" +
                        sharedSettings.getString("plb_lblestado", "0").toString()+ "/" +
                        sharedSettings.getString("iID_EMPRESA", "1").toString() + "/" +
                        sharedSettings.getString("iID_LOCAL", "1").toString() + "/" +
                        sharedSettings.getString("plb_lbltipodoc", "XXX").toString()+ "/" +
                        sharedSettings.getString("plb_txtserief", "XXX").toString()+ "/" +
                        sharedSettings.getString("plb_txtfnumerof", "XXX").toString()+ "/" +
                        sharedSettings.getString("plb_lblmoneda", "XXX").toString()+ "/" +
                        sharedSettings.getString("plb_txtoserie", "0").toString()+ "/" +
                        sharedSettings.getString("plb_txtonumero", "0").toString()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public class Load_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return cobranzas_reporteBL.getAllRest(p[0]);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }


        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Reportes.this, result.getString("message")).Show();
                } else {
                    cobranza_reporte_adapter = new Cobranza_Reporte_Adapter(Activity_Reportes.this, 0, cobranzas_reporteBL.lst);
                    cobranza_reporte_adapter.notifyDataSetChanged();
                    cr_lsdetalle.setAdapter(cobranza_reporte_adapter);

                 Double dTotalGeneral=0.0;

                    for (int j = 0; j < cobranza_reporte_adapter.lstFiltrado.size(); j++) {
                        dTotalGeneral+= Double.valueOf(cobranza_reporte_adapter.lst.get(j).getM_COBRANZA());
                    }
                    cr_lbltotalg.setText(Funciones.FormatDecimal(dTotalGeneral.toString()));

                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }




}

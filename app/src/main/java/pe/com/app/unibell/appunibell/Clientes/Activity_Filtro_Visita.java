package pe.com.app.unibell.appunibell.Clientes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;


public class Activity_Filtro_Visita
        extends AppCompatActivity implements Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener {

    private SharedPreferences sharedSettings;
    private TextView ddlFechaFiltroVisita,  txtCodigoClienteFiltroVisita, ddlDistritoFiltroVisita;
    private Button btnBuscarRecorrido;
    Dialogo_Fragment_Fecha dialogFragmentFecha ;
    Dialog_Fragment_Auxiliar dialog_fragment_auxiliar ;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes_Adapter clientes_adapter = null;
    Funciones funciones = new Funciones();
    private ArrayList<String> RazonSocialArray;

    private AppCompatAutoCompleteTextView txtRazonSocialFiltroVisita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_visita);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filtro recorrido");
        getSupportActionBar().setSubtitle("");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);

        ddlFechaFiltroVisita = (TextView)findViewById(R.id.ddlFechaFiltroVisita);
        txtRazonSocialFiltroVisita = (AppCompatAutoCompleteTextView)findViewById(R.id.txtRazonSocialFiltroVisita);
        txtCodigoClienteFiltroVisita = (TextView)findViewById(R.id.txtCodigoClienteFiltroVisita);
        ddlDistritoFiltroVisita = (TextView)findViewById(R.id.ddlDistritoFiltroVisita);
        btnBuscarRecorrido = (Button) findViewById(R.id.btnBuscarRecorrido);


        funciones.addTextChangedListener(ddlFechaFiltroVisita, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtRazonSocialFiltroVisita, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(txtCodigoClienteFiltroVisita, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);
        funciones.addTextChangedListener(ddlDistritoFiltroVisita, R.drawable.borderradius_busqueda_cliente_activo, R.drawable.borderradius_busqueda_cliente);

        ddlFechaFiltroVisita.setOnClickListener(OnClickListener_ddlFechaFiltroVisita);
        ddlDistritoFiltroVisita.setOnClickListener(OnClickListener_ddlDistritoFiltroVisita);

        Bundle   parametros = getIntent().getExtras();

        /*
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
        }
        else {
            txtEstado.setTag("40003");
            txtEstado.setText("REGISTRADO");
        }
        */

        btnBuscarRecorrido.setOnClickListener(OnClickListener_btnBuscarRecorrido);
        AutoComplete();
    }


    View.OnClickListener OnClickListener_btnBuscarRecorrido = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            try {

                // TODO Auto-generated method stub
                Intent data = new Intent();

                data.putExtra("hdfRazonSocial", txtRazonSocialFiltroVisita.getText().toString());
                data.putExtra("hdfCodigoCliente", txtCodigoClienteFiltroVisita.getText().toString());
                data.putExtra("hdfFechaVisita", ddlFechaFiltroVisita.getText().toString());
                if(ddlDistritoFiltroVisita.getTag()!=null) {
                    data.putExtra("hdfDistritoVisita", ddlDistritoFiltroVisita.getTag().toString());
                }

                // data.setData(Uri.parse(cad));
                setResult(RESULT_OK, data);
                finish();


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    };

    View.OnClickListener OnClickListener_ddlFechaFiltroVisita = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getSupportFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };


    View.OnClickListener OnClickListener_ddlDistritoFiltroVisita = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Filtro_Visita.this,800,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };


    @Override
    public void onTablaAuxiliarSI() {
        ddlDistritoFiltroVisita .setTag(sharedSettings.getString("iCOD_UBIGEO", "").toString());
        ddlDistritoFiltroVisita .setText(sharedSettings.getString("iNOM_UBIGEO", "").toString());
    }

    @Override
    public void setearFecha(String fecha) {

        ddlFechaFiltroVisita.setText(fecha);
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
                txtRazonSocialFiltroVisita.setThreshold(1); //will start working from first character
                txtRazonSocialFiltroVisita.setAdapter(adapter);

            } catch (Exception ex) {
                Toast.makeText(getApplication(),"No existen registros!!", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void AutoComplete() {
        try {
            new Activity_Filtro_Visita.LoadClientesSQLite_AsyncTask().execute(
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

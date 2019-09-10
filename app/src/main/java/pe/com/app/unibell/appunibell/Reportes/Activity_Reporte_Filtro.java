package pe.com.app.unibell.appunibell.Reportes;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.Clientes.Activity_FiltroClientes;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_Reporte_Filtro extends AppCompatActivity
        implements Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener{

    private TextView plb_lblestado,plb_lblinicio,plb_lblfin,plb_lblfpago,plb_lbltipodoc,plb_lblmoneda;
    private EditText plb_txtcodigo,plb_txtnumero,plb_txtseriep,plb_txtnumerop,plb_txtserief;
    private EditText plb_txtfnumerof,plb_txtoserie,plb_txtonumero;
    private Button plb_btnregistrar;
    private AppCompatAutoCompleteTextView plb_txtcliente ;
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes_Adapter clientes_adapter = null;
    private ArrayList<String> RazonSocialArray;

    private Integer iFiltroFecha =0;
    private Integer iAuxiliar = 0,iTabla=0;
    private DialogFragment dialogFragmentFecha;
    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_filtro);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
        getSupportActionBar().setSubtitle("REPORTE");

        sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();


        plb_txtcodigo = (EditText)findViewById(R.id.plb_txtcodigo);
        plb_txtcliente = (AppCompatAutoCompleteTextView)findViewById(R.id.plb_txtcliente);
        plb_txtnumero = (EditText)findViewById(R.id.plb_txtnumero);
        plb_txtseriep = (EditText)findViewById(R.id.plb_txtseriep);
        plb_txtnumerop = (EditText)findViewById(R.id.plb_txtnumerop);
        plb_txtserief = (EditText)findViewById(R.id.plb_txtserief);
        plb_txtfnumerof = (EditText)findViewById(R.id.plb_txtfnumerof);
        plb_txtoserie = (EditText)findViewById(R.id.plb_txtoserie);
        plb_txtonumero = (EditText)findViewById(R.id.plb_txtonumero);

        plb_lblestado = (TextView)findViewById(R.id.plb_lblestado);
        plb_lblinicio = (TextView)findViewById(R.id.plb_lblinicio);
        plb_lblfin = (TextView)findViewById(R.id.plb_lblfin);
        plb_lblfpago = (TextView)findViewById(R.id.plb_lblfpago);
        plb_lbltipodoc = (TextView)findViewById(R.id.plb_lbltipodoc);
        plb_lblmoneda = (TextView)findViewById(R.id.plb_lblmoneda);
        plb_btnregistrar = (Button) findViewById(R.id.plb_btnregistrar);

        plb_lblestado.setOnClickListener(OnClickList_plb_lblestado);
        plb_lblinicio.setOnClickListener(OnClickList_plb_lblinicio);
        plb_lblfin.setOnClickListener(OnClickList_plb_lblfin);
        plb_lblfpago.setOnClickListener(OnClickListener_plb_lblfpago);
        plb_lbltipodoc.setOnClickListener(OnClickListener_plb_lbltipodoc);
        plb_lblmoneda.setOnClickListener(OnClickListener_plb_lblmoneda);
        plb_btnregistrar.setOnClickListener(OnClickListener_plb_btnregistrar);

        AutoComplete();

    }

    View.OnClickListener OnClickList_plb_lblinicio = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iFiltroFecha=1;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_plb_lblfin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iFiltroFecha=2;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickListener_plb_lblfpago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar = 1;
                iTabla = 14;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Reporte_Filtro.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception e) {
            }
        }
    };
    View.OnClickListener OnClickList_plb_lblestado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción Estado
                iAuxiliar=2;
                iTabla=100;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Reporte_Filtro.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickListener_plb_lbltipodoc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción Estado
                iAuxiliar=3;
                iTabla=500;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Reporte_Filtro.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);



            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickListener_plb_lblmoneda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar=4;
                iTabla=600;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Reporte_Filtro.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };



    View.OnClickListener OnClickListener_plb_btnregistrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent data = new Intent();
                editor_Shared.putString("plb_txtcodigo",plb_txtcodigo.getText().toString().replace("","XXX"));
                editor_Shared.putString("plb_lblfpago",plb_lblfpago.getTag().toString().replace("","XXX"));
                editor_Shared.putString("plb_txtseriep", plb_txtseriep.getText().toString().replace("","0"));
                editor_Shared.putString("plb_txtnumerop",plb_txtnumerop.getText().toString().replace("","0"));
                editor_Shared.putString("plb_lblestado",plb_lblestado.getTag().toString().replace("","0"));
                editor_Shared.putString("plb_lbltipodoc",plb_lbltipodoc.getTag().toString().replace("","XXX"));
                editor_Shared.putString("plb_txtserief", plb_txtserief.getText().toString().replace("","XXX"));
                editor_Shared.putString("plb_txtfnumerof",plb_txtfnumerof.getText().toString().replace("","XXX"));
                editor_Shared.putString("plb_lblmoneda",plb_lblmoneda.getTag().toString().replace("","XXX"));
                editor_Shared.putString("plb_txtoserie",plb_txtoserie.getText().toString().replace("","0"));
                editor_Shared.putString("plb_txtonumero",plb_txtonumero.getText().toString().replace("","0"));
                editor_Shared.putString("plb_txtcliente",plb_txtcliente.getTag().toString().replace("","0"));
                editor_Shared.putString("plb_txtnumero", plb_txtnumero.getText().toString().replace("","0"));
                editor_Shared.putString("plb_lblinicio",plb_lblinicio.getText().toString().replace("","XXX"));
                editor_Shared.putString("plb_lblfin",plb_lblfin.getText().toString().replace("","XXX"));



                editor_Shared.commit();

                setResult(RESULT_OK, data);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };


    @Override
    public void onAceptar() {

    }

    @Override
    public void onTablaAuxiliarSI() {
        switch (iAuxiliar) {
            case 1:
                plb_lblfpago.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                plb_lblfpago.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 2:
                plb_lblestado.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                plb_lblestado.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 3:
                plb_lbltipodoc.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                plb_lbltipodoc.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 4:
                plb_lblmoneda.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                plb_lblmoneda.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;


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
                plb_txtcliente.setThreshold(1); //will start working from first character
                plb_txtcliente.setAdapter(adapter);

            } catch (Exception ex) {
                Toast.makeText(getApplication(),"No existen registros!!", Toast.LENGTH_LONG).show();
            }
        }
    }



    @Override
    public void setearFecha(String fecha) {
        if(iFiltroFecha==1){
            plb_lblinicio.setText(fecha);
        }
        if(iFiltroFecha==2){
            plb_lblfin.setText(fecha);
        }
    }

}

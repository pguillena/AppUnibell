package pe.com.app.unibell.appunibell.Clientes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import pe.com.app.unibell.appunibell.BL.ClientesBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_ClienteBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_Cliente_Codigo_AntBL;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_MigrarCliente extends AppCompatActivity implements Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,  Dialog_Fragment_Aceptar.DialogFragmentAceptarListener {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private  EditText plb_txtcodClienteMigrar;
    private  Button plb_btnmigrar;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private Integer iAccion = 0;
    private ClientesDAO clientesDAO;
    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Progress clientesPG;
    private Dialog_Fragment_Progress s_gem_clientePG;
    private Dialog_Fragment_Progress s_gem_cliente_codigo_antPG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_migrar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Migrar cliente");

        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();
        plb_txtcodClienteMigrar = (EditText) findViewById(R.id.plb_txtcodClienteMigrar);
        plb_btnmigrar = (Button) findViewById(R.id.plb_btnmigrar);
        plb_btnmigrar.setOnClickListener(OnClickListener_plb_btnmigrar);

    }


    View.OnClickListener OnClickListener_plb_btnmigrar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(!plb_txtcodClienteMigrar.getText().toString().equals(""))
            {
                iAccion = 1;
                String sMensaje = "¿Desea migrar al cliente?";
                dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_MigrarCliente.this, sMensaje);
                dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
                dialog_fragment_confirmar.isCancelable();
            }

        }
    };


    @Override
    public void onConfirmacionSI() {

        if(iAccion==1)
        {
            clientesDAO = new ClientesDAO();
            clientesDAO.getByCodCliente(plb_txtcodClienteMigrar.getText().toString().toUpperCase());

            if(clientesDAO.lst!=null && clientesDAO.lst.size()>0)
            {
                Mensaje("El cliente ya existe");
            }
            else
            {
              Migrar(plb_txtcodClienteMigrar.getText().toString().trim().toUpperCase());
            }

        }
    }

    private void Migrar(String codCliente) {

        try{
            new Activity_MigrarCliente.S_Gem_Cliente_CodigoBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_cliente_codigo_ant + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + codCliente);
        } catch (Exception ex) {
            new ToastLibrary(this,"Error al Sincronizar Clientes anteriores.").Show();
        }


        try{
            new Activity_MigrarCliente.S_Gem_ClienteBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_clientexCodigo + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + codCliente);
        } catch (Exception ex) {
            new ToastLibrary(Activity_MigrarCliente.this,"Error al Sincronizar Gem Clientes.").Show();
        }

        try{
            new Activity_MigrarCliente.Cliente_Sincronizar_AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blclientesxcodigo + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + codCliente);
        } catch (Exception ex) {
            new ToastLibrary(this,"Error al sincronizar el cliente.").Show();
        }




    }

    private void Mensaje(String msj){
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Activity_MigrarCliente.this);
        log_dialogaceptar.show(getSupportFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

    @Override
    public void onAceptar() {

    }


    public class Cliente_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            clientesPG = new Dialog_Fragment_Progress();
            clientesPG.setMensaje("Sincronizando");
            clientesPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new ClientesBL().getSincronizaxCodigo(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (clientesPG != null && clientesPG.isVisible()) {
                clientesPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_MigrarCliente.this, result.getString("message")+ ":Cliente").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.clientesBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class S_Gem_ClienteBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_clientePG = new Dialog_Fragment_Progress();
            s_gem_clientePG.setMensaje("Sincronizando");
            s_gem_clientePG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new S_Gem_ClienteBL().getSincronizarxCod(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_clientePG != null && s_gem_clientePG.isVisible()) {
                s_gem_clientePG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_MigrarCliente.this, result.getString("message") + ":Cliente").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_clienteBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class S_Gem_Cliente_CodigoBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_cliente_codigo_antPG = new Dialog_Fragment_Progress();
            s_gem_cliente_codigo_antPG.setMensaje("Sincronizando");
            s_gem_cliente_codigo_antPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new S_Gem_Cliente_Codigo_AntBL().getSincronizarxCod(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_cliente_codigo_antPG != null && s_gem_cliente_codigo_antPG.isVisible()) {
                s_gem_cliente_codigo_antPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_MigrarCliente.this, result.getString("message")+ ":Cliente Código antiguo").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_clientes_codigo_antBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }




}

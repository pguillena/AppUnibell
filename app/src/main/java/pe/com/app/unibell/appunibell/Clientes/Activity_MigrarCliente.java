package pe.com.app.unibell.appunibell.Clientes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.BL.ClientesBL;
import pe.com.app.unibell.appunibell.BL.DocuventBL;
import pe.com.app.unibell.appunibell.BL.FactCobBL;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.DAO.DocuventDAO;
import pe.com.app.unibell.appunibell.DAO.FactCobDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;



public class Activity_MigrarCliente extends AppCompatActivity
        implements Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private EditText plb_txtcodClienteMigrar;
    private Button plb_btnmigrar;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private Integer iAccion = 0;
    private ClientesDAO clientesDAO;
    private DocuventDAO docuventDAO;
    private FactCobDAO factCobDAO;

    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Progress clientesPG;
    private Dialog_Fragment_Progress factCobPG;
    private Dialog_Fragment_Progress docuventPG;

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
            clientesDAO.getByCodCliente(plb_txtcodClienteMigrar.getText().toString().toUpperCase().trim());

            if(clientesDAO.lst!=null && clientesDAO.lst.size()>0)
            {
                Mensaje("El cliente ya existe");
            }
            else
            {
              Migrar(plb_txtcodClienteMigrar.getText().toString().trim().toUpperCase().trim());
            }

        }
    }



    private void Migrar(String codCliente) {

        try{
            new Cliente_Sincronizar_AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blclientes_migrar_cliente + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + codCliente);
        } catch (Exception ex) {
            new ToastLibrary(this,"Error al sincronizar el cliente.").Show();
        }



        docuventDAO = new DocuventDAO();
        docuventDAO.getAll(codCliente);

        if(docuventDAO.lst==null || docuventDAO.lst.size()==0) {
            try {
                new DocuventBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocuventxcodigo + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0") + '/'
                                + sharedSettings.getString("iID_LOCAL", "0") + '/'
                                + codCliente);
            } catch (Exception ex) {
                new ToastLibrary(this, "Error al Sincronizar Docuvent.").Show();
            }
        }


        factCobDAO = new FactCobDAO();
        factCobDAO.getEstadoCuentaCliente(codCliente,"XXX","XXX","XXX","");

        if(factCobDAO.lst==null || factCobDAO.lst.size()==0) {

            try {
                new FactCobBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blfactcobxcodigo + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0") + '/'
                                + sharedSettings.getString("iID_LOCAL", "0") + '/'
                                + codCliente);
            } catch (Exception ex) {
                new ToastLibrary(this, "Error al Sincronizar Facturas por cobrar.").Show();
            }

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
            return new ClientesBL().getMigrarClienteCompleto(p[0]);
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
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class FactCobBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            factCobPG = new Dialog_Fragment_Progress();
            factCobPG.setMensaje("Sincronizando");
            factCobPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new FactCobBL().getSincronizarxCodigo(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (factCobPG != null && factCobPG.isVisible()) {
                factCobPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_MigrarCliente.this, result.getString("message")+ ":FactCob").Show();
                } else {

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.factCobBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class DocuventBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            docuventPG = new Dialog_Fragment_Progress();
            docuventPG.setMensaje("Sincronizando");
            docuventPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new DocuventBL().getSincronizarxCodigo(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (docuventPG != null && docuventPG.isVisible()) {
                docuventPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_MigrarCliente.this, result.getString("message")+ ":Docuvent").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.docuventBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }



}

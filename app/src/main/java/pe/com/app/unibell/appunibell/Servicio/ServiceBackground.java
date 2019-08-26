package pe.com.app.unibell.appunibell.Servicio;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import java.sql.SQLException;

import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.Cobranza.Fragment_Cobranza;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class ServiceBackground extends Service {
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private Funciones funciones=new Funciones();
    private String sfecha,shora;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Integer lhoraActual24;
    private Integer HORAINICIO,HORAFIN;
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        this.HORAINICIO=800;
        this.HORAFIN=2000;
        sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
            dataBaseHelper.openDataBase();
        } catch (SQLException e){
            e.printStackTrace();
        }
        Log.i(ConstantsLibrary.DEBUG_TAG, "SE CREO EL SERVICIO");
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
        Log.i("SERVICIO", "SE DESTRUYO");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //Se ejecuta solo si aun el servicio despues de ser llamado no se a completado
            if (!this.isRunning) {
                this.isRunning = true;
                sfecha = funciones.FechaActual();
                shora = funciones.HoraActual();
                lhoraActual24 = Integer.parseInt(funciones.HoraActual24().trim().replace(":", ""));
                //Toast.makeText(this.context, "EJECUTO EL SERVICIO", Toast.LENGTH_SHORT).show();
                if (lhoraActual24 > HORAINICIO && lhoraActual24 < HORAFIN) {
                    //Se ejecuta solo si tenemos plan de datos
                    if (funciones.isConnectingToInternet(getApplicationContext())) {
                        Toast toastCodigo = Toast.makeText(getApplicationContext(),"COBRANZA REGISTRADA ENVIADA AL ORACLE", Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        //new LoadGetGuardadaSQLite_AsyncTask().execute();

                        Log.i(ConstantsLibrary.DEBUG_TAG , "COBRANZA REGISTRADA ENVIADA AL ORACLE");
                        Log.e("FECHA", sfecha);
                        Log.e("HORA", shora);
                        //Toast.makeText(this.context, "GUARDO COORDENADAS", Toast.LENGTH_SHORT).show();
                    }
                }
                this.backgroundThread.start();
            }
        }catch(Exception e){
            this.backgroundThread.start();
        }
        return START_STICKY;
    }


    private class LoadGetGuardadaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getByGuardado();
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
               Integer ID_COBRANZA=0;
                Integer CODUNC_LOCAL=0;

                for (int c = 0; c < documentos_cobra_cabDAO.lst.size(); c++) {
                    ID_COBRANZA=documentos_cobra_cabDAO.lst.get(c).getID_COBRANZA();
                    CODUNC_LOCAL=documentos_cobra_cabDAO.lst.get(c).getCODUNC_LOCAL();
                    new InserCobranzaAsyncTask(
                             ID_COBRANZA.toString(),
                             CODUNC_LOCAL.toString()).execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Insert);

                }


            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }


    private class InserCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;
        private String ID_COBRANZA,CODUNC_LOCAL;

        public InserCobranzaAsyncTask(String ID_COBRANZA,String CODUNC_LOCAL) {
            this.ID_COBRANZA=ID_COBRANZA;
            this.CODUNC_LOCAL=CODUNC_LOCAL;
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.InsertRest(ID_COBRANZA,CODUNC_LOCAL,p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (result.getInt("status")==0) {
                } else {
                    //new ToastLibrary(getActivity(), result.getString("message")).Show();
                    if(documentos_cobra_cabBL.lst.size()>0){
                        /*
                        editor_Shared.putString("PECNROPED",pedido_cabBL.lstPedido.get(0).getPECNROPED().toString());
                        editor_Shared.putString("pREGISTRANDO","0");
                        editor_Shared.commit();
                        */
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {

            }
        }
    }




}



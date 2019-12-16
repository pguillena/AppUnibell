package pe.com.app.unibell.appunibell.Servicio;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.S_Vem_CorrelativoBL;
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
    private String currentVersion;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        this.HORAINICIO=600;
        this.HORAFIN=2350;
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

                        currentVersion = funciones.getVersionActual(getApplicationContext());
                        new LoadGetGuardadaSQLite_AsyncTask().execute();

                        Toast toastCodigo = Toast.makeText(getApplicationContext(),"COBRANZA REGISTRADA ENVIADA AL ORACLE", Toast.LENGTH_SHORT);
                        toastCodigo.show();


                        Log.i(ConstantsLibrary.DEBUG_TAG , "COBRANZA REGISTRADA ENVIADA AL ORACLE");
                        Log.e("FECHA", sfecha);
                        Log.e("HORA", shora);
                        //Toast.makeText(this.context, "GUARDO COORDENADAS", Toast.LENGTH_SHORT).show();
                    }


                    if (funciones.isConnectingToInternet(getApplicationContext())) {

                        //ANULAMOS
                        new AnularSQLite_AsyncTask().execute();

                        Toast toastCodigo = Toast.makeText(getApplicationContext(),"SE ACTUALIZARON LOS ANULADOS", Toast.LENGTH_SHORT);
                        toastCodigo.show();

                    }

                    if (funciones.isConnectingToInternet(getApplicationContext())) {

                        if(lhoraActual24>2000  && lhoraActual24<700) {

                            String online = sharedSettings.getString("VERSION_PLAYSTORE", "").toString()+funciones.FechaActual();
                            String local = currentVersion+funciones.FechaActual();

                            if (!(sharedSettings.getString("VERSION_PLAYSTORE", "").toString()).equals(currentVersion+funciones.FechaActual()))
                            {
                                new updateApplication().execute();
                            }

                        }

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
                documentos_cobra_cabDAO.getByGuardado("2");
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

/*
                        new s_vem_correlativoBL_Sincronizar().execute(
                                ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_vem_correlativo + '/'
                                        + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                        + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                        + sharedSettings.getString("iID_VENDEDOR", "0"));

*/




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

    public class s_vem_correlativoBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return new S_Vem_CorrelativoBL().getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {

        }
    }

    private class AnularSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getByGuardado("4"); //4 Pendiente de anular
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

                    String sURLCobranza_Cab = ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Anula;

                    new AnularAsyncTask(
                            documentos_cobra_cabDAO.lst.get(c).getID_COBRANZA().toString(),
                            documentos_cobra_cabDAO.lst.get(c).getCODUNC_LOCAL().toString()).execute(sURLCobranza_Cab);


                }


            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AnularAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;
        private String ID_COBRANZA,CODUNC_LOCAL;

        public AnularAsyncTask(String ID_COBRANZA,String CODUNC_LOCAL) {
            this.ID_COBRANZA=ID_COBRANZA;
            this.CODUNC_LOCAL=CODUNC_LOCAL;
        }

        @Override
        protected JSONObject doInBackground(String... p) {

            return documentos_cobra_cabBL.AnulaRest("null", ID_COBRANZA,CODUNC_LOCAL,p[0]);
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

    private  class updateApplication extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion = null;
            try {
                //Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()  + "&hl=en")
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
                        .timeout(30000)
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Log.d("updateAndroid", "Document: " + document);
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            Log.d("updateAndroid", "Current version: " + currentVersion + " PlayStore version: " + onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                editor_Shared.putString("VERSION_PLAYSTORE", onlineVersion+funciones.FechaActual());
                editor_Shared.commit();
                if(isUpdateRequired(currentVersion, onlineVersion)){
                    Log.d("updateAndroid", "Update is required!!! Current version: " + currentVersion + " PlayStore version: " + onlineVersion);
                    openAppRating(getApplicationContext()); //Open PlayStore
                }else{
                    Log.d("updateAndroid", "Update is NOT required!");
                }




            }

        }
/*
        private void openPlayStore(Context ctx){

            final String appPackageName = ctx.getPackageName();
            try {
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
*/

        public boolean isUpdateRequired(String versionActual, String versionNueva) {
            boolean result = false;
            int[] versiones = new int[8];
            int i = 0, anterior = 0, orden = 0;
            if(versionActual != null && versionNueva != null){
                try{
                    for(i = 0; i < 8; i++){
                        versiones[i] = 0;
                    }
                    i = 0;
                    do{
                        i = versionActual.indexOf('.', anterior);
                        if(i > 0){
                            versiones[orden] = Integer.parseInt(versionActual.substring(anterior, i));
                        }else{
                            versiones[orden] = Integer.parseInt(versionActual.substring(anterior));
                        }
                        anterior = i + 1;
                        orden++;
                    }while(i != -1);
                    anterior = 0;
                    orden = 4;
                    i = 0;
                    do{
                        i = versionNueva.indexOf('.', anterior);
                        if(i > 0){
                            versiones[orden] = Integer.parseInt(versionNueva.substring(anterior, i));
                        }else{
                            versiones[orden] = Integer.parseInt(versionNueva.substring(anterior));
                        }
                        anterior = i + 1;
                        orden++;
                    }while(i != -1 && orden < 8);
                    if(versiones[0] < versiones[4]){
                        result = true;
                    }else if(versiones[1] < versiones[5] && versiones[0] == versiones[4]){
                        result = true;
                    }else if(versiones[2] < versiones[6] && versiones[0] == versiones[4] && versiones[1] == versiones[5]){
                        result = true;
                    }else if(versiones[3] < versiones[7] && versiones[2] == versiones[6] && versiones[0] == versiones[4] && versiones[1] == versiones[5]){
                        result = true;
                    }
                }catch (NumberFormatException e){
                    Log.e("updateApp", "NFE " + e.getMessage() + " parsing versionAct " + versionActual + " and versionNew " + versionNueva);
                }catch (Exception e){
                    Log.e("updateApp", "Ex " + e.getMessage() + " parsing versionAct " + versionActual + " and versionNew " + versionNueva);
                }
            }
            return result;
        }


        public void openAppRating(Context context) {
            // you can also use BuildConfig.APPLICATION_ID
            String appId = context.getPackageName();
            Intent rateIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appId));
            boolean marketFound = false;

            // find all applications able to handle our rateIntent
            final List<ResolveInfo> otherApps = context.getPackageManager()
                    .queryIntentActivities(rateIntent, 0);
            for (ResolveInfo otherApp: otherApps) {
                // look for Google Play application
                if (otherApp.activityInfo.applicationInfo.packageName
                        .equals("com.android.vending")) {

                    ActivityInfo otherAppActivity = otherApp.activityInfo;
                    ComponentName componentName = new ComponentName(
                            otherAppActivity.applicationInfo.packageName,
                            otherAppActivity.name
                    );
                    // make sure it does NOT open in the stack of your activity
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // task reparenting if needed
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    // if the Google Play was already open in a search result
                    //  this make sure it still go to the app page you requested
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // this make sure only the Google Play app is allowed to
                    // intercept the intent
                    rateIntent.setComponent(componentName);
                    context.startActivity(rateIntent);
                    marketFound = true;
                    break;

                }
            }

            // if GP not present on device, open web browser
            if (!marketFound) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id="+appId));
                context.startActivity(webIntent);
            }
        }


    }




}



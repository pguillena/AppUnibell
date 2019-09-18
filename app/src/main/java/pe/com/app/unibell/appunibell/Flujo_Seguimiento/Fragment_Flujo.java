package pe.com.app.unibell.appunibell.Flujo_Seguimiento;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Cobranza_FlujoResumen_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.Planilla.Activity_AprobacionPlanilla;
import pe.com.app.unibell.appunibell.Planilla.Activity_FlujoSeguimiento;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Flujo extends Fragment {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private GridView fp_lvflujoresumen;
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Cobranza_FlujoResumen_Seguimiento_Adapter cobranza_flujo_seguimiento_adapter = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flujo, container, false);
        try {
            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();


            fp_lvflujoresumen=(GridView)view.findViewById(R.id.fp_lvflujoresumen);

            String myTag = getTag();
            ((Activity_Flujo_Seguimiento) getActivity()).setFragment_flujo(myTag);
            ((Activity_Flujo_Seguimiento) getActivity()).FragFlujo();


        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }finally {

        }
        return view;
    }

    public void CargarData(){

        new Load_FlujoAsyncTask().execute(
                ConstantsLibrary.RESTFUL_URL +
                        ConstantsLibrary.bldocumentos_cobra_mov_flujoresumen + "/"+
                        sharedSettings.getString("SERIE_PLANILLA", "0").toString()+ "/"+
                        sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                        sharedSettings.getString("iID_EMPRESA", "0")
        );


    }

    public class Load_FlujoAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getFlujoResumen(p[0]);
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
                    new ToastLibrary(getActivity(), result.getString("message")).Show();

                } else {
                    cobranza_flujo_seguimiento_adapter = new Cobranza_FlujoResumen_Seguimiento_Adapter(getActivity(), 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_seguimiento_adapter.notifyDataSetChanged();
                    fp_lvflujoresumen.setAdapter(cobranza_flujo_seguimiento_adapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }





}

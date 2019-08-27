package pe.com.app.unibell.appunibell.Flujo_Seguimiento;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Cobranza_Flujo3_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.Planilla.Activity_FlujoSeguimiento;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Seguimiento extends Fragment {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Cobranza_Flujo3_Seguimiento_Adapter cobranza_flujo_SeguimientoAdapter = null;

    private ListView fp_lvtres;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seguimiento, container, false);
        try {
            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            fp_lvtres=(ListView)view.findViewById(R.id.fp_lvtres);

            String myTag = getTag();
            ((Activity_Flujo_Seguimiento) getActivity()).setFragment_seguimiento(myTag);


        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }finally {

        }
        return view;
    }

    public void CargarData(){

        new Load_FlujoSeguimientoAsyncTask().execute(
                ConstantsLibrary.RESTFUL_URL +
                        ConstantsLibrary.bldocumentos_cobra_mov_flujo3 + "/"+
                        "0/10014/"+
                        sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                        sharedSettings.getString("SERIE_PLANILLA", "0").toString()+ "/"+
                        sharedSettings.getString("N_PLANILLA", "0").toString()+ "/"+
                        sharedSettings.getString("iID_EMPRESA", "0").toString()+ "/"+
                        sharedSettings.getString("iID_LOCAL", "0").toString()
        );

    }

    public class Load_FlujoSeguimientoAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getFlujo3(p[0]);
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
                    cobranza_flujo_SeguimientoAdapter = new Cobranza_Flujo3_Seguimiento_Adapter(getActivity(), 0, documentos_cobra_movBL.lst);
                    cobranza_flujo_SeguimientoAdapter.notifyDataSetChanged();
                    fp_lvtres.setAdapter(cobranza_flujo_SeguimientoAdapter);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }


}

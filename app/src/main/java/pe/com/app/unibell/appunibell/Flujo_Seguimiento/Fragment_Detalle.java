package pe.com.app.unibell.appunibell.Flujo_Seguimiento;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import pe.com.app.unibell.appunibell.AD.Cobranza_Flujo1_Seguimiento_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_Detalle extends Fragment {

    private ListView fp_lvuno;
    private TextView txtCobranzaTotal;
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
    private Cobranza_Flujo1_Seguimiento_Adapter cobranza_flujo_DetalleSeguimientoAdapter = null;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        try {
            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            fp_lvuno= (ListView) view.findViewById(R.id.fp_lvuno);
            txtCobranzaTotal=(TextView)view.findViewById(R.id.txtCobranzaTotal);

            String myTag = getTag();
            ((Activity_Flujo_Seguimiento) getActivity()).setFragment_detalle(myTag);



        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }finally {

        }
        return view;
    }

    public void CargarData(){
        try {
            String fInicio="17530101",fFin="17530101";

            new Load_FlujoDetalleAsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL +
                            ConstantsLibrary.bldocumentos_cobra_cab_flujo1 + "/"+
                            "0/0/0/0/0/XXX/"+
                            fInicio+ "/"+
                            fFin+ "/XXX/XXX/XXX/XXX/"+
                            sharedSettings.getString("iID_EMPRESA", "0").toString()+ "/"+
                            sharedSettings.getString("iID_LOCAL", "0").toString()+ "/"+
                            sharedSettings.getString("ROL", "0").toString()+ "/"+
                            sharedSettings.getString("N_PLANILLA", "0").toString()+ "/0"
            );


        } catch (Exception ex){

        }
    }

    @Override
    public void onStart() {
        ((Activity_Flujo_Seguimiento) getActivity()).FragDetalle();
        super.onStart();
    }

    public class Load_FlujoDetalleAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getFlujo1(p[0]);
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
                    cobranza_flujo_DetalleSeguimientoAdapter = new Cobranza_Flujo1_Seguimiento_Adapter(getActivity(), 0, documentos_cobra_cabBL.lst);
                    cobranza_flujo_DetalleSeguimientoAdapter.notifyDataSetChanged();
                    fp_lvuno.setAdapter(cobranza_flujo_DetalleSeguimientoAdapter);

                    double montoTotalPlanillaSoles = 0.0;
                    double montoTotalPlanillaDolares = 0.0;
                    for (int i = 0; i<documentos_cobra_cabBL.lst.size();i++)
                    {
                        montoTotalPlanillaSoles = montoTotalPlanillaSoles + documentos_cobra_cabBL.lst.get(i).getM_COBRANZA() ;

                    }

                    if(montoTotalPlanillaSoles>0) {
                        txtCobranzaTotal.setText("S/ " + String.valueOf(montoTotalPlanillaSoles));
                    }

                    if(montoTotalPlanillaDolares>0){


                        txtCobranzaTotal.setText("U$D " + String.valueOf(montoTotalPlanillaDolares));
                    }

                    if(montoTotalPlanillaSoles>0 && montoTotalPlanillaDolares>0)
                    {

                        txtCobranzaTotal.setText("U$D " + String.valueOf(montoTotalPlanillaDolares) + "   S/ " + String.valueOf(montoTotalPlanillaSoles));
                    }

                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }



}

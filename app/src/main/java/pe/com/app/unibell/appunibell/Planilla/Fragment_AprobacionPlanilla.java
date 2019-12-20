package pe.com.app.unibell.appunibell.Planilla;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import pe.com.app.unibell.appunibell.AD.Cobranza_Aprobacion_Planilla_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_AprobacionPlanilla extends Fragment
        implements
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener{

    private TextView ap_lbltotalg,btnVerDetalle;
    private FloatingActionButton ap_fabBuscar;
    private ListView ap_lsdetalle;

    private Integer iAuxiliar=0;
    private Integer iTabla=0;
    private Integer iInacializa=0;
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();

    private Cobranza_Aprobacion_Planilla_Adapter cobranza_aprobacion_planilla_adapter = null;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private DialogFragment dialogFragmentFecha;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    public Comunicator comunicator;
    private  Integer iEvento=0;
    private Dialog_Fragment_Aceptar log_dialogaceptar;
    Funciones funciones=new Funciones();

    private Double dMontoCheque=0.0;
    private Double dMontoVisa=0.0;
    private Double dMontoEfectivo=0.0;
    private Double dMontoBancarizado=0.0;
    private Double dMontoDeposito=0.0;
    private Double dTotalGeneral=0.0;
    private String fPago="";

    private TextView txtProgressLoadingAprobacion;
    private ProgressBar pbAprobacion;



    @Override
    public void onAceptar() {}

    public interface Comunicator{
        public  void Finalizar();
        public  void FechaInicio();
        public  void FechaFin();
        public  void Filtros();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comunicator=(Comunicator)activity;
    }
    public Fragment_AprobacionPlanilla() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cobranza_aprobacion_planilla, container, false);
        try {
            sharedSettings = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getContext().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            String myTag = getTag();
            ((Activity_AprobacionPlanilla) getActivity()).setiFragment_AprobacionPlanilla(myTag);

            ap_lsdetalle = (ListView) view.findViewById(R.id.ap_lsdetalle);
            ap_lbltotalg = (TextView) view.findViewById(R.id.ap_lbltotalg);
            btnVerDetalle = (TextView) view.findViewById(R.id.btnVerDetalle);

            txtProgressLoadingAprobacion=(TextView)view.findViewById(R.id.txtProgressLoadingAprobacion);
            pbAprobacion = (ProgressBar)view.findViewById(R.id.pbAprobacion);

            FloatingActionButton fabBuscar = (FloatingActionButton) view.findViewById(R.id.ap_fabBuscar);
            fabBuscar.setOnClickListener(OnClickListenercl_ap_fabBuscar);

            btnVerDetalle.setOnClickListener(OnClickListenercl_btnVerDetalle);

            //Intent data = new Intent();
            //BuscarPlanilla(data);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }finally {
            iInacializa=1;
        }
        return view;
    }

    public void BuscarPlanilla(Intent data){
        try{

            String cobranza="0", serie="0",recibo="0",fpago="0",codigoCliente="XXX",nomCliente="XXX",ruc="XXX",dni="XXX",noperacion="XXX",estado="40004",planilla="0", pl_cobrador="0";
            String sFechaInicio="17530101",sFechaFin="17530101";

            String iID_VENDEDOR= sharedSettings.getString("iID_VENDEDOR", "0").toString();
            Bundle parametros = data.getExtras();

            planilla= sharedSettings.getString("SERIE_PLANILLA_FILTRO", "0").toString();

            if(planilla.equals(""))
            {
                planilla="0";
            }

            if(parametros !=null && iInacializa==1 ){
                serie=parametros.getString("pl_serie","0").toString().trim();
                recibo=parametros.getString("pl_numero","0").toString().trim();;
                codigoCliente=parametros.getString("pl_codigo","XXX").toString().trim();
                nomCliente=parametros.getString("pl_cliente","XXX").toString().trim();
                ruc=parametros.getString("pl_ruc","XXX").toString().trim();
                dni=parametros.getString("pl_dni","XXX").toString().trim();
                noperacion=parametros.getString("pl_noperacion","XXX").toString().trim();
                //planilla=parametros.getString("pl_planilla","0").toString().trim();
                sFechaInicio=parametros.getString("pl_inicio","17530101").toString().trim();
                sFechaFin=parametros.getString("pl_fin","17530101").toString().trim();
                fpago=parametros.getString("pl_fpago","0").toString().trim();
                estado=parametros.getString("pl_estado","0").toString().trim();
                pl_cobrador=parametros.getString("pl_cobrador","0").toString().trim();


            }

         new Load_AsyncTask().execute(
                 ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_mov_APlanilla + "/" +
                 cobranza + "/" +
                 recibo + "/" +
                 serie + "/" +
                 fpago  + "/" +
                 pl_cobrador + "/" +
                 codigoCliente+ "/" +
                 sFechaInicio+ "/" +
                 sFechaFin + "/" +
                 nomCliente+ "/" +
                 ruc+ "/" +
                 dni+ "/" +
                 noperacion+ "/" +
                 sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                 sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                 sharedSettings.getString("ROL", "0").toString() + "/" +
                 planilla+ "/" +
                 estado + "/" +
                 "30" +
                 "/0"
         );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Filtro(String newText){
        if (TextUtils.isEmpty(newText)) {
            Cobranza_Aprobacion_Planilla_Adapter ca = (Cobranza_Aprobacion_Planilla_Adapter) ap_lsdetalle.getAdapter();
                if (ca != null) {
                    ca.getFilter().filter(newText);
                    ca.notifyDataSetChanged();
                }
            }else{
            Cobranza_Aprobacion_Planilla_Adapter ca = (Cobranza_Aprobacion_Planilla_Adapter) ap_lsdetalle.getAdapter();
                if (ca != null) {
                    ca.getFilter().filter(newText);
                    ca.notifyDataSetChanged();
                }
            }
    }

    private void VerDetalleTotales() {
        Intent intent = new Intent(getActivity(), Activity_Aprobacion_Totales.class);
        intent.putExtra("dMontoCheque", dMontoCheque.toString());
        intent.putExtra("dMontoVisa", dMontoVisa.toString());
        intent.putExtra("dMontoEfectivo", dMontoEfectivo.toString());
        intent.putExtra("dMontoBancarizado", dMontoBancarizado.toString());
        intent.putExtra("dMontoDeposito", dMontoDeposito.toString());
        intent.putExtra("dTotalGeneral", dTotalGeneral.toString());
        startActivity(intent);
    }


    AbsListView.OnItemClickListener ap_lsdetalle_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };


    View.OnClickListener OnClickListenercl_ap_fabBuscar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent data = new Intent();
               comunicator.Filtros();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btnVerDetalle = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                VerDetalleTotales();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };




    public void AprobarPlanilla(){
        iEvento=1;
        String sMensaje = "¿Desea aprobar la planilla?";
        dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
        dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_AprobacionPlanilla.this, sMensaje);
        dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
        dialog_fragment_confirmar.isCancelable();
    }

    public void RetornarPlanilla(){
        iEvento=2;
        String sMensaje = "¿Desea retornar la planilla?";
        dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
        dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_AprobacionPlanilla.this, sMensaje);
        dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
        dialog_fragment_confirmar.isCancelable();
    }

    @Override
    public void onConfirmacionSI() {
        if(iEvento==1){


            String sFecha=    funciones.FechaActual().substring(6)
                            + funciones.FechaActual().substring(3, 5)
                            + funciones.FechaActual().substring(0, 2);


            new PutInsertPlanilla_AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_InsertarPlanilla + "/" +
                            sharedSettings.getString("USUARIO", "").toString()+ "/" +
                            sharedSettings.getString("C_PERFIL", "").toString()+ "/" +
                            sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                            sharedSettings.getString("C_PACKING", "0") + "/"+
                            sharedSettings.getString("SERIE_PLANILLA", "0").toString() + "/" +
                            sharedSettings.getString("N_PLANILLA", "0").toString() + "/" +
                            sharedSettings.getString("ID_DOCUMENTO_MOVIMIENTO", "0").toString() + "/" +
                            sharedSettings.getString("iID_VENDEDOR", "0").toString() + "/" +
                            sFecha + "/" +
                            "40005" + "/" +
                            sharedSettings.getString("NOMBRE_TELEFONO", "").toString()+ "/" +
                            sharedSettings.getString("sIMEI", "").toString() + "/" +
                            sharedSettings.getString("ID_COBRANZA", "0").toString()
            );
        }
        if(iEvento==2){
            new PutRetornarPlanilla_AsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_RetornarPlanilla + "/" +
                            sharedSettings.getString("SERIE_PLANILLA", "0").toString() + "/" +
                            sharedSettings.getString("N_PLANILLA", "0").toString() + "/" +
                            "40003" + "/" +
                            sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                            sharedSettings.getString("ID_DOCUMENTO_MOVIMIENTO", "0").toString() + "/" +
                            sharedSettings.getString("USUARIO","").toString()+ "/" +
                            sharedSettings.getString("NOMBRE_TELEFONO", "").toString()+ "/" +
                            sharedSettings.getString("sIMEI", "").toString()
            );
        }

    }

    @Override
    public void onConfirmacionNO() {

    }


    public class Load_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {

            txtProgressLoadingAprobacion.setText("Buscando");
            txtProgressLoadingAprobacion.setVisibility(View.VISIBLE);
            pbAprobacion.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getAPlanillaRest(p[0]);
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
                    cobranza_aprobacion_planilla_adapter = new Cobranza_Aprobacion_Planilla_Adapter(getContext(), 0, documentos_cobra_movBL.lst);
                    cobranza_aprobacion_planilla_adapter.notifyDataSetChanged();
                    ap_lsdetalle.setAdapter(cobranza_aprobacion_planilla_adapter);

                    dMontoCheque=0.0;
                    dMontoVisa=0.0;
                    dMontoEfectivo=0.0;
                    dMontoBancarizado=0.0;
                    dMontoDeposito=0.0;
                    dTotalGeneral=0.0;

                    for (int j = 0; j < cobranza_aprobacion_planilla_adapter.lstFiltrado.size(); j++) {
                        fPago = cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getFPAGO().toString().trim();
                        //CHEQUE
                        if (fPago.equals("C")) {
                            dMontoCheque += Double.valueOf(cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                        }
                        //VISA
                        if (fPago.equals("D") || fPago.equals("V") || fPago.equals("M") || fPago.equals("S") || fPago.equals("I")|| fPago.equals("H")) {
                            dMontoVisa += Double.valueOf(cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                        }
                        //EFECTIVO
                        if (fPago.equals("E")) {
                            dMontoEfectivo += Double.valueOf(cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                        }
                        //BANCARIZADO
                        if (fPago.equals("Z")) {
                            dMontoBancarizado += Double.valueOf(cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                        }
                        //Deposito
                        if (fPago.equals("P")) {
                            dMontoDeposito += Double.valueOf(cobranza_aprobacion_planilla_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                        }
                    }
                    dTotalGeneral=dMontoCheque+dMontoVisa+dMontoEfectivo+dMontoBancarizado+dMontoDeposito;
                    ap_lbltotalg.setText(funciones.FormatSoles(dTotalGeneral.toString()));

                }
            }catch(Exception ex){
                ex.printStackTrace();
            }


            txtProgressLoadingAprobacion.setVisibility(View.GONE);
            pbAprobacion.setVisibility(View.GONE);
        }
    }

    public class PutInsertPlanilla_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            txtProgressLoadingAprobacion.setText("Aprobando");
            txtProgressLoadingAprobacion.setVisibility(View.VISIBLE);
            pbAprobacion.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.PutInsertarPlanilla(p[0]);
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
                    //cyper100
                    Mensaje(documentos_cobra_cabBL.lst.get(0).getMSG().toString());
                    Intent data = new Intent();
                    BuscarPlanilla(data);


                }
            }catch(Exception ex){
                ex.printStackTrace();
            }


            txtProgressLoadingAprobacion.setVisibility(View.GONE);
            pbAprobacion.setVisibility(View.GONE);
        }
    }

    public class PutRetornarPlanilla_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.PutRetornarPlanilla(p[0]);
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
                    Mensaje(documentos_cobra_cabBL.lst.get(0).getMSG().toString());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void Mensaje(String msj){
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Fragment_AprobacionPlanilla.this);
        log_dialogaceptar.show(getFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

}

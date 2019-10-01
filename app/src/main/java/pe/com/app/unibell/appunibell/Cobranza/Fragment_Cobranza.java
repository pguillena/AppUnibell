package pe.com.app.unibell.appunibell.Cobranza;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Cobranza_Cabecera_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Detalle_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Detalle_Adapter_Edit;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_DetBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_DetDAO;
import pe.com.app.unibell.appunibell.DAO.FactCobDAO;
import pe.com.app.unibell.appunibell.DAO.Recibos_CcobranzaDAO;
import pe.com.app.unibell.appunibell.DAO.S_gem_TipoCambioDAO;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Amortizar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Recibo_Rep;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.Globals;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Fragment_Cobranza extends Fragment implements
        Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Amortizar.Dialog_Fragment_AmortizarListener{

    private TextView co_btnguardar,co_lblcliente;
    private ListView co_lsdet;
    private GridView co_lscab;
    private TextView co_btnagregarpago,co_btnanticipo;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
    private Documentos_Cobra_DetBE documentos_cobra_detBE = null;
    private FactCobBE factCobBE = null;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;

    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Amortizar dialog_fragment_amortizar = null;

    private Integer iAnticipo = 0;

    private Funciones funciones = new Funciones();
    private Cobranza_Cabecera_Adapter cobranza_cabecera_adapter = null;
    private Cobranza_Detalle_Adapter cobranza_detalle_adapter = null;
    private Cobranza_Detalle_Adapter_Edit cobranza_detalle_adapter_edit = null;

    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();
    private Documentos_Cobra_DetDAO documentos_cobra_detDAO = new Documentos_Cobra_DetDAO();
    private Recibos_CcobranzaDAO recibos_ccobranzaDAO = new Recibos_CcobranzaDAO();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
    private Documentos_Cobra_DetBL documentos_cobra_detBL = new Documentos_Cobra_DetBL();
    private S_gem_TipoCambioDAO s_gem_tipoCambioDAO = new S_gem_TipoCambioDAO();

    private FactCobDAO factCobDAO = new FactCobDAO();
    private SistemaDAO sistemaDAO = new SistemaDAO();

    public Comunicator comunicator;
    private Integer MAX_CODUNICO = 0;
    private Integer iPocicionCab = 0;
    private Integer iAccion = 0;
    private Dialog_Fragment_Progress documentos_cobra_cabPG;
    private Dialog_Fragment_Progress documentos_cobra_detPG;
    private String sFORMA_PAGO = "";

    private String cpserie ="";
    private String cpnumero="";
    private String cpfplanilla="";
    private String cpfpago ="";
    private String cpbancoctacte = "";
    private String cpmonto = "";
    private String cptipocambio = "";
    private String cpmoneda = "";

    private FactCobBE factCobBE2=null;
    private Documentos_Cobra_DetBE documentos_cobra_detBE2 = null;
    private Integer iDialog=0,iOpcionDialog=0;

    @Override
    public void onAmortizarSI(String Precio) {
        if(iDialog==1){
            factCobBE2.setVAMORTIZADO(Double.valueOf(Precio));
            GuadarDetalle(factCobBE2);
        }
        if(iDialog==2){
            documentos_cobra_detBE2.setM_COBRANZA(Double.valueOf(Precio));
            DetalleCobranzaEdit(documentos_cobra_detBE2,iOpcionDialog);
        }
    }

    public void AmortizarDialogTemp(FactCobBE factCobBE3){
        iDialog=1;
        factCobBE2=factCobBE3;
        dialog_fragment_amortizar = new Dialog_Fragment_Amortizar();
        dialog_fragment_amortizar.setPrecioDialogfragmentListener(Fragment_Cobranza.this);
        dialog_fragment_amortizar.show(getFragmentManager(), dialog_fragment_amortizar.TAG);
        dialog_fragment_amortizar.isCancelable();
    }

    public void AmortizarDialogDet(Documentos_Cobra_DetBE documentos_cobra_detBE3,Integer iOpcion){
        iDialog=2;
        iOpcionDialog=iOpcion;
        documentos_cobra_detBE2=documentos_cobra_detBE3;
        dialog_fragment_amortizar = new Dialog_Fragment_Amortizar();
        dialog_fragment_amortizar.setPrecioDialogfragmentListener(Fragment_Cobranza.this);
        dialog_fragment_amortizar.show(getFragmentManager(), dialog_fragment_amortizar.TAG);
        dialog_fragment_amortizar.isCancelable();
    }


    public interface Comunicator {
        public void Finalizar();
        public void OcultaTeclado();
        public void FechaPlanilla();
        public void FechaDeposito();
        public void AgregarPago();
        public void CobranzaEditar();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        comunicator = (Comunicator) activity;
    }

    public Fragment_Cobranza() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cobranza, container, false);
        try {
            String myTag = getTag();
            ((Activity_Cobranzas) getActivity()).setiFragmentCobranzas(myTag);

            sharedSettings = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE);
            editor_Shared = getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE).edit();

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            co_lblcliente = (TextView) view.findViewById(R.id.co_lblcliente);
            //co_lblfecha = (TextView) view.findViewById(R.id.co_lblfecha);
            co_btnagregarpago = (TextView) view.findViewById(R.id.co_btnagregarpago);
            co_btnanticipo = (TextView) view.findViewById(R.id.co_btnanticipo);


            co_lblcliente.setText(new Funciones().LetraCapital(sharedSettings.getString("RAZON_SOCIAL", "").toString()));
            co_lblcliente.setTag(sharedSettings.getString("CODIGO_ANTIGUO", "").toString());

            co_btnguardar = (TextView) view.findViewById(R.id.co_btnguardar);

            co_lscab = (GridView) view.findViewById(R.id.co_lscab);
            co_lsdet = (ListView) view.findViewById(R.id.co_lsdet);

            //EVENTOS
            co_btnanticipo.setOnClickListener(OnClickList_co_lblanticipo);
            co_btnguardar.setOnClickListener(OnClickList_co_btnguardar);
            co_btnagregarpago.setOnClickListener(OnClickList_co_btnagregarpago);
            co_lscab.setOnItemClickListener(lv_EditarOnItemClickListener);
            //FIN EVENTOS

            //ELIMINAMOS LAS COBRANZAS TERMPORALES
            documentos_cobra_cabDAO.deleteTemp();

            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                this.CargarCabecera();
                this.CargarDetalleEdit();
                this.co_btnagregarpago.setEnabled(false);
                this.co_btnanticipo.setEnabled(false);
            } else {
                MAX_CODUNICO = sistemaDAO.MAX_REGISTRO("S_CCM_DOCUMENTOS_COBRA_CAB", "ID_CORREL2");
                editor_Shared.putString("MAX_CODUNICO", MAX_CODUNICO.toString());
                editor_Shared.commit();

                Globals g = (Globals)getActivity().getApplication();
                g.setIntentCobranzaCab(cobranza_cabecera_adapter);

                comunicator.AgregarPago();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private Boolean ValidarGuardar() {
        if (cobranza_cabecera_adapter == null) {
            Mensaje("Cabecera de cobranza no es válido");
            return false;
        }
        if (cobranza_cabecera_adapter.getCount() == 0) {
            Mensaje("Cabecera de cobranza no es válido");
            return false;
        }
        Integer ic = 0;
        for (int c = 0; c < cobranza_cabecera_adapter.getCount(); c++) {
            ic += 1;
            if (Double.valueOf(cobranza_cabecera_adapter.getItem(c).getSALDO().toString()) > 0) {
                Mensaje("Aun existe saldo en la cobranza.");
                return false;
            }
        }
        if (cobranza_detalle_adapter == null) {
            Mensaje("La cobranza no tiene detalle");
            return false;
        }
        if (cobranza_detalle_adapter.getCount() == 0) {
            Mensaje("La cobranza no tiene detalle");
            return false;
        }
        return true;
    }

    public Integer ValidarGuardarDetalle() {
        Integer iValor = 0;
        if (cobranza_cabecera_adapter == null) {
            new ToastLibrary(getActivity(), "Agregar cabecera de cobranza").Show();
            return iValor;
        }
        if (cobranza_cabecera_adapter.getCount() == 0) {
            new ToastLibrary(getActivity(), "Agregar cabecera de cobranza").Show();
            return iValor;
        }
        iValor = 1;
        return iValor;
    }

    private void Mensaje(String msj) {
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Fragment_Cobranza.this);
        log_dialogaceptar.show(getFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

    //Fin Validaciones

    //Eventos
    AbsListView.OnItemClickListener lv_EditarOnItemClickListener = new AbsListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                iPocicionCab = position;
                String EDITAR_TPAGO="";
                editor_Shared.putString("ID_COBRANZA", cobranza_cabecera_adapter.getItem(iPocicionCab ).getID_COBRANZA().toString());
                editor_Shared.putString("SALDO_ANTICIPO", cobranza_cabecera_adapter.getItem(iPocicionCab).getSALDO().toString());
                editor_Shared.putString("COD_FPAGO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGO().toString());
                editor_Shared.putString("sFPAGODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGODESC().toString());

                editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getID_BANCO().toString());
                editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getBANCODESC().toString());
                editor_Shared.putString("sN_TARJETA", cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
                editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());

                 String fPago = cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGO().toString();

                //TARJETAS DE CREDITO
                if (fPago.equals("D") || fPago.equals("V") || fPago.equals("M") || fPago.equals("S") || fPago.equals("I")|| fPago.equals("H")) {
                    //co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
                    EDITAR_TPAGO="TARJETA";
                    editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getCTACORRIENTE_BANCO().toString());
                    editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getNOMCTACORRIENTE().toString());
                    editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
                    editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());
                }

                //DEPOSITO EN BANCO
                if (fPago.equals("P")) {
                    //co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getNRO_OPERACION().toString());
                    EDITAR_TPAGO="DEPOSITO";
                    editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getCTACORRIENTE_BANCO().toString());
                    editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getNOMCTACORRIENTE().toString());
                    editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getNRO_OPERACION().toString());
                    editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());
                }

                //CHEQUE
                if (fPago.equals("C")) {
                    // co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getNUMCHEQ().toString());
                    EDITAR_TPAGO="CHEQUE";
                    editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getID_BANCO().toString());
                    editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getBANCODESC().toString());
                    editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getNUMCHEQ().toString());
                    editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECCHEQ().toString());
                }


                editor_Shared.putString("SALDO_CABECERA",cobranza_cabecera_adapter.getItem(iPocicionCab).getSALDO().toString());
                editor_Shared.putString("EDITAR_TPAGO",EDITAR_TPAGO.toString().trim());
                editor_Shared.commit();

                cobranza_cabecera_adapter.notifyDataSetChanged();
                co_lscab.setAdapter(cobranza_cabecera_adapter);

                if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                    CargarDetalleEdit();
                } else {
                    CargarTemporal();
                }

            } catch (Exception ex) {
                Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void CobrazaEditar(Integer iPocicionCab){
        try {
            String EDITAR_TPAGO="";
            editor_Shared.putString("ID_COBRANZA", cobranza_cabecera_adapter.getItem(iPocicionCab ).getID_COBRANZA().toString());
            editor_Shared.putString("SALDO_ANTICIPO", cobranza_cabecera_adapter.getItem(iPocicionCab).getSALDO().toString());
            editor_Shared.putString("COD_FPAGO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGO().toString());
            editor_Shared.putString("sFPAGODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGODESC().toString());

            editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getID_BANCO().toString());
            editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getBANCODESC().toString());
            editor_Shared.putString("sN_TARJETA", cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
            editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());

            String fPago = cobranza_cabecera_adapter.getItem(iPocicionCab).getFPAGO().toString();

            //TARJETAS DE CREDITO
            if (fPago.equals("D") || fPago.equals("V") || fPago.equals("M") || fPago.equals("S") || fPago.equals("I")|| fPago.equals("H")) {
                //co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
                EDITAR_TPAGO="TARJETA";
                editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getCTACORRIENTE_BANCO().toString());
                editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getNOMCTACORRIENTE().toString());
                editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getN_TARJETA().toString());
                editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());
            }

            //DEPOSITO EN BANCO
            if (fPago.equals("P")) {
                //co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getNRO_OPERACION().toString());
                EDITAR_TPAGO="DEPOSITO";
                editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getCTACORRIENTE_BANCO().toString());
                editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getNOMCTACORRIENTE().toString());
                editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getNRO_OPERACION().toString());
                editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECHA_DEPOSITO().toString());
            }

            //CHEQUE
            if (fPago.equals("C")) {
                // co_txtnro.setText(cobranza_cabecera_adapter.getItem(iPocicionCab).getNUMCHEQ().toString());
                EDITAR_TPAGO="CHEQUE";
                editor_Shared.putString("iID_BANCO", cobranza_cabecera_adapter.getItem(iPocicionCab).getID_BANCO().toString());
                editor_Shared.putString("sBANCODESC", cobranza_cabecera_adapter.getItem(iPocicionCab).getBANCODESC().toString());
                editor_Shared.putString("sNNUMERO", cobranza_cabecera_adapter.getItem(iPocicionCab).getNUMCHEQ().toString());
                editor_Shared.putString("sFECHA_DEPOSITO", cobranza_cabecera_adapter.getItem(iPocicionCab).getFECCHEQ().toString());
            }

            editor_Shared.putString("EDITAR_TPAGO",EDITAR_TPAGO.toString().trim());
            editor_Shared.commit();

            cobranza_cabecera_adapter.notifyDataSetChanged();
            co_lscab.setAdapter(cobranza_cabecera_adapter);

            /*
            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                CargarDetalleEdit();
            } else {
                CargarTemporal();
            }
            */
            //Editamos le acobranza
            if(EDITAR_TPAGO.equals("TARJETA") || EDITAR_TPAGO.equals("DEPOSITO") || EDITAR_TPAGO.equals("CHEQUE")){
                comunicator.CobranzaEditar();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    View.OnClickListener OnClickList_co_btnagregarpago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

                editor_Shared.putString("PAE", "0");
                editor_Shared.commit();

              Globals g = (Globals)getActivity().getApplication();
              g.setIntentCobranzaCab(cobranza_cabecera_adapter);

            comunicator.AgregarPago();
            } catch (Exception e) {
            }
        }
    };


    View.OnClickListener OnClickList_co_lblanticipo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Integer iActicipo=0;

                for (int j = 0; j < factCobDAO.lst.size(); j++) {
                    if(factCobDAO.lst.get(j).getTIPDOC().toString().trim().equals("A1")){
                        iActicipo+=1;
                    }
                }
                if(iActicipo>=1){
                    Toast.makeText(getActivity(),"Ya Existe un aticipo ingresado para la cobranza.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cobranza_cabecera_adapter != null) {
                    factCobBE = new FactCobBE();
                    factCobBE.setID_COBRANZA(cobranza_cabecera_adapter.lst.get(iPocicionCab).getID_COBRANZA());
                    factCobBE.setCOD_CLIENTE(cobranza_cabecera_adapter.lst.get(iPocicionCab).getCOD_CLIENTE());
                    factCobBE.setTIPDOC("A1");
                    factCobBE.setSERIE_NUM("1");
                    factCobBE.setNUMERO(1);
                    factCobBE.setFECHA(funciones.FechaActual());
                    factCobBE.setF_VENCTO(funciones.FechaActual());
                    factCobBE.setF_ACEPTACION(funciones.FechaActual());
                    factCobBE.setF_TRANSFE(funciones.FechaActual());
                    factCobBE.setANO(Integer.valueOf(funciones.Year(funciones.FechaActual())));
                    factCobBE.setMES(Integer.valueOf(funciones.Month(funciones.FechaActual())));
                    factCobBE.setLIBRO("");
                    factCobBE.setVOUCHER("");
                    factCobBE.setITEM(0);
                    factCobBE.setTIPO_REFERENCIA("");
                    factCobBE.setSERIE_REF("");
                    factCobBE.setNRO_REFERENCIA("");
                    factCobBE.setCONCEPTO("");
                    factCobBE.setSISTEMA_ORIGEN("");
                    factCobBE.setVENDED(sharedSettings.getString("iID_VENDEDOR", "0").toString());
                    factCobBE.setBANCO("");
                    factCobBE.setL_AGENCIA("");
                    factCobBE.setL_REFBCO("");
                    factCobBE.setL_CONDLE("");
                    factCobBE.setMONEDA("");
                    factCobBE.setIMPORTE(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    factCobBE.setTCAM_IMP(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    factCobBE.setSALDO(0.0);
                    factCobBE.setCOBRANZA(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    factCobBE.setTCAM_SAL(0);
                    factCobBE.setNUMERO_CANJE("");
                    factCobBE.setESTADO("40003");
                    factCobBE.setCTACTBLE("");
                    factCobBE.setF_RECEPCION("");
                    factCobBE.setC_USUARIO(sharedSettings.getString("USUARIO", "").toString());
                    factCobBE.setC_PERFIL("");
                    factCobBE.setC_CPU(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
                    factCobBE.setFEC_REG(funciones.FechaActual());
                    factCobBE.setC_USUARIO_MOD(sharedSettings.getString("sIMEI", "").toString());
                    factCobBE.setC_PERFIL_MOD("");
                    factCobBE.setFEC_MOD(funciones.FechaActual());
                    factCobBE.setC_CPU_MOD(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
                    factCobBE.setN_SERIE_RECIBO_COBRA("");
                    factCobBE.setN_RECIBO_COBRA(0);
                    factCobBE.setANO_PROVISION(0);
                    factCobBE.setMES_CSTGO(0);
                    factCobBE.setANO_CSTGO(0);
                    factCobBE.setLIBRO_CSTGO("");
                    factCobBE.setVOUCHER_CSTGO(0);
                    factCobBE.setDIAS(-99);
                    factCobBE.setCODUNC_LOCAL(Integer.valueOf(sharedSettings.getString("MAX_CODUNICO", "0").toString()));
                    new Inser_TemporalAsyncTask(factCobBE).execute();

                    documentos_cobra_detBE = new Documentos_Cobra_DetBE();
                    //Id de cobranza cabbecera
                    documentos_cobra_detBE.setID_COBRANZA(cobranza_cabecera_adapter.lst.get(iPocicionCab).getID_COBRANZA());
                    documentos_cobra_detBE.setFPAGO(cobranza_cabecera_adapter.lst.get(iPocicionCab).getFPAGO());
                    //Datos del detalle de la consulta faccob
                    documentos_cobra_detBE.setTIPDOC("A1");
                    documentos_cobra_detBE.setSERIE_NUM("1");
                    documentos_cobra_detBE.setNUMERO("1");
                    documentos_cobra_detBE.setIMPORTE(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    documentos_cobra_detBE.setMONEDA("S");
                    //Saldo que se descuenta mientras se va descontando
                    documentos_cobra_detBE.setSALDO(0.0);
                    //Monto que se esta amortizando(Al aplicar se copia al campo de cobranazas y se quita el boton)
                    documentos_cobra_detBE.setM_COBRANZA(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    documentos_cobra_detBE.setID_EMPRESA(Integer.valueOf(sharedSettings.getString("iID_EMPRESA", "").toString()));
                    documentos_cobra_detBE.setID_LOCAL(Integer.valueOf(sharedSettings.getString("iID_LOCAL", "").toString()));
                    //Nace con estado registrado
                    documentos_cobra_detBE.setESTADO(40003);
                    documentos_cobra_detBE.setFECHA_REGISTRO(funciones.FechaActual());
                    documentos_cobra_detBE.setFECHA_MODIFICACION(funciones.FechaActual());
                    documentos_cobra_detBE.setUSUARIO_REGISTRO(sharedSettings.getString("USUARIO", "").toString());
                    documentos_cobra_detBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
                    documentos_cobra_detBE.setPC_REGISTRO(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
                    documentos_cobra_detBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
                    documentos_cobra_detBE.setIP_REGISTRO(sharedSettings.getString("sIMEI", "").toString());
                    documentos_cobra_detBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
                    //Campo VenDed del Factcob
                    documentos_cobra_detBE.setID_VENDEDOR(Integer.valueOf(sharedSettings.getString("iID_VENDEDOR", "0").toString()));
                    //Igual al Saldo del Factcob
                    documentos_cobra_detBE.setSALDO_INICIAL(cobranza_cabecera_adapter.lst.get(iPocicionCab).getSALDO());
                    documentos_cobra_detBE.setCODUNC_LOCAL(Integer.valueOf(sharedSettings.getString("MAX_CODUNICO", "0").toString()));
                    documentos_cobra_detBE.setGUARDADO(0);
                    documentos_cobra_detBE.setSINCRONIZADO(0);
                    documentos_cobra_detBE.setDIAS(-99);
                    new Inser_DetalleAsyncTask(documentos_cobra_detBE).execute();
                }
            } catch (Exception e) {
            }
        }
    };

    public void GuardarCobranzas(){
        try {
            //VALIDAMOS SI GUARDO VALORES
            String sFPago = "";
            Integer vID_Cobranza = 0;
            for (int j = 0; j < documentos_cobra_cabDAO.lst.size(); j++) {
                Documentos_Cobra_CabBE documentos_cobra_cabBE2 = new Documentos_Cobra_CabBE();
                documentos_cobra_cabBE2.setGUARDADO(2);
                documentos_cobra_cabBE2.setID_COBRANZA(cobranza_cabecera_adapter.lst.get(j).getID_COBRANZA());
                documentos_cobra_cabBE2.setN_RECIBO(cobranza_cabecera_adapter.lst.get(j).getN_RECIBO());
                documentos_cobra_cabBE2.setID_COBRADOR(cobranza_cabecera_adapter.lst.get(j).getID_COBRADOR());
                documentos_cobra_cabBE2.setN_SERIE_RECIBO(cobranza_cabecera_adapter.lst.get(j).getN_SERIE_RECIBO());
                documentos_cobra_cabDAO.updateEstado(documentos_cobra_cabBE2);

                vID_Cobranza = cobranza_cabecera_adapter.lst.get(j).getID_COBRANZA();
                sFPago = cobranza_cabecera_adapter.lst.get(j).getFPAGO().trim();

                if (sFPago.equals("D") || sFPago.equals("V") || sFPago.equals("M") || sFPago.equals("S") || sFPago.equals("I")|| sFPago.equals("H")) {
                    if (cobranza_cabecera_adapter.lst.get(j).getFECHA_DEPOSITO().trim().equals("") || cobranza_cabecera_adapter.lst.get(j).getN_TARJETA().trim().equals("")) {
                        Mensaje("Ingrese Tarjeta de credito y fecha de deposito.");
                        return;
                    }
                }
                //DEPOSITO EN BANCO
                if (sFPago.equals("P")) {
                    if (cobranza_cabecera_adapter.lst.get(j).getFECHA_DEPOSITO().trim().equals("") || cobranza_cabecera_adapter.lst.get(j).getNRO_OPERACION().trim().equals("")) {
                        Mensaje("Ingrese Número de operación y fecha de deposito.");
                        return;
                    }
                }
                //CHEQUE
                if (sFPago.equals("C")) {
                    if (cobranza_cabecera_adapter.lst.get(j).getFECCHEQ().trim().equals("") || cobranza_cabecera_adapter.lst.get(j).getNUMCHEQ().trim().equals("")) {
                        Mensaje("Ingrese Número de cheque y fecha.");
                        return;
                    }
                }
            }

            //NUEVO REGISTRO
            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("0")) {
                if (ValidarGuardar() == false) {
                    return;
                }
                iAccion = 1;
                String sMensaje = "¿Desea guardar los documentos?";
                dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_Cobranza.this, sMensaje);
                dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
                dialog_fragment_confirmar.isCancelable();
            }

            //NUEVO REGISTRO
            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                iAccion = 3;
                String sMensaje = "¿Desea actualizar la cobranza?";
                dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_Cobranza.this, sMensaje);
                dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
                dialog_fragment_confirmar.isCancelable();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    View.OnClickListener OnClickList_co_btnguardar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GuardarCobranzas();
        }
    };



    View.OnClickListener OnClickList_co_lblfecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                comunicator.FechaDeposito();
            } catch (Exception e) {
            }
        }
    };

    //Guardar Cabecera Cobranza
    public void GuardarCabecera() {
        try {
            Double  M_COBRANZA = 0.0,
                    M_COBRANZA_D = 0.0,
                    M_COBRANZA_SALDO = 0.0;

              cpserie =sharedSettings.getString("cpserie", "").toString();
              cpnumero=sharedSettings.getString("cpnumero", "").toString();
              cpfplanilla=sharedSettings.getString("cpfplanilla", "").toString();
              cpfpago =sharedSettings.getString("cpfpago", "").toString();
              cpbancoctacte =sharedSettings.getString("cpbancoctacte", "").toString();
              cpmonto =sharedSettings.getString("cpmonto", "").toString();
              cptipocambio = sharedSettings.getString("cptipocambio", "").toString();
              cpmoneda = sharedSettings.getString("cpmoneda", "").toString();

            if (cpmoneda.equals("S")) {
                M_COBRANZA = Double.valueOf(cpmonto);
                M_COBRANZA_SALDO = M_COBRANZA;
            }
            if (cpmoneda.equals("$")) {
                M_COBRANZA_SALDO = Double.valueOf(cpmonto) * Double.valueOf(cptipocambio);
                M_COBRANZA_D = Double.valueOf(cpmonto);
            }

            documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
            documentos_cobra_cabBE.setID_COBRANZA(1);
            documentos_cobra_cabBE.setCOD_CLIENTE(sharedSettings.getString("CODIGO_ANTIGUO", "").toString());
            documentos_cobra_cabBE.setN_RECIBO(cpnumero);
            documentos_cobra_cabBE.setN_SERIE_RECIBO(cpserie);
            documentos_cobra_cabBE.setFPAGO(cpfpago);
            documentos_cobra_cabBE.setID_COBRADOR(Integer.valueOf(sharedSettings.getString("iID_VENDEDOR", "").toString()));
            documentos_cobra_cabBE.setFECHA(cpfplanilla);
            documentos_cobra_cabBE.setC_PACKING(sharedSettings.getString("C_PACKING", "0").toString());
            documentos_cobra_cabBE.setM_COBRANZA(Double.valueOf(M_COBRANZA.toString()));
            documentos_cobra_cabBE.setM_COBRANZA_D(Double.valueOf(M_COBRANZA_D.toString()));
            //setSALDO=El saldo segun se va descontando en soles convertido al tipo de cambio del dia
            documentos_cobra_cabBE.setSALDO(Double.valueOf(M_COBRANZA_SALDO.toString()));
            //setNUMCHEQ dependiendo de la forma de pago
            documentos_cobra_cabBE.setNUMCHEQ("");
            documentos_cobra_cabBE.setFECCHEQ("");

            //16082019 Se borró los dos primeros valores
            //CHEQUE
            if (cpfpago.equals("C")) {
                documentos_cobra_cabBE.setNUMCHEQ("");
                documentos_cobra_cabBE.setFECCHEQ("");
                documentos_cobra_cabBE.setID_BANCO(Integer.valueOf(cpbancoctacte));
            }
            //DEPOSITO
            if (cpfpago.equals("P")) {
                documentos_cobra_cabBE.setCTACORRIENTE_BANCO(cpbancoctacte);
                documentos_cobra_cabBE.setNRO_OPERACION("");
                documentos_cobra_cabBE.setFECHA_DEPOSITO("");
            }

            documentos_cobra_cabBE.setCOMENTARIO("");
            documentos_cobra_cabBE.setID_EMPRESA(Integer.valueOf(sharedSettings.getString("iID_EMPRESA", "").toString()));
            documentos_cobra_cabBE.setID_LOCAL(Integer.valueOf(sharedSettings.getString("iID_LOCAL", "").toString()));
            //Nace con estado Registrado tipo entero
            documentos_cobra_cabBE.setESTADO("40003");
            documentos_cobra_cabBE.setFECHA_REGISTRO(funciones.FechaActual());
            documentos_cobra_cabBE.setFECHA_MODIFICACION(funciones.FechaActual());
            documentos_cobra_cabBE.setUSUARIO_REGISTRO(sharedSettings.getString("USUARIO", "").toString());
            documentos_cobra_cabBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
            //Registrar IMEI
            documentos_cobra_cabBE.setPC_REGISTRO(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            //Registrar IMEI
            documentos_cobra_cabBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            documentos_cobra_cabBE.setIP_REGISTRO(sharedSettings.getString("sIMEI", "").toString());
            documentos_cobra_cabBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
            //Secuencia de cabecera para el registro(Tipo Entero)
            documentos_cobra_cabBE.setITEM("1");
            //Nace en estado pendiente
            documentos_cobra_cabBE.setESTADO_CONCILIADO("40004");
            documentos_cobra_cabBE.setSERIE_PLANILLA("");
            documentos_cobra_cabBE.setN_PLANILLA("");
            //Cuando concilia
            documentos_cobra_cabBE.setID_MOV_BANCO("");
            //Pendiente
            documentos_cobra_cabBE.setESTADO_PROCESO("40004");
            documentos_cobra_cabBE.setT_CAMBIO_TIENDA(sharedSettings.getString("dTIPO_CAMBIO", "0.0").toString());

            //Cuando eligen targeta y llenan el campo
            if (cpfpago.equals("D") || cpfpago.equals("V") || cpfpago.equals("M") || cpfpago.equals("S") || cpfpago.equals("I")|| cpfpago.equals("H")) {
                documentos_cobra_cabBE.setCTACORRIENTE_BANCO(cpbancoctacte);
                documentos_cobra_cabBE.setFECHA_DEPOSITO("");
                documentos_cobra_cabBE.setN_TARJETA("");

            }

            documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(0);
            documentos_cobra_cabBE.setFECHA_DEPOSITO_ABONO("");
            documentos_cobra_cabBE.setLOTE("");
            documentos_cobra_cabBE.setFLAG_COBRANZA("");
            documentos_cobra_cabBE.setCODUNC_LOCAL(Integer.valueOf(sharedSettings.getString("MAX_CODUNICO", "0").toString()));
            documentos_cobra_cabBE.setGUARDADO(0);
            documentos_cobra_cabBE.setSINCRONIZADO(0);
            //Agregar Campo para ver si ya ha sincronizado
            new Inser_CabeceraAsyncTask(documentos_cobra_cabBE).execute();
        } catch (Exception e) {

        }
    }


    //Cargar Detalle Cobranza
    public void CargarCabecera() {
        try {
            String sParametro = "", sN_SERIE_RECIBO = "", sN_RECIBO = "";
            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                sParametro = sharedSettings.getString("ID_COBRANZA", "0").toString();
                sN_SERIE_RECIBO = sharedSettings.getString("sN_SERIE_RECIBO", "").toString();
                sN_RECIBO = sharedSettings.getString("sN_RECIBO", "").toString();
            } else {
                sParametro = sharedSettings.getString("MAX_CODUNICO", "0").toString();
            }
            new LoadCabeceraCobranzaSQLite_AsyncTask().execute(
                    sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim()
                    , sParametro, sN_SERIE_RECIBO, sN_RECIBO);

        } catch (Exception ex) {
        }
    }

    private void CargarDetalleEdit() {
        try {
            new LoadDetalleCobranzaEditSQLite_AsyncTask().execute(sharedSettings.getString("ID_COBRANZA", "0").toString().trim());
        } catch (Exception ex) {
        }
    }

    //Guardar Temporal Cobranza
    private void GuardarTemporal() {
        //Cargamos e insertamos en la tabla temporal de cobranzas
        new LoadDetalleCobranzaSQLite_AsyncTask().execute(
                sharedSettings.getString("CODIGO_ANTIGUO", "").toString(),
                "XXX", "XXX", "XXX",
                sharedSettings.getString("MAX_CODUNICO", "0").toString(),
                sharedSettings.getString("ID_COBRANZA", "0").toString());
    }

    //Cargar Temporal Cobranza
    private void CargarTemporal() {
        try {
            //Cargamos las cobranzas temporales anexadas a la cabecera de la cobranza
            new LoadCobranzaTemporalSQLite_AsyncTask().execute(
                    sharedSettings.getString("MAX_CODUNICO", "0").toString(),
                    sharedSettings.getString("ID_COBRANZA", "0").toString()
            );
        } catch (Exception ex) {

        }
    }

    //Guardar Detalle Cobranza
    public void GuadarDetalle(FactCobBE factCobBE) {
        try {
            documentos_cobra_detBE = new Documentos_Cobra_DetBE();
            //Id de cobranza cabbecera
            documentos_cobra_detBE.setID_COBRANZA(factCobBE.getID_COBRANZA());
            documentos_cobra_detBE.setFPAGO(cpfpago);
            //Datos del detalle de la consulta faccob
            documentos_cobra_detBE.setTIPDOC(factCobBE.getTIPDOC());
            documentos_cobra_detBE.setSERIE_NUM(factCobBE.getSERIE_NUM());
            documentos_cobra_detBE.setNUMERO(factCobBE.getNUMERO().toString());
            documentos_cobra_detBE.setIMPORTE(Double.valueOf(factCobBE.getIMPORTE().toString()));
            documentos_cobra_detBE.setMONEDA(factCobBE.getMONEDA().toString());
            //Saldo que se descuenta mientras se va descontando
            documentos_cobra_detBE.setSALDO(factCobBE.getSALDO());
            //Monto que se esta amortizando(Al aplicar se copia al campo de cobranazas y se quita el boton)
            documentos_cobra_detBE.setM_COBRANZA(factCobBE.getVAMORTIZADO());
            documentos_cobra_detBE.setID_EMPRESA(Integer.valueOf(sharedSettings.getString("iID_EMPRESA", "").toString()));
            documentos_cobra_detBE.setID_LOCAL(Integer.valueOf(sharedSettings.getString("iID_LOCAL", "").toString()));
            //Nace con estado registrado
            documentos_cobra_detBE.setESTADO(40003);
            documentos_cobra_detBE.setFECHA_REGISTRO(funciones.FechaActual());
            documentos_cobra_detBE.setFECHA_MODIFICACION(funciones.FechaActual());
            documentos_cobra_detBE.setUSUARIO_REGISTRO(sharedSettings.getString("USUARIO", "").toString());
            documentos_cobra_detBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
            documentos_cobra_detBE.setPC_REGISTRO(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            documentos_cobra_detBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            documentos_cobra_detBE.setIP_REGISTRO(sharedSettings.getString("sIMEI", "").toString());
            documentos_cobra_detBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
            //Campo VenDed del Factcob
            documentos_cobra_detBE.setID_VENDEDOR(Integer.valueOf(factCobBE.getVENDED().toString()));
            //Igual al Saldo del Factcob
            documentos_cobra_detBE.setSALDO_INICIAL(factCobBE.getSALDO());
            documentos_cobra_detBE.setCODUNC_LOCAL(Integer.valueOf(sharedSettings.getString("MAX_CODUNICO", "0").toString()));
            documentos_cobra_detBE.setGUARDADO(0);
            documentos_cobra_detBE.setSINCRONIZADO(0);

            new Inser_DetalleAsyncTask(documentos_cobra_detBE).execute();
        } catch (Exception e) {

        } finally {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    //Eliminar Detalle Cobranza
    public void EliminarDetalle(FactCobBE factCobBE) {
        try {
            documentos_cobra_detBE = new Documentos_Cobra_DetBE();
            documentos_cobra_detBE.setID_COBRANZA(Integer.valueOf(sharedSettings.getString("ID_COBRANZA", "0").toString()));
            documentos_cobra_detBE.setID_EMPRESA(Integer.valueOf(sharedSettings.getString("ID_EMPRESA", "0").toString()));
            documentos_cobra_detBE.setID_LOCAL(Integer.valueOf(sharedSettings.getString("ID_LOCAL", "0").toString()));
            documentos_cobra_detBE.setTIPDOC(factCobBE.getTIPDOC());
            documentos_cobra_detBE.setSERIE_NUM(factCobBE.getSERIE_NUM());
            documentos_cobra_detBE.setNUMERO(factCobBE.getNUMERO().toString());
            documentos_cobra_detDAO.delete(documentos_cobra_detBE);
            //X=   documentos_cobra_detDAO.SaldoCobranza_By_ID_Cobranza_Cabecera(documentos_cobra_detBE)
            CargarCabecera();
            CargarTemporal();

        } catch (Exception e) {
        }
    }

    public void DetalleCobranzaEdit(Documentos_Cobra_DetBE documentos_cobra_detBE1, Integer iOpcion) {
        try {
            // iOpcion=1 Editar Detalle, iOpcion Eliminar Detalle
            documentos_cobra_detBE = new Documentos_Cobra_DetBE();
            documentos_cobra_detBE.setID_COBRANZA(Integer.valueOf(sharedSettings.getString("ID_COBRANZA", "0").toString()));
            documentos_cobra_detBE.setID_EMPRESA(Integer.valueOf(sharedSettings.getString("ID_EMPRESA", "0").toString()));
            documentos_cobra_detBE.setID_LOCAL(Integer.valueOf(sharedSettings.getString("ID_LOCAL", "0").toString()));
            documentos_cobra_detBE.setTIPDOC(documentos_cobra_detBE1.getTIPDOC());
            documentos_cobra_detBE.setSERIE_NUM(documentos_cobra_detBE1.getSERIE_NUM());
            documentos_cobra_detBE.setNUMERO(documentos_cobra_detBE1.getNUMERO().toString());
            documentos_cobra_detBE.setSALDO(documentos_cobra_detBE1.getSALDO());
            if (iOpcion == 1) {
                documentos_cobra_detBE.setM_COBRANZA(documentos_cobra_detBE1.getM_COBRANZA());
                documentos_cobra_detBE.setESTADO(1);
            } else {
                documentos_cobra_detBE.setESTADO(-1);
            }
            documentos_cobra_detDAO.EditDetalle(documentos_cobra_detBE, iOpcion);
            CargarCabecera();
            if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                CargarDetalleEdit();
            } else {
                CargarTemporal();
            }
        } catch (Exception e) {
        } finally {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }



    @Override
    public void onConfirmacionSI() {
        try {
            // CONFIRMAR GUARDAR
            if (iAccion == 1) {
                for (int j = 0; j < documentos_cobra_cabDAO.lst.size(); j++) {
                    Documentos_Cobra_CabBE documentos_cobra_cabBE2 = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE2.setGUARDADO(2);
                    documentos_cobra_cabBE2.setID_COBRANZA(cobranza_cabecera_adapter.lst.get(j).getID_COBRANZA());
                    documentos_cobra_cabBE2.setID_COBRADOR(cobranza_cabecera_adapter.lst.get(j).getID_COBRADOR());
                    documentos_cobra_cabBE2.setN_RECIBO(cobranza_cabecera_adapter.lst.get(j).getN_RECIBO());
                    documentos_cobra_cabBE2.setN_SERIE_RECIBO(cobranza_cabecera_adapter.lst.get(j).getN_SERIE_RECIBO());
                    documentos_cobra_cabDAO.updateEstado(documentos_cobra_cabBE2);
                }

                //ENVIAMOS AL ORACLE CONTAMOS CON INTERNET
                if (funciones.isConnectingToInternet(getContext())) {
                    new InserCobranzaAsyncTask(
                            sharedSettings.getString("ID_COBRANZA", "0").toString(),
                            sharedSettings.getString("MAX_CODUNICO", "0").toString()
                    ).execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Insert);
                }
            }
            //CONFIRMAMOS LA ELIMINACIÓN DE LA COBRANZA
            if (iAccion == 2) {
                EliminarCobranzaCabConfirma();
            }

            //CONFIRMAMOS LA ACTUALIZACIÓN DE LA COBRANZA
            if (iAccion == 3) {
                new Inser_UpdateCobranzaAsyncTask(
                        sharedSettings.getString("ID_COBRANZA", "0").toString(),
                        sharedSettings.getString("MAX_CODUNICO", "0").toString()
                ).execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Insert_Update);
            }

        } catch (Exception e) {
        }
    }

    @Override
    public void onAceptar() {
    }



    public void EliminarCobranzaCab(Integer position) {
        String sMensaje = "¿Desea eliminar la cobranza?";
        iAccion = 2;
        iPocicionCab = position;
        dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
        dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Fragment_Cobranza.this, sMensaje);
        dialog_fragment_confirmar.show(getFragmentManager(), dialog_fragment_confirmar.TAG);
        dialog_fragment_confirmar.isCancelable();
    }

    private void EliminarCobranzaCabConfirma() {
        try {
            Documentos_Cobra_CabBE documentos_cobra_cabBE2 = (Documentos_Cobra_CabBE) cobranza_cabecera_adapter.getItem(iPocicionCab);
            new Delete_CabeceraAsyncTask(documentos_cobra_cabBE2).execute();

        } catch (Exception e) {
        }
    }

    private void Anticipo(Documentos_Cobra_CabBE documentos_cobra_cabBE) {
        if (cobranza_cabecera_adapter == null) {
            Mensaje("Cabecera no es válida");
            return;
        }
        if (cobranza_cabecera_adapter.getCount() > 0) {
            Mensaje("Cabecera no es válida");
            return;
        }
        new Inser_TemporalAsyncTask(factCobBE).execute();
    }

    private class Inser_CabeceraAsyncTask extends AsyncTask<String, String, String> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;
        private Documentos_Cobra_CabBE documentos_cobra_cabBE;
        private Context context;

        public Inser_CabeceraAsyncTask(Documentos_Cobra_CabBE documentos_cobra_cabBE) {
            this.documentos_cobra_cabBE = documentos_cobra_cabBE;
        }

        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... p) {
            return documentos_cobra_cabDAO.insert(documentos_cobra_cabBE, getContext(), iAnticipo);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }

        @Override
        protected void onPostExecute(String result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (!result.trim().equals("")) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.toString()).Show();
                } else {

                    if (sharedSettings.getString("VALIDASALDO", "0").toString().trim().equals("0")) {
                        //  ActivarVisible(co_txtformapago.getTag().toString().trim());
                        //CARGAMOS CABECERA DE LA COBRANZA
                        CargarCabecera();
                        //Guardamos en la tabla temporal FactCOb amarrado a la Cabecera de la cobranza
                        GuardarTemporal();
                    } else {
                        new ToastLibrary(getActivity(), "Ya no hay saldo a cobrar.").Show();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LoadDetalleCobranzaSQLite_AsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... p) {
            try {

                factCobDAO.getCobranza(p[0], p[1], p[2], p[3], p[4], p[5]);
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
                cobranza_detalle_adapter = new Cobranza_Detalle_Adapter(getActivity(), 0, factCobDAO.lst);
                cobranza_detalle_adapter.notifyDataSetChanged();
                //co_lsdet.setAdapter(cobranza_pagos_adapter);
                for (int j = 0; j < cobranza_detalle_adapter.lstFiltrado.size(); j++) {
                    factCobBE = new FactCobBE();
                    factCobBE.setID_COBRANZA(Integer.valueOf(sharedSettings.getString("ID_COBRANZA", "0").toString()));
                    factCobBE.setCOD_CLIENTE(cobranza_detalle_adapter.lst.get(j).getCOD_CLIENTE());
                    factCobBE.setTIPDOC(cobranza_detalle_adapter.lst.get(j).getTIPDOC());
                    factCobBE.setSERIE_NUM(cobranza_detalle_adapter.lst.get(j).getSERIE_NUM());
                    factCobBE.setNUMERO(cobranza_detalle_adapter.lst.get(j).getNUMERO());
                    factCobBE.setFECHA(cobranza_detalle_adapter.lst.get(j).getFECHA());
                    factCobBE.setF_VENCTO(cobranza_detalle_adapter.lst.get(j).getF_VENCTO());
                    factCobBE.setF_ACEPTACION(cobranza_detalle_adapter.lst.get(j).getF_ACEPTACION());
                    factCobBE.setF_TRANSFE(cobranza_detalle_adapter.lst.get(j).getF_TRANSFE());
                    factCobBE.setANO(cobranza_detalle_adapter.lst.get(j).getANO());
                    factCobBE.setMES(cobranza_detalle_adapter.lst.get(j).getMES());
                    factCobBE.setLIBRO(cobranza_detalle_adapter.lst.get(j).getLIBRO());
                    factCobBE.setVOUCHER(cobranza_detalle_adapter.lst.get(j).getVOUCHER());
                    factCobBE.setITEM(cobranza_detalle_adapter.lst.get(j).getITEM());
                    factCobBE.setTIPO_REFERENCIA(cobranza_detalle_adapter.lst.get(j).getTIPO_REFERENCIA());
                    factCobBE.setSERIE_REF(cobranza_detalle_adapter.lst.get(j).getSERIE_REF());
                    factCobBE.setNRO_REFERENCIA(cobranza_detalle_adapter.lst.get(j).getNRO_REFERENCIA());
                    factCobBE.setCONCEPTO(cobranza_detalle_adapter.lst.get(j).getCONCEPTO());
                    factCobBE.setSISTEMA_ORIGEN(cobranza_detalle_adapter.lst.get(j).getSISTEMA_ORIGEN());
                    factCobBE.setVENDED(cobranza_detalle_adapter.lst.get(j).getVENDED());
                    factCobBE.setBANCO(cobranza_detalle_adapter.lst.get(j).getBANCO());
                    factCobBE.setL_AGENCIA(cobranza_detalle_adapter.lst.get(j).getL_AGENCIA());
                    factCobBE.setL_REFBCO(cobranza_detalle_adapter.lst.get(j).getL_REFBCO());
                    factCobBE.setL_CONDLE(cobranza_detalle_adapter.lst.get(j).getL_CONDLE());
                    factCobBE.setMONEDA(cobranza_detalle_adapter.lst.get(j).getMONEDA());
                    factCobBE.setIMPORTE(Double.valueOf(cobranza_detalle_adapter.lst.get(j).getIMPORTE().toString()));
                    factCobBE.setTCAM_IMP(Double.valueOf(cobranza_detalle_adapter.lst.get(j).getTCAM_IMP().toString()));
                    factCobBE.setSALDO(cobranza_detalle_adapter.lst.get(j).getSALDO());
                    factCobBE.setCOBRANZA(cobranza_detalle_adapter.lst.get(j).getCOBRANZA());
                    factCobBE.setTCAM_SAL(cobranza_detalle_adapter.lst.get(j).getTCAM_SAL());
                    factCobBE.setNUMERO_CANJE(cobranza_detalle_adapter.lst.get(j).getNUMERO_CANJE());
                    factCobBE.setESTADO(cobranza_detalle_adapter.lst.get(j).getESTADO());
                    factCobBE.setCTACTBLE(cobranza_detalle_adapter.lst.get(j).getCTACTBLE());
                    factCobBE.setF_RECEPCION(cobranza_detalle_adapter.lst.get(j).getF_RECEPCION());
                    factCobBE.setC_USUARIO(cobranza_detalle_adapter.lst.get(j).getC_USUARIO());
                    factCobBE.setC_PERFIL(cobranza_detalle_adapter.lst.get(j).getC_PERFIL());
                    factCobBE.setC_CPU(cobranza_detalle_adapter.lst.get(j).getC_CPU());
                    factCobBE.setFEC_REG(cobranza_detalle_adapter.lst.get(j).getFEC_REG());
                    factCobBE.setC_USUARIO_MOD(cobranza_detalle_adapter.lst.get(j).getC_USUARIO_MOD());
                    factCobBE.setC_PERFIL_MOD(cobranza_detalle_adapter.lst.get(j).getC_PERFIL_MOD());
                    factCobBE.setFEC_MOD(cobranza_detalle_adapter.lst.get(j).getFEC_MOD());
                    factCobBE.setC_CPU_MOD(cobranza_detalle_adapter.lst.get(j).getC_CPU_MOD());
                    factCobBE.setN_SERIE_RECIBO_COBRA(cobranza_detalle_adapter.lst.get(j).getN_SERIE_RECIBO_COBRA());
                    factCobBE.setN_RECIBO_COBRA(cobranza_detalle_adapter.lst.get(j).getN_RECIBO_COBRA());
                    factCobBE.setANO_PROVISION(cobranza_detalle_adapter.lst.get(j).getANO_PROVISION());
                    factCobBE.setMES_CSTGO(cobranza_detalle_adapter.lst.get(j).getMES_CSTGO());
                    factCobBE.setANO_CSTGO(cobranza_detalle_adapter.lst.get(j).getANO_CSTGO());
                    factCobBE.setLIBRO_CSTGO(cobranza_detalle_adapter.lst.get(j).getLIBRO_CSTGO());
                    factCobBE.setVOUCHER_CSTGO(cobranza_detalle_adapter.lst.get(j).getVOUCHER_CSTGO());
                    factCobBE.setDIAS(cobranza_detalle_adapter.lst.get(j).getDIAS());
                    factCobBE.setCOBRANZA(0.0);
                    factCobBE.setCODUNC_LOCAL(Integer.valueOf(sharedSettings.getString("MAX_CODUNICO", "0").toString()));
                    new Inser_TemporalAsyncTask(factCobBE).execute();
                }
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Inser_TemporalAsyncTask extends AsyncTask<String, String, String> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;
        private FactCobBE factCobBE;
        private Context context;

        public Inser_TemporalAsyncTask(FactCobBE factCobBE) {
            this.factCobBE = factCobBE;
        }

        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... p) {
            return factCobDAO.insertTemp(factCobBE);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }

        @Override
        protected void onPostExecute(String result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (!result.trim().equals("")) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.toString()).Show();
                } else {
                    //Cargamos el Temporal FactCOb amarrado a la Cabecera de la cobranza.
                    CargarTemporal();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LoadCobranzaTemporalSQLite_AsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                factCobDAO.getCobranzaTemp(p[0], p[1]);
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
                cobranza_detalle_adapter = new Cobranza_Detalle_Adapter(getActivity(), 0, factCobDAO.lst);
                cobranza_detalle_adapter.notifyDataSetChanged();
                co_lsdet.setAdapter(cobranza_detalle_adapter);
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class LoadCabeceraCobranzaSQLite_AsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getAllBy(p[0], p[1], p[2], p[3]);
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
                cobranza_cabecera_adapter = new Cobranza_Cabecera_Adapter(getActivity(), 0, documentos_cobra_cabDAO.lst);
                cobranza_cabecera_adapter.notifyDataSetChanged();
                co_lscab.setAdapter(cobranza_cabecera_adapter);

                editor_Shared.putString("SALDO_CABECERA",cobranza_cabecera_adapter.getItem(iPocicionCab).getSALDO().toString());
                editor_Shared.commit();

                Globals g = (Globals)getActivity().getApplication();
                g.setIntentCobranzaCab(cobranza_cabecera_adapter);

                if (cobranza_cabecera_adapter.getCount() > 0) {
                    //co_lbltipocambio.setText(cobranza_cabecera_adapter.lst.get(0).getT_CAMBIO_TIENDA().toString());
                    // ActivarRecibo(false);
                } else {
                    // ActivarRecibo(true);
                }
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Delete_CabeceraAsyncTask extends AsyncTask<String, String, String> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;
        private Documentos_Cobra_CabBE documentos_cobra_cabBE;
        private Context context;

        public Delete_CabeceraAsyncTask(Documentos_Cobra_CabBE documentos_cobra_cabBE) {
            this.documentos_cobra_cabBE = documentos_cobra_cabBE;
        }

        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... p) {
            //CYPER100
            return documentos_cobra_cabDAO.delete(documentos_cobra_cabBE);

            //return documentos_cobra_cabDAO.UpdateCobranzaAnular(documentos_cobra_cabBE);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }

        @Override
        protected void onPostExecute(String result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (!result.trim().equals("")) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.toString()).Show();
                } else {
                    CargarCabecera();
                    CargarTemporal();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                if (documentos_cobra_cabDAO.lst.size()== 0){
                    //Si estamos Editando entonces Cerramos
                    if (sharedSettings.getString("COBRANZA_EVENTO", "0").toString().trim().equals("1")) {
                        comunicator.Finalizar();
                    }
                }
            }
        }
    }

    private class Inser_DetalleAsyncTask extends AsyncTask<String, String, String> {
        private volatile boolean running = true;
        private Documentos_Cobra_DetBE documentos_cobra_detBE;
        private Context context;

        public Inser_DetalleAsyncTask(Documentos_Cobra_DetBE documentos_cobra_detBE) {
            this.documentos_cobra_detBE = documentos_cobra_detBE;
        }

        private ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            /*
            Double dSALDOCAB = documentos_cobra_detDAO.SaldoCobranza_By_ID_Cobranza_Cabecera(documentos_cobra_detBE);
            Double dCOBRANZA = documentos_cobra_detDAO.MontoCobrado_By_ID_Cobranza_Temporal(documentos_cobra_detBE);
            Double dSALDOCABACT = (dSALDOCAB -dCOBRANZA);
            if (dSALDOCAB > 0 && documentos_cobra_detBE.getM_COBRANZA() > dSALDOCABACT){
                documentos_cobra_detBE.setM_COBRANZA(dSALDOCABACT);
            }
            */

        }

        @Override
        protected String doInBackground(String... p) {
            return documentos_cobra_detDAO.insert(documentos_cobra_detBE);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }

        @Override
        protected void onPostExecute(String result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (!result.toString().trim().equals("")) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.toString()).Show();
                }
                CargarCabecera();
                CargarTemporal();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class LoadDetalleCobranzaEditSQLite_AsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_detDAO.getByID_Cobranza(p[0]);
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
                cobranza_detalle_adapter_edit = new Cobranza_Detalle_Adapter_Edit(getActivity(), 0, documentos_cobra_detDAO.lst);
                cobranza_detalle_adapter_edit.notifyDataSetChanged();
                co_lsdet.setAdapter(cobranza_detalle_adapter_edit);
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }

    //COBRANZA INSERT,UPDATE,DELETE
    private class InserCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;
        private String ID_COBRANZA, CODUNC_LOCAL;

        public InserCobranzaAsyncTask(String ID_COBRANZA, String CODUNC_LOCAL) {
            this.ID_COBRANZA = ID_COBRANZA;
            this.CODUNC_LOCAL = CODUNC_LOCAL;
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.InsertRest(ID_COBRANZA, CODUNC_LOCAL, p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (result.getInt("status") == 0) {
                    Mensaje("Error al enviar los registros.");
                } else {
                    if (!result.getString("MSG").toString().trim().equals("-")) {
                        Mensaje(result.getString("MSG").toString().trim());
                        return;
                    }
                    //ABRIMOS EL RECIBO
                    editor_Shared.putString("iN_SERIE_RECIBO",sharedSettings.getString("cpserie", "0").toString());
                    editor_Shared.putString("iN_RECIBO", sharedSettings.getString("cpnumero", "0").toString());
                    editor_Shared.putString("IOPCION_REPORTE","1");
                    editor_Shared.commit();

                    Intent intent = new Intent(getContext().getApplicationContext(), Activity_Cobranza_Recibo_Rep.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                    //FIN ABRIR EL RECIBO

                    comunicator.Finalizar();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

    private class Inser_UpdateCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;
        private String ID_COBRANZA, CODUNC_LOCAL;

        public Inser_UpdateCobranzaAsyncTask(String ID_COBRANZA, String CODUNC_LOCAL) {
            this.ID_COBRANZA = ID_COBRANZA;
            this.CODUNC_LOCAL = CODUNC_LOCAL;
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.Insert_UpdateRest(ID_COBRANZA, CODUNC_LOCAL, p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (result.getInt("status") == 0) {
                } else {
                    comunicator.Finalizar();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }

    //COBRANZA SINCRONIZAR CABECERA Y DETALLE
    public class Documentos_Cobra_CabBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            documentos_cobra_cabPG = new Dialog_Fragment_Progress();
            documentos_cobra_cabPG.setMensaje("Sincronizando");
            documentos_cobra_cabPG.show(getActivity().getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getSincronizar(p[0], p[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (documentos_cobra_cabPG != null && documentos_cobra_cabPG.isVisible()) {
                documentos_cobra_cabPG.dismiss();
            }
            try {
                if (result.getInt("status") != 1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.getString("message")).Show();
                } else {
                  /*  Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.documentos_Cobra_CabBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();*/

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public class Documentos_Cobra_DetBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            documentos_cobra_detPG = new Dialog_Fragment_Progress();
            documentos_cobra_detPG.setMensaje("Sincronizando");
            documentos_cobra_detPG.show(getActivity().getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_detBL.getSincronizar(p[0], p[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (documentos_cobra_detPG != null && documentos_cobra_detPG.isVisible()) {
                documentos_cobra_detPG.dismiss();
            }
            try {
                if (result.getInt("status") != 1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(getActivity(), result.getString("message")).Show();
                } else {
                    /*Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.documentos_Cobra_DetBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();*/

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}

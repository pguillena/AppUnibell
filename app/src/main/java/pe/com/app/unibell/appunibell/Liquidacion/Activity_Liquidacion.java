package pe.com.app.unibell.appunibell.Liquidacion;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.ByteArrayOutputStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import pe.com.app.unibell.appunibell.AD.Cobranza_Liquidacion_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;
import android.widget.ProgressBar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class Activity_Liquidacion extends AppCompatActivity
        implements Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    //private TextView lq_txtfestado,lq_txtfplan,lq_lblbuscar;
    private String lq_txtffecha;
    private String lq_txttipopago,lq_txtccbanco,lq_txtnroope,lq_txtfecha ;
    private TextView lq_lbltotalg;
    private LinearLayout lq_lnFiltro;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private ListView lq_lsdetalle;
    private CheckBox lq_chktodos;
    private Integer iOpcionFecha=0,iOpcionHora=0;
    private DialogFragment dialogFragmentFecha;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private Integer iAuxiliar=0;
    private Integer iTabla=0;

    private Funciones funciones=new Funciones();
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO2 = new Documentos_Cobra_CabDAO();
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO3 = new Documentos_Cobra_CabDAO();
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();

    private Documentos_Cobra_CabBE documentos_cobra_cabBE;
    private Documentos_Cobra_MovBE documentos_cobra_movBE;

    private Cobranza_Liquidacion_Adapter cobranza_liquidacion_adapter = null;

    private Dialog_Fragment_Aceptar log_dialogaceptar;

    private double dMontoCheque=0.0;
    private double dMontoVisa=0.0;
    private double dMontoEfectivo=0.0;
    private double dMontoBancarizado=0.0;
    private double dMontoGeneral=0.0;
    private double dMontoDeposito=0.0;
    private Integer iEvento=0;
    private static String NOMBRE_CARPETA_APP = "UNIBELL_REPORT";
    private static String GENERADOS = "PDF";
    private Document document;
    private  String nombre_completo="";
    private String sMoneda="";
    private  Integer iValorEnviar=0;
    private Integer iValor=0;
    int request_code = 1;
    int contadorChecks = 0;
    private String sNroPlanilla, sCPacking, sEstado="40003", sFecha, sFormaPago, sRazonSocialCliente;
    private String lq_lblmontoc,lq_lblmontov,lq_lblmontoe,lq_lblmontob, lq_lblmontop;
    private EditText txtPackingEnvio;
    private TextView txtProgressLoading;
    private ProgressBar pbLiquidacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_liquidacion);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Liquidación de cobranzas");
            getSupportActionBar().setSubtitle("");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            lq_chktodos = (CheckBox) findViewById(R.id.lq_chktodos);
            lq_lbltotalg=(TextView)findViewById(R.id.lq_lbltotalg);
            lq_lsdetalle=(ListView)findViewById(R.id.lq_lsdetalle);

            txtProgressLoading=(TextView)findViewById(R.id.txtProgressLoading);
            pbLiquidacion = (ProgressBar)findViewById(R.id.pbLiquidacion);

            lq_chktodos.setOnCheckedChangeListener(lq_chktodos_ChangeListener);
            FloatingActionButton fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
            fabBuscar.setOnClickListener(OnClickListener_fabBuscar);

            Button btnAsignar = (Button) findViewById(R.id.btnAsignar);
            Button btnEnvio = (Button) findViewById(R.id.btnEnvio);
            TextView btnVerDetalle = (TextView) findViewById(R.id.btnVerDetalle);
            txtPackingEnvio = (EditText) findViewById(R.id.txtPackingEnvio);
            btnAsignar.setOnClickListener(OnClickListener_btnAsignar);
            btnEnvio.setOnClickListener(OnClickListener_btnEnvio);
            btnVerDetalle.setOnClickListener(OnClickListener_btnVerDetalle);

            ColorStateList cs3 = new ColorStateList(new int[][]{new int[0]}, new int[] {0xff78ae6e});

            Button btnConciliar = (Button) findViewById(R.id.btnConciliar);
            btnConciliar.setOnClickListener(OnClickListenercl_btnConciliar);

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplication());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();


            new LoadGetGuardadaSQLite_AsyncTask().execute();

            Cargar();

            txtPackingEnvio.setVisibility(View.GONE);
            //(usuario.ROL == (int)EnumRoles.LiquidadorCobranzaDespacho || usuario.ROL == (int)EnumRoles.RegistradorPedidos)
            if(sharedSettings.getString("ROL", "").toString().equals("130019") || sharedSettings.getString("ROL", "").toString().equals("130008"))
            {
                txtPackingEnvio.setVisibility(View.VISIBLE);
            }



        } catch (Exception ex) {

        }
        lq_lsdetalle.setOnItemLongClickListener(OnItemLongClickListener_lq_lsdetalle);

        lq_lsdetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(cobranza_liquidacion_adapter.lst.get(position).getESTADO().toString().equals("40003")) //SOLO SE PUEDE REASIGNAR SI EL ESTADO ESTA COMO REGISTRADO
                {
                    int CantidadSeleccionado = 0;

                    if (cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO()) {


                        boolean estadoBol = cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO();

                        for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
                            if (cobranza_liquidacion_adapter.lst.get(i).getRECIBO().equals(cobranza_liquidacion_adapter.lst.get(position).getRECIBO().toString())) {
                                cobranza_liquidacion_adapter.lst.get(i).setCHKMARCADO(!estadoBol);
                            }
                        }


                        //cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(!cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO());
                        cobranza_liquidacion_adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
                            if (cobranza_liquidacion_adapter.lst.get(i).getCHKMARCADO()) {
                                CantidadSeleccionado = CantidadSeleccionado + 1;
                            }
                        }

                        if (CantidadSeleccionado > 0) {
                           // cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(!cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO());

                            boolean estadoBol = cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO();

                            for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
                                if (cobranza_liquidacion_adapter.lst.get(i).getRECIBO().equals(cobranza_liquidacion_adapter.lst.get(position).getRECIBO().toString())) {
                                    cobranza_liquidacion_adapter.lst.get(i).setCHKMARCADO(!estadoBol);
                                }
                            }

                            cobranza_liquidacion_adapter.notifyDataSetChanged();
                        }

                    }

                }

                CambiarNumeroCheck();

            }


        });

    }

    private void CambiarNumeroCheck() {
        contadorChecks=0;
        double montoTotalSeleccionado = 0.0;
        for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
            if (cobranza_liquidacion_adapter.lst.get(i).getCHKMARCADO()) {
                contadorChecks = contadorChecks + 1;

                montoTotalSeleccionado = funciones.sumar(montoTotalSeleccionado,cobranza_liquidacion_adapter.lst.get(i).getM_COBRANZA());
            }
        }

        if (contadorChecks>0)
        {
            lq_chktodos.setText(""+contadorChecks + "   S/ " + montoTotalSeleccionado);
        }
        else
        {
            lq_chktodos.setText("Todo");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_filtro_list, menu);
        final MenuItem ic_action_refresh = menu.findItem(R.id.ic_action_refresh);
        final MenuItem ic_action_buscar = menu.findItem(R.id.ic_action_buscar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(ic_action_buscar);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        searchView.setQueryHint("Ingresa Datos a filtrar");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if(lq_lsdetalle.getAdapter()!=null) {
                        Cobranza_Liquidacion_Adapter ca = (Cobranza_Liquidacion_Adapter) lq_lsdetalle.getAdapter();
                        ca.getFilter().filter(newText);
                    }
                } else {
                    Cobranza_Liquidacion_Adapter ca = (Cobranza_Liquidacion_Adapter) lq_lsdetalle.getAdapter();
                    ca.getFilter().filter(newText);
                }
                return true;
            }
        });

        ic_action_refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Cargar();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    AdapterView.OnItemLongClickListener OnItemLongClickListener_lq_lsdetalle = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            // TODO Auto-generated method stub

            if(cobranza_liquidacion_adapter.lst.get(position).getESTADO().toString().equals("40003")  ) //SOLO SE PUEDE REASIGNAR SI EL ESTADO ESTA COMO REGISTRADO
            {
                if(!cobranza_liquidacion_adapter.lst.get(position).getESTADO_CONCILIADO().toString().equals("40025")) //ESTADO DIFERENTE DE CONCILIADO
                {
                    boolean estadoBol = cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO();

                    for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
                        if (cobranza_liquidacion_adapter.lst.get(i).getRECIBO().equals(cobranza_liquidacion_adapter.lst.get(position).getRECIBO().toString())) {
                            cobranza_liquidacion_adapter.lst.get(i).setCHKMARCADO(!estadoBol);
                        }
                    }

                    cobranza_liquidacion_adapter.notifyDataSetChanged();
                }
                else
                {
                    cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(false);
                    cobranza_liquidacion_adapter.notifyDataSetChanged();

                    Mensaje("No se puede cambiar los datos de un documento conciliado");
                }

            }

            CambiarNumeroCheck();

            return true;
        }
    };

    View.OnClickListener OnClickListener_btnAsignar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            request_code = 2;
            int existeCheck=0;
            Boolean iValor = false;
            for (int j = 0; j < cobranza_liquidacion_adapter.lst.size(); j++) {
                iValor = Boolean.valueOf(cobranza_liquidacion_adapter.lst.get(j).getCHKMARCADO().toString());
                if(iValor)
                {
                    existeCheck=existeCheck+1;
                }
            }
            if (existeCheck>0)
            {
                try {
                    Intent intent = new Intent(Activity_Liquidacion.this, Activity_Asignar.class);
                    startActivityForResult(intent,request_code);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(getApplication(),"Debe elegir al menos un documento para reasiganar", Toast.LENGTH_LONG).show();
            }
        }


    };

    View.OnClickListener OnClickListener_btnEnvio = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            boolean existeDatos = false;
            if(documentos_cobra_cabDAO.lst!=null && documentos_cobra_cabDAO.lst.size()>0)
            {
                for(int i=0; i<documentos_cobra_cabDAO.lst.size(); i++) {
                    if(documentos_cobra_cabDAO.lst.get(i).getESTADO().equals("40003"))
                    {
                        existeDatos = true;
                        break;
                    }
                }
            }

            if(existeDatos)
            {
            iOpcionFecha = 3;//EnviarPlanilla
            try {
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getSupportFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
            else
            {
                Mensaje("No existen cobranzas pendientes de envio");
            }


        }


    };

    @Override
    public void setearFecha(String fecha) {
        try {
            if(iOpcionFecha==3){
                EnviarPlanilla(fecha);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    View.OnClickListener OnClickListener_btnVerDetalle = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            VerDetalleTotales();

        }


    };

    View.OnClickListener OnClickListener_fabBuscar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            request_code = 1;
            try {
                BuscarLiquidacion();
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }


    };

    private void BuscarLiquidacion() {

        Intent intent = new Intent(Activity_Liquidacion.this, Activity_FiltroLiquidacion.class);
        intent.putExtra("txtEstado",sEstado);
        intent.putExtra("txtFechaFiltro",lq_txtffecha);
        intent.putExtra("txtNroPlanilla",sNroPlanilla);
        intent.putExtra("txtCpacking",sCPacking);
        intent.putExtra("txtFormaPagoFiltroLiquidacion",sFormaPago);
        if(sRazonSocialCliente.equals("XXX"))
        {
            sRazonSocialCliente = "";
        }
        intent.putExtra("txtClienteFiltroLiquidacion",sRazonSocialCliente);
        startActivityForResult(intent,request_code);
    }

    private void VerDetalleTotales() {

        Intent intent = new Intent(Activity_Liquidacion.this, Activity_Totales_Liquidacion.class);
        intent.putExtra("txtMontoTotalGeneral", lq_lbltotalg.getText().toString().trim());
        intent.putExtra("txtMontoCheque", lq_lblmontoc);
        intent.putExtra("txtMontoTarjeta", lq_lblmontov);
        intent.putExtra("txtMontoEfectivo", lq_lblmontoe);
        intent.putExtra("txtMontoBancarizado", lq_lblmontob);
        intent.putExtra("txtMontoDeposito", lq_lblmontop);
        intent.putExtra("listaDepositos", documentos_cobra_cabDAO2.lst);
        intent.putExtra("listaTarjetas", documentos_cobra_cabDAO3.lst);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        if ((requestCode == 1) && (resultCode == RESULT_OK)){
            Bundle   parametros = data.getExtras();
            if(parametros !=null){

                lq_txtffecha = (parametros.getString("lq_txtffechaFiltro"));
                sNroPlanilla =  parametros.getString("txtNroPlanilla");
                sCPacking =  parametros.getString("txtCpacking");
                sEstado =  parametros.getString("lq_txtfestado");
                sFormaPago =  parametros.getString("lq_txtfpagoFiltro");
                sRazonSocialCliente = parametros.getString("txtClienteFiltroLiquidacion");

                Cargar();
            }

        }
        else if ((requestCode == 2) && (resultCode == RESULT_OK))
        {
            Bundle   parametros = data.getExtras();

            if(parametros !=null){
                lq_txttipopago = (parametros.getString("hdfTipoPago"));
                lq_txtccbanco = (parametros.getString("hdfCCBanco"));
                lq_txtnroope = (parametros.getString("hdfNroOp"));
                lq_txtfecha = (parametros.getString("hdfFecha"));
                try {
                    if (Validar()==false){return;}
                    iEvento=1;
                    Reasignar();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }
    }

    private void Reasignar() {
        Boolean iValor=false;
        String fPago="";

        //ASIGNAR DOCUMENTOS
        if (iEvento==1) {
            try {
                for (int j = 0; j < cobranza_liquidacion_adapter.lst.size(); j++) {
                    iValor = Boolean.valueOf(cobranza_liquidacion_adapter.lst.get(j).getCHKMARCADO().toString());
                    if (iValor == true) {
                        documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                        fPago = lq_txttipopago.toString().trim();

                        documentos_cobra_cabBE.setFPAGO(fPago);
                        documentos_cobra_cabBE.setID_COBRANZA(cobranza_liquidacion_adapter.lst.get(j).getID_COBRANZA());

                        if (fPago.equals("C")) {
                            documentos_cobra_cabBE.setNUMCHEQ(lq_txtnroope.toString());
                            documentos_cobra_cabBE.setFECCHEQ(lq_txtfecha.toString());
                            documentos_cobra_cabBE.setID_BANCO(Integer.valueOf(lq_txtccbanco.toString()));
                        }
                        //DEPOCITO
                        if (fPago.equals("P")) {
                            documentos_cobra_cabBE.setCTACORRIENTE_BANCO(lq_txtccbanco.toString());
                            documentos_cobra_cabBE.setNRO_OPERACION(lq_txtnroope.toString());
                            documentos_cobra_cabBE.setFECHA_DEPOSITO(lq_txtfecha.toString());
                        }
                        //Cuando eligen targeta y llenan el campo
                        if (fPago.equals("D") || fPago.equals("V") || fPago.equals("M") || fPago.equals("S") || fPago.equals("I")|| fPago.equals("H")) {
                            documentos_cobra_cabBE.setCTACORRIENTE_BANCO(lq_txtccbanco.toString());
                            documentos_cobra_cabBE.setFECHA_DEPOSITO(lq_txtfecha.toString());
                            documentos_cobra_cabBE.setN_TARJETA(lq_txtnroope.toString());
                        }
                        documentos_cobra_cabBE.setGUARDADO(3);
                        documentos_cobra_cabDAO.UpdateCobranzaReasignar(documentos_cobra_cabBE);
                    }
                }
                this.Cargar();
            }catch (Exception e){
            }
            try{
                Integer vID_COBRANZA=0;
                //ENVIMAOS LOS REGISTROS AL ORACLE TODAS LAS COBRANZAS QUE EL CAMPO ""GUARDADO=3-COBRANZAS MODIFICADAS"
                new UpdateCobranzaAsyncTask(vID_COBRANZA.toString(),"LIQUIDACION_ASIGNAR").execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Update);
            }catch (Exception e){

            }

        }
    }

    CompoundButton.OnCheckedChangeListener lq_chktodos_ChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            contadorChecks = 0;
            if (!isChecked) {
                MarcarCheks(false);
            } else {
                MarcarCheks(true);
            }

        }
    };

    private void MarcarCheks(Boolean chk){

        for (int j = 0; j < cobranza_liquidacion_adapter.lst.size(); j++) {

            if(cobranza_liquidacion_adapter.lst.get(j).getESTADO().toString().equals("40003") && !cobranza_liquidacion_adapter.lst.get(j).getESTADO_CONCILIADO().toString().equals("40025")) //SOLO SE PUEDE REASIGNAR SI EL ESTADO ESTA COMO REGISTRADO Y QUE NO ESTE CONCILIADO
            {
                cobranza_liquidacion_adapter.lst.get(j).setCHKMARCADO(chk);
                contadorChecks  = contadorChecks + 1;
            }
        }
        cobranza_liquidacion_adapter.notifyDataSetChanged();

        if(chk) {
            if (contadorChecks > 0) {
                lq_chktodos.setTextColor(this.getResources().getColor(R.color.Button_login_unibell));
            } else {
                lq_chktodos.setChecked(false);
                Mensaje("No se puede seleccionar cobranzas enviadas");
                lq_chktodos.setTextColor(this.getResources().getColor(R.color.Button_recibo_unibell));
            }
        }

        CambiarNumeroCheck();

    }


    AbsListView.OnItemClickListener lq_lsdetalle_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };


    public class GeneraPDFAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        @Override
        protected void onPreExecute(){}
        @Override
        protected JSONObject doInBackground(String... p) {
            String NOMBRE_ARCHIVO = "REP_LIQUIDACION.pdf";
            if (iValorEnviar==1) {
                NOMBRE_ARCHIVO =sharedSettings.getString("iID_EMPRESA", "REP_LIQUIDACION").toString() +
                        "_" + funciones.Year(funciones.FechaActual()).toString()+
                        funciones.Month(funciones.FechaActual()).toString()+
                        funciones.Day(funciones.FechaActual()).toString()+".pdf";
            }
            String tarjetaSD = Environment.getExternalStorageDirectory().toString();
            File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);
            try {
                if (!pdfDir.exists()) {
                    pdfDir.mkdir();
                }

                File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
                if (!pdfSubDir.exists()) {
                    pdfSubDir.mkdir();
                }
                nombre_completo = Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_APP + File.separator + GENERADOS + File.separator + NOMBRE_ARCHIVO;
                File outputfile = new File(nombre_completo);
                if (outputfile.exists()) {
                    outputfile.delete();
                }
                document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
                //document = new Document();
                //document.setPageSize(PageSize.A4.rotate(),5,5,5,5);
                //document= new Document();
                PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));
                //PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));

                document.open();
                document.addAuthor("Renan galvez");
                document.addCreator("RENAN");
                document.addSubject("Reportes");
                document.addCreationDate();
                document.addTitle("LIQUIDACIÓN Y APROBACIÓN DE PLANILLA");
                XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

                String htmToCab="";
                String htmToDet="";
                String htmTotalGeneral="";
                String htmPie="";
                String PDF="";
                Double SubTal=0.0;
                String co_txtfechainicio="";
                try {
                    //REPORTE POR CATEGORIA
                    if (cobranza_liquidacion_adapter != null) {
                        htmToCab =
                                "<html>" +
                                        "<head>" +
                                        "<body>" +
                                        "<table width='100%'>" +
                                        "<tr width='100%'>" +
                                        "<td width='80%' style='font-weight:bold'>PLANILLA LIQUIDACIÓN DE COBRANZA " + cobranza_liquidacion_adapter.lst.get(0).getPLANILLA().toString() + "</td><td width='20%'><b>Usuario:</b>" + sharedSettings.getString("USUARIO", "").toString() + "</td></tr>" +
                                        "<tr width='100%'>" +
                                        "<td width='80%'></td><td width='20%'><b>Fecha:</b>" + funciones.FechaActualNow() + "</td></tr>" +
                                        "<tr width='100%'>" +
                                        "<td width='80%'><b>Fecha Cobranza:</b>" + cobranza_liquidacion_adapter.lst.get(0).getFECHA().toString() +  "</td><td width='20%'><b>Cobrador:</b>" +  cobranza_liquidacion_adapter.lst.get(0).getNOMCOBRADOR().toString() + "</td></tr>" +
                                        "<tr width='100%' style='background:#FFDDDD'>" +
                                        "<td width='80%' ><b>PALNILLA:</b>" + cobranza_liquidacion_adapter.lst.get(0).getPLANILLA().toString() + "</td><td width='20%'></td></tr>" +
                                        "</table>" +
                                        "<table width='100%' border=0.01 cellspacing=0 cellpadding=5 bordercolor='666633'>" +
                                        "<tr><td width='7.5%' align='left' style='font-size:10px;font-weight:bold' >Código</td>" +
                                        "<td width='7.5%' style='font-size:10px;font-weight:bold'>RUC/DNI</td>" +
                                        "<td width='15%' style='font-size:10px;font-weight:bold'>Nombre/Razon Social</td>" +
                                        "<td width='5%' style='font-size:10px;font-weight:bold'>Tipo Doc</td>" +
                                        "<td width='10%' style='font-size:10px;font-weight:bold'>N°.Doc</td>" +
                                        "<td width='6%' style='font-size:10px;font-weight:bold' >Forma Pago</td>" +
                                        "<td width='10%' style='font-size:10px;font-weight:bold' >Efectivo/Baco/Cuenta Corriente</td>" +
                                        "<td width='10%' style='font-size:10px;font-weight:bold' >Fecha Transacción</td>" +
                                        "<td width='7.5%' style='font-size:10px;font-weight:bold'>N° OP/N° Cheque</td>" +
                                        "<td width='4%' style='font-size:10px;font-weight:bold'>Md</td>" +
                                        "<td width='7.5%' style='font-size:10px;font-weight:bold'>Importe</td>" +
                                        "<td width='5%' style='font-size:10px;font-weight:bold'>Recibo</td>" +
                                        "<td width='5%' style='font-size:10px;font-weight:bold'>Aprob</td>" +
                                        "</tr>";
                        Double dCobranza=0.0;
                        for (int j = 0; j < cobranza_liquidacion_adapter.lst.size(); j++) {
                            dCobranza +=cobranza_liquidacion_adapter.lst.get(j).getM_COBRANZA();
                            htmToDet = htmToDet + "<tr><td width='7.5%' style='font-size:10px' align='left' >" + cobranza_liquidacion_adapter.lst.get(j).getCOD_CLIENTE().toString() + "</td>" +
                                    "<td width='7.5%' style='font-size:10px'>" + cobranza_liquidacion_adapter.lst.get(j).getRUC().toString() + "</td>" +
                                    "<td width='15%' style='font-size:10px'>" +  cobranza_liquidacion_adapter.lst.get(j).getRAZON_SOCIAL().toString() + "</td>" +
                                    "<td width='5%' style='font-size:10px'>" +   cobranza_liquidacion_adapter.lst.get(j).getTIPODOC().toString() + "</td>" +
                                    "<td width='10%' style='font-size:10px'>" +  cobranza_liquidacion_adapter.lst.get(j).getNUMERO().toString() + "</td>" +
                                    "<td width='6%' style='font-size:10px'>" +   cobranza_liquidacion_adapter.lst.get(j).getFPAGODESC().toString() + "</td>" +
                                    "<td width='10%' style='font-size:10px'>" +  cobranza_liquidacion_adapter.lst.get(j).getNOMCTACORRIENTE().toString() + "</td>" +
                                    "<td width='10%' style='font-size:10px' >" + cobranza_liquidacion_adapter.lst.get(j).getFECHA().toString() + "</td>" +
                                    "<td width='7.5%' style='font-size:10px'>" + cobranza_liquidacion_adapter.lst.get(j).getNUMCHEQ().toString() + "</td>" +
                                    "<td width='4%' style='font-size:10px'>" +   cobranza_liquidacion_adapter.lst.get(j).getMONEDA().toString() + "</td>" +
                                    "<td width='7.5%' style='font-size:10px'>" + funciones.FormatDecimal(cobranza_liquidacion_adapter.lst.get(j).getM_COBRANZA().toString()) + "</td>" +
                                    "<td width='5%' style='font-size:10px'>" +   cobranza_liquidacion_adapter.lst.get(j).getRECIBO().toString() + "</td>" +
                                    "<td width='5%' style='font-size:10px'>" +   cobranza_liquidacion_adapter.lst.get(j).getID_COBRADOR().toString() + "</td>" +
                                    "</tr>";
                        }
                        htmToDet = htmToDet + "<tr><td colspan='10' style='font-weight:bold' align='right' >TOTAL DE PLANILLA" + cobranza_liquidacion_adapter.lst.get(0).getPLANILLA().toString() + "</td>" + "<td width='7.5%' style='font-size:12px' align='right'  >" +  funciones.FormatDecimal(dCobranza.toString()) + "</td>" + "</tr>";

                        htmPie = "</table>" +
                                "</body>" +
                                "</head>" +
                                "</html>";

                        htmTotalGeneral = "<table width='88.5%' border=0 cellspacing=0 bordercolor='666633'>" +
                                "<tr><td  style='font-weight:bold;background:#FFDDDD' align='center' >TOTAL GENERAL</td>" + "<td style='font-weight:bold;background:#FFDDDD' width='7.5%'>1600.20</td>" + "</tr></table>";

                        String sResumen =
                                "<table width='100%'>" +
                                        "<tr width='100%'><td></td></tr>" +
                                        "<tr width='100%'>" +
                                        "<td width='100%' style='font-weight:bold;align='center''>RESUMEN DE COBRANZAS</td></tr>" +
                                        "</table>" +
                                        "<table width='100%' border=0.01 cellspacing=0 cellpadding=5 bordercolor='666633'>" +
                                        "<tr><td width='7.5%' align='left' style='font-size:10px;font-weight:bold' >PLANILLA</td>" +
                                        "<td width='12%' style='font-size:10px;font-weight:bold'>FORMA PAGO</td>" +
                                        "<td width='40%' style='font-size:10px;font-weight:bold'>EFECTIVO/BANCO/CUENTA CORRIENTE</td>" +
                                        "<td width='12%' style='font-size:10px;font-weight:bold'>FECHA OP/CHEQUE</td>" +
                                        "<td width='12%' style='font-size:10px;font-weight:bold'>N°.OP/N°CHEQUE</td>" +
                                        "<td width='12%' style='font-size:10px;font-weight:bold' >VOUCHER</td>" +
                                        "<td width='12%' style='font-size:10px;font-weight:bold' >MONTO TOTAL</td>" +
                                        "</tr></table>";

                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        Image imagen = Image.getInstance(stream.toByteArray());
                        imagen.scaleToFit(120, 100);
                        imagen.setBorderColor(BaseColor.WHITE);
                        imagen.setBackgroundColor(BaseColor.WHITE);
                        imagen.setAlignment(Chunk.ALIGN_LEFT);
                        document.add(imagen);

                        PDF = htmToCab + htmToDet + htmPie + htmTotalGeneral + sResumen;
                        worker.parseXHtml(pdfWriter, document, new StringReader(PDF));
                        if (iValorEnviar == 1) {
                            //1 EMAIL, 2 WHATSAPP
                            EnviarDocumento(1);
                        } else {
                            muestraPDF(nombre_completo, getApplication());
                        }
                    }else{
                        Mensaje("No hay registros.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                document.close();

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;

        }

        public void muestraPDF(String archivo, Context context) {

            File file = new File(archivo);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Mensaje("No Tiene ninguna aplicación para abrir el archivo");
            }
        }

        private void EnviarDocumento(Integer iOpcion){
            try {
                String to="",cc="",asunto="asunto",mensaje="mensaje";
                File file = new File(nombre_completo);

                //Email
                if (iOpcion==1){
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                    emailIntent.putExtra(Intent.EXTRA_CC, cc);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    emailIntent.setType("application/pdf"); //indicamos el tipo de dato
                    emailIntent.setPackage("com.google.android.gm");
                    if (emailIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(Intent.createChooser(emailIntent, "Email "));
                    }else {
                        Toast.makeText(Activity_Liquidacion.this,"Por favor instale email",Toast.LENGTH_LONG).show();
                    }
                }
                //whatsapp
                if (iOpcion==2){
                    Intent whatsappsend = new Intent();
                    whatsappsend.setAction(Intent.ACTION_SEND);
                    whatsappsend.putExtra(Intent.EXTRA_TEXT, mensaje);
                    whatsappsend.putExtra("jid", "941895433" +"@s.whatsapp.net");
                    whatsappsend.setType("application/pdf"); //indicamos el tipo de dato
                    //whatsappsend.setType("text/plain");
                    whatsappsend.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    whatsappsend.setPackage("com.whatsapp");
                    if (whatsappsend.resolveActivity(getPackageManager()) != null) {
                        startActivity(whatsappsend);
                    }else {
                        Toast.makeText(Activity_Liquidacion.this,"Por favor instale whatsapp",Toast.LENGTH_LONG).show();
                    }

                }

            }
            catch (Exception e){
                new ToastLibrary(Activity_Liquidacion.this,e.getMessage()).Show();
            }
        }


        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }
        @Override
        protected void onPostExecute(JSONObject result){
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void Cargar(){
        Integer iValidar=0;
        try {

            lq_chktodos.setChecked(false);

            String sFecha = "";
            if ( lq_txtffecha != null && !lq_txtffecha.toString().trim().equals("")) {
                sFecha = lq_txtffecha.toString().substring(6)
                        + lq_txtffecha.toString().substring(3, 5)
                        + lq_txtffecha.toString().substring(0, 2);
            }


            if(sFormaPago == null || sFormaPago.equals("") ||sFormaPago.equals("0"))
            {
                sFormaPago = "XXX";
            }

            if(sRazonSocialCliente==null || sRazonSocialCliente.equals("") || sRazonSocialCliente.equals("0"))
            {
                sRazonSocialCliente = "XXX";
            }

            new LoadLiquidacionSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_LOCAL", "0").toString(),
                    sFecha,
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    sEstado,
                    sNroPlanilla,
                    sCPacking,
                    sFormaPago,
                    sRazonSocialCliente
            );


        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if(iValidar==0){
               /*
               lq_lnFiltro.setVisibility(View.GONE);
                lq_lblfiltro.setTag("Ver Filtro");
                lq_lblfiltro.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_expand, 0, 0);
                */
            }

        }
    }

    View.OnClickListener OnClickListenercl_lq_lblbuscar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Cargar();
        }
    };

    private Boolean Validar(){
        if (lq_txttipopago.toString().trim().equals("")){
            Mensaje("Seleccione el forma de pago.");
            return false;
        }
        String fpago= lq_txttipopago.toString().trim();
        if (!fpago.equals("E") &&  !fpago.equals("Z")){
            if (lq_txtccbanco.toString().trim().equals("")){
                Mensaje("Seleccione banco.");
                return false;
            }
        }
        if(!fpago.equals("E") && lq_txtfecha.toString().trim().equals("") )  {
            Mensaje("Seleccione Fecha.");
            return false;
        }
        //TARJETA DE CREDTO
        if(fpago.equals("D") || fpago.equals("V") || fpago.equals("M")  || fpago.equals("S")  || fpago.equals("I")|| fpago.equals("H")  ) {
            if(lq_txtfecha.toString().trim().equals("") || lq_txtnroope.toString().trim().equals(""))  {
                Mensaje("Ingrese Número de Tarjeta de credito y fecha de deposito.");
                return false;
            }
        }
        //DEPOSITO EN BANCO
        if(fpago.equals("P")) {
            if(lq_txtfecha.toString().trim().equals("") || lq_txtnroope.toString().trim().equals(""))  {
                Mensaje("Ingrese Número de operación y fecha de deposito.");
                return false;
            }
        }
        //CHEQUE
        if(fpago.equals("C") ) {
            if(lq_txtfecha.toString().trim().equals("") || lq_txtnroope.toString().trim().equals(""))  {
                Mensaje("Ingrese Número de cheque y fecha.");
                return false;
            }
        }
        return true;
    }

    View.OnClickListener OnClickListenercl_lq_txtfecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iOpcionFecha=2;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getSupportFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_lq_txtfestado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=100;
                iAuxiliar=1;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Liquidacion.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
    View.OnClickListener OnClickListenercl_lq_txttipopago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=14;
                iAuxiliar=2;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Liquidacion.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
    View.OnClickListener OnClickListenercl_lq_txtccbanco = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=200;
                iAuxiliar=3;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Liquidacion.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btnConciliar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iEvento=2;
                if(sEstado.equals("40003")) {
                    String sMensaje = "¿Desea conciliar los documentos?";
                    dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                    dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_Liquidacion.this, sMensaje);
                    dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
                    dialog_fragment_confirmar.isCancelable();
                }
                else{
                    Mensaje("No se puede conciliar documentos ya enviados");

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planilla, menu);
        final MenuItem ic_action_enviar = menu.findItem(R.id.action_enviar);
        final MenuItem ic_action_pdf = menu.findItem(R.id.action_pdf);

        ic_action_enviar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return  EnviarPlanilla();

            }
        });

        ic_action_pdf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                iValorEnviar=1;
                new GeneraPDFAsyncTask().execute("");
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/


    private boolean EnviarPlanilla(String fecha) {
        iEvento = 3;
        lq_txtffecha = fecha;
        if(sEstado.equals("40003")){

            //(usuario.ROL == (int)EnumRoles.LiquidadorCobranzaDespacho || usuario.ROL == (int)EnumRoles.RegistradorPedidos)
            if(sharedSettings.getString("ROL", "").toString().equals("130019") || sharedSettings.getString("ROL", "").toString().equals("130008"))
            {
                if(txtPackingEnvio.getText()== null || txtPackingEnvio.getText().toString().trim().equals("") ||txtPackingEnvio.getText().toString().trim().equals("0"))
                {
                    txtPackingEnvio.requestFocus();
                    Mensaje("Debe ingresar una planilla de despacho");
                    return false;
                }
                else {
                    sCPacking = txtPackingEnvio.getText().toString();
                }
            }

                if (lq_txtffecha != null && !lq_txtffecha.toString().trim().equals("")) {
                    String sMensaje = "¿Desea enviar la planilla del " + lq_txtffecha.toString().trim();
                    dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                    dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_Liquidacion.this, sMensaje);
                    dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
                    dialog_fragment_confirmar.isCancelable();
                } else {
                    Mensaje("Seleccione Fecha para poder enviar los documentos.");
                    BuscarLiquidacion();
                }

        }
        else
        {
            Mensaje("Los documentos ya fueron enviados");
        }
        return false;
    }

    @Override
    public void onConfirmacionSI() {
        Boolean iValor=false;
        String fPago="";

        //CONCILIAR DOCUMENTOS
        if (iEvento==2) {
            String sFecha = "";

            if (lq_txtffecha != null && !lq_txtffecha.toString().equals(""))
            {
                sFecha=  lq_txtffecha.toString().substring(6)
                        + lq_txtffecha.toString().substring(3, 5)
                        + lq_txtffecha.toString().substring(0, 2);
            }
            else
            {
                sFecha =  "17530101";
            }

            String sPACKING="0";

            if(sCPacking!=null && !sCPacking.equals("")) {
                sPACKING = sCPacking.trim();
            }
            new ConciliarDepositosCobranzaAsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_ConciliarDepositos + "/" +
                            sFecha + "/" +
                            sharedSettings.getString("iID_VENDEDOR", "0").toString() + "/" +
                            "40003"+ "/" +
                            sharedSettings.getString("USUARIO", "").toString() + "/" +
                            sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                            sPACKING
            );
        }

        //GENERA PLANILLA Y ENVIA LOS DOCUMENTOS
        if (iEvento==3) {
            String sFecha = lq_txtffecha.toString().substring(6)
                    + lq_txtffecha.toString().substring(3, 5)
                    + lq_txtffecha.toString().substring(0, 2);

            String sPACKING="0";

            if(sCPacking!=null && !sCPacking.trim().equals("")) {
                sPACKING = sCPacking.trim();
            }


            if(documentos_cobra_cabDAO.lst!=null && documentos_cobra_cabDAO.lst.size()>0)
            { String fpago, fechaRecibo;
                for(int i=0; i<documentos_cobra_cabDAO.lst.size(); i++)
                {
                    fpago = documentos_cobra_cabDAO.lst.get(i).getCODIGO_FPAGO();
                    fechaRecibo = documentos_cobra_cabDAO.lst.get(i).getFECHA_RECIBO();

                    if(fechaRecibo.equals(lq_txtffecha.toString()) && ( fpago.equals("P") || fpago.equals("V") || fpago.equals("D") || fpago.equals("M") || fpago.equals("I") || fpago.equals("H") || fpago.equals("S")))
                    {
                        if(!documentos_cobra_cabDAO.lst.get(i).getESTADO_CONCILIADO().equals("40025"))
                        {
                            Mensaje("No se pueden enviar documentos sin conciliar");
                            return;
                        }
                    }
                }
            }


            new GenerarPlanillaCobranzaAsyncTask().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_GeneraPlanilla + "/" +
                            sFecha + "/" +
                            sharedSettings.getString("iID_VENDEDOR", "0").toString() + "/" +
                            "40003"+ "/" +
                            sharedSettings.getString("USUARIO", "").toString() + "/" +
                            sharedSettings.getString("C_PERFIL", "").toString() + "/" +
                            sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                            sPACKING + "/" +
                            sharedSettings.getString("sIMEI", sharedSettings.getString("NOMBRE_TELEFONO", "").toString()).toString() + "/" +
                            sharedSettings.getString("NOMBRE_TELEFONO", "").toString()
            );

        }
    }

    @Override
    public void onConfirmacionNO() {

    }


    @Override
    public void onTablaAuxiliarSI() {

    }

    private class LoadLiquidacionSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getLiquidacionBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8]);
                documentos_cobra_cabDAO2.getDepositosBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8]);
                documentos_cobra_cabDAO3.getTarjetasBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8]);
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

                cobranza_liquidacion_adapter = new Cobranza_Liquidacion_Adapter(getBaseContext(), 0, documentos_cobra_cabDAO.lst);
                cobranza_liquidacion_adapter.notifyDataSetChanged();
                lq_lsdetalle.setAdapter(cobranza_liquidacion_adapter);

                dMontoDeposito=0.0;
                dMontoCheque=0.0;
                dMontoVisa=0.0;
                dMontoEfectivo=0.0;
                dMontoBancarizado=0.0;
                dMontoGeneral=0.0;
                String fPago="";

                for (int j = 0; j < cobranza_liquidacion_adapter.lstFiltrado.size(); j++) {

                    fPago=cobranza_liquidacion_adapter.lstFiltrado.get(j).getFPAGO().toString().trim();

                    //Deposito
                    if(fPago.equals("P")){
                        dMontoDeposito+=Double.valueOf(cobranza_liquidacion_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                    }

                    //CHEQUE
                    if(fPago.equals("C")){
                        dMontoCheque+=Double.valueOf(cobranza_liquidacion_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                    }
                    //VISA
                    if(fPago.equals("D") || fPago.equals("V") || fPago.equals("M")  || fPago.equals("S")  || fPago.equals("I") || fPago.equals("H")  ) {
                        dMontoVisa+=Double.valueOf(cobranza_liquidacion_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                    }
                    //EFECTIVO
                    if(fPago.equals("E")){
                        dMontoEfectivo+=Double.valueOf(cobranza_liquidacion_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                    }
                    //BANCARIZADO
                    if(fPago.equals("Z")){
                        dMontoBancarizado+=Double.valueOf(cobranza_liquidacion_adapter.lstFiltrado.get(j).getM_COBRANZA().toString());
                    }
                }
                dMontoGeneral=(dMontoCheque+dMontoVisa+dMontoEfectivo+dMontoBancarizado+dMontoDeposito);

                lq_lblmontoc = ("S/ " +funciones.FormatDecimal(String.valueOf(dMontoCheque).trim().replace(",","")));
                lq_lblmontov = ("S/ " +funciones.FormatDecimal(String.valueOf(dMontoVisa).trim().replace(",","")) );
                lq_lblmontoe = ("S/ " +funciones.FormatDecimal(String.valueOf(dMontoEfectivo).trim().replace(",","")) );
                lq_lblmontob = ("S/ " +funciones.FormatDecimal(String.valueOf(dMontoBancarizado).trim().replace(",","")) );
                lq_lblmontop = ("S/ " +funciones.FormatDecimal(String.valueOf(dMontoDeposito).trim().replace(",","")) );
                lq_lbltotalg.setText("S/ " + funciones.FormatDecimal(String.valueOf(dMontoGeneral).trim().replace(",","")) );

            } catch (Exception ex) {
                //Toast.makeText(getApplication(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ConciliarDepositosCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            MostrarLoading("Conciliando",true);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getConciliarDepositos(p[0]);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }


        @Override
        protected void onPostExecute(JSONObject result) {

            MostrarLoading("",false);
            Cargar();
            try {
                Mensaje(result.getString("MSG").toString());
            }catch(Exception ex){
                if(!ex.getMessage().equals("No value for MSG"))
                {
                    Mensaje(ex.getMessage());
                    //Mensaje("El servidor se encuentra ocupado, por favor inténtelo en unos minutos");
                   // Mensaje("No se encontraron documentos pendientes de conciliar");
                }

            }
        }
    }

    public class GenerarPlanillaCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {

            MostrarLoading("Enviando",true);
        }


        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getGeneraPlanilla(p[0]);
        }

        @Override
        protected void onProgressUpdate(String... prog) {
            super.onProgressUpdate(prog);
        }


        @Override
        protected void onPostExecute(JSONObject result) {

            MostrarLoading("",false);
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    Mensaje(result.getString("MSG").toString());
                } else {
                    Mensaje(result.getString("MSG").toString());

                    if(result.getString("N_PLANILLA").toString()!= null && !result.getString("N_PLANILLA").toString().equals("0"))
                    {
                        //Se comento porque demora mucho en generar la planilla y a veces no la genera correctamente.

                      /*
                        editor_Shared.putString("REP_N_PLANILLA",result.getString("N_PLANILLA").toString());
                        editor_Shared.putString("IOPCION_REPORTE", "0");
                        editor_Shared.commit();

                        Intent intent = new Intent(getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                      */
                    }

                    Cargar();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private class UpdateCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;
        private String ID_COBRANZA,TIPO_EVENTO;

        public UpdateCobranzaAsyncTask(String ID_COBRANZA,String TIPO_EVENTO) {
            this.ID_COBRANZA=ID_COBRANZA;
            this.TIPO_EVENTO=TIPO_EVENTO;
        }

        @Override
        protected void onPreExecute() {

            MostrarLoading("Asignando",true);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.UpdateRest(ID_COBRANZA,TIPO_EVENTO,p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {



            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (result.getInt("status")==0) {
                    new ToastLibrary(Activity_Liquidacion.this, "Ocurrio un error al momento de reasignar los registros, intentelo nuevamente").Show();

                } else {
                    new ToastLibrary(Activity_Liquidacion.this, "Asignacion exitosa").Show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {

            }


            MostrarLoading("",false);
        }
    }

    private void MostrarLoading(String texto, boolean mostrar) {

        if(mostrar) {
            txtProgressLoading.setText(texto);
            txtProgressLoading.setVisibility(View.VISIBLE);
            pbLiquidacion.setVisibility(View.VISIBLE);
            lq_lsdetalle.setVisibility(View.GONE);
        }
        else
        {
            txtProgressLoading.setText("");
            txtProgressLoading.setVisibility(View.GONE);
            pbLiquidacion.setVisibility(View.GONE);
            lq_lsdetalle.setVisibility(View.VISIBLE);
        }

    }


    private void Mensaje(String msj){
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Activity_Liquidacion.this);
        log_dialogaceptar.show(getSupportFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

    @Override
    public void onAceptar() {
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cargar();
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

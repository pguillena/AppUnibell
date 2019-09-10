package pe.com.app.unibell.appunibell.Liquidacion;

import android.app.DialogFragment;
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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;

import pe.com.app.unibell.appunibell.AD.Cobranza_Liquidacion_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.Clientes.Activity_FiltroClientes;
import pe.com.app.unibell.appunibell.Clientes.Activity_clientes;
import pe.com.app.unibell.appunibell.Cobranza.Fragment_Cobranza;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Cobranza_Liquidacion_Rep;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

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
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();

    private Documentos_Cobra_CabBE documentos_cobra_cabBE;
    private Documentos_Cobra_MovBE documentos_cobra_movBE;

    private Cobranza_Liquidacion_Adapter cobranza_liquidacion_adapter = null;
    private Cobranza_Liquidacion_Adapter cobranza_liquidacion_adapter2 = null;
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
    private String sNroPlanilla, sCPacking, sEstado="40003", sFecha;
    private String lq_lblmontoc,lq_lblmontov,lq_lblmontoe,lq_lblmontob, lq_lblmontop;

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

            lq_chktodos.setOnCheckedChangeListener(lq_chktodos_ChangeListener);
            FloatingActionButton fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
            fabBuscar.setOnClickListener(OnClickListener_fabBuscar);

            Button btnAsignar = (Button) findViewById(R.id.btnAsignar);
            Button btnEnvio = (Button) findViewById(R.id.btnEnvio);
            TextView btnVerDetalle = (TextView) findViewById(R.id.btnVerDetalle);

            btnAsignar.setOnClickListener(OnClickListener_btnAsignar);
            btnEnvio.setOnClickListener(OnClickListener_btnEnvio);
            btnVerDetalle.setOnClickListener(OnClickListener_btnVerDetalle);

            ColorStateList cs3 = new ColorStateList(new int[][]{new int[0]}, new int[] {0xff78ae6e});

            Button btnConciliar = (Button) findViewById(R.id.btnConciliar);
            btnConciliar.setOnClickListener(OnClickListenercl_btnConciliar);

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplication());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            Cargar();

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
                        cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(!cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO());
                        cobranza_liquidacion_adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < cobranza_liquidacion_adapter.getCount(); i++) {
                            if (cobranza_liquidacion_adapter.lst.get(i).getCHKMARCADO()) {
                                CantidadSeleccionado = CantidadSeleccionado + 1;
                            }
                        }

                        if (CantidadSeleccionado > 0) {
                            cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(!cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO());
                            cobranza_liquidacion_adapter.notifyDataSetChanged();
                        }

                    }

                }
            }


        });

    }

    AdapterView.OnItemLongClickListener OnItemLongClickListener_lq_lsdetalle = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
            // TODO Auto-generated method stub

            if(cobranza_liquidacion_adapter.lst.get(position).getESTADO().toString().equals("40003")) //SOLO SE PUEDE REASIGNAR SI EL ESTADO ESTA COMO REGISTRADO
            {
                cobranza_liquidacion_adapter.lst.get(position).setCHKMARCADO(!cobranza_liquidacion_adapter.lst.get(position).getCHKMARCADO());
                cobranza_liquidacion_adapter.notifyDataSetChanged();

            }

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

            iOpcionFecha = 3;//EnviarPlanilla

            try {
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");

            } catch (Exception ex) {
                ex.printStackTrace();
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

            if(cobranza_liquidacion_adapter.lst.get(j).getESTADO().toString().equals("40003")) //SOLO SE PUEDE REASIGNAR SI EL ESTADO ESTA COMO REGISTRADO
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

    }


    AbsListView.OnItemClickListener lq_lsdetalle_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };



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


            new LoadLiquidacionSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_LOCAL", "0").toString(),
                    sFecha,
                    sharedSettings.getString("iID_VENDEDOR", "0").toString(),
                    sEstado,
                    sNroPlanilla,
                    sCPacking
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
                dialogFragmentFecha.show(getFragmentManager(), "");

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
            if(lq_txtffecha != null && !lq_txtffecha.toString().trim().equals("")) {
                String sMensaje = "¿Desea enviar la planilla del " + lq_txtffecha.toString().trim();
                dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_Liquidacion.this, sMensaje);
                dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
                dialog_fragment_confirmar.isCancelable();
            }else{
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
            { String fpago;
                for(int i=0; i<documentos_cobra_cabDAO.lst.size(); i++)
                {
                    fpago = documentos_cobra_cabDAO.lst.get(i).getCODIGO_FPAGO();
                    if(fpago.equals("P") || fpago.equals("V") || fpago.equals("D") || fpago.equals("M") || fpago.equals("I") || fpago.equals("H") || fpago.equals("S"))
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
                            lq_txtffecha.toString().trim() + "/" +
                            sharedSettings.getString("iID_VENDEDOR", "0").toString() + "/" +
                            "40003"+ "/" +
                            sharedSettings.getString("USUARIO", "").toString() + "/" +
                            sharedSettings.getString("C_PERFIL", "").toString() + "/" +
                            sharedSettings.getString("iID_EMPRESA", "0").toString() + "/" +
                            sharedSettings.getString("iID_LOCAL", "0").toString() + "/" +
                            sPACKING + "/" +
                            sharedSettings.getString("ROL", "").toString() + "/" +
                            lq_txtffecha.toString() + "/" +
                            lq_txtffecha.toString() + "/" +
                            "XXX"
            );
        }
    }


    @Override
    public void onTablaAuxiliarSI() {

    }

    private class LoadLiquidacionSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                documentos_cobra_cabDAO.getLiquidacionBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6]);
                documentos_cobra_cabDAO2.getDepositosBy(p[0],p[1],p[2],p[3],p[4],p[5],p[6]);
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
            try {
                if (result.getInt("status")!=1) {
                    Mensaje(result.getString("MSG").toString());
                } else {
                    Mensaje(result.getString("MSG").toString());
                    Cargar();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class GenerarPlanillaCobranzaAsyncTask extends AsyncTask<String, String, JSONObject> {
        /*DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
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
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    Mensaje(result.getString("MSG").toString());
                } else {
                    Mensaje(result.getString("MSG").toString());

                    if(result.getString("N_PLANILLA").toString()!= null && !result.getString("N_PLANILLA").toString().equals("0"))
                    {
                        editor_Shared.putString("REP_N_PLANILLA",result.getString("N_PLANILLA").toString());
                        editor_Shared.putString("IOPCION_REPORTE", "0");
                        editor_Shared.commit();

                        Intent intent = new Intent(getApplicationContext(), Activity_Cobranza_Liquidacion_Rep.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.UpdateRest(ID_COBRANZA,TIPO_EVENTO,p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            try {
                if (result.getInt("status")==0) {
                } else {

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {

            }
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

}

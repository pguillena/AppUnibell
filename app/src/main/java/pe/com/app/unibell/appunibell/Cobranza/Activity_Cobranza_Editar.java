package pe.com.app.unibell.appunibell.Cobranza;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import pe.com.app.unibell.appunibell.AD.Cobranza_Cabecera_Adapter;
import pe.com.app.unibell.appunibell.AD.ParTabla_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.DAO.ParTablaDAO;
import pe.com.app.unibell.appunibell.DAO.Recibos_CcobranzaDAO;
import pe.com.app.unibell.appunibell.DAO.S_gem_TipoCambioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_Cobranza_Editar
        extends AppCompatActivity
        implements Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener
        {

    private EditText ch_lblnumero;
    private TextView ch_lbl1,ch_lbl2,ch_lbl3,ch_lblbanco,ch_lblfpago;
    private TextView ch_btnregistrar,ch_lblfplanilla, txtFechaRecibo;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private DialogFragment dialogFragmentFecha;
    private Cobranza_Cabecera_Adapter cobranza_cabecera_adapter=null;
    private Recibos_CcobranzaDAO recibos_ccobranzaDAO = new Recibos_CcobranzaDAO();
    private S_gem_TipoCambioDAO s_gem_tipoCambioDAO = new S_gem_TipoCambioDAO();
    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO=new Documentos_Cobra_CabDAO();
    private ParTablaDAO parTablaDAO = new ParTablaDAO();
            private ParTabla_Adapter parTabla_adapter = null;
            public Integer   iMinDate = 0;;
    private Integer iAuxiliar = 0,iTabla=0;
    private  String EDITAR_TPAGO="";
    private  int iFecha=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_editar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
        getSupportActionBar().setSubtitle("EDITAR COBRANZA");

        ch_lblfpago = (TextView)findViewById(R.id.ch_lblfpago);
        ch_lbl1 = (TextView)findViewById(R.id.ch_lbl1);
        ch_lbl2 = (TextView)findViewById(R.id.ch_lbl2);
        ch_lbl3 = (TextView)findViewById(R.id.ch_lbl3);

        ch_lblbanco = (TextView)findViewById(R.id.ch_lblbanco);
        ch_lblnumero = (EditText)findViewById(R.id.ch_lblnumero);
        ch_lblfplanilla = (TextView) findViewById(R.id.ch_lblfplanilla);
        ch_btnregistrar = (TextView) findViewById(R.id.ch_btnregistrar);
        txtFechaRecibo = (TextView) findViewById(R.id.txtFechaRecibo);


        sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();


        EDITAR_TPAGO=sharedSettings.getString("EDITAR_TPAGO", "").toString();

        if(EDITAR_TPAGO.equals("TARJETA")){
            ch_lbl1.setText("Seleccione Cuenta Corriente");
            ch_lbl2.setText("Ingrese Cuatro últimos dígitos de la tarjeta");
            ch_lbl3.setText("Seleccione Fecha de Transacción");
        }
        if(EDITAR_TPAGO.equals("DEPOSITO")){
            ch_lbl1.setText("Seleccione Cuenta Corriente");
            ch_lbl2.setText("Ingrese Número de Operación");
            ch_lbl3.setText("Seleccione Fecha Deposito");
        }
        if(EDITAR_TPAGO.equals("CHEQUE")){
            ch_lbl1.setText("Seleccione Banco");
            ch_lbl2.setText("Ingrese Número de Cheque");
            ch_lbl3.setText("Seleccione Fecha de Cheque");
        }

        String sNNUMERO= sharedSettings.getString("sNNUMERO", "").toString();

        ch_lblfpago.setText(sharedSettings.getString("sFPAGODESC", "").toString());
        ch_lblfpago.setTag(sharedSettings.getString("COD_FPAGO", "").toString());

        ch_lblbanco.setTag(sharedSettings.getString("iID_BANCO", "0").toString());
        ch_lblbanco.setText(sharedSettings.getString("sBANCODESC", "").toString());
        ch_lblnumero.setText(sNNUMERO);
        ch_lblfplanilla.setText(sharedSettings.getString("sFECHA_DEPOSITO", "").toString());
        txtFechaRecibo.setText(sharedSettings.getString("sFECHA_RECIBO", "").toString());

        //EVENTOS
        ch_lblbanco.setOnClickListener(OnClickList_ch_lblbanco);
        ch_lblfplanilla.setOnClickListener(OnClickList_ch_lblfplanilla);
        ch_btnregistrar.setOnClickListener(OnClickListener_rp_btnregistrar);
        txtFechaRecibo.setOnClickListener(OnClickList_txtFechaRecibo);

    }

        View.OnClickListener OnClickList_ch_lblfplanilla = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFecha = 1;
                try {
                    dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                    dialogFragmentFecha.show(getSupportFragmentManager(), "");
                } catch (Exception e) {
                }
            }
        };


            View.OnClickListener OnClickList_txtFechaRecibo = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        iFecha=2;
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                        dataBaseHelper.createDataBase();
                        dataBaseHelper.openDataBase();
                        parTablaDAO.getByGroup("120000", "120025");

                        parTabla_adapter = new ParTabla_Adapter(getApplicationContext(), 0, parTablaDAO.lst);
                        parTabla_adapter.notifyDataSetChanged();

                        if(parTabla_adapter!=null){

                            for (int i = 0; i<parTabla_adapter.getCount(); i++) {
                                iMinDate =Integer.parseInt(parTabla_adapter.getItem(i).getVALORSUNAT());
                            }
                        }


                        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                        ((Dialogo_Fragment_Fecha) dialogFragmentFecha).iMinDate = iMinDate;
                        dialogFragmentFecha.show(getSupportFragmentManager(), "");
                    } catch (Exception e) {
                    }
                }
            };




            View.OnClickListener OnClickList_rp_lblfpago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar = 1;
                iTabla = 14;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Cobranza_Editar.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception e) {
            }
         }
    };

    View.OnClickListener OnClickList_ch_lblbanco = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //iTabla=200 CC/BANCOS
                iAuxiliar = 2;
                iTabla = 200;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Cobranza_Editar.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);


            } catch (Exception e) {
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTablaAuxiliarSI() {
        switch (iAuxiliar) {
            case 2:
                ch_lblbanco.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                ch_lblbanco.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
        }
    }

    View.OnClickListener OnClickListener_rp_btnregistrar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            try {
                if (ValidarGeneral()==false){return;}
                // TODO Auto-generated method stub
                Intent data = new Intent();
                editor_Shared.putString("edfplanilla",ch_lblfplanilla.getText().toString());
                editor_Shared.putString("ednumero",ch_lblnumero.getText().toString());
                editor_Shared.putString("edbanco",ch_lblbanco.getTag().toString());
                editor_Shared.commit();

                String sFPAGO=sharedSettings.getString("COD_FPAGO", "").toString();

                Documentos_Cobra_CabBE documentos_cobra_cabBE2 = new Documentos_Cobra_CabBE();
                documentos_cobra_cabBE2.setFPAGO(sFPAGO);

                documentos_cobra_cabBE2.setFECHA(txtFechaRecibo.getText().toString());

                //TARJETAS DE CRÉDITO
                if (sFPAGO.equals("D") || sFPAGO.equals("V") || sFPAGO.equals("M") || sFPAGO.equals("S") || sFPAGO.equals("I")|| sFPAGO.equals("H")) {
                    documentos_cobra_cabBE2.setFECHA_DEPOSITO(ch_lblfplanilla.getText().toString());
                    documentos_cobra_cabBE2.setN_TARJETA(ch_lblnumero.getText().toString().trim());
                    documentos_cobra_cabBE2.setCTACORRIENTE_BANCO(ch_lblbanco.getTag().toString().trim());
                }
                //DEPOSITO EN BANCO
                if (sFPAGO.equals("P")) {
                    documentos_cobra_cabBE2.setFECHA_DEPOSITO(ch_lblfplanilla.getText().toString());
                    documentos_cobra_cabBE2.setNRO_OPERACION(ch_lblnumero.getText().toString().trim());
                    documentos_cobra_cabBE2.setCTACORRIENTE_BANCO(ch_lblbanco.getTag().toString().trim());
                }

                //CHEQUE
                if (sFPAGO.equals("C")) {
                    documentos_cobra_cabBE2.setFECCHEQ(ch_lblfplanilla.getText().toString());
                    documentos_cobra_cabBE2.setNUMCHEQ(ch_lblnumero.getText().toString().trim());
                    documentos_cobra_cabBE2.setID_BANCO(Integer.valueOf(ch_lblbanco.getTag().toString().trim()));
                }

                documentos_cobra_cabBE2.setID_COBRANZA(Integer.valueOf(sharedSettings.getString("ID_COBRANZA", "0").toString()));

                documentos_cobra_cabBE2.setN_RECIBO(sharedSettings.getString("N_RECIBO", "0"));
                documentos_cobra_cabBE2.setN_SERIE_RECIBO(sharedSettings.getString("N_SERIE_RECIBO", "0"));

                documentos_cobra_cabDAO.updateDeposito(documentos_cobra_cabBE2);

                setResult(RESULT_OK, data);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private Boolean ValidarGeneral() {
        if (ch_lblbanco.getText().toString().trim().equals("")) {
            ch_lblbanco.requestFocus();
            Mensaje(ch_lbl1.getText().toString());
            return false;
        }
        if (ch_lblnumero.getText().toString().trim().equals("")) {
            ch_lblnumero.requestFocus();
            Mensaje(ch_lbl2.getText().toString());
            return false;
        }
        if (ch_lblfplanilla.getText().toString().trim().equals("")) {
            ch_lblfplanilla.requestFocus();
            Mensaje(ch_lbl3.getText().toString());
            return false;
        }
        return true;
    }

    private void Mensaje(String msj) {
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Activity_Cobranza_Editar.this);
        log_dialogaceptar.show(getSupportFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }
    @Override
    public void onAceptar() {

    }

    @Override
    public void setearFecha(String fecha) {

        if(iFecha == 1)
        {
            ch_lblfplanilla.setText(fecha);
        }
        else if (iFecha == 2)
        {
            txtFechaRecibo.setText(fecha);
        }

    }


    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

  }


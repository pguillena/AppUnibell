package pe.com.app.unibell.appunibell.Cobranza;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import pe.com.app.unibell.appunibell.AD.Cobranza_Cabecera_Adapter;
import pe.com.app.unibell.appunibell.AD.CtaBnco_Adapter;
import pe.com.app.unibell.appunibell.DAO.Recibos_CcobranzaDAO;
import pe.com.app.unibell.appunibell.DAO.S_gem_TipoCambioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Globals;

public class Activity_Cobranza_Agregar_Pago
        extends AppCompatActivity
        implements Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener{

    private EditText rp_txtserie,rp_txtnumero,rp_txtmonto;
    private TextView rp_lblfplanilla,rp_lblfpago,rp_lblbancoctacte,rp_lblcliente,rp_txttipocambio;
    private Button rp_btnregistrar;
    private Switch rp_swmoneda;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private DialogFragment dialogFragmentFecha;

    private Cobranza_Cabecera_Adapter cobranza_cabecera_adapter=null;
    private Recibos_CcobranzaDAO recibos_ccobranzaDAO = new Recibos_CcobranzaDAO();
    private S_gem_TipoCambioDAO s_gem_tipoCambioDAO = new S_gem_TipoCambioDAO();

    private Integer iAuxiliar = 0,iTabla=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_agregar_pago);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
        getSupportActionBar().setSubtitle("PROCESO DE COBRANZA");

        rp_txtserie = (EditText)findViewById(R.id.rp_txtserie);
        rp_txtnumero = (EditText)findViewById(R.id.rp_txtnumero);
        rp_txtmonto = (EditText)findViewById(R.id.rp_txtmonto);
        rp_btnregistrar = (Button) findViewById(R.id.rp_btnregistrar);

        rp_lblfplanilla = (TextView)findViewById(R.id.rp_lblfplanilla);
        rp_lblfpago = (TextView)findViewById(R.id.rp_lblfpago);
        rp_lblbancoctacte = (TextView)findViewById(R.id.rp_lblbancoctacte);
        rp_lblcliente = (TextView)findViewById(R.id.rp_lblcliente);
        rp_txttipocambio = (TextView)findViewById(R.id.rp_txttipocambio);
        rp_swmoneda = (Switch) findViewById(R.id.rp_swmoneda);

        //rp_tbmoneda = (ToggleButton) findViewById(R.id.rp_tbmoneda);

        rp_lblfpago.setTag("0");
        rp_lblbancoctacte.setTag("0");
        //Valore por Default
        rp_txtmonto.setText("");
        rp_txtserie.setText("1");
        rp_txtnumero.setText("817151");

        sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

        rp_lblcliente.setText(sharedSettings.getString("RAZON_SOCIAL", "").toString());
        rp_lblcliente.setTag(sharedSettings.getString("CODIGO_ANTIGUO", "").toString());

        //Cargamos las cobranzas registradas hasta el momento
       /*
        Globals g = (Globals) getApplication();
        cobranza_cabecera_adapter  = (Cobranza_Cabecera_Adapter) g.getIntentCobranzaCab();
        */
        rp_lblfpago.setOnClickListener(OnClickList_rp_lblfpago);
        rp_lblfplanilla.setOnClickListener(OnClickList_rp_lblfplanilla);
        rp_lblbancoctacte.setOnClickListener(OnClickList_rp_lblbancoctacte);

        rp_btnregistrar.setOnClickListener(OnClickListener_rp_btnregistrar);

        rp_swmoneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2;
                if (rp_swmoneda.isChecked())
                    rp_swmoneda.setText("Dolares");
                else
                    rp_swmoneda.setText("Soles");
            }
        });

    }

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


    View.OnClickListener OnClickList_rp_lblfpago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar = 1;
                iTabla = 14;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Cobranza_Agregar_Pago.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_rp_lblbancoctacte = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //iTabla=200 CC/BANCOS
                iAuxiliar = 2;
                iTabla = 200;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Cobranza_Agregar_Pago.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);


            } catch (Exception e) {
            }
        }
    };

    @Override
    public void onTablaAuxiliarSI() {
        switch (iAuxiliar) {
            case 1:
                rp_lblfpago.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                rp_lblfpago.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                rp_lblbancoctacte.setTag("0");
                rp_lblbancoctacte.setText("");
                if (rp_lblfpago.getTag().toString().trim().equals("E") || rp_lblfpago.getTag().toString().trim().equals("Z")) {
                    rp_lblbancoctacte.setEnabled(false);
                } else {
                    rp_lblbancoctacte.setEnabled(true);
                }
                break;
            case 2:
                rp_lblbancoctacte.setTag(sharedSettings.getString("ICODTABAUX", "").toString().toUpperCase());
                rp_lblbancoctacte.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
        }
    }


    View.OnClickListener OnClickList_rp_lblfplanilla = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickListener_rp_btnregistrar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            try {
                if (ValidarGeneral()==false){return;}
                // TODO Auto-generated method stub
                Intent data = new Intent();
                editor_Shared.putString("cpserie",rp_txtserie.getText().toString());
                editor_Shared.putString("cpnumero",rp_txtnumero.getText().toString());
                editor_Shared.putString("cpfplanilla", rp_lblfplanilla.getText().toString());
                editor_Shared.putString("cpfpago", rp_lblfpago.getTag().toString());
                editor_Shared.putString("cpbancoctacte",rp_lblbancoctacte.getTag().toString());
                editor_Shared.putString("cpmonto", rp_txtmonto.getText().toString());
                editor_Shared.putString("cptipocambio",rp_txttipocambio.getText().toString());
                if(rp_swmoneda.isChecked()) {
                    editor_Shared.putString("cpmoneda", "$");
                }else{
                    editor_Shared.putString("cpmoneda", "S");
                }
                editor_Shared.commit();
                setResult(RESULT_OK, data);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };


    private Boolean ValidarGeneral() {
        String sMoneda = "S", sDescripcionMoneda = "soles";

        if(rp_swmoneda.isChecked()) {
            sMoneda = "$";
            sDescripcionMoneda = "Dolares";
        }else{
            sMoneda = "S";
            sDescripcionMoneda = "Soles";
        }

        if (rp_txtserie.getText().toString().trim().equals("")) {
            Mensaje("Ingrese serie de decumento");
            return false;
        }
        if (rp_txtnumero.getText().toString().trim().equals("")) {
            Mensaje("Ingrese número de documento");
            return false;
        }
        if (rp_lblfplanilla.getText().toString().trim().equals("")) {
            Mensaje("Seleccione Fecha");
            return false;
        }

        String fpago = rp_lblfpago.getTag().toString().trim();
        if (fpago.equals("")) {
            Mensaje("Seleccione el forma de pago");
            return false;
        }

        if (!fpago.equals("E") && !fpago.equals("Z")) {
            if (rp_lblbancoctacte.getText().toString().trim().equals("")) {
                if (fpago.equals("C")) {
                    Mensaje("Seleccione un banco");
                } else {

                    Mensaje("Seleccione una cuenta corriente");
                }
                return false;
            }
        } else {
            if (cobranza_cabecera_adapter != null && cobranza_cabecera_adapter.getCount() > 0) {
                for (int i = 0; i < cobranza_cabecera_adapter.getCount(); i++) {
                    if (cobranza_cabecera_adapter.getItem(i).getFPAGO().equals(fpago)) {
                        Mensaje("Ya existe la forma de pago");
                        return false;
                    }
                    if (fpago.equals("Z")) {
                        if (!cobranza_cabecera_adapter.getItem(i).getFPAGO().trim().equals(fpago)) {
                            Mensaje("La forma de pago bancarizacion, no se puede combinar con otra forma de pago");
                            return false;
                        }
                    }
                }
            }
        }

        if (fpago.equals("C")) {
            if (cobranza_cabecera_adapter != null && cobranza_cabecera_adapter.getCount() > 0) {
                for (int i = 0; i < cobranza_cabecera_adapter.getCount(); i++) {
                    if (cobranza_cabecera_adapter.getItem(i).getFPAGO().trim().equals(fpago)) {
                        Mensaje("Solo puede ingresar un cheque por recibo");
                        return false;
                    }
                }
            }
        }
        Double dmonto = 0.0;
        if (rp_txtmonto.getText().toString().trim().equals("")) {
            Mensaje("Ingrese monto válido");
        }

        dmonto = Double.valueOf(rp_txtmonto.getText().toString());

        if (dmonto <= 0.0 ) {
            Mensaje("Ingrese monto válido");
            return false;
        }
        CtaBnco_Adapter ca = ((Dialog_Fragment_Auxiliar) dialog_fragment_auxiliar).ctaBnco_adapter;
        if (!fpago.equals("C") && !fpago.equals("E") && !fpago.equals("Z")) {
            if (ca != null || ca.getCount() > 0) {
                for (int i = 0; i < ca.getCount(); i++) {
                    if (ca.getItem(i).getCODIGO().equals(rp_lblbancoctacte.getTag().toString().trim()) && !ca.getItem(i).getMONEDA().equals(sMoneda)) {
                        Mensaje("Debe elegir una cuenta en " + sDescripcionMoneda);
                        return false;
                    }
                }
            }
        }

        if (Integer.valueOf(sharedSettings.getString("VALIDA_RECIBO", "0").toString()) > 0) {
            Integer iValor = recibos_ccobranzaDAO.getValidar(
                    sharedSettings.getString("iID_VENDEDOR", "").toString(),
                    rp_txtserie.getText().toString().trim(),
                    rp_txtnumero.getText().toString().trim());

            if (iValor == 0) {
                recibos_ccobranzaDAO.getByRangoRecibos(
                        sharedSettings.getString("iID_VENDEDOR", "").toString(),
                        rp_txtserie.getText().toString().trim(),
                        rp_txtnumero.getText().toString().trim()
                );
                if (recibos_ccobranzaDAO.lst.size() > 0) {
                    String sRango = "";
                    for (int c = 0; c < recibos_ccobranzaDAO.lst.size(); c++) {
                        sRango = sRango + "De: " + recibos_ccobranzaDAO.lst.get(c).getN_NUMINI().toString() + " A " + recibos_ccobranzaDAO.lst.get(c).getN_NUMFIN().toString() + "\n";
                    }
                    Mensaje("El Número de recibo no esta en el rango asignado.\n" + sRango);
                }
                return false;
            }
        }
        return true;
    }


    private void Mensaje(String msj) {
        log_dialogaceptar = new Dialog_Fragment_Aceptar();
        log_dialogaceptar.setMensaje(msj);
        log_dialogaceptar.setAceptarDialogfragmentListener(Activity_Cobranza_Agregar_Pago.this);
        log_dialogaceptar.show(getSupportFragmentManager(), Dialog_Fragment_Aceptar.TAG);
    }

    @Override
    public void onAceptar() {

    }



    @Override
    public void setearFecha(String fecha) {
        rp_lblfplanilla.setText(fecha);

        //TIPO DE CAMBIO
        Double dTipocambio = 0.0;
        String sFecha = fecha.toString().substring(6)
                + fecha.toString().substring(3, 5)
                + fecha.toString().substring(0, 2);

        dTipocambio = s_gem_tipoCambioDAO.getAll(sFecha);
        rp_txttipocambio.setText(dTipocambio.toString());
        //FIN TIPO DE CAMBIO
        editor_Shared.putString("dTIPO_CAMBIO", dTipocambio.toString());
        editor_Shared.commit();

    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}

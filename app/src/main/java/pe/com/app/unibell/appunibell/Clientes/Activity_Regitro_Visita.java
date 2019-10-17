package pe.com.app.unibell.appunibell.Clientes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.DAO.VisitaDetDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Activity_Regitro_Visita  extends AppCompatActivity
        implements Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener {
    private TextView txtHoraInicio, txtHoraFin, txtNombreClienteVisita, ddlObjecionVenta, ddlObjecionCobranza, ddlObjecionOtros, ddlCambioDia, ddlCambioFrecuencia;
    private EditText txtOtros, txtCambioOrden, txtCambioCuadrante;
    private CheckBox chkVenta, chkCobranza, chkOtros, chkResultadoVenta, chkResultadoCobranza;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar = null;
    private SharedPreferences sharedSettings;
    private ArrayList<String> RazonSocialArray;
    private SharedPreferences.Editor editor_Shared;
    Funciones funciones = new Funciones();
    VisitaDetDAO visitaDetDAO = new VisitaDetDAO();
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    String sN_SEQUENCIA,sN_INFORME;
    Integer iTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_visita);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gestión de Visita");
        getSupportActionBar().setSubtitle("");

        sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

        txtHoraInicio = (TextView)findViewById(R.id.txtHoraInicio);
        txtHoraFin = (TextView)findViewById(R.id.txtHoraFin);
        txtNombreClienteVisita = (TextView)findViewById(R.id.txtNombreClienteVisita);

        ddlObjecionVenta = (TextView)findViewById(R.id.ddlObjecionVenta);
        ddlObjecionCobranza = (TextView)findViewById(R.id.ddlObjecionCobranza);
        ddlObjecionOtros = (TextView)findViewById(R.id.ddlObjecionOtros);


        ddlCambioDia = (TextView)findViewById(R.id.ddlCambioDia);
        ddlCambioFrecuencia = (TextView)findViewById(R.id.ddlCambioFrecuencia);

        chkVenta = (CheckBox)findViewById(R.id.chkVenta);
        chkCobranza = (CheckBox)findViewById(R.id.chkCobranza);
        chkOtros = (CheckBox)findViewById(R.id.chkOtros);
        chkResultadoVenta = (CheckBox)findViewById(R.id.chkResultadoVenta);
        chkResultadoCobranza = (CheckBox)findViewById(R.id.chkResultadoCobranza);


        txtOtros = (EditText)findViewById(R.id.txtOtros);
        txtCambioOrden = (EditText)findViewById(R.id.txtCambioOrden);
        txtCambioCuadrante = (EditText)findViewById(R.id.txtCambioCuadrante);


        ddlObjecionVenta.setOnClickListener(OnClickList_ddlObjecionVenta);
        ddlObjecionCobranza.setOnClickListener(OnClickList_ddlObjecionCobranza);
        ddlObjecionOtros.setOnClickListener(OnClickList_ddlObjecionOtros);



        ddlCambioDia.setOnClickListener(OnClickList_ddlCambioDia);
        ddlCambioFrecuencia.setOnClickListener(OnClickList_ddlCambioFrecuencia);

        chkResultadoCobranza.setOnClickListener(OnClickList_chkResultadoCobranza);

        Bundle parametros = getIntent().getExtras();

        if(parametros !=null){
            sN_INFORME = parametros.getString("N_INFORME");
            sN_SEQUENCIA = parametros.getString("N_SEQUENCIA");

            visitaDetDAO.getVisita(sN_INFORME, sN_SEQUENCIA);

            if(visitaDetDAO.lst!=null && visitaDetDAO.lst.size()>0)
            {
                if (visitaDetDAO.lst.get(0).getHORA_I().trim().equals(""))
                {
                    visitaDetDAO.lst.get(0).setHORA_I(funciones.HoraActual());
                }

                txtHoraInicio.setText(visitaDetDAO.lst.get(0).getHORA_I());
                txtHoraFin.setText(visitaDetDAO.lst.get(0).getHORA_S());
                txtNombreClienteVisita.setText(funciones.LetraCapital(visitaDetDAO.lst.get(0).getNOMBRE_CLIENTE()));

                chkCobranza.setChecked(funciones.CheckBoolean(visitaDetDAO.lst.get(0).getGESTION_C().toString()));
                chkVenta.setChecked(funciones.CheckBoolean(visitaDetDAO.lst.get(0).getGESTION_V().toString()));
                chkOtros.setChecked(funciones.CheckBoolean(visitaDetDAO.lst.get(0).getGESTION_O().toString()));

                chkResultadoVenta.setChecked(funciones.CheckBoolean(visitaDetDAO.lst.get(0).getRESULTADO_V().toString()));
                chkResultadoCobranza.setChecked(funciones.CheckBoolean(visitaDetDAO.lst.get(0).getRESULTADO_C().toString()));
                ddlObjecionVenta.setTag(funciones.isNull(visitaDetDAO.lst.get(0).getOBJECION_V()));
                ddlObjecionCobranza.setTag(funciones.isNull(visitaDetDAO.lst.get(0).getOBJECION_C()));
                ddlObjecionOtros.setTag(funciones.isNull(visitaDetDAO.lst.get(0).getOBJECION_O()));
                txtOtros.setText(funciones.isNull(visitaDetDAO.lst.get(0).getOBSERVACION()));



                editor_Shared.putString("CODIGO_ANTIGUO", visitaDetDAO.lst.get(0).getC_CLIENTE().toString());
                editor_Shared.commit();
            }

        }


    }

    View.OnClickListener OnClickList_ddlObjecionVenta = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Objecion Venta
                iTabla=45;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Regitro_Visita.this,iTabla, Integer.parseInt(sharedSettings.getString("ROL", "0")));
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };



    View.OnClickListener OnClickList_ddlObjecionCobranza = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Objecion Venta
                iTabla=44;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Regitro_Visita.this,iTabla, Integer.parseInt(sharedSettings.getString("ROL", "0")));
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };


    View.OnClickListener OnClickList_ddlObjecionOtros = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Objecion Venta
                iTabla=52;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Regitro_Visita.this,iTabla, Integer.parseInt(sharedSettings.getString("ROL", "0")));
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };


    View.OnClickListener OnClickList_ddlCambioDia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Objecion Venta
                iTabla=23;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Regitro_Visita.this,iTabla, Integer.parseInt(sharedSettings.getString("ROL", "0")));
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_ddlCambioFrecuencia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Objecion Venta
                iTabla=22;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Regitro_Visita.this,iTabla, Integer.parseInt(sharedSettings.getString("ROL", "0")));
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };



    View.OnClickListener OnClickList_chkResultadoCobranza = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(chkResultadoCobranza.isChecked())
            {
                String sMensaje = "¿Desea registrar recibo?";
                dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
                dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_Regitro_Visita.this, sMensaje);
                dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
                dialog_fragment_confirmar.isCancelable();

            }

        }
    };


    @Override
    public void onAceptar() {


    }

    @Override
    public void onTablaAuxiliarSI() {

        switch (iTabla) {
            case 45:
                ddlObjecionVenta.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                ddlObjecionVenta.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 44:
                ddlObjecionCobranza.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                ddlObjecionCobranza.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 52:
                ddlObjecionOtros.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                ddlObjecionOtros.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 23:
                ddlCambioDia.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                ddlCambioDia.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 22:
                ddlCambioFrecuencia.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                ddlCambioFrecuencia.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
        }

    }

    @Override
    public void onConfirmacionSI() {

        try {

/*
            editor_Shared.putString("COBRANZA_EVENTO","0");
            editor_Shared.putString("PAE", "");
            editor_Shared.putString("cpserie","");
            editor_Shared.putString("cpnumero","");
            editor_Shared.putString("cpfplanilla", "");
            editor_Shared.commit();

            Intent intent = new Intent(this , Activity_Cobranzas.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
*/

        }catch (Exception ex) {
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }
}

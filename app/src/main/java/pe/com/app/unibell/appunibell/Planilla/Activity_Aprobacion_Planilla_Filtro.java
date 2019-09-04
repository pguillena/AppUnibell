package pe.com.app.unibell.appunibell.Planilla;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_Aprobacion_Planilla_Filtro   extends AppCompatActivity
        implements Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialogo_Fragment_Fecha.NoticeDialogoListener{

    private EditText pl_txtserie,pl_txtnumero,pl_txtcodigo,pl_txtcliente,pl_txtruc,pl_txtdni,pl_txtcobrador,pl_txtoperacion,pl_txtplanilla;
    private TextView pl_lblinicio,pl_lblfin,pl_lblfpago,pl_lblestado;
    private Button rp_btnregistrar;

    private Integer iFiltroFecha =0;
    private Integer iAuxiliar = 0,iTabla=0;
    private DialogFragment dialogFragmentFecha;
    private Dialog_Fragment_Aceptar log_dialogaceptar;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprobacion_planilla_filtro);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Filtro de aprobación");
        getSupportActionBar().setSubtitle("");

        sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
        editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();


        pl_txtserie = (EditText)findViewById(R.id.pl_txtserie);
        pl_txtnumero = (EditText)findViewById(R.id.pl_txtnumero);
        pl_txtcodigo = (EditText)findViewById(R.id.pl_txtcodigo);
        pl_txtcliente = (EditText)findViewById(R.id.pl_txtcliente);
        pl_txtruc = (EditText)findViewById(R.id.pl_txtruc);
        pl_txtdni = (EditText)findViewById(R.id.pl_txtdni);
        pl_txtcobrador = (EditText)findViewById(R.id.pl_txtcobrador);
        pl_txtoperacion = (EditText)findViewById(R.id.pl_txtoperacion);
        pl_txtplanilla = (EditText)findViewById(R.id.pl_txtplanilla);
        pl_lblinicio = (TextView)findViewById(R.id.pl_lblinicio);
        pl_lblfin = (TextView)findViewById(R.id.pl_lblfin);
        pl_lblfpago = (TextView)findViewById(R.id.pl_lblfpago);
        pl_lblestado = (TextView)findViewById(R.id.pl_lblestado);
        rp_btnregistrar = (Button)findViewById(R.id.rp_btnregistrar);
        pl_lblfpago.setTag("0");
        pl_lblestado.setTag("40004");

        pl_lblinicio.setOnClickListener(OnClickList_pl_lblinicio);
        pl_lblfin.setOnClickListener(OnClickList_pl_lblfin);
        pl_lblfpago.setOnClickListener(OnClickList_pl_lblfpago);
        pl_lblestado.setOnClickListener(OnClickList_pl_lblestado);
        rp_btnregistrar.setOnClickListener(OnClickList_rp_btnregistrar);
    }


    View.OnClickListener OnClickList_pl_lblinicio = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iFiltroFecha=1;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_pl_lblfin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iFiltroFecha=2;
                dialogFragmentFecha = new Dialogo_Fragment_Fecha();
                dialogFragmentFecha.show(getFragmentManager(), "");
            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_pl_lblfpago = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iAuxiliar = 1;
                iTabla = 14;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Aprobacion_Planilla_Filtro.this, iTabla, 0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception e) {
            }
        }
    };
    View.OnClickListener OnClickList_pl_lblestado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción Estado
                iTabla=100;
                iAuxiliar=2;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Aprobacion_Planilla_Filtro.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception e) {
            }
        }
    };

    View.OnClickListener OnClickList_rp_btnregistrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                // TODO Auto-generated method stub
                String fpago="";
                Intent data = new Intent();
                data.putExtra("pl_serie",pl_txtserie.getText().toString().replace("","0"));
                data.putExtra("pl_numero",pl_txtnumero.getText().toString().replace("","0"));
                data.putExtra("pl_codigo", pl_txtcodigo.getText().toString().replace("","XXX"));
                data.putExtra("pl_cliente", pl_txtcliente.getText().toString().replace("","XXX"));
                data.putExtra("pl_ruc",pl_txtruc.getText().toString().replace("","XXX"));
                data.putExtra("pl_dni", pl_txtdni.getText().toString().replace("","XXX"));
                data.putExtra("pl_cobrador",pl_txtcobrador.getText().toString().replace("","XXX"));
                data.putExtra("pl_noperacion",pl_txtoperacion.getText().toString().replace("","XXX"));
                data.putExtra("pl_planilla",pl_txtplanilla.getText().toString().replace("","0"));
                data.putExtra("pl_inicio",pl_lblinicio.getText().toString().replace("","17530101"));
                data.putExtra("pl_fin",pl_lblfin.getText().toString().replace("","17530101"));
                fpago=pl_lblfpago.getTag().toString().trim();
                data.putExtra("pl_fpago",fpago.trim());
                data.putExtra("pl_estado",pl_lblestado.getTag().toString().trim());
                setResult(RESULT_OK, data);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };


    @Override
    public void onAceptar() {

    }

    @Override
    public void onTablaAuxiliarSI() {
        switch (iAuxiliar) {
            case 1:
                pl_lblfpago.setTag(sharedSettings.getString("ICODTABAUX", "0").toString().toUpperCase());
                pl_lblfpago.setText(sharedSettings.getString("IDESTABAUX", "").toString().toUpperCase());
                break;
            case 2:
                pl_lblestado.setTag(sharedSettings.getString("ICODTABAUX", "0").toString());
                pl_lblestado.setText(sharedSettings.getString("IDESTABAUX", "").toString());
                break;
        }
    }

    @Override
    public void setearFecha(String fecha) {
         if(iFiltroFecha==1){
             pl_lblinicio.setText(fecha);
         }
        if(iFiltroFecha==2){
            pl_lblfin.setText(fecha);
        }

    }
}

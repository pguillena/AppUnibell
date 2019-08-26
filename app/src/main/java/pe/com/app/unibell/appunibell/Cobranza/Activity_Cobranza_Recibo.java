package pe.com.app.unibell.appunibell.Cobranza;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_Cobranza_Recibo extends AppCompatActivity
        implements Dialogo_Fragment_Fecha.NoticeDialogoListener,
        Dialog_Fragment_Aceptar.DialogFragmentAceptarListener,
        Fragment_Cobranza_Recibo.Comunicator{

    private String iFragmentRecibo;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private DialogFragment dialogFragmentFecha;

    public String getiFragmentRecibo() {
        return iFragmentRecibo;
    }

    public void setiFragmentRecibo(String iFragmentRecibo) {
        this.iFragmentRecibo = iFragmentRecibo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranzas_recibo);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
            getSupportActionBar().setSubtitle("RECIBO DE COBRANZA");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment_Cobranza_Recibo fragment = new Fragment_Cobranza_Recibo();
            ft.add(R.id.contenedor, fragment,"Fragment_Cobranza_Recibo");
            ft.commit();

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

        } catch (Exception ex) {
        }
    }

    public void CargarRecibos(){
        String iFragment =getiFragmentRecibo();
        Fragment_Cobranza_Recibo fragment_cobranza = (Fragment_Cobranza_Recibo)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.CargarRecibo();
    }


    @Override
    public void setearFecha(String fecha) {
        String iFragment =getiFragmentRecibo();
        Fragment_Cobranza_Recibo fragment_cobranza = (Fragment_Cobranza_Recibo)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.setFecha(fecha);
    }




    @Override
    public void onAceptar() {
    }

    @Override
    public void Finalizar() {
        finish();
    }


    @Override
    public void Fecha() {
        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        dialogFragmentFecha.show(getFragmentManager(), "");
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}

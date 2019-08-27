package pe.com.app.unibell.appunibell.Planilla;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Clientes_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Aprobacion_Planilla_Adapter;
import pe.com.app.unibell.appunibell.AD.Cobranza_Liquidacion_Adapter;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.Cobranza.Activity_Cobranzas;
import pe.com.app.unibell.appunibell.Cobranza.Fragment_Cobranza;
import pe.com.app.unibell.appunibell.DAO.ClientesDAO;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_AprobacionPlanilla extends AppCompatActivity
           implements Fragment_AprobacionPlanilla.Comunicator{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private DialogFragment dialogFragmentFecha;
    private Integer iFecha=0;
    private String  iFragment_AprobacionPlanilla;

    public String getiFragment_AprobacionPlanilla() {
        return iFragment_AprobacionPlanilla;
    }

    public void setiFragment_AprobacionPlanilla(String iFragment_AprobacionPlanilla) {
        this.iFragment_AprobacionPlanilla = iFragment_AprobacionPlanilla;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_aprobacion_planilla);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("APROBACIÓN DE PLANILLA");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplication());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment_AprobacionPlanilla fragment = new Fragment_AprobacionPlanilla();
            ft.add(R.id.contenedor, fragment,"Fragment_AprobacionPlanilla");
            ft.commit();

        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aprobacion, menu);
        final MenuItem ic_action_pdf = menu.findItem(R.id.action_pdf);
        ic_action_pdf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void Finalizar() {
        finish();
    }

    @Override
    public void FechaInicio() {
        iFecha=1;
        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        dialogFragmentFecha.show(getFragmentManager(), "");
    }

    @Override
    public void FechaFin() {
        iFecha=2;
        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        dialogFragmentFecha.show(getFragmentManager(), "");
    }

    @Override
    public void Filtros() {
        Intent intent = new Intent(getApplication(), Activity_Aprobacion_Planilla_Filtro.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case 1:
                    String iFragment =getiFragment_AprobacionPlanilla();
                    Fragment_AprobacionPlanilla fragment_aprobacionPlanilla = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(iFragment);
                    fragment_aprobacionPlanilla.BuscarPlanilla(data);
                    break;
            }
        }
    }



    public void AprobarPlanilla(){
        String iFragment =getiFragment_AprobacionPlanilla();
        Fragment_AprobacionPlanilla fragment_aprobacion = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_aprobacion.AprobarPlanilla();
    }

    public void RetornarPlanilla(){
        String iFragment =getiFragment_AprobacionPlanilla();
        Fragment_AprobacionPlanilla fragment_aprobacion = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_aprobacion.RetornarPlanilla();
    }


}

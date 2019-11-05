package pe.com.app.unibell.appunibell.Planilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_AprobacionPlanilla extends AppCompatActivity
           implements Fragment_AprobacionPlanilla.Comunicator{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private DialogFragment dialogFragmentFecha;
    private Integer iFecha=0;
    private String  iFragment_AprobacionPlanilla;
    private String searchText="";
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
            getSupportActionBar().setTitle("Aprobación de planillas");

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
                searchText = newText;
                if (TextUtils.isEmpty(newText)) {
                    String TabFragment = getiFragment_AprobacionPlanilla();
                    Fragment_AprobacionPlanilla fragment_aprobacionPlanilla = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(TabFragment);
                    if (TabFragment != null ) {
                        fragment_aprobacionPlanilla.Filtro(newText);
                    }
                } else {
                    String TabFragment = getiFragment_AprobacionPlanilla();
                    Fragment_AprobacionPlanilla fragment_aprobacionPlanilla = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(TabFragment);
                    if (TabFragment != null ) {
                        fragment_aprobacionPlanilla.Filtro(newText);
                    }
                }
                return true;
            }
        });

        ic_action_refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String TabFragment = getiFragment_AprobacionPlanilla();
                Fragment_AprobacionPlanilla fragment_aprobacionPlanilla = (Fragment_AprobacionPlanilla)getSupportFragmentManager().findFragmentByTag(TabFragment);
                if (TabFragment != null ) {
                    Intent data = new Intent();
                   fragment_aprobacionPlanilla.BuscarPlanilla(data);
                }

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
        dialogFragmentFecha.show(getSupportFragmentManager(), "");
    }

    @Override
    public void FechaFin() {
        iFecha=2;
        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        dialogFragmentFecha.show(getSupportFragmentManager(), "");
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

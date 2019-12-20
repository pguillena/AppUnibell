package pe.com.app.unibell.appunibell.ScannerBarcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;


import org.json.JSONObject;

import pe.com.app.unibell.appunibell.AD.Scanner_Adapter;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import pe.com.app.unibell.appunibell.BL.S_Inv_InventarioBL;
import pe.com.app.unibell.appunibell.DAO.S_Inv_InventarioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Aceptar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;

public class Activity_Scanner extends AppCompatActivity
        implements Fragment_Scanner.Comunicator{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private S_Inv_InventarioDAO inventarioDAO;
    private ListView  listScan;
    private Scanner_Adapter scan_adapter = null;
    private  Dialog_Fragment_Confirmar dialog_fragment_confirmar;
    private S_Inv_InventarioBE inventarioBE;
    private String iFragmentScanner;
    private Dialog_Fragment_Aceptar log_dialogaceptar;

    public String getiFragmentScanner() {
        return iFragmentScanner;
    }

    public void setigetiFragmentScanner(String iFragmentScanner) {
        this.iFragmentScanner = iFragmentScanner;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_contenedor);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inventario");

        try {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment_Scanner fragment = new Fragment_Scanner();
            ft.add(R.id.contenedor, fragment,"Fragment_Scanner");
            ft.commit();

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }





    public void EliminarInventario(S_Inv_InventarioBE inventarioBE) {
        String iFragment =getiFragmentScanner();
        Fragment_Scanner fragment_scanner = (Fragment_Scanner)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_scanner.EliminarInventario(inventarioBE);
    }

    public void EditarInventario(S_Inv_InventarioBE inventarioBE) {
        String iFragment =getiFragmentScanner();
        Fragment_Scanner fragment_scanner = (Fragment_Scanner)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_scanner.EditarInventario(inventarioBE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_enviar, menu);
        final MenuItem action_enviar = menu.findItem(R.id.action_ordenfresh);
        final MenuItem ic_action_buscar = menu.findItem(R.id.ic_action_buscar);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(ic_action_buscar);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        searchView.setQueryHint("Ingresa datos a filtrar");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                String iFragment =getiFragmentScanner();
                Fragment_Scanner fragment_scanner = (Fragment_Scanner)getSupportFragmentManager().findFragmentByTag(iFragment);
                return fragment_scanner.onQueryTextChange(newText);

            }
        });


        action_enviar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String iFragment =getiFragmentScanner();
                Fragment_Scanner fragment_scanner = (Fragment_Scanner)getSupportFragmentManager().findFragmentByTag(iFragment);
                fragment_scanner.EnviarInventario();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }







}

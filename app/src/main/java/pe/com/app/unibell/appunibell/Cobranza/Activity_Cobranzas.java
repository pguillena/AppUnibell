package pe.com.app.unibell.appunibell.Cobranza;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import pe.com.app.unibell.appunibell.AD.ParTabla_Adapter;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.ParTablaDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialogo_Fragment_Fecha;
import pe.com.app.unibell.appunibell.R;

public class Activity_Cobranzas
        extends AppCompatActivity
        implements Fragment_Cobranza.Comunicator{

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private String iFragmentCobranzas;
    private DialogFragment dialogFragmentFecha;
    public Integer iFecha=0, iMinDate = 0;;
    private ParTablaDAO parTablaDAO = new ParTablaDAO();
    private ParTabla_Adapter parTabla_adapter = null;

    public String getiFragmentCobranzas() {
        return iFragmentCobranzas;
    }

    public void setiFragmentCobranzas(String iFragmentCobranzas) {
        this.iFragmentCobranzas = iFragmentCobranzas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_registro);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Registro de cobranza");
        //getSupportActionBar().setSubtitle("PROCESO DE COBRANZA");

        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment_Cobranza fragment = new Fragment_Cobranza();
            ft.add(R.id.contenedor, fragment,"Fragment_Cobranza");
            ft.commit();

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            editor_Shared.putString("iAmortizar_Dialog","1");
            editor_Shared.commit();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        final MenuItem Itemsave = menu.findItem(R.id.action_guardar);

        Itemsave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String iFragment =getiFragmentCobranzas();
                Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
                fragment_cobranza.GuardarCobranzas();
             return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void Finalizar() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void OcultaTeclado() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void FechaPlanilla() {

        iFecha=1;

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(this );
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
            parTablaDAO.getByGroup("120000", "120025");

            parTabla_adapter = new ParTabla_Adapter(this, 0, parTablaDAO.lst);
            parTabla_adapter.notifyDataSetChanged();

            if(parTabla_adapter!=null){

                for (int i = 0; i<parTabla_adapter.getCount(); i++) {
                    iMinDate =Integer.parseInt(parTabla_adapter.getItem(i).getVALORSUNAT());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        ((Dialogo_Fragment_Fecha) dialogFragmentFecha).iMinDate = iMinDate;
        dialogFragmentFecha.show(getFragmentManager(), "");
    }

    @Override
    public void FechaDeposito() {
        iFecha=2;
        dialogFragmentFecha = new Dialogo_Fragment_Fecha();
        dialogFragmentFecha.show(getFragmentManager(), "");
    }

    @Override
    public void AgregarPago() {
        Intent intent = new Intent(Activity_Cobranzas.this, Activity_Cobranza_Agregar_Pago.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void CobranzaEditar() {
        Intent intent = new Intent(getApplication(), Activity_Cobranza_Editar.class);
        startActivityForResult(intent,10);
    }

    public void CobranzaEditar_Adapter(Integer position){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.CobrazaEditar(position);
    }

    public void EliminarCobranzaCab(Integer position){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.EliminarCobranzaCab(position);
    }

    public Integer ValidarGuardarDetalle(){
        Integer iValor=0;
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        iValor=fragment_cobranza.ValidarGuardarDetalle();
        return iValor;
    }

    //TEMPORAL DE COBRANZA
    public void GuardarCobranzaDet(FactCobBE factCobBE){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.GuadarDetalle(factCobBE);
    }

    //DIALOG AMORTIZAR
    public void AmortizarDialogTemp(FactCobBE factCobBE){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.AmortizarDialogTemp(factCobBE);
    }

    public void AmortizarDialogDet(Documentos_Cobra_DetBE documentos_cobra_detBE,Integer iOpcion){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.AmortizarDialogDet(documentos_cobra_detBE,iOpcion);
    }
    //FIN DIALOG AMORTIZAR

    public void EliminarDetalle(FactCobBE factCobBE){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.EliminarDetalle(factCobBE);
    }

    //DETALLE DE COBRANZA
    public void DetalleCobranzaEdit(Documentos_Cobra_DetBE documentos_cobra_detBE,Integer iOpcion){
        String iFragment =getiFragmentCobranzas();
        Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
        fragment_cobranza.DetalleCobranzaEdit(documentos_cobra_detBE,iOpcion);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case 1:
                    String iFragment =getiFragmentCobranzas();
                    Fragment_Cobranza fragment_cobranza = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment);
                    fragment_cobranza.GuardarCabecera();
                    break;
                case 10:
                    //Refrescamos la Cabecera de la cobranza
                    String iFragment2 =getiFragmentCobranzas();
                    Fragment_Cobranza fragment_cobranza2 = (Fragment_Cobranza)getSupportFragmentManager().findFragmentByTag(iFragment2);
                    fragment_cobranza2.CargarCabecera();
                    break;

            }
        }
    }

    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}


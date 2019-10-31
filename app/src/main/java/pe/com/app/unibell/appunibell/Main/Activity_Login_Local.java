package pe.com.app.unibell.appunibell.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pe.com.app.unibell.appunibell.BL.PreferenciasBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_VendedorBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_AccesosBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_Usuario_AccionBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_Usuario_LocalBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_EmpresaBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_LocalBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_MenuBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_PerfilBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_UsuarioBL;
import pe.com.app.unibell.appunibell.BL.S_gem_TipoCambioBL;
import pe.com.app.unibell.appunibell.BL.UsuarioBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.PreferenciasDAO;
import pe.com.app.unibell.appunibell.DAO.S_Gem_Vendedor_Codigo_antDAO;
import pe.com.app.unibell.appunibell.DAO.S_Sem_UsuarioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.UtilLibrary;

/**
 * Created by rgalvez on 10/10/2016.
 */
public class Activity_Login_Local extends AppCompatActivity
        implements   Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener {

    private Button btLoginLocal;
    private String edtUserName, edtUserPass;
    private S_Sem_UsuarioDAO s_sem_usuarioDAO = new S_Sem_UsuarioDAO();
    private PreferenciasDAO preferenciasDAO = new PreferenciasDAO();
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private UsuarioBL BL = new UsuarioBL();
    private PreferenciasBL preferenciasBL = new PreferenciasBL();
    private S_Sem_EmpresaBL s_sem_empresaBL = new S_Sem_EmpresaBL();
    private S_Sem_LocalBL s_sem_localBL = new S_Sem_LocalBL();
    private S_Sem_MenuBL s_sem_menuBL = new S_Sem_MenuBL();
    //private TextView LogTxvIMEI,LogTxvVersion,
    private TextView   txtempresa,txtlocal;
    private Context ctx = this;
    private int versionCode = 0;
    private String sConfNombre="",sConfValor="";

    private Dialog_Fragment_Progress s_sem_usuarioPG;
    private Dialog_Fragment_Progress s_sea_accesosPG;
    private Dialog_Fragment_Progress s_sea_usuario_accionPG;
    private Dialog_Fragment_Progress s_sem_empresaPG;
    private Dialog_Fragment_Progress s_sem_localPG;
    private Dialog_Fragment_Progress s_sem_menuPG;
    private Dialog_Fragment_Progress s_sem_perfilPG;
    private Dialog_Fragment_Progress s_sea_usuario_localPG;
    private Dialog_Fragment_Progress S_gem_TipoCambioPG;
    private Dialog_Fragment_Progress s_gem_vendedorPG;

    private S_Sem_UsuarioBL s_sem_usuarioBL = new S_Sem_UsuarioBL();
    private S_Sea_AccesosBL s_sea_accesosBL = new S_Sea_AccesosBL();
    private S_Sea_Usuario_AccionBL s_sea_usuario_accionBL = new S_Sea_Usuario_AccionBL();
    private S_Sea_Usuario_LocalBL s_sea_usuario_localBL = new S_Sea_Usuario_LocalBL();
    private S_Sem_PerfilBL s_sem_perfilBL = new S_Sem_PerfilBL();
    private S_gem_TipoCambioBL s_gem_tipoCambioBL = new S_gem_TipoCambioBL();
    private S_Gem_VendedorBL s_gem_vendedorBL = new S_Gem_VendedorBL();

    private S_Gem_Vendedor_Codigo_antDAO s_gem_vendedor_codigo_antDAO = new S_Gem_Vendedor_Codigo_antDAO();
    private Funciones funciones=new Funciones();
    private Integer  iTabla=0;
    private Dialog_Fragment_Auxiliar dialog_fragment_auxiliar = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_local);
        getSupportActionBar().hide();
        Bundle parametros = getIntent().getExtras();
        if(parametros != null){
            edtUserName = parametros.getString("edtUserName");
            edtUserPass = parametros.getString("edtUserPass");
        }
        try {
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            String version = pInfo.versionName;
            versionCode = pInfo.versionCode;

            btLoginLocal=(Button) findViewById(R.id.btLoginLocal);
            txtempresa=(TextView)findViewById(R.id.txtempresa);
            txtlocal=(TextView)findViewById(R.id.txtlocal);

            funciones.addTextChangedListener(txtlocal, R.drawable.borderradius_activo, R.drawable.borderradius);
            funciones.addTextChangedListener(txtempresa, R.drawable.borderradius_activo, R.drawable.borderradius);

            addTextChangedListener(txtlocal, R.drawable.boton_redondo, R.drawable.boton_redondo_inactivo);
            addTextChangedListener(txtempresa, R.drawable.boton_redondo, R.drawable.boton_redondo_inactivo);


            // edtUserName.setDrawingCacheBackgroundColor(R.color.label_login_unibell);
            // edtUserName.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.eye), null, null, null);
            //edtUserName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.eye), null);

            try{
                //LogTxvVersion.setText(String.format(getResources().getString(R.string.version_text), versionCode));
                //LogTxvIMEI.setText(String.format(getResources().getString(R.string.imei_text), UtilLibrary.fnNumIMEI(ctx)));


             String sValor=UtilLibrary.fnNumIMEI(ctx).toString().trim();

                if(sValor.equals("")) {
                    sValor = funciones.getDeviceName();
                }


                editor_Shared.putString("sIMEI",sValor);
                editor_Shared.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_Login_Local.this);
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }

            txtempresa.setTag(sharedSettings.getString("iID_EMPRESA", "0"));
            txtempresa.setText(sharedSettings.getString("NOM_EMPRESA", ""));

            txtlocal.setTag(sharedSettings.getString("iID_LOCAL", "0"));
            txtlocal.setText(sharedSettings.getString("NOM_LOCAL", ""));

            btLoginLocal.setOnClickListener(OnClickList_btLoginLocal);
            txtempresa.setOnClickListener(OnClickList_txtempresa);
            txtlocal.setOnClickListener(OnClickList_txtlocal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void addTextChangedListener(final TextView textView, final int activo, final int inactivo){

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!txtempresa.getTag().toString().trim().equals("0") && !txtlocal.getTag().toString().trim().equals("0")) {
                    btLoginLocal.setBackgroundResource(activo);
                }
                else
                {
                    btLoginLocal.setBackgroundResource(inactivo);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private Boolean Validar(){
        if(txtempresa.getText().toString().trim().equals("")){
            Toast toastCodigo = Toast.makeText(getApplicationContext(),"SELECCIONE UNA EMPRESA", Toast.LENGTH_SHORT);
            toastCodigo.show();
            return false;
        }
        if(txtlocal.getText().toString().trim().equals("")){
            Toast toastCodigo = Toast.makeText(getApplicationContext(),"SELECCIONE LOCAL", Toast.LENGTH_SHORT);
            toastCodigo.show();
            return false;
        }
        return true;

    }


    View.OnClickListener OnClickList_txtempresa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                iTabla=300;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Login_Local.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickList_txtlocal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=400;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Login_Local.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        final MenuItem ic_action_settings = menu.findItem(R.id.action_settings);
        ic_action_settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    View.OnClickListener OnClickList_btLoginLocal=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Validar()==false){return;}

            editor_Shared.putString("USUARIO_LOGUEADO","1");
            editor_Shared.commit();

            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onTablaAuxiliarSI() {
        try {
            if(iTabla==300){
                txtlocal.setTag("0");
                txtlocal.setText("Seleccionar");
                txtempresa.setTag(sharedSettings.getString("iID_EMPRESA", "0"));
                txtempresa.setText(sharedSettings.getString("NOM_EMPRESA", "Seleccionar"));
            }

            if(iTabla==400){
                txtlocal.setTag(sharedSettings.getString("iID_LOCAL", "0"));
                txtlocal.setText(sharedSettings.getString("NOM_LOCAL", "Seleccionar"));
            }

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

}

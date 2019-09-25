package pe.com.app.unibell.appunibell.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import pe.com.app.unibell.appunibell.BL.PreferenciasBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_PersonaBL;
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
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;
import pe.com.app.unibell.appunibell.Util.UtilLibrary;

/**
 * Created by rgalvez on 10/10/2016.
 */
public class Activity_Login extends AppCompatActivity
        implements   Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener {

    private Button btLogin;

    private EditText edtUserName, edtUserPass;
    //private CheckBox LogChbRecord;
    private S_Sem_UsuarioDAO s_sem_usuarioDAO = new S_Sem_UsuarioDAO();
    private PreferenciasDAO preferenciasDAO = new PreferenciasDAO();
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    private UsuarioBL BL = new UsuarioBL();
    private PreferenciasBL preferenciasBL = new PreferenciasBL();
    private S_Sem_EmpresaBL s_sem_empresaBL = new S_Sem_EmpresaBL();
    private S_Sem_LocalBL s_sem_localBL = new S_Sem_LocalBL();
    private S_Sem_MenuBL s_sem_menuBL = new S_Sem_MenuBL();

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
    private Button btnSincronizarLogin, btnInfoLogin;
    private Integer MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;
    static final Integer PHONESTATS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        try {
            PackageInfo pInfo = null;
            String sValor="",versionCode="";

            try {
                sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_Login.this);
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();

                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            try {
                sValor=UtilLibrary.fnNumIMEI(ctx).toString().trim();

                if(sValor.equals("")){
                    sValor=UtilLibrary.fnNumEquipo(ctx).toString().trim();
                }

                if(sValor.equals("")){
                    sValor=UtilLibrary.fnNumSim(ctx).toString().trim();
                }
                editor_Shared.putString("sIMEI",sValor);
                editor_Shared.commit();

                String version = pInfo.versionName;
                versionCode =  String.valueOf(pInfo.versionCode);

            }catch (Exception e){
                versionCode="0";
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                }
            }


            btLogin=(Button) findViewById(R.id.btLogin);
            edtUserName=(EditText)findViewById(R.id.LogEdtUserName);
            edtUserPass=(EditText)findViewById(R.id.LogEdtUserPass);
            //edtUserName.setTextAppearance(getApplication(), R.style.SkyTextViewStyle);

            btnSincronizarLogin = (Button)findViewById(R.id.btnSincronizarLogin);
            btnInfoLogin = (Button)findViewById(R.id.btnInfoLogin);

            edtUserName.setText(sharedSettings.getString("USUARIO", "AGRAUA").toString());
            edtUserPass.setText("0");



            funciones.addTextChangedListener(edtUserName, R.drawable.borderradius_activo, R.drawable.borderradius);
            funciones.addTextChangedListener(edtUserPass, R.drawable.borderradius_activo, R.drawable.borderradius);
            addTextChangedListener(edtUserName, R.drawable.borderradiusbutton, R.drawable.borderradiusbutton_inactivo);
            addTextChangedListener(edtUserPass, R.drawable.borderradiusbutton, R.drawable.borderradiusbutton_inactivo);




            btLogin.setOnClickListener(OnClickList_btLogin);
            btnSincronizarLogin.setOnClickListener(OnClickListener_btnSincronizarLogin);
            btnInfoLogin.setOnClickListener(OnClickListener_btnInfoLogin);

            //SincronizarTipoCambio();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Con este método mostramos en un Toast con un mensaje que el usuario ha concedido los permisos a la aplicación
    private void consultarPermiso(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(Activity_Login.this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Login.this, permission)) {

                ActivityCompat.requestPermissions(Activity_Login.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(Activity_Login.this, new String[]{permission}, requestCode);
            }
        } else {
            editor_Shared.putString("sIMEI",UtilLibrary.fnNumIMEI(ctx).toString().trim());
            editor_Shared.commit();
            Toast.makeText(this,permission + " El permiso a la aplicación esta concedido.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addTextChangedListener(final EditText textView, final int activo, final int inactivo){

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!edtUserName.getText().toString().trim().equals("") && !edtUserPass.getText().toString().trim().equals("")) {
                    btLogin.setBackgroundResource(activo);
                }
                else
                {
                    btLogin.setBackgroundResource(inactivo);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    View.OnClickListener OnClickListener_btnInfoLogin=new View.OnClickListener(){

        @Override
        public void onClick(View anchorView) {
            PopupWindow popup = new PopupWindow(Activity_Login.this);

            try {
                String sValor="";
                String sValor2="";
                String sValor3="";

                View layout = getLayoutInflater().inflate(R.layout.popup_login, null);
                TextView lblVersion = (TextView)layout.findViewById(R.id.lblPopupLogin);

                sValor=String.format(getResources().getString(R.string.version_text), versionCode);
                sValor2=getResources().getString(R.string.imei_text);
                sValor3=sharedSettings.getString("sIMEI", "").toString();

                lblVersion.setText(sValor + "\n" + sValor3.toString() );

                popup.setContentView(layout);
                // Set content width and height
                popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

                // Closes the popup window when touch outside of it - when looses focus
                popup.setOutsideTouchable(true);
                popup.setFocusable(true);
                // Show anchored to button
                popup.setBackgroundDrawable(new BitmapDrawable());

                int x = (int)btnInfoLogin.getX()-385;
                int y = (int)btnInfoLogin.getY()-350;

                popup.showAsDropDown(btnInfoLogin, x, y);
               // popup.showAsDropDown(btnInfoLogin);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListener_btnSincronizarLogin=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            SincronizarLogin();

        }
    };

    private Boolean Validar(){
        if(edtUserName.getText().toString().trim().equals("")){
            Toast toastCodigo = Toast.makeText(getApplicationContext(),"Ingrese Usuario", Toast.LENGTH_SHORT);
            toastCodigo.show();
            return false;
        }
        if(edtUserPass.getText().toString().trim().equals("")){
            Toast toastCodigo = Toast.makeText(getApplicationContext(),"Ingrese Password", Toast.LENGTH_SHORT);
            toastCodigo.show();
            return false;
        }

        return true;

    }

    private void ValidarUsuario(){
        try {
            if (edtUserName.getText().toString().trim().length() == 0 || edtUserPass.getText().toString().trim().length() ==0) {
                new ToastLibrary(Activity_Login.this,"Ingrese Usuario y Password.").Show();
            }else{

                new LoginUsuarioValidaSQLite_AsyncTask().execute(
                        edtUserName.getText().toString().trim().toUpperCase(),
                        edtUserPass.getText().toString().trim().toUpperCase());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    View.OnClickListener OnClickList_lo_txtempresa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {

                ValidarUsuario();
                iTabla=300;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Login.this,iTabla,0);
                dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);




            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickList_lo_txtlocal = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                //Descripción de la tabla Forma Pago
                iTabla=400;
                dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
                dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_Login.this,iTabla,0);
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

                SincronizarLogin();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void SincronizarLogin() {
        try {
            new S_Sem_EmpresaBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sem_empresa);
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar Empresas.").Show();
        }


        try {
            new S_Sem_LocalBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sem_local + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0"));

        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar Locales.").Show();
        }

        try{
            new S_Gem_VendedorBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_vendedor + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar vendedores.").Show();
        }



        try {

            new S_Sem_UsuarioBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sem_usuario + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar Usuarios.").Show();
        }


        try {
            new S_Sem_MenuBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sem_menu + "/0");
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar opciones de menú.").Show();
        }

        try {
            new S_Sea_AccesosBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sea_accesos + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar accesos.").Show();
        }

        try{
            new S_Sem_PerfilBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sem_perfil + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar perfiles de usuario.").Show();
        }
        try{
            new S_Sea_Usuario_LocalBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sea_usuario_local + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar usuarios x local.").Show();
        }

        try{
            new S_Sea_Usuario_AccionBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_sea_usuario_accion + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar acciones de usuario.").Show();
        }

        SincronizarTipoCambio();
    }

    private void SincronizarTipoCambio() {
        try{
            new S_gem_TipoCambioBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_tipocambio);
        } catch (Exception ex) {
            new ToastLibrary(Activity_Login.this,"Error al sincronizar tipo de cambio.").Show();
        }
    }



    /*
        CompoundButton.OnCheckedChangeListener cboShowPasswordChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edtUserPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    edtUserPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        };
    */





    View.OnClickListener OnClickList_btLogin=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Validar()==false){return;}
            if (edtUserName.getText().toString().trim().length() == 0 || edtUserPass.getText().toString().trim().length() ==0) {
                new ToastLibrary(Activity_Login.this,"Ingrese Usuario y Password.").Show();
            }else{

                try {

                    SincronizarTipoCambio();

                    new S_Sem_UsuarioBL_RecuperarUsuarioMD5().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_rec_usuario + '/'
                                    + edtUserName.getText().toString().trim().toUpperCase()+ '/'
                                    + edtUserPass.getText().toString().trim());

                } catch (Exception ex) {
                    new ToastLibrary(Activity_Login.this,"Usuario o contraseña invalidos.").Show();
                }



            }
        }
    };

    /*    View.OnClickListener OnClickList_btSalir=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    */
    @Override
    public void onTablaAuxiliarSI() {

    }

    private class LoginUsuarioSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                s_sem_usuarioDAO.getLogin(p[0],p[1]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            s_sem_usuarioPG = new Dialog_Fragment_Progress();
            s_sem_usuarioPG.setMensaje("Obteniendo empresas y locales...");
            s_sem_usuarioPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected void onPostExecute(String restResult) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_usuarioPG != null && s_sem_usuarioPG.isVisible()) {
                s_sem_usuarioPG.dismiss();
            }

            super.onPostExecute(restResult);
            try {
                if(s_sem_usuarioDAO.lst.size()>0) {
                    Intent intent = new Intent(getApplication(), Activity_Login_Local.class);
                    editor_Shared.putString("USUARIO",s_sem_usuarioDAO.lst.get(0).getCREDENCIAL().trim().toUpperCase());
                    editor_Shared.putString("NOMBRE_COMPLETO",Funciones.LetraCapital(s_sem_usuarioDAO.lst.get(0).getNOMBRES().trim()) + " " +  Funciones.LetraCapital(s_sem_usuarioDAO.lst.get(0).getAPELLIDO_PATERNO().trim()) +" "+Funciones.LetraCapital(s_sem_usuarioDAO.lst.get(0).getAPELLIDO_MATERNO().trim()));
                    editor_Shared.putString("PERFIL",s_sem_usuarioDAO.lst.get(0).getCREDENCIAL().trim().toUpperCase());
                    editor_Shared.putString("iID_VENDEDOR",s_sem_usuarioDAO.lst.get(0).getID_PERSONA().toString());
                    editor_Shared.putString("C_PERFIL",s_sem_usuarioDAO.lst.get(0).getC_PERFIL().toString());
                    editor_Shared.putString("ROL",s_sem_usuarioDAO.lst.get(0).getROL().toString());
                    editor_Shared.putString("VALIDA_RECIBO",s_sem_usuarioDAO.lst.get(0).getVALIDA_RECIBO().toString());
                    editor_Shared.putString("NOMBRE_TELEFONO",funciones.getDeviceName());

                    editor_Shared.commit();
                    //new LoginVendedorCodAntiguoSQLite_AsyncTask().execute(sharedSettings.getString("iID_VENDEDOR", "0"));
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),getResources().getString(R.string.lgn_msg_error), Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }
    private class LoginUsuarioValidaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                s_sem_usuarioDAO.getLogin(p[0],p[1]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String restResult) {
            super.onPostExecute(restResult);
            try {
                if(s_sem_usuarioDAO.lst.size()>0) {
                    editor_Shared.putString("USUARIO",s_sem_usuarioDAO.lst.get(0).getCREDENCIAL().trim().toUpperCase());
                    editor_Shared.putString("PERFIL",s_sem_usuarioDAO.lst.get(0).getCREDENCIAL().trim().toUpperCase());
                    editor_Shared.putString("iID_VENDEDOR",s_sem_usuarioDAO.lst.get(0).getID_PERSONA().toString());
                    editor_Shared.commit();
                }else{
                    Toast.makeText(getApplication(),getResources().getString(R.string.lgn_msg_error), Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }
    public class S_Sem_UsuarioBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_usuarioPG = new Dialog_Fragment_Progress();
            s_sem_usuarioPG.setMensaje("Sincronizando");
            s_sem_usuarioPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_usuarioBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_usuarioPG != null && s_sem_usuarioPG.isVisible()) {
                s_sem_usuarioPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_usuarioBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    //Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }




    public class S_Sem_EmpresaBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_empresaPG = new Dialog_Fragment_Progress();
            s_sem_empresaPG.setMensaje("Sincronizando");
            s_sem_empresaPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_empresaBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_empresaPG != null && s_sem_empresaPG.isVisible()) {
                s_sem_empresaPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_empresaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sem_LocalBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_localPG = new Dialog_Fragment_Progress();
            s_sem_localPG.setMensaje("Sincronizando");
            s_sem_localPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_localBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_localPG != null && s_sem_localPG.isVisible()) {
                s_sem_localPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_localBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sea_AccesosBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sea_accesosPG = new Dialog_Fragment_Progress();
            s_sea_accesosPG.setMensaje("Sincronizando");
            s_sea_accesosPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sea_accesosBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sea_accesosPG != null && s_sea_accesosPG.isVisible()) {
                s_sea_accesosPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_accesosBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sea_Usuario_AccionBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sea_usuario_accionPG = new Dialog_Fragment_Progress();
            s_sea_usuario_accionPG.setMensaje("Sincronizando");
            s_sea_usuario_accionPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sea_usuario_accionBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sea_usuario_accionPG != null && s_sea_usuario_accionPG.isVisible()) {
                s_sea_usuario_accionPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_usuario_accionBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sem_MenuBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_menuPG = new Dialog_Fragment_Progress();
            s_sem_menuPG.setMensaje("Sincronizando");
            s_sem_menuPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_menuBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_menuPG != null && s_sem_menuPG.isVisible()) {
                s_sem_menuPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_menuBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sem_PerfilBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_perfilPG = new Dialog_Fragment_Progress();
            s_sem_perfilPG.setMensaje("Sincronizando");
            s_sem_perfilPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_perfilBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_perfilPG != null && s_sem_perfilPG.isVisible()) {
                s_sem_perfilPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_perfilBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Sea_Usuario_LocalBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sea_usuario_localPG = new Dialog_Fragment_Progress();
            s_sea_usuario_localPG.setMensaje("Sincronizando");
            s_sea_usuario_localPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sea_usuario_localBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sea_usuario_localPG != null && s_sea_usuario_localPG.isVisible()) {
                s_sea_usuario_localPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")).Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_usuario_localBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private class LoginVendedorCodAntiguoSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                s_gem_vendedor_codigo_antDAO.getAll(p[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String restResult) {
            super.onPostExecute(restResult);
            try {
                if(s_gem_vendedor_codigo_antDAO.lst.size()>0) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    editor_Shared.putString("CODIGO_ANTIGUO",s_gem_vendedor_codigo_antDAO.lst.get(0).getCODIGO_ANTIGUO().toString());
                    editor_Shared.commit();

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplication(),getResources().getString(R.string.lgn_msg_error), Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }


    public class S_Gem_VendedorBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_vendedorPG = new Dialog_Fragment_Progress();
            s_gem_vendedorPG.setMensaje("Sincronizando");
            s_gem_vendedorPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_vendedorBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_vendedorPG != null && s_gem_vendedorPG.isVisible()) {
                s_gem_vendedorPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message")+ ":Vededor").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_vendedorBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class S_gem_TipoCambioBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            S_gem_TipoCambioPG = new Dialog_Fragment_Progress();
            S_gem_TipoCambioPG.setMensaje("Sincronizando");
            S_gem_TipoCambioPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_tipoCambioBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (S_gem_TipoCambioPG != null && S_gem_TipoCambioPG.isVisible()) {
                S_gem_TipoCambioPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message") + ":Tipo de Cambio").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.S_gem_TipoCambioBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }









    @Override
    protected void onStart() {
        //SE EJECUTA ANTES DE QUE LA APLICACION SEA VISIBLE
        super.onStart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



    public class S_Sem_UsuarioBL_RecuperarUsuarioMD5 extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_sem_usuarioPG = new Dialog_Fragment_Progress();
            s_sem_usuarioPG.setMensaje("Validando..");
            s_sem_usuarioPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_sem_usuarioBL.getUsuarioMD5(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_sem_usuarioPG != null && s_sem_usuarioPG.isVisible()) {
                s_sem_usuarioPG.dismiss();
            }

            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Login.this, result.getString("message") + ":Usuarios").Show();
                }
                else if (result.getInt("status")==1 && result.getString("message").equals("0"))
                {
                    new ToastLibrary(Activity_Login.this, "Usuario o contraseña incorrectos").Show();
                }
                else
                {
                    if(s_sem_usuarioBL.lst!=null && s_sem_usuarioBL.lst.size()>0) {
                        new LoginUsuarioSQLite_AsyncTask().execute(edtUserName.getText().toString().trim().toUpperCase(), edtUserPass.getText().toString().trim().toUpperCase());
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



}

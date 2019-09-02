package pe.com.app.unibell.appunibell.Main;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

import pe.com.app.unibell.appunibell.AD.Menu_Adapter;
import pe.com.app.unibell.appunibell.BL.MenuStringBL;
import pe.com.app.unibell.appunibell.Clientes.Activity_clientes;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.Menu_StringDAO;
import pe.com.app.unibell.appunibell.Flujo_Seguimiento.Activity_Flujo_Seguimiento;
import pe.com.app.unibell.appunibell.Liquidacion.Activity_Liquidacion;
import pe.com.app.unibell.appunibell.Planilla.Activity_AprobacionPlanilla;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Reportes.Activity_Reportes;
import pe.com.app.unibell.appunibell.Servicio.AlarmReceiver;
import pe.com.app.unibell.appunibell.Util.Globals;

public class MainActivity  extends AppCompatActivity{
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Menu_StringDAO menu_stringDAO = new Menu_StringDAO();
    private Menu_StringDAO menu_stringDAO1 = null;
    //private TextView username;
    private String searchText="";
    //private Integer iOrden_Seleccion=-1;
    private Parcelable state;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Menu_Adapter menu_adapter;
    private LinearLayout llMenu;
    private Integer iCountOrdenes=0;
    private ListView mnu_lvmenu;
    private MenuStringBL menuStringBL = new MenuStringBL();
    private static final int REQUEST_CODE_1=1;
    private Integer  SMNU_ORDLIST=0,SMNU_OLOCAL=0;
    private static String DB_NAME = "REST_POS_BD.sqlite";
    private Button sp_img1, btnsincronizar, btncobranzas,btnliquidacion,btnaplanilla,btnreportes;
    //DIEZ MINUTOS
    private Integer iTiempoEjecuta=2;

    // Variables que almacenarán el tiempo inicial y el número de tazas
    // acumuladas.
    private int brewTime, numTazas;
    // TextViews que mostrarán el tiempo restante y el número de tazas
    // acumuladas.
    private TextView tvTime, tvNumTazas, lo_txtempresa, lo_txtlocal, lblNombreUsuario, lblNombreUsuario2;
    // Botón para iniciar y parar el contador.
    // Variable que indicará si la aplicación está o no en marcha.
    private boolean working;
    // Cronómetro de la aplicación.
    private CountDownTimer timer;
    private Boolean iActivado=false;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
        numTazas = 0;
        brewTime = 1;
        working = false;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
        getSupportActionBar().setSubtitle("COBRANZAS");


         try{
             sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
             editor_Shared= getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

             DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
         } catch (Exception e) {
             e.printStackTrace();
         }

        //username=(TextView) findViewById(R.id.username);
        llMenu = (LinearLayout) findViewById(R.id.llMenu);
        mnu_lvmenu = (ListView) findViewById(R.id.mnu_lvmenu);

        btnsincronizar=(Button) findViewById(R.id.btnsincronizar);
        btncobranzas=(Button) findViewById(R.id.btncobranzas);
        btnliquidacion=(Button) findViewById(R.id.btnliquidacion);
        btnaplanilla=(Button) findViewById(R.id.btnaplanilla);
            btnreportes=(Button) findViewById(R.id.btnreportes);

        lo_txtempresa=(TextView) findViewById(R.id.lo_txtempresa);
        lo_txtlocal=(TextView) findViewById(R.id.lo_txtlocal);
            lblNombreUsuario = (TextView)findViewById(R.id.lblNombreUsuario);
            lblNombreUsuario2 = (TextView)findViewById(R.id.lblNombreUsuario2);

            lblNombreUsuario.setText(sharedSettings.getString("NOMBRE_COMPLETO", "").toString());
            lblNombreUsuario2.setText(sharedSettings.getString("NOMBRE_COMPLETO", "").toString());
        lo_txtempresa.setText(sharedSettings.getString("NOM_EMPRESA", "").toString());
        lo_txtlocal.setText(sharedSettings.getString("NOM_LOCAL", "").toString());

        btnsincronizar.setEnabled(false);
        btncobranzas.setEnabled(false);
        btnliquidacion.setEnabled(false);
        btnaplanilla.setEnabled(false);

        btnsincronizar.setOnClickListener(OnClickListenercl_btnsincronizar);
        btncobranzas.setOnClickListener(OnClickListenercl_btncobranzas);
        btnliquidacion.setOnClickListener(OnClickListenercl_btnliquidacion);
        btnaplanilla.setOnClickListener(OnClickListenercl_btnaplanilla);
            btnreportes.setOnClickListener(OnClickListenercl_btnreportes);

       //EJECUTA EL SERVICIO
        Intent alarm = new Intent(MainActivity.this, AlarmReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(MainActivity.this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (alarmRunning == false) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000*iTiempoEjecuta, pendingIntent);
            Random random = new Random();
            int iRandom = random.nextInt(999 - 100) + 100;
            editor_Shared.putInt("random",iRandom);
            editor_Shared.putString("tarea_gps", "1");
            editor_Shared.commit();
        }

        Activar(false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        DrawerLayout.LayoutParams dllp = (DrawerLayout.LayoutParams) llMenu.getLayoutParams();
        // dllp.width = displayMetrics.widthPixels - getResources().getDimensionPixelOffset(R.dimen.lvmenu_marginright);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = getResources().getConfiguration().orientation;

        dllp.width = display.getWidth();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.app_name, R.string.app_name) {

            @Override
        public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                }

        @Override
        public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        //Cargamos los clientes desde la base de datos SQLite
        Cargar();
        aTrabajar();
        //username.setText(sharedSettings.getString("USUARIO", "").toString());

     mnu_lvmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long l) {
            String mnu_nom=menu_adapter.getItem(position).getMNUNOM().toString();

         switch(mnu_nom) {
             case "SMNU_SINCRONIZAR":
                 Intent SMNU_SINCRONIZAR = new Intent(getApplication(), Activity_Sincronizar.class);
                 startActivity(SMNU_SINCRONIZAR);
                 btnsincronizar.setEnabled(true);
                 OcultarDrawel();
                 break;

/*            case "SMNU_CLIENTES":
                Intent SMNU_CLIENTES = new Intent(getApplication(), Activity_clientes.class);
                startActivity(SMNU_CLIENTES);
                btncobranzas.setEnabled(true);
                 OcultarDrawel();
                 break;
*/
            case "SMNU_COBRANZAS":
                Intent SMNU_COBRANZAS = new Intent(getApplication(), Activity_clientes.class);
                startActivity(SMNU_COBRANZAS);
                OcultarDrawel();
                break;

            case "SMNU_LINQCOBRANZA":
                Intent SMNU_LINQCOBRANZA = new Intent(getApplication(), Activity_Liquidacion.class);
                startActivity(SMNU_LINQCOBRANZA);
                btnliquidacion.setEnabled(true);
                OcultarDrawel();
                 break;

          /*  case "SMNU_FLUJOSEGUIMIENTO":
                Intent SMNU_FLUJOSEGUIMIENTO = new Intent(getApplication(), Activity_FlujoSeguimiento.class);
                startActivity(SMNU_FLUJOSEGUIMIENTO);
                 OcultarDrawel();
                 break;*/

            case "SMNU_APROBACIONPLA":
                Intent SMNU_APROBACIONPLA = new Intent(getApplication(), Activity_AprobacionPlanilla.class);
                startActivity(SMNU_APROBACIONPLA);
                btnaplanilla.setEnabled(true);
                OcultarDrawel();
                break;

            case "SMNU_REPORTES":
                Intent SMNU_REPORTES = new Intent(getApplication(), Activity_Reportes.class);
                startActivity(SMNU_REPORTES);
                OcultarDrawel();
                 break;

            case "SMNU_ACERCADE":
                OcultarDrawel();
                break;

             case "SMNU_CAMBIO":
                 finish();
                 break;

            case "SMNU_CERRAR":
                finish();
                Intent SMNU_CERRAR = new Intent(getApplication(), Activity_Login.class);
                startActivity(SMNU_CERRAR);
                OcultarDrawel();
                break;
        }
        }
        });

        } catch (Exception ex) {
        ex.printStackTrace();
        }
        }


    View.OnClickListener OnClickListenercl_btnsincronizar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent SMNU_SINCRONIZAR = new Intent(getApplication(), Activity_Sincronizar.class);
                startActivity(SMNU_SINCRONIZAR);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btncobranzas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent SMNU_CLIENTES = new Intent(getApplication(), Activity_clientes.class);
                startActivity(SMNU_CLIENTES);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btnliquidacion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent SMNU_LINQCOBRANZA = new Intent(getApplication(), Activity_Liquidacion.class);
                startActivity(SMNU_LINQCOBRANZA);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btnaplanilla = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent SMNU_APROBACIONPLA = new Intent(getApplication(), Activity_AprobacionPlanilla.class);
                startActivity(SMNU_APROBACIONPLA);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    View.OnClickListener OnClickListenercl_btnreportes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent SMNU_REPORTES = new Intent(getApplication(), Activity_Reportes.class);
                startActivity(SMNU_REPORTES);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };




    private void Activar(Boolean valor){
        iActivado=valor;
        }

   private void aTrabajar() {
        // Si no esta funcionando, la iniciamos.
        working = true;
        timer = new CountDownTimer(brewTime * 60 * 1000, 100) {
@Override
   public void onTick(long millisUntilFinished) {
        if(Long.valueOf(millisUntilFinished / 1000)==20){
        working = true;
        numTazas=0;
        timer.start();
        }
        }
        @Override
        public void onFinish() {
        working = false;
        numTazas++;
        }
        };
        // Una vez configurado el timer, lo iniciamos.
        timer.start();

        }

    private void OcultarDrawel(){
        drawerLayout.closeDrawers();
        }

    private void Cargar(){
        Activar(false);
        new LoadMenuSQLite_AsyncTask().execute(
                sharedSettings.getString("iID_EMPRESA", "").toString(),
                sharedSettings.getString("C_PERFIL", "").toString());
   }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        switch(requestCode){
        case REQUEST_CODE_1:
        Cargar();
        break;
        }
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        final MenuItem action_ordenfresh = menu.findItem(R.id.action_ordenfresh);

        String sPerfil=sharedSettings.getString("Perfil", "").toString().trim();
        if (sPerfil.equals("ADMIN")) {
        SMNU_ORDLIST=1;
        SMNU_OLOCAL=1;
        }

        action_ordenfresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
@Override
public boolean onMenuItemClick(MenuItem item) {
        /*CargarSoloMesas();*/
        return false;
        }
        });
        return super.onCreateOptionsMenu(menu);
        }


//SQLITE

private class LoadMenuSQLite_AsyncTask extends AsyncTask<String, String,String> {
    @Override
    protected String doInBackground(String... p) {
        try {
           menu_stringDAO.getallBy(p[0],p[1]);
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
            menu_adapter  = new Menu_Adapter(getApplication(), 0, menu_stringDAO.lisMenu);
            menu_adapter.notifyDataSetChanged();
            mnu_lvmenu.setAdapter(menu_adapter);
            String sNOMBRE_MENU="";
            for (int j = 0; j < menu_adapter.lstFiltrado.size(); j++) {
                sNOMBRE_MENU=menu_adapter.lstFiltrado.get(j).getMNUNOM();
                switch (sNOMBRE_MENU.trim()) {
                    case "SMNU_SINCRONIZAR":
                        btnsincronizar.setEnabled(true);
                        break;
                    case "SMNU_COBRANZAS":
                        btncobranzas.setEnabled(true);
                        break;
                    case "SMNU_LINQCOBRANZA":
                        btnliquidacion.setEnabled(true);
                        break;
                    case "SMNU_APROBACIONPLA":
                        btnaplanilla.setEnabled(true);
                        break;
                }
            }
            invalidateOptionsMenu();
            Globals g = (Globals)getApplication();
            g.setIntentAdapterMenuString(menu_stringDAO.lisMenu);

        } catch (Exception ex) {
            //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
        }
    }
}


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }
        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something on back.
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
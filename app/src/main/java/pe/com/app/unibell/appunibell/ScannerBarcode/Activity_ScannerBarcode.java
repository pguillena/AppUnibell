package pe.com.app.unibell.appunibell.ScannerBarcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import pe.com.app.unibell.appunibell.BE.M_ARTICULOSBE;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;
import pe.com.app.unibell.appunibell.BL.S_Inv_InventarioBL;
import pe.com.app.unibell.appunibell.DAO.M_ArticulosDAO;
import pe.com.app.unibell.appunibell.DAO.S_Inv_InventarioDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Auxiliar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Confirmar;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Scanner;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Activity_ScannerBarcode extends AppCompatActivity
        implements ZBarScannerView.ResultHandler,
        Dialog_Fragment_Scanner.Dialog_Fragment_ScannerListener, Dialog_Fragment_Auxiliar.Dialog_Fragment_AuxiliarListener,
        Dialog_Fragment_Confirmar.Dialog_Fragment_ConfirmarListener
{
    private static final String FLASH_STATE = "FLASH_STATE";
    private ViewGroup contentFrame;
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private MediaPlayer mp;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Funciones funciones = new Funciones();
    private S_Inv_InventarioDAO inventarioDAO = new S_Inv_InventarioDAO();
    private S_Inv_InventarioDAO inventarioDAO2 = new S_Inv_InventarioDAO();
    private M_ArticulosDAO marticulosDAO = new M_ArticulosDAO();
    private S_Inv_InventarioBE inventarioBE;
    private M_ARTICULOSBE marticulosBE;
    private TextView txtTotalArticulos, txtTotalXArticulo, lblTotalXArticulo, lblUbicacionScan, txtUbicacionScan;
    private Button btnScanearPorCantidad, btnScanearPorCodigo, btnNuevaUbicacion, btnNuevoConteo, btnScanearPorUnidad;
    private Dialog_Fragment_Scanner dialog_fragment_scanner = null;
    private Dialog_Fragment_Confirmar dialog_fragment_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Toma de Inventario");

            sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            contentFrame = (ViewGroup) findViewById(R.id.content_frame);
            mScannerView = new ZBarScannerView(this);
            contentFrame.addView(mScannerView);

            //Validamos Permisos de Almace/(Creación de PDF)
            if (funciones.ValidacionPermisos(Activity_ScannerBarcode.this) == false) {
                return;
            }
            //Inactivar Optimización de la bateria para ejecutar procesos en Background
            if (funciones.Valid_InactivaOptimatBateria(Activity_ScannerBarcode.this) == false) {
                return;
            }

            if( ContextCompat.checkSelfPermission(Activity_ScannerBarcode.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            5);
                }
            }

            txtTotalArticulos =(TextView)findViewById(R.id.txtTotalArticulos);
            txtTotalXArticulo =(TextView)findViewById(R.id.txtTotalXArticulo);
            lblTotalXArticulo =(TextView)findViewById(R.id.lblTotalXArticulo);

            lblUbicacionScan =(TextView)findViewById(R.id.lblUbicacionScan);
            txtUbicacionScan =(TextView)findViewById(R.id.txtUbicacionScan);


            btnScanearPorCantidad = (Button)findViewById(R.id.btnScanearPorCantidad);
            btnScanearPorCodigo = (Button)findViewById(R.id.btnScanearPorCodigo);
            btnScanearPorUnidad = (Button)findViewById(R.id.btnScanearPorUnidad);


            btnNuevaUbicacion = (Button)findViewById(R.id.btnNuevaUbicacion);
            btnNuevoConteo = (Button)findViewById(R.id.btnNuevoConteo);


            btnScanearPorCantidad.setOnClickListener(OnClickListener_btnScanearPorCantidad);
            btnScanearPorCodigo.setOnClickListener(OnClickListener_btnScanearPorCodigo);
            btnScanearPorUnidad.setOnClickListener(OnClickListener_btnScanearPorUnidad);

            btnNuevaUbicacion.setOnClickListener(OnClickListener_btnNuevaUbicacion);
            txtTotalXArticulo.setOnClickListener(OnClickListener_txtTotalXArticulo);
            lblTotalXArticulo.setOnClickListener(OnClickListener_txtTotalXArticulo);
            txtUbicacionScan.setOnClickListener(OnClickListener_txtUbicacionScan);
            lblUbicacionScan.setOnClickListener(OnClickListener_txtUbicacionScan);
            btnNuevoConteo.setOnClickListener(OnClickListener_btnNuevoConteo);


            ValidaTipoScanedo();
            Cargar(sharedSettings.getString("iCONTEO", "0").toString(),sharedSettings.getString("iCOD_ART", "").toString(),  sharedSettings.getString("iUBICACION", "").toString(), funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));


            if(Integer.parseInt(sharedSettings.getString("iCONTEO", "0").toString())>0){
                btnNuevoConteo.setText("NUEVO CONTEO (" + sharedSettings.getString("iCONTEO", "0").toString()+")");
            }



        } catch (Exception ex) {
            new ToastLibrary(this, ex.getMessage()).Show();
        }
    }


    View.OnClickListener OnClickListener_btnNuevaUbicacion = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            NuevaUbicacion();
        }

    };

    private void NuevaUbicacion() {

        editor_Shared.putString("iACCION_INVENTARIO", "NUEVO");
        editor_Shared.commit();
        CargarCantidad(inventarioBE);
    }


    View.OnClickListener OnClickListener_txtTotalXArticulo = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            inventarioDAO.getAll(sharedSettings.getString("iCONTEO", "0").toString(), sharedSettings.getString("iCOD_ART", "").toString(),sharedSettings.getString("iUBICACION", "").toString(), funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));;
            if(inventarioDAO.lst!=null && inventarioDAO.lst.size()>0)
            {    mScannerView.stopCamera();
                inventarioBE =inventarioDAO.lst.get(0);
                editor_Shared.putString("iACCION_INVENTARIO", "EDITAR");
                editor_Shared.commit();
                dialog_fragment_scanner = new Dialog_Fragment_Scanner(inventarioBE.getCANTIDAD());
                dialog_fragment_scanner.inventarioBE = inventarioBE;
                dialog_fragment_scanner.setCantidadDialogfragmentListener(Activity_ScannerBarcode.this);
                dialog_fragment_scanner.show(getSupportFragmentManager(), dialog_fragment_scanner.TAG);
                dialog_fragment_scanner.isCancelable();
            }

        }

    };


    View.OnClickListener OnClickListener_txtUbicacionScan = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Dialog_Fragment_Auxiliar  dialog_fragment_auxiliar = new Dialog_Fragment_Auxiliar();
            dialog_fragment_auxiliar.setAuxiliarDialogfragmentListener(Activity_ScannerBarcode.this, 900, 0);
            dialog_fragment_auxiliar.show(getSupportFragmentManager(), dialog_fragment_auxiliar.TAG);

        }

    };


    public void CargarCantidad(S_Inv_InventarioBE inventarioBE){
    mScannerView.stopCamera();
    dialog_fragment_scanner = new Dialog_Fragment_Scanner();
    dialog_fragment_scanner.inventarioBE = inventarioBE;
    dialog_fragment_scanner.setCantidadDialogfragmentListener(Activity_ScannerBarcode.this);
    dialog_fragment_scanner.show(getSupportFragmentManager(), dialog_fragment_scanner.TAG);
    dialog_fragment_scanner.isCancelable();
}

    public void CargarxCodigo(){
        mScannerView.stopCamera();
        dialog_fragment_scanner = new Dialog_Fragment_Scanner();
        dialog_fragment_scanner.setCantidadDialogfragmentListener(Activity_ScannerBarcode.this);
        dialog_fragment_scanner.show(getSupportFragmentManager(), dialog_fragment_scanner.TAG);
        dialog_fragment_scanner.isCancelable();
    }


    View.OnClickListener OnClickListener_btnScanearPorCantidad = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            editor_Shared.putString("iTIPO_SCAN", "CANTIDAD");
            editor_Shared.commit();
            ValidaTipoScanedo();
            new ToastLibrary(Activity_ScannerBarcode.this, "MODO ESCANEO POR CANTIDAD" ).Show();


        }

    };

    View.OnClickListener OnClickListener_btnNuevoConteo = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            NuevoConteo();
        }

    };

    private void NuevoConteo() {
        mScannerView.stopCamera();
        if(sharedSettings.getString("iCONTEO", "").toString().trim().equals("") || sharedSettings.getString("iCONTEO", "").toString().trim().equals("null"))
        {
            String sMensaje = "¿Desea iniciar un conteo?";
            dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
            dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_ScannerBarcode.this, sMensaje);
            dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
            dialog_fragment_confirmar.isCancelable();
        }
        else
        {
            String sMensaje = "¿Desea cerrar el conteo actual?\nConteo N°: "+ sharedSettings.getString("iCONTEO", "").toString() +" \nEsta acción no podra ser revertida.";
            dialog_fragment_confirmar = new Dialog_Fragment_Confirmar();
            dialog_fragment_confirmar.setmConfirmarDialogfragmentListener(Activity_ScannerBarcode.this, sMensaje);
            dialog_fragment_confirmar.show(getSupportFragmentManager(), dialog_fragment_confirmar.TAG);
            dialog_fragment_confirmar.isCancelable();
        }

    }

    private void ValidaTipoScanedo() {

        if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("CANTIDAD"))
        {
            btnScanearPorCantidad.setBackgroundResource(R.color.Button_login_unibell);
            btnScanearPorCodigo.setBackgroundResource(R.color.Button_login_disable_unibell);
            btnScanearPorUnidad.setBackgroundResource(R.color.Button_login_disable_unibell);
        }
        else  if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("UNIDAD")) {
            btnScanearPorCodigo.setBackgroundResource(R.color.Button_login_disable_unibell);
            btnScanearPorCantidad.setBackgroundResource(R.color.Button_login_disable_unibell);
            btnScanearPorUnidad.setBackgroundResource(R.color.Button_login_unibell);
        }
        else   if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("CODIGO"))
        {
            btnScanearPorCodigo.setBackgroundResource(R.color.Button_login_unibell);
            btnScanearPorCantidad.setBackgroundResource(R.color.Button_login_disable_unibell);
            btnScanearPorUnidad.setBackgroundResource(R.color.Button_login_disable_unibell);
        }


    }


    View.OnClickListener OnClickListener_btnScanearPorCodigo = new View.OnClickListener() {

        @Override
        public void onClick(View view) {


            if(sharedSettings.getString("iCONTEO", "").toString().trim().equals("") || sharedSettings.getString("iCONTEO", "").toString().trim().equals("null"))
            {
                NuevoConteo();
                return;
            }

            if(sharedSettings.getString("iUBICACION", "").toString().trim().equals("") || sharedSettings.getString("iUBICACION", "").toString().trim().equals("null"))
            {
                new ToastLibrary(Activity_ScannerBarcode.this, "Debe ingresar una ubicación" ).Show();
                return;
            }


            editor_Shared.putString("iTIPO_SCAN", "CODIGO");
            editor_Shared.commit();
            ValidaTipoScanedo();
           // new ToastLibrary(Activity_ScannerBarcode.this, "MODO ESCANEO POR CODIGO" ).Show();

            editor_Shared.putString("iACCION_INVENTARIO", "CODIGO");
            editor_Shared.commit();
            CargarxCodigo();
        }

    };

    View.OnClickListener OnClickListener_btnScanearPorUnidad = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            editor_Shared.putString("iTIPO_SCAN", "UNIDAD");
            editor_Shared.commit();
            ValidaTipoScanedo();
            new ToastLibrary(Activity_ScannerBarcode.this, "MODO ESCANEO POR UNIDAD" ).Show();

        }

    };




    @Override
    public void handleResult(Result rawResult) {
        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScannerView.resumeCameraPreview(Activity_ScannerBarcode.this);
                }
            }, 2000);



            //editor_Shared.putString("iCODIGO_IDEN", rawResult.getContents().trim());
            //editor_Shared.putString("iCODIGO_TIPO", rawResult.getBarcodeFormat().getName().trim());
            if(sharedSettings.getString("iCONTEO", "").toString().trim().equals("") || sharedSettings.getString("iCONTEO", "").toString().trim().equals("null"))
            {
                NuevoConteo();
                return;
            }

            if(sharedSettings.getString("iUBICACION", "").toString().trim().equals("") || sharedSettings.getString("iUBICACION", "").toString().trim().equals("null"))
            {
                new ToastLibrary(this, "Debe ingresar un codigo de ubicacion primero").Show();
                return;
            }

            marticulosDAO.getAllByCodigoBarras(rawResult.getContents().trim(), "XXX");

            if(marticulosDAO.lst!=null && marticulosDAO.lst.size()>0)
            {
                mp = MediaPlayer.create(Activity_ScannerBarcode.this, R.raw.barcode);
                mp.start();
            inventarioBE = new S_Inv_InventarioBE();
            inventarioBE.setCONTEO( Integer.valueOf(sharedSettings.getString("iCONTEO", "0").toString()));
            inventarioBE.setCODIGO_BARRA(rawResult.getContents().trim());
                inventarioBE.setUBICACION(sharedSettings.getString("iUBICACION", "").toString());
                inventarioBE.setCOD_ALM(sharedSettings.getString("iCOD_ALM", "").toString());
                inventarioBE.setMES( Integer.valueOf(funciones.Month(funciones.FechaActual())));
                inventarioBE.setANIO( Integer.valueOf(funciones.Year(funciones.FechaActual())));
            inventarioBE.setCANTIDAD(1);
            inventarioBE.setESTADO(40003);
            inventarioBE.setFECHA_REGISTRO(funciones.FechaActualNow());
            inventarioBE.setFECHA_MODIFICACION(funciones.FechaActualNow());
            inventarioBE.setUSUARIO_REGISTRO(sharedSettings.getString("USUARIO", "").toString());
            inventarioBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
            inventarioBE.setPC_REGISTRO(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            inventarioBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            inventarioBE.setIP_REGISTRO(sharedSettings.getString("sIMEI", "").toString());
            inventarioBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());





                inventarioBE.setCOD_ART(marticulosDAO.lst.get(0).getCOD_ART());
                inventarioBE.setDESCRIPCION(marticulosDAO.lst.get(0).getDESCRIPCION());


                editor_Shared.putString("iCODIGO_BARRA", inventarioBE.getCODIGO_BARRA());
                editor_Shared.putString("iCOD_ART", inventarioBE.getCOD_ART());
            editor_Shared.putString("iUBICACION", inventarioBE.getUBICACION());
            editor_Shared.commit();

            if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("CANTIDAD"))
            {
                editor_Shared.putString("iACCION_INVENTARIO", "SCAN");
                editor_Shared.commit();

                CargarCantidad(inventarioBE);
            }
            else  if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("UNIDAD"))
            {
                inventarioDAO.grabar(inventarioBE);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            else
            {
                new ToastLibrary(this, "Debe elegir un modo de escaneo").Show();
                mp = MediaPlayer.create(Activity_ScannerBarcode.this, R.raw.no);
                mp.start();
            }

            }
            else
            {
                new ToastLibrary(this, "No se encontro el codigo de barras en la Base de Datos de Articulos").Show();
                mp = MediaPlayer.create(Activity_ScannerBarcode.this, R.raw.no);
                mp.start();


            }


        } catch (IllegalStateException e) {
            new ToastLibrary(this, e.getMessage()).Show();
        }
    }

    private void Cargar(String conteo, String codArt, String ubicacion, String mes, String anio ) {

        lblTotalXArticulo.setText("");
        txtUbicacionScan.setText("");
        txtTotalArticulos.setText("");
        txtTotalXArticulo.setText("");

        inventarioDAO.getAll(conteo, codArt,ubicacion, mes, anio);
        inventarioDAO2.getAll(conteo,"XXX",ubicacion, mes, anio);

        if(inventarioDAO!=null && inventarioDAO.lst.size()>0)
        {
            lblTotalXArticulo.setText(inventarioDAO.lst.get(0).getCOD_ART());
            txtTotalXArticulo.setText(inventarioDAO.lst.get(0).getCANTIDAD().toString());
            txtUbicacionScan.setText(inventarioDAO.lst.get(0).getUBICACION());
            if (!inventarioDAO.lst.get(0).getDESCRIPCION().equals(""))
            {
                lblTotalXArticulo.setText(inventarioDAO.lst.get(0).getCOD_ART() +"\n"+inventarioDAO.lst.get(0).getDESCRIPCION());
            }
        }
        else
        {
            txtUbicacionScan.setText(sharedSettings.getString("iUBICACION", "").toString());
            lblTotalXArticulo.setText("");
            txtTotalXArticulo.setText("0");
        }

        if(inventarioDAO2!=null && inventarioDAO2.lst.size()>0)
        {
            Integer total =0;
            for (Integer i = 0; i< inventarioDAO2.lst.size(); i++ )
            {
                total = total + inventarioDAO2.lst.get(i).getCANTIDAD();
            }

            txtTotalArticulos.setText(total.toString());
        }
        else
        {
            txtTotalArticulos.setText("0");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setAspectTolerance(0.2f);
        mScannerView.setFlash(mFlash);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }
/*
    public void toggleFlash(View v) {
        try {
            mFlash = !mFlash;
            mScannerView.setFlash(mFlash);
        } catch (Exception e) {
            new ToastLibrary(this, e.getMessage()).Show();
        }
    }
*/
    @Override
    public void onCantidadSI(Integer Cantidad) {
        inventarioBE.setCANTIDAD(Cantidad);
        inventarioDAO.grabar(inventarioBE);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCantidadNO() {

        mScannerView.startCamera();
    }

    @Override
    public void onUbicacionSI(String Ubicacion) {

        Cargar(sharedSettings.getString("iCONTEO", "0").toString(), "XXX",  Ubicacion, funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));

        mScannerView.startCamera();
    }

    @Override
    public void onUbicacionNO() {
        mScannerView.startCamera();
    }

    @Override
    public void onActualizarSI(S_Inv_InventarioBE objBE) {
        objBE.setFECHA_MODIFICACION(new Funciones().FechaActualNow());
        objBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
        objBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
        objBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
        new S_Inv_InventarioDAO().update(objBE);
        Cargar(objBE.getCONTEO().toString(), objBE.getCOD_ART(), objBE.getUBICACION(), objBE.getMES().toString(), objBE.getANIO().toString());

    }

    @Override
    public void onCodigoSI(Integer Cantidad, String CodArt) {
        marticulosDAO.getAllByCodArt(CodArt);

        if(marticulosDAO.lst!=null && marticulosDAO.lst.size()>0) {
            inventarioBE = new S_Inv_InventarioBE();
            inventarioBE.setCOD_ART(CodArt);
            inventarioBE.setCONTEO(Integer.valueOf(sharedSettings.getString("iCONTEO", "0").toString()));
            inventarioBE.setCODIGO_BARRA(marticulosDAO.lst.get(0).getC_ARTICULO());
            inventarioBE.setUBICACION(sharedSettings.getString("iUBICACION", "").toString());
            inventarioBE.setCOD_ALM(sharedSettings.getString("iCOD_ALM", "").toString());
            inventarioBE.setMES(Integer.valueOf(funciones.Month(funciones.FechaActual())));
            inventarioBE.setANIO(Integer.valueOf(funciones.Year(funciones.FechaActual())));
            inventarioBE.setCANTIDAD(Cantidad);
            inventarioBE.setESTADO(40003);
            inventarioBE.setFECHA_REGISTRO(funciones.FechaActualNow());
            inventarioBE.setFECHA_MODIFICACION(funciones.FechaActualNow());
            inventarioBE.setUSUARIO_REGISTRO(sharedSettings.getString("USUARIO", "").toString());
            inventarioBE.setUSUARIO_MODIFICACION(sharedSettings.getString("USUARIO", "").toString());
            inventarioBE.setPC_REGISTRO(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            inventarioBE.setPC_MODIFICACION(sharedSettings.getString("NOMBRE_TELEFONO", "").toString());
            inventarioBE.setIP_REGISTRO(sharedSettings.getString("sIMEI", "").toString());
            inventarioBE.setIP_MODIFICACION(sharedSettings.getString("sIMEI", "").toString());
            inventarioBE.setDESCRIPCION(marticulosDAO.lst.get(0).getDESCRIPCION());

            editor_Shared.putString("iCODIGO_BARRA", inventarioBE.getCODIGO_BARRA());
            editor_Shared.putString("iCOD_ART", inventarioBE.getCOD_ART());
            editor_Shared.putString("iUBICACION", inventarioBE.getUBICACION());
            editor_Shared.commit();

            mp = MediaPlayer.create(Activity_ScannerBarcode.this, R.raw.barcode);
            mp.start();

            inventarioDAO.grabar(inventarioBE);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast toastCodigo = Toast.makeText(getApplicationContext(),"El codigo ingresado no existe", Toast.LENGTH_SHORT);
            toastCodigo.show();

        }

        ValidaEscaneoXCodigo();
    }

    @Override
    public void onCodigoNO() {
        ValidaEscaneoXCodigo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_flash, menu);
        final MenuItem action_flash = menu.findItem(R.id.action_ordenfresh);
        final MenuItem action_list = menu.findItem(R.id.action_list);

        if ( sharedSettings.getString("mFlash", "0").equals("1"))
        {
            mFlash = true;
            action_flash.setIcon(R.drawable.ic_action_flash_off);
        }
        else
        {
            mFlash = false;
            action_flash.setIcon(R.drawable.ic_action_flash_on);
        }

        mScannerView.setFlash(mFlash);

        action_flash.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                    mFlash = !mFlash;
                    mScannerView.setFlash(mFlash);

                    if (mFlash)
                    {
                        action_flash.setIcon(R.drawable.ic_action_flash_off);
                        editor_Shared.putString("mFlash", "1");

                    }
                    else
                    {
                        action_flash.setIcon(R.drawable.ic_action_flash_on);
                        editor_Shared.putString("mFlash", "0");
                    }
                editor_Shared.commit();

                return false;
            }
        });

        action_list.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    lblTotalXArticulo.setText("");
                    txtTotalArticulos.setText("");
                    txtTotalXArticulo.setText("");

                    editor_Shared.putString("iACCION_INVENTARIO", "SCAN");
                    editor_Shared.commit();
                    Intent intent = new Intent(getApplicationContext(), Activity_Scanner.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex) {
                    Toast toastCodigo = Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT);
                    toastCodigo.show();
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onTablaAuxiliarSI() {
        Cargar(sharedSettings.getString("iCONTEO", "").toString(), "XXX",  sharedSettings.getString("iUBICACION", "").toString(), funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));
    }

    @Override
    public void onConfirmacionSI() {

        if(!sharedSettings.getString("iCONTEO", "0").toString().equals("0")){

            if (funciones.isConnectingToInternet(getApplicationContext())) {
                String Confirmacion = inventarioDAO.updateEstadoxConteo(Integer.valueOf(sharedSettings.getString("iCONTEO", "").toString()), sharedSettings.getString("USUARIO", "").toString(), 40006);

                if (Confirmacion.equals("OK")) {
                    new InsertInventarioAsyncTask().execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blinventario_insert);
                }
            }
            else {
                Toast toastCodigo = Toast.makeText(getApplicationContext(),"No cuenta con internet, por favor verifique su plan de datos, o conectese a una red WIFI", Toast.LENGTH_SHORT);
                toastCodigo.show();
                return;

            }

        }



            inventarioDAO.getMaxConteo(funciones.Month(funciones.FechaActual()), funciones.Year(funciones.FechaActual()));

            if (inventarioDAO.lst != null && inventarioDAO.lst.size() > 0) {
                editor_Shared.putString("iCONTEO", String.valueOf(inventarioDAO.lst.get(0).getCONTEO() + 1));
                editor_Shared.commit();

                if (Integer.parseInt(sharedSettings.getString("iCONTEO", "0").toString()) > 0) {
                    btnNuevoConteo.setText("NUEVO CONTEO (" + sharedSettings.getString("iCONTEO", "0").toString() + ")");
                }
                NuevaUbicacion();
            }


    }

    @Override
    public void onConfirmacionNO() {


        mScannerView.startCamera();
    }

    private void ValidaEscaneoXCodigo() {

        if (sharedSettings.getString("iTIPO_SCAN", "").toString().equals("CODIGO"))
        {
            editor_Shared.putString("iTIPO_SCAN", "UNIDAD");
            editor_Shared.commit();
            ValidaTipoScanedo();
        }
    }


    //INSERTAR  Y ACTUALIZAR INVENTARIO
    private class InsertInventarioAsyncTask extends AsyncTask<String, String, JSONObject> {
        private volatile boolean running = true;
        private ProgressDialog progressDialog = null;

        @Override
        protected JSONObject doInBackground(String... p) {
            return new S_Inv_InventarioBL().InsertRest(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {

                if (result.getString("status").equals("0") || result.getString("status").equals("false")) {

                    if (result.getString("status").equals("false") && result.getString("message").contains("reset"))
                    {
                        Toast toastCodigo = Toast.makeText(getApplicationContext(),"Error, verifique su conexion a internet.", Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        return;
                    }
                    else
                    {

                        Toast toastCodigo = Toast.makeText(getApplicationContext(),"Error al ENVIAR los registros.", Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        return;
                    }


                } else {
                    if (!result.getString("MSG").toString().trim().equals("-")) {
                        inventarioDAO.updateEstadoxConteo( Integer.valueOf(result.getString("CONTEO").toString()), sharedSettings.getString("USUARIO", "").toString(), 40013);
                        Toast toastCodigo = Toast.makeText(getApplicationContext(),result.getString("MSG").toString().trim(), Toast.LENGTH_SHORT);
                        toastCodigo.show();
                        return;
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }




}

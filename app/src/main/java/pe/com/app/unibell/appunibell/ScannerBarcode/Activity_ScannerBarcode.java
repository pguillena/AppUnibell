package pe.com.app.unibell.appunibell.ScannerBarcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import pe.com.app.unibell.appunibell.Main.MainActivity;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_ScannerBarcode extends AppCompatActivity
        implements ZBarScannerView.ResultHandler {
    private static final String FLASH_STATE = "FLASH_STATE";
    private ViewGroup contentFrame;
    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private MediaPlayer mp;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Funciones funciones = new Funciones();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Escanee Código");

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

        } catch (Exception ex) {
            new ToastLibrary(this, ex.getMessage()).Show();
        }
    }


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

            mp = MediaPlayer.create(Activity_ScannerBarcode.this, R.raw.ok);
            mp.start();

            Intent intent = new Intent();
            editor_Shared.putString("iCODIGO_IDEN", rawResult.getContents().trim());
            editor_Shared.putString("iCODIGO_TIPO", rawResult.getBarcodeFormat().getName().trim());
            editor_Shared.commit();

            setResult(RESULT_OK, intent);
            finish();

        } catch (IllegalStateException e) {
            new ToastLibrary(this, e.getMessage()).Show();
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

    public void toggleFlash(View v) {
        try {
            mFlash = !mFlash;
            mScannerView.setFlash(mFlash);
        } catch (Exception e) {
            new ToastLibrary(this, e.getMessage()).Show();
        }
    }
}

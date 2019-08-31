package pe.com.app.unibell.appunibell.Reportes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import pe.com.app.unibell.appunibell.R;

public class Activity_Reportes extends AppCompatActivity {

    private ListView cr_lsdetalle;
    private FloatingActionButton cr_fabbuscar;
    private TextView cr_btnverdetalle,cr_lbltotalg;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobranza_reportes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("REPORTES");
        getSupportActionBar().setSubtitle("");

        cr_lsdetalle=(ListView)findViewById(R.id.cr_lsdetalle);
        cr_fabbuscar=(FloatingActionButton)findViewById(R.id.cr_fabbuscar);
        cr_btnverdetalle=(TextView)findViewById(R.id.cr_btnverdetalle);
        cr_lbltotalg=(TextView)findViewById(R.id.cr_lbltotalg);

        cr_btnverdetalle.setOnClickListener(OnClickListener_cr_btnverdetalle);
        cr_fabbuscar.setOnClickListener(OnClickListener_cr_fabbuscar);

        Cargar();
    }

    private void Cargar(){

    }

    View.OnClickListener OnClickListener_cr_btnverdetalle = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    View.OnClickListener OnClickListener_cr_fabbuscar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Activity_Reportes.this, Activity_Reporte_Filtro.class);
            startActivityForResult(intent,1);

        }
    };


}

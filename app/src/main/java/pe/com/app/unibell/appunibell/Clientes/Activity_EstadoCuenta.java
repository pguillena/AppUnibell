package pe.com.app.unibell.appunibell.Clientes;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.AD.Cliente_EstadoCuenta_Adapter;
import pe.com.app.unibell.appunibell.DAO.FactCobDAO;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Activity_EstadoCuenta extends AppCompatActivity {

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private TextView ec_lblcliente,ec_txtsaldovencido,ec_txtsaldoxvencer,ec_txttboleta,ec_txttfacturas,ec_txttnotacredito,ec_txttnotadebito;
    private ListView ec_lsdet;
    private Cliente_EstadoCuenta_Adapter cliente_estadoCuenta_adapter = null;
    private FactCobDAO factCobDAO = new FactCobDAO();
    private Funciones funciones=new Funciones();

    private double dDedaVencida=0.0;
    private double dDedaxVencer=0.0;
    private double dTotalBoletas=0.0;
    private double dTotalFacturas=0.0;
    private double dTotalNCredito=0.0;
    private double dTotalNDebito=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta_cliente);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Estado de cuenta del cliente");

            sharedSettings=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared=getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF),MODE_PRIVATE).edit();

            ec_lblcliente=(TextView) findViewById(R.id.ec_lblcliente);
            ec_txtsaldovencido=(TextView) findViewById(R.id.ec_txtsaldovencido);
            ec_txtsaldoxvencer=(TextView) findViewById(R.id.ec_txtsaldoxvencer);
            ec_txttboleta=(TextView) findViewById(R.id.ec_txttboleta);
            ec_txttfacturas=(TextView) findViewById(R.id.ec_txttfacturas);
            ec_txttnotacredito=(TextView) findViewById(R.id.ec_txttnotacredito);
            ec_txttnotadebito=(TextView) findViewById(R.id.ec_txttnotadebito);  ec_txtsaldovencido=(TextView) findViewById(R.id.ec_txtsaldovencido);
            ec_txtsaldoxvencer=(TextView) findViewById(R.id.ec_txtsaldoxvencer);
            ec_txttboleta=(TextView) findViewById(R.id.ec_txttboleta);
            ec_txttfacturas=(TextView) findViewById(R.id.ec_txttfacturas);
            ec_txttnotacredito=(TextView) findViewById(R.id.ec_txttnotacredito);
            ec_txttnotadebito=(TextView) findViewById(R.id.ec_txttnotadebito);

            ec_txtsaldovencido.setText(funciones.FormatDecimal(String.valueOf(dDedaVencida).trim().replace(",","")));
            ec_txtsaldoxvencer.setText(funciones.FormatDecimal(String.valueOf(dDedaxVencer).trim().replace(",","")) );
            ec_txttboleta.setText(funciones.FormatDecimal(String.valueOf(dTotalBoletas).trim().replace(",","")) );
            ec_txttfacturas.setText(funciones.FormatDecimal(String.valueOf(dTotalFacturas).trim().replace(",","")) );
            ec_txttnotacredito.setText(funciones.FormatDecimal(String.valueOf(dTotalNCredito).trim().replace(",","")) );
            ec_txttnotadebito.setText(funciones.FormatDecimal(String.valueOf(dTotalNCredito).trim().replace(",","")));

            ec_lsdet=(ListView)findViewById(R.id.ec_lsdet);
            ec_lblcliente.setText(funciones.LetraCapital(sharedSettings.getString("RAZON_SOCIAL", "").toString()));

            LayoutInflater inflater = getLayoutInflater();
            //ViewGroup header = (ViewGroup)inflater.inflate(R.layout.item_clientes_estadocuenta_header,ec_lsdet,false);
            //ec_lsdet.addHeaderView(header);

            ec_lsdet.setOnItemClickListener(cl_ec_lsdet_OnItemClickListener);

            Cargar();

        } catch (Exception ex) {

        }
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

    private void Cargar(){
        try {
            new LoadClientesECuentaSQLite_AsyncTask().execute(
                    sharedSettings.getString("CODIGO_ANTIGUO", "").toString(), "XXX","XXX","XXX","1");
        } catch (Exception ex){

        }
    }


    AbsListView.OnItemClickListener cl_ec_lsdet_OnItemClickListener = new AbsListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private class LoadClientesECuentaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                factCobDAO.getEstadoCuentaCliente(p[0],p[1],p[2],p[3],p[4]);
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
                dDedaVencida=0.0;
                dDedaxVencer=0.0;
                dTotalBoletas=0.0;
                dTotalFacturas=0.0;
                dTotalNCredito=0.0;
                dTotalNCredito=0.0;

                cliente_estadoCuenta_adapter = new Cliente_EstadoCuenta_Adapter(getApplication(), 0, factCobDAO.lst);
                cliente_estadoCuenta_adapter.notifyDataSetChanged();
                ec_lsdet.setAdapter(cliente_estadoCuenta_adapter);

                for (int j = 0; j < cliente_estadoCuenta_adapter.lstFiltrado.size(); j++) {

                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getDIAS()>=0){
                        dDedaVencida+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }

                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getDIAS()<0){
                        dDedaxVencer+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }
                    //Boleta
                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getTIPDOC().toString().trim().equals("03")){
                        dTotalBoletas+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }
                    //Factura
                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getTIPDOC().toString().trim().equals("01")){
                        dTotalFacturas+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }
                    //Nota Credito
                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getTIPDOC().toString().trim().equals("07")){
                        dTotalNCredito+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }
                    //Nota Debito
                    if(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getTIPDOC().toString().trim().equals("08")){
                        dTotalNCredito+=Double.valueOf(cliente_estadoCuenta_adapter.lstFiltrado.get(j).getSALDO().toString());
                    }
                }
                ec_txtsaldovencido.setText(funciones.FormatDecimal(String.valueOf(dDedaVencida).trim().replace(",","")));
                ec_txtsaldoxvencer.setText(funciones.FormatDecimal(String.valueOf(dDedaxVencer).trim().replace(",","")) );
                ec_txttboleta.setText(funciones.FormatDecimal(String.valueOf(dTotalBoletas).trim().replace(",","")) );
                ec_txttfacturas.setText(funciones.FormatDecimal(String.valueOf(dTotalFacturas).trim().replace(",","")) );
                ec_txttnotacredito.setText(funciones.FormatDecimal(String.valueOf(dTotalNCredito).trim().replace(",","")) );
                ec_txttnotadebito.setText(funciones.FormatDecimal(String.valueOf(dTotalNCredito).trim().replace(",","")));

            } catch (Exception ex) {
                //Toast.makeText(getApplication(),getResources().getString(R.string.msg_nohayregistros), Toast.LENGTH_LONG).show();
            }
        }
    }


}

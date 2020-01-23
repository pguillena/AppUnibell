package pe.com.app.unibell.appunibell.Dialogs;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import pe.com.app.unibell.appunibell.AD.CtaBnco_Adapter;
import pe.com.app.unibell.appunibell.AD.ParTabla_Adapter;
import pe.com.app.unibell.appunibell.AD.S_Gem_Vendedor_Adapter;
import pe.com.app.unibell.appunibell.AD.S_Sem_Empresa_Adapter;
import pe.com.app.unibell.appunibell.AD.S_Sem_Local_Adapter;
import pe.com.app.unibell.appunibell.AD.Scanner_Adapter;
import pe.com.app.unibell.appunibell.AD.Tablas_Auxiliares_Adapter;
import pe.com.app.unibell.appunibell.AD.Ubicacion_Adapter;
import pe.com.app.unibell.appunibell.AD.Ubigeo_Adapter;
import pe.com.app.unibell.appunibell.DAO.CtaBncoDAO;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.ParTablaDAO;
import pe.com.app.unibell.appunibell.DAO.S_Gem_VendedorDAO;
import pe.com.app.unibell.appunibell.DAO.S_Gem_Vendedor_Codigo_antDAO;
import pe.com.app.unibell.appunibell.DAO.S_Inv_InventarioDAO;
import pe.com.app.unibell.appunibell.DAO.S_Sem_EmpresaDAO;
import pe.com.app.unibell.appunibell.DAO.S_Sem_LocalDAO;
import pe.com.app.unibell.appunibell.DAO.Tablas_AuxiliaresDAO;
import pe.com.app.unibell.appunibell.DAO.UbigeoDAO;
import pe.com.app.unibell.appunibell.R;

public class Dialog_Fragment_Auxiliar extends DialogFragment {
    public final static String TAG = "Dialog_Fragment_Auxiliar";
    private ListView tab_lvauxiliar;
    private TextView tba_lbltitulo;
    private EditText tba_edttitulo;

    private Tablas_AuxiliaresDAO tablas_auxiliaresDAO = new Tablas_AuxiliaresDAO();
    private S_Gem_VendedorDAO s_gem_vendedorDAO = new S_Gem_VendedorDAO();
    private UbigeoDAO ubigeoDAO = new UbigeoDAO();
    private ParTablaDAO parTablaDAO = new ParTablaDAO();
    private CtaBncoDAO ctaBncoDAO = new CtaBncoDAO();
    private S_Inv_InventarioDAO inventarioDAO = new S_Inv_InventarioDAO();

    private S_Sem_EmpresaDAO s_sem_empresaDAO = new S_Sem_EmpresaDAO();
    private S_Sem_LocalDAO s_sem_localDAO = new S_Sem_LocalDAO();

    private S_Sem_Empresa_Adapter s_sem_empresa_adapter = null;
    private S_Sem_Local_Adapter s_sem_local_adapter = null;
    private S_Gem_Vendedor_Adapter s_gem_vendedor_adapter = null;
    private Ubigeo_Adapter ubigeo_adapter = null;
    private Ubicacion_Adapter ubicacion_adapter = null;

    WindowManager.LayoutParams lWindowParams;

    private Tablas_Auxiliares_Adapter tablas_auxiliares_adapter = null;
    private ParTabla_Adapter parTabla_adapter = null;
    public CtaBnco_Adapter ctaBnco_adapter = null;

    private Dialog_Fragment_AuxiliarListener dialog_fragment_categoriaListener;

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Integer iTabla=0,iRol;

    public interface Dialog_Fragment_AuxiliarListener {
        void onTablaAuxiliarSI();

    }
    public void setAuxiliarDialogfragmentListener(Dialog_Fragment_AuxiliarListener dialog_fragment_auxiliarListener,Integer iTabla,Integer iRol) {
        this.dialog_fragment_categoriaListener = dialog_fragment_auxiliarListener;
        this.iTabla=iTabla;
        this.iRol=iRol;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_auxiliar, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


       lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.copyFrom(getDialog().getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.FILL_PARENT; // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        /*   getDialog().show();// I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
*/

       // getActivity().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getDialog().show();
        getDialog().getWindow().setLayout(lWindowParams.width, lWindowParams.height);


        sharedSettings=getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE);
        editor_Shared= getActivity().getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), getActivity().MODE_PRIVATE).edit();

        tab_lvauxiliar = (ListView) view.findViewById(R.id.tab_lvauxiliar);
        tba_lbltitulo = (TextView) view.findViewById(R.id.tba_lbltitulo);
        tba_edttitulo = (EditText) view.findViewById(R.id.tba_edttitulo);

        switch(iTabla) {
            case 23:
                tba_lbltitulo.setText("Elegir nuevo día de visita");
                break;
            case 22:
                tba_lbltitulo.setText("Elegir la nueva frecuencia de visita");
                break;
            case 45:
                tba_lbltitulo.setText("¿Por qué no se vendió?");
                break;
            case 44:
                tba_lbltitulo.setText("¿Por qué no se cobro?");
                break;
            case 52:
                tba_lbltitulo.setText("Seleccione una objeción");
                break;
            case 14:
                tba_lbltitulo.setText("Seleccione una forma de pago");
                break;
            case 100:
                tba_lbltitulo.setText("Seleccione un estado");
                break;
            case 200:
                tba_lbltitulo.setText("Seleccione una cuenta");
                break;
            case 300:
                tba_lbltitulo.setText("Seleccione una empresa");

                break;
            case 400:
                tba_lbltitulo.setText("Seleccione un local");
                break;
            case 500:
                tba_lbltitulo.setText("Seleccione un documento");
                break;
            case 600:
                tba_lbltitulo.setText("Seleccione un moneda");
                break;
            case 700:
                tba_lbltitulo.setText("Seleccione un cobrador");
                break;
            case 800:
                tba_lbltitulo.setText("Seleccione un distrito");
                break;
            case 900:
                tba_lbltitulo.setText("Seleccione una ubicación");
                break;
        }
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            this.Cargar();

        } catch (Exception ex) {
            //Toast.makeText(getApplication(),getResources().getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }

        tba_edttitulo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,int count) {


                if(iTabla!=100 && iTabla!=200 && iTabla!=300 && iTabla!=400 && iTabla!=700&& iTabla!=800&& iTabla!=900) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        Tablas_Auxiliares_Adapter ca = (Tablas_Auxiliares_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                //ESTADO,TIPO DOC,MONEDA
                if(!s.equals("") && (iTabla==100 || iTabla==500 || iTabla==600)) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        ParTabla_Adapter ca = (ParTabla_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                if(!s.equals("") && iTabla==200) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        CtaBnco_Adapter ca = (CtaBnco_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                if(!s.equals("") && iTabla==300) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        S_Sem_Empresa_Adapter ca = (S_Sem_Empresa_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                if(!s.equals("") && iTabla==400) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        S_Sem_Local_Adapter ca = (S_Sem_Local_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                if(!s.equals("") && iTabla==700) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        S_Gem_Vendedor_Adapter ca = (S_Gem_Vendedor_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }


                if(!s.equals("") && iTabla==800) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        Ubigeo_Adapter ca = (Ubigeo_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

                if(!s.equals("") && iTabla==900) {
                    if(tab_lvauxiliar.getAdapter()!=null) {
                        Ubicacion_Adapter ca = (Ubicacion_Adapter) tab_lvauxiliar.getAdapter();
                        ca.getFilter().filter(s.toString());
                    }
                }

            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        tab_lvauxiliar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(iTabla==100 || iTabla==500 || iTabla==600) {
                    editor_Shared.putString("ICODTABAUX", parTabla_adapter.getItem(position).getIDTABLA().toString());
                    editor_Shared.putString("IDESTABAUX", parTabla_adapter.getItem(position).getDESCRIPCION());
                    editor_Shared.commit();
                }

                if(iTabla==200) {
                    editor_Shared.putString("ICODTABAUX", ctaBnco_adapter.getItem(position).getCODIGO().toString());
                    editor_Shared.putString("IDESTABAUX", ctaBnco_adapter.getItem(position).getDESCRIPCION());
                    editor_Shared.commit();
                }

               //EMPRESAS
                if(iTabla==300) {
                    editor_Shared.putString("iID_EMPRESA", s_sem_empresa_adapter.getItem(position).getID_EMPRESA().toString());
                    editor_Shared.putString("NOM_EMPRESA", s_sem_empresa_adapter.getItem(position).getNOMBRE());
                    editor_Shared.commit();
                }
               //LOCALES
                if(iTabla==400) {
                    editor_Shared.putString("iID_LOCAL", s_sem_local_adapter.getItem(position).getID_LOCAL().toString());
                    editor_Shared.putString("NOM_LOCAL", s_sem_local_adapter.getItem(position).getNOMBRE());
                    editor_Shared.commit();
                }

                //VENDEDORES
                if(iTabla==700) {
                    editor_Shared.putString("iID_COBRADOR", s_gem_vendedor_adapter.getItem(position).getID_PERSONA().toString());
                    editor_Shared.putString("NOM_COBRADOR", s_gem_vendedor_adapter.getItem(position).getNOMBRE_COMPLETO());
                    editor_Shared.commit();
                }

                //DISTRITOS
                if(iTabla==800) {
                    editor_Shared.putString("iCOD_UBIGEO", ubigeo_adapter.getItem(position).getCODUBIGEO().toString());
                    editor_Shared.putString("iNOM_UBIGEO", ubigeo_adapter.getItem(position).getNOMBRE());
                    editor_Shared.commit();
                }
                //UBICACION INVENTARIO
                if(iTabla==900) {
                    editor_Shared.putString("iUBICACION", ubicacion_adapter.getItem(position).getUBICACION().toString());
                    editor_Shared.putString("iCOD_ALM", ubicacion_adapter.getItem(position).getCOD_ALM().toString());
                    editor_Shared.commit();
                }

                if(iTabla!=100 && iTabla!=200 && iTabla!=300 && iTabla!=400 && iTabla!=500 && iTabla!=600 && iTabla!=700 && iTabla!=800&& iTabla!=900) {
                    editor_Shared.putString("ICODTABAUX", tablas_auxiliares_adapter.getItem(position).getCODIGO().toString());
                    editor_Shared.putString("IDESTABAUX", tablas_auxiliares_adapter.getItem(position).getDESCRIPCION());
                    editor_Shared.putString("COD_FPAGO", tablas_auxiliares_adapter.getItem(position).getCODIGO().toString());
                    editor_Shared.commit();
                }


                if (dialog_fragment_categoriaListener != null) {
                    dialog_fragment_categoriaListener.onTablaAuxiliarSI();
                }

                dismissAllowingStateLoss();
            }
        });

        return view;
    }

    private  void Cargar(){
        //TABLA DE TABLAS
        if(iTabla==100) {
            if(iRol==0) {
                new LoadParTablaSQLite_AsyncTask().execute("0", sharedSettings.getString("ROL", "").toString());
            }else{
                new LoadParTablaSQLite_AsyncTask().execute("1",sharedSettings.getString("ROL", "").toString());
            }
        }
        //TIPO DOCUMENTO
        if(iTabla==500) {
            new LoadParTablaSQLite_AsyncTask().execute("2",sharedSettings.getString("ROL", "").toString());
        }
        //MONEDA
        if(iTabla==600) {
            new LoadParTablaSQLite_AsyncTask().execute("3",sharedSettings.getString("ROL", "").toString());
        }

       //CTA BANCOS
        if(iTabla==200) {
             new LoadCtaBancosSQLite_AsyncTask().execute(
            sharedSettings.getString("iID_EMPRESA", "0").toString(),
            sharedSettings.getString("iID_LOCAL", "0").toString(),
            sharedSettings.getString("COD_BANCO", "0").toString(),
            "",sharedSettings.getString("COD_FPAGO", "0").toString()
            );
        }

        //EMPRESA
        if(iTabla==300) {
            new LoadEmpresaSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_VENDEDOR", "0").toString()
            );
        }

        //LOCALES
        if(iTabla==400) {
            new LoadLocalSQLite_AsyncTask().execute(
                    sharedSettings.getString("iID_EMPRESA", "0").toString(),
                    sharedSettings.getString("iID_VENDEDOR", "0").toString()
            );
        }

        //VENDEDORES
        if(iTabla==700) {
            new LoadVendedoresSQLite_AsyncTask().execute("0");
        }

        //VENDEDORES
        if(iTabla==800) {
            new LoadUbigeoSQLite_AsyncTask().execute("0");
        }
        //INVENTARIO
        if(iTabla==900) {
            new LoadInventarioSQLite_AsyncTask().execute();
        }

        //AUXILIARES
        if(iTabla!=100 && iTabla!=200 && iTabla!=300 && iTabla!=400 && iTabla!=500 && iTabla!=600 && iTabla!=700&& iTabla!=800&& iTabla!=900) {
            new LoadAuxiliaresSQLite_AsyncTask().execute(iTabla.toString(), iRol.toString());
        }

    }



    private class LoadVendedoresSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                s_gem_vendedorDAO.getAll(p[0]);
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
                s_gem_vendedor_adapter = new S_Gem_Vendedor_Adapter(getContext(), 0, s_gem_vendedorDAO.lst);
                s_gem_vendedor_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(s_gem_vendedor_adapter);
            } catch (Exception ex) {
            }
        }
    }


    private class LoadUbigeoSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                ubigeoDAO.getDistritos("150101"); //LISTAR DISTRITOS DE LIMA 150101
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
                ubigeo_adapter = new Ubigeo_Adapter(getContext(), 0, ubigeoDAO.lst);
                ubigeo_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(ubigeo_adapter);
            } catch (Exception ex) {
            }
        }
    }


    private class LoadInventarioSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                inventarioDAO.getGroupUbicacion();
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
                ubicacion_adapter = new Ubicacion_Adapter(getContext(), 0, inventarioDAO.lst);
                ubicacion_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(ubicacion_adapter);
            } catch (Exception ex) {
            }
        }
    }



    private class LoadAuxiliaresSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();

                tablas_auxiliaresDAO.getAll(p[0], p[1]);
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
                tablas_auxiliares_adapter = new Tablas_Auxiliares_Adapter(getContext(), 0, tablas_auxiliaresDAO.lst);
                tablas_auxiliares_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(tablas_auxiliares_adapter);
            } catch (Exception ex) {
            }
        }
    }

    private class LoadParTablaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                parTablaDAO.getAll(p[0],p[1]);
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
                parTabla_adapter = new ParTabla_Adapter(getContext(), 0, parTablaDAO.lst);
                parTabla_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(parTabla_adapter);
            } catch (Exception ex) {
            }
        }
    }

    private class LoadCtaBancosSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                ctaBncoDAO.getAllBY(p[0],p[1],p[2],p[3],p[4]);
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
                ctaBnco_adapter = new CtaBnco_Adapter(getContext(), 0, ctaBncoDAO.lst);
                ctaBnco_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(ctaBnco_adapter);
            } catch (Exception ex) {
            }
        }
    }

    private class LoadEmpresaSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                s_sem_empresaDAO.getAllBY(p[0]);
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
                s_sem_empresa_adapter = new S_Sem_Empresa_Adapter(getContext(), 0, s_sem_empresaDAO.lst);
                s_sem_empresa_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(s_sem_empresa_adapter);
            } catch (Exception ex) {
            }
        }
    }

    private class LoadLocalSQLite_AsyncTask extends AsyncTask<String, String,String> {
        @Override
        protected String doInBackground(String... p) {
            try {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                s_sem_localDAO.getAllBY(p[0],p[1]);
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
                s_sem_local_adapter = new S_Sem_Local_Adapter(getContext(), 0, s_sem_localDAO.lst);
                s_sem_local_adapter.notifyDataSetChanged();
                tab_lvauxiliar.setAdapter(s_sem_local_adapter);
            } catch (Exception ex) {
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        getDialog().getWindow().setAttributes(lWindowParams);
    }

}
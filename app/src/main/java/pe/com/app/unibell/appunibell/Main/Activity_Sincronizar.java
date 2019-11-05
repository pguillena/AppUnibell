package pe.com.app.unibell.appunibell.Main;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import pe.com.app.unibell.appunibell.AD.SincronizarAdapter;
import pe.com.app.unibell.appunibell.BE.SincronizarBE;
import pe.com.app.unibell.appunibell.BL.CabfcobBL;
import pe.com.app.unibell.appunibell.BL.Cliente_VendedorBL;
import pe.com.app.unibell.appunibell.BL.ClientesBL;
import pe.com.app.unibell.appunibell.BL.CtaBncoBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_DetBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_MovBL;
import pe.com.app.unibell.appunibell.BL.DocuventBL;
import pe.com.app.unibell.appunibell.BL.Dpm_Packing_CabBL;
import pe.com.app.unibell.appunibell.BL.Dpm_Packing_DetBL;
import pe.com.app.unibell.appunibell.BL.Dpm_Personal_TransporteBL;
import pe.com.app.unibell.appunibell.BL.FactCobBL;
import pe.com.app.unibell.appunibell.BL.Gem_BancoBL;
import pe.com.app.unibell.appunibell.BL.MvendedorBL;
import pe.com.app.unibell.appunibell.BL.ParTablaBL;
import pe.com.app.unibell.appunibell.BL.Recibos_CcobranzaBL;
import pe.com.app.unibell.appunibell.BL.S_Gea_Vendedor_ClienteBL;
import pe.com.app.unibell.appunibell.BL.S_Gea_Vendedor_SupervisorBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_ClienteBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_Cliente_Codigo_AntBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_PersonaBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_Persona_DireccionBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_VendedorBL;
import pe.com.app.unibell.appunibell.BL.S_Gem_Vendedor_Codigo_AntBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_AccesosBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_Usuario_AccionBL;
import pe.com.app.unibell.appunibell.BL.S_Sea_Usuario_LocalBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_EmpresaBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_LocalBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_PerfilBL;
import pe.com.app.unibell.appunibell.BL.S_Sem_UsuarioBL;
import pe.com.app.unibell.appunibell.BL.S_Vem_CorrelativoBL;
import pe.com.app.unibell.appunibell.BL.S_gem_TipoCambioBL;
import pe.com.app.unibell.appunibell.BL.SucursalesBL;
import pe.com.app.unibell.appunibell.BL.Tablas_AuxiliaresBL;
import pe.com.app.unibell.appunibell.BL.UbigeoBL;
import pe.com.app.unibell.appunibell.BL.Vem_Cobrador_ZonaBL;
import pe.com.app.unibell.appunibell.BL.VisitaCabBL;
import pe.com.app.unibell.appunibell.BL.VisitaDetBL;
import pe.com.app.unibell.appunibell.BL.VisitaMovCambioBL;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SincronizaDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.Constants;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestResult;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Activity_Sincronizar extends AppCompatActivity {
    private GridView sin_lvprocesos;
    private SincronizarAdapter sincronizarAdapter = null;
    private SincronizaDAO sincronizaDAO = new SincronizaDAO();
    private SincronizarBE sincronizarBE=null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private Funciones funciones=new Funciones();

    private Dialog_Fragment_Progress cabfcobPG;
    private Dialog_Fragment_Progress clientesPG;
    private Dialog_Fragment_Progress ctaBncoPG;
    private Dialog_Fragment_Progress documentos_cobra_cabPG;
    private Dialog_Fragment_Progress documentos_cobra_detPG;
    private Dialog_Fragment_Progress documentos_cobra_movPG;
    private Dialog_Fragment_Progress factCobPG;
    private Dialog_Fragment_Progress gem_bancoPG;
    private Dialog_Fragment_Progress mvendedorPG;
    private Dialog_Fragment_Progress parTablaPG;
    private Dialog_Fragment_Progress recibos_ccobranzaPG;
    private Dialog_Fragment_Progress s_gem_clientePG;
    private Dialog_Fragment_Progress s_gem_persona_direccionPG;
    private Dialog_Fragment_Progress s_gem_personaPG;
    private Dialog_Fragment_Progress s_gem_vendedorPG;
    private Dialog_Fragment_Progress s_sea_accesosPG;
    private Dialog_Fragment_Progress s_sea_usuario_accionPG;
    private Dialog_Fragment_Progress s_sea_usuario_localPG;
    private Dialog_Fragment_Progress s_sem_empresaPG;
    private Dialog_Fragment_Progress s_sem_localPG;
    private Dialog_Fragment_Progress s_sem_perfilPG;
    private Dialog_Fragment_Progress s_sem_usuarioPG;
    private Dialog_Fragment_Progress sucursalesPG;
    private Dialog_Fragment_Progress tablas_auxiliaresPG;
    private Dialog_Fragment_Progress s_gem_cliente_codigo_antPG;
    private Dialog_Fragment_Progress s_gea_vendedor_clientePG;
    private Dialog_Fragment_Progress s_gem_vendedor_codigo_antPG;

    private Dialog_Fragment_Progress vem_visitaCabPG;
    private Dialog_Fragment_Progress vem_visitaDetPG;
    private Dialog_Fragment_Progress vem_cliente_vendedorPG;
    private Dialog_Fragment_Progress vem_visitaMovCambioPG;

    private Dialog_Fragment_Progress ubigeoPG;

    private Dialog_Fragment_Progress docuventPG;
    private Dialog_Fragment_Progress dpm_Packing_CabPG;
    private Dialog_Fragment_Progress dpm_Packing_DetBLPG;
    private Dialog_Fragment_Progress s_Gea_Vendedor_SupervisorPG;
    private Dialog_Fragment_Progress s_Vem_CorrelativoPG;
    private Dialog_Fragment_Progress vem_Cobrador_ZonaBLPG;
    private Dialog_Fragment_Progress S_gem_TipoCambioPG;
    private Dialog_Fragment_Progress dpm_Personal_TransportePG;

    private CabfcobBL cabfcobBL = new CabfcobBL();
    private ClientesBL clientesBL = new ClientesBL();
    private Cliente_VendedorBL cliente_vendedorBL = new Cliente_VendedorBL();
    private VisitaCabBL visitaCabBL = new VisitaCabBL();
    private VisitaDetBL visitaDetBL = new VisitaDetBL();
    private VisitaMovCambioBL visitaMovCambioBL = new VisitaMovCambioBL();

    private UbigeoBL ubigeoBL = new UbigeoBL();

    private CtaBncoBL ctaBncoBL = new CtaBncoBL();
    private Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
    private Documentos_Cobra_DetBL documentos_cobra_detBL = new Documentos_Cobra_DetBL();
    private Documentos_Cobra_MovBL documentos_cobra_movBL = new Documentos_Cobra_MovBL();
    private FactCobBL factCobBL = new FactCobBL();
    private Gem_BancoBL gem_bancoBL = new Gem_BancoBL();
    private MvendedorBL mvendedorBL = new MvendedorBL();
    private ParTablaBL parTablaBL = new ParTablaBL();
    private Recibos_CcobranzaBL recibos_ccobranzaBL = new Recibos_CcobranzaBL();
    private S_Gem_ClienteBL s_gem_clienteBL = new S_Gem_ClienteBL();
    private S_Gem_Persona_DireccionBL s_gem_persona_direccionBL = new S_Gem_Persona_DireccionBL();
    private S_Gem_PersonaBL s_gem_personaBL = new S_Gem_PersonaBL();
    private S_Gem_VendedorBL s_gem_vendedorBL = new S_Gem_VendedorBL();
    private S_Sea_AccesosBL s_sea_accesosBL = new S_Sea_AccesosBL();
    private S_Sea_Usuario_AccionBL s_sea_usuario_accionBL = new S_Sea_Usuario_AccionBL();
    private S_Sea_Usuario_LocalBL s_sea_usuario_localBL = new S_Sea_Usuario_LocalBL();
    private S_Sem_EmpresaBL s_sem_empresaBL = new S_Sem_EmpresaBL();
    private S_Sem_LocalBL s_sem_localBL = new S_Sem_LocalBL();
    private S_Sem_PerfilBL s_sem_perfilBL = new S_Sem_PerfilBL();
    private S_Sem_UsuarioBL s_sem_usuarioBL = new S_Sem_UsuarioBL();
    private SucursalesBL sucursalesBL = new SucursalesBL();
    private Tablas_AuxiliaresBL tablas_auxiliaresBL = new Tablas_AuxiliaresBL();
    private S_Gem_Cliente_Codigo_AntBL s_gem_cliente_codigo_antBL = new S_Gem_Cliente_Codigo_AntBL();
    private S_Gea_Vendedor_ClienteBL s_gea_vendedor_clienteBL = new S_Gea_Vendedor_ClienteBL();
    private S_Gem_Vendedor_Codigo_AntBL s_gem_vendedor_codigo_antBL = new S_Gem_Vendedor_Codigo_AntBL();

    private DocuventBL docuventBL = new DocuventBL();
    private Dpm_Packing_CabBL dpm_packing_cabBL = new Dpm_Packing_CabBL();
    private Dpm_Personal_TransporteBL dpm_personal_transporteBL = new Dpm_Personal_TransporteBL();

    private Dpm_Packing_DetBL dpm_packing_detBL = new Dpm_Packing_DetBL();
    private S_Gea_Vendedor_SupervisorBL s_gea_vendedor_supervisorBL = new S_Gea_Vendedor_SupervisorBL();
    private S_Vem_CorrelativoBL s_vem_correlativoBL = new S_Vem_CorrelativoBL();
    private Vem_Cobrador_ZonaBL vem_cobrador_zonaBL = new Vem_Cobrador_ZonaBL();
    private S_gem_TipoCambioBL s_gem_tipoCambioBL = new S_gem_TipoCambioBL();

     private String sOPCION_SINCRONIZADA="";
    private Integer indexPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizacin_list);
        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.UNIBELL_PREF);
            getSupportActionBar().setSubtitle("SINCRONIZAR");

            DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_Sincronizar.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();

            sharedSettings = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared = getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();
            sin_lvprocesos = (GridView) findViewById(R.id.sin_lvprocesos);

            new LoadProcesosSQLite_AsyncTask().execute();
        } catch (Exception ex) {
        }
    }


    public void Sincronizar(String desc,Integer iPosition){
        indexPosition=iPosition;
        sOPCION_SINCRONIZADA=desc.toString().trim();
        switch (desc.trim()) {
            case "EMPRESA":
                try {
                new Gem_BancoBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blgem_banco + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar bancos.").Show();
                }


                try{
                new CtaBncoBL_Sincronizar_AsyncTask().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blctabnco + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                +  "XXX/XXX");

                 } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Cuentas Bancarias.").Show();
                 }


                try{
                new SucursalesBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blsucursales + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar sucursales.").Show();
                }


            break;




            case "TABLADETABLAS":
                try{
                new ParTablaBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blpartabla);

                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar tabla de tablas.").Show();
                }

                try{
                new Tablas_AuxiliaresBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bltablas_auxiliares + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0"));

                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar tablas auxiliares.").Show();
                }

                try{
                new s_vem_correlativoBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_vem_correlativo + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                        new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar correlativos.").Show();
                   }

                try{
                    new Ubigeo_Sincronizar_AsyncTask().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blubigeo);
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar ubigeo.").Show();
                }

                break;

            case "CLIENTES":
                try{
                new S_Gem_Cliente_CodigoBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_cliente_codigo_ant + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Clientes anteriores.").Show();
                }

                try{
                new S_Gem_ClienteBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_cliente + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                    } catch (Exception ex) {
                        new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Gem Clientes.").Show();
                    }

                    try{
                 new Cliente_Sincronizar_AsyncTask().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blcliente + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                        } catch (Exception ex) {
                            new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar clientes.").Show();
                        }


                break;

            case "VENDEDORES":
                try{
                new MvendedorBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blmvendedor + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar mevendedores.").Show();
                }

                try{
                new S_Gem_PersonaBLSincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_persona + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar personas.").Show();
                }

                try{
                new S_Gem_Vendedor_Codigo_AntBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_vendedor_codigo_ant + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar vendedores código antiguo.").Show();
                }

                try{
                new S_Gea_Vendedor_ClienteBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gea_vendedor_cliente + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar vendedores cliente.").Show();
                }

                try{
                new S_Gem_VendedorBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_vendedor + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
            } catch (Exception ex) {
                new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar vendedores.").Show();
            }

                try{
                new s_gea_vendedor_supervisorBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gea_vendedor_supervisor + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar(Vendedore Supervisor).").Show();
                }

                try{
                new vem_cobrador_zonaBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blvem_cobrador_zona + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar zonas de cobrador.").Show();
                }
                break;

            case "DIRECCIONES":
                try{
                new S_Gem_Persona_DireccionBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bls_gem_persona_direccion + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar direcciones.").Show();
                }

                break;
            case "DOCUMENTOS":
                try{
                new Documentos_Cobra_CabBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0")+ "/0/0","0");

                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Documentos Cab.").Show();
                }

               try{
                new Documentos_Cobra_DetBL_Sincronizar().execute(
                    ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_det + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0")+ "/0/0","0");
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Documentos Det.").Show();
                }

                try{
                    new Recibos_CcobranzaBL_Sincronizar().execute(ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blrecibos_ccobranza + '/'
                            + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                            + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                            + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar sucursales.").Show();
                }

            break;
            case "PACKING":
                try{
                new dpm_packing_cabBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldpm_packing_cab + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Packing Cab.").Show();
                }

                try{
                new dpm_packing_detBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldpm_packing_det + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar packing Det.").Show();
                }

                try{
                    new dpm_personal_transporteBL_Sincronizar().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldpm_personal_transporte + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al sincronizar personal transporte.").Show();
                }

                break;
            case "PROCESOS":
                try{

                new CabfcobBL_Sincronizar_AsyncTask().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blcabfcob + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Cabfcob.").Show();
                }

                try{
                new FactCobBL_Sincronizar().execute(
                        ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blfactcob + '/'
                                + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Facturas por cobrar.").Show();
                }


                try{
                    new DocuventBL_Sincronizar().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocuvent + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Docuvent.").Show();
                }

                try{
                    new Documentos_Cobra_MovBL_Sincronizar().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_mov + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar Movimientos.").Show();
                }
                break;

            case "RECORRIDO":
                try{
                    new VisitaCab_Sincronizar_AsyncTask().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blvem_visita_cab + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar visita cab.").Show();
                }

                try{
                    new VisitaDet_Sincronizar_AsyncTask().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blvem_visita_det + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar det.").Show();
                }

                try{
                    new VisitaMovCambio_Sincronizar_AsyncTask().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blvem_visita_movimiento_cambio + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar visita mov cambio.").Show();
                }


                try{
                    new ClienteVendedor_Sincronizar_AsyncTask().execute(
                            ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blvem_cliente_vendedor + '/'
                                    + sharedSettings.getString("iID_EMPRESA", "0")+ '/'
                                    + sharedSettings.getString("iID_LOCAL", "0")+ '/'
                                    + sharedSettings.getString("iID_VENDEDOR", "0"));
                } catch (Exception ex) {
                    new ToastLibrary(Activity_Sincronizar.this,"Error al Sincronizar cliente vendedor.").Show();
                }
                break;


        }
    }



    private void Actualizar(String sOPCION_SINCRONIZADA){
        sincronizarBE=new SincronizarBE();
        sincronizarBE.setFECHA(funciones.FechaActual() + " "+ funciones.HoraActual24());
        sincronizarBE.setESTADO("OK");
        sincronizarBE.setPROCESO(sOPCION_SINCRONIZADA);
        sincronizaDAO.update(sincronizarBE);
        new LoadProcesosSQLite_AsyncTask().execute();
    }

    public class LoadProcesosSQLite_AsyncTask extends AsyncTask<String, String, RestResult> {
        @Override
        protected RestResult doInBackground(String... p) {
            try {
                sincronizaDAO.getAllSQLite();
            } catch (Exception ex) {
                Toast.makeText(Activity_Sincronizar.this, "Ocurrio un error :-(", Toast.LENGTH_SHORT).show();
                Log.d(Constants.DEBUG_TAG, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(RestResult restResult) {
            super.onPostExecute(restResult);
            try {
                sincronizarAdapter = new SincronizarAdapter(Activity_Sincronizar.this, 0, sincronizaDAO.lstSincroniza);
                sincronizarAdapter.notifyDataSetChanged();
                sin_lvprocesos.setAdapter(sincronizarAdapter);
                if(sincronizaDAO.lstSincroniza.size()>0) {
                    sin_lvprocesos.smoothScrollToPosition(indexPosition + 1);
                }

            } catch (Exception ex) {
                Toast.makeText(Activity_Sincronizar.this, ex.getMessage().toString() +  " No hay Registros :-(", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class CabfcobBL_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            cabfcobPG = new Dialog_Fragment_Progress();
            cabfcobPG.setMensaje("Sincronizando");
            cabfcobPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return cabfcobBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (cabfcobPG != null && cabfcobPG.isVisible()) {
                cabfcobPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":CabfCob").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.cabfcobBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Cliente_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            clientesPG = new Dialog_Fragment_Progress();
            clientesPG.setMensaje("Sincronizando");
            clientesPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return clientesBL.getSincroniza(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (clientesPG != null && clientesPG.isVisible()) {
                clientesPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Clientes").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.clientesBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class CtaBncoBL_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            ctaBncoPG = new Dialog_Fragment_Progress();
            ctaBncoPG.setMensaje("Sincronizando");
            ctaBncoPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return ctaBncoBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (ctaBncoPG != null && ctaBncoPG.isVisible()) {
                ctaBncoPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Cta Bancos").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.ctaBncoBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Documentos_Cobra_CabBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            documentos_cobra_cabPG = new Dialog_Fragment_Progress();
            documentos_cobra_cabPG.setMensaje("Sincronizando");
            documentos_cobra_cabPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_cabBL.getSincronizar(p[0],p[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (documentos_cobra_cabPG != null && documentos_cobra_cabPG.isVisible()) {
                documentos_cobra_cabPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Documentos de cobranza Cab").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.documentos_Cobra_CabBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Documentos_Cobra_DetBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            documentos_cobra_detPG = new Dialog_Fragment_Progress();
            documentos_cobra_detPG.setMensaje("Sincronizando");
            documentos_cobra_detPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_detBL.getSincronizar(p[0],p[1]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (documentos_cobra_detPG != null && documentos_cobra_detPG.isVisible()) {
                documentos_cobra_detPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message") + ":Detalle Cobranzas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.documentos_Cobra_DetBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Documentos_Cobra_MovBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            documentos_cobra_movPG = new Dialog_Fragment_Progress();
            documentos_cobra_movPG.setMensaje("Sincronizando");
            documentos_cobra_movPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return documentos_cobra_movBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (documentos_cobra_movPG != null && documentos_cobra_movPG.isVisible()) {
                documentos_cobra_movPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Documentos de cobranza Mov").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.documentos_Cobra_MovBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class FactCobBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            factCobPG = new Dialog_Fragment_Progress();
            factCobPG.setMensaje("Sincronizando");
            factCobPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return factCobBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (factCobPG != null && factCobPG.isVisible()) {
                factCobPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":FactCob").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.factCobBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class Gem_BancoBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            gem_bancoPG = new Dialog_Fragment_Progress();
            gem_bancoPG.setMensaje("Sincronizando");
            gem_bancoPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return gem_bancoBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (gem_bancoPG != null && gem_bancoPG.isVisible()) {
                gem_bancoPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Bancos").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.gem_bancoBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class MvendedorBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            mvendedorPG = new Dialog_Fragment_Progress();
            mvendedorPG.setMensaje("Sincronizando");
            mvendedorPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return mvendedorBL .getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (mvendedorPG != null && mvendedorPG.isVisible()) {
                mvendedorPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Vendedor").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.mvendedorBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class ParTablaBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            parTablaPG = new Dialog_Fragment_Progress();
            parTablaPG.setMensaje("Sincronizando");
            parTablaPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return parTablaBL .getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (parTablaPG != null && parTablaPG.isVisible()) {
                parTablaPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Tablas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.parTablaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Recibos_CcobranzaBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            recibos_ccobranzaPG = new Dialog_Fragment_Progress();
            recibos_ccobranzaPG.setMensaje("Sincronizando");
            recibos_ccobranzaPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return recibos_ccobranzaBL .getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (recibos_ccobranzaPG != null && recibos_ccobranzaPG.isVisible()) {
                recibos_ccobranzaPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Recibos Cobranzas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.recibos_ccobranzaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Gem_ClienteBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_clientePG = new Dialog_Fragment_Progress();
            s_gem_clientePG.setMensaje("Sincronizando");
            s_gem_clientePG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_clienteBL .getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_clientePG != null && s_gem_clientePG.isVisible()) {
                s_gem_clientePG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message") + ":Clientes").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_clienteBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class S_Gem_Persona_DireccionBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_persona_direccionPG = new Dialog_Fragment_Progress();
            s_gem_persona_direccionPG.setMensaje("Sincronizando");
            s_gem_persona_direccionPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_persona_direccionBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_persona_direccionPG != null && s_gem_persona_direccionPG.isVisible()) {
                s_gem_persona_direccionPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Dirección de Personas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_persona_direccionBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Gem_PersonaBLSincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_personaPG = new Dialog_Fragment_Progress();
            s_gem_personaPG.setMensaje("Sincronizando");
            s_gem_personaPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_personaBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_personaPG != null && s_gem_personaPG.isVisible()) {
                s_gem_personaPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Personas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_personaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Vededor").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_vendedorBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Accesos").Show();
                } else {
                    Actualizar(sOPCION_SINCRONIZADA);
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_accesosBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();

                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Usuario Acción").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_usuario_accionBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Usuarios locales").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sea_usuario_localBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message") + ":Empresas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_empresaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ": Locales").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_localBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ": Perfiles").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_perfilBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
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
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Sem Usuarios").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_sem_usuarioBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class SucursalesBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            sucursalesPG = new Dialog_Fragment_Progress();
            sucursalesPG.setMensaje("Sincronizando");
            sucursalesPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return sucursalesBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (sucursalesPG != null && sucursalesPG.isVisible()) {
                sucursalesPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Sucursales").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.sucursalesBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class Tablas_AuxiliaresBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            tablas_auxiliaresPG = new Dialog_Fragment_Progress();
            tablas_auxiliaresPG.setMensaje("Sincronizando");
            tablas_auxiliaresPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return tablas_auxiliaresBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (tablas_auxiliaresPG != null && tablas_auxiliaresPG.isVisible()) {
                tablas_auxiliaresPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Tabla Auxiliar").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.tablas_auxiliaresBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Gem_Cliente_CodigoBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_cliente_codigo_antPG = new Dialog_Fragment_Progress();
            s_gem_cliente_codigo_antPG.setMensaje("Sincronizando");
            s_gem_cliente_codigo_antPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_cliente_codigo_antBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_cliente_codigo_antPG != null && s_gem_cliente_codigo_antPG.isVisible()) {
                s_gem_cliente_codigo_antPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Cliente Código antiguo").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_clientes_codigo_antBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class S_Gea_Vendedor_ClienteBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gea_vendedor_clientePG = new Dialog_Fragment_Progress();
            s_gea_vendedor_clientePG.setMensaje("Sincronizando");
            s_gea_vendedor_clientePG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gea_vendedor_clienteBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gea_vendedor_clientePG != null && s_gea_vendedor_clientePG.isVisible()) {
                s_gea_vendedor_clientePG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Vendedor Cliente").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gea_vendedor_clienteBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class S_Gem_Vendedor_Codigo_AntBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_gem_vendedor_codigo_antPG = new Dialog_Fragment_Progress();
            s_gem_vendedor_codigo_antPG.setMensaje("Sincronizando");
            s_gem_vendedor_codigo_antPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gem_vendedor_codigo_antBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_gem_vendedor_codigo_antPG != null && s_gem_vendedor_codigo_antPG.isVisible()) {
                s_gem_vendedor_codigo_antPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Vendedor Codigo Antiguo").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gem_vendedor_codigo_antBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class DocuventBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            docuventPG = new Dialog_Fragment_Progress();
            docuventPG.setMensaje("Sincronizando");
            docuventPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return docuventBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (docuventPG != null && docuventPG.isVisible()) {
                docuventPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Docuvent").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.docuventBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class dpm_packing_cabBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            dpm_Packing_CabPG = new Dialog_Fragment_Progress();
            dpm_Packing_CabPG.setMensaje("Sincronizando");
            dpm_Packing_CabPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return dpm_packing_cabBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (dpm_Packing_CabPG != null && dpm_Packing_CabPG.isVisible()) {
                dpm_Packing_CabPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Packing Cabecera").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.dpm_packing_cabBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class dpm_personal_transporteBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            dpm_Personal_TransportePG = new Dialog_Fragment_Progress();
            dpm_Personal_TransportePG.setMensaje("Sincronizando");
            dpm_Personal_TransportePG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return dpm_personal_transporteBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (dpm_Personal_TransportePG != null && dpm_Personal_TransportePG.isVisible()) {
                dpm_Personal_TransportePG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Personal Transporte").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.dpm_personal_transporteBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }




    public class dpm_packing_detBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            dpm_Packing_DetBLPG = new Dialog_Fragment_Progress();
            dpm_Packing_DetBLPG.setMensaje("Sincronizando");
            dpm_Packing_DetBLPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return dpm_packing_detBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (dpm_Packing_DetBLPG != null && dpm_Packing_DetBLPG.isVisible()) {
                dpm_Packing_DetBLPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Packing Detalle").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.dpm_packing_detBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
    public class s_gea_vendedor_supervisorBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_Gea_Vendedor_SupervisorPG = new Dialog_Fragment_Progress();
            s_Gea_Vendedor_SupervisorPG.setMensaje("Sincronizando");
            s_Gea_Vendedor_SupervisorPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_gea_vendedor_supervisorBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_Gea_Vendedor_SupervisorPG != null && s_Gea_Vendedor_SupervisorPG.isVisible()) {
                s_Gea_Vendedor_SupervisorPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Vendedor Supervisor").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_gea_vendedor_supervisorBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class s_vem_correlativoBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            s_Vem_CorrelativoPG = new Dialog_Fragment_Progress();
            s_Vem_CorrelativoPG.setMensaje("Sincronizando");
            s_Vem_CorrelativoPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return s_vem_correlativoBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (s_Vem_CorrelativoPG != null && s_Vem_CorrelativoPG.isVisible()) {
                s_Vem_CorrelativoPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message") + ":Ven Correlativo").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.s_vem_correlativoBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class vem_cobrador_zonaBL_Sincronizar extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            vem_Cobrador_ZonaBLPG = new Dialog_Fragment_Progress();
            vem_Cobrador_ZonaBLPG.setMensaje("Sincronizando");
            vem_Cobrador_ZonaBLPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return vem_cobrador_zonaBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (vem_Cobrador_ZonaBLPG != null && vem_Cobrador_ZonaBLPG.isVisible()) {
                vem_Cobrador_ZonaBLPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message") + ":Cobrador Zona").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.vem_cobrador_zonaBL) + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class ClienteVendedor_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            vem_cliente_vendedorPG = new Dialog_Fragment_Progress();
            vem_cliente_vendedorPG.setMensaje("Sincronizando");
            vem_cliente_vendedorPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return cliente_vendedorBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (vem_cliente_vendedorPG != null && vem_cliente_vendedorPG.isVisible()) {
                vem_cliente_vendedorPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Cliente Vendedor").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.cliente_vendedorBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class Ubigeo_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            ubigeoPG = new Dialog_Fragment_Progress();
            ubigeoPG.setMensaje("Sincronizando");
            ubigeoPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return ubigeoBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (ubigeoPG != null && ubigeoPG.isVisible()) {
                ubigeoPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Ubigeos").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.ubigeoBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    public class VisitaCab_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            vem_visitaCabPG = new Dialog_Fragment_Progress();
            vem_visitaCabPG.setMensaje("Sincronizando");
            vem_visitaCabPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return visitaCabBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (vem_visitaCabPG != null && vem_visitaCabPG.isVisible()) {
                vem_visitaCabPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Visitas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.visitaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class VisitaDet_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            vem_visitaDetPG = new Dialog_Fragment_Progress();
            vem_visitaDetPG.setMensaje("Sincronizando");
            vem_visitaDetPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return visitaDetBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (vem_visitaDetPG != null && vem_visitaDetPG.isVisible()) {
                vem_visitaDetPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Visitas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.visitaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class VisitaMovCambio_Sincronizar_AsyncTask extends AsyncTask<String, String, JSONObject> {
        /*ASYNCTASK<Parametros, Progreso, Resultado>
        DECLARACION DE VARIABLES PRIVADAS EN LA CLASE ASYNTASK*/
        private volatile boolean running = true;

        @Override
        protected void onPreExecute() {
            vem_visitaMovCambioPG = new Dialog_Fragment_Progress();
            vem_visitaMovCambioPG.setMensaje("Sincronizando");
            vem_visitaMovCambioPG.show(getFragmentManager(), Dialog_Fragment_Progress.TAG);
        }

        @Override
        protected JSONObject doInBackground(String... p) {
            return visitaMovCambioBL.getSincronizar(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //SI PROGRESSDIALOG ES VISIBLE LO CERRAMOS
            if (vem_visitaMovCambioPG != null && vem_visitaMovCambioPG.isVisible()) {
                vem_visitaMovCambioPG.dismiss();
            }
            try {
                if (result.getInt("status")!=1) {
                    //MOSTRAMOS MESSAGE
                    new ToastLibrary(Activity_Sincronizar.this, result.getString("message")+ ":Visitas").Show();
                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.visitaBL)  + result.getString("message") , Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    Actualizar(sOPCION_SINCRONIZADA);
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

}

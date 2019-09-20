package pe.com.app.unibell.appunibell.Util;
/**
 * Created by rgalvez on 15/11/2016.
 */
public interface ConstantsLibrary {
        public static final String DEBUG_TAG = "DRES_DEBUG";
        //LOCAL
        public static final String RESTFUL_URL = "http://172.16.1.78/ServiceUnibell";
        //REMOTO
       // public static final String RESTFUL_URL ="http://181.65.215.174/ServiceUnibell";
        public static final String blcabfcob = "/blcabfcob";
        public static final String blcliente = "/blclientes";
        public static final String blctabnco = "/blctabnco";

        //MOVMIENTOS
        public static final String bldocumentos_cobra_mov = "/bldocumentos_cobra_mov";
        public static final String bldocumentos_cobra_mov_Insert = "/documentos_insert";

        //CABECERA DE COBRANZA
        public static final String bldocumentos_cobra_cab = "/bldocumentos_cobra_cab";
        public static final String bldocumentos_cobra_cab_Insert = "/cobranza_insert";
        public static final String bldocumentos_cobra_cab_Insert_Update = "/cobranza_insert_update";
        public static final String bldocumentos_cobra_cab_Update = "/cobranza_update";
        public static final String bldocumentos_cobra_cab_Delete = "/cobranza_delete";
        public static final String bldocumentos_cobra_cab_ConciliarDepositos = "/ConciliarDepositos";

        //DETALLE DE COBRANZA
        public static final String bldocumentos_cobra_det = "/bldocumentos_cobra_det";

        public static final String bldocumentos_cobra_cab_flujo1 = "/bldocumentos_cobra_cab_Flujo1";
        public static final String bldocumentos_cobra_mov_flujoresumen = "/bldocumentos_cobra_mov_flujoresumen";
        public static final String bldocumentos_cobra_mov_flujo2 = "/bldocumentos_cobra_mov_flujo2";
        public static final String bldocumentos_cobra_mov_flujo3 = "/bldocumentos_cobra_mov_flujo3";
        public static final String bldocumentos_cobra_cab_liquidacion_cobranza = "/bldocumentos_cobra_cab_LiqCobranza";
        public static final String bldocumentos_cobra_cab_LiqCobranza_Grupo = "/bldocumentos_cobra_cab_LiqCobranza_Grupo";
        public static final String bldocumentos_cobra_cab_RptCobranza = "/bldocumentos_cobra_cab_RptCobranza";

        public static final String blfactcob = "/blfactcob";
        public static final String blgem_banco = "/blgem_banco";
        public static final String blmvendedor = "/blmvendedor";
        public static final String blpartabla = "/blpartabla";
        public static final String blrecibos_ccobranza = "/blrecibos_ccobranza";
        public static final String bls_gem_cliente = "/bls_gem_cliente";
        public static final String bls_gem_persona_direccion = "/bls_gem_persona_direccion";
        public static final String bls_gem_persona = "/bls_gem_persona";
        public static final String bls_gem_vendedor = "/bls_gem_vendedor";
        public static final String bls_sea_accesos = "/bls_sea_accesos";
        public static final String bls_sea_usuario_accion = "/bls_sea_usuario_accion";
        public static final String bls_sea_usuario_local = "/bls_sea_usuario_local";
        public static final String bls_sem_empresa = "/bls_sem_empresa";
        public static final String bls_sem_local = "/bls_sem_local";
        public static final String bls_sem_perfil = "/bls_sem_perfil";
        public static final String bls_sem_usuario = "/bls_sem_usuario";
        public static final String blsucursales = "/blsucursales";
        public static final String bltablas_auxiliares = "/bltablas_auxiliares";
        public static final String bls_sem_menu = "/bls_sem_menu";
        public static final String bls_rec_usuario = "/bls_rec_usuario";


        public static final String bls_gea_vendedor_cliente = "/bls_gea_vendedor_cliente";
        public static final String bls_gem_cliente_codigo_ant = "/bls_gem_cliente_codigo_ant";
        public static final String bls_gem_vendedor_codigo_ant = "/bls_gem_vendedor_codigo_ant";


        public static final String blvem_cobrador_zona = "/blvem_cobrador_zona";
        public static final String bls_vem_correlativo = "/bls_vem_correlativo";
        public static final String bls_gea_vendedor_supervisor = "/bls_gea_vendedor_supervisor";
        public static final String bldocuvent = "/bldocuvent";
        public static final String bldpm_packing_cab = "/bldpm_packing_cab";
        public static final String bldpm_packing_det = "/bldpm_packing_det";

        public static final String bldocumentos_cobra_mov_APlanilla = "/planilla_doccobramov";
        public static final String bldocumentos_cobra_cab_GeneraPlanilla = "/GenerarPlanillaCobranza";
        public static final String bldocumentos_cobra_cab_InsertarPlanilla = "/InsertarPlanilla";
        public static final String bldocumentos_cobra_cab_RetornarPlanilla = "/RetornarPlanilla";

        public static final String bls_gem_tipocambio = "/bls_gem_tipocambio";

        public static final String bldpm_personal_transporte = "/bldpm_personal_transporte";





}

package pe.com.app.unibell.appunibell.BL;


import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class ClientesBL {
    public ArrayList<ClientesBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        ClientesBE clientesBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<ClientesBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    clientesBE = new ClientesBE();
                    clientesBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    clientesBE.setNOMBRE(jsonObjectItem.getString("NOMBRE"));
                    clientesBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                    clientesBE.setCIUDAD(jsonObjectItem.getString("CIUDAD"));
                    clientesBE.setDISTRITO(jsonObjectItem.getString("DISTRITO"));
                    clientesBE.setPAIS(jsonObjectItem.getString("PAIS"));
                    clientesBE.setZONA(jsonObjectItem.getString("ZONA"));
                    clientesBE.setVENDEDOR(jsonObjectItem.getString("VENDEDOR"));
                    clientesBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    clientesBE.setGIRO(jsonObjectItem.getString("GIRO"));
                    clientesBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    clientesBE.setFAX(jsonObjectItem.getString("FAX"));
                    clientesBE.setFECHA_ING(jsonObjectItem.getString("FECHA_ING"));
                    clientesBE.setCOND_PAG(jsonObjectItem.getString("COND_PAG"));
                    clientesBE.setCALIFICACION(jsonObjectItem.getString("CALIFICACION"));
                    clientesBE.setLIMITE_CRED(jsonObjectItem.getString("LIMITE_CRED"));
                    clientesBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    clientesBE.setRUC(jsonObjectItem.getString("RUC"));
                    clientesBE.setDSCTO_1(jsonObjectItem.getInt("DSCTO_1"));
                    clientesBE.setDSCTO_2(jsonObjectItem.getInt("DSCTO_2"));
                    clientesBE.setCTACONTS(jsonObjectItem.getString("CTACONTS"));
                    clientesBE.setCTACONTD(jsonObjectItem.getString("CTACONTD"));
                    clientesBE.setCLIENTE_AFECTO(jsonObjectItem.getString("CLIENTE_AFECTO"));
                    clientesBE.setDSCTO_MAX(jsonObjectItem.getInt("DSCTO_MAX"));
                    clientesBE.setLISTA_PRECIO(jsonObjectItem.getString("LISTA_PRECIO"));
                    clientesBE.setNIVEL_CP(jsonObjectItem.getInt("NIVEL_CP"));
                    clientesBE.setCRED_USADO(jsonObjectItem.getInt("CRED_USADO"));
                    clientesBE.setLETRA_PRT(jsonObjectItem.getString("LETRA_PRT"));
                    clientesBE.setCHEQ_DEV(jsonObjectItem.getString("CHEQ_DEV"));
                    clientesBE.setGRUPO(jsonObjectItem.getString("GRUPO"));
                    clientesBE.setDIREC_FACT(jsonObjectItem.getString("DIREC_FACT"));
                    clientesBE.setINTERES(jsonObjectItem.getString("INTERES"));
                    clientesBE.setCOMI_PREDET(jsonObjectItem.getString("COMI_PREDET"));
                    clientesBE.setALM_CONS(jsonObjectItem.getString("ALM_CONS"));
                    clientesBE.setCOD_UBC(jsonObjectItem.getString("COD_UBC"));
                    clientesBE.setDOCIDEN(jsonObjectItem.getString("DOCIDEN"));
                    clientesBE.setRUC_NEW(jsonObjectItem.getString("RUC_NEW"));
                    clientesBE.setUBIGEO(jsonObjectItem.getString("UBIGEO"));
                    clientesBE.setDIA_VISITA(jsonObjectItem.getInt("DIA_VISITA"));
                    clientesBE.setORD_VISITA(jsonObjectItem.getInt("ORD_VISITA"));
                    clientesBE.setFRC_VISITA(jsonObjectItem.getString("FRC_VISITA"));
                    clientesBE.setANIVERSARIO(jsonObjectItem.getString("ANIVERSARIO"));
                    clientesBE.setR_CUMPLEANOS(jsonObjectItem.getString("R_CUMPLEANOS"));
                    clientesBE.setL_TAMANO(jsonObjectItem.getString("L_TAMANO"));
                    clientesBE.setL_DECORACION(jsonObjectItem.getString("L_DECORACION"));
                    clientesBE.setL_TECNOLOGIA(jsonObjectItem.getString("L_TECNOLOGIA"));
                    clientesBE.setNRO_EMPLEADOS(jsonObjectItem.getString("NRO_EMPLEADOS"));
                    clientesBE.setNRO_EXIBIDOR(jsonObjectItem.getString("NRO_EXIBIDOR"));
                    clientesBE.setNRO_VISITAS(jsonObjectItem.getString("NRO_VISITAS"));
                    clientesBE.setCANEMP(jsonObjectItem.getInt("CANEMP"));
                    clientesBE.setCEOCLIE(jsonObjectItem.getString("CEOCLIE"));
                    clientesBE.setPROFESO(jsonObjectItem.getString("PROFESO"));
                    clientesBE.setCARNET(jsonObjectItem.getString("CARNET"));
                    clientesBE.setREFERENCIA(jsonObjectItem.getString("REFERENCIA"));
                    clientesBE.setCOD_STROE(jsonObjectItem.getString("COD_STROE"));
                    clientesBE.setGIRO_CLI(jsonObjectItem.getString("GIRO_CLI"));
                    clientesBE.setR_SEXO(jsonObjectItem.getString("R_SEXO"));
                    clientesBE.setR_LUGNAC(jsonObjectItem.getString("R_LUGNAC"));
                    clientesBE.setR_ESTCIV(jsonObjectItem.getString("R_ESTCIV"));
                    clientesBE.setR_HOBBIE(jsonObjectItem.getString("R_HOBBIE"));
                    clientesBE.setR_GENERO(jsonObjectItem.getString("R_GENERO"));
                    clientesBE.setR_REVIST(jsonObjectItem.getString("R_REVIST"));
                    clientesBE.setR_PERIOD(jsonObjectItem.getString("R_PERIOD"));
                    clientesBE.setRETENCION(jsonObjectItem.getString("RETENCION"));
                    clientesBE.setCALIF_VTA(jsonObjectItem.getString("CALIF_VTA"));
                    clientesBE.setCALIF_CRED(jsonObjectItem.getString("CALIF_CRED"));
                    clientesBE.setPLAZO_PAGO(jsonObjectItem.getString("PLAZO_PAGO"));
                    clientesBE.setI_SITUACION(jsonObjectItem.getString("I_SITUACION"));
                    clientesBE.setC_CANAL(jsonObjectItem.getString("C_CANAL"));
                    clientesBE.setC_CLIENTE(jsonObjectItem.getString("C_CLIENTE"));
                    clientesBE.setC_ETAPA(jsonObjectItem.getString("C_ETAPA"));
                    clientesBE.setFLG_CONSIGNA_VTA(jsonObjectItem.getString("FLG_CONSIGNA_VTA"));
                    clientesBE.setFLG_CONSIGNA_PROM(jsonObjectItem.getString("FLG_CONSIGNA_PROM"));
                    clientesBE.setC_COBRADOR_EXT(jsonObjectItem.getString("C_COBRADOR_EXT"));
                    clientesBE.setC_DIA_VISITA_EXT(jsonObjectItem.getString("C_DIA_VISITA_EXT"));
                    clientesBE.setN_ORD_VISITA_EXT(jsonObjectItem.getString("N_ORD_VISITA_EXT"));
                    clientesBE.setC_FRC_VISITA_EXT(jsonObjectItem.getString("C_FRC_VISITA_EXT"));
                    clientesBE.setC_RUTA_DISTRIBUCION(jsonObjectItem.getString("C_RUTA_DISTRIBUCION"));
                    clientesBE.setE_MAIL(jsonObjectItem.getString("E_MAIL"));
                    clientesBE.setN_ARCHIVO(jsonObjectItem.getInt("N_ARCHIVO"));
                    clientesBE.setC_CLIENTE_PAGO(jsonObjectItem.getString("C_CLIENTE_PAGO"));
                    clientesBE.setC_NIVEL(jsonObjectItem.getString("C_NIVEL"));
                    clientesBE.setI_CLIENTE_INT_EXT(jsonObjectItem.getString("I_CLIENTE_INT_EXT"));
                    clientesBE.setM_CUOTA_SEMANAL(jsonObjectItem.getInt("M_CUOTA_SEMANAL"));
                    clientesBE.setC_FRC_COB(jsonObjectItem.getString("C_FRC_COB"));
                    clientesBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    clientesBE.setC_GRUPO_CANAL_VTA(jsonObjectItem.getString("C_GRUPO_CANAL_VTA"));
                    clientesBE.setC_FUERZA_VTA(jsonObjectItem.getString("C_FUERZA_VTA"));
                    clientesBE.setI_DIR_FISCAL(jsonObjectItem.getString("I_DIR_FISCAL"));
                    clientesBE.setC_REGION_VTA(jsonObjectItem.getString("C_REGION_VTA"));
                    clientesBE.setC_COBRADOR(jsonObjectItem.getString("C_COBRADOR"));
                    clientesBE.setI_ORIGEN(jsonObjectItem.getString("I_ORIGEN"));
                    clientesBE.setI_DI(jsonObjectItem.getString("I_DI"));
                    clientesBE.setI_AGENTE_RET(jsonObjectItem.getString("I_AGENTE_RET"));
                    clientesBE.setI_AGENTE_PER(jsonObjectItem.getString("I_AGENTE_PER"));
                    clientesBE.setI_PERCEPCION(jsonObjectItem.getString("I_PERCEPCION"));
                    clientesBE.setCOD_DPT(jsonObjectItem.getString("COD_DPT"));
                    clientesBE.setCOD_PVC(jsonObjectItem.getString("COD_PVC"));
                    clientesBE.setDIRECCION_VSTA(jsonObjectItem.getString("DIRECCION_VSTA"));
                    clientesBE.setID_NIVEL(jsonObjectItem.getString("ID_NIVEL"));
                    clientesBE.setI_RELACION(jsonObjectItem.getString("I_RELACION"));
                    clientesBE.setF_FACT_AUTOM(jsonObjectItem.getString("F_FACT_AUTOM"));
                    clientesBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    clientesBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    clientesBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    clientesBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    clientesBE.setI_REGISTRO_CASTIGO(jsonObjectItem.getString("I_REGISTRO_CASTIGO"));
                    clientesBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    clientesBE.setID_LOCAL(jsonObjectItem.getString("ID_LOCAL"));
                    clientesBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    lst.add(clientesBE);
                }
            }
            //CREAMOS UN JSON PARA MOSTRAR EL STATUS Y MESSAGE
            jsonObjectResult.accumulate("status", jsonObjectRest.getInt("status"));
            jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", 0);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }


    public JSONObject getSincroniza(String newURL) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<ClientesBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("CLIENTES", null, null);

                String sql = "INSERT OR REPLACE INTO CLIENTES" +
                        "(COD_CLIENTE,NOMBRE,DIRECCION,CIUDAD,DISTRITO,PAIS," +
                        "ZONA,VENDEDOR,COBRADOR,GIRO,TELEFONO,FAX," +
                        "FECHA_ING,COND_PAG,CALIFICACION,LIMITE_CRED,ESTADO,RUC," +
                        "DSCTO_1,DSCTO_2,CTACONTS,CTACONTD,CLIENTE_AFECTO,DSCTO_MAX," +
                        "LISTA_PRECIO,NIVEL_CP,CRED_USADO,LETRA_PRT,CHEQ_DEV,GRUPO," +
                        "DIREC_FACT,INTERES,COMI_PREDET,ALM_CONS,COD_UBC,DOCIDEN," +
                        "RUC_NEW,UBIGEO,DIA_VISITA,ORD_VISITA,FRC_VISITA,ANIVERSARIO," +
                        "R_CUMPLEANOS,L_TAMANO,L_DECORACION,L_TECNOLOGIA,NRO_EMPLEADOS,NRO_EXIBIDOR," +
                        "NRO_VISITAS,CANEMP,CEOCLIE,PROFESO,CARNET,REFERENCIA," +
                        "COD_STROE,GIRO_CLI,R_SEXO,R_LUGNAC,R_ESTCIV,R_HOBBIE," +
                        "R_GENERO,R_REVIST,R_PERIOD,RETENCION,CALIF_VTA,CALIF_CRED," +
                        "PLAZO_PAGO,I_SITUACION,C_CANAL,C_CLIENTE,C_ETAPA,FLG_CONSIGNA_VTA," +
                        "FLG_CONSIGNA_PROM,C_COBRADOR_EXT,C_DIA_VISITA_EXT,N_ORD_VISITA_EXT,C_FRC_VISITA_EXT,C_RUTA_DISTRIBUCION," +
                        "E_MAIL,N_ARCHIVO,C_CLIENTE_PAGO,C_NIVEL,I_CLIENTE_INT_EXT,M_CUOTA_SEMANAL," +
                        "C_FRC_COB,C_SUC_EMP,C_GRUPO_CANAL_VTA,C_FUERZA_VTA,I_DIR_FISCAL,C_REGION_VTA," +
                        "C_COBRADOR,I_ORIGEN,I_DI,I_AGENTE_RET,I_AGENTE_PER,I_PERCEPCION," +
                        "COD_DPT,COD_PVC,DIRECCION_VSTA,ID_NIVEL,I_RELACION,F_FACT_AUTOM," +
                        "C_USUARIO,C_PERFIL,C_CPU,FEC_REG,I_REGISTRO_CASTIGO,OBSERVACION,ID_LOCAL,ID_EMPRESA) " +
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?)";

                    DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                    DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                    DataBaseHelper.myDataBase.setLockingEnabled(false);
                    DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                    SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(sql);
                
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE"));
                    stmt.bindString(3,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(4,jsonObjectItem.getString("CIUDAD"));
                    stmt.bindString(5,jsonObjectItem.getString("DISTRITO"));
                    stmt.bindString(6,jsonObjectItem.getString("PAIS"));
                    stmt.bindString(7,jsonObjectItem.getString("ZONA"));
                    stmt.bindString(8,jsonObjectItem.getString("VENDEDOR"));
                    stmt.bindString(9,jsonObjectItem.getString("COBRADOR"));
                    stmt.bindString(10,jsonObjectItem.getString("GIRO"));
                    stmt.bindString(11,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(12,jsonObjectItem.getString("FAX"));
                    stmt.bindString(13,jsonObjectItem.getString("FECHA_ING"));
                    stmt.bindString(14,jsonObjectItem.getString("COND_PAG"));
                    stmt.bindString(15,jsonObjectItem.getString("CALIFICACION"));
                    stmt.bindString(16,jsonObjectItem.getString("LIMITE_CRED"));
                    stmt.bindString(17,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(18,jsonObjectItem.getString("RUC"));
                    stmt.bindString(19,jsonObjectItem.getString("DSCTO_1"));
                    stmt.bindString(20,jsonObjectItem.getString("DSCTO_2"));
                    stmt.bindString(21,jsonObjectItem.getString("CTACONTS"));
                    stmt.bindString(22,jsonObjectItem.getString("CTACONTD"));
                    stmt.bindString(23,jsonObjectItem.getString("CLIENTE_AFECTO"));
                    stmt.bindString(24,jsonObjectItem.getString("DSCTO_MAX"));
                    stmt.bindString(25,jsonObjectItem.getString("LISTA_PRECIO"));
                    stmt.bindString(26,jsonObjectItem.getString("NIVEL_CP"));
                    stmt.bindString(27,jsonObjectItem.getString("CRED_USADO"));
                    stmt.bindString(28,jsonObjectItem.getString("LETRA_PRT"));
                    stmt.bindString(29,jsonObjectItem.getString("CHEQ_DEV"));
                    stmt.bindString(30,jsonObjectItem.getString("GRUPO"));
                    stmt.bindString(31,jsonObjectItem.getString("DIREC_FACT"));
                    stmt.bindString(32,jsonObjectItem.getString("INTERES"));
                    stmt.bindString(33,jsonObjectItem.getString("COMI_PREDET"));
                    stmt.bindString(34,jsonObjectItem.getString("ALM_CONS"));
                    stmt.bindString(35,jsonObjectItem.getString("COD_UBC"));
                    stmt.bindString(36,jsonObjectItem.getString("DOCIDEN"));
                    stmt.bindString(37,jsonObjectItem.getString("RUC_NEW"));
                    stmt.bindString(38,jsonObjectItem.getString("UBIGEO"));
                    stmt.bindString(39,jsonObjectItem.getString("DIA_VISITA"));
                    stmt.bindString(40,jsonObjectItem.getString("ORD_VISITA"));
                    stmt.bindString(41,jsonObjectItem.getString("FRC_VISITA"));
                    stmt.bindString(42,jsonObjectItem.getString("ANIVERSARIO"));
                    stmt.bindString(43,jsonObjectItem.getString("R_CUMPLEANOS"));
                    stmt.bindString(44,jsonObjectItem.getString("L_TAMANO"));
                    stmt.bindString(45,jsonObjectItem.getString("L_DECORACION"));
                    stmt.bindString(46,jsonObjectItem.getString("L_TECNOLOGIA"));
                    stmt.bindString(47,jsonObjectItem.getString("NRO_EMPLEADOS"));
                    stmt.bindString(48,jsonObjectItem.getString("NRO_EXIBIDOR"));
                    stmt.bindString(49,jsonObjectItem.getString("NRO_VISITAS"));
                    stmt.bindString(50,jsonObjectItem.getString("CANEMP"));
                    stmt.bindString(51,jsonObjectItem.getString("CEOCLIE"));
                    stmt.bindString(52,jsonObjectItem.getString("PROFESO"));
                    stmt.bindString(53,jsonObjectItem.getString("CARNET"));
                    stmt.bindString(54,jsonObjectItem.getString("REFERENCIA"));
                    stmt.bindString(55,jsonObjectItem.getString("COD_STROE"));
                    stmt.bindString(56,jsonObjectItem.getString("GIRO_CLI"));
                    stmt.bindString(57,jsonObjectItem.getString("R_SEXO"));
                    stmt.bindString(58,jsonObjectItem.getString("R_LUGNAC"));
                    stmt.bindString(59,jsonObjectItem.getString("R_ESTCIV"));
                    stmt.bindString(60,jsonObjectItem.getString("R_HOBBIE"));
                    stmt.bindString(61,jsonObjectItem.getString("R_GENERO"));
                    stmt.bindString(62,jsonObjectItem.getString("R_REVIST"));
                    stmt.bindString(63,jsonObjectItem.getString("R_PERIOD"));
                    stmt.bindString(64,jsonObjectItem.getString("RETENCION"));
                    stmt.bindString(65,jsonObjectItem.getString("CALIF_VTA"));
                    stmt.bindString(66,jsonObjectItem.getString("CALIF_CRED"));
                    stmt.bindString(67,jsonObjectItem.getString("PLAZO_PAGO"));
                    stmt.bindString(68,jsonObjectItem.getString("I_SITUACION"));
                    stmt.bindString(69,jsonObjectItem.getString("C_CANAL"));
                    stmt.bindString(70,jsonObjectItem.getString("C_CLIENTE"));
                    stmt.bindString(71,jsonObjectItem.getString("C_ETAPA"));
                    stmt.bindString(72,jsonObjectItem.getString("FLG_CONSIGNA_VTA"));
                    stmt.bindString(73,jsonObjectItem.getString("FLG_CONSIGNA_PROM"));
                    stmt.bindString(74,jsonObjectItem.getString("C_COBRADOR_EXT"));
                    stmt.bindString(75,jsonObjectItem.getString("C_DIA_VISITA_EXT"));
                    stmt.bindString(76,jsonObjectItem.getString("N_ORD_VISITA_EXT"));
                    stmt.bindString(77,jsonObjectItem.getString("C_FRC_VISITA_EXT"));
                    stmt.bindString(78,jsonObjectItem.getString("C_RUTA_DISTRIBUCION"));
                    stmt.bindString(79,jsonObjectItem.getString("E_MAIL"));
                    stmt.bindString(80,jsonObjectItem.getString("N_ARCHIVO"));
                    stmt.bindString(81,jsonObjectItem.getString("C_CLIENTE_PAGO"));
                    stmt.bindString(82,jsonObjectItem.getString("C_NIVEL"));
                    stmt.bindString(83,jsonObjectItem.getString("I_CLIENTE_INT_EXT"));
                    stmt.bindString(84,jsonObjectItem.getString("M_CUOTA_SEMANAL"));
                    stmt.bindString(85,jsonObjectItem.getString("C_FRC_COB"));
                    stmt.bindString(86,jsonObjectItem.getString("C_SUC_EMP"));
                    stmt.bindString(87,jsonObjectItem.getString("C_GRUPO_CANAL_VTA"));
                    stmt.bindString(88,jsonObjectItem.getString("C_FUERZA_VTA"));
                    stmt.bindString(89,jsonObjectItem.getString("I_DIR_FISCAL"));
                    stmt.bindString(90,jsonObjectItem.getString("C_REGION_VTA"));
                    stmt.bindString(91,jsonObjectItem.getString("C_COBRADOR"));
                    stmt.bindString(92,jsonObjectItem.getString("I_ORIGEN"));
                    stmt.bindString(93,jsonObjectItem.getString("I_DI"));
                    stmt.bindString(94,jsonObjectItem.getString("I_AGENTE_RET"));
                    stmt.bindString(95,jsonObjectItem.getString("I_AGENTE_PER"));
                    stmt.bindString(96,jsonObjectItem.getString("I_PERCEPCION"));
                    stmt.bindString(97,jsonObjectItem.getString("COD_DPT"));
                    stmt.bindString(98,jsonObjectItem.getString("COD_PVC"));
                    stmt.bindString(99,jsonObjectItem.getString("DIRECCION_VSTA"));
                    stmt.bindString(100,jsonObjectItem.getString("ID_NIVEL"));
                    stmt.bindString(101,jsonObjectItem.getString("I_RELACION"));
                    stmt.bindString(102,jsonObjectItem.getString("F_FACT_AUTOM"));
                    stmt.bindString(103,jsonObjectItem.getString("C_USUARIO"));
                    stmt.bindString(104,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(105,jsonObjectItem.getString("C_CPU"));
                    stmt.bindString(106,jsonObjectItem.getString("FEC_REG"));
                    stmt.bindString(107,jsonObjectItem.getString("I_REGISTRO_CASTIGO"));
                    stmt.bindString(108,jsonObjectItem.getString("OBSERVACION"));
                    stmt.bindString(109,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(110,jsonObjectItem.getString("ID_EMPRESA"));

                    stmt.execute();
                    stmt.clearBindings();
                }
                DataBaseHelper.myDataBase.setTransactionSuccessful();
                DataBaseHelper.myDataBase.endTransaction();
                DataBaseHelper.myDataBase.setLockingEnabled(true);
                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=NORMAL");
            }
            //CREAMOS UN JSON PARA MOSTRAR EL STATUS Y MESSAGE
            jsonObjectResult.accumulate("status", jsonObjectRest.getInt("status"));
            jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));
        } catch (Exception e) {
            DataBaseHelper.myDataBase.endTransaction();
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", 0);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }





}

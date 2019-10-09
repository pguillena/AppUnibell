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
                        "(COD_CLIENTE,   NOMBRE,   RUC,   GRUPO,   COD_UBC,   DOCIDEN,   DIA_VISITA,   C_CANAL,  E_MAIL,   I_CANC_ANTIGUO, DIRECCION, I_SITUACION, ID_EMPRESA) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                    DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                    DataBaseHelper.myDataBase.setLockingEnabled(false);
                    DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                    SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(sql);
                
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE"));
                    stmt.bindString(3,jsonObjectItem.getString("RUC"));
                    stmt.bindString(4,jsonObjectItem.getString("GRUPO"));
                    stmt.bindString(5,jsonObjectItem.getString("COD_UBC"));
                    stmt.bindString(6,jsonObjectItem.getString("DOCIDEN"));
                    stmt.bindString(7,jsonObjectItem.getString("DIA_VISITA"));
                    stmt.bindString(8,jsonObjectItem.getString("C_CANAL"));
                    stmt.bindString(9,jsonObjectItem.getString("E_MAIL"));
                    stmt.bindString(10,jsonObjectItem.getString("I_CANC_ANTIGUO"));
                    stmt.bindString(11,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(12,jsonObjectItem.getString("I_SITUACION"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_EMPRESA"));

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

    public JSONObject getSincronizaxCodigo(String newURL) {
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
                //DataBaseHelper.myDataBase.delete("CLIENTES", null, null);

                String sql = "INSERT OR REPLACE INTO CLIENTES" +
                        "(COD_CLIENTE,   NOMBRE,   RUC,   GRUPO,   COD_UBC,   DOCIDEN,   DIA_VISITA,   C_CANAL,  E_MAIL,   I_CANC_ANTIGUO, DIRECCION,  I_SITUACION,  ID_EMPRESA) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(sql);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE"));
                    stmt.bindString(3,jsonObjectItem.getString("RUC"));
                    stmt.bindString(4,jsonObjectItem.getString("GRUPO"));
                    stmt.bindString(5,jsonObjectItem.getString("COD_UBC"));
                    stmt.bindString(6,jsonObjectItem.getString("DOCIDEN"));
                    stmt.bindString(7,jsonObjectItem.getString("DIA_VISITA"));
                    stmt.bindString(8,jsonObjectItem.getString("C_CANAL"));
                    stmt.bindString(9,jsonObjectItem.getString("E_MAIL"));
                    stmt.bindString(10,jsonObjectItem.getString("I_CANC_ANTIGUO"));
                    stmt.bindString(11,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(12,jsonObjectItem.getString("I_SITUACION"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_EMPRESA"));

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


    public JSONObject getMigrarClienteCompleto(String newURL) {
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
                //DataBaseHelper.myDataBase.delete("CLIENTES", null, null);

                String sqlClientes = "INSERT OR REPLACE INTO CLIENTES" +
                        "(COD_CLIENTE,   NOMBRE,   RUC,   GRUPO,   COD_UBC,   DOCIDEN,   DIA_VISITA,   C_CANAL,  E_MAIL,   I_CANC_ANTIGUO, DIRECCION, I_SITUACION, ID_EMPRESA) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                String sqlClienteCodigoAntiguo="INSERT OR REPLACE INTO S_GEM_CLIENTE_CODIGO_ANT(" +
                        "ID_CLIENTE,CODIGO_ANTIGUO,FLAG_VIGENCIA)"+
                        "VALUES " +
                        "(?,?,?)";

                String sqlGemCliente="INSERT OR REPLACE INTO S_GEM_CLIENTE(ID_CLIENTE, CODIGO, RAZON_SOCIAL,  RUC, CLIENTE_AFECTO, DNI, ID_CANAL, CORREO, COND_PAGO, ID_GRUPO, ID_LISTA_DESCUENTO, ID_CANAL_DETALLE, CLIENTE_ESPECIAL, COD_ALM_CONSIG)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                String sqlGemPersona="INSERT OR REPLACE INTO S_GEM_PERSONA(" +
                        "ID_PERSONA,TIPO_PERSONA,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,DIRECCION," +
                        "RUC,DISTRITO,TELEFONO,SEXO,FECHA_NATAL,ESTADO_CIVIL," +
                        "TIPO_DOC,NRO_DOC,CELULAR,CORREO,ESTADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();

                SQLiteStatement stmtClientes = DataBaseHelper.myDataBase.compileStatement(sqlClientes);
                SQLiteStatement stmtClienteCodigoAntiguo = DataBaseHelper.myDataBase.compileStatement(sqlClienteCodigoAntiguo);
                SQLiteStatement stmtGemCliente = DataBaseHelper.myDataBase.compileStatement(sqlGemCliente);
                SQLiteStatement stmtGemPersona = DataBaseHelper.myDataBase.compileStatement(sqlGemPersona);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmtClientes.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmtClientes.bindString(2,jsonObjectItem.getString("RAZON_SOCIAL"));
                    stmtClientes.bindString(3,jsonObjectItem.getString("RUC"));
                    stmtClientes.bindString(4,jsonObjectItem.getString("GRUPO"));
                    stmtClientes.bindString(5,jsonObjectItem.getString("COD_UBC"));
                    stmtClientes.bindString(6,jsonObjectItem.getString("DOCIDEN"));
                    stmtClientes.bindString(7,jsonObjectItem.getString("DIA_VISITA"));
                    stmtClientes.bindString(8,jsonObjectItem.getString("C_CANAL"));
                    stmtClientes.bindString(9,jsonObjectItem.getString("E_MAIL"));
                    stmtClientes.bindString(10,jsonObjectItem.getString("I_CANC_ANTIGUO"));
                    stmtClientes.bindString(11,jsonObjectItem.getString("DIRECCION"));
                    stmtClientes.bindString(12,jsonObjectItem.getString("I_SITUACION"));
                    stmtClientes.bindString(13,jsonObjectItem.getString("ID_EMPRESA"));

                    stmtClientes.execute();
                    stmtClientes.clearBindings();
                }

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmtClienteCodigoAntiguo.bindString(1,jsonObjectItem.getString("ID_CLIENTE"));
                    stmtClienteCodigoAntiguo.bindString(2,jsonObjectItem.getString("COD_CLIENTE"));
                    stmtClienteCodigoAntiguo.bindString(3,jsonObjectItem.getString("FLAG_VIGENCIA"));
                    stmtClienteCodigoAntiguo.execute();
                    stmtClienteCodigoAntiguo.clearBindings();
                }

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmtGemCliente.bindString(1,jsonObjectItem.getString("ID_CLIENTE"));
                    stmtGemCliente.bindString(2,jsonObjectItem.getString("CODIGO"));
                    stmtGemCliente.bindString(3,jsonObjectItem.getString("RAZON_SOCIAL"));
                    stmtGemCliente.bindString(4,jsonObjectItem.getString("RUC"));
                    stmtGemCliente.bindString(5,jsonObjectItem.getString("CLIENTE_AFECTO"));
                    stmtGemCliente.bindString(6,jsonObjectItem.getString("DNI"));
                    stmtGemCliente.bindString(7,jsonObjectItem.getString("ID_CANAL"));
                    stmtGemCliente.bindString(8,jsonObjectItem.getString("E_MAIL"));
                    stmtGemCliente.bindString(9,jsonObjectItem.getString("COND_PAGO"));
                    stmtGemCliente.bindString(10,jsonObjectItem.getString("ID_GRUPO"));
                    stmtGemCliente.bindString(11,jsonObjectItem.getString("ID_LISTA_DESCUENTO"));
                    stmtGemCliente.bindString(12,jsonObjectItem.getString("ID_CANAL_DETALLE"));
                    stmtGemCliente.bindString(13,jsonObjectItem.getString("CLIENTE_ESPECIAL"));
                    stmtGemCliente.bindString(14,jsonObjectItem.getString("COD_ALM_CONSIG"));

                    stmtGemCliente.execute();
                    stmtGemCliente.clearBindings();
                }

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmtGemPersona.bindString(1,jsonObjectItem.getString("ID_CLIENTE"));
                    stmtGemPersona.bindString(2,jsonObjectItem.getString("TIPO_PERSONA"));
                    stmtGemPersona.bindString(3,jsonObjectItem.getString("APELLIDO_PATERNO"));
                    stmtGemPersona.bindString(4,jsonObjectItem.getString("APELLIDO_MATERNO"));
                    stmtGemPersona.bindString(5,jsonObjectItem.getString("NOMBRES"));
                    stmtGemPersona.bindString(6,jsonObjectItem.getString("DIRECCION"));
                    stmtGemPersona.bindString(7,jsonObjectItem.getString("RUC"));
                    stmtGemPersona.bindString(8,jsonObjectItem.getString("DISTRITO"));
                    stmtGemPersona.bindString(9,jsonObjectItem.getString("TELEFONO"));
                    stmtGemPersona.bindString(10,jsonObjectItem.getString("SEXO"));
                    stmtGemPersona.bindString(11,jsonObjectItem.getString("FECHA_NATAL"));
                    stmtGemPersona.bindString(12,jsonObjectItem.getString("ESTADO_CIVIL"));
                    stmtGemPersona.bindString(13,jsonObjectItem.getString("TIPO_DOC"));
                    stmtGemPersona.bindString(14,jsonObjectItem.getString("NRO_DOC"));
                    stmtGemPersona.bindString(15,jsonObjectItem.getString("CELULAR"));
                    stmtGemPersona.bindString(16,jsonObjectItem.getString("E_MAIL"));
                    stmtGemPersona.bindString(17,jsonObjectItem.getString("ESTADO"));
                    stmtGemPersona.execute();
                    stmtGemPersona.clearBindings();
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Documentos_Cobra_CabBL {

    public ArrayList<Documentos_Cobra_CabBE> lst = null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public JSONObject getAllRest(String newURL) {
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                    documentos_cobra_cabBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    documentos_cobra_cabBE.setN_RECIBO(jsonObjectItem.getString("N_RECIBO"));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(jsonObjectItem.getString("N_SERIE_RECIBO"));
                    documentos_cobra_cabBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    documentos_cobra_cabBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                    documentos_cobra_cabBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    documentos_cobra_cabBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                    documentos_cobra_cabBE.setM_COBRANZA_D(jsonObjectItem.getDouble("M_COBRANZA_D"));
                    documentos_cobra_cabBE.setSALDO(jsonObjectItem.getDouble("SALDO"));
                    documentos_cobra_cabBE.setNUMCHEQ(jsonObjectItem.getString("NUMCHEQ"));
                    documentos_cobra_cabBE.setFECCHEQ(jsonObjectItem.getString("FECCHEQ"));
                    documentos_cobra_cabBE.setID_BANCO(jsonObjectItem.getInt("ID_BANCO"));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(jsonObjectItem.getString("CTACORRIENTE_BANCO"));
                    documentos_cobra_cabBE.setNRO_OPERACION(jsonObjectItem.getString("NRO_OPERACION"));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO(jsonObjectItem.getString("FECHA_DEPOSITO"));
                    documentos_cobra_cabBE.setCOMENTARIO(jsonObjectItem.getString("COMENTARIO"));
                    documentos_cobra_cabBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_cabBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_cabBE.setESTADO(jsonObjectItem.getString("ESTADO"));
                    documentos_cobra_cabBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    documentos_cobra_cabBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    documentos_cobra_cabBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    documentos_cobra_cabBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    documentos_cobra_cabBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    documentos_cobra_cabBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    documentos_cobra_cabBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    documentos_cobra_cabBE.setITEM(jsonObjectItem.getString("ITEM"));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(jsonObjectItem.getString("ESTADO_CONCILIADO"));
                    documentos_cobra_cabBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
                    documentos_cobra_cabBE.setN_PLANILLA(jsonObjectItem.getString("N_PLANILLA"));
                    documentos_cobra_cabBE.setC_PACKING(jsonObjectItem.getString("C_PACKING"));
                    documentos_cobra_cabBE.setID_MOV_BANCO(jsonObjectItem.getString("ID_MOV_BANCO"));
                    documentos_cobra_cabBE.setESTADO_PROCESO(jsonObjectItem.getString("ESTADO_PROCESO"));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(jsonObjectItem.getString("T_CAMBIO_TIENDA"));
                    documentos_cobra_cabBE.setN_TARJETA(jsonObjectItem.getString("N_TARJETA"));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(jsonObjectItem.getInt("ID_MOV_BANCO_ABONO"));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO_ABONO(jsonObjectItem.getString("FECHA_DEPOSITO_ABONO"));
                    documentos_cobra_cabBE.setLOTE(jsonObjectItem.getString("LOTE"));
                    documentos_cobra_cabBE.setFLAG_COBRANZA(jsonObjectItem.getString("FLAG_COBRANZA"));
                    lst.add(documentos_cobra_cabBE);
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

    public JSONObject getPlanilla(String newURL) {
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                    documentos_cobra_cabBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    documentos_cobra_cabBE.setN_RECIBO(jsonObjectItem.getString("N_RECIBO"));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(jsonObjectItem.getString("N_SERIE_RECIBO"));
                    documentos_cobra_cabBE.setFPAGO(jsonObjectItem.getString("FPAGO"));

                    documentos_cobra_cabBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                    documentos_cobra_cabBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    documentos_cobra_cabBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                    documentos_cobra_cabBE.setM_COBRANZA_D(jsonObjectItem.getDouble("M_COBRANZA_D"));
                    documentos_cobra_cabBE.setSALDO(jsonObjectItem.getDouble("SALDO"));

                    documentos_cobra_cabBE.setNUMCHEQ(jsonObjectItem.getString("NUMCHEQ"));
                    documentos_cobra_cabBE.setFECCHEQ(jsonObjectItem.getString("FECCHEQ"));
                    documentos_cobra_cabBE.setID_BANCO(jsonObjectItem.getInt("ID_BANCO"));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(jsonObjectItem.getString("CTACORRIENTE_BANCO"));
                    documentos_cobra_cabBE.setNRO_OPERACION(jsonObjectItem.getString("NRO_OPERACION"));

                    documentos_cobra_cabBE.setFECHA_DEPOSITO(jsonObjectItem.getString("FECHA_DEPOSITO"));
                    documentos_cobra_cabBE.setCOMENTARIO(jsonObjectItem.getString("COMENTARIO"));
                    documentos_cobra_cabBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_cabBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_cabBE.setESTADO(jsonObjectItem.getString("ESTADO"));

                    documentos_cobra_cabBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    documentos_cobra_cabBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    documentos_cobra_cabBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    documentos_cobra_cabBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));

                    documentos_cobra_cabBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    documentos_cobra_cabBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    documentos_cobra_cabBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    documentos_cobra_cabBE.setITEM(jsonObjectItem.getString("ITEM"));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(jsonObjectItem.getString("ESTADO_CONCILIADO"));

                    documentos_cobra_cabBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
                    documentos_cobra_cabBE.setN_PLANILLA(jsonObjectItem.getString("N_PLANILLA"));
                    documentos_cobra_cabBE.setC_PACKING(jsonObjectItem.getString("C_PACKING"));
                    documentos_cobra_cabBE.setID_MOV_BANCO(jsonObjectItem.getString("ID_MOV_BANCO"));
                    documentos_cobra_cabBE.setESTADO_PROCESO(jsonObjectItem.getString("ESTADO_PROCESO"));

                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(jsonObjectItem.getString("T_CAMBIO_TIENDA"));
                    documentos_cobra_cabBE.setN_TARJETA(jsonObjectItem.getString("N_TARJETA"));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(jsonObjectItem.getInt("ID_MOV_BANCO_ABONO"));
                     //****
                    documentos_cobra_cabBE.setID_DOCUMENTO_MOVIMIENTO(jsonObjectItem.getString("ID_DOCUMENTO_MOVIMIENTO"));
                    documentos_cobra_cabBE.setRAZON_SOCIAL(jsonObjectItem.getString("NOMBRECLIENTE"));
                    documentos_cobra_cabBE.setBANCODESC(jsonObjectItem.getString("NOMBREBANCO"));
                    documentos_cobra_cabBE.setESTADODESC(jsonObjectItem.getString("NOMBREESTADO"));
                    documentos_cobra_cabBE.setNOMCOBRADOR(jsonObjectItem.getString("NOMBRECOBRADOR"));
                    lst.add(documentos_cobra_cabBE);
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

    public JSONObject getConciliarDepositos(String newURL) {
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
                String s=jsonObjectRest.getString("message");
                String MSG= Funciones.ObtienMsqOracle(s);
                if(MSG.equals("")){
                    MSG="Error al conciliar.";
                }
                jsonObjectResult.accumulate("MSG", MSG);
            } else{
                JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                //OBTENEMOS LOS VALORES ENVIADOS Y DE RETORNO
                String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");
                if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")) {
                    String sID_LOCAL = jsonObjectItem.getString("ID_LOCAL");
                    String sID_VENDEDOR = jsonObjectItem.getString("ID_VENDEDOR");
                    //jsonObjectResult.accumulate("MSG", jsonObjectItem.getString("MSG"));
                    //SI NO SALTO LAS VALIDACIONES DE LA BD ENTONCES
                   this.PostSincronizarCobranza(sID_EMPRESA,sID_LOCAL,sID_VENDEDOR,"0","40025");
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

    public JSONObject getGeneraPlanilla(String newURL) {
        newURL = newURL.replaceAll(" ","%20");
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
                String s=jsonObjectRest.getString("message");
                String MSG= Funciones.ObtienMsqOracle(s);
                if(MSG.equals("")){
                    MSG=s;
                }
                jsonObjectResult.accumulate("MSG", MSG);
            } else{
                JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");
                String sNPlanilla = jsonObjectItem.getString("N_PLANILLA");

                if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")) {
                    String sID_LOCAL = jsonObjectItem.getString("ID_LOCAL");
                    String sID_VENDEDOR = jsonObjectItem.getString("ID_VENDEDOR");
                    jsonObjectResult.accumulate("MSG", jsonObjectItem.getString("MSG"));
                    jsonObjectResult.accumulate("N_PLANILLA", sNPlanilla);
                    //SI NO SALTO LAS VALIDACIONES DE LA BD ENTONCES
                    this.PostSincronizarCobranza(sID_EMPRESA,sID_LOCAL,sID_VENDEDOR,"0","40005");
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

    public JSONObject PutInsertarPlanilla(String newURL) {
        newURL = newURL.replaceAll(" ","%20");
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setMSG(jsonObjectItem.getString("MSG"));
                    lst.add(documentos_cobra_cabBE);
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

    public JSONObject PutRetornarPlanilla(String newURL) {
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setMSG(jsonObjectItem.getString("MSG"));
                    lst.add(documentos_cobra_cabBE);
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

    public JSONObject getFlujo1(String newURL) {
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                     documentos_cobra_cabBE.setC_PACKING(jsonObjectItem.getString("C_PACKING"));
                     documentos_cobra_cabBE.setFECHA_PLANILLA(jsonObjectItem.getString("FECHA_PLANILLA"));
                     documentos_cobra_cabBE.setESTADO(jsonObjectItem.getString("ESTADO"));
                     documentos_cobra_cabBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                     documentos_cobra_cabBE.setVOUCHER(jsonObjectItem.getString("VOUCHER"));
                     documentos_cobra_cabBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                     documentos_cobra_cabBE.setRUC(jsonObjectItem.getString("RUC"));
                     documentos_cobra_cabBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                     documentos_cobra_cabBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                     documentos_cobra_cabBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                     documentos_cobra_cabBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                     documentos_cobra_cabBE.setCODIGO_FPAGO(jsonObjectItem.getString("CODIGO_FPAGO"));
                     documentos_cobra_cabBE.setNRO_OPERACION(jsonObjectItem.getString("NRO_OPERACION"));
                     documentos_cobra_cabBE.setENTIDAD(jsonObjectItem.getString("ENTIDAD"));
                     documentos_cobra_cabBE.setCONSTANCIA(jsonObjectItem.getString("CONSTANCIA"));
                     documentos_cobra_cabBE.setFECHA(jsonObjectItem.getString("FECHA"));
                     documentos_cobra_cabBE.setFECHA_ABONO(jsonObjectItem.getString("FECHA_ABONO"));
                     documentos_cobra_cabBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                     documentos_cobra_cabBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                     documentos_cobra_cabBE.setRECIBO(jsonObjectItem.getString("RECIBO"));
                     documentos_cobra_cabBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                     documentos_cobra_cabBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                     documentos_cobra_cabBE.setESTADO_CONCILIADO(jsonObjectItem.getString("ESTADO_CONCILIADO"));
                     documentos_cobra_cabBE.setPLANILLA(jsonObjectItem.getString("PLANILLA"));
                    lst.add(documentos_cobra_cabBE);
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

    public JSONObject getSincronizar(String newURL,String sOpcionCobranza) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //SI SE REALIZA LA SINCO¿RONIZACIÓN DE LA OPCIÓN GENERAL, ELEIMINA TODO DEL LOCAL
                if(sOpcionCobranza.equals("0")) {
                    DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_CAB", null, null);
                }

                String SQL="INSERT OR REPLACE INTO S_CCM_DOCUMENTOS_COBRA_CAB(" +
                        "ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                        "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                        "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                        "ID_LOCAL,ESTADO,ITEM,ESTADO_CONCILIADO," +
                        "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                        "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA,GUARDADO,SINCRONIZADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);
                String SFECHA="";

                String ID_COBRANZA="0";
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    SFECHA="";
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    ID_COBRANZA=jsonObjectItem.getString("ID_COBRANZA");

                     //ELIMINAMOS LA COBRANZA LOCAL DE LA COBRANZA QUE ESTAMOS SINCRONIZANDO.
                    if(!sOpcionCobranza.equals("0")) {
                        DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET","ID_COBRANZA = ?", new String[]{String.valueOf(ID_COBRANZA)});
                        DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_CAB","ID_COBRANZA = ?", new String[]{String.valueOf(ID_COBRANZA)});
                    }
                    stmt.bindString(1,ID_COBRANZA);
                    stmt.bindString(2,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(3,jsonObjectItem.getString("N_RECIBO"));
                    stmt.bindString(4,jsonObjectItem.getString("N_SERIE_RECIBO"));
                    stmt.bindString(5,jsonObjectItem.getString("FPAGO"));
                    stmt.bindString(6,jsonObjectItem.getString("ID_COBRADOR"));
                    SFECHA=jsonObjectItem.getString("FECHA");
                    stmt.bindString(7,jsonObjectItem.getString("FECHA"));
                    stmt.bindString(8,jsonObjectItem.getString("M_COBRANZA"));
                    stmt.bindString(9,jsonObjectItem.getString("M_COBRANZA_D"));
                    stmt.bindString(10,jsonObjectItem.getString("SALDO"));
                    stmt.bindString(11,jsonObjectItem.getString("NUMCHEQ"));
                    stmt.bindString(12,jsonObjectItem.getString("FECCHEQ"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_BANCO"));
                    stmt.bindString(14,jsonObjectItem.getString("CTACORRIENTE_BANCO"));
                    stmt.bindString(15,jsonObjectItem.getString("NRO_OPERACION"));
                    stmt.bindString(16,jsonObjectItem.getString("FECHA_DEPOSITO"));
                    stmt.bindString(17,jsonObjectItem.getString("COMENTARIO"));
                    stmt.bindString(18,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(19,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(20,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(21,jsonObjectItem.getString("ITEM"));
                    stmt.bindString(22,jsonObjectItem.getString("ESTADO_CONCILIADO"));
                    stmt.bindString(23,jsonObjectItem.getString("SERIE_PLANILLA"));
                    stmt.bindString(24,jsonObjectItem.getString("N_PLANILLA"));
                    stmt.bindString(25,jsonObjectItem.getString("C_PACKING"));
                    stmt.bindString(26,jsonObjectItem.getString("ID_MOV_BANCO"));
                    stmt.bindString(27,jsonObjectItem.getString("ESTADO_PROCESO"));
                    stmt.bindString(28,jsonObjectItem.getString("T_CAMBIO_TIENDA"));
                    stmt.bindString(29,jsonObjectItem.getString("N_TARJETA"));
                    stmt.bindString(30,jsonObjectItem.getString("ID_MOV_BANCO_ABONO"));
                    stmt.bindString(31,jsonObjectItem.getString("FECHA_DEPOSITO_ABONO"));
                    stmt.bindString(32,jsonObjectItem.getString("LOTE"));
                    stmt.bindString(33,jsonObjectItem.getString("FLAG_COBRANZA"));
                    stmt.bindString(34,"2");
                    stmt.bindString(35,"1");
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

    public JSONObject InsertRest(String ID_COBRANZA,String CODUNC_LOCAL,String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        String SQL=
                "SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                "ID_LOCAL,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA " +
                "FROM  S_CCM_DOCUMENTOS_COBRA_CAB WHERE CODUNC_LOCAL="+CODUNC_LOCAL;

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                     jsonObject.accumulate("ID_COBRANZA", Funciones.isNullColumn(cursor,"ID_COBRANZA","0"));
                     jsonObject.accumulate("COD_CLIENTE", Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                     jsonObject.accumulate("N_RECIBO", Funciones.isNullColumn(cursor,"N_RECIBO",""));
                     jsonObject.accumulate("N_SERIE_RECIBO", Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                     jsonObject.accumulate("FPAGO", Funciones.isNullColumn(cursor,"FPAGO",""));
                     jsonObject.accumulate("ID_COBRADOR", Funciones.isNullColumn(cursor,"ID_COBRADOR","0"));
                     jsonObject.accumulate("FECHA", Funciones.isNullColumn(cursor,"FECHA",""));
                     jsonObject.accumulate("M_COBRANZA", Funciones.isNullColumn(cursor,"M_COBRANZA","0"));
                     jsonObject.accumulate("M_COBRANZA_D", Funciones.isNullColumn(cursor,"M_COBRANZA_D","0"));
                     jsonObject.accumulate("SALDO", Funciones.isNullColumn(cursor,"SALDO",""));
                     jsonObject.accumulate("NUMCHEQ", Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                     jsonObject.accumulate("FECCHEQ", Funciones.isNullColumn(cursor,"FECCHEQ",""));
                     jsonObject.accumulate("ID_BANCO", Funciones.isNullColumn(cursor,"ID_BANCO","0"));
                     jsonObject.accumulate("CTACORRIENTE_BANCO", Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO","0"));
                     jsonObject.accumulate("NRO_OPERACION", Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                     jsonObject.accumulate("FECHA_DEPOSITO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                     jsonObject.accumulate("COMENTARIO", Funciones.isNullColumn(cursor,"COMENTARIO",""));
                     jsonObject.accumulate("ID_EMPRESA", Funciones.isNullColumn(cursor,"ID_EMPRESA","0"));
                     jsonObject.accumulate("ID_LOCAL", Funciones.isNullColumn(cursor,"ID_LOCAL","0"));
                     jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                     jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                     jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                     jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                     jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                     jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                     jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                     jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                     jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                     jsonObject.accumulate("ITEM", Funciones.isNullColumn(cursor,"ITEM",""));
                     jsonObject.accumulate("ESTADO_CONCILIADO", Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                     jsonObject.accumulate("SERIE_PLANILLA", Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                     jsonObject.accumulate("N_PLANILLA", Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                     jsonObject.accumulate("C_PACKING", Funciones.isNullColumn(cursor,"C_PACKING",""));
                     jsonObject.accumulate("ID_MOV_BANCO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                     jsonObject.accumulate("ESTADO_PROCESO", Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                     jsonObject.accumulate("T_CAMBIO_TIENDA", Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                     jsonObject.accumulate("N_TARJETA", Funciones.isNullColumn(cursor,"N_TARJETA",""));
                     jsonObject.accumulate("ID_MOV_BANCO_ABONO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",""));
                     jsonObject.accumulate("FECHA_DEPOSITO_ABONO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                     jsonObject.accumulate("LOTE", Funciones.isNullColumn(cursor,"LOTE",""));
                     jsonObject.accumulate("FLAG_COBRANZA", Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));
                    String SQL2=
                            "SELECT ID_COBRANZA,FPAGO,TIPDOC,SERIE_NUM,NUMERO,IMPORTE," +
                            "MONEDA,SALDO,M_COBRANZA,ID_EMPRESA,ID_LOCAL,ESTADO," +
                             "FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                             "IP_REGISTRO,IP_MODIFICACION,ID_VENDEDOR ,SALDO_INICIAL " +
                             "FROM  S_CCM_DOCUMENTOS_COBRA_DET WHERE ESTADO<>'-1' AND  ID_COBRANZA="+Funciones.isNullColumn(cursor,"ID_COBRANZA","0");

                    JSONArray jsonArray= new JSONArray();
                    Cursor cursorDet = null;
                    cursorDet= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
                    Double M_COBRANZA=0.0,SALDO_INICIAL=0.0;
                    if (cursorDet.moveToFirst()) {
                        do {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.accumulate("ID_COBRANZA ",Funciones.isNullColumn(cursorDet,"ID_COBRANZA",0));
                            jsonObject1.accumulate("FPAGO",Funciones.isNullColumn(cursorDet,"FPAGO",""));
                            jsonObject1.accumulate("TIPDOC",Funciones.isNullColumn(cursorDet,"TIPDOC",""));
                            jsonObject1.accumulate("SERIE_NUM",Funciones.isNullColumn(cursorDet,"SERIE_NUM",""));
                            jsonObject1.accumulate("NUMERO",Funciones.isNullColumn(cursorDet,"NUMERO",0));
                            jsonObject1.accumulate("IMPORTE",Funciones.isNullColumn(cursorDet,"IMPORTE",0.0));
                            jsonObject1.accumulate("MONEDA",Funciones.isNullColumn(cursorDet,"MONEDA",""));
                            jsonObject1.accumulate("SALDO",Funciones.isNullColumn(cursorDet,"SALDO",0.0));
                            M_COBRANZA=Double.valueOf(Funciones.isNullColumn(cursorDet,"M_COBRANZA",0.0));

                            jsonObject1.accumulate("M_COBRANZA",Funciones.isNullColumn(cursorDet,"M_COBRANZA",0.0));
                            jsonObject1.accumulate("ID_EMPRESA",Funciones.isNullColumn(cursorDet,"ID_EMPRESA",0));
                            jsonObject1.accumulate("ID_LOCAL",Funciones.isNullColumn(cursorDet,"ID_LOCAL",0));
                            jsonObject1.accumulate("ESTADO",Funciones.isNullColumn(cursorDet,"ESTADO",0));
                            jsonObject1.accumulate("FECHA_REGISTRO",Funciones.isNullColumn(cursorDet,"FECHA_REGISTRO",""));
                            jsonObject1.accumulate("FECHA_MODIFICACION",Funciones.isNullColumn(cursorDet,"FECHA_MODIFICACION",""));
                            jsonObject1.accumulate("USUARIO_REGISTRO",Funciones.isNullColumn(cursorDet,"USUARIO_REGISTRO",""));
                            jsonObject1.accumulate("USUARIO_MODIFICACION",Funciones.isNullColumn(cursorDet,"USUARIO_MODIFICACION",""));
                            jsonObject1.accumulate("PC_REGISTRO",Funciones.isNullColumn(cursorDet,"PC_REGISTRO",""));
                            jsonObject1.accumulate("PC_MODIFICACION",Funciones.isNullColumn(cursorDet,"PC_MODIFICACION",""));
                            jsonObject1.accumulate("IP_REGISTRO",Funciones.isNullColumn(cursorDet,"IP_REGISTRO",""));
                            jsonObject1.accumulate("IP_MODIFICACION",Funciones.isNullColumn(cursorDet,"IP_MODIFICACION",""));
                            jsonObject1.accumulate("ID_VENDEDOR ",Funciones.isNullColumn(cursorDet,"ID_VENDEDOR",0));

                            SALDO_INICIAL=Double.valueOf(Funciones.isNullColumn(cursorDet,"SALDO_INICIAL",0.0));
                            jsonObject1.accumulate("SALDO_INICIAL ",Funciones.isNullColumn(cursorDet,"SALDO_INICIAL",0.0));
                            jsonObject1.accumulate("VOUCHER",Funciones.isNullColumn(cursorDet,"VOUCHER",0));
                            jsonArray.put(jsonObject1);
                        } while (cursorDet.moveToNext());
                        jsonObject.accumulate("detalle",jsonArray);
                    }
                    String aux = new RestClientLibrary().post(newURL,jsonObject);
                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getString("status").trim()));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getString("status").trim().equals("0")) {
                    } else {
                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        //OBTENEMOS LOS VALORES ENVIADOS Y DE RETORNO
                        String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");
                        if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")){
                            String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");
                            String sID_VENDEDOR= jsonObjectItem.getString("ID_VENDEDOR");
                            String sID_COBRANZA_LOCAL= jsonObjectItem.getString("ID_COBRANZA");
                            String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");
                            String MSG=jsonObjectItem.getString("MSG");
                            jsonObjectResult.accumulate("MSG",MSG);

                            //SI NO SALTO LAS VALIDACIONES DE LA BD ENTONCES
                            if(MSG.toString().trim().equals("-")){
                                //ELIMINAMOS LA COBRANZA LOCAL
                                this.EliminarCobranzaByID(sID_COBRANZA_LOCAL);
                                //VOLVEMOS A SINCRONIZAR LA COBRANZA SOLO CON sID_COBRANZA_ORACLE
                                this.PostSincronizarCobranza(sID_EMPRESA,sID_LOCAL,sID_VENDEDOR,sID_COBRANZA_ORACLE,"0");
                            }
                        }

                    }
                } while (cursor.moveToNext());
            }
            //Eliminar(ID_COBRANZA);
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", "1");
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }

    public JSONObject Insert_UpdateRest(String ID_COBRANZA,String CODUNC_LOCAL,String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        String SQL=
                "SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                        "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                        "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                        "ID_LOCAL,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                        "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                        "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                        "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA " +
                        "FROM  S_CCM_DOCUMENTOS_COBRA_CAB WHERE ID_COBRANZA="+ID_COBRANZA;

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("ID_COBRANZA", Funciones.isNullColumn(cursor,"ID_COBRANZA","0"));
                    jsonObject.accumulate("COD_CLIENTE", Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    jsonObject.accumulate("N_RECIBO", Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    jsonObject.accumulate("N_SERIE_RECIBO", Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    jsonObject.accumulate("FPAGO", Funciones.isNullColumn(cursor,"FPAGO",""));
                    jsonObject.accumulate("ID_COBRADOR", Funciones.isNullColumn(cursor,"ID_COBRADOR",""));
                    jsonObject.accumulate("FECHA", Funciones.isNullColumn(cursor,"FECHA",""));
                    jsonObject.accumulate("M_COBRANZA", Funciones.isNullColumn(cursor,"M_COBRANZA","0"));
                    jsonObject.accumulate("M_COBRANZA_D", Funciones.isNullColumn(cursor,"M_COBRANZA_D","0"));
                    jsonObject.accumulate("SALDO", Funciones.isNullColumn(cursor,"SALDO",""));
                    jsonObject.accumulate("NUMCHEQ", Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    jsonObject.accumulate("FECCHEQ", Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    jsonObject.accumulate("ID_BANCO", Funciones.isNullColumn(cursor,"ID_BANCO",""));
                    jsonObject.accumulate("CTACORRIENTE_BANCO", Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    jsonObject.accumulate("NRO_OPERACION", Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    jsonObject.accumulate("FECHA_DEPOSITO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    jsonObject.accumulate("COMENTARIO", Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    jsonObject.accumulate("ID_EMPRESA", Funciones.isNullColumn(cursor,"ID_EMPRESA",""));
                    jsonObject.accumulate("ID_LOCAL", Funciones.isNullColumn(cursor,"ID_LOCAL",""));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    jsonObject.accumulate("ITEM", Funciones.isNullColumn(cursor,"ITEM",""));
                    jsonObject.accumulate("ESTADO_CONCILIADO", Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    jsonObject.accumulate("SERIE_PLANILLA", Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    jsonObject.accumulate("N_PLANILLA", Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    jsonObject.accumulate("C_PACKING", Funciones.isNullColumn(cursor,"C_PACKING",""));
                    jsonObject.accumulate("ID_MOV_BANCO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    jsonObject.accumulate("ESTADO_PROCESO", Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    jsonObject.accumulate("T_CAMBIO_TIENDA", Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    jsonObject.accumulate("N_TARJETA", Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    jsonObject.accumulate("ID_MOV_BANCO_ABONO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",""));
                    jsonObject.accumulate("FECHA_DEPOSITO_ABONO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    jsonObject.accumulate("LOTE", Funciones.isNullColumn(cursor,"LOTE",""));
                    jsonObject.accumulate("FLAG_COBRANZA", Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));
                    String SQL2=
                            "SELECT ID_COBRANZA,FPAGO,TIPDOC,SERIE_NUM,NUMERO,IMPORTE," +
                                    "MONEDA,SALDO,M_COBRANZA,ID_EMPRESA,ID_LOCAL,ESTADO," +
                                    "FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                                    "IP_REGISTRO,IP_MODIFICACION,ID_VENDEDOR ,SALDO_INICIAL " +
                                    "FROM  S_CCM_DOCUMENTOS_COBRA_DET WHERE ESTADO<>'-1' AND ID_COBRANZA="+ ID_COBRANZA;

                    Double M_COBRANZA=0.0,SALDO_INICIAL=0.0;
                    JSONArray jsonArray= new JSONArray();
                    Cursor cursorDet = null;
                    cursorDet= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
                    if (cursorDet.moveToFirst()) {
                        do {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.accumulate("ID_COBRANZA ",Funciones.isNullColumn(cursorDet,"ID_COBRANZA",0));
                            jsonObject1.accumulate("FPAGO",Funciones.isNullColumn(cursorDet,"FPAGO",""));
                            jsonObject1.accumulate("TIPDOC",Funciones.isNullColumn(cursorDet,"TIPDOC",""));
                            jsonObject1.accumulate("SERIE_NUM",Funciones.isNullColumn(cursorDet,"SERIE_NUM",""));
                            jsonObject1.accumulate("NUMERO",Funciones.isNullColumn(cursorDet,"NUMERO",0));
                            jsonObject1.accumulate("IMPORTE",Funciones.isNullColumn(cursorDet,"IMPORTE",0));
                            jsonObject1.accumulate("MONEDA",Funciones.isNullColumn(cursorDet,"MONEDA",""));
                            jsonObject1.accumulate("SALDO",Funciones.isNullColumn(cursorDet,"SALDO",0));

                            M_COBRANZA=Double.valueOf(Funciones.isNullColumn(cursorDet,"M_COBRANZA",0.0));
                            jsonObject1.accumulate("M_COBRANZA",M_COBRANZA);

                            jsonObject1.accumulate("ID_EMPRESA",Funciones.isNullColumn(cursorDet,"ID_EMPRESA",0));
                            jsonObject1.accumulate("ID_LOCAL",Funciones.isNullColumn(cursorDet,"ID_LOCAL",0));
                            jsonObject1.accumulate("ESTADO",Funciones.isNullColumn(cursorDet,"ESTADO",0));
                            jsonObject1.accumulate("FECHA_REGISTRO",Funciones.isNullColumn(cursorDet,"FECHA_REGISTRO",""));
                            jsonObject1.accumulate("FECHA_MODIFICACION",Funciones.isNullColumn(cursorDet,"FECHA_MODIFICACION",""));
                            jsonObject1.accumulate("USUARIO_REGISTRO",Funciones.isNullColumn(cursorDet,"USUARIO_REGISTRO",""));
                            jsonObject1.accumulate("USUARIO_MODIFICACION",Funciones.isNullColumn(cursorDet,"USUARIO_MODIFICACION",""));
                            jsonObject1.accumulate("PC_REGISTRO",Funciones.isNullColumn(cursorDet,"PC_REGISTRO",""));
                            jsonObject1.accumulate("PC_MODIFICACION",Funciones.isNullColumn(cursorDet,"PC_MODIFICACION",""));
                            jsonObject1.accumulate("IP_REGISTRO",Funciones.isNullColumn(cursorDet,"IP_REGISTRO",""));
                            jsonObject1.accumulate("IP_MODIFICACION",Funciones.isNullColumn(cursorDet,"IP_MODIFICACION",""));
                            jsonObject1.accumulate("ID_VENDEDOR ",Funciones.isNullColumn(cursorDet,"ID_VENDEDOR",0));
                            SALDO_INICIAL=Double.valueOf(Funciones.isNullColumn(cursorDet,"SALDO_INICIAL",0.0));
                            jsonObject1.accumulate("SALDO_INICIAL",SALDO_INICIAL);
                            jsonObject1.accumulate("VOUCHER",Funciones.isNullColumn(cursorDet,"VOUCHER",0));
                            jsonArray.put(jsonObject1);
                        } while (cursorDet.moveToNext());
                        jsonObject.accumulate("detalle",jsonArray);
                    }
                    String aux = new RestClientLibrary().post(newURL,jsonObject);
                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getInt("status")));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getInt("status")==0) {
                    } else {
                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");

                        if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")){
                            String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");
                            String sID_VENDEDOR= jsonObjectItem.getString("ID_VENDEDOR");
                            String sID_COBRANZA= jsonObjectItem.getString("ID_COBRANZA");
                            String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");

                            //ELIMINAMOS LA COBRANZA LOCAL
                            //this.EliminarCobranzaByID(sID_COBRANZA);
                            //VOLVEMOS A SINCRONIZAR LA COBRANZA SOLO CON sID_COBRANZA_ORACLE
                            this.PostSincronizarCobranza(sID_EMPRESA,sID_LOCAL,sID_VENDEDOR,sID_COBRANZA_ORACLE,"0");
                        }
                    }
                } while (cursor.moveToNext());
            }
            //Eliminar(ID_COBRANZA);
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", false);
                jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }

    public JSONObject UpdateRest(String ID_COBRANZA,String TIPO_EVENTO,String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        String SQL=
                "SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                    "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                    "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                    "ID_LOCAL,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                    "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                    "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                    "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA " +
                    "FROM  S_CCM_DOCUMENTOS_COBRA_CAB WHERE GUARDADO='3'";

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("ID_COBRANZA", Funciones.isNullColumn(cursor,"ID_COBRANZA",""));
                    jsonObject.accumulate("COD_CLIENTE", Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    jsonObject.accumulate("N_RECIBO", Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    jsonObject.accumulate("N_SERIE_RECIBO", Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    jsonObject.accumulate("FPAGO", Funciones.isNullColumn(cursor,"FPAGO",""));
                    jsonObject.accumulate("ID_COBRADOR", Funciones.isNullColumn(cursor,"ID_COBRADOR",""));
                    jsonObject.accumulate("FECHA", Funciones.isNullColumn(cursor,"FECHA",""));
                    jsonObject.accumulate("M_COBRANZA", Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    jsonObject.accumulate("M_COBRANZA_D", Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    jsonObject.accumulate("SALDO", Funciones.isNullColumn(cursor,"SALDO",0.0));
                    jsonObject.accumulate("NUMCHEQ", Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    jsonObject.accumulate("FECCHEQ", Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    jsonObject.accumulate("ID_BANCO", Funciones.isNullColumn(cursor,"ID_BANCO",""));
                    jsonObject.accumulate("CTACORRIENTE_BANCO", Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    jsonObject.accumulate("NRO_OPERACION", Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    jsonObject.accumulate("FECHA_DEPOSITO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    jsonObject.accumulate("COMENTARIO", Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    jsonObject.accumulate("ID_EMPRESA", Funciones.isNullColumn(cursor,"ID_EMPRESA",""));
                    jsonObject.accumulate("ID_LOCAL", Funciones.isNullColumn(cursor,"ID_LOCAL",""));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    jsonObject.accumulate("ITEM", Funciones.isNullColumn(cursor,"ITEM",""));
                    jsonObject.accumulate("ESTADO_CONCILIADO", Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    jsonObject.accumulate("SERIE_PLANILLA", Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    jsonObject.accumulate("N_PLANILLA", Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    jsonObject.accumulate("C_PACKING", Funciones.isNullColumn(cursor,"C_PACKING",""));
                    jsonObject.accumulate("ID_MOV_BANCO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    jsonObject.accumulate("ESTADO_PROCESO", Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    jsonObject.accumulate("T_CAMBIO_TIENDA", Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    jsonObject.accumulate("N_TARJETA", Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    jsonObject.accumulate("ID_MOV_BANCO_ABONO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",""));
                    jsonObject.accumulate("FECHA_DEPOSITO_ABONO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    jsonObject.accumulate("LOTE", Funciones.isNullColumn(cursor,"LOTE",""));
                    jsonObject.accumulate("FLAG_COBRANZA", Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));
                    jsonObject.accumulate("TIPO_EVENTO",TIPO_EVENTO);

                    String SQL2=
                            "SELECT ID_COBRANZA,FPAGO,TIPDOC,SERIE_NUM,NUMERO,IMPORTE," +
                                    "MONEDA,SALDO,M_COBRANZA,ID_EMPRESA,ID_LOCAL,ESTADO," +
                                    "FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                                    "IP_REGISTRO,IP_MODIFICACION,ID_VENDEDOR ,SALDO_INICIAL " +
                                    "FROM  S_CCM_DOCUMENTOS_COBRA_DET WHERE ID_COBRANZA=" + Funciones.isNullColumn(cursor,"ID_COBRANZA","0");

                    JSONArray jsonArray= new JSONArray();
                    Cursor cursorDet = null;
                    cursorDet= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
                    if (cursorDet.moveToFirst()) {
                        do {
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.accumulate("ID_COBRANZA ",Funciones.isNullColumn(cursorDet,"ID_COBRANZA",0));
                            jsonObject1.accumulate("FPAGO",Funciones.isNullColumn(cursorDet,"FPAGO",""));
                            jsonObject1.accumulate("TIPDOC",Funciones.isNullColumn(cursorDet,"TIPDOC",""));
                            jsonObject1.accumulate("SERIE_NUM",Funciones.isNullColumn(cursorDet,"SERIE_NUM",""));
                            jsonObject1.accumulate("NUMERO",Funciones.isNullColumn(cursorDet,"NUMERO",0));
                            jsonObject1.accumulate("IMPORTE",Funciones.isNullColumn(cursorDet,"IMPORTE",0.0));
                            jsonObject1.accumulate("MONEDA",Funciones.isNullColumn(cursorDet,"MONEDA",""));
                            jsonObject1.accumulate("SALDO",Funciones.isNullColumn(cursorDet,"SALDO",0.0));
                            jsonObject1.accumulate("M_COBRANZA",Funciones.isNullColumn(cursorDet,"M_COBRANZA",0.0));
                            jsonObject1.accumulate("ID_EMPRESA",Funciones.isNullColumn(cursorDet,"ID_EMPRESA",0));
                            jsonObject1.accumulate("ID_LOCAL",Funciones.isNullColumn(cursorDet,"ID_LOCAL",0));
                            jsonObject1.accumulate("ESTADO",Funciones.isNullColumn(cursorDet,"ESTADO",0));
                            jsonObject1.accumulate("FECHA_REGISTRO",Funciones.isNullColumn(cursorDet,"FECHA_REGISTRO",""));
                            jsonObject1.accumulate("FECHA_MODIFICACION",Funciones.isNullColumn(cursorDet,"FECHA_MODIFICACION",""));
                            jsonObject1.accumulate("USUARIO_REGISTRO",Funciones.isNullColumn(cursorDet,"USUARIO_REGISTRO",""));
                            jsonObject1.accumulate("USUARIO_MODIFICACION",Funciones.isNullColumn(cursorDet,"USUARIO_MODIFICACION",""));
                            jsonObject1.accumulate("PC_REGISTRO",Funciones.isNullColumn(cursorDet,"PC_REGISTRO",""));
                            jsonObject1.accumulate("PC_MODIFICACION",Funciones.isNullColumn(cursorDet,"PC_MODIFICACION",""));
                            jsonObject1.accumulate("IP_REGISTRO",Funciones.isNullColumn(cursorDet,"IP_REGISTRO",""));
                            jsonObject1.accumulate("IP_MODIFICACION",Funciones.isNullColumn(cursorDet,"IP_MODIFICACION",""));
                            jsonObject1.accumulate("ID_VENDEDOR ",Funciones.isNullColumn(cursorDet,"ID_VENDEDOR",0));
                            jsonObject1.accumulate("SALDO_INICIAL",Funciones.isNullColumn(cursorDet,"SALDO_INICIAL",0.0));
                            jsonObject1.accumulate("VOUCHER",Funciones.isNullColumn(cursorDet,"VOUCHER",0));
                            jsonArray.put(jsonObject1);
                        } while (cursorDet.moveToNext());
                        jsonObject.accumulate("detalle",jsonArray);
                    }
                    String aux = new RestClientLibrary().post(newURL,jsonObject);
                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult = new JSONObject();
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getInt("status")));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getInt("status")==0) {
                    } else {
                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");

                        if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")){
                            String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");
                            String sID_VENDEDOR= jsonObjectItem.getString("ID_VENDEDOR");
                            String sID_COBRANZA= jsonObjectItem.getString("ID_COBRANZA");
                            String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");

                            //ELIMINAMOS LA COBRANZA LOCAL
                            //this.EliminarCobranzaByID(sID_COBRANZA);
                            //VOLVEMOS A SINCRONIZAR LA COBRANZA SOLO CON sID_COBRANZA_ORACLE
                            this.PostSincronizarCobranza(sID_EMPRESA,sID_LOCAL,sID_VENDEDOR,sID_COBRANZA_ORACLE,"0");
                        }
                    }
                } while (cursor.moveToNext());
            }
            //Eliminar(ID_COBRANZA);
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", false);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }

    public JSONObject DeleteRest(String ID_COBRANZA,String CODUNC_LOCAL,String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();

        String SQL=
                "SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                        "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                        "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                        "ID_LOCAL,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                        "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                        "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                        "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA " +
                        "FROM  S_CCM_DOCUMENTOS_COBRA_CAB WHERE ID_COBRANZA="+ID_COBRANZA;

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("ID_COBRANZA", Funciones.isNullColumn(cursor,"ID_COBRANZA",""));
                    jsonObject.accumulate("COD_CLIENTE", Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    jsonObject.accumulate("N_RECIBO", Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    jsonObject.accumulate("N_SERIE_RECIBO", Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    jsonObject.accumulate("FPAGO", Funciones.isNullColumn(cursor,"FPAGO",""));
                    jsonObject.accumulate("ID_COBRADOR", Funciones.isNullColumn(cursor,"ID_COBRADOR",""));
                    jsonObject.accumulate("FECHA", Funciones.isNullColumn(cursor,"FECHA",""));
                    jsonObject.accumulate("M_COBRANZA", Funciones.isNullColumn(cursor,"M_COBRANZA","0"));
                    jsonObject.accumulate("M_COBRANZA_D", Funciones.isNullColumn(cursor,"M_COBRANZA_D","0"));
                    jsonObject.accumulate("SALDO", Funciones.isNullColumn(cursor,"SALDO",""));
                    jsonObject.accumulate("NUMCHEQ", Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    jsonObject.accumulate("FECCHEQ", Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    jsonObject.accumulate("ID_BANCO", Funciones.isNullColumn(cursor,"ID_BANCO",""));
                    jsonObject.accumulate("CTACORRIENTE_BANCO", Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    jsonObject.accumulate("NRO_OPERACION", Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    jsonObject.accumulate("FECHA_DEPOSITO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    jsonObject.accumulate("COMENTARIO", Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    jsonObject.accumulate("ID_EMPRESA", Funciones.isNullColumn(cursor,"ID_EMPRESA",""));
                    jsonObject.accumulate("ID_LOCAL", Funciones.isNullColumn(cursor,"ID_LOCAL",""));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    jsonObject.accumulate("ITEM", Funciones.isNullColumn(cursor,"ITEM",""));
                    jsonObject.accumulate("ESTADO_CONCILIADO", Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    jsonObject.accumulate("SERIE_PLANILLA", Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    jsonObject.accumulate("N_PLANILLA", Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    jsonObject.accumulate("C_PACKING", Funciones.isNullColumn(cursor,"C_PACKING",""));
                    jsonObject.accumulate("ID_MOV_BANCO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    jsonObject.accumulate("ESTADO_PROCESO", Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    jsonObject.accumulate("T_CAMBIO_TIENDA", Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    jsonObject.accumulate("N_TARJETA", Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    jsonObject.accumulate("ID_MOV_BANCO_ABONO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",""));
                    jsonObject.accumulate("FECHA_DEPOSITO_ABONO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    jsonObject.accumulate("LOTE", Funciones.isNullColumn(cursor,"LOTE",""));
                    jsonObject.accumulate("FLAG_COBRANZA", Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));

                    String aux = new RestClientLibrary().post(newURL,jsonObject);
                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getInt("status")));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getInt("status")==0) {
                    } else {
                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        String sID_COBRANZA= jsonObjectItem.getString("ID_COBRANZA");
                        String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");
                        String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");
                        String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");

                        jsonObjectResult.accumulate("iID_COBRANZA",sID_COBRANZA);
                        jsonObjectResult.accumulate("iID_COBRANZA_ORACLE", sID_COBRANZA_ORACLE);

                        //SI ELEIMINO EN EL ORACLE ENTONCES ANULA LA COBRANZA LOCAL
                        EliminarCobranzaByID(sID_COBRANZA);

                    }
                } while (cursor.moveToNext());
            }
            //Eliminar(ID_COBRANZA);
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", false);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }

    private void PostSincronizarCobranza(String sID_EMPRESA,String sID_LOCAL,String sID_VENDEDOR,String sID_COBRANZA_ORACLE,String ESTADO){
        try{
            //VOLVEMOS A SINCRONIZAR LA DATA ACTUALIZADA
            //CABECERA
            String sURLCobranza_Cab=ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab + '/'
                    + sID_EMPRESA + '/'
                    + sID_LOCAL + '/'
                    + sID_VENDEDOR+ '/'
                    + sID_COBRANZA_ORACLE+ '/'
                    + ESTADO;

            getSincronizar(sURLCobranza_Cab,"1");

            //DETALLE
            String sURLCobranza_Det=ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_det + '/'
                    + sID_EMPRESA + '/'
                    + sID_LOCAL + '/'
                    + sID_VENDEDOR+ '/'
                    + sID_COBRANZA_ORACLE+ '/'
                    + ESTADO;

            Documentos_Cobra_DetBL documentos_cobra_detBL=new Documentos_Cobra_DetBL();
            documentos_cobra_detBL.getSincronizar(sURLCobranza_Det,"1");

        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
        }
    }

    private void EliminarCobranzaByID(String ID_COBRANZA){
        try{
            DataBaseHelper.myDataBase.beginTransaction();
            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET","ID_COBRANZA = ?", new String[]{String.valueOf(ID_COBRANZA)});
            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_CAB","ID_COBRANZA = ?", new String[]{String.valueOf(ID_COBRANZA)});
            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();
        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
        }
    }


    private void ActualizaEstadoGuardadoCobranzaByID(String ID_COBRANZA){
        try{
            ContentValues cv2 = new ContentValues();
            cv2.put("GUARDADO", 2); //ENVIADO Y SINCRONIZADO
            DataBaseHelper.myDataBase.beginTransaction();
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB", cv2, "ID_COBRANZA = ?", new String[]{String.valueOf(ID_COBRANZA)});
            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();

        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
        }
    }



    public JSONObject AnulaRest(String motivoAnulacion, String ID_COBRANZA,String CODUNC_LOCAL,String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();

        String SQL=
                "SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO,ID_COBRADOR," +
                        "FECHA,M_COBRANZA,M_COBRANZA_D,SALDO,NUMCHEQ,FECCHEQ," +
                        "ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION,FECHA_DEPOSITO,COMENTARIO,ID_EMPRESA," +
                        "ID_LOCAL,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                        "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                        "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO,T_CAMBIO_TIENDA," +
                        "N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA " +
                        "FROM  S_CCM_DOCUMENTOS_COBRA_CAB WHERE ID_COBRANZA="+ID_COBRANZA;

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("ID_COBRANZA", Funciones.isNullColumn(cursor,"ID_COBRANZA",""));
                    jsonObject.accumulate("COD_CLIENTE", Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    jsonObject.accumulate("N_RECIBO", Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    jsonObject.accumulate("N_SERIE_RECIBO", Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    jsonObject.accumulate("FPAGO", Funciones.isNullColumn(cursor,"FPAGO",""));
                    jsonObject.accumulate("ID_COBRADOR", Funciones.isNullColumn(cursor,"ID_COBRADOR",""));
                    jsonObject.accumulate("FECHA", Funciones.isNullColumn(cursor,"FECHA",""));
                    jsonObject.accumulate("M_COBRANZA", Funciones.isNullColumn(cursor,"M_COBRANZA","0"));
                    jsonObject.accumulate("M_COBRANZA_D", Funciones.isNullColumn(cursor,"M_COBRANZA_D","0"));
                    jsonObject.accumulate("SALDO", Funciones.isNullColumn(cursor,"SALDO",""));
                    jsonObject.accumulate("NUMCHEQ", Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    jsonObject.accumulate("FECCHEQ", Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    jsonObject.accumulate("ID_BANCO", Funciones.isNullColumn(cursor,"ID_BANCO",""));
                    jsonObject.accumulate("CTACORRIENTE_BANCO", Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    jsonObject.accumulate("NRO_OPERACION", Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    jsonObject.accumulate("FECHA_DEPOSITO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    jsonObject.accumulate("COMENTARIO", motivoAnulacion.trim().toUpperCase());
                    jsonObject.accumulate("ID_EMPRESA", Funciones.isNullColumn(cursor,"ID_EMPRESA",""));
                    jsonObject.accumulate("ID_LOCAL", Funciones.isNullColumn(cursor,"ID_LOCAL",""));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    jsonObject.accumulate("ITEM", Funciones.isNullColumn(cursor,"ITEM",""));
                    jsonObject.accumulate("ESTADO_CONCILIADO", Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    jsonObject.accumulate("SERIE_PLANILLA", Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    jsonObject.accumulate("N_PLANILLA", Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    jsonObject.accumulate("C_PACKING", Funciones.isNullColumn(cursor,"C_PACKING",""));
                    jsonObject.accumulate("ID_MOV_BANCO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    jsonObject.accumulate("ESTADO_PROCESO", Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    jsonObject.accumulate("T_CAMBIO_TIENDA", Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    jsonObject.accumulate("N_TARJETA", Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    jsonObject.accumulate("ID_MOV_BANCO_ABONO", Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",""));
                    jsonObject.accumulate("FECHA_DEPOSITO_ABONO", Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    jsonObject.accumulate("LOTE", Funciones.isNullColumn(cursor,"LOTE",""));
                    jsonObject.accumulate("FLAG_COBRANZA", Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));

                    String aux = new RestClientLibrary().post(newURL,jsonObject);
                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getInt("status")));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getInt("status")==0) {
                    } else {
                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        String sID_COBRANZA= jsonObjectItem.getString("ID_COBRANZA");
                        String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");
                        String sID_EMPRESA= jsonObjectItem.getString("ID_EMPRESA");
                        String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");

                        jsonObjectResult.accumulate("iID_COBRANZA",sID_COBRANZA);
                        jsonObjectResult.accumulate("iID_COBRANZA_ORACLE", sID_COBRANZA_ORACLE);

                        //ACTUALIZO EL ESTADO EN EL SQLLITE PARA QUE NO SE ENVIE DE NUEVO.
                        ActualizaEstadoGuardadoCobranzaByID(sID_COBRANZA);

                    }
                } while (cursor.moveToNext());
            }
            //Eliminar(ID_COBRANZA);
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", false);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }



}

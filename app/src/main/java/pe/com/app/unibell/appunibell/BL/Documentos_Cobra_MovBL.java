package pe.com.app.unibell.appunibell.BL;


import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Documentos_Cobra_MovBL {
    public ArrayList<Documentos_Cobra_MovBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(jsonObjectItem.getInt("ID_DOCUMENTO_MOVIMIENTO"));
                    documentos_cobra_movBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
                    documentos_cobra_movBE.setN_PLANILLA(jsonObjectItem.getString("N_PLANILLA"));
                    documentos_cobra_movBE.setID_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_USUARIO_REGISTRO"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO (jsonObjectItem.getInt("ID_ROL_USUARIO_REGISTRO"));
                    documentos_cobra_movBE.setFECHA_RECEPCION (jsonObjectItem.getString("FECHA_RECEPCION"));
                    documentos_cobra_movBE.setID_USUARIO_DERIVAR (jsonObjectItem.getInt("ID_USUARIO_DERIVAR"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_DERIVAR (jsonObjectItem.getInt("ID_ROL_USUARIO_DERIVAR"));
                    documentos_cobra_movBE.setFECHA_DERIVAR (jsonObjectItem.getString("FECHA_DERIVAR"));
                    documentos_cobra_movBE.setFECHA_MOVIMIENTO (jsonObjectItem.getString("FECHA_MOVIMIENTO"));
                    documentos_cobra_movBE.setFECHA_ACCION (jsonObjectItem.getString("FECHA_ACCION"));
                    documentos_cobra_movBE.setESTADO_MOVIMIENTO (jsonObjectItem.getInt("ESTADO_MOVIMIENTO"));
                    documentos_cobra_movBE.setID_EMPRESA (jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_movBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    lst.add(documentos_cobra_movBE);
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

    public JSONObject getFlujoResumen(String newURL) {
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setORDEN(jsonObjectItem.getInt("ORDEN"));
                    documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(jsonObjectItem.getInt("ID_DOCUMENTO_MOVIMIENTO"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_ROL_USUARIO"));
                    documentos_cobra_movBE.setNOMBRE_ROL(jsonObjectItem.getString("NOMBRE_ROL"));
                    documentos_cobra_movBE.setNOMBRE_USUARIO(jsonObjectItem.getString("NOMBRE_USUARIO"));

               /*     documentos_cobra_movBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
                    documentos_cobra_movBE.setN_PLANILLA(jsonObjectItem.getString("N_PLANILLA"));
                    documentos_cobra_movBE.setID_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_USUARIO_REGISTRO"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO (jsonObjectItem.getInt("ID_ROL_USUARIO_REGISTRO"));
                    documentos_cobra_movBE.setFECHA_RECEPCION (jsonObjectItem.getString("FECHA_RECEPCION").replace("null",""));
                    documentos_cobra_movBE.setID_USUARIO_DERIVAR (jsonObjectItem.getInt("ID_USUARIO_DERIVAR"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_DERIVAR (jsonObjectItem.getInt("ID_ROL_USUARIO_DERIVAR"));
                    documentos_cobra_movBE.setFECHA_DERIVAR (jsonObjectItem.getString("FECHA_DERIVAR").replace("null",""));
                    documentos_cobra_movBE.setFECHA_MOVIMIENTO (jsonObjectItem.getString("FECHA_MOVIMIENTO").replace("null",""));
                    documentos_cobra_movBE.setFECHA_ACCION (jsonObjectItem.getString("FECHA_ACCION").replace("null",""));
                    documentos_cobra_movBE.setESTADO_MOVIMIENTO (jsonObjectItem.getInt("ESTADO_MOVIMIENTO"));
                    documentos_cobra_movBE.setID_EMPRESA (jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_movBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_movBE.setNOM_ROL_ORIGEN(jsonObjectItem.getString("NOM_ROL_ORIGEN"));
                    documentos_cobra_movBE.setNOM_ROL_DESTINO(jsonObjectItem.getString("NOM_ROL_DESTINO"));
                    documentos_cobra_movBE.setNOM_ESTADO_MOVIMIENTO(jsonObjectItem.getString("NOM_ESTADO_MOVIMIENTO"));
                    documentos_cobra_movBE.setNOM_PERSONA_ORIGEN(jsonObjectItem.getString("NOM_PERSONA_ORIGEN"));
                    documentos_cobra_movBE.setNOM_PERSONA_DESTINO(jsonObjectItem.getString("NOM_PERSONA_DESTINO"));
                */    lst.add(documentos_cobra_movBE);
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

    public JSONObject getFlujo2(String newURL) {
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setORDEN(jsonObjectItem.getInt("ORDEN"));
                    documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(jsonObjectItem.getInt("ID_DOCUMENTO_MOVIMIENTO"));
                    documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_ROL_USUARIO"));
                    documentos_cobra_movBE.setNOMBRE_ROL(jsonObjectItem.getString("NOMBRE_ROL"));
                    documentos_cobra_movBE.setNOMBRE_USUARIO (jsonObjectItem.getString("NOMBRE_USUARIO"));
                    lst.add(documentos_cobra_movBE);
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

    public JSONObject getFlujo3(String newURL) {
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setID_AUDITORIA(jsonObjectItem.getInt("ID_AUDITORIA"));
                    documentos_cobra_movBE.setTIPO_DOC(jsonObjectItem.getString("TIPO_DOC"));
                    documentos_cobra_movBE.setID_DOC(jsonObjectItem.getInt("ID_DOC"));
                    documentos_cobra_movBE.setSERIE_DOC(jsonObjectItem.getString("SERIE_DOC"));
                    documentos_cobra_movBE.setNRO_DOC(jsonObjectItem.getString("NRO_DOC"));
                    documentos_cobra_movBE.setACCION(jsonObjectItem.getString("ACCION"));
                    documentos_cobra_movBE.setFECHA_ACCION(jsonObjectItem.getString("FECHA_ACCION"));
                    documentos_cobra_movBE.setUSUARIO_REGISTRO (jsonObjectItem.getString("USUARIO_REGISTRO").trim().replace("null",""));
                    documentos_cobra_movBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    documentos_cobra_movBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION").trim().replace("null",""));
                    documentos_cobra_movBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION").trim().replace("null",""));
                    documentos_cobra_movBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    documentos_cobra_movBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    documentos_cobra_movBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_movBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_movBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    documentos_cobra_movBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    documentos_cobra_movBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION").trim().replace("null",""));
                    documentos_cobra_movBE.setNOMBREACCION(jsonObjectItem.getString("NOMBREACCION").trim().replace("null",""));
                    lst.add(documentos_cobra_movBE);
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

    public JSONObject getAPlanillaRest(String newURL) {
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setORDEN(jsonObjectItem.getInt("ORDEN"));
                    documentos_cobra_movBE.setID_COBRANZA(jsonObjectItem.getString("ID_COBRANZA"));
                    documentos_cobra_movBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    documentos_cobra_movBE.setN_RECIBO(jsonObjectItem.getString("N_RECIBO"));
                    documentos_cobra_movBE.setN_SERIE_RECIBO(jsonObjectItem.getString("N_SERIE_RECIBO"));
                    documentos_cobra_movBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    documentos_cobra_movBE.setID_COBRADOR(jsonObjectItem.getString("ID_COBRADOR"));
                    documentos_cobra_movBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    documentos_cobra_movBE.setM_COBRANZA(jsonObjectItem.getString("M_COBRANZA"));
                    documentos_cobra_movBE.setM_COBRANZA_D(jsonObjectItem.getString("M_COBRANZA_D"));
                    documentos_cobra_movBE.setSALDO(jsonObjectItem.getString("SALDO"));
                    documentos_cobra_movBE.setNUMCHEQ(jsonObjectItem.getString("NUMCHEQ"));
                    documentos_cobra_movBE.setFECCHEQ(jsonObjectItem.getString("FECCHEQ"));
                    documentos_cobra_movBE.setID_BANCO(jsonObjectItem.getString("ID_BANCO"));
                    documentos_cobra_movBE.setCTACORRIENTE_BANCO(jsonObjectItem.getString("CTACORRIENTE_BANCO"));
                    documentos_cobra_movBE.setNRO_OPERACION(jsonObjectItem.getString("NRO_OPERACION"));
                    documentos_cobra_movBE.setFECHA_DEPOSITO(jsonObjectItem.getString("FECHA_DEPOSITO"));
                    documentos_cobra_movBE.setCOMENTARIO(jsonObjectItem.getString("COMENTARIO"));
                    documentos_cobra_movBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_movBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_movBE.setESTADO(jsonObjectItem.getString("ESTADO"));
                    documentos_cobra_movBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    documentos_cobra_movBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    documentos_cobra_movBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    documentos_cobra_movBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    documentos_cobra_movBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    documentos_cobra_movBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    documentos_cobra_movBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    documentos_cobra_movBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    documentos_cobra_movBE.setITEM(jsonObjectItem.getString("ITEM"));
                    documentos_cobra_movBE.setESTADO_CONCILIADO(jsonObjectItem.getString("ESTADO_CONCILIADO"));
                    documentos_cobra_movBE.setN_PLANILLA(jsonObjectItem.getString("N_PLANILLA"));
                    documentos_cobra_movBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
                    documentos_cobra_movBE.setC_PACKING(jsonObjectItem.getString("C_PACKING"));
                    documentos_cobra_movBE.setID_MOV_BANCO(jsonObjectItem.getString("ID_MOV_BANCO"));
                    documentos_cobra_movBE.setESTADO_PROCESO(jsonObjectItem.getString("ESTADO_PROCESO"));
                    documentos_cobra_movBE.setT_CAMBIO_TIENDA(jsonObjectItem.getString("T_CAMBIO_TIENDA"));
                    documentos_cobra_movBE.setN_TARJETA(jsonObjectItem.getString("N_TARJETA"));
                    documentos_cobra_movBE.setID_MOV_BANCO_ABONO(jsonObjectItem.getString("ID_MOV_BANCO_ABONO"));
                    documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(jsonObjectItem.getInt("ID_DOCUMENTO_MOVIMIENTO"));
                    documentos_cobra_movBE.setNOMBRECLIENTE(jsonObjectItem.getString("NOMBRECLIENTE"));
                    documentos_cobra_movBE.setNOMBRECUENTACORRIENTE(jsonObjectItem.getString("NOMBRECUENTACORRIENTE"));
                    documentos_cobra_movBE.setNOMBREBANCO(jsonObjectItem.getString("NOMBREBANCO"));
                    documentos_cobra_movBE.setNOMBREFORMAPAGO(jsonObjectItem.getString("NOMBREFORMAPAGO"));
                    documentos_cobra_movBE.setNOMBREESTADO(jsonObjectItem.getString("NOMBREESTADO"));
                    documentos_cobra_movBE.setNOMBRECOBRADOR(jsonObjectItem.getString("NOMBRECOBRADOR"));
                    lst.add(documentos_cobra_movBE);
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

    public JSONObject getSincronizar(String newURL) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //Eliminamos los registros
            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_MOV", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                String SQL="INSERT OR REPLACE INTO S_CCM_DOCUMENTOS_COBRA_MOV(" +
                        "ID_DOCUMENTO_MOVIMIENTO,SERIE_PLANILLA,N_PLANILLA,ID_USUARIO_REGISTRO,ID_ROL_USUARIO_REGISTRO,FECHA_RECEPCION ," +
                        "ID_USUARIO_DERIVAR,ID_ROL_USUARIO_DERIVAR,FECHA_DERIVAR,FECHA_MOVIMIENTO,FECHA_ACCION,ESTADO_MOVIMIENTO," +
                        "ID_EMPRESA,ID_LOCAL,GUARDADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_DOCUMENTO_MOVIMIENTO"));
                    stmt.bindString(2,jsonObjectItem.getString("SERIE_PLANILLA"));
                    stmt.bindString(3,jsonObjectItem.getString("N_PLANILLA"));
                    stmt.bindString(4,jsonObjectItem.getString("ID_USUARIO_REGISTRO"));
                    stmt.bindString(5,jsonObjectItem.getString("ID_ROL_USUARIO_REGISTRO"));
                    stmt.bindString(6,jsonObjectItem.getString("FECHA_RECEPCION"));
                    stmt.bindString(7,jsonObjectItem.getString("ID_USUARIO_DERIVAR"));
                    stmt.bindString(8,jsonObjectItem.getString("ID_ROL_USUARIO_DERIVAR"));
                    stmt.bindString(9,jsonObjectItem.getString("FECHA_DERIVAR"));
                    stmt.bindString(10,jsonObjectItem.getString("FECHA_MOVIMIENTO"));
                    stmt.bindString(11,jsonObjectItem.getString("FECHA_ACCION"));
                    stmt.bindString(12,jsonObjectItem.getString("ESTADO_MOVIMIENTO"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(14,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(15,"1");
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

    public JSONObject insert(String newURL,Documentos_Cobra_MovBE documentos_cobra_movBE){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("ID_DOCUMENTO_MOVIMIENTO", documentos_cobra_movBE.getID_DOCUMENTO_MOVIMIENTO());
            jsonObject.accumulate("SERIE_PLANILLA", documentos_cobra_movBE.getSERIE_PLANILLA());
            jsonObject.accumulate("N_PLANILLA", documentos_cobra_movBE.getN_PLANILLA());
            jsonObject.accumulate("ID_USUARIO_REGISTRO", documentos_cobra_movBE.getID_USUARIO_REGISTRO());
            jsonObject.accumulate("ID_ROL_USUARIO_REGISTRO", documentos_cobra_movBE.getID_ROL_USUARIO_REGISTRO());
            jsonObject.accumulate("FECHA_RECEPCION", documentos_cobra_movBE.getFECHA_RECEPCION());
            jsonObject.accumulate("ID_USUARIO_DERIVAR", documentos_cobra_movBE.getID_USUARIO_DERIVAR());
            jsonObject.accumulate("ID_ROL_USUARIO_DERIVAR", documentos_cobra_movBE.getID_ROL_USUARIO_DERIVAR());
            jsonObject.accumulate("FECHA_DERIVAR", documentos_cobra_movBE.getFECHA_DERIVAR());
            jsonObject.accumulate("FECHA_MOVIMIENTO", documentos_cobra_movBE.getFECHA_MOVIMIENTO());
            jsonObject.accumulate("FECHA_ACCION", documentos_cobra_movBE.getFECHA_ACCION());
            jsonObject.accumulate("ESTADO_MOVIMIENTO", documentos_cobra_movBE.getESTADO_MOVIMIENTO());
            jsonObject.accumulate("ID_EMPRESA", documentos_cobra_movBE.getID_EMPRESA());
            jsonObject.accumulate("ID_LOCAL", documentos_cobra_movBE.getID_LOCAL());
            String aux = new RestClientLibrary().post(newURL,jsonObject);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                Documentos_Cobra_MovBE documentos_cobra_movBE2;
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_movBE2 = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE2.setMSG(jsonObjectItem.getString("MSG"));
                    lst.add(documentos_cobra_movBE2);
                }
            }

            jsonObjectResult.accumulate("status", jsonObjectRest.getInt("status"));
            jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));
        }catch(Exception e){
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

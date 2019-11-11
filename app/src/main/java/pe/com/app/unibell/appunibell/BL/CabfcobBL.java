package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class CabfcobBL {
    public ArrayList<CabfcobBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        CabfcobBE cabfcobBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<CabfcobBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
            cabfcobBE = new CabfcobBE();
            cabfcobBE.setTIPDOC(jsonObjectItem.getString("TIPDOC"));
            cabfcobBE.setSERIE_NUM(jsonObjectItem.getString("SERIE_NUM"));
            cabfcobBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
            cabfcobBE.setFECHA(jsonObjectItem.getString("FECHA"));
            cabfcobBE.setANO(jsonObjectItem.getInt("ANO"));
            cabfcobBE.setMES(jsonObjectItem.getInt("MES"));
            cabfcobBE.setLIBRO(jsonObjectItem.getString("LIBRO"));
            cabfcobBE.setVOUCHER(jsonObjectItem.getInt("VOUCHER"));
            cabfcobBE.setITEM(jsonObjectItem.getInt("ITEM"));
            cabfcobBE.setTIPO_REFERENCIA(jsonObjectItem.getString("TIPO_REFERENCIA"));
            cabfcobBE.setSERIE_REF(jsonObjectItem.getString("SERIE_REF"));
            cabfcobBE.setNRO_REFERENCIA(jsonObjectItem.getString("NRO_REFERENCIA"));
            cabfcobBE.setCONCEPTO(jsonObjectItem.getString("CONCEPTO"));
            cabfcobBE.setFORMA_PAGO(jsonObjectItem.getString("FORMA_PAGO"));
            cabfcobBE.setBANCO(jsonObjectItem.getString("BANCO"));
            cabfcobBE.setCHEQUE(jsonObjectItem.getString("CHEQUE"));
            cabfcobBE.setSISTEMA_ORIGEN(jsonObjectItem.getString("SISTEMA_ORIGEN"));
            cabfcobBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
            cabfcobBE.setIMPORTE(jsonObjectItem.getDouble("IMPORTE"));
            cabfcobBE.setIMPORTE_X(jsonObjectItem.getDouble("IMPORTE_X"));
            cabfcobBE.setTIPO_CAMBIO(jsonObjectItem.getDouble("TIPO_CAMBIO"));
            cabfcobBE.setESTADO(jsonObjectItem.getString("ESTADO"));
            cabfcobBE.setC_ANO(jsonObjectItem.getInt("C_ANO"));
            cabfcobBE.setC_MES(jsonObjectItem.getInt("C_MES"));
            cabfcobBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
            cabfcobBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
            cabfcobBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
            cabfcobBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
            cabfcobBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
            cabfcobBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
            cabfcobBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
            cabfcobBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
            cabfcobBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
            cabfcobBE.setN_SERIE_RECIBO_COBRA(jsonObjectItem.getString("N_SERIE_RECIBO_COBRA"));
            cabfcobBE.setN_RECIBO_COBRA(jsonObjectItem.getInt("N_RECIBO_COBRA"));
            cabfcobBE.setSERIE_PLANILLA(jsonObjectItem.getString("SERIE_PLANILLA"));
            cabfcobBE.setNRO_PLANILLA(jsonObjectItem.getInt("NRO_PLANILLA"));
            cabfcobBE.setC_LIQUIDADOR(jsonObjectItem.getString("C_LIQUIDADOR"));
            cabfcobBE.setTIPO_LIQUIDADOR(jsonObjectItem.getString("TIPO_LIQUIDADOR"));
            cabfcobBE.setFECHA_COBRANZA(jsonObjectItem.getString("FECHA_COBRANZA"));
            lst.add(cabfcobBE);
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
            lst=new ArrayList<CabfcobBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("CABFCOB", null, null);

                String SQL="INSERT OR REPLACE INTO CABFCOB(TIPDOC, SERIE_NUM, NUMERO, FECHA, IMPORTE, COBRADOR, N_SERIE_RECIBO_COBRA, N_RECIBO_COBRA)" +
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("TIPDOC"));
                    stmt.bindString(2,jsonObjectItem.getString("SERIE_NUM"));
                    stmt.bindString(3,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(4,jsonObjectItem.getString("FECHA"));
                    stmt.bindString(5,jsonObjectItem.getString("IMPORTE"));
                    stmt.bindString(6,jsonObjectItem.getString("COBRADOR"));
                    stmt.bindString(7,jsonObjectItem.getString("N_SERIE_RECIBO_COBRA"));
                    stmt.bindString(8,jsonObjectItem.getString("N_RECIBO_COBRA"));
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

    public JSONObject getDocumentRest(String newURL) {
        CabfcobBE cabfcobBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<CabfcobBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    cabfcobBE = new CabfcobBE();
                    cabfcobBE.setTIPDOC(jsonObjectItem.getString("TIPDOC"));
                    cabfcobBE.setSERIE_NUM(jsonObjectItem.getString("SERIE_NUM"));
                    cabfcobBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    cabfcobBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    cabfcobBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    cabfcobBE.setN_SERIE_RECIBO_COBRA(jsonObjectItem.getString("N_SERIE_RECIBO_COBRA"));
                    cabfcobBE.setN_RECIBO_COBRA(jsonObjectItem.optInt("N_RECIBO_COBRA",0));
                    cabfcobBE.setIMPORTE(jsonObjectItem.getDouble("IMPORTE"));
                    cabfcobBE.setCONCEPTO(jsonObjectItem.getString("CONCEPTO"));
                    cabfcobBE.setMONEDA(jsonObjectItem.getString("MONEDA"));


                    lst.add(cabfcobBE);
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




}

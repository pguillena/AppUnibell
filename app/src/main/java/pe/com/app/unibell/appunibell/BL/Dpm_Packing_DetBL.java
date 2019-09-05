package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Dpm_Packing_DetBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Dpm_Packing_DetBL {
    public ArrayList<Dpm_Packing_DetBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Dpm_Packing_DetBE dpm_packing_detBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Dpm_Packing_DetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    dpm_packing_detBE = new Dpm_Packing_DetBE();
                    dpm_packing_detBE.setC_PACKING(jsonObjectItem.getInt("C_PACKING"));
                    dpm_packing_detBE.setF_PACKING(jsonObjectItem.getString("F_PACKING"));
                    dpm_packing_detBE.setC_EMPRESA(jsonObjectItem.getString("C_EMPRESA"));
                    dpm_packing_detBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                    dpm_packing_detBE.setSERIE(jsonObjectItem.getString("SERIE"));
                    dpm_packing_detBE.setNUMERO(jsonObjectItem.getInt("NUMERO"));
                    dpm_packing_detBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    dpm_packing_detBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    dpm_packing_detBE.setTOTAL_DOC(jsonObjectItem.getString("TOTAL_DOC"));
                    dpm_packing_detBE.setFLG_COND(jsonObjectItem.getString("FLG_COND"));
                    dpm_packing_detBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    dpm_packing_detBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    dpm_packing_detBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    dpm_packing_detBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    dpm_packing_detBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    dpm_packing_detBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
                    dpm_packing_detBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
                    dpm_packing_detBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
                    dpm_packing_detBE.setC_ESTADO(jsonObjectItem.getString("C_ESTADO"));
                    dpm_packing_detBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
                    dpm_packing_detBE.setANULADO(jsonObjectItem.getString("ANULADO"));
                    dpm_packing_detBE.setBANDEJAS(jsonObjectItem.getInt("BANDEJAS"));
                    dpm_packing_detBE.setCAJAS(jsonObjectItem.getInt("CAJAS"));
                    dpm_packing_detBE.setBOLSAS(jsonObjectItem.getInt("BOLSAS"));
                    dpm_packing_detBE.setUNISUELTAS(jsonObjectItem.getInt("UNISUELTAS"));
                    dpm_packing_detBE.setTOTPESO(jsonObjectItem.getInt("TOTPESO"));
                    dpm_packing_detBE.setFLG_ENTREGADO(jsonObjectItem.getString("FLG_ENTREGADO"));
                    dpm_packing_detBE.setC_MOTIVO(jsonObjectItem.getString("C_MOTIVO"));
                    dpm_packing_detBE.setN_PLANILLA_COB(jsonObjectItem.getString("N_PLANILLA_COB"));
                    dpm_packing_detBE.setBULTOS(jsonObjectItem.getInt("BULTOS"));
                    dpm_packing_detBE.setN_VOUCHER_TES(jsonObjectItem.getInt("N_VOUCHER_TES"));
                    dpm_packing_detBE.setF_ANO_TES(jsonObjectItem.getInt("F_ANO_TES"));
                    dpm_packing_detBE.setF_MES_TES(jsonObjectItem.getInt("F_MES_TES"));
                    dpm_packing_detBE.setC_TIPO_TES(jsonObjectItem.getString("C_TIPO_TES"));
                    dpm_packing_detBE.setN_ITEM_TES(jsonObjectItem.getInt("N_ITEM_TES"));
                    dpm_packing_detBE.setCOND_PAGO(jsonObjectItem.getString("COND_PAGO"));
                    dpm_packing_detBE.setM_COBRANZA(jsonObjectItem.getInt("M_COBRANZA"));
                    dpm_packing_detBE.setM_COBRANZA_D(jsonObjectItem.getInt("M_COBRANZA_D"));
                    dpm_packing_detBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    dpm_packing_detBE.setNUMCHEQ(jsonObjectItem.getString("NUMCHEQ"));
                    dpm_packing_detBE.setFECCHEQ(jsonObjectItem.getString("FECCHEQ"));
                    dpm_packing_detBE.setBANCO(jsonObjectItem.getString("BANCO"));
                    dpm_packing_detBE.setN_RECIBO(jsonObjectItem.getInt("N_RECIBO"));
                    dpm_packing_detBE.setN_SERIE_RECIBO(jsonObjectItem.getInt("N_SERIE_RECIBO"));
                    dpm_packing_detBE.setORD_VISITA(jsonObjectItem.getInt("ORD_VISITA"));
                    dpm_packing_detBE.setN_SEQUENCIA(jsonObjectItem.getInt("N_SEQUENCIA"));
                    dpm_packing_detBE.setM_COBRANZA_ANT(jsonObjectItem.getInt("M_COBRANZA_ANT"));
                    dpm_packing_detBE.setNRO_OPERACION(jsonObjectItem.getString("NRO_OPERACION"));
                    dpm_packing_detBE.setFECHA_DEPOSITO(jsonObjectItem.getString("FECHA_DEPOSITO"));
                    dpm_packing_detBE.setBCO_CTACTE(jsonObjectItem.getString("BCO_CTACTE"));
                    dpm_packing_detBE.setIC_MONTO(jsonObjectItem.getString("IC_MONTO"));
                    dpm_packing_detBE.setN_TARJETA(jsonObjectItem.getString("N_TARJETA"));
                    dpm_packing_detBE.setID_MOVIMIENTO_POS(jsonObjectItem.getInt("ID_MOVIMIENTO_POS"));
                    dpm_packing_detBE.setSERIE_PLANILLA_COB(jsonObjectItem.getString("SERIE_PLANILLA_COB"));                
                    lst.add(dpm_packing_detBE);
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
            lst=new ArrayList<Dpm_Packing_DetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("DPM_PACKING_DET", null, null);

                String SQL="INSERT OR REPLACE INTO DPM_PACKING_DET(C_PACKING, F_PACKING, C_EMPRESA, TIPODOC, SERIE, NUMERO, COD_CLIENTE, N_PLANILLA_COB, COND_PAGO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("C_PACKING"));
                    stmt.bindString(2,jsonObjectItem.getString("F_PACKING"));
                    stmt.bindString(3,jsonObjectItem.getString("C_EMPRESA"));
                    stmt.bindString(4,jsonObjectItem.getString("TIPODOC"));
                    stmt.bindString(5,jsonObjectItem.getString("SERIE"));
                    stmt.bindString(6,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(7,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(8,jsonObjectItem.getString("N_PLANILLA_COB"));
                    stmt.bindString(9,jsonObjectItem.getString("COND_PAGO"));
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


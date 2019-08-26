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
            //Eliminamos los registros
            DataBaseHelper.myDataBase.delete("DPM_PACKING_DET", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{

                String SQL="INSERT OR REPLACE INTO DPM_PACKING_DET(" +
                        "C_PACKING,F_PACKING,C_EMPRESA,TIPODOC,SERIE,NUMERO," +
                        "COD_CLIENTE,MONEDA,TOTAL_DOC,FLG_COND,OBSERVACION,C_USUARIO," +
                        "C_PERFIL,C_CPU,FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD," +
                        "C_ESTADO,C_CPU_MOD,ANULADO,BANDEJAS,CAJAS,BOLSAS," +
                        "UNISUELTAS,TOTPESO,FLG_ENTREGADO,C_MOTIVO,N_PLANILLA_COB,BULTOS," +
                        "N_VOUCHER_TES,F_ANO_TES,F_MES_TES,C_TIPO_TES,N_ITEM_TES,COND_PAGO," +
                        "M_COBRANZA,M_COBRANZA_D,FPAGO,NUMCHEQ,FECCHEQ,BANCO," +
                        "N_RECIBO,N_SERIE_RECIBO,ORD_VISITA,N_SEQUENCIA,M_COBRANZA_ANT,NRO_OPERACION," +
                        "FECHA_DEPOSITO,BCO_CTACTE,IC_MONTO,N_TARJETA,ID_MOVIMIENTO_POS,SERIE_PLANILLA_COB)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
                    stmt.bindString(8,jsonObjectItem.getString("MONEDA"));
                    stmt.bindString(9,jsonObjectItem.getString("TOTAL_DOC"));
                    stmt.bindString(10,jsonObjectItem.getString("FLG_COND"));
                    stmt.bindString(11,jsonObjectItem.getString("OBSERVACION"));
                    stmt.bindString(12,jsonObjectItem.getString("C_USUARIO"));
                    stmt.bindString(13,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(14,jsonObjectItem.getString("C_CPU"));
                    stmt.bindString(15,jsonObjectItem.getString("FEC_REG"));
                    stmt.bindString(16,jsonObjectItem.getString("C_USUARIO_MOD"));
                    stmt.bindString(17,jsonObjectItem.getString("C_PERFIL_MOD"));
                    stmt.bindString(18,jsonObjectItem.getString("FEC_MOD"));
                    stmt.bindString(19,jsonObjectItem.getString("C_ESTADO"));
                    stmt.bindString(20,jsonObjectItem.getString("C_CPU_MOD"));
                    stmt.bindString(21,jsonObjectItem.getString("ANULADO"));
                    stmt.bindString(22,jsonObjectItem.getString("BANDEJAS"));
                    stmt.bindString(23,jsonObjectItem.getString("CAJAS"));
                    stmt.bindString(24,jsonObjectItem.getString("BOLSAS"));
                    stmt.bindString(25,jsonObjectItem.getString("UNISUELTAS"));
                    stmt.bindString(26,jsonObjectItem.getString("TOTPESO"));
                    stmt.bindString(27,jsonObjectItem.getString("FLG_ENTREGADO"));
                    stmt.bindString(28,jsonObjectItem.getString("C_MOTIVO"));
                    stmt.bindString(29,jsonObjectItem.getString("N_PLANILLA_COB"));
                    stmt.bindString(30,jsonObjectItem.getString("BULTOS"));
                    stmt.bindString(31,jsonObjectItem.getString("N_VOUCHER_TES"));
                    stmt.bindString(32,jsonObjectItem.getString("F_ANO_TES"));
                    stmt.bindString(33,jsonObjectItem.getString("F_MES_TES"));
                    stmt.bindString(34,jsonObjectItem.getString("C_TIPO_TES"));
                    stmt.bindString(35,jsonObjectItem.getString("N_ITEM_TES"));
                    stmt.bindString(36,jsonObjectItem.getString("COND_PAGO"));
                    stmt.bindString(3,jsonObjectItem.getString("M_COBRANZA"));
                    stmt.bindString(38,jsonObjectItem.getString("M_COBRANZA_D"));
                    stmt.bindString(39,jsonObjectItem.getString("FPAGO"));
                    stmt.bindString(40,jsonObjectItem.getString("NUMCHEQ"));
                    stmt.bindString(41,jsonObjectItem.getString("FECCHEQ"));
                    stmt.bindString(42,jsonObjectItem.getString("BANCO"));
                    stmt.bindString(43,jsonObjectItem.getString("N_RECIBO"));
                    stmt.bindString(44,jsonObjectItem.getString("N_SERIE_RECIBO"));
                    stmt.bindString(45,jsonObjectItem.getString("ORD_VISITA"));
                    stmt.bindString(46,jsonObjectItem.getString("N_SEQUENCIA"));
                    stmt.bindString(47,jsonObjectItem.getString("M_COBRANZA_ANT"));
                    stmt.bindString(48,jsonObjectItem.getString("NRO_OPERACION"));
                    stmt.bindString(49,jsonObjectItem.getString("FECHA_DEPOSITO"));
                    stmt.bindString(50,jsonObjectItem.getString("BCO_CTACTE"));
                    stmt.bindString(51,jsonObjectItem.getString("IC_MONTO"));
                    stmt.bindString(52,jsonObjectItem.getString("N_TARJETA"));
                    stmt.bindString(53,jsonObjectItem.getString("ID_MOVIMIENTO_POS"));
                    stmt.bindString(54,jsonObjectItem.getString("SERIE_PLANILLA_COB"));
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


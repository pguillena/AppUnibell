package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class FactCobBL {
    public ArrayList<FactCobBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        FactCobBE factCobBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<FactCobBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    factCobBE.setTIPDOC(jsonObjectItem.getString("TIPDOC"));
                    factCobBE.setSERIE_NUM(jsonObjectItem.getString("SERIE_NUM"));
                    factCobBE.setNUMERO(jsonObjectItem.getInt("NUMERO"));
                    factCobBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    factCobBE.setF_VENCTO(jsonObjectItem.getString("F_VENCTO"));
                    factCobBE.setF_ACEPTACION(jsonObjectItem.getString("F_ACEPTACION"));
                    factCobBE.setF_TRANSFE(jsonObjectItem.getString("F_TRANSFE"));
                    factCobBE.setANO(jsonObjectItem.getInt("ANO"));
                    factCobBE.setMES(jsonObjectItem.getInt("MES"));
                    factCobBE.setLIBRO(jsonObjectItem.getString("LIBRO"));
                    factCobBE.setVOUCHER(jsonObjectItem.getString("VOUCHER"));
                    factCobBE.setITEM(jsonObjectItem.getInt("ITEM"));
                    factCobBE.setTIPO_REFERENCIA(jsonObjectItem.getString("TIPO_REFERENCIA"));
                    factCobBE.setSERIE_REF(jsonObjectItem.getString("SERIE_REF"));
                    factCobBE.setNRO_REFERENCIA(jsonObjectItem.getString("NRO_REFERENCIA"));
                    factCobBE.setCONCEPTO(jsonObjectItem.getString("CONCEPTO"));
                    factCobBE.setSISTEMA_ORIGEN(jsonObjectItem.getString("SISTEMA_ORIGEN"));
                    factCobBE.setVENDED(jsonObjectItem.getString("VENDED"));
                    factCobBE.setBANCO(jsonObjectItem.getString("BANCO"));
                    factCobBE.setL_AGENCIA(jsonObjectItem.getString("L_AGENCIA"));
                    factCobBE.setL_REFBCO(jsonObjectItem.getString("L_REFBCO"));
                    factCobBE.setL_CONDLE(jsonObjectItem.getString("L_CONDLE"));
                    factCobBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    factCobBE.setIMPORTE(jsonObjectItem.getDouble("IMPORTE"));
                    factCobBE.setTCAM_IMP(jsonObjectItem.getDouble("TCAM_IMP"));
                    factCobBE.setSALDO(jsonObjectItem.getDouble("SALDO"));
                    factCobBE.setTCAM_SAL(jsonObjectItem.getInt("TCAM_SAL"));
                    factCobBE.setNUMERO_CANJE(jsonObjectItem.getString("NUMERO_CANJE"));
                    factCobBE.setESTADO(jsonObjectItem.getString("ESTADO"));
                    factCobBE.setCTACTBLE(jsonObjectItem.getString("CTACTBLE"));
                    factCobBE.setF_RECEPCION(jsonObjectItem.getString("F_RECEPCION"));
                    factCobBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    factCobBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    factCobBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    factCobBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    factCobBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
                    factCobBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
                    factCobBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
                    factCobBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
                    factCobBE.setN_SERIE_RECIBO_COBRA(jsonObjectItem.getString("N_SERIE_RECIBO_COBRA"));
                    factCobBE.setN_RECIBO_COBRA(jsonObjectItem.getInt("N_RECIBO_COBRA"));
                    factCobBE.setANO_PROVISION(jsonObjectItem.getInt("ANO_PROVISION"));
                    factCobBE.setMES_CSTGO(jsonObjectItem.getInt("MES_CSTGO"));
                    factCobBE.setANO_CSTGO(jsonObjectItem.getInt("ANO_CSTGO"));
                    factCobBE.setLIBRO_CSTGO(jsonObjectItem.getString("LIBRO_CSTGO"));
                    factCobBE.setVOUCHER_CSTGO(jsonObjectItem.getInt("VOUCHER_CSTGO"));
                    factCobBE.setDIAS(jsonObjectItem.getInt("DIAS"));
                    factCobBE.setCOBRANZA(jsonObjectItem.getDouble("COBRANZA"));
                    factCobBE.setVAMORTIZADO(jsonObjectItem.getDouble("VAMORTIZADO"));


                    lst.add(factCobBE);
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
            lst=new ArrayList<FactCobBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("FACTCOB", null, null);

                String SQL="INSERT OR REPLACE INTO FACTCOB( " +
                        "COD_CLIENTE,TIPDOC,SERIE_NUM,NUMERO,FECHA," +
                        "F_VENCTO,F_ACEPTACION,F_TRANSFE,ANO,MES," +
                        "LIBRO,VOUCHER,ITEM,TIPO_REFERENCIA,SERIE_REF," +
                        "NRO_REFERENCIA,CONCEPTO,SISTEMA_ORIGEN,VENDED,BANCO," +
                        "L_AGENCIA,L_REFBCO,L_CONDLE,MONEDA,IMPORTE," +
                        "TCAM_IMP,SALDO,TCAM_SAL,NUMERO_CANJE,ESTADO," +
                        "CTACTBLE,F_RECEPCION,C_USUARIO,C_PERFIL,C_CPU," +
                        "FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD,C_CPU_MOD," +
                        "N_SERIE_RECIBO_COBRA,N_RECIBO_COBRA,ANO_PROVISION,MES_CSTGO,ANO_CSTGO," +
                        "LIBRO_CSTGO,VOUCHER_CSTGO,DIAS,AGREGADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("TIPDOC"));
                    stmt.bindString(3,jsonObjectItem.getString("SERIE_NUM"));
                    stmt.bindString(4,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(5,jsonObjectItem.getString("FECHA"));
                    stmt.bindString(6,jsonObjectItem.getString("F_VENCTO"));
                    stmt.bindString(7,jsonObjectItem.getString("F_ACEPTACION"));
                    stmt.bindString(8,jsonObjectItem.getString("F_TRANSFE"));
                    stmt.bindString(9,jsonObjectItem.getString("ANO"));
                    stmt.bindString(10,jsonObjectItem.getString("MES"));
                    stmt.bindString(11,jsonObjectItem.getString("LIBRO"));
                    stmt.bindString(12,jsonObjectItem.getString("VOUCHER"));
                    stmt.bindString(13,jsonObjectItem.getString("ITEM"));
                    stmt.bindString(14,jsonObjectItem.getString("TIPO_REFERENCIA"));
                    stmt.bindString(15,jsonObjectItem.getString("SERIE_REF"));
                    stmt.bindString(16,jsonObjectItem.getString("NRO_REFERENCIA"));
                    stmt.bindString(17,jsonObjectItem.getString("CONCEPTO"));
                    stmt.bindString(18,jsonObjectItem.getString("SISTEMA_ORIGEN"));
                    stmt.bindString(19,jsonObjectItem.getString("VENDED"));
                    stmt.bindString(20,jsonObjectItem.getString("BANCO"));
                    stmt.bindString(21,jsonObjectItem.getString("L_AGENCIA"));
                    stmt.bindString(22,jsonObjectItem.getString("L_REFBCO"));
                    stmt.bindString(23,jsonObjectItem.getString("L_CONDLE"));
                    stmt.bindString(24,jsonObjectItem.getString("MONEDA"));
                    stmt.bindString(25,jsonObjectItem.getString("IMPORTE"));
                    stmt.bindString(26,jsonObjectItem.getString("TCAM_IMP"));
                    stmt.bindString(27,jsonObjectItem.getString("SALDO"));
                    stmt.bindString(28,jsonObjectItem.getString("TCAM_SAL"));
                    stmt.bindString(29,jsonObjectItem.getString("NUMERO_CANJE"));
                    stmt.bindString(30,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(31,jsonObjectItem.getString("CTACTBLE"));
                    stmt.bindString(32,jsonObjectItem.getString("F_RECEPCION"));
                    stmt.bindString(33,jsonObjectItem.getString("C_USUARIO"));
                    stmt.bindString(34,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(35,jsonObjectItem.getString("C_CPU"));
                    stmt.bindString(36,jsonObjectItem.getString("FEC_REG"));
                    stmt.bindString(37,jsonObjectItem.getString("C_USUARIO_MOD"));
                    stmt.bindString(38,jsonObjectItem.getString("C_PERFIL_MOD"));
                    stmt.bindString(39,jsonObjectItem.getString("FEC_MOD"));
                    stmt.bindString(40,jsonObjectItem.getString("C_CPU_MOD"));
                    stmt.bindString(41,jsonObjectItem.getString("N_SERIE_RECIBO_COBRA"));
                    stmt.bindString(42,jsonObjectItem.getString("N_RECIBO_COBRA"));
                    stmt.bindString(43,jsonObjectItem.getString("ANO_PROVISION"));
                    stmt.bindString(44,jsonObjectItem.getString("MES_CSTGO"));
                    stmt.bindString(45,jsonObjectItem.getString("ANO_CSTGO"));
                    stmt.bindString(46,jsonObjectItem.getString("LIBRO_CSTGO"));
                    stmt.bindString(47,jsonObjectItem.getString("VOUCHER_CSTGO"));
                    stmt.bindString(48,jsonObjectItem.getString("DIAS"));
                    stmt.bindString(49,"0");

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

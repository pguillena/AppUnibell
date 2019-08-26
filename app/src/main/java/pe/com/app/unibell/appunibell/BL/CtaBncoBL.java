package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class CtaBncoBL {
    public ArrayList<CtaBncoBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        CtaBncoBE ctaBncoBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<CtaBncoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    ctaBncoBE = new CtaBncoBE();
                    ctaBncoBE.setCODIGO(jsonObjectItem.getString("CODIGO"));
                    ctaBncoBE.setDESCRIPCION(jsonObjectItem.getString("DESCRIPCION"));
                    ctaBncoBE.setCUENTA(jsonObjectItem.getString("CUENTA"));
                    ctaBncoBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    ctaBncoBE.setBANCO(jsonObjectItem.getString("BANCO"));
                    ctaBncoBE.setTIPO_CUENTA(jsonObjectItem.getString("TIPO_CUENTA"));
                    ctaBncoBE.setIND(jsonObjectItem.getString("IND"));
                    ctaBncoBE.setSECTORISTA(jsonObjectItem.getString("SECTORISTA"));
                    ctaBncoBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    ctaBncoBE.setNUMCHEI(jsonObjectItem.getString("NUMCHEI"));
                    ctaBncoBE.setNUMCHEF(jsonObjectItem.getString("NUMCHEF"));
                    ctaBncoBE.setVOUCHER(jsonObjectItem.getInt("VOUCHER"));
                    ctaBncoBE.setN_CTA_CORRIENTE(jsonObjectItem.getString("N_CTA_CORRIENTE"));
                    ctaBncoBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    ctaBncoBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    ctaBncoBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));

                    lst.add(ctaBncoBE);
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
            lst=new ArrayList<CtaBncoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //Eliminamos los registros
            DataBaseHelper.myDataBase.delete("CTABNCO", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{

                String SQL="INSERT OR REPLACE INTO CTABNCO(" +
                        "CODIGO,DESCRIPCION,CUENTA,MONEDA,BANCO," +
                        "TIPO_CUENTA,IND,SECTORISTA,TELEFONO,NUMCHEI," +
                        "NUMCHEF,VOUCHER,N_CTA_CORRIENTE,C_SUC_EMP,ID_LOCAL,ID_EMPRESA)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("CODIGO"));
                    stmt.bindString(2,jsonObjectItem.getString("DESCRIPCION"));
                    stmt.bindString(3,jsonObjectItem.getString("CUENTA"));
                    stmt.bindString(4,jsonObjectItem.getString("MONEDA"));
                    stmt.bindString(5,jsonObjectItem.getString("BANCO"));
                    stmt.bindString(6,jsonObjectItem.getString("TIPO_CUENTA"));
                    stmt.bindString(7,jsonObjectItem.getString("IND"));
                    stmt.bindString(8,jsonObjectItem.getString("SECTORISTA"));
                    stmt.bindString(9,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(10,jsonObjectItem.getString("NUMCHEI"));
                    stmt.bindString(11,jsonObjectItem.getString("NUMCHEF"));
                    stmt.bindString(12,jsonObjectItem.getString("VOUCHER"));
                    stmt.bindString(13,jsonObjectItem.getString("N_CTA_CORRIENTE"));
                    stmt.bindString(14,jsonObjectItem.getString("C_SUC_EMP"));
                    stmt.bindString(15,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(16,jsonObjectItem.getString("ID_EMPRESA"));

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

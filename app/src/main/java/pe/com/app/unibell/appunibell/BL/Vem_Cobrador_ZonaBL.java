package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.Vem_Cobrador_ZonaBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Vem_Cobrador_ZonaBL {
    public ArrayList<Vem_Cobrador_ZonaBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Vem_Cobrador_ZonaBE vem_cobrador_zonaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Vem_Cobrador_ZonaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    vem_cobrador_zonaBE = new Vem_Cobrador_ZonaBE();

                    vem_cobrador_zonaBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    vem_cobrador_zonaBE.setCOD_VENDE(jsonObjectItem.getString("COD_VENDE"));
                    vem_cobrador_zonaBE.setDIA_VISITA(jsonObjectItem.getString("DIA_VISITA"));
                    vem_cobrador_zonaBE.setUBIGEO(jsonObjectItem.getString("UBIGEO"));
                    vem_cobrador_zonaBE.setZONA(jsonObjectItem.getString("ZONA"));
                    vem_cobrador_zonaBE.setC_CANAL(jsonObjectItem.getString("C_CANAL"));
                    vem_cobrador_zonaBE.setC_SUBCANAL(jsonObjectItem.getString("C_SUBCANAL"));
                    lst.add(vem_cobrador_zonaBE);
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
            lst=new ArrayList<Vem_Cobrador_ZonaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
               //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("VEM_COBRADOR_ZONA", null, null);

                String SQL="INSERT OR REPLACE INTO VEM_COBRADOR_ZONA(" +
                        "COBRADOR,COD_VENDE,DIA_VISITA,UBIGEO,ZONA,C_CANAL," +
                        "C_SUBCANAL)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                     stmt.bindString(1,jsonObjectItem.getString("COBRADOR"));
                     stmt.bindString(2,jsonObjectItem.getString("COD_VENDE"));
                     stmt.bindString(3,jsonObjectItem.getString("DIA_VISITA"));
                     stmt.bindString(4,jsonObjectItem.getString("UBIGEO"));
                     stmt.bindString(5,jsonObjectItem.getString("ZONA"));
                     stmt.bindString(6,jsonObjectItem.getString("C_CANAL"));
                     stmt.bindString(7,jsonObjectItem.getString("C_SUBCANAL"));
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


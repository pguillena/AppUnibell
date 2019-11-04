package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Cliente_VendedorBE;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Cliente_VendedorBL {
    public ArrayList<Cliente_VendedorBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Cliente_VendedorBE cliente_vendedorBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Cliente_VendedorBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    cliente_vendedorBE = new Cliente_VendedorBE();
                    cliente_vendedorBE.setC_CLIENTE(jsonObjectItem.getString("C_CLIENTE"));
                    cliente_vendedorBE.setC_VENDEDOR(jsonObjectItem.getString("C_VENDEDOR"));
                    cliente_vendedorBE.setC_CANAL(jsonObjectItem.getString("C_CANAL"));
                    cliente_vendedorBE.setC_DIA_VISITA(jsonObjectItem.getInt("C_DIA_VISITA"));
                    cliente_vendedorBE.setN_ORD_VISITA(jsonObjectItem.getInt("N_ORD_VISITA"));
                    cliente_vendedorBE.setC_FRC_VISITA(jsonObjectItem.getString("C_FRC_VISITA"));

                    lst.add(cliente_vendedorBE);
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
            lst=new ArrayList<Cliente_VendedorBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("VEM_CLIENTE_VENDEDOR", null, null);

                String SQL="INSERT OR REPLACE INTO VEM_CLIENTE_VENDEDOR(C_CLIENTE, C_VENDEDOR, C_CANAL, C_DIA_VISITA, N_ORD_VISITA, C_FRC_VISITA)" +
                        "VALUES " +
                        "(?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("C_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("C_VENDEDOR"));
                    stmt.bindString(3,jsonObjectItem.getString("C_CANAL"));
                    stmt.bindString(4,jsonObjectItem.getString("C_DIA_VISITA"));
                    stmt.bindString(5,jsonObjectItem.getString("N_ORD_VISITA"));
                    stmt.bindString(6,jsonObjectItem.getString("C_FRC_VISITA"));

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

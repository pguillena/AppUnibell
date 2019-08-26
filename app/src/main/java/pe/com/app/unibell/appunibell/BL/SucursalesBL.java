package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.SucursalesBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class SucursalesBL {
    public ArrayList<SucursalesBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        SucursalesBE sucursalesBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<SucursalesBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    sucursalesBE = new SucursalesBE();
                    sucursalesBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    sucursalesBE.setNRO_SUCUR(jsonObjectItem.getString("NRO_SUCUR"));
                    sucursalesBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                    sucursalesBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    sucursalesBE.setRESPONSABLE(jsonObjectItem.getString("RESPONSABLE"));
                    sucursalesBE.setDISTRITO(jsonObjectItem.getString("DISTRITO"));
                    sucursalesBE.setCIUDAD(jsonObjectItem.getString("CIUDAD"));
                    sucursalesBE.setALM_CONS(jsonObjectItem.getString("ALM_CONS"));
                    sucursalesBE.setVENDEDOR(jsonObjectItem.getString("ID_PERSONA"));
                    sucursalesBE.setNOMBRE_SUCURSAL(jsonObjectItem.getString("NOMBRE_SUCURSAL"));
                    sucursalesBE.setCOD_UBC(jsonObjectItem.getString("COD_UBC"));
                    sucursalesBE.setORD_REPARTO(jsonObjectItem.getInt("ORD_REPARTO"));
                    sucursalesBE.setZONA(jsonObjectItem.getString("ZONA"));
                    sucursalesBE.setESTADO(jsonObjectItem.getString("ESTADO"));                   
                    lst.add(sucursalesBE);
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

            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //Eliminamos los registros
            DataBaseHelper.myDataBase.delete("SUCURSALES", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{

                String SQL="INSERT OR REPLACE INTO SUCURSALES(" +
                        " COD_CLIENTE ,NRO_SUCUR,DIRECCION,TELEFONO,RESPONSABLE," +
                        " DISTRITO,CIUDAD,ALM_CONS,NOMBRE_SUCURSAL,COD_UBC," +
                        " ORD_REPARTO,ZONA,ESTADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("NRO_SUCUR"));
                    stmt.bindString(3,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(4,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(5,jsonObjectItem.getString("RESPONSABLE"));
                    stmt.bindString(6,jsonObjectItem.getString("DISTRITO"));
                    stmt.bindString(7,jsonObjectItem.getString("CIUDAD"));
                    stmt.bindString(8,jsonObjectItem.getString("ALM_CONS"));
                    stmt.bindString(9,jsonObjectItem.getString("NOMBRE_SUCURSAL"));
                    stmt.bindString(10,jsonObjectItem.getString("COD_UBC"));
                    stmt.bindString(11,jsonObjectItem.getString("ORD_REPARTO"));
                    stmt.bindString(12,jsonObjectItem.getString("ZONA"));
                    stmt.bindString(13,jsonObjectItem.getString("ESTADO"));
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

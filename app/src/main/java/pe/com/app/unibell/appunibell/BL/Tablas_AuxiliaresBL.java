package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.Tablas_AuxiliaresBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Tablas_AuxiliaresBL {
    public ArrayList<Tablas_AuxiliaresBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Tablas_AuxiliaresBE tablas_auxiliaresBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Tablas_AuxiliaresBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    tablas_auxiliaresBE = new Tablas_AuxiliaresBE();
                    tablas_auxiliaresBE.setTIPO(jsonObjectItem.getInt("TIPO"));
                    tablas_auxiliaresBE.setCODIGO(jsonObjectItem.getString("CODIGO"));
                    tablas_auxiliaresBE.setDESCRIPCION(jsonObjectItem.getString("DESCRIPCION"));
                    tablas_auxiliaresBE.setABREVIADA(jsonObjectItem.getString("ABREVIADA"));
                    tablas_auxiliaresBE.setVALOR1(jsonObjectItem.getInt("VALOR1"));
                    tablas_auxiliaresBE.setVALOR2(jsonObjectItem.getInt("VALOR2"));
                    tablas_auxiliaresBE.setVALOR3(jsonObjectItem.getInt("VALOR3"));
                    tablas_auxiliaresBE.setINDICADOR1(jsonObjectItem.getString("INDICADOR1"));
                    tablas_auxiliaresBE.setINDICADOR2(jsonObjectItem.getString("INDICADOR2"));
                    tablas_auxiliaresBE.setINDICADOR3(jsonObjectItem.getString("INDICADOR3"));
                    tablas_auxiliaresBE.setINDICADOR4(jsonObjectItem.getString("INDICADOR4"));
                    tablas_auxiliaresBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    lst.add(tablas_auxiliaresBE);
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

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("TABLAS_AUXILIARES", null, null);

                String SQL="INSERT OR REPLACE INTO TABLAS_AUXILIARES(" +
                        "TIPO,CODIGO,DESCRIPCION,ABREVIADA,VALOR1," +
                        "VALOR2,VALOR3,INDICADOR1,INDICADOR2,INDICADOR3," +
                        "INDICADOR4,ID_EMPRESA)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("TIPO"));
                    stmt.bindString(2,jsonObjectItem.getString("CODIGO"));
                    stmt.bindString(3,jsonObjectItem.getString("DESCRIPCION"));
                    stmt.bindString(4,jsonObjectItem.getString("ABREVIADA"));
                    stmt.bindString(5,jsonObjectItem.getString("VALOR1").toString().replace("null",""));
                    stmt.bindString(6,jsonObjectItem.getString("VALOR2").toString().replace("null",""));
                    stmt.bindString(7,jsonObjectItem.getString("VALOR3").toString().replace("null",""));
                    stmt.bindString(8,jsonObjectItem.getString("INDICADOR1").toString().replace("null",""));
                    stmt.bindString(9,jsonObjectItem.getString("INDICADOR2").toString().replace("null",""));
                    stmt.bindString(10,jsonObjectItem.getString("INDICADOR3").toString().replace("null",""));
                    stmt.bindString(11,jsonObjectItem.getString("INDICADOR4").toString().replace("null",""));
                    stmt.bindString(12,jsonObjectItem.getString("ID_EMPRESA").toString());
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

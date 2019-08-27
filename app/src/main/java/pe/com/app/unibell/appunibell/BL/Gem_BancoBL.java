package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.Gem_BancoBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Gem_BancoBL {
    public ArrayList<Gem_BancoBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Gem_BancoBE gem_bancoBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Gem_BancoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    gem_bancoBE = new Gem_BancoBE();
                    gem_bancoBE.setID_BANCO(jsonObjectItem.getInt("ID_BANCO"));
                    gem_bancoBE.setBANCO(jsonObjectItem.getString("BANCO"));
                    gem_bancoBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    gem_bancoBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    gem_bancoBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    gem_bancoBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    gem_bancoBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    gem_bancoBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    gem_bancoBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    gem_bancoBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    gem_bancoBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    gem_bancoBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    gem_bancoBE.setABREVIADO(jsonObjectItem.getString("ABREVIADO"));
                    gem_bancoBE.setCODIGO_LOGIX(jsonObjectItem.getString("CODIGO_LOGIX"));
                    gem_bancoBE.setVISIBLE(jsonObjectItem.getString("VISIBLE"));
                    lst.add(gem_bancoBE);
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
                DataBaseHelper.myDataBase.delete("S_GEM_BANCO", null, null);

                String SQL="INSERT OR REPLACE INTO S_GEM_BANCO(" +
                        "ID_BANCO,BANCO,ESTADO,ID_EMPRESA,FECHA_REGISTRO," +
                        "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                        "IP_REGISTRO,IP_MODIFICACION,ABREVIADO,CODIGO_LOGIX,VISIBLE)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_BANCO"));
                    stmt.bindString(2,jsonObjectItem.getString("BANCO"));
                    stmt.bindString(3,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(4,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(5,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(6,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(7,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(8,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(9,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(10,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(11,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(12,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(13,jsonObjectItem.getString("ABREVIADO"));
                    stmt.bindString(14,jsonObjectItem.getString("CODIGO_LOGIX"));
                    stmt.bindString(15,jsonObjectItem.getString("VISIBLE"));
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

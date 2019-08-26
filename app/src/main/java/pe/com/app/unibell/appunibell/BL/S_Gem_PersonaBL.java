package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_PersonaBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Gem_PersonaBL {
    public ArrayList<S_Gem_PersonaBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Gem_PersonaBE s_gem_personaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Gem_PersonaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                s_gem_personaBE = new S_Gem_PersonaBE();
                s_gem_personaBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                s_gem_personaBE.setTIPO_PERSONA(jsonObjectItem.getInt("TIPO_PERSONA"));
                s_gem_personaBE.setAPELLIDO_PATERNO(jsonObjectItem.getString("APELLIDO_PATERNO"));
                s_gem_personaBE.setAPELLIDO_MATERNO(jsonObjectItem.getString("APELLIDO_MATERNO"));
                s_gem_personaBE.setNOMBRES(jsonObjectItem.getString("NOMBRES"));
                s_gem_personaBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                s_gem_personaBE.setRUC(jsonObjectItem.getString("RUC"));
                s_gem_personaBE.setDISTRITO(jsonObjectItem.getString("DISTRITO"));
                s_gem_personaBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                s_gem_personaBE.setSEXO(jsonObjectItem.getInt("SEXO"));
                s_gem_personaBE.setFECHA_NATAL(jsonObjectItem.getString("FECHA_NATAL"));
                s_gem_personaBE.setESTADO_CIVIL(jsonObjectItem.getInt("ESTADO_CIVIL"));
                s_gem_personaBE.setTIPO_DOC(jsonObjectItem.getInt("TIPO_DOC"));
                s_gem_personaBE.setNRO_DOC(jsonObjectItem.getString("NRO_DOC"));
                s_gem_personaBE.setCELULAR(jsonObjectItem.getString("CELULAR"));
                s_gem_personaBE.setCORREO(jsonObjectItem.getString("CORREO"));
                s_gem_personaBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                lst.add(s_gem_personaBE);
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
            DataBaseHelper.myDataBase.delete("S_GEM_PERSONA", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                String SQL="INSERT OR REPLACE INTO S_GEM_PERSONA(" +
                        "ID_PERSONA,TIPO_PERSONA,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,DIRECCION," +
                        "RUC,DISTRITO,TELEFONO,SEXO,FECHA_NATAL,ESTADO_CIVIL," +
                        "TIPO_DOC,NRO_DOC,CELULAR,CORREO,ESTADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_PERSONA"));
                    stmt.bindString(2,jsonObjectItem.getString("TIPO_PERSONA"));
                    stmt.bindString(3,jsonObjectItem.getString("APELLIDO_PATERNO"));
                    stmt.bindString(4,jsonObjectItem.getString("APELLIDO_MATERNO"));
                    stmt.bindString(5,jsonObjectItem.getString("NOMBRES"));
                    stmt.bindString(6,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(7,jsonObjectItem.getString("RUC"));
                    stmt.bindString(8,jsonObjectItem.getString("DISTRITO"));
                    stmt.bindString(9,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(10,jsonObjectItem.getString("SEXO"));
                    stmt.bindString(11,jsonObjectItem.getString("FECHA_NATAL"));
                    stmt.bindString(12,jsonObjectItem.getString("ESTADO_CIVIL"));
                    stmt.bindString(13,jsonObjectItem.getString("TIPO_DOC"));
                    stmt.bindString(14,jsonObjectItem.getString("NRO_DOC"));
                    stmt.bindString(15,jsonObjectItem.getString("CELULAR"));
                    stmt.bindString(16,jsonObjectItem.getString("CORREO"));
                    stmt.bindString(17,jsonObjectItem.getString("ESTADO"));
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.ParTablaBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class ParTablaBL {

    public ArrayList<ParTablaBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        ParTablaBE parTablaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<ParTablaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    parTablaBE = new ParTablaBE();
                    parTablaBE.setIDTABLA(jsonObjectItem.getInt("IDTABLA"));
                    parTablaBE.setDESCRIPCION(jsonObjectItem.getString("DESCRIPCION"));
                    parTablaBE.setABREVIADO(jsonObjectItem.getString("ABREVIADO"));
                    parTablaBE.setVALOR1(jsonObjectItem.getString("VALOR1"));
                    parTablaBE.setVALOR2(jsonObjectItem.getString("VALOR2"));
                    parTablaBE.setVALOR3(jsonObjectItem.getString("VALOR3"));
                    parTablaBE.setINDICADOR1(jsonObjectItem.getString("INDICADOR1"));
                    parTablaBE.setINDICADOR2(jsonObjectItem.getString("INDICADOR2"));
                    parTablaBE.setINDICADOR3(jsonObjectItem.getString("INDICADOR3"));
                    parTablaBE.setVALORSUNAT(jsonObjectItem.getString("VALORSUNAT"));
                    parTablaBE.setGRUPO(jsonObjectItem.getInt("GRUPO"));
                    parTablaBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    parTablaBE.setSEMUESTRA(jsonObjectItem.getInt("SEMUESTRA"));
                    parTablaBE.setGRUPO_SUPERIOR(jsonObjectItem.getInt("GRUPO_SUPERIOR"));
                    lst.add(parTablaBE);
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
            DataBaseHelper.myDataBase.delete("PARTABLA", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                String SQL="INSERT OR REPLACE INTO PARTABLA(" +
                        "IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                        "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                        "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR )"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("IDTABLA"));
                    stmt.bindString(2,jsonObjectItem.getString("DESCRIPCION"));
                    stmt.bindString(3,jsonObjectItem.getString("ABREVIADO"));
                    stmt.bindString(4,jsonObjectItem.getString("VALOR1").toString().replace("null",""));
                    stmt.bindString(5,jsonObjectItem.getString("VALOR2").toString().replace("null",""));
                    stmt.bindString(6,jsonObjectItem.getString("VALOR3").toString().replace("null",""));
                    stmt.bindString(7,jsonObjectItem.getString("INDICADOR1").toString().replace("null",""));
                    stmt.bindString(8,jsonObjectItem.getString("INDICADOR2").toString().replace("null",""));
                    stmt.bindString(9,jsonObjectItem.getString("INDICADOR3").toString().replace("null",""));
                    stmt.bindString(10,jsonObjectItem.getString("VALORSUNAT").toString().replace("null",""));
                    stmt.bindString(11,jsonObjectItem.getString("GRUPO").toString().replace("null",""));
                    stmt.bindString(12,jsonObjectItem.getString("ESTADO").toString().replace("null",""));
                    stmt.bindString(13,jsonObjectItem.getString("SEMUESTRA").toString().replace("null",""));
                    stmt.bindString(14,jsonObjectItem.getString("GRUPO_SUPERIOR").toString().replace("null",""));
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

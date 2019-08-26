package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Sea_Usuario_LocalBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sea_Usuario_LocalBL {
    public ArrayList<S_Sea_Usuario_LocalBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sea_Usuario_LocalBE s_sea_usuario_localBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sea_Usuario_LocalBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sea_usuario_localBE = new S_Sea_Usuario_LocalBE(); 
                    s_sea_usuario_localBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                    s_sea_usuario_localBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_sea_usuario_localBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    s_sea_usuario_localBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    s_sea_usuario_localBE.setROL(jsonObjectItem.getInt("ROL"));
                    s_sea_usuario_localBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_sea_usuario_localBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_sea_usuario_localBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_sea_usuario_localBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_sea_usuario_localBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_sea_usuario_localBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_sea_usuario_localBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_sea_usuario_localBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_sea_usuario_localBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_sea_usuario_localBE.setPRINCIPAL(jsonObjectItem.getInt("PRINCIPAL"));
                    lst.add(s_sea_usuario_localBE);
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
            DataBaseHelper.myDataBase.delete("S_SEA_USUARIO_LOCAL", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                String SQL="INSERT OR REPLACE INTO S_SEA_USUARIO_LOCAL(" +
                        "ID_PERSONA,ID_EMPRESA,ID_LOCAL,C_PERFIL,ROL," +
                        "ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION," +
                        "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,PRINCIPAL)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_PERSONA"));
                    stmt.bindString(2,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(3,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(4,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(5,jsonObjectItem.getString("ROL"));

                    stmt.bindString(6,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(7,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(8,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(9,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(10,jsonObjectItem.getString("USUARIO_MODIFICACION"));

                    stmt.bindString(11,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(12,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(13,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(14,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(15,jsonObjectItem.getString("PRINCIPAL"));
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

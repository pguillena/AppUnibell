package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Sem_LocalBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sem_LocalBL {

    public ArrayList<S_Sem_LocalBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sem_LocalBE s_sem_localBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_LocalBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sem_localBE = new S_Sem_LocalBE();
                    s_sem_localBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_sem_localBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    s_sem_localBE.setNOMBRE(jsonObjectItem.getString("NOMBRE"));
                    s_sem_localBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                    s_sem_localBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    s_sem_localBE.setFAX(jsonObjectItem.getString("FAX"));
                    s_sem_localBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_sem_localBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_sem_localBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_sem_localBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_sem_localBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_sem_localBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_sem_localBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_sem_localBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_sem_localBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_sem_localBE.setPRINCIPAL(jsonObjectItem.getInt("PRINCIPAL"));
                    s_sem_localBE.setESPROVINCIA(jsonObjectItem.getInt("ESPROVINCIA"));
                    s_sem_localBE.setUBIGEO(jsonObjectItem.getString("UBIGEO"));
                    s_sem_localBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    lst.add(s_sem_localBE);
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
            DataBaseHelper.myDataBase.delete("S_SEM_LOCAL", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                String SQL="INSERT OR REPLACE INTO S_SEM_LOCAL(" +
                        "ID_EMPRESA,ID_LOCAL,NOMBRE,DIRECCION,TELEFONO," +
                        "FAX,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO," +
                        "USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,"+
                        "PRINCIPAL,ESPROVINCIA,UBIGEO,C_SUC_EMP)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(2,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(3,jsonObjectItem.getString("NOMBRE"));
                    stmt.bindString(4,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(5,jsonObjectItem.getString("TELEFONO"));

                    stmt.bindString(6,jsonObjectItem.getString("FAX"));
                    stmt.bindString(7,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(8,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(9,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(10,jsonObjectItem.getString("USUARIO_REGISTRO"));

                    stmt.bindString(11,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(12,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(13,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(14,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(15,jsonObjectItem.getString("IP_MODIFICACION"));

                    stmt.bindString(16,jsonObjectItem.getString("PRINCIPAL"));
                    stmt.bindString(17,jsonObjectItem.getString("ESPROVINCIA"));
                    stmt.bindString(18,jsonObjectItem.getString("UBIGEO"));
                    stmt.bindString(19,jsonObjectItem.getString("C_SUC_EMP"));
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sem_PerfilBE;
import pe.com.app.unibell.appunibell.BE.S_Sem_UsuarioBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sem_UsuarioBL {

    public ArrayList<S_Sem_UsuarioBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sem_UsuarioBE s_sem_usuarioBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_UsuarioBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                     s_sem_usuarioBE = new S_Sem_UsuarioBE();
                     s_sem_usuarioBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                     s_sem_usuarioBE.setCREDENCIAL(jsonObjectItem.getString("CREDENCIAL"));
                     s_sem_usuarioBE.setCLAVE(jsonObjectItem.getString("CLAVE"));
                     s_sem_usuarioBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                     s_sem_usuarioBE.setRESETEADO(jsonObjectItem.getString("RESETEADO"));
                     s_sem_usuarioBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                     s_sem_usuarioBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                     s_sem_usuarioBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                     s_sem_usuarioBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                     s_sem_usuarioBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                     s_sem_usuarioBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                     s_sem_usuarioBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                     s_sem_usuarioBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                     s_sem_usuarioBE.setNOMBRE_CORTO(jsonObjectItem.getString("NOMBRE_CORTO"));
                     s_sem_usuarioBE.setPC_USUARIO(jsonObjectItem.getString("PC_USUARIO"));
                    lst.add(s_sem_usuarioBE);
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
                DataBaseHelper.myDataBase.delete("S_SEM_USUARIO", null, null);

                String SQL="INSERT OR REPLACE INTO S_SEM_USUARIO(ID_PERSONA ,CREDENCIAL,CLAVE,ESTADO,RESETEADO, NOMBRE_CORTO,PC_USUARIO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_PERSONA"));
                    stmt.bindString(2,jsonObjectItem.getString("CREDENCIAL"));
                    stmt.bindString(3,jsonObjectItem.getString("CLAVE"));
                    stmt.bindString(4,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(5,jsonObjectItem.getString("RESETEADO"));
                    stmt.bindString(6,jsonObjectItem.getString("NOMBRE_CORTO"));
                    stmt.bindString(7,jsonObjectItem.getString("PC_USUARIO"));
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


    public JSONObject getUsuarioMD5(String newURL) {
        S_Sem_UsuarioBE s_sem_usuarioBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_UsuarioBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sem_usuarioBE = new S_Sem_UsuarioBE();
                    s_sem_usuarioBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                    lst.add(s_sem_usuarioBE);
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


}

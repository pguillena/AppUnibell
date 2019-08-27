package pe.com.app.unibell.appunibell.BL;


import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Sem_PerfilBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sem_PerfilBL {
    public ArrayList<S_Sem_PerfilBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sem_PerfilBE s_sem_perfilBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_PerfilBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sem_perfilBE = new S_Sem_PerfilBE();
                    s_sem_perfilBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    s_sem_perfilBE.setNOMBRE_PERFIL(jsonObjectItem.getString("NOMBRE_PERFIL"));
                    s_sem_perfilBE.setCOMENTARIO(jsonObjectItem.getString("COMENTARIO"));
                    s_sem_perfilBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_sem_perfilBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_sem_perfilBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_sem_perfilBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_sem_perfilBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_sem_perfilBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_sem_perfilBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_sem_perfilBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_sem_perfilBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_sem_perfilBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    lst.add(s_sem_perfilBE);
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
                DataBaseHelper.myDataBase.delete("S_SEM_PERFIL", null, null);

                String SQL="INSERT OR REPLACE INTO S_SEM_PERFIL(" +
                        "C_PERFIL,NOMBRE_PERFIL,COMENTARIO,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                        "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,FECHA_REGISTRO,FECHA_MODIFICACION,ESTADO," +
                        "ID_EMPRESA)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE_PERFIL"));
                    stmt.bindString(3,jsonObjectItem.getString("COMENTARIO"));
                    stmt.bindString(4,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(5,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(6,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(7,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(8,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(9,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(10,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(11,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(12,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_EMPRESA"));
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

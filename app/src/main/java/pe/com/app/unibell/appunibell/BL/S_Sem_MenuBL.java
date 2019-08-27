package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Sem_MenuBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sem_MenuBL {
    public ArrayList<S_Sem_MenuBE> lst = null;

    public JSONObject getSincronizar(String newURL) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_MenuBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("S_SEM_MENU", null, null);

                String SQL="INSERT OR REPLACE INTO S_SEM_MENU(" +
                        "C_MENU,NOMBRE_MENU,NIVEL,COLUMNA,PARIENTE,ORDEN," +
                        "FORMULARIO,TIPO_FORMULARIO,RUTA_IMAGEN,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                        "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,FECHA_REGISTRO,FECHA_MODIFICACION,ESTADO," +
                        "ID_EMPRESA)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("C_MENU"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE_MENU"));
                    stmt.bindString(3,jsonObjectItem.getString("NIVEL"));
                    stmt.bindString(4,jsonObjectItem.getString("COLUMNA"));
                    stmt.bindString(5,jsonObjectItem.getString("PARIENTE"));
                    stmt.bindString(6,jsonObjectItem.getString("ORDEN"));
                    stmt.bindString(7,jsonObjectItem.getString("FORMULARIO"));
                    stmt.bindString(8,jsonObjectItem.getString("TIPO_FORMULARIO"));
                    stmt.bindString(9,jsonObjectItem.getString("RUTA_IMAGEN"));
                    stmt.bindString(10,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(11,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(12,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(13,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(14,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(15,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(16,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(17,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(18,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(19,jsonObjectItem.getString("ID_EMPRESA"));
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

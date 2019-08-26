package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Vem_CorrelativoBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Vem_CorrelativoBL {
    public ArrayList<S_Vem_CorrelativoBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Vem_CorrelativoBE s_vem_correlativoBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Vem_CorrelativoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_vem_correlativoBE = new S_Vem_CorrelativoBE();
                    s_vem_correlativoBE.setTIPO_COMPROBANTE(jsonObjectItem.getString("TIPO_COMPROBANTE"));
                    s_vem_correlativoBE.setID_CANAL(jsonObjectItem.getInt("ID_CANAL"));
                    s_vem_correlativoBE.setNRO_SERIE(jsonObjectItem.getString("NRO_SERIE"));
                    s_vem_correlativoBE.setNRO(jsonObjectItem.getInt("NRO"));
                    s_vem_correlativoBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_vem_correlativoBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    s_vem_correlativoBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_vem_correlativoBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_vem_correlativoBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_vem_correlativoBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_vem_correlativoBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_vem_correlativoBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_vem_correlativoBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_vem_correlativoBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_vem_correlativoBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_vem_correlativoBE.setAUTOMATICO(jsonObjectItem.getInt("AUTOMATICO"));
                    s_vem_correlativoBE.setID_USUARIO(jsonObjectItem.getInt("ID_USUARIO"));                 
                    lst.add(s_vem_correlativoBE);
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
            lst=new ArrayList<S_Vem_CorrelativoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //Eliminamos los registros
            DataBaseHelper.myDataBase.delete("S_VEM_CORRELATIVO", null, null);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{

                String SQL="INSERT OR REPLACE INTO S_VEM_CORRELATIVO(" +
                        "TIPO_COMPROBANTE,ID_CANAL,NRO_SERIE,NRO,ID_EMPRESA,ID_LOCAL," +
                        "ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                        "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,AUTOMATICO,ID_USUARIO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                
                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("TIPO_COMPROBANTE"));
                    stmt.bindString(2,jsonObjectItem.getString("ID_CANAL"));
                    stmt.bindString(3,jsonObjectItem.getString("NRO_SERIE"));
                    stmt.bindString(4,jsonObjectItem.getString("NRO"));
                    stmt.bindString(5,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(6,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(7,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(8,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(9,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(10,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(11,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(12,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(13,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(14,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(15,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(16,jsonObjectItem.getString("AUTOMATICO"));
                    stmt.bindString(17,jsonObjectItem.getString("ID_USUARIO"));                    
                  
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


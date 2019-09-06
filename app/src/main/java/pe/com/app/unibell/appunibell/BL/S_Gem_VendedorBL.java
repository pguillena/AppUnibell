package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_VendedorBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Gem_VendedorBL {
    public ArrayList<S_Gem_VendedorBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Gem_VendedorBE s_gem_vendedorBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Gem_VendedorBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_gem_vendedorBE = new S_Gem_VendedorBE();
                    s_gem_vendedorBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                    s_gem_vendedorBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_gem_vendedorBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    s_gem_vendedorBE.setTIPO_VENDEDOR(jsonObjectItem.getInt("TIPO_VENDEDOR"));
                    s_gem_vendedorBE.setFECHA_CESE(jsonObjectItem.getString("FECHA_CESE"));
                    s_gem_vendedorBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_gem_vendedorBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_gem_vendedorBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_gem_vendedorBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_gem_vendedorBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_gem_vendedorBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_gem_vendedorBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_gem_vendedorBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_gem_vendedorBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_gem_vendedorBE.setVISIBLE(jsonObjectItem.getInt("VISIBLE"));
                    s_gem_vendedorBE.setVALIDA_RECIBO(jsonObjectItem.getInt("VALIDA_RECIBO"));
                    s_gem_vendedorBE.setID_CANAL(jsonObjectItem.getInt("ID_CANAL"));
                   lst.add(s_gem_vendedorBE);
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
                DataBaseHelper.myDataBase.delete("S_GEM_VENDEDOR", null, null);

                String SQL="INSERT OR REPLACE INTO S_GEM_VENDEDOR(ID_PERSONA, ID_EMPRESA, ID_LOCAL, TIPO_VENDEDOR, FECHA_CESE, ESTADO, VISIBLE, VALIDA_RECIBO, ID_CANAL) " +
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?)";

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
                    stmt.bindString(4,jsonObjectItem.getString("TIPO_VENDEDOR"));
                    stmt.bindString(5,jsonObjectItem.getString("FECHA_CESE"));
                    stmt.bindString(6,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(7,jsonObjectItem.getString("VISIBLE"));
                    stmt.bindString(8,jsonObjectItem.getString("VALIDA_RECIBO"));
                    stmt.bindString(9,jsonObjectItem.getString("ID_CANAL"));
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

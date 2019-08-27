package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.MvendedorBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class MvendedorBL {
    public ArrayList<MvendedorBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        MvendedorBE mvendedorBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<MvendedorBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    mvendedorBE = new MvendedorBE();
                    mvendedorBE.setC_VENDEDOR(jsonObjectItem.getString("C_VENDEDOR"));
                    mvendedorBE.setAPELLIDO_PATERNO(jsonObjectItem.getString("APELLIDO_PATERNO"));
                    mvendedorBE.setAPELLIDO_MATERNO(jsonObjectItem.getString("APELLIDO_MATERNO"));
                    mvendedorBE.setNOMBRES(jsonObjectItem.getString("NOMBRES"));
                    mvendedorBE.setC_CANAL(jsonObjectItem.getString("C_CANAL"));
                    mvendedorBE.setC_SUBCANAL(jsonObjectItem.getString("C_SUBCANAL"));
                    mvendedorBE.setI_VENDEDOR(jsonObjectItem.getString("I_VENDEDOR"));
                    mvendedorBE.setC_EMPRESA(jsonObjectItem.getString("C_EMPRESA"));
                    mvendedorBE.setF_INGRESO(jsonObjectItem.getString("F_INGRESO"));
                    mvendedorBE.setF_CESE(jsonObjectItem.getString("F_CESE"));
                    mvendedorBE.setF_REGISTRO(jsonObjectItem.getString("F_REGISTRO"));
                    mvendedorBE.setC_USUARIO_REGISTRO(jsonObjectItem.getString("C_USUARIO_REGISTRO"));
                    mvendedorBE.setF_ACTUALIZACION(jsonObjectItem.getString("F_ACTUALIZACION"));
                    mvendedorBE.setC_USUARIO_ACTUALIZACION(jsonObjectItem.getString("C_GERENCIA"));
                    mvendedorBE.setC_GERENCIA(jsonObjectItem.getString("C_GERENCIA"));
                    mvendedorBE.setI_TIPO_VENDEDOR(jsonObjectItem.getString("I_TIPO_VENDEDOR"));
                    mvendedorBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    mvendedorBE.setN_CELULAR(jsonObjectItem.getString("N_CELULAR"));
                    mvendedorBE.setI_SIGA(jsonObjectItem.getInt("I_SIGA"));
                    mvendedorBE.setF_ENTREGA_TABLET(jsonObjectItem.getString("F_ENTREGA_TABLET"));
                    mvendedorBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    mvendedorBE.setI_RUTA(jsonObjectItem.getString("I_RUTA"));
                    mvendedorBE.setC_CODIGO(jsonObjectItem.getString("C_CODIGO"));
                    mvendedorBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    lst.add(mvendedorBE);
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
                DataBaseHelper.myDataBase.delete("MVENDEDOR", null, null);

                String SQL="INSERT OR REPLACE INTO MVENDEDOR(" +
                        "C_VENDEDOR,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,C_CANAL,C_SUBCANAL,"+
                        "I_VENDEDOR,C_EMPRESA,F_INGRESO,F_CESE,F_REGISTRO,C_USUARIO_REGISTRO,"+
                        "F_ACTUALIZACION,C_USUARIO_ACTUALIZACION,C_GERENCIA,I_TIPO_VENDEDOR,C_SUC_EMP,N_CELULAR,"+
                        "I_SIGA,F_ENTREGA_TABLET,OBSERVACION,I_RUTA,C_CODIGO,ID_LOCAL)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("C_VENDEDOR"));
                    stmt.bindString(2,jsonObjectItem.getString("APELLIDO_PATERNO"));
                    stmt.bindString(3,jsonObjectItem.getString("APELLIDO_MATERNO"));
                    stmt.bindString(4,jsonObjectItem.getString("NOMBRES"));
                    stmt.bindString(5,jsonObjectItem.getString("C_CANAL"));
                    stmt.bindString(6,jsonObjectItem.getString("C_SUBCANAL"));

                    stmt.bindString(7,jsonObjectItem.getString("I_VENDEDOR"));
                    stmt.bindString(8,jsonObjectItem.getString("C_EMPRESA"));
                    stmt.bindString(9,jsonObjectItem.getString("F_INGRESO"));
                    stmt.bindString(10,jsonObjectItem.getString("F_CESE"));
                    stmt.bindString(11,jsonObjectItem.getString("F_REGISTRO"));
                    stmt.bindString(12,jsonObjectItem.getString("C_USUARIO_REGISTRO"));

                    stmt.bindString(13,jsonObjectItem.getString("F_ACTUALIZACION"));
                    stmt.bindString(14,jsonObjectItem.getString("C_GERENCIA"));
                    stmt.bindString(15,jsonObjectItem.getString("C_GERENCIA"));
                    stmt.bindString(16,jsonObjectItem.getString("I_TIPO_VENDEDOR"));
                    stmt.bindString(17,jsonObjectItem.getString("C_SUC_EMP"));
                    stmt.bindString(18,jsonObjectItem.getString("N_CELULAR"));

                    stmt.bindString(19,jsonObjectItem.getString("I_SIGA"));
                    stmt.bindString(20,jsonObjectItem.getString("F_ENTREGA_TABLET"));
                    stmt.bindString(21,jsonObjectItem.getString("OBSERVACION"));
                    stmt.bindString(22,jsonObjectItem.getString("I_RUTA"));
                    stmt.bindString(23,jsonObjectItem.getString("C_CODIGO"));
                    stmt.bindString(24,jsonObjectItem.getString("ID_LOCAL"));
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

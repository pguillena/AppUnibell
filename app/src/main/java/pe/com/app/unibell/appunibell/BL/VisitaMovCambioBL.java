package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.VisitaMovCambioBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class VisitaMovCambioBL {


    public ArrayList<VisitaMovCambioBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        VisitaMovCambioBE visitaMovCambioBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<VisitaMovCambioBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    visitaMovCambioBE = new VisitaMovCambioBE();
                    visitaMovCambioBE.setN_INFORME(jsonObjectItem.getInt("N_INFORME"));
                    visitaMovCambioBE.setN_SEQUENCIA(jsonObjectItem.getInt("N_SEQUENCIA"));
                    visitaMovCambioBE.setC_CLIENTE(jsonObjectItem.getString("C_CLIENTE"));
                    visitaMovCambioBE.setC_VENDEDOR(jsonObjectItem.getString("C_VENDEDOR"));
                    visitaMovCambioBE.setID_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_USUARIO_REGISTRO"));
                    visitaMovCambioBE.setID_ROL_USUARIO_REGISTRO(jsonObjectItem.getInt("ID_ROL_USUARIO_REGISTRO"));
                    visitaMovCambioBE.setID_USUARIO_DERIVAR(jsonObjectItem.getInt("ID_USUARIO_DERIVAR"));
                    visitaMovCambioBE.setID_ROL_USUARIO_DERIVAR(jsonObjectItem.getInt("ID_ROL_USUARIO_DERIVAR"));
                    visitaMovCambioBE.setOBSERVACION_FINALIZAR(jsonObjectItem.getString("OBSERVACION_FINALIZAR"));
                    visitaMovCambioBE.setID_USUARIO_FINALIZAR(jsonObjectItem.getInt("ID_USUARIO_FINALIZAR"));
                    visitaMovCambioBE.setESTADO_MOVIMIENTO(jsonObjectItem.getInt("ESTADO_MOVIMIENTO"));
                    visitaMovCambioBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    visitaMovCambioBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    visitaMovCambioBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    visitaMovCambioBE.setC_DIA_VISITA(jsonObjectItem.getInt("C_DIA_VISITA"));
                    visitaMovCambioBE.setN_ORD_VISITA(jsonObjectItem.getInt("N_ORD_VISITA"));
                    visitaMovCambioBE.setC_FRC_VISITA(jsonObjectItem.getString("C_FRC_VISITA"));
                    visitaMovCambioBE.setC_CUADRANTE(jsonObjectItem.getString("C_CUADRANTE"));


                    lst.add(visitaMovCambioBE);
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
            lst=new ArrayList<VisitaMovCambioBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("VEM_VISITA_MOVIMIENTO_CAMBIO", null, null);

                String SQL="INSERT OR REPLACE INTO VEM_VISITA_MOVIMIENTO_CAMBIO(N_INFORME,  N_SEQUENCIA,  C_CLIENTE,  C_VENDEDOR,  ID_USUARIO_REGISTRO,  ID_ROL_USUARIO_REGISTRO,  ID_USUARIO_DERIVAR,  ID_ROL_USUARIO_DERIVAR,  OBSERVACION_FINALIZAR,  ID_USUARIO_FINALIZAR,  ESTADO_MOVIMIENTO,  ID_EMPRESA,  ID_LOCAL,  OBSERVACION,  C_DIA_VISITA,  N_ORD_VISITA,  C_FRC_VISITA,  C_CUADRANTE)" +
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();

                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("N_INFORME,"));
                    stmt.bindString(2,jsonObjectItem.getString("N_SEQUENCIA,"));
                    stmt.bindString(3,jsonObjectItem.getString("C_CLIENTE,"));
                    stmt.bindString(4,jsonObjectItem.getString("C_VENDEDOR,"));
                    stmt.bindString(5,jsonObjectItem.getString("ID_USUARIO_REGISTRO,"));
                    stmt.bindString(6,jsonObjectItem.getString("ID_ROL_USUARIO_REGISTRO,"));
                    stmt.bindString(7,jsonObjectItem.getString("ID_USUARIO_DERIVAR,"));
                    stmt.bindString(8,jsonObjectItem.getString("ID_ROL_USUARIO_DERIVAR,"));
                    stmt.bindString(9,jsonObjectItem.getString("OBSERVACION_FINALIZAR,"));
                    stmt.bindString(10,jsonObjectItem.getString("ID_USUARIO_FINALIZAR,"));
                    stmt.bindString(11,jsonObjectItem.getString("ESTADO_MOVIMIENTO,"));
                    stmt.bindString(12,jsonObjectItem.getString("ID_EMPRESA,"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_LOCAL,"));
                    stmt.bindString(14,jsonObjectItem.getString("OBSERVACION,"));
                    stmt.bindString(15,jsonObjectItem.getString("C_DIA_VISITA,"));
                    stmt.bindString(16,jsonObjectItem.getString("N_ORD_VISITA,"));
                    stmt.bindString(17,jsonObjectItem.getString("C_FRC_VISITA,"));
                    stmt.bindString(18,jsonObjectItem.getString("C_CUADRANTE"));


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

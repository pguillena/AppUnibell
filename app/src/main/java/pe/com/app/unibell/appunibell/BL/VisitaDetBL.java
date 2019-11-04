package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.VisitaDetBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class VisitaDetBL {
    public ArrayList<VisitaDetBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        VisitaDetBE visitaDetBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<VisitaDetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    visitaDetBE = new VisitaDetBE();
                    visitaDetBE.setN_INFORME(jsonObjectItem.getInt("N_INFORME"));
                    visitaDetBE.setN_SEQUENCIA(jsonObjectItem.getInt("N_SEQUENCIA"));
                    visitaDetBE.setC_CLIENTE(jsonObjectItem.getString("C_CLIENTE"));
                    visitaDetBE.setHORA_S(jsonObjectItem.getString("HORA_S"));

                    lst.add(visitaDetBE);
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
            lst=new ArrayList<VisitaDetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("VEM_VISITA_DET", null, null);

                String SQL2="INSERT OR REPLACE INTO VEM_VISITA_DET(N_INFORME, N_SEQUENCIA, C_CLIENTE, NOMBRE_CLIENTE, CONTACTO, HORA_I, HORA_S, " +
                        "                                          GESTION_V, GESTION_C, RESULTADO_V, RESULTADO_C, OBJECION_V, OBJECION_C, OBJECION_O," +
                        "                                          GESTION_O, OBSERVACION, M_PEDIDO, M_COBRANZA, NUEVO_DIA_VISITA, M_FACTURADO, NUEVO_DIA_COBRANZA, DIAS, TOTAL_DEUDA)" +
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();

                SQLiteStatement stmt2 = DataBaseHelper.myDataBase.compileStatement(SQL2);


                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt2.bindString(1,jsonObjectItem.getString("N_INFORME"));
                    stmt2.bindString(2,jsonObjectItem.getString("N_SEQUENCIA"));
                    stmt2.bindString(3,jsonObjectItem.getString("C_CLIENTE"));
                    stmt2.bindString(4,jsonObjectItem.getString("NOMBRE_CLIENTE"));
                    stmt2.bindString(5,jsonObjectItem.getString("CONTACTO"));

                    if(jsonObjectItem.getString("HORA_I").equals("null"))
                    {
                        stmt2.bindString(6,"");
                    }
                    else
                    {
                        stmt2.bindString(6,jsonObjectItem.getString("HORA_I"));
                    }

                    if(jsonObjectItem.getString("HORA_S").equals("null"))
                    {
                        stmt2.bindString(7,"");
                    }
                    else
                    {
                        stmt2.bindString(7,jsonObjectItem.getString("HORA_S"));
                    }


                    stmt2.bindString(8,jsonObjectItem.getString("GESTION_V"));
                    stmt2.bindString(9,jsonObjectItem.getString("GESTION_C"));
                    stmt2.bindString(10,jsonObjectItem.getString("RESULTADO_V"));
                    stmt2.bindString(11,jsonObjectItem.getString("RESULTADO_C"));
                    stmt2.bindString(12,jsonObjectItem.getString("OBJECION_V"));
                    stmt2.bindString(13,jsonObjectItem.getString("OBJECION_C"));
                    stmt2.bindString(14,jsonObjectItem.getString("OBJECION_O"));
                    stmt2.bindString(15,jsonObjectItem.getString("GESTION_O"));
                    stmt2.bindString(16,jsonObjectItem.getString("OBSERVACION"));
                    stmt2.bindString(17,jsonObjectItem.getString("M_PEDIDO"));
                    stmt2.bindString(18,jsonObjectItem.getString("M_COBRANZA"));
                    stmt2.bindString(19,jsonObjectItem.getString("NUEVO_DIA_VISITA"));
                    stmt2.bindString(20,jsonObjectItem.getString("M_FACTURADO"));
                    stmt2.bindString(21,jsonObjectItem.getString("NUEVO_DIA_COBRANZA"));
                    stmt2.bindString(22,jsonObjectItem.getString("DIAS"));
                    stmt2.bindString(23,jsonObjectItem.getString("TOTAL_DEUDA"));

                    stmt2.execute();
                    stmt2.clearBindings();
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

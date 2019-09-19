package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.Recibos_CcobranzaBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Main.Activity_Sincronizar;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;
import pe.com.app.unibell.appunibell.Util.ToastLibrary;

public class Recibos_CcobranzaBL {
    public ArrayList<Recibos_CcobranzaBE> lst = null;
   private Context context;
    public JSONObject getAllRest(String newURL) {
        Recibos_CcobranzaBE recibos_ccobranzaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Recibos_CcobranzaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    recibos_ccobranzaBE = new Recibos_CcobranzaBE();
                    recibos_ccobranzaBE.setN_SERIE(jsonObjectItem.getString("N_SERIE"));
                    recibos_ccobranzaBE.setN_NUMINI(jsonObjectItem.getString("N_NUMINI"));
                    recibos_ccobranzaBE.setN_NUMFIN(jsonObjectItem.getString("N_NUMFIN"));
                    recibos_ccobranzaBE.setC_TIPO_REC(jsonObjectItem.getString("C_TIPO_REC"));
                    recibos_ccobranzaBE.setC_RECEPTOR(jsonObjectItem.getString("C_RECEPTOR"));
                    recibos_ccobranzaBE.setF_RECEPCION(jsonObjectItem.getString("F_RECEPCION"));
                    recibos_ccobranzaBE.setF_DEVOLUCION(jsonObjectItem.getString("F_DEVOLUCION"));
                    recibos_ccobranzaBE.setVIGENCIA(jsonObjectItem.getString("VIGENCIA"));
                    recibos_ccobranzaBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    recibos_ccobranzaBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    recibos_ccobranzaBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    recibos_ccobranzaBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    recibos_ccobranzaBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
                    recibos_ccobranzaBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
                    recibos_ccobranzaBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
                    recibos_ccobranzaBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
                    recibos_ccobranzaBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    recibos_ccobranzaBE.setC_ESTADO(jsonObjectItem.getString("C_ESTADO"));
                    lst.add(recibos_ccobranzaBE);
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
                DataBaseHelper.myDataBase.delete("CCM_RECIBOS_COBRANZA", null, null);

                String SQL="INSERT OR REPLACE INTO CCM_RECIBOS_COBRANZA(" +
                        "N_SERIE,N_NUMINI,N_NUMFIN,C_TIPO_REC,C_RECEPTOR," +
                        "F_RECEPCION,F_DEVOLUCION,VIGENCIA,C_USUARIO, OBSERVACION,C_ESTADO) "+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);
                String sF_DEVOLUCION="";

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("N_SERIE"));
                    stmt.bindString(2,jsonObjectItem.getString("N_NUMINI"));
                    stmt.bindString(3,jsonObjectItem.getString("N_NUMFIN"));
                    stmt.bindString(4,jsonObjectItem.getString("C_TIPO_REC"));
                    stmt.bindString(5,jsonObjectItem.getString("C_RECEPTOR"));
                    stmt.bindString(6,jsonObjectItem.getString("F_RECEPCION"));
                    stmt.bindString(7,jsonObjectItem.getString("F_DEVOLUCION").trim().replace("null",""));
                    stmt.bindString(8,jsonObjectItem.getString("VIGENCIA"));
                    stmt.bindString(9,jsonObjectItem.getString("C_USUARIO"));
                    stmt.bindString(10,jsonObjectItem.getString("OBSERVACION"));
                    stmt.bindString(11,jsonObjectItem.getString("C_ESTADO"));
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
            e.printStackTrace();
            DataBaseHelper.myDataBase.endTransaction();

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

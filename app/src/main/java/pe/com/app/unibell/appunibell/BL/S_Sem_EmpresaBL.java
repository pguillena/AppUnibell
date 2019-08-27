package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Sem_EmpresaBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sem_EmpresaBL {

    public ArrayList<S_Sem_EmpresaBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sem_EmpresaBE s_sem_empresaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sem_EmpresaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sem_empresaBE = new S_Sem_EmpresaBE();
                    s_sem_empresaBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_sem_empresaBE.setNOMBRE(jsonObjectItem.getString("NOMBRE"));
                    s_sem_empresaBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                    s_sem_empresaBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                    s_sem_empresaBE.setRUC(jsonObjectItem.getString("RUC"));
                    s_sem_empresaBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    s_sem_empresaBE.setFAX(jsonObjectItem.getString("FAX"));
                    s_sem_empresaBE.setREPRESENTANTE(jsonObjectItem.getString("REPRESENTANTE"));
                    s_sem_empresaBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_sem_empresaBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_sem_empresaBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_sem_empresaBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_sem_empresaBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_sem_empresaBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_sem_empresaBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_sem_empresaBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_sem_empresaBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_sem_empresaBE.setC_EMPRESA(jsonObjectItem.getString("C_EMPRESA"));
                    s_sem_empresaBE.setAGENTE_PERCEPCION(jsonObjectItem.getInt("AGENTE_PERCEPCION"));
                    s_sem_empresaBE.setAGENTE_RETENCION(jsonObjectItem.getInt("AGENTE_RETENCION"));
                    s_sem_empresaBE.setBUEN_CONTRIBUYENTE(jsonObjectItem.getInt("BUEN_CONTRIBUYENTE"));
                    s_sem_empresaBE.setUBIGEO(jsonObjectItem.getString("UBIGEO"));
                    s_sem_empresaBE.setRESOLUCION_SUNAT_FE(jsonObjectItem.getString("RESOLUCION_SUNAT_FE"));
                    s_sem_empresaBE.setPAGINA_WEB(jsonObjectItem.getString("PAGINA_WEB"));
                    lst.add(s_sem_empresaBE);
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
                DataBaseHelper.myDataBase.delete("S_SEM_EMPRESA", null, null);

                String SQL="INSERT OR REPLACE INTO S_SEM_EMPRESA(" +
                        "ID_EMPRESA,NOMBRE,RAZON_SOCIAL,DIRECCION,RUC," +
                        "TELEFONO,FAX,REPRESENTANTE,ESTADO,FECHA_REGISTRO," +
                        "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                        "IP_REGISTRO,IP_MODIFICACION,C_EMPRESA,AGENTE_PERCEPCION,AGENTE_RETENCION," +
                        "BUEN_CONTRIBUYENTE,UBIGEO,RESOLUCION_SUNAT_FE,PAGINA_WEB)"+
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

                    stmt.bindString(1,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(2,jsonObjectItem.getString("NOMBRE"));
                    stmt.bindString(3,jsonObjectItem.getString("RAZON_SOCIAL"));
                    stmt.bindString(4,jsonObjectItem.getString("DIRECCION"));
                    stmt.bindString(5,jsonObjectItem.getString("RUC"));
                    stmt.bindString(6,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(7,jsonObjectItem.getString("FAX"));
                    stmt.bindString(8,jsonObjectItem.getString("REPRESENTANTE"));
                    stmt.bindString(9,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(10,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(11,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(12,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(13,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(14,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(15,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(16,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(17,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(18,jsonObjectItem.getString("C_EMPRESA"));
                    stmt.bindString(19,jsonObjectItem.getString("AGENTE_PERCEPCION"));
                    stmt.bindString(20,jsonObjectItem.getString("AGENTE_RETENCION"));
                    stmt.bindString(21,jsonObjectItem.getString("BUEN_CONTRIBUYENTE"));
                    stmt.bindString(22,jsonObjectItem.getString("UBIGEO"));
                    stmt.bindString(23,jsonObjectItem.getString("RESOLUCION_SUNAT_FE"));
                    stmt.bindString(24,jsonObjectItem.getString("PAGINA_WEB"));
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_Persona_DireccionBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Gem_Persona_DireccionBL {
    public ArrayList<S_Gem_Persona_DireccionBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Gem_Persona_DireccionBE s_gem_persona_direccionBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Gem_Persona_DireccionBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_gem_persona_direccionBE = new S_Gem_Persona_DireccionBE();
                    s_gem_persona_direccionBE.setID_DIRECCION(jsonObjectItem.getInt("ID_DIRECCION"));
                    s_gem_persona_direccionBE.setID_PERSONA(jsonObjectItem.getInt("ID_PERSONA"));
                    s_gem_persona_direccionBE.setTIPO_DIRECCION(jsonObjectItem.getInt("TIPO_DIRECCION"));
                    s_gem_persona_direccionBE.setFACTURACION(jsonObjectItem.getInt("FACTURACION"));
                    s_gem_persona_direccionBE.setID_VIA(jsonObjectItem.getInt("ID_VIA"));
                    s_gem_persona_direccionBE.setNOMBRE_VIA(jsonObjectItem.getString("NOMBRE_VIA"));
                    s_gem_persona_direccionBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    s_gem_persona_direccionBE.setINTERIOR(jsonObjectItem.getString("INTERIOR"));
                    s_gem_persona_direccionBE.setID_ZONA(jsonObjectItem.getString("ID_ZONA"));
                    s_gem_persona_direccionBE.setNOMBRE_ZONA(jsonObjectItem.getString("NOMBRE_ZONA"));
                    s_gem_persona_direccionBE.setKILOMETRO(jsonObjectItem.getString("KILOMETRO"));
                    s_gem_persona_direccionBE.setMANZANA(jsonObjectItem.getString("MANZANA"));
                    s_gem_persona_direccionBE.setLOTE(jsonObjectItem.getString("LOTE"));
                    s_gem_persona_direccionBE.setREFERENCIA(jsonObjectItem.getString("REFERENCIA"));
                    s_gem_persona_direccionBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    s_gem_persona_direccionBE.setUBIGEO(jsonObjectItem.getString("UBIGEO"));
                    s_gem_persona_direccionBE.setDEPARTAMENTO(jsonObjectItem.getString("DEPARTAMENTO"));
                    s_gem_persona_direccionBE.setBLOQUE(jsonObjectItem.getString("BLOQUE"));
                    s_gem_persona_direccionBE.setETAPA(jsonObjectItem.getString("ETAPA"));
                    s_gem_persona_direccionBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_gem_persona_direccionBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_gem_persona_direccionBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_gem_persona_direccionBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_gem_persona_direccionBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_gem_persona_direccionBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_gem_persona_direccionBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_gem_persona_direccionBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_gem_persona_direccionBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_gem_persona_direccionBE.setDIRECCION_COMPLETA(jsonObjectItem.getString("DIRECCION_COMPLETA"));
                    s_gem_persona_direccionBE.setTELEFONO(jsonObjectItem.getString("TELEFONO")); 
                    lst.add(s_gem_persona_direccionBE);
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
                DataBaseHelper.myDataBase.delete("S_GEM_PERSONA_DIRECCION", null, null);

                String SQL="INSERT OR REPLACE INTO S_GEM_PERSONA_DIRECCION(" +
                        "ID_DIRECCION,ID_PERSONA,TIPO_DIRECCION,FACTURACION,ID_VIA,NOMBRE_VIA," +
                        "NUMERO,INTERIOR,ID_ZONA,NOMBRE_ZONA,KILOMETRO,MANZANA," +
                        "LOTE,REFERENCIA,OBSERVACION,UBIGEO,DEPARTAMENTO,BLOQUE," +
                        "USUARIO_MODIFICACION,ETAPA,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO," +
                        "PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,DIRECCION_COMPLETA,TELEFONO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("ID_DIRECCION"));
                    stmt.bindString(2,jsonObjectItem.getString("ID_PERSONA"));
                    stmt.bindString(3,jsonObjectItem.getString("TIPO_DIRECCION"));
                    stmt.bindString(4,jsonObjectItem.getString("FACTURACION"));
                    stmt.bindString(5,jsonObjectItem.getString("ID_VIA"));
                    stmt.bindString(6,jsonObjectItem.getString("NOMBRE_VIA"));
                    stmt.bindString(7,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(8,jsonObjectItem.getString("INTERIOR"));
                    stmt.bindString(9,jsonObjectItem.getString("ID_ZONA"));
                    stmt.bindString(10,jsonObjectItem.getString("NOMBRE_ZONA"));
                    stmt.bindString(11,jsonObjectItem.getString("KILOMETRO"));
                    stmt.bindString(12,jsonObjectItem.getString("MANZANA"));
                    stmt.bindString(13,jsonObjectItem.getString("LOTE"));
                    stmt.bindString(14,jsonObjectItem.getString("REFERENCIA"));
                    stmt.bindString(15,jsonObjectItem.getString("OBSERVACION"));
                    stmt.bindString(16,jsonObjectItem.getString("UBIGEO"));
                    stmt.bindString(17,jsonObjectItem.getString("DEPARTAMENTO"));
                    stmt.bindString(18,jsonObjectItem.getString("BLOQUE"));
                    stmt.bindString(19,jsonObjectItem.getString("ETAPA"));
                    stmt.bindString(20,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(21,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(22,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(23,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(24,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(25,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(26,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(27,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(28,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(29,jsonObjectItem.getString("DIRECCION_COMPLETA"));
                    stmt.bindString(30,jsonObjectItem.getString("TELEFONO"));
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.MvendedorBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_ClienteBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Gem_ClienteBL {

    public ArrayList<S_Gem_ClienteBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Gem_ClienteBE s_gem_clienteBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Gem_ClienteBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_gem_clienteBE = new S_Gem_ClienteBE();
                    s_gem_clienteBE.setID_CLIENT(jsonObjectItem.getInt("ID_CLIENT"));
                    s_gem_clienteBE.setCODIGO(jsonObjectItem.getString("CODIGO"));
                    s_gem_clienteBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                    s_gem_clienteBE.setID_ZONA(jsonObjectItem.getInt("ID_ZONA"));
                    s_gem_clienteBE.setTIPO_GIRO(jsonObjectItem.getInt("TIPO_GIRO"));
                    s_gem_clienteBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    s_gem_clienteBE.setFAX(jsonObjectItem.getString("FAX"));
                    s_gem_clienteBE.setFECHA_INGRES(jsonObjectItem.getString("FECHA_INGRES"));
                    s_gem_clienteBE.setTIPO_CALIFICACION(jsonObjectItem.getInt("TIPO_CALIFICACION"));
                    s_gem_clienteBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_gem_clienteBE.setRUC(jsonObjectItem.getString("RUC"));
                    s_gem_clienteBE.setCLIENTE_AFECTO(jsonObjectItem.getInt("CLIENTE_AFECTO"));
                    s_gem_clienteBE.setUBICACION(jsonObjectItem.getString("UBICACION"));
                    s_gem_clienteBE.setDNI(jsonObjectItem.getString("DNI"));
                    s_gem_clienteBE.setDIA_VISITA(jsonObjectItem.getDouble("DIA_VISITA"));
                    s_gem_clienteBE.setFRC_VISITA(jsonObjectItem.getDouble("FRC_VISITA"));
                    s_gem_clienteBE.setFLAG_RETENCION(jsonObjectItem.getString("FLAG_RETENCION"));
                    s_gem_clienteBE.setCALIF_VTA(jsonObjectItem.getInt("CALIF_VTA"));
                    s_gem_clienteBE.setCALIF_CRED(jsonObjectItem.getInt("CALIF_CRED"));
                    s_gem_clienteBE.setPLAZO_PAGO(jsonObjectItem.getDouble("PLAZO_PAGO"));
                    s_gem_clienteBE.setID_CANAL(jsonObjectItem.getInt("ID_CANAL"));
                    s_gem_clienteBE.setCORREO(jsonObjectItem.getString("CORREO"));
                    s_gem_clienteBE.setID_NIVEL(jsonObjectItem.getInt("ID_NIVEL"));
                    s_gem_clienteBE.setID_FUERZA_VTA(jsonObjectItem.getInt("ID_FUERZA_VTA"));
                    s_gem_clienteBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    s_gem_clienteBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    s_gem_clienteBE.setCOND_PAGO(jsonObjectItem.getDouble("COND_PAGO"));
                    s_gem_clienteBE.setID_GRUPO(jsonObjectItem.getInt("ID_GRUPO"));
                    s_gem_clienteBE.setID_LISTA_DESCUENTO(jsonObjectItem.getInt("ID_LISTA_DESCUENTO"));
                    s_gem_clienteBE.setID_CANAL_DETALLE(jsonObjectItem.getInt("ID_CANAL_DETALLE"));
                    s_gem_clienteBE.setLIM_CRED(jsonObjectItem.getDouble("LIM_CRED"));
                    s_gem_clienteBE.setPROM_MAX_DIA_PAGO(jsonObjectItem.getDouble("PROM_MAX_DIA_PAGO"));
                    s_gem_clienteBE.setPORC_MAX_DEUDA(jsonObjectItem.getDouble("PORC_MAX_DEUDA"));
                    s_gem_clienteBE.setMONT_MAX_DEUDA(jsonObjectItem.getDouble("MONT_MAX_DEUDA"));
                    s_gem_clienteBE.setFLAG_PERCEPCION(jsonObjectItem.getInt("FLAG_PERCEPCION"));
                    s_gem_clienteBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_gem_clienteBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_gem_clienteBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_gem_clienteBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_gem_clienteBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_gem_clienteBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_gem_clienteBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_gem_clienteBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_gem_clienteBE.setCLIENTE_ESPECIAL(jsonObjectItem.getInt("CLIENTE_ESPECIAL"));
                    s_gem_clienteBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                    s_gem_clienteBE.setID_COBRADOR_EXT(jsonObjectItem.getInt("ID_COBRADOR_EXT"));
                    s_gem_clienteBE.setCUOTA_SEMANAL(jsonObjectItem.getInt("CUOTA_SEMANAL"));
                    s_gem_clienteBE.setCOD_ALM_CONSIG(jsonObjectItem.getString("COD_ALM_CONSIG"));
                    s_gem_clienteBE.setFLAG_FE(jsonObjectItem.getInt("FLAG_FE"));
                    s_gem_clienteBE.setLISTA_PRECIO(jsonObjectItem.getDouble("LISTA_PRECIO"));
                    lst.add(s_gem_clienteBE);
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
                DataBaseHelper.myDataBase.delete("S_GEM_CLIENTE", null, null);

                String SQL="INSERT OR REPLACE INTO S_GEM_CLIENTE(ID_CLIENTE, CODIGO, RAZON_SOCIAL,  RUC, CLIENTE_AFECTO, DNI, ID_CANAL, CORREO, COND_PAGO, ID_GRUPO, ID_LISTA_DESCUENTO, ID_CANAL_DETALLE, CLIENTE_ESPECIAL, COD_ALM_CONSIG)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("CODIGO"));
                    stmt.bindString(3,jsonObjectItem.getString("RAZON_SOCIAL"));
                    stmt.bindString(4,jsonObjectItem.getString("RUC"));
                    stmt.bindString(5,jsonObjectItem.getString("CLIENTE_AFECTO"));
                    stmt.bindString(6,jsonObjectItem.getString("DNI"));
                    stmt.bindString(7,jsonObjectItem.getString("ID_CANAL"));
                    stmt.bindString(8,jsonObjectItem.getString("CORREO"));
                    stmt.bindString(9,jsonObjectItem.getString("COND_PAGO"));
                    stmt.bindString(10,jsonObjectItem.getString("ID_GRUPO"));
                    stmt.bindString(11,jsonObjectItem.getString("ID_LISTA_DESCUENTO"));
                    stmt.bindString(12,jsonObjectItem.getString("ID_CANAL_DETALLE"));
                    stmt.bindString(13,jsonObjectItem.getString("CLIENTE_ESPECIAL"));
                    stmt.bindString(14,jsonObjectItem.getString("COD_ALM_CONSIG"));

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


    public JSONObject getSincronizarxCod(String newURL) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                //DataBaseHelper.myDataBase.delete("S_GEM_CLIENTE", null, null);

                String SQL="INSERT OR REPLACE INTO S_GEM_CLIENTE(ID_CLIENTE, CODIGO, RAZON_SOCIAL,  RUC, CLIENTE_AFECTO, DNI, ID_CANAL, CORREO, COND_PAGO, ID_GRUPO, ID_LISTA_DESCUENTO, ID_CANAL_DETALLE, CLIENTE_ESPECIAL, COD_ALM_CONSIG)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("ID_CLIENTE"));
                    stmt.bindString(2,jsonObjectItem.getString("CODIGO"));
                    stmt.bindString(3,jsonObjectItem.getString("RAZON_SOCIAL"));
                    stmt.bindString(4,jsonObjectItem.getString("RUC"));
                    stmt.bindString(5,jsonObjectItem.getString("CLIENTE_AFECTO"));
                    stmt.bindString(6,jsonObjectItem.getString("DNI"));
                    stmt.bindString(7,jsonObjectItem.getString("ID_CANAL"));
                    stmt.bindString(8,jsonObjectItem.getString("CORREO"));
                    stmt.bindString(9,jsonObjectItem.getString("COND_PAGO"));
                    stmt.bindString(10,jsonObjectItem.getString("ID_GRUPO"));
                    stmt.bindString(11,jsonObjectItem.getString("ID_LISTA_DESCUENTO"));
                    stmt.bindString(12,jsonObjectItem.getString("ID_CANAL_DETALLE"));
                    stmt.bindString(13,jsonObjectItem.getString("CLIENTE_ESPECIAL"));
                    stmt.bindString(14,jsonObjectItem.getString("COD_ALM_CONSIG"));

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

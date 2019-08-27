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

                String SQL="INSERT OR REPLACE INTO S_GEM_CLIENTE(" +
                        "ID_CLIENTE,CODIGO,RAZON_SOCIAL,ID_ZONA,TIPO_GIRO,TELEFONO," +
                        "FAX,FECHA_INGRES,TIPO_CALIFICACION,ESTADO,RUC,CLIENTE_AFECTO," +
                        "UBICACION,DNI,DIA_VISITA,FRC_VISITA,FLAG_RETENCION,CALIF_VTA," +
                        "CALIF_CRED,PLAZO_PAGO,ID_CANAL,CORREO,ID_NIVEL,ID_FUERZA_VTA," +
                        "ID_EMPRESA,ID_LOCAL,COND_PAGO,ID_GRUPO,ID_LISTA_DESCUENTO,ID_CANAL_DETALLE," +
                        "LIM_CRED,PROM_MAX_DIA_PAGO,PORC_MAX_DEUDA,MONT_MAX_DEUDA,FLAG_PERCEPCION,FECHA_REGISTRO," +
                        "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO," +
                        "IP_MODIFICACION,CLIENTE_ESPECIAL,ID_COBRADOR,ID_COBRADOR_EXT,CUOTA_SEMANAL,COD_ALM_CONSIG," +
                        "FLAG_FE,LISTA_PRECIO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?)";

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
                    stmt.bindString(4,jsonObjectItem.getString("ID_ZONA"));
                    stmt.bindString(5,jsonObjectItem.getString("TIPO_GIRO"));
                    stmt.bindString(6,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(7,jsonObjectItem.getString("FAX"));
                    stmt.bindString(8,jsonObjectItem.getString("FECHA_INGRES"));
                    stmt.bindString(9,jsonObjectItem.getString("TIPO_CALIFICACION"));
                    stmt.bindString(10,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(11,jsonObjectItem.getString("RUC"));
                    stmt.bindString(12,jsonObjectItem.getString("CLIENTE_AFECTO"));
                    stmt.bindString(13,jsonObjectItem.getString("UBICACION"));
                    stmt.bindString(14,jsonObjectItem.getString("DNI"));
                    stmt.bindString(15,jsonObjectItem.getString("DIA_VISITA"));
                    stmt.bindString(16,jsonObjectItem.getString("FRC_VISITA"));
                    stmt.bindString(17,jsonObjectItem.getString("FLAG_RETENCION"));
                    stmt.bindString(18,jsonObjectItem.getString("CALIF_VTA"));
                    stmt.bindString(19,jsonObjectItem.getString("CALIF_CRED"));
                    stmt.bindString(20,jsonObjectItem.getString("PLAZO_PAGO"));
                    stmt.bindString(21,jsonObjectItem.getString("ID_CANAL"));
                    stmt.bindString(22,jsonObjectItem.getString("CORREO"));
                    stmt.bindString(23,jsonObjectItem.getString("ID_NIVEL"));
                    stmt.bindString(24,jsonObjectItem.getString("ID_FUERZA_VTA"));
                    stmt.bindString(25,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(26,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(27,jsonObjectItem.getString("COND_PAGO"));
                    stmt.bindString(28,jsonObjectItem.getString("ID_GRUPO"));
                    stmt.bindString(29,jsonObjectItem.getString("ID_LISTA_DESCUENTO"));
                    stmt.bindString(30,jsonObjectItem.getString("ID_CANAL_DETALLE"));
                    stmt.bindString(31,jsonObjectItem.getString("LIM_CRED"));
                    stmt.bindString(32,jsonObjectItem.getString("PROM_MAX_DIA_PAGO"));
                    stmt.bindString(33,jsonObjectItem.getString("PORC_MAX_DEUDA"));
                    stmt.bindString(34,jsonObjectItem.getString("MONT_MAX_DEUDA"));
                    stmt.bindString(35,jsonObjectItem.getString("FLAG_PERCEPCION"));
                    stmt.bindString(36,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(37,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(38,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(39,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(40,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(41,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(42,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(43,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(44,jsonObjectItem.getString("CLIENTE_ESPECIAL"));
                    stmt.bindString(45,jsonObjectItem.getString("ID_COBRADOR"));
                    stmt.bindString(46,jsonObjectItem.getString("ID_COBRADOR_EXT"));
                    stmt.bindString(47,jsonObjectItem.getString("CUOTA_SEMANAL"));
                    stmt.bindString(48,jsonObjectItem.getString("COD_ALM_CONSIG"));
                    stmt.bindString(49,jsonObjectItem.getString("FLAG_FE"));
                    stmt.bindString(50,jsonObjectItem.getString("LISTA_PRECIO"));
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

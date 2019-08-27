package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Sea_AccesosBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Sea_AccesosBL {
    public ArrayList<S_Sea_AccesosBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        S_Sea_AccesosBE s_sea_accesosBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<S_Sea_AccesosBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    s_sea_accesosBE = new S_Sea_AccesosBE();
                    s_sea_accesosBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    s_sea_accesosBE.setC_MENU(jsonObjectItem.getString("C_MENU"));
                    s_sea_accesosBE.setVISIBLE(jsonObjectItem.getString("VISIBLE"));
                    s_sea_accesosBE.setNUEVO(jsonObjectItem.getInt("NUEVO"));
                    s_sea_accesosBE.setEDITAR(jsonObjectItem.getInt("EDITAR"));
                    s_sea_accesosBE.setGRABAR(jsonObjectItem.getInt("GRABAR"));
                    s_sea_accesosBE.setBUSCAR(jsonObjectItem.getInt("BUSCAR"));
                    s_sea_accesosBE.setANULAR(jsonObjectItem.getInt("ANULAR"));
                    s_sea_accesosBE.setENVIAR(jsonObjectItem.getInt("ENVIAR"));
                    s_sea_accesosBE.setAGREGAR(jsonObjectItem.getInt("AGREGAR"));
                    s_sea_accesosBE.setELIMINAR(jsonObjectItem.getInt("ELIMINAR"));
                    s_sea_accesosBE.setEXPORTAR(jsonObjectItem.getInt("EXPORTAR"));
                    s_sea_accesosBE.setIMPRIMIR(jsonObjectItem.getInt("IMPRIMIR"));
                    s_sea_accesosBE.setCERRAR(jsonObjectItem.getInt("CERRAR"));
                    s_sea_accesosBE.setEX_WORD(jsonObjectItem.getInt("EX_WORD"));
                    s_sea_accesosBE.setEX_EXCEL(jsonObjectItem.getInt("EX_EXCEL"));
                    s_sea_accesosBE.setEX_PDF(jsonObjectItem.getInt("EX_PDF"));
                    s_sea_accesosBE.setEMAIL(jsonObjectItem.getInt("EMAIL"));
                    s_sea_accesosBE.setICONO(jsonObjectItem.getInt("ICONO"));
                    s_sea_accesosBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    s_sea_accesosBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    s_sea_accesosBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    s_sea_accesosBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    s_sea_accesosBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    s_sea_accesosBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    s_sea_accesosBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    s_sea_accesosBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    s_sea_accesosBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    s_sea_accesosBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    lst.add(s_sea_accesosBE);
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
                DataBaseHelper.myDataBase.delete("S_SEA_ACCESOS", null, null);
                String SQL="INSERT OR REPLACE INTO S_SEA_ACCESOS(" +
                        "C_PERFIL,C_MENU,VISIBLE,NUEVO,EDITAR,GRABAR," +
                        "BUSCAR,ANULAR,ENVIAR,AGREGAR,ELIMINAR,EXPORTAR," +
                        "IMPRIMIR,CERRAR,EX_WORD,EX_EXCEL,EX_PDF,EMAIL," +
                        "ICONO,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO," +
                        "IP_MODIFICACION,FECHA_REGISTRO,FECHA_MODIFICACION,ESTADO,ID_EMPRESA)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(2,jsonObjectItem.getString("C_MENU"));
                    stmt.bindString(3,jsonObjectItem.getString("VISIBLE"));
                    stmt.bindString(4,jsonObjectItem.getString("NUEVO"));
                    stmt.bindString(5,jsonObjectItem.getString("EDITAR"));
                    stmt.bindString(6,jsonObjectItem.getString("GRABAR"));
                    stmt.bindString(7,jsonObjectItem.getString("BUSCAR"));
                    stmt.bindString(8,jsonObjectItem.getString("ANULAR"));
                    stmt.bindString(9,jsonObjectItem.getString("ENVIAR"));
                    stmt.bindString(10,jsonObjectItem.getString("AGREGAR"));
                    stmt.bindString(11,jsonObjectItem.getString("ELIMINAR"));
                    stmt.bindString(12,jsonObjectItem.getString("EXPORTAR"));
                    stmt.bindString(13,jsonObjectItem.getString("IMPRIMIR"));
                    stmt.bindString(14,jsonObjectItem.getString("CERRAR"));
                    stmt.bindString(15,jsonObjectItem.getString("EX_WORD"));
                    stmt.bindString(16,jsonObjectItem.getString("EX_EXCEL"));
                    stmt.bindString(17,jsonObjectItem.getString("EX_PDF"));
                    stmt.bindString(18,jsonObjectItem.getString("EMAIL"));
                    stmt.bindString(19,jsonObjectItem.getString("ICONO"));
                    stmt.bindString(20,jsonObjectItem.getString("USUARIO_REGISTRO"));
                    stmt.bindString(21,jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    stmt.bindString(22,jsonObjectItem.getString("PC_REGISTRO"));
                    stmt.bindString(23,jsonObjectItem.getString("PC_MODIFICACION"));
                    stmt.bindString(24,jsonObjectItem.getString("IP_REGISTRO"));
                    stmt.bindString(25,jsonObjectItem.getString("IP_MODIFICACION"));
                    stmt.bindString(26,jsonObjectItem.getString("FECHA_REGISTRO"));
                    stmt.bindString(27,jsonObjectItem.getString("FECHA_MODIFICACION"));
                    stmt.bindString(28,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(29,jsonObjectItem.getString("ID_EMPRESA"));
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

package pe.com.app.unibell.appunibell.BL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.DAO.SistemaDAO;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Documentos_Cobra_DetBL {
    public ArrayList<Documentos_Cobra_DetBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Documentos_Cobra_DetBE documentos_cobra_detBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_DetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    documentos_cobra_detBE = new Documentos_Cobra_DetBE();
                    documentos_cobra_detBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                    documentos_cobra_detBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    documentos_cobra_detBE.setTIPDOC(jsonObjectItem.getString("TIPDOC"));
                    documentos_cobra_detBE.setSERIE_NUM(jsonObjectItem.getString("SERIE_NUM"));
                    documentos_cobra_detBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    documentos_cobra_detBE.setIMPORTE(jsonObjectItem.getDouble("IMPORTE"));
                    documentos_cobra_detBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    documentos_cobra_detBE.setSALDO(jsonObjectItem.getDouble("SALDO"));
                    documentos_cobra_detBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                    documentos_cobra_detBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));
                    documentos_cobra_detBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    documentos_cobra_detBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    documentos_cobra_detBE.setFECHA_REGISTRO(jsonObjectItem.getString("FECHA_REGISTRO"));
                    documentos_cobra_detBE.setFECHA_MODIFICACION(jsonObjectItem.getString("FECHA_MODIFICACION"));
                    documentos_cobra_detBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    documentos_cobra_detBE.setUSUARIO_MODIFICACION(jsonObjectItem.getString("USUARIO_MODIFICACION"));
                    documentos_cobra_detBE.setPC_REGISTRO(jsonObjectItem.getString("PC_REGISTRO"));
                    documentos_cobra_detBE.setPC_MODIFICACION(jsonObjectItem.getString("PC_MODIFICACION"));
                    documentos_cobra_detBE.setIP_REGISTRO(jsonObjectItem.getString("IP_REGISTRO"));
                    documentos_cobra_detBE.setIP_MODIFICACION(jsonObjectItem.getString("IP_MODIFICACION"));
                    documentos_cobra_detBE.setID_VENDEDOR(jsonObjectItem.getInt("ID_VENDEDOR"));
                    documentos_cobra_detBE.setSALDO_INICIAL(jsonObjectItem.getDouble("SALDO_INICIAL"));
                    documentos_cobra_detBE.setVOUCHER(jsonObjectItem.getString("VOUCHER"));
                    lst.add(documentos_cobra_detBE);
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

    public JSONObject getSincronizar(String newURL,String sOpcionCobranza) {
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Documentos_Cobra_DetBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{

                //Eliminamos los registros
                if(sOpcionCobranza.equals("0")) {
                    DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET", null, null);
                }

                String SQL="INSERT OR REPLACE INTO S_CCM_DOCUMENTOS_COBRA_DET(" +
                        "ID_COBRANZA,FPAGO,TIPDOC,SERIE_NUM,NUMERO,IMPORTE," +
                        "MONEDA,SALDO,M_COBRANZA,ID_EMPRESA,ID_LOCAL,ESTADO,ID_VENDEDOR,SALDO_INICIAL,VOUCHER,GUARDADO)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("ID_COBRANZA"));
                    stmt.bindString(2,jsonObjectItem.getString("FPAGO"));
                    stmt.bindString(3,jsonObjectItem.getString("TIPDOC"));
                    stmt.bindString(4,jsonObjectItem.getString("SERIE_NUM"));
                    stmt.bindString(5,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(6,jsonObjectItem.getString("IMPORTE"));
                    stmt.bindString(7,jsonObjectItem.getString("MONEDA"));
                    stmt.bindString(8,jsonObjectItem.getString("SALDO"));
                    stmt.bindString(9,jsonObjectItem.getString("M_COBRANZA"));
                    stmt.bindString(10,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(11,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(12,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(13,jsonObjectItem.getString("ID_VENDEDOR"));
                    stmt.bindString(14,jsonObjectItem.getString("SALDO_INICIAL"));
                    stmt.bindString(15,jsonObjectItem.getString("VOUCHER"));
                    stmt.bindString(16,"1");
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

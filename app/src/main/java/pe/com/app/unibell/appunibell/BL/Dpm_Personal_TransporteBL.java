package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Dpm_Personal_TransporteBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Dpm_Personal_TransporteBL {


    public ArrayList<Dpm_Personal_TransporteBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Dpm_Personal_TransporteBE dpm_personal_transporteBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Dpm_Personal_TransporteBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    dpm_personal_transporteBE = new Dpm_Personal_TransporteBE();
                    dpm_personal_transporteBE.setC_PERTRANS(jsonObjectItem.getString("C_PERTRANS"));
                    dpm_personal_transporteBE.setC_EMPTRANS(jsonObjectItem.getString("C_EMPTRANS"));
                    dpm_personal_transporteBE.setAPE_PATERNO(jsonObjectItem.getString("APE_PATERNO"));
                    dpm_personal_transporteBE.setAPE_MATERNO(jsonObjectItem.getString("APE_MATERNO"));
                    dpm_personal_transporteBE.setNOMBRES(jsonObjectItem.getString("NOMBRES"));
                    dpm_personal_transporteBE.setRUC(jsonObjectItem.getString("RUC"));
                    dpm_personal_transporteBE.setTIPODOCID(jsonObjectItem.getString("TIPODOCID"));
                    dpm_personal_transporteBE.setDOCIDENT(jsonObjectItem.getString("DOCIDENT"));
                    dpm_personal_transporteBE.setNRO_BREVETE(jsonObjectItem.getString("NRO_BREVETE"));
                    dpm_personal_transporteBE.setDOMICILIO(jsonObjectItem.getString("DOMICILIO"));
                    dpm_personal_transporteBE.setFLG_CHOFER(jsonObjectItem.getInt("FLG_CHOFER"));
                    dpm_personal_transporteBE.setFLG_REPART(jsonObjectItem.getInt("FLG_REPART"));
                    dpm_personal_transporteBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    dpm_personal_transporteBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    dpm_personal_transporteBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    dpm_personal_transporteBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    dpm_personal_transporteBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
                    dpm_personal_transporteBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
                    dpm_personal_transporteBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
                    dpm_personal_transporteBE.setC_ESTADO(jsonObjectItem.getString("C_ESTADO"));
                    dpm_personal_transporteBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
                    dpm_personal_transporteBE.setANULADO(jsonObjectItem.getString("ANULADO"));
                    dpm_personal_transporteBE.setFLG_CHEQUEADOR(jsonObjectItem.getInt("FLG_CHEQUEADOR"));
                    dpm_personal_transporteBE.setFLG_CONTROLADOR(jsonObjectItem.getInt("FLG_CONTROLADOR"));
                    dpm_personal_transporteBE.setFLG_DESPACHADOR(jsonObjectItem.getInt("FLG_DESPACHADOR"));
                    dpm_personal_transporteBE.setFLG_ARMADOR(jsonObjectItem.getInt("FLG_ARMADOR"));
                    dpm_personal_transporteBE.setTELEFONO(jsonObjectItem.getString("TELEFONO"));
                    dpm_personal_transporteBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    dpm_personal_transporteBE.setFNATAL(jsonObjectItem.getString("FNATAL"));
                    dpm_personal_transporteBE.setFLAG_SCTR(jsonObjectItem.getString("FLAG_SCTR"));
                    dpm_personal_transporteBE.setC_NIVEL_RIEZGO(jsonObjectItem.getInt("C_NIVEL_RIEZGO"));
                    dpm_personal_transporteBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));


                    lst.add(dpm_personal_transporteBE);
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
            lst=new ArrayList<Dpm_Personal_TransporteBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("DPM_PERSONAL_TRANSPORTE", null, null);

                String SQL="INSERT OR REPLACE INTO DPM_PERSONAL_TRANSPORTE( " +
                        " C_PERTRANS, C_EMPTRANS, APE_PATERNO, APE_MATERNO, NOMBRES, RUC, TIPODOCID, DOCIDENT, NRO_BREVETE, DOMICILIO, FLG_CHOFER, FLG_REPART, C_USUARIO, C_PERFIL, C_CPU, FEC_REG, C_USUARIO_MOD, C_PERFIL_MOD, FEC_MOD, C_ESTADO, C_CPU_MOD, ANULADO, FLG_CHEQUEADOR, FLG_CONTROLADOR, FLG_DESPACHADOR, FLG_ARMADOR, TELEFONO, C_SUC_EMP, FNATAL, FLAG_SCTR, C_NIVEL_RIEZGO, ID_EMPRESA) "+
                        " VALUES " +
                        " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";


                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                    stmt.bindString(1,jsonObjectItem.getString("C_PERTRANS"));
                    stmt.bindString(2,jsonObjectItem.getString("C_EMPTRANS"));
                    stmt.bindString(3,jsonObjectItem.getString("APE_PATERNO"));
                    stmt.bindString(4,jsonObjectItem.getString("APE_MATERNO"));
                    stmt.bindString(5,jsonObjectItem.getString("NOMBRES"));
                    stmt.bindString(6,jsonObjectItem.getString("RUC"));
                    stmt.bindString(7,jsonObjectItem.getString("TIPODOCID"));
                    stmt.bindString(8,jsonObjectItem.getString("DOCIDENT"));
                    stmt.bindString(9,jsonObjectItem.getString("NRO_BREVETE"));
                    stmt.bindString(10,jsonObjectItem.getString("DOMICILIO"));
                    stmt.bindString(11,jsonObjectItem.getString("FLG_CHOFER"));
                    stmt.bindString(12,jsonObjectItem.getString("FLG_REPART"));
                    stmt.bindString(13,jsonObjectItem.getString("C_USUARIO"));
                    stmt.bindString(14,jsonObjectItem.getString("C_PERFIL"));
                    stmt.bindString(15,jsonObjectItem.getString("C_CPU"));
                    stmt.bindString(16,jsonObjectItem.getString("FEC_REG"));
                    stmt.bindString(17,jsonObjectItem.getString("C_USUARIO_MOD"));
                    stmt.bindString(18,jsonObjectItem.getString("C_PERFIL_MOD"));
                    stmt.bindString(19,jsonObjectItem.getString("FEC_MOD"));
                    stmt.bindString(20,jsonObjectItem.getString("C_ESTADO"));
                    stmt.bindString(21,jsonObjectItem.getString("C_CPU_MOD"));
                    stmt.bindString(22,jsonObjectItem.getString("ANULADO"));
                    stmt.bindString(23,jsonObjectItem.getString("FLG_CHEQUEADOR"));
                    stmt.bindString(24,jsonObjectItem.getString("FLG_CONTROLADOR"));
                    stmt.bindString(25,jsonObjectItem.getString("FLG_DESPACHADOR"));
                    stmt.bindString(26,jsonObjectItem.getString("FLG_ARMADOR"));
                    stmt.bindString(27,jsonObjectItem.getString("TELEFONO"));
                    stmt.bindString(28,jsonObjectItem.getString("C_SUC_EMP"));
                    stmt.bindString(29,jsonObjectItem.getString("FNATAL"));
                    stmt.bindString(30,jsonObjectItem.getString("FLAG_SCTR"));
                    stmt.bindString(31,jsonObjectItem.getString("C_NIVEL_RIEZGO"));
                    stmt.bindString(32,jsonObjectItem.getString("ID_EMPRESA"));

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

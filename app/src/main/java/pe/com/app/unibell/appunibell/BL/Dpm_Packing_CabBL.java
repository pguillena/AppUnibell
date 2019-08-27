package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Dpm_Packing_CabBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Dpm_Packing_CabBL {
    public ArrayList<Dpm_Packing_CabBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        Dpm_Packing_CabBE dpm_packing_cabBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<Dpm_Packing_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    dpm_packing_cabBE = new Dpm_Packing_CabBE();
                    dpm_packing_cabBE.setC_PACKING(jsonObjectItem.getInt("C_PACKING"));
                    dpm_packing_cabBE.setF_PACKING(jsonObjectItem.getString("F_PACKING"));
                    dpm_packing_cabBE.setF_SALIDA(jsonObjectItem.getString("F_SALIDA"));
                    dpm_packing_cabBE.setF_RETORNO(jsonObjectItem.getString("F_RETORNO"));
                    dpm_packing_cabBE.setC_EMPTRANS(jsonObjectItem.getString("C_EMPTRANS"));
                    dpm_packing_cabBE.setC_VEHICULO(jsonObjectItem.getString("C_VEHICULO"));
                    dpm_packing_cabBE.setC_CHOFER(jsonObjectItem.getString("C_CHOFER"));
                    dpm_packing_cabBE.setC_REPARTIDOR(jsonObjectItem.getString("C_REPARTIDOR"));
                    dpm_packing_cabBE.setPLACA(jsonObjectItem.getString("PLACA"));
                    dpm_packing_cabBE.setHORA_SALIDA(jsonObjectItem.getString("HORA_SALIDA"));
                    dpm_packing_cabBE.setHORA_RETORNO(jsonObjectItem.getString("HORA_RETORNO"));
                    dpm_packing_cabBE.setOBSERVACION(jsonObjectItem.getString("OBSERVACION"));
                    dpm_packing_cabBE.setF_CIERRE(jsonObjectItem.getString("F_CIERRE"));
                    dpm_packing_cabBE.setC_USUARIO_CIE(jsonObjectItem.getString("C_USUARIO_CIE"));
                    dpm_packing_cabBE.setC_USUARIO(jsonObjectItem.getString("C_USUARIO"));
                    dpm_packing_cabBE.setC_PERFIL(jsonObjectItem.getString("C_PERFIL"));
                    dpm_packing_cabBE.setC_CPU(jsonObjectItem.getString("C_CPU"));
                    dpm_packing_cabBE.setFEC_REG(jsonObjectItem.getString("FEC_REG"));
                    dpm_packing_cabBE.setC_USUARIO_MOD(jsonObjectItem.getString("C_USUARIO_MOD"));
                    dpm_packing_cabBE.setC_PERFIL_MOD(jsonObjectItem.getString("C_PERFIL_MOD"));
                    dpm_packing_cabBE.setFEC_MOD(jsonObjectItem.getString("FEC_MOD"));
                    dpm_packing_cabBE.setC_ESTADO(jsonObjectItem.getString("C_ESTADO"));
                    dpm_packing_cabBE.setC_CPU_MOD(jsonObjectItem.getString("C_CPU_MOD"));
                    dpm_packing_cabBE.setANULADO(jsonObjectItem.getString("ANULADO"));
                    dpm_packing_cabBE.setC_DESPACHADOR(jsonObjectItem.getString("C_DESPACHADOR"));
                    dpm_packing_cabBE.setC_CHEQUEADOR(jsonObjectItem.getString("C_CHEQUEADOR"));
                    dpm_packing_cabBE.setC_CONTROLADOR(jsonObjectItem.getString("C_CONTROLADOR"));
                    dpm_packing_cabBE.setC_USUARIO_RECCRE(jsonObjectItem.getString("C_USUARIO_RECCRE"));
                    dpm_packing_cabBE.setC_PERFIL_RECCRE(jsonObjectItem.getString("C_PERFIL_RECCRE"));
                    dpm_packing_cabBE.setC_CPU_RECCRE(jsonObjectItem.getString("C_CPU_RECCRE"));
                    dpm_packing_cabBE.setF_REGISTRO_RECCRE(jsonObjectItem.getString("F_REGISTRO_RECCRE"));
                    dpm_packing_cabBE.setI_VERIFICA_RECCRE(jsonObjectItem.getString("I_VERIFICA_RECCRE"));
                    dpm_packing_cabBE.setC_TUSUARIO_RECCRE(jsonObjectItem.getString("C_TUSUARIO_RECCRE"));
                    dpm_packing_cabBE.setC_TPERFIL_RECCRE(jsonObjectItem.getString("C_TPERFIL_RECCRE"));
                    dpm_packing_cabBE.setC_TCPU_RECCRE(jsonObjectItem.getString("C_TCPU_RECCRE"));
                    dpm_packing_cabBE.setF_TREGISTRO_RECCRE(jsonObjectItem.getString("F_TREGISTRO_RECCRE"));
                    dpm_packing_cabBE.setI_TVERIFICA_RECCRE(jsonObjectItem.getString("I_TVERIFICA_RECCRE"));
                    dpm_packing_cabBE.setID_LOCAL(jsonObjectItem.getInt("ID_LOCAL"));
                    lst.add(dpm_packing_cabBE);
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
            lst=new ArrayList<Dpm_Packing_CabBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("DPM_PACKING_CAB", null, null);

                String SQL="INSERT OR REPLACE INTO DPM_PACKING_CAB(" +
                        "C_PACKING,F_PACKING,F_SALIDA,F_RETORNO,C_EMPTRANS,C_VEHICULO," +
                        "C_CHOFER,C_REPARTIDOR,PLACA,HORA_SALIDA,HORA_RETORNO,OBSERVACION," +
                        "F_CIERRE,C_USUARIO_CIE,C_USUARIO,C_PERFIL,C_CPU,FEC_REG," +
                        "C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD,C_ESTADO,C_CPU_MOD,ANULADO," +
                        "C_DESPACHADOR,C_CHEQUEADOR,C_CONTROLADOR,C_USUARIO_RECCRE,C_PERFIL_RECCRE,C_CPU_RECCRE," +
                        "F_REGISTRO_RECCRE,I_VERIFICA_RECCRE,C_TUSUARIO_RECCRE,C_TPERFIL_RECCRE,C_TCPU_RECCRE,F_TREGISTRO_RECCRE," +
                        "I_TVERIFICA_RECCRE,ID_LOCAL)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                         "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);

                     stmt.bindString(1,jsonObjectItem.getString("C_PACKING"));
                     stmt.bindString(2,jsonObjectItem.getString("F_PACKING"));
                     stmt.bindString(3,jsonObjectItem.getString("F_SALIDA"));
                     stmt.bindString(4,jsonObjectItem.getString("F_RETORNO"));
                     stmt.bindString(5,jsonObjectItem.getString("C_EMPTRANS"));
                     stmt.bindString(6,jsonObjectItem.getString("C_VEHICULO"));
                     stmt.bindString(7,jsonObjectItem.getString("C_CHOFER"));
                     stmt.bindString(8,jsonObjectItem.getString("C_REPARTIDOR"));
                     stmt.bindString(9,jsonObjectItem.getString("PLACA"));
                     stmt.bindString(10,jsonObjectItem.getString("HORA_SALIDA"));
                     stmt.bindString(11,jsonObjectItem.getString("HORA_RETORNO"));
                     stmt.bindString(12,jsonObjectItem.getString("OBSERVACION"));
                     stmt.bindString(13,jsonObjectItem.getString("F_CIERRE"));
                     stmt.bindString(14,jsonObjectItem.getString("C_USUARIO_CIE"));
                     stmt.bindString(15,jsonObjectItem.getString("C_USUARIO"));
                     stmt.bindString(16,jsonObjectItem.getString("C_PERFIL"));
                     stmt.bindString(17,jsonObjectItem.getString("C_CPU"));
                     stmt.bindString(18,jsonObjectItem.getString("FEC_REG"));
                     stmt.bindString(19,jsonObjectItem.getString("C_USUARIO_MOD"));
                     stmt.bindString(20,jsonObjectItem.getString("C_PERFIL_MOD"));
                     stmt.bindString(21,jsonObjectItem.getString("FEC_MOD"));
                     stmt.bindString(22,jsonObjectItem.getString("C_ESTADO"));
                     stmt.bindString(23,jsonObjectItem.getString("C_CPU_MOD"));
                     stmt.bindString(24,jsonObjectItem.getString("ANULADO"));
                     stmt.bindString(25,jsonObjectItem.getString("C_DESPACHADOR"));
                     stmt.bindString(26,jsonObjectItem.getString("C_CHEQUEADOR"));
                     stmt.bindString(27,jsonObjectItem.getString("C_CONTROLADOR"));
                     stmt.bindString(28,jsonObjectItem.getString("C_USUARIO_RECCRE"));
                     stmt.bindString(29,jsonObjectItem.getString("C_PERFIL_RECCRE"));
                     stmt.bindString(30,jsonObjectItem.getString("C_CPU_RECCRE"));
                     stmt.bindString(31,jsonObjectItem.getString("F_REGISTRO_RECCRE"));
                     stmt.bindString(32,jsonObjectItem.getString("I_VERIFICA_RECCRE"));
                     stmt.bindString(33,jsonObjectItem.getString("C_TUSUARIO_RECCRE"));
                     stmt.bindString(34,jsonObjectItem.getString("C_TPERFIL_RECCRE"));
                     stmt.bindString(35,jsonObjectItem.getString("C_TCPU_RECCRE"));
                     stmt.bindString(36,jsonObjectItem.getString("F_TREGISTRO_RECCRE"));
                     stmt.bindString(37,jsonObjectItem.getString("I_TVERIFICA_RECCRE"));
                     stmt.bindString(38,jsonObjectItem.getString("ID_LOCAL"));
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


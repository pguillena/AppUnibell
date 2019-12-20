package pe.com.app.unibell.appunibell.BL;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Inv_InventarioBL {

    public JSONObject InsertRest(String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        String SQL=
                "SELECT CONTEO, CODIGO_BARRA, UBICACION, MES, ANIO, CANTIDAD, COD_ART, DESCRIPCION, ESTADO, " +
                        "FECHA_REGISTRO, FECHA_MODIFICACION, USUARIO_REGISTRO, USUARIO_MODIFICACION, " +
                        "PC_REGISTRO, PC_MODIFICACION, IP_REGISTRO, IP_MODIFICACION " +
                        "FROM S_INV_INVENTARIO";

        Cursor cursor = null;
        cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        try {
            JSONObject jsonObject = new JSONObject();
            if (cursor.moveToFirst()) {
                do {
                    jsonObject = new JSONObject();
                    jsonObject.accumulate("CONTEO", Funciones.isNullColumn(cursor,"CONTEO",""));
                    jsonObject.accumulate("CODIGO_BARRA", Funciones.isNullColumn(cursor,"CODIGO_BARRA",""));
                    jsonObject.accumulate("UBICACION", Funciones.isNullColumn(cursor,"UBICACION",""));
                    jsonObject.accumulate("MES", Funciones.isNullColumn(cursor,"MES",""));
                    jsonObject.accumulate("ANIO", Funciones.isNullColumn(cursor,"ANIO",""));
                    jsonObject.accumulate("CANTIDAD", Funciones.isNullColumn(cursor,"CANTIDAD",""));
                    jsonObject.accumulate("COD_ART", Funciones.isNullColumn(cursor,"COD_ART",""));
                    jsonObject.accumulate("DESCRIPCION", Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));

                    String aux = new RestClientLibrary().post(newURL,jsonObject);

                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getString("status").trim()));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getString("status").trim().equals("0") || jsonObjectRest.getString("status").trim().equals("false")) {
                    } else {

                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        String sMensaje= jsonObjectItem.getString("MSG");
                        jsonObjectResult.accumulate("MSG",sMensaje);

                      /*  if(!sID_EMPRESA.equals("null") && !sID_EMPRESA.equals("0")){
                            String sID_LOCAL=jsonObjectItem.getString("ID_LOCAL");
                            String sID_VENDEDOR= jsonObjectItem.getString("ID_VENDEDOR");
                            String sID_COBRANZA_LOCAL= jsonObjectItem.getString("ID_COBRANZA");
                            String sID_COBRANZA_ORACLE=jsonObjectItem.getString("ID_COBRANZA_ORACLE");
                            String MSG=jsonObjectItem.getString("MSG");
                            jsonObjectResult.accumulate("MSG",MSG);

                            //SI NO SALTO LAS VALIDACIONES DE LA BD ENTONCES
                            if(MSG.toString().trim().equals("-")){



                            }
                        }
*/
                    }
                } while (cursor.moveToNext());
            }
        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", "1");
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }






}

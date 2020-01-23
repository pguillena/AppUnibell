package pe.com.app.unibell.appunibell.BL;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class S_Inv_InventarioBL {

    public JSONObject InsertRest(String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        String sMensaje= "";
        String sConteo = "";

        String SQL=
                "SELECT CONTEO, CODIGO_BARRA, UBICACION, MES, ANIO, CANTIDAD, COD_ART, DESCRIPCION, ESTADO, " +
                        "FECHA_REGISTRO, FECHA_MODIFICACION, USUARIO_REGISTRO, USUARIO_MODIFICACION, " +
                        "PC_REGISTRO, PC_MODIFICACION, IP_REGISTRO, IP_MODIFICACION, COD_ALM " +
                        " FROM S_INV_INVENTARIO " +
                        " WHERE ESTADO <> 40013";

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
                   // jsonObject.accumulate("DESCRIPCION", Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    jsonObject.accumulate("DESCRIPCION",  URLEncoder.encode(Funciones.isNullColumn(cursor,"DESCRIPCION",""), "UTF-8"));
                    jsonObject.accumulate("ESTADO", Funciones.isNullColumn(cursor,"ESTADO",""));
                    jsonObject.accumulate("FECHA_REGISTRO", Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    jsonObject.accumulate("FECHA_MODIFICACION", Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    jsonObject.accumulate("USUARIO_REGISTRO", Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    jsonObject.accumulate("USUARIO_MODIFICACION", Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    jsonObject.accumulate("PC_REGISTRO", Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    jsonObject.accumulate("PC_MODIFICACION", Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    jsonObject.accumulate("IP_REGISTRO", Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    jsonObject.accumulate("IP_MODIFICACION", Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    jsonObject.accumulate("COD_ALM", Funciones.isNullColumn(cursor,"COD_ALM",""));

                    String aux = new RestClientLibrary().post(newURL,jsonObject);

                    jsonObjectRest = new JSONObject(aux);
                    jsonObjectResult.accumulate("status", String.valueOf(jsonObjectRest.getString("status").trim()));
                    jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

                    if (jsonObjectRest.getString("status").trim().equals("0") || jsonObjectRest.getString("status").trim().equals("false")) {
                        sMensaje =  jsonObjectRest.getString("message").trim();
                       // JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                        break;
                    } else {

                        JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(0);
                         sMensaje = jsonObjectItem.getString("MSG");
                         sConteo  = jsonObjectItem.getString("CONTEO");

                    }
                }
                while(cursor.moveToNext());
                jsonObjectResult.accumulate("MSG",sMensaje);
                jsonObjectResult.accumulate("CONTEO",sConteo);
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

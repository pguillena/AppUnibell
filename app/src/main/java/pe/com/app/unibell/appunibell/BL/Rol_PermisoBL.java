package pe.com.app.unibell.appunibell.BL;

import org.json.JSONException;
import org.json.JSONObject;

import pe.com.app.unibell.appunibell.BE.Rol_PermisoBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

/**
 * Created by rgalvez on 22/11/2016.
 */
public class Rol_PermisoBL {

    public JSONObject insert_Rest(Rol_PermisoBE rol_permisoBE, Integer iEvento, String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("ROL_CORREL", rol_permisoBE.getROL_CORREL());
            jsonObject.accumulate("ROL_NOMBRE", rol_permisoBE.getROL_NOMBRE());
            jsonObject.accumulate("ROL_FCION", rol_permisoBE.getROL_FCION());
            jsonObject.accumulate("ROL_VSIBL", rol_permisoBE.getROL_VSIBL());
            jsonObject.accumulate("ROL_ADMIN", rol_permisoBE.getROL_ADMIN());
            String aux ="";
            if(iEvento==0) {
                aux = new RestClientLibrary().post(newURL,jsonObject);
            }else {
                aux = new RestClientLibrary().put(newURL,jsonObject);
            }
            jsonObjectRest = new JSONObject(aux);

            jsonObjectResult.accumulate("status", jsonObjectRest.getInt("status"));
            jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));
        }catch(Exception e){
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

    public JSONObject deleteRest(String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try{
            String aux = new RestClientLibrary().del(newURL);
            jsonObjectRest = new JSONObject(aux);
            jsonObjectResult.accumulate("status", jsonObjectRest.getInt("status"));
            jsonObjectResult.accumulate("message", jsonObjectRest.getString("message"));

        }catch(Exception e){
            e.printStackTrace();
            try {
                jsonObjectResult.accumulate("status", false);
                jsonObjectResult.accumulate("message", e.getMessage());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return jsonObjectResult;
    }



}

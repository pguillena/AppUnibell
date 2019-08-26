package pe.com.app.unibell.appunibell.BL;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.UsuarioBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

/**
 * Created by rgalvez on 22/11/2016.
 */
public class UsuarioBL {
    public ArrayList<UsuarioBE> lst =null;

    public JSONObject getAllRest(String newURL){
        UsuarioBE usuarioBE=null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try{
            lst=new ArrayList<UsuarioBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1){
            }else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    //CREAMOS UN JSONOBJECT PARA CONTENER EL ITEM DEL JSONARRAY QUE ES UN JSONOBJECT
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    //CREAMOS UN BE
                    usuarioBE=new UsuarioBE();
                    usuarioBE.setUSU_CORREL(jsonObjectItem.getInt("USU_CORREL"));
                    usuarioBE.setUSU_NOMBRE(jsonObjectItem.getString("USU_NOMBRE"));
                    usuarioBE.setUSU_PASSWO(jsonObjectItem.getString("USU_PASSWO"));
                    usuarioBE.setROL_CORREL(jsonObjectItem.getInt("ROL_CORREL"));
                    usuarioBE.setUSU_ESTADO(jsonObjectItem.getInt("USU_ESTADO"));
                    usuarioBE.setTIU_NOMBRE(jsonObjectItem.getString("TIU_NOMBRE"));
                    lst.add(usuarioBE);
                }
            }
            //CREAMOS UN JSON PARA MOSTRAR EL STATUS Y MESSAGE
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

    public JSONObject insert_UpdateRest(UsuarioBE usuarioBE, Integer iEvento, String newURL){
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("USU_CORREL", usuarioBE.getUSU_CORREL());
            jsonObject.accumulate("USU_NOMBRE", usuarioBE.getUSU_NOMBRE());
            jsonObject.accumulate("USU_PASSWO", usuarioBE.getUSU_PASSWO());
            jsonObject.accumulate("ROL_CORREL", usuarioBE.getROL_CORREL());
            jsonObject.accumulate("USU_ESTADO", usuarioBE.getUSU_ESTADO());
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
}


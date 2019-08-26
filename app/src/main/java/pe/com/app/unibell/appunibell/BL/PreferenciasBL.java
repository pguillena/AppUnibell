package pe.com.app.unibell.appunibell.BL;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.PreferenciasBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

/**
 * Created by rgalvez on 18/08/2017.
 */

public class PreferenciasBL {
    public ArrayList<PreferenciasBE> lsPreferencias =null;

    public JSONObject getAllRest(String newURL){
        PreferenciasBE preferenciasBE=null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try{
            lsPreferencias=new ArrayList<PreferenciasBE>();
            lsPreferencias.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1){
            }else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    //CREAMOS UN JSONOBJECT PARA CONTENER EL ITEM DEL JSONARRAY QUE ES UN JSONOBJECT
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    //CREAMOS UN BE
                    preferenciasBE=new PreferenciasBE();
                    preferenciasBE.setPRE_KEYNOM(jsonObjectItem.getString("PRE_KEYNOM"));
                    preferenciasBE.setPRE_KEYVAL(jsonObjectItem.getString("PRE_KEYVAL"));
                    lsPreferencias.add(preferenciasBE);
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

}

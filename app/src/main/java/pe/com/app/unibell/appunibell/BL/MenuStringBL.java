package pe.com.app.unibell.appunibell.BL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Menu_StringBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

/**
 * Created by rgalvez on 22/11/2016.
 */
public class MenuStringBL {
    public ArrayList<Menu_StringBE> lst =null;

    public JSONObject getAllRest(String newURL){
        Menu_StringBE menu_stringBE=null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try{
            lst=new ArrayList<Menu_StringBE>();
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
                    menu_stringBE=new Menu_StringBE();
                    menu_stringBE.setIDCORREL(jsonObjectItem.getInt("IDCORREL"));
                    menu_stringBE.setMNUNOM(jsonObjectItem.getString("MNUNOM"));
                    menu_stringBE.setMNUDES(jsonObjectItem.getString("MNUDES"));
                    menu_stringBE.setMNUCHEK(jsonObjectItem.getInt("MNUCHEK"));
                    menu_stringBE.setMNUACTI(jsonObjectItem.getInt("MNUACTI"));
                    menu_stringBE.setMENUEST(jsonObjectItem.getInt("MENUEST"));
                    menu_stringBE.setMNUORDEN(jsonObjectItem.getInt("MNUORDEN"));
                    menu_stringBE.setMNUPERMISO(Integer.valueOf(jsonObjectItem.getString("MNUPERMISO")) == 1 ? true: false);
                    lst.add(menu_stringBE);
                }
                //Agregamos la opcion Acerca De
                menu_stringBE = new Menu_StringBE();
                menu_stringBE.setIDCORREL(100);
                menu_stringBE.setMNUNOM("SMNU_ACERCADE");
                menu_stringBE.setMNUDES("Acerca De");
                menu_stringBE.setMNUCHEK(0);
                menu_stringBE.setMNUACTI(1);
                menu_stringBE.setMENUEST(1);
                menu_stringBE.setMNUORDEN(100);
                menu_stringBE.setMNUPERMISO(true);
                lst.add(menu_stringBE);

                //Agregamos la opcion de cerrar sesión
                menu_stringBE = new Menu_StringBE();
                menu_stringBE.setIDCORREL(100);
                menu_stringBE.setMNUNOM("SMNU_CERRAR");
                menu_stringBE.setMNUDES("Cerrar sesión");
                menu_stringBE.setMNUCHEK(0);
                menu_stringBE.setMNUACTI(1);
                menu_stringBE.setMENUEST(1);
                menu_stringBE.setMNUORDEN(100);
                menu_stringBE.setMNUPERMISO(true);
                lst.add(menu_stringBE);
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

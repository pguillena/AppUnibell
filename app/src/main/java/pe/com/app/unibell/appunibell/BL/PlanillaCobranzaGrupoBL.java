package pe.com.app.unibell.appunibell.BL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.PlanillaCobranzaBE;
import pe.com.app.unibell.appunibell.BE.PlanillaCobranzaGrupoBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class PlanillaCobranzaGrupoBL {
    public ArrayList<PlanillaCobranzaGrupoBE> lst = null;

    public JSONObject getLiquidacionCobranza(String newURL) {
        PlanillaCobranzaGrupoBE planillaCobranzaGrupoBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<PlanillaCobranzaGrupoBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    planillaCobranzaGrupoBE = new PlanillaCobranzaGrupoBE();
                    planillaCobranzaGrupoBE.setPLANILLA(jsonObjectItem.getString("PLANILLA"));
                    planillaCobranzaGrupoBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    planillaCobranzaGrupoBE.setENTIDAD(jsonObjectItem.getString("ENTIDAD"));
                    planillaCobranzaGrupoBE.setVOUCHER(jsonObjectItem.getString("VOUCHER"));
                    planillaCobranzaGrupoBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    planillaCobranzaGrupoBE.setCONSTANCIA(jsonObjectItem.getString("CONSTANCIA"));
                    planillaCobranzaGrupoBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    planillaCobranzaGrupoBE.setVOUCHER(jsonObjectItem.getString("VOUCHER"));
                    planillaCobranzaGrupoBE.setM_COBRANZA(jsonObjectItem.getString("M_COBRANZA"));
                    lst.add(planillaCobranzaGrupoBE);
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



}

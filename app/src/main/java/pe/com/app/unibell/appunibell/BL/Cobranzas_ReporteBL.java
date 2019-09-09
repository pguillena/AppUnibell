package pe.com.app.unibell.appunibell.BL;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CobranzaReporteBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class Cobranzas_ReporteBL {

    public ArrayList<CobranzaReporteBE> lst = null;

    public JSONObject getAllRest(String newURL) {



        CobranzaReporteBE cobranzaReporteBE = null;
        JSONObject jsonObjectRest = null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst = new ArrayList<CobranzaReporteBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status") != 1) {
            } else {
                for (int i = 0; i < jsonObjectRest.getJSONArray("datos").length(); i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    cobranzaReporteBE = new CobranzaReporteBE();

                    cobranzaReporteBE.setC_PACKING(jsonObjectItem.getString("C_PACKING"));
                    cobranzaReporteBE.setESTADO (jsonObjectItem.getString("ESTADO"));
                    cobranzaReporteBE.setID_COBRANZA(jsonObjectItem.getString("ID_COBRANZA"));
                    cobranzaReporteBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    cobranzaReporteBE.setCODIGO_FPAGO(jsonObjectItem.getString("CODIGO_FPAGO"));
                    cobranzaReporteBE.setFECHA_RECIBO(jsonObjectItem.getString("FECHA_RECIBO"));
                    cobranzaReporteBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                    cobranzaReporteBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                    cobranzaReporteBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    cobranzaReporteBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    cobranzaReporteBE.setENTIDAD(jsonObjectItem.getString("ENTIDAD"));
                    cobranzaReporteBE.setCONSTANCIA(jsonObjectItem.getString("CONSTANCIA"));
                    cobranzaReporteBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    cobranzaReporteBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    cobranzaReporteBE.setM_COBRANZA(jsonObjectItem.getString("M_COBRANZA"));
                    cobranzaReporteBE.setRECIBO(jsonObjectItem.getString("RECIBO"));
                    cobranzaReporteBE.setN_RECIBO(jsonObjectItem.getString("N_RECIBO"));
                    cobranzaReporteBE.setID_COBRADOR(jsonObjectItem.getString("ID_COBRADOR"));
                    cobranzaReporteBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    cobranzaReporteBE.setESTADO_CONCILIADO(jsonObjectItem.getString("ESTADO_CONCILIADO"));
                    cobranzaReporteBE.setPLANILLA(jsonObjectItem.getString("PLANILLA"));
                    lst.add(cobranzaReporteBE);
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
package pe.com.app.unibell.appunibell.BL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.PlanillaCobranzaBE;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class PlanillaCobranzaBL {
    public ArrayList<PlanillaCobranzaBE> lst = null;

    public JSONObject getLiquidacionCobranza(String newURL) {
        PlanillaCobranzaBE planillaCobranzaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<PlanillaCobranzaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    planillaCobranzaBE = new PlanillaCobranzaBE();
                    planillaCobranzaBE.setFECHA_PLANILLA(jsonObjectItem.getString("FECHA_PLANILLA"));
                    planillaCobranzaBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    planillaCobranzaBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                    planillaCobranzaBE.setVOUCHER(jsonObjectItem.getInt("VOUCHER"));
                    planillaCobranzaBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    planillaCobranzaBE.setRUC(jsonObjectItem.getString("RUC"));
                    planillaCobranzaBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    planillaCobranzaBE.setCODIGO_FPAGO(jsonObjectItem.getString("CODIGO_FPAGO"));
                    planillaCobranzaBE.setFECHA_RECIBO(jsonObjectItem.getString("FECHA_RECIBO"));
                    planillaCobranzaBE.setUSUARIO_REGISTRO(jsonObjectItem.getString("USUARIO_REGISTRO"));
                    planillaCobranzaBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                    planillaCobranzaBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                    planillaCobranzaBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    planillaCobranzaBE.setENTIDAD(jsonObjectItem.getString("ENTIDAD"));
                    planillaCobranzaBE.setCONSTANCIA(jsonObjectItem.getString("CONSTANCIA"));
                    planillaCobranzaBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    planillaCobranzaBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    planillaCobranzaBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                    planillaCobranzaBE.setRECIBO(jsonObjectItem.getString("RECIBO"));
                    planillaCobranzaBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                    planillaCobranzaBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    planillaCobranzaBE.setESTADO_CONCILIADO(jsonObjectItem.getInt("ESTADO_CONCILIADO"));
                    planillaCobranzaBE.setPLANILLA(jsonObjectItem.getString("PLANILLA"));

                    lst.add(planillaCobranzaBE);
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

    public JSONObject getRptCobranza(String newURL) {
        PlanillaCobranzaBE planillaCobranzaBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<PlanillaCobranzaBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    planillaCobranzaBE = new PlanillaCobranzaBE();
                    planillaCobranzaBE.setC_PACKING(jsonObjectItem.getInt("C_PACKING"));
                    planillaCobranzaBE.setESTADO(jsonObjectItem.getInt("ESTADO"));
                    planillaCobranzaBE.setID_COBRANZA(jsonObjectItem.getInt("ID_COBRANZA"));
                    planillaCobranzaBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    planillaCobranzaBE.setCODIGO_FPAGO(jsonObjectItem.getString("CODIGO_FPAGO"));
                    planillaCobranzaBE.setFECHA_RECIBO(jsonObjectItem.getString("FECHA_RECIBO"));
                    planillaCobranzaBE.setRAZON_SOCIAL(jsonObjectItem.getString("RAZON_SOCIAL"));
                    planillaCobranzaBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                    planillaCobranzaBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    planillaCobranzaBE.setFPAGO(jsonObjectItem.getString("FPAGO"));
                    planillaCobranzaBE.setENTIDAD(jsonObjectItem.getString("ENTIDAD"));
                    planillaCobranzaBE.setCONSTANCIA(jsonObjectItem.getString("CONSTANCIA"));
                    planillaCobranzaBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    planillaCobranzaBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    planillaCobranzaBE.setM_COBRANZA(jsonObjectItem.getDouble("M_COBRANZA"));
                    planillaCobranzaBE.setRECIBO(jsonObjectItem.getString("RECIBO"));
                    planillaCobranzaBE.setN_RECIBO(jsonObjectItem.getInt("N_RECIBO"));
                    planillaCobranzaBE.setID_COBRADOR(jsonObjectItem.getInt("ID_COBRADOR"));
                    planillaCobranzaBE.setCOBRADOR(jsonObjectItem.getString("COBRADOR"));
                    planillaCobranzaBE.setESTADO_CONCILIADO(jsonObjectItem.getInt("ESTADO_CONCILIADO"));
                    planillaCobranzaBE.setPLANILLA(jsonObjectItem.getString("PLANILLA"));
                    lst.add(planillaCobranzaBE);
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

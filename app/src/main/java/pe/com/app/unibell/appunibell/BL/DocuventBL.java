package pe.com.app.unibell.appunibell.BL;

import android.database.sqlite.SQLiteStatement;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.DocuventBE;
import pe.com.app.unibell.appunibell.DAO.DataBaseHelper;
import pe.com.app.unibell.appunibell.Util.RestClientLibrary;

public class DocuventBL {
    public ArrayList<DocuventBE> lst = null;

    public JSONObject getAllRest(String newURL) {
        DocuventBE docuventBE = null;
        JSONObject jsonObjectRest =null;
        JSONObject jsonObjectResult = new JSONObject();
        try {
            lst=new ArrayList<DocuventBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);
            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    docuventBE = new DocuventBE();
                    docuventBE.setTIPODOC(jsonObjectItem.getString("TIPODOC"));
                    docuventBE.setSERIE(jsonObjectItem.getString("SERIE"));
                    docuventBE.setNUMERO(jsonObjectItem.getString("NUMERO"));
                    docuventBE.setESTADO(jsonObjectItem.getString("ESTADO"));
                    docuventBE.setFECHA(jsonObjectItem.getString("FECHA"));
                    docuventBE.setCOD_CLIENTE(jsonObjectItem.getString("COD_CLIENTE"));
                    docuventBE.setNRO_SUCUR(jsonObjectItem.getString("NRO_SUCUR"));
                    docuventBE.setRUC(jsonObjectItem.getString("RUC"));
                    docuventBE.setCOND_PAG(jsonObjectItem.getString("COND_PAG"));
                    docuventBE.setCOD_VENDE(jsonObjectItem.getString("COD_VENDE"));
                    docuventBE.setORIGEN(jsonObjectItem.getString("ORIGEN"));
                    docuventBE.setMONEDA(jsonObjectItem.getString("MONEDA"));
                    docuventBE.setNRO_LISTA(jsonObjectItem.getString("NRO_LISTA"));
                    docuventBE.setPOR_DESC1(jsonObjectItem.getString("POR_DESC1"));
                    docuventBE.setPOR_DESC2(jsonObjectItem.getString("POR_DESC2"));
                    docuventBE.setDETALLE(jsonObjectItem.getString("DETALLE"));
                    docuventBE.setVAL_VENTA(jsonObjectItem.getDouble("VAL_VENTA"));
                    docuventBE.setIMP_DESCTO(jsonObjectItem.getDouble("IMP_DESCTO"));
                    docuventBE.setIMP_NETO(jsonObjectItem.getDouble("IMP_NETO"));
                    docuventBE.setIMP_INTERES(jsonObjectItem.getDouble("IMP_INTERES"));
                    docuventBE.setIMP_ISC(jsonObjectItem.getDouble("IMP_ISC"));
                    docuventBE.setIMP_IGV(jsonObjectItem.getDouble("IMP_IGV"));
                    docuventBE.setPRECIO_VTA(jsonObjectItem.getDouble("PRECIO_VTA"));
                    docuventBE.setCUOTA_INIC(jsonObjectItem.getDouble("CUOTA_INIC"));
                    docuventBE.setDESCTO_ADIC(jsonObjectItem.getDouble("DESCTO_ADIC"));
                    docuventBE.setCTA_DESCTO(jsonObjectItem.getString("CTA_DESCTO"));
                    docuventBE.setCTA_INTERES(jsonObjectItem.getString("CTA_INTERES"));
                    docuventBE.setCTA_IMPISC(jsonObjectItem.getString("CTA_IMPISC"));
                    docuventBE.setCTA_IMPIGV(jsonObjectItem.getString("CTA_IMPIGV"));
                    docuventBE.setCTA_PVTA(jsonObjectItem.getString("CTA_PVTA"));
                    docuventBE.setMOTIVO(jsonObjectItem.getString("MOTIVO"));
                    docuventBE.setPASECC(jsonObjectItem.getString("PASECC"));
                    docuventBE.setVOUCHER(jsonObjectItem.getDouble("VOUCHER"));
                    docuventBE.setCOD_ALM(jsonObjectItem.getString("COD_ALM"));
                    docuventBE.setCLIENTE_AFECTO(jsonObjectItem.getString("CLIENTE_AFECTO"));
                    docuventBE.setTIPO_CAMBIO(jsonObjectItem.getString("TIPO_CAMBIO"));
                    docuventBE.setIMPORT_CAM(jsonObjectItem.getDouble("IMPORT_CAM"));
                    docuventBE.setCALC_INT(jsonObjectItem.getString("CALC_INT"));
                    docuventBE.setGNRA_LETRA(jsonObjectItem.getString("GNRA_LETRA"));
                    docuventBE.setF_VENCTO(jsonObjectItem.getString("F_VENCTO"));
                    docuventBE.setPORC_COMISION(jsonObjectItem.getString("PORC_COMISION"));
                    docuventBE.setTIP_DOC_REF(jsonObjectItem.getString("TIP_DOC_REF"));
                    docuventBE.setSER_DOC_REF(jsonObjectItem.getString("SER_DOC_REF"));
                    docuventBE.setNRO_DOC_REF(jsonObjectItem.getString("NRO_DOC_REF"));
                    docuventBE.setFLG_IMPR(jsonObjectItem.getString("FLG_IMPR"));
                    docuventBE.setUBICACION(jsonObjectItem.getString("UBICACION"));
                    docuventBE.setNOMBRE(jsonObjectItem.getString("NOMBRE"));
                    docuventBE.setDIRECCION(jsonObjectItem.getString("DIRECCION"));
                    docuventBE.setESTADO1(jsonObjectItem.getString("ESTADO1"));
                    docuventBE.setF_ANO_COMISION(jsonObjectItem.getString("F_ANO_COMISION"));
                    docuventBE.setF_MES_COMISION(jsonObjectItem.getString("F_MES_COMISION"));
                    docuventBE.setC_SUC_EMP(jsonObjectItem.getString("C_SUC_EMP"));
                    docuventBE.setI_DI(jsonObjectItem.getString("I_DI"));
                    docuventBE.setVNUMREGOPE(jsonObjectItem.getDouble("VNUMREGOPE"));
                    docuventBE.setNC_TIP_REF(jsonObjectItem.getString("NC_TIP_REF"));
                    docuventBE.setNC_SER_REF(jsonObjectItem.getString("NC_SER_REF"));
                    docuventBE.setNC_NRO_REF(jsonObjectItem.getDouble("NC_NRO_REF"));
                    docuventBE.setM_PORC_PERC(jsonObjectItem.getDouble("M_PORC_PERC"));
                    docuventBE.setM_PERCEPCION(jsonObjectItem.getDouble("M_PERCEPCION"));
                    docuventBE.setPERIODO_PLE(jsonObjectItem.getString("PERIODO_PLE"));
                    docuventBE.setI_FE(jsonObjectItem.getDouble("I_FE"));
                    docuventBE.setID_LOCAL(jsonObjectItem.getDouble("ID_LOCAL"));
                    docuventBE.setM_PAE(jsonObjectItem.getDouble("M_PAE"));
                    docuventBE.setID_EMPRESA(jsonObjectItem.getInt("ID_EMPRESA"));

                    lst.add(docuventBE);
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
            lst=new ArrayList<DocuventBE>();
            lst.clear();
            String aux = new RestClientLibrary().get(newURL);
            jsonObjectRest = new JSONObject(aux);

            //EVALUAMOS EL STATUS
            if (jsonObjectRest.getInt("status")!=1) {
            } else{
                //Eliminamos los registros
                DataBaseHelper.myDataBase.delete("DOCUVENT", null, null);

                String SQL="INSERT OR REPLACE INTO DOCUVENT(TIPODOC,   SERIE,   NUMERO,    FECHA,   COD_CLIENTE,   RUC,   COND_PAG,   COD_VENDE,   MONEDA,   VAL_VENTA,   IMP_DESCTO,   IMP_NETO, " +
                                                            "IMP_INTERES, IMP_ISC,   IMP_IGV,   PRECIO_VTA,   TIPO_CAMBIO,   IMPORT_CAM,   F_VENCTO,   UBICACION,   ID_LOCAL, M_PAE, ID_EMPRESA, URL_PDF, URL_XML, I_RESPUESTA, ESTADO, ORIGEN, NOMBRE)"+
                        "VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                DataBaseHelper.myDataBase.execSQL("PRAGMA synchronous=OFF");
                DataBaseHelper.myDataBase.execSQL("PRAGMA count_changes=OFF");
                DataBaseHelper.myDataBase.setLockingEnabled(false);
                DataBaseHelper.myDataBase.beginTransactionNonExclusive();
                SQLiteStatement stmt = DataBaseHelper.myDataBase.compileStatement(SQL);

                for(int i=0;i<jsonObjectRest.getJSONArray("datos").length();i++) {
                    JSONObject jsonObjectItem = jsonObjectRest.getJSONArray("datos").getJSONObject(i);
                    stmt.bindString(1,jsonObjectItem.getString("TIPODOC"));
                    stmt.bindString(2,jsonObjectItem.getString("SERIE"));
                    stmt.bindString(3,jsonObjectItem.getString("NUMERO"));
                    stmt.bindString(4,jsonObjectItem.getString("FECHA"));
                    stmt.bindString(5,jsonObjectItem.getString("COD_CLIENTE"));
                    stmt.bindString(6,jsonObjectItem.getString("RUC"));
                    stmt.bindString(7,jsonObjectItem.getString("COND_PAG"));
                    stmt.bindString(8,jsonObjectItem.getString("COD_VENDE"));
                    stmt.bindString(9,jsonObjectItem.getString("MONEDA"));
                    stmt.bindString(10,jsonObjectItem.getString("VAL_VENTA"));
                    stmt.bindString(11,jsonObjectItem.getString("IMP_DESCTO"));
                    stmt.bindString(12,jsonObjectItem.getString("IMP_NETO"));
                    stmt.bindString(13,jsonObjectItem.getString("IMP_INTERES"));
                    stmt.bindString(14,jsonObjectItem.getString("IMP_ISC"));
                    stmt.bindString(15,jsonObjectItem.getString("IMP_IGV"));
                    stmt.bindString(16,jsonObjectItem.getString("PRECIO_VTA"));
                    stmt.bindString(17,jsonObjectItem.getString("TIPO_CAMBIO"));
                    stmt.bindString(18,jsonObjectItem.getString("IMPORT_CAM"));
                    stmt.bindString(19,jsonObjectItem.getString("F_VENCTO"));
                    stmt.bindString(20,jsonObjectItem.getString("UBICACION"));
                    stmt.bindString(21,jsonObjectItem.getString("ID_LOCAL"));
                    stmt.bindString(22,jsonObjectItem.getString("M_PAE"));
                    stmt.bindString(23,jsonObjectItem.getString("ID_EMPRESA"));
                    stmt.bindString(24,jsonObjectItem.getString("URL_PDF"));
                    stmt.bindString(25,jsonObjectItem.getString("URL_XML"));
                    stmt.bindString(26,jsonObjectItem.getString("I_RESPUESTA"));
                    stmt.bindString(27,jsonObjectItem.getString("ESTADO"));
                    stmt.bindString(28,jsonObjectItem.getString("ORIGEN"));
                    stmt.bindString(29,jsonObjectItem.getString("NOMBRE"));
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

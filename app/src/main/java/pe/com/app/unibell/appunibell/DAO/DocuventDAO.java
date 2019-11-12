package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.DocuventBE;
import pe.com.app.unibell.appunibell.Util.Funciones;
    public class DocuventDAO {
    public ArrayList<DocuventBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        DocuventBE docuventBE = null;
        try {
            String SQL="SELECT TIPODOC,SERIE,NUMERO,ESTADO,FECHA,COD_CLIENTE,\n" +
                    "NRO_SUCUR,RUC,COND_PAG,COD_VENDE,ORIGEN,MONEDA,\n" +
                    "NRO_LISTA,POR_DESC1,POR_DESC2,DETALLE,VAL_VENTA,IMP_DESCTO,\n" +
                    "IMP_NETO,IMP_INTERES,IMP_ISC,IMP_IGV,PRECIO_VTA,CUOTA_INIC,\n" +
                    "DESCTO_ADIC,CTA_DESCTO,CTA_INTERES,CTA_IMPISC,CTA_IMPIGV,CTA_PVTA,\n" +
                    "MOTIVO,PASECC,VOUCHER,COD_ALM,CLIENTE_AFECTO,TIPO_CAMBIO,\n" +
                    "IMPORT_CAM,CALC_INT,GNRA_LETRA,F_VENCTO,PORC_COMISION,TIP_DOC_REF,\n" +
                    "SER_DOC_REF,NRO_DOC_REF,FLG_IMPR,UBICACION,NOMBRE,DIRECCION,\n" +
                    "ESTADO1,F_ANO_COMISION,F_MES_COMISION,C_SUC_EMP,I_DI,VNUMREGOPE,\n" +
                    "NC_TIP_REF,NC_SER_REF,NC_NRO_REF,M_PORC_PERC,M_PERCEPCION,PERIODO_PLE,\n" +
                    "I_FE,ID_LOCAL FROM DOCUVENT WHERE COD_CLIENTE = '"+pCODIGO +"'";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<DocuventBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    docuventBE = new DocuventBE();
                    docuventBE.setTIPODOC(Funciones.isNullColumn(cursor,"TIPODOC",""));
                    docuventBE.setSERIE(Funciones.isNullColumn(cursor,"SERIE",""));
                    docuventBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    docuventBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    docuventBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    docuventBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    docuventBE.setNRO_SUCUR(Funciones.isNullColumn(cursor,"NRO_SUCUR",""));
                    docuventBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                    docuventBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG",""));
                    docuventBE.setCOD_VENDE(Funciones.isNullColumn(cursor,"COD_VENDE",""));
                    docuventBE.setORIGEN(Funciones.isNullColumn(cursor,"ORIGEN",""));
                    docuventBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    docuventBE.setNRO_LISTA(Funciones.isNullColumn(cursor,"NRO_LISTA",""));
                    docuventBE.setPOR_DESC1(Funciones.isNullColumn(cursor,"POR_DESC1",""));
                    docuventBE.setPOR_DESC2(Funciones.isNullColumn(cursor,"POR_DESC2",""));
                    docuventBE.setDETALLE(Funciones.isNullColumn(cursor,"DETALLE",""));
                    docuventBE.setVAL_VENTA(Funciones.isNullColumn(cursor,"VAL_VENTA",0));
                    docuventBE.setIMP_DESCTO(Funciones.isNullColumn(cursor,"IMP_DESCTO",0));
                    docuventBE.setIMP_NETO(Funciones.isNullColumn(cursor,"IMP_NETO",0));
                    docuventBE.setIMP_INTERES(Funciones.isNullColumn(cursor,"IMP_INTERES",0));
                    docuventBE.setIMP_ISC(Funciones.isNullColumn(cursor,"IMP_ISC",0));
                    docuventBE.setIMP_IGV(Funciones.isNullColumn(cursor,"IMP_IGV",0));
                    docuventBE.setPRECIO_VTA(Funciones.isNullColumn(cursor,"PRECIO_VTA",0));
                    docuventBE.setCUOTA_INIC(Funciones.isNullColumn(cursor,"CUOTA_INIC",0));
                    docuventBE.setDESCTO_ADIC(Funciones.isNullColumn(cursor,"DESCTO_ADIC",0));
                    docuventBE.setCTA_DESCTO(Funciones.isNullColumn(cursor,"CTA_DESCTO",""));
                    docuventBE.setCTA_INTERES(Funciones.isNullColumn(cursor,"CTA_INTERES",""));
                    docuventBE.setCTA_IMPISC(Funciones.isNullColumn(cursor,"CTA_IMPISC",""));
                    docuventBE.setCTA_IMPIGV(Funciones.isNullColumn(cursor,"CTA_IMPIGV",""));
                    docuventBE.setCTA_PVTA(Funciones.isNullColumn(cursor,"CTA_PVTA",""));
                    docuventBE.setMOTIVO(Funciones.isNullColumn(cursor,"MOTIVO",""));
                    docuventBE.setPASECC(Funciones.isNullColumn(cursor,"PASECC",""));
                    docuventBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER",0));
                    docuventBE.setCOD_ALM(Funciones.isNullColumn(cursor,"COD_ALM",""));
                    docuventBE.setCLIENTE_AFECTO(Funciones.isNullColumn(cursor,"CLIENTE_AFECTO",""));
                    docuventBE.setTIPO_CAMBIO(Funciones.isNullColumn(cursor,"TIPO_CAMBIO",""));
                    docuventBE.setIMPORT_CAM(Funciones.isNullColumn(cursor,"IMPORT_CAM",0));
                    docuventBE.setCALC_INT(Funciones.isNullColumn(cursor,"CALC_INT",""));
                    docuventBE.setGNRA_LETRA(Funciones.isNullColumn(cursor,"GNRA_LETRA",""));
                    docuventBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO",""));
                    docuventBE.setPORC_COMISION(Funciones.isNullColumn(cursor,"PORC_COMISION",""));
                    docuventBE.setTIP_DOC_REF(Funciones.isNullColumn(cursor,"TIP_DOC_REF",""));
                    docuventBE.setSER_DOC_REF(Funciones.isNullColumn(cursor,"SER_DOC_REF",""));
                    docuventBE.setNRO_DOC_REF(Funciones.isNullColumn(cursor,"NRO_DOC_REF",""));
                    docuventBE.setFLG_IMPR(Funciones.isNullColumn(cursor,"FLG_IMPR",""));
                    docuventBE.setUBICACION(Funciones.isNullColumn(cursor,"UBICACION",""));
                    docuventBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));
                    docuventBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    docuventBE.setESTADO1(Funciones.isNullColumn(cursor,"ESTADO1",""));
                    docuventBE.setF_ANO_COMISION(Funciones.isNullColumn(cursor,"F_ANO_COMISION",""));
                    docuventBE.setF_MES_COMISION(Funciones.isNullColumn(cursor,"F_MES_COMISION",""));
                    docuventBE.setC_SUC_EMP(Funciones.isNullColumn(cursor,"C_SUC_EMP",""));
                    docuventBE.setI_DI(Funciones.isNullColumn(cursor,"I_DI",""));
                    docuventBE.setVNUMREGOPE(Funciones.isNullColumn(cursor,"VNUMREGOPE",0));
                    docuventBE.setNC_TIP_REF(Funciones.isNullColumn(cursor,"NC_TIP_REF",""));
                    docuventBE.setNC_SER_REF(Funciones.isNullColumn(cursor,"NC_SER_REF",""));
                    docuventBE.setNC_NRO_REF(Funciones.isNullColumn(cursor,"NC_NRO_REF",0));
                    docuventBE.setM_PORC_PERC(Funciones.isNullColumn(cursor,"M_PORC_PERC",0));
                    docuventBE.setM_PERCEPCION(Funciones.isNullColumn(cursor,"M_PERCEPCION",0));
                    docuventBE.setPERIODO_PLE(Funciones.isNullColumn(cursor,"PERIODO_PLE",""));
                    docuventBE.setI_FE(Funciones.isNullColumn(cursor,"I_FE",0));
                    docuventBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    lst.add(docuventBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

}

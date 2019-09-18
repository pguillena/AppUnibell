package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class CabfcobDAO {
    public ArrayList<CabfcobBE> lst = null;

    public void getAll(String pTIPDOC) {
        Cursor cursor = null;
        CabfcobBE cabfcobBE = null;
        try {
            String SQL="SELECT TIPDOC,SERIE_NUM,NUMERO,FECHA,ANO,MES,"+
                    "LIBRO,VOUCHER,ITEM,TIPO_REFERENCIA,SERIE_REF,NRO_REFERENCIA,"+
                    "CONCEPTO,FORMA_PAGO,BANCO,CHEQUE,SISTEMA_ORIGEN,MONEDA,"+
                    "IMPORTE,IMPORTE_X,TIPO_CAMBIO,ESTADO,C_ANO,C_MES,COBRADOR,"+
                    "C_USUARIO,C_PERFIL,C_CPU,FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,"+
                    "FEC_MOD,C_CPU_MOD,N_SERIE_RECIBO_COBRA,N_RECIBO_COBRA,SERIE_PLANILLA,NRO_PLANILLA,"+
                    "C_LIQUIDADOR,TIPO_LIQUIDADOR,FECHA_COBRANZA" +
                    "FROM CABFCOB WHERE (" + pTIPDOC + "=-1 OR COD_CLIENTE=" + pTIPDOC + ") ORDER BY TIPDOC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<CabfcobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
            cabfcobBE = new CabfcobBE();
            cabfcobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC",""));
            cabfcobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM",""));
            cabfcobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
            cabfcobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
            cabfcobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
            cabfcobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
            cabfcobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO",""));
            cabfcobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER",0));
            cabfcobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
            cabfcobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA",""));
            cabfcobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
            cabfcobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA",""));
            cabfcobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO",""));
            cabfcobBE.setFORMA_PAGO(Funciones.isNullColumn(cursor,"FORMA_PAGO",""));
            cabfcobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO",""));
            cabfcobBE.setCHEQUE(Funciones.isNullColumn(cursor,"CHEQUE",""));
            cabfcobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN",""));
            cabfcobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
            cabfcobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.00));
            cabfcobBE.setIMPORTE_X(Funciones.isNullColumn(cursor,"IMPORTE_X",0.00));
            cabfcobBE.setTIPO_CAMBIO(Funciones.isNullColumn(cursor,"TIPO_CAMBIO",0.00));
            cabfcobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
            cabfcobBE.setC_ANO(Funciones.isNullColumn(cursor,"C_ANO",0));
            cabfcobBE.setC_MES(Funciones.isNullColumn(cursor,"C_MES",0));
            cabfcobBE.setCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
            cabfcobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
            cabfcobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
            cabfcobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
            cabfcobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
            cabfcobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
            cabfcobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
            cabfcobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
            cabfcobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
            cabfcobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA",""));
            cabfcobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
            cabfcobBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
            cabfcobBE.setNRO_PLANILLA(Funciones.isNullColumn(cursor,"NRO_PLANILLA",0));
            cabfcobBE.setC_LIQUIDADOR(Funciones.isNullColumn(cursor,"C_LIQUIDADOR",""));
            cabfcobBE.setTIPO_LIQUIDADOR(Funciones.isNullColumn(cursor,"TIPO_LIQUIDADOR",""));
            cabfcobBE.setFECHA_COBRANZA(Funciones.isNullColumn(cursor,"CODIGO",""));
            lst.add(cabfcobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(CabfcobBE cabfcobBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();

            cv.put("TIPDOC",cabfcobBE.getTIPDOC());
            cv.put("SERIE_NUM",cabfcobBE.getSERIE_NUM());
            cv.put("NUMERO",cabfcobBE.getNUMERO());
            cv.put("FECHA",cabfcobBE.getFECHA());
            cv.put("ANO",cabfcobBE.getANO());
            cv.put("MES",cabfcobBE.getMES());
            cv.put("LIBRO",cabfcobBE.getLIBRO());
            cv.put("VOUCHER",cabfcobBE.getVOUCHER());
            cv.put("ITEM",cabfcobBE.getITEM());
            cv.put("TIPO_REFERENCIA",cabfcobBE.getTIPO_REFERENCIA());
            cv.put("SERIE_REF",cabfcobBE.getSERIE_REF());
            cv.put("NRO_REFERENCIA",cabfcobBE.getNRO_REFERENCIA());
            cv.put("CONCEPTO",cabfcobBE.getCONCEPTO());
            cv.put("FORMA_PAGO",cabfcobBE.getFORMA_PAGO());
            cv.put("BANCO",cabfcobBE.getBANCO());
            cv.put("CHEQUE",cabfcobBE.getCHEQUE());
            cv.put("SISTEMA_ORIGEN",cabfcobBE.getSISTEMA_ORIGEN());
            cv.put("MONEDA",cabfcobBE.getMONEDA());
            cv.put("IMPORTE",cabfcobBE.getIMPORTE());
            cv.put("IMPORTE_X",cabfcobBE.getIMPORTE_X());
            cv.put("TIPO_CAMBIO",cabfcobBE.getTIPO_CAMBIO());
            cv.put("ESTADO",cabfcobBE.getESTADO());
            cv.put("C_ANO",cabfcobBE.getC_ANO());
            cv.put("C_MES",cabfcobBE.getC_MES());
            cv.put("COBRADOR",cabfcobBE.getCOBRADOR());
            cv.put("C_USUARIO",cabfcobBE.getC_USUARIO());
            cv.put("C_PERFIL",cabfcobBE.getC_PERFIL());
            cv.put("C_CPU",cabfcobBE.getC_CPU());
            cv.put("FEC_REG",cabfcobBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",cabfcobBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",cabfcobBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",cabfcobBE.getFEC_MOD());
            cv.put("C_CPU_MOD",cabfcobBE.getC_CPU_MOD());
            cv.put("N_SERIE_RECIBO_COBRA",cabfcobBE.getN_SERIE_RECIBO_COBRA());
            cv.put("N_RECIBO_COBRA",cabfcobBE.getN_RECIBO_COBRA());
            cv.put("SERIE_PLANILLA",cabfcobBE.getSERIE_PLANILLA());
            cv.put("NRO_PLANILLA",cabfcobBE.getNRO_PLANILLA());
            cv.put("C_LIQUIDADOR",cabfcobBE.getC_LIQUIDADOR());
            cv.put("TIPO_LIQUIDADOR",cabfcobBE.getTIPO_LIQUIDADOR());
            cv.put("FECHA_COBRANZA",cabfcobBE.getFECHA_COBRANZA());

            DataBaseHelper.myDataBase.insert("CtaBnco",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public void getByRecibo(String pSerie, String pNumero) {
        Cursor cursor = null;
        CabfcobBE cabfcobBE = null;
        try {
            String SQL=" SELECT TIPDOC, SERIE_NUM, NUMERO, FECHA, IMPORTE, COBRADOR, N_SERIE_RECIBO_COBRA, N_RECIBO_COBRA " +
                    " FROM CABFCOB " +
                    " WHERE N_SERIE_RECIBO_COBRA =" + pSerie + " AND N_RECIBO_COBRA = " + pNumero + "   AND N_RECIBO_COBRA IS NOT NULL   AND N_RECIBO_COBRA <> '0' ";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<CabfcobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    cabfcobBE = new CabfcobBE();
                    cabfcobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TINRO PDOC",""));
                    cabfcobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM",""));
                    cabfcobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    cabfcobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    cabfcobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.00));
                    cabfcobBE.setCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
                    cabfcobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA",""));
                    cabfcobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));

                    lst.add(cabfcobBE);
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

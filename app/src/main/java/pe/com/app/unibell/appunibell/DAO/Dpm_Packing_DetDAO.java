package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Dpm_Packing_DetBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Dpm_Packing_DetDAO {
    public ArrayList<Dpm_Packing_DetBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        Dpm_Packing_DetBE dpm_packing_detBE = null;
        try {
            String SQL="SELECT C_PACKING,F_PACKING,C_EMPRESA,TIPODOC,SERIE,NUMERO," +
                    "COD_CLIENTE,MONEDA,TOTAL_DOC,FLG_COND,OBSERVACION,C_USUARIO," +
                    "C_PERFIL,C_CPU,FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD," +
                    "C_ESTADO,C_CPU_MOD,ANULADO,BANDEJAS,CAJAS,BOLSAS," +
                    "UNISUELTAS,TOTPESO,FLG_ENTREGADO,C_MOTIVO,N_PLANILLA_COB,BULTOS," +
                    "N_VOUCHER_TES,F_ANO_TES,F_MES_TES,C_TIPO_TES,N_ITEM_TES,COND_PAGO," +
                    "M_COBRANZA,M_COBRANZA_D,FPAGO,NUMCHEQ,FECCHEQ,BANCO," +
                    "N_RECIBO,N_SERIE_RECIBO,ORD_VISITA,N_SEQUENCIA,M_COBRANZA_ANT,NRO_OPERACION," +
                    "FECHA_DEPOSITO,BCO_CTACTE,IC_MONTO,N_TARJETA,ID_MOVIMIENTO_POS,SERIE_PLANILLA_COB" +
                    "FROM DPM_PACKING_DET";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Dpm_Packing_DetBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    dpm_packing_detBE = new Dpm_Packing_DetBE();
                    dpm_packing_detBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",0));
                    dpm_packing_detBE.setF_PACKING(Funciones.isNullColumn(cursor,"F_PACKING",""));
                    dpm_packing_detBE.setC_EMPRESA(Funciones.isNullColumn(cursor,"C_EMPRESA",""));
                    dpm_packing_detBE.setTIPODOC(Funciones.isNullColumn(cursor,"TIPODOC",""));
                    dpm_packing_detBE.setSERIE(Funciones.isNullColumn(cursor,"SERIE",""));
                    dpm_packing_detBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    dpm_packing_detBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    dpm_packing_detBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    dpm_packing_detBE.setTOTAL_DOC(Funciones.isNullColumn(cursor,"TOTAL_DOC",""));
                    dpm_packing_detBE.setFLG_COND(Funciones.isNullColumn(cursor,"FLG_COND",""));
                    dpm_packing_detBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    dpm_packing_detBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    dpm_packing_detBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    dpm_packing_detBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    dpm_packing_detBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    dpm_packing_detBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    dpm_packing_detBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    dpm_packing_detBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
                    dpm_packing_detBE.setC_ESTADO(Funciones.isNullColumn(cursor,"C_ESTADO",""));
                    dpm_packing_detBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    dpm_packing_detBE.setANULADO(Funciones.isNullColumn(cursor,"ANULADO",""));
                    dpm_packing_detBE.setBANDEJAS(Funciones.isNullColumn(cursor,"BANDEJAS",0));
                    dpm_packing_detBE.setCAJAS(Funciones.isNullColumn(cursor,"CAJAS",0));
                    dpm_packing_detBE.setBOLSAS(Funciones.isNullColumn(cursor,"BOLSAS",0));
                    dpm_packing_detBE.setUNISUELTAS(Funciones.isNullColumn(cursor,"UNISUELTAS",0));
                    dpm_packing_detBE.setTOTPESO(Funciones.isNullColumn(cursor,"TOTPESO",0));
                    dpm_packing_detBE.setFLG_ENTREGADO(Funciones.isNullColumn(cursor,"FLG_ENTREGADO",""));
                    dpm_packing_detBE.setC_MOTIVO(Funciones.isNullColumn(cursor,"C_MOTIVO",""));
                    dpm_packing_detBE.setN_PLANILLA_COB(Funciones.isNullColumn(cursor,"N_PLANILLA_COB",""));
                    dpm_packing_detBE.setBULTOS(Funciones.isNullColumn(cursor,"BULTOS",0));
                    dpm_packing_detBE.setN_VOUCHER_TES(Funciones.isNullColumn(cursor,"N_VOUCHER_TES",0));
                    dpm_packing_detBE.setF_ANO_TES(Funciones.isNullColumn(cursor,"F_ANO_TES",0));
                    dpm_packing_detBE.setF_MES_TES(Funciones.isNullColumn(cursor,"F_MES_TES",0));
                    dpm_packing_detBE.setC_TIPO_TES(Funciones.isNullColumn(cursor,"C_TIPO_TES",""));
                    dpm_packing_detBE.setN_ITEM_TES(Funciones.isNullColumn(cursor,"N_ITEM_TES",0));
                    dpm_packing_detBE.setCOND_PAGO(Funciones.isNullColumn(cursor,"COND_PAGO",""));
                    dpm_packing_detBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0));
                    dpm_packing_detBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0));
                    dpm_packing_detBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    dpm_packing_detBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    dpm_packing_detBE.setFECCHEQ(Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    dpm_packing_detBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO",""));
                    dpm_packing_detBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",0));
                    dpm_packing_detBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",0));
                    dpm_packing_detBE.setORD_VISITA(Funciones.isNullColumn(cursor,"ORD_VISITA",0));
                    dpm_packing_detBE.setN_SEQUENCIA(Funciones.isNullColumn(cursor,"N_SEQUENCIA",0));
                    dpm_packing_detBE.setM_COBRANZA_ANT(Funciones.isNullColumn(cursor,"M_COBRANZA_ANT",0));
                    dpm_packing_detBE.setNRO_OPERACION(Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    dpm_packing_detBE.setFECHA_DEPOSITO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    dpm_packing_detBE.setBCO_CTACTE(Funciones.isNullColumn(cursor,"BCO_CTACTE",""));
                    dpm_packing_detBE.setIC_MONTO(Funciones.isNullColumn(cursor,"IC_MONTO",""));
                    dpm_packing_detBE.setN_TARJETA(Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    dpm_packing_detBE.setID_MOVIMIENTO_POS(Funciones.isNullColumn(cursor,"ID_MOVIMIENTO_POS",0));
                    dpm_packing_detBE.setSERIE_PLANILLA_COB(Funciones.isNullColumn(cursor,"SERIE_PLANILLA_COB",""));
                    lst.add(dpm_packing_detBE);
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

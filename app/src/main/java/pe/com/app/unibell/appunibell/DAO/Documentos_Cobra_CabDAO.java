package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_CabBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_DetBL;
import pe.com.app.unibell.appunibell.R;
import pe.com.app.unibell.appunibell.Util.ConstantsLibrary;
import pe.com.app.unibell.appunibell.Util.Funciones;
import static android.content.Context.MODE_PRIVATE;

public class Documentos_Cobra_CabDAO {
    public  ArrayList<Documentos_Cobra_CabBE> lst = null;
    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    public void getAllBy(String sEvento,String iPARAMETRO,String sN_SERIE_RECIBO,String sN_RECIBO) {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        String SQL="";
        String SQLCONDICION="";
        try {
            if(sEvento.trim().equals("0")) {
                SQLCONDICION = " WHERE CODUNC_LOCAL=" + iPARAMETRO + " ORDER BY ID_COBRANZA DESC";
            }else{
                if(sN_SERIE_RECIBO.equals("") || sN_RECIBO.equals("")) {
                    SQLCONDICION = " WHERE ID_COBRANZA=" + iPARAMETRO + " ORDER BY ID_COBRANZA DESC";
                }else{
                    SQLCONDICION = " WHERE N_SERIE_RECIBO=" + sN_SERIE_RECIBO + " AND N_RECIBO=" + sN_RECIBO;
                }
            }

            SQL="SELECT C.ID_COBRANZA,C.COD_CLIENTE,C.N_RECIBO,C.N_SERIE_RECIBO,C.FPAGO," +
                    "C.ID_COBRADOR,C.FECHA,C.M_COBRANZA,C.M_COBRANZA_D,C.SALDO," +
                    "C.NUMCHEQ,C.FECCHEQ,C.ID_BANCO,C.CTACORRIENTE_BANCO,C.NRO_OPERACION," +
                    "C.FECHA_DEPOSITO,C.COMENTARIO,C.ID_EMPRESA,C.ID_LOCAL,C.ESTADO," +
                    "C.FECHA_REGISTRO,C.FECHA_MODIFICACION,C.USUARIO_REGISTRO,C.USUARIO_MODIFICACION,C.PC_REGISTRO," +
                    "C.PC_MODIFICACION,C.IP_REGISTRO,C.IP_MODIFICACION,C.ITEM,C.ESTADO_CONCILIADO," +
                    "C.SERIE_PLANILLA,C.N_PLANILLA,C.C_PACKING,C.ID_MOV_BANCO,C.ESTADO_PROCESO," +
                    "C.T_CAMBIO_TIENDA,C.N_TARJETA,C.ID_MOV_BANCO_ABONO,C.FECHA_DEPOSITO_ABONO,C.LOTE,FLAG_COBRANZA,C.CODUNC_LOCAL, " +
                    " T.DESCRIPCION AS FPAGODESC,IFNULL(B1.BANCO,'') AS BANCODESC,IFNULL(B2.DESCRIPCION,'') AS NOMCTACORRIENTE,C.GUARDADO,C.SINCRONIZADO "+
                    " FROM S_CCM_DOCUMENTOS_COBRA_CAB C" +
                    " INNER JOIN TABLAS_AUXILIARES T ON T.CODIGO=C.FPAGO AND T.TIPO='14'" +
                    " LEFT JOIN S_GEM_BANCO B1 ON B1.ID_BANCO=C.ID_BANCO AND B1.ID_EMPRESA=C.ID_EMPRESA "+
                    " LEFT JOIN CTABNCO B2 ON B2.CODIGO=C.CTACORRIENTE_BANCO "+ SQLCONDICION ;

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    documentos_cobra_cabBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_cabBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ","").trim().replace("null",""));
                    documentos_cobra_cabBE.setFECCHEQ(Funciones.isNullColumn(cursor,"FECCHEQ","").trim().replace("null",""));
                    documentos_cobra_cabBE.setID_BANCO(Funciones.isNullColumn(cursor,"ID_BANCO",0));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    documentos_cobra_cabBE.setNRO_OPERACION(Funciones.isNullColumn(cursor,"NRO_OPERACION","").trim().replace("null",""));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO","").trim().replace("null",""));
                    documentos_cobra_cabBE.setCOMENTARIO(Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    documentos_cobra_cabBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_cabBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    documentos_cobra_cabBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    documentos_cobra_cabBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    documentos_cobra_cabBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    documentos_cobra_cabBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    documentos_cobra_cabBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    documentos_cobra_cabBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    documentos_cobra_cabBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    documentos_cobra_cabBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    documentos_cobra_cabBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",""));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    documentos_cobra_cabBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    documentos_cobra_cabBE.setN_PLANILLA(Funciones.isNullColumn(cursor,"N_PLANILLA","").trim().replace("null",""));
                    documentos_cobra_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    documentos_cobra_cabBE.setESTADO_PROCESO(Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    documentos_cobra_cabBE.setN_TARJETA(Funciones.isNullColumn(cursor,"N_TARJETA","").trim().replace("null",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",0));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO_ABONO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    documentos_cobra_cabBE.setLOTE(Funciones.isNullColumn(cursor,"LOTE",""));
                    documentos_cobra_cabBE.setFLAG_COBRANZA(Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));
                    documentos_cobra_cabBE.setCODUNC_LOCAL(Funciones.isNullColumn(cursor,"CODUNC_LOCAL",0));
                    documentos_cobra_cabBE.setFPAGODESC(Funciones.isNullColumn(cursor,"FPAGODESC",""));
                    documentos_cobra_cabBE.setBANCODESC(Funciones.isNullColumn(cursor,"BANCODESC",""));
                    documentos_cobra_cabBE.setNOMCTACORRIENTE(Funciones.isNullColumn(cursor,"NOMCTACORRIENTE",""));
                    documentos_cobra_cabBE.setGUARDADO(Funciones.isNullColumn(cursor,"GUARDADO",0));
                    documentos_cobra_cabBE.setSINCRONIZADO(Funciones.isNullColumn(cursor,"SINCRONIZADO",0));
                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public void getByGuardado() {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            String SQL="SELECT ID_COBRANZA,COD_CLIENTE,N_RECIBO,N_SERIE_RECIBO,FPAGO," +
                    "ID_COBRADOR,FECHA,M_COBRANZA,M_COBRANZA_D,SALDO," +
                    "NUMCHEQ,FECCHEQ,ID_BANCO,CTACORRIENTE_BANCO,NRO_OPERACION," +
                    "FECHA_DEPOSITO,COMENTARIO,C.ID_EMPRESA,C.ID_LOCAL,ESTADO," +
                    "FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                    "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ITEM,ESTADO_CONCILIADO," +
                    "SERIE_PLANILLA,N_PLANILLA,C_PACKING,ID_MOV_BANCO,ESTADO_PROCESO," +
                    "T_CAMBIO_TIENDA,N_TARJETA,ID_MOV_BANCO_ABONO,FECHA_DEPOSITO_ABONO,LOTE,FLAG_COBRANZA,CODUNC_LOCAL, " +
                    " T.DESCRIPCION AS FPAGODESC,IFNULL(B1.DESCRIPCION,B2.DESCRIPCION) BANCODESC "+
                    " FROM S_CCM_DOCUMENTOS_COBRA_CAB C" +
                    " INNER JOIN TABLAS_AUXILIARES T ON T.CODIGO=C.FPAGO AND T.TIPO='14'" +
                    " LEFT JOIN CTABNCO B1 ON B1.CODIGO=C.ID_BANCO "+
                    " LEFT JOIN CTABNCO B2 ON B2.CODIGO=C.CTACORRIENTE_BANCO "+
                    " WHERE C.GUARDADO=2";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    documentos_cobra_cabBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_cabBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    documentos_cobra_cabBE.setFECCHEQ(Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    documentos_cobra_cabBE.setID_BANCO(Funciones.isNullColumn(cursor,"ID_BANCO",0));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    documentos_cobra_cabBE.setNRO_OPERACION(Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    documentos_cobra_cabBE.setCOMENTARIO(Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    documentos_cobra_cabBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_cabBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    documentos_cobra_cabBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    documentos_cobra_cabBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    documentos_cobra_cabBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    documentos_cobra_cabBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    documentos_cobra_cabBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    documentos_cobra_cabBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    documentos_cobra_cabBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    documentos_cobra_cabBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    documentos_cobra_cabBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",""));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    documentos_cobra_cabBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    documentos_cobra_cabBE.setN_PLANILLA(Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    documentos_cobra_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    documentos_cobra_cabBE.setESTADO_PROCESO(Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    documentos_cobra_cabBE.setN_TARJETA(Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",0));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO_ABONO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO_ABONO",""));
                    documentos_cobra_cabBE.setLOTE(Funciones.isNullColumn(cursor,"LOTE",""));
                    documentos_cobra_cabBE.setFLAG_COBRANZA(Funciones.isNullColumn(cursor,"FLAG_COBRANZA",""));
                    documentos_cobra_cabBE.setCODUNC_LOCAL(Funciones.isNullColumn(cursor,"CODUNC_LOCAL",0));
                    documentos_cobra_cabBE.setFPAGODESC(Funciones.isNullColumn(cursor,"FPAGODESC",""));
                    documentos_cobra_cabBE.setBANCODESC(Funciones.isNullColumn(cursor,"BANCODESC",""));
                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


 public void getLiquidacionBy(String iID_EMPRESA,String iID_LOCAL,String iFECHA,String iID_COBRADOR,String iESTADO,String iN_PLANILLA,String iC_PACKING) {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            if ( iC_PACKING == null || iC_PACKING.toString().equals("")) {
                iC_PACKING="0";
            }
            if (iN_PLANILLA  == null || iN_PLANILLA.toString().equals("")) {
                iN_PLANILLA="0";
            }

            String SQL=
                    "SELECT \n" +
                            "C.C_PACKING,C.ID_MOV_BANCO,C.FECHA AS FECHA_PLANILLA,C.ESTADO,C.ID_COBRANZA,D.VOUCHER,\n" +
                            "C.COD_CLIENTE,CASE WHEN S.RUC IS NULL THEN S.DOCIDEN ELSE S.RUC END AS RUC,\n" +
                            "C.FPAGO AS CODIGO_FPAGO,C.FECHA AS FECHA_RECIBO,C.USUARIO_REGISTRO,\n" +
                            "S.NOMBRE AS RAZON_SOCIAL,T.ABREVIADA AS TIPODOC,D.SERIE_NUM || '-' ||D.NUMERO AS NUMERO,\n" +
                            "A.ABREVIADA AS FPAGODESC, C.FPAGO, \n" +
                            "CASE     \n" +
                            "   WHEN C.FPAGO IN('P','V','Z','D','M','I','H','S') THEN B.DESCRIPCION\n" +
                            "   WHEN C.FPAGO='C' THEN SB.BANCO  \n" +
                            "   WHEN C.FPAGO='E' THEN 'EFECTIVO'\n" +
                            "   END ENTIDAD,\n" +
                            "CASE     \n" +
                            "   WHEN C.FPAGO IN('P','Z') THEN C.NRO_OPERACION\n" +
                            "   WHEN C.FPAGO='C' THEN C.NUMCHEQ \n" +
                            "   WHEN C.FPAGO IN('V','D','M','I','H','S') THEN '****' || C.N_TARJETA\n" +
                            "   WHEN C.FPAGO='E' THEN 'EFECTIVO'\n" +
                            "   WHEN C.FPAGO='C' THEN C.NUMCHEQ    \n" +
                            "   END CONSTANCIA,\n" +
                            "CASE     \n" +
                            "   WHEN C.FPAGO IN('P','V','Z','D','M','I','H','S') THEN C.FECHA_DEPOSITO\n" +
                            "   WHEN C.FPAGO='C' THEN C.FECCHEQ\n" +
                            "   WHEN C.FPAGO='E' THEN C.FECHA    \n" +
                            "   END FECHA,\n" +
                            "CASE     \n" +
                            "   WHEN D.MONEDA IN('D') THEN 'S' ELSE  D.MONEDA END AS MONEDA , \n" +
                            " CASE     \n" +
                            "   WHEN D.MONEDA IN('S') THEN D.M_COBRANZA ELSE  D.M_COBRANZA*C.T_CAMBIO_TIENDA END AS M_COBRANZA,    \n" +
                            "(C.N_SERIE_RECIBO || '-' || C.N_RECIBO) AS RECIBO,\n" +
                            "VA.CODIGO_ANTIGUO AS ID_COBRADOR,\n" +
                            "(SELECT P.NOMBRES || ' ' || P.APELLIDO_PATERNO || ' ' || P.APELLIDO_MATERNO FROM S_GEM_PERSONA P WHERE P.ID_PERSONA = 122) AS COBRADOR,\n" +
                            "C.ESTADO_CONCILIADO,\n" +
                            "CASE     \n" +
                            "   WHEN C.N_PLANILLA IN(0) THEN '' ELSE  C.SERIE_PLANILLA || '-'|| C.N_PLANILLA  END AS PLANILLA,C.CODUNC_LOCAL, \n" +
                            " IFNULL(B.DESCRIPCION,'') AS NOMCTACORRIENTE,C.NUMCHEQ,IFNULL(C.N_SERIE_RECIBO,'') AS N_SERIE_RECIBO ,IFNULL(C.N_RECIBO,'') AS N_RECIBO,IFNULL(T_CAMBIO_TIENDA,0.0) AS T_CAMBIO_TIENDA " +
                            "FROM S_CCM_DOCUMENTOS_COBRA_CAB C \n" +
                            "INNER JOIN S_CCM_DOCUMENTOS_COBRA_DET D ON (C.ID_COBRANZA = D.ID_COBRANZA)\n" +
                            "INNER JOIN CLIENTES S ON (C.COD_CLIENTE = S.COD_CLIENTE AND S.ID_EMPRESA=1)\n" +
                            "LEFT JOIN  TABLAS_AUXILIARES A ON (D.FPAGO = A.CODIGO AND A.TIPO = 14 AND A.ID_EMPRESA=" +  iID_EMPRESA + ") \n" +
                            "INNER JOIN TABLAS_AUXILIARES T ON(D.TIPDOC = T.CODIGO AND T.TIPO = 2 AND T.ID_EMPRESA=" +  iID_EMPRESA + " )\n" +
                            "LEFT JOIN CTABNCO B ON(C.CTACORRIENTE_BANCO = B.CODIGO and B.ID_EMPRESA=" +  iID_EMPRESA + ") \n" +
                            "LEFT JOIN S_GEM_BANCO SB ON(C.ID_BANCO = SB.ID_BANCO AND SB.ID_EMPRESA =" +  iID_EMPRESA + ") \n" +
                            "LEFT JOIN S_GEM_VENDEDOR_CODIGO_ANT VA ON(C.ID_COBRADOR = VA.ID_VENDEDOR AND VA.FLAG_VIGENCIA>0)" +
                             "WHERE (C.ID_COBRADOR ="+ iID_COBRADOR + " OR ( " + iID_COBRADOR + " IN(8719,15737) AND C.C_PACKING>0) )\n" +
                             " AND (  substr(C.FECHA,7,4) || substr(C.FECHA,4,2) || substr(C.FECHA,1,2)  BETWEEN '"+ iFECHA +"' AND '" + iFECHA +"'" + " OR '" + iFECHA + "' = '' )" +
                              "AND (C.ESTADO =" +  iESTADO + " OR " +  iESTADO + "= 0)\n" +
                                 "AND C.ESTADO <> 40002 \n" +
                                 "AND (C.C_PACKING ="+  iC_PACKING + " OR " + iC_PACKING + "= 0)\n" +
                                 "AND C.ID_EMPRESA =" +  iID_EMPRESA + "\n" +
                                 "AND C.ID_LOCAL =" +  iID_LOCAL + "\n" +
                                 "AND (C.N_PLANILLA ="+ iN_PLANILLA + " OR " + iN_PLANILLA + "=0) \n" +
                                 "AND C.GUARDADO IN(2,3) " +
                                 "ORDER BY C.C_PACKING ASC, C.N_RECIBO ASC";
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String sFECHA="";
            if (cursor.moveToFirst()) {
                do {
                    sFECHA="";
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    documentos_cobra_cabBE.setFECHA_PLANILLA(Funciones.isNullColumn(cursor,"FECHA_PLANILLA",""));
                    documentos_cobra_cabBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    documentos_cobra_cabBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_cabBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER",""));
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                    documentos_cobra_cabBE.setCODIGO_FPAGO(Funciones.isNullColumn(cursor,"CODIGO_FPAGO",""));
                    documentos_cobra_cabBE.setFECHA_RECIBO(Funciones.isNullColumn(cursor,"FECHA_RECIBO",""));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    documentos_cobra_cabBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL",""));
                    documentos_cobra_cabBE.setTIPODOC(Funciones.isNullColumn(cursor,"TIPODOC",""));
                    documentos_cobra_cabBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setENTIDAD(Funciones.isNullColumn(cursor,"ENTIDAD",""));
                    documentos_cobra_cabBE.setCONSTANCIA(Funciones.isNullColumn(cursor,"CONSTANCIA",""));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    sFECHA=Funciones.isNullColumn(cursor,"FECHA","");
                    documentos_cobra_cabBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setRECIBO(Funciones.isNullColumn(cursor,"RECIBO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    documentos_cobra_cabBE.setNOMCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    documentos_cobra_cabBE.setPLANILLA(Funciones.isNullColumn(cursor,"PLANILLA","0"));
                    documentos_cobra_cabBE.setFPAGODESC(Funciones.isNullColumn(cursor,"FPAGODESC",""));
                    documentos_cobra_cabBE.setCODUNC_LOCAL(Funciones.isNullColumn(cursor,"CODUNC_LOCAL",0));
                    documentos_cobra_cabBE.setNOMCTACORRIENTE(Funciones.isNullColumn(cursor,"NOMCTACORRIENTE",""));
                    documentos_cobra_cabBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA","0.0"));

                    documentos_cobra_cabBE.setCHKMARCADO(false);

                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getReciboElectronico(String iID_EMPRESA,
                                     String iID_LOCAL,
                                     String iSERIE_RECIBO,
                                     String iNUMERO_RECIBO
    ) {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            String SQL=
                    "SELECT C.ID_EMPRESA,\n" +
                            "       C.COD_CLIENTE,\n" +
                            "       C.FPAGO AS CODIGO_FPAGO,\n" +
                            "       C.FECHA AS FECHA_RECIBO,\n" +
                            "       D.ID_VENDEDOR,\n" +
                            "       D.SALDO, \n" +
                            "       D.SALDO_INICIAL,          \n" +
                            "       C.T_CAMBIO_TIENDA,   \n" +
                            "       S.NOMBRE AS RAZON_SOCIAL,\n" +
                            "       T.ABREVIADA AS TIPODOC,\n" +
                            "   D.SERIE_NUM || '-' || D.NUMERO AS NUMERO,\n" +
                            "       A.ABREVIADA AS FPAGO,\n" +
                            " CASE WHEN C.FPAGO IN('P','V','Z','D','M','I','H','S') THEN B.DESCRIPCION \n" +
                            "            WHEN C.FPAGO='C' THEN SB.BANCO  \n" +
                            "            WHEN C.FPAGO='E' THEN 'EFECTIVO'\n" +
                            "            END ENTIDAD,\n" +
                            " CASE\n" +
                            "     WHEN C.FPAGO IN('P','Z') THEN C.NRO_OPERACION\n" +
                            "              WHEN C.FPAGO='C' THEN C.NUMCHEQ \n" +
                            "              WHEN C.FPAGO IN('V','D','M','I','H','S') THEN '****' || C.N_TARJETA \n" +
                            "              WHEN C.FPAGO='E' THEN 'EFECTIVO'\n" +
                            "              END CONSTANCIA, \n" +
                            " CASE  WHEN C.FPAGO IN('P','V','Z','D','M','I','H','S') THEN C.FECHA_DEPOSITO \n" +
                            "               WHEN C.FPAGO='C' THEN C.FECCHEQ \n" +
                            "               WHEN C.FPAGO='E' THEN C.FECHA \n" +
                            "               END FECHA,\n" +
                            " CASE   WHEN D.MONEDA IN('D') THEN 'S' ELSE  D.MONEDA END AS MONEDA ,\n" +
                            " CASE   WHEN D.MONEDA IN('S') THEN D.M_COBRANZA ELSE  D.M_COBRANZA* C.T_CAMBIO_TIENDA END AS M_COBRANZA,\n" +
                            "        C.N_SERIE_RECIBO || '-' || C.N_RECIBO AS RECIBO,\n C.N_SERIE_RECIBO,C.N_RECIBO," +
                            " CASE \n" +
                            "       WHEN C.N_PLANILLA IN(0) THEN '' ELSE  C.SERIE_PLANILLA || '-'|| C.N_PLANILLA  END AS PLANILLA,\n" +
                            " CASE WHEN C.C_PACKING > 0 " +
                            " THEN (SELECT DT.NOMBRES || ' ' || DT.APE_PATERNO || ' ' || DT.APE_MATERNO FROM DPM_PACKING_CAB DA INNER JOIN DPM_PERSONAL_TRANSPORTE DT  ON (DA.C_REPARTIDOR = DT.C_PERTRANS) WHERE DA.C_PACKING = C.C_PACKING)" +
                            " ELSE (P.NOMBRES || ' ' || P.APELLIDO_PATERNO || ' ' || P.APELLIDO_MATERNO) END AS COBRADOR, \n"+
                            "       V.NOMBRES || ' ' || V.APELLIDO_PATERNO || ' ' || V.APELLIDO_MATERNO AS VENDEDOR,\n" +
                            "       E.DIRECCION,E.RUC,E.TELEFONO,E.NOMBRE AS EMPRESA,'' AS DISTRITO,'' AS DEPARTAMENTO\n" +
                            " FROM S_CCM_DOCUMENTOS_COBRA_CAB C\n" +
                            " INNER JOIN S_CCM_DOCUMENTOS_COBRA_DET D  ON (C.ID_COBRANZA = D.ID_COBRANZA) \n" +
                            " LEFT JOIN S_GEM_PERSONA P ON(C.ID_COBRADOR = P.ID_PERSONA) \n" +
                            " LEFT JOIN S_GEM_PERSONA V ON(D.ID_VENDEDOR = V.ID_PERSONA) \n" +
                            " LEFT JOIN S_SEM_EMPRESA E ON(C.ID_EMPRESA = E.ID_EMPRESA)\n" +
                            " LEFT JOIN CLIENTES S ON (C.COD_CLIENTE = S.COD_CLIENTE) \n" +
                            " LEFT JOIN TABLAS_AUXILIARES A ON (D.FPAGO = A.CODIGO AND A.TIPO = 14) \n" +
                            " LEFT JOIN TABLAS_AUXILIARES T ON(D.TIPDOC = T.CODIGO AND T.TIPO = 2) \n" +
                            " LEFT JOIN CTABNCO B ON(C.CTACORRIENTE_BANCO = B.CODIGO) \n" +
                            " LEFT JOIN  S_GEM_BANCO SB ON(C.ID_BANCO = SB.ID_BANCO AND SB.ID_EMPRESA =C.ID_EMPRESA)" +
                            " WHERE C.N_SERIE_RECIBO  =" + iSERIE_RECIBO  +
                            "  AND C.N_RECIBO =" +  iNUMERO_RECIBO+
                            "  AND C.ID_LOCAL =" +  iID_LOCAL +
                            "  AND C.ID_EMPRESA=" +  iID_EMPRESA +
                            "  AND C.N_RECIBO>0 ";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            String sFECHA="";
            if (cursor.moveToFirst()) {
                do {
                    sFECHA="";
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setCODIGO_FPAGO(Funciones.isNullColumn(cursor,"CODIGO_FPAGO",""));
                    documentos_cobra_cabBE.setFECHA_RECIBO(Funciones.isNullColumn(cursor,"FECHA_RECIBO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_VENDEDOR",0));
                    documentos_cobra_cabBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_cabBE.setSALDO_INICIAL(Funciones.isNullColumn(cursor,"SALDO_INICIAL",0.0));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA","0.0"));
                    documentos_cobra_cabBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL",""));
                    documentos_cobra_cabBE.setTIPODOC(Funciones.isNullColumn(cursor,"TIPODOC",""));
                    documentos_cobra_cabBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setENTIDAD(Funciones.isNullColumn(cursor,"ENTIDAD",""));
                    documentos_cobra_cabBE.setCONSTANCIA(Funciones.isNullColumn(cursor,"CONSTANCIA",""));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_cabBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setRECIBO(Funciones.isNullColumn(cursor,"RECIBO",""));
                    documentos_cobra_cabBE.setPLANILLA(Funciones.isNullColumn(cursor,"PLANILLA","0"));
                    documentos_cobra_cabBE.setNOMCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
                    documentos_cobra_cabBE.setVENDEDOR(Funciones.isNullColumn(cursor,"VENDEDOR",""));
                    documentos_cobra_cabBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    documentos_cobra_cabBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                    documentos_cobra_cabBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    documentos_cobra_cabBE.setEMPRESA(Funciones.isNullColumn(cursor,"EMPRESA",""));
                    documentos_cobra_cabBE.setDISTRITO(Funciones.isNullColumn(cursor,"DISTRITO",""));
                    documentos_cobra_cabBE.setDEPARTAMENTO(Funciones.isNullColumn(cursor,"DEPARTAMENTO",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));


                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public void getRecibo(String sID_EMPRESA,
                          String sID_LOCAL,
                          String sID_COBRADOR,
                          String sCOD_CLIENTE,
                          String sFECHA) {


        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            String SQLRECIBOS="SELECT \n" +
                    " A.ID_COBRANZA,A.COD_CLIENTE,A.N_RECIBO,A.N_SERIE_RECIBO,A.FPAGO,A.ID_COBRADOR,A.FECHA,A.M_COBRANZA,\n" +
                    " A.M_COBRANZA_D,A.SALDO,A.NUMCHEQ,A.FECCHEQ,A.ID_BANCO,A.CTACORRIENTE_BANCO,A.NRO_OPERACION,A.FECHA_DEPOSITO,\n" +
                    " A.COMENTARIO,A.ID_EMPRESA,A.ID_LOCAL,A.ESTADO,A.FECHA_REGISTRO,A.FECHA_MODIFICACION,A.USUARIO_REGISTRO,A.USUARIO_MODIFICACION,\n" +
                    " A.PC_REGISTRO, A.PC_MODIFICACION, A.IP_REGISTRO, A.IP_MODIFICACION,A.ITEM,A.ESTADO_CONCILIADO,A.N_PLANILLA,A.SERIE_PLANILLA,\n" +
                    " A.C_PACKING,A.ID_MOV_BANCO,A.ESTADO_PROCESO,A.T_CAMBIO_TIENDA,A.N_TARJETA,A.ID_MOV_BANCO_ABONO,B.DESCRIPCION AS NOMCTACORRIENTE,SB.BANCO AS BANCODESC,\n" +
                    " TA.DESCRIPCION AS FPAGODESC, PT.DESCRIPCION AS ESTADODESC,A.CODUNC_LOCAL \n" +
                    " FROM S_CCM_DOCUMENTOS_COBRA_CAB A \n" +
                    " INNER JOIN TABLAS_AUXILIARES TA ON(A.FPAGO = TA.CODIGO AND TA.TIPO = 14) \n" +
                    " INNER JOIN PARTABLA PT ON(A.ESTADO = PT.IDTABLA AND PT.GRUPO = 40000)  \n" +
                    " LEFT JOIN  CTABNCO B ON(A.CTACORRIENTE_BANCO = B.CODIGO) \n" +
                    " LEFT JOIN  S_GEM_BANCO SB ON(A.ID_BANCO = SB.ID_BANCO AND SB.ID_EMPRESA = A.ID_EMPRESA)\n" +
                    " WHERE (A.ID_COBRANZA = 0 OR 0=0)\n" +
                    " AND  (A.N_RECIBO = 0 OR 0=0)\n" +
                    " AND  (A.N_SERIE_RECIBO = 0 OR 0=0)\n" +
                    " AND  (A.FPAGO = 'XXX' OR 'XXX'='XXX')\n" +
                    " AND (A.ID_COBRADOR =" + sID_COBRADOR +" OR " + sID_COBRADOR + "= 0)\n" +
                    " AND (A.COD_CLIENTE ='"+ sCOD_CLIENTE+ "' OR '" + sCOD_CLIENTE +"'='XXX')\n" +
                    " AND A.ID_EMPRESA =" + sID_EMPRESA +"\n" +
                    " AND A.ID_LOCAL =" + sID_LOCAL + "\n" +
                    " AND (substr(A.FECHA,7,4) || substr(A.FECHA,4,2) || substr(A.FECHA,1,2) ='" + sFECHA +"' OR '" + sFECHA + "'= '')\n" +
                    " AND A.ESTADO <> 40002";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQLRECIBOS, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {

                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    documentos_cobra_cabBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_cabBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    documentos_cobra_cabBE.setFECCHEQ(Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    documentos_cobra_cabBE.setID_BANCO(Funciones.isNullColumn(cursor,"ID_BANCO",0));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    documentos_cobra_cabBE.setNRO_OPERACION(Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    documentos_cobra_cabBE.setCOMENTARIO(Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    documentos_cobra_cabBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_cabBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    documentos_cobra_cabBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    documentos_cobra_cabBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    documentos_cobra_cabBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    documentos_cobra_cabBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    documentos_cobra_cabBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    documentos_cobra_cabBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    documentos_cobra_cabBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    documentos_cobra_cabBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    documentos_cobra_cabBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    documentos_cobra_cabBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",""));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    documentos_cobra_cabBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    documentos_cobra_cabBE.setN_PLANILLA(Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    documentos_cobra_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    documentos_cobra_cabBE.setESTADO_PROCESO(Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    documentos_cobra_cabBE.setN_TARJETA(Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",0));
                    documentos_cobra_cabBE.setNOMCTACORRIENTE(Funciones.isNullColumn(cursor,"NOMCTACORRIENTE",""));
                    documentos_cobra_cabBE.setFPAGODESC(Funciones.isNullColumn(cursor,"FPAGODESC",""));
                    documentos_cobra_cabBE.setBANCODESC(Funciones.isNullColumn(cursor,"BANCODESC",""));
                    documentos_cobra_cabBE.setESTADODESC(Funciones.isNullColumn(cursor,"ESTADODESC",""));
                    documentos_cobra_cabBE.setCODUNC_LOCAL(Funciones.isNullColumn(cursor,"CODUNC_LOCAL",0));

                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(Documentos_Cobra_CabBE documentos_cobra_cabBE, Context context,Integer iAnticipo){
        String sMensaje="";
        SistemaDAO sistemaDAO=new SistemaDAO();
        Integer ID_COBRANZA=sistemaDAO.MAX_REGISTRO("S_CCM_DOCUMENTOS_COBRA_CAB","ID_CORREL");

        try{
            //ObTenemos el Total Cobrado
            String SQL1="SELECT IFNULL(SUM(M_COBRANZA),0) AS M_COBRANZA FROM S_CCM_DOCUMENTOS_COBRA_DET " +
                    " WHERE CODUNC_LOCAL=" + documentos_cobra_cabBE.getCODUNC_LOCAL().toString()  ;

            Cursor cursor1 = null;
            cursor1= DataBaseHelper.myDataBase.rawQuery(SQL1, null);
            Double M_COBRANZA=0.0;
            try{
                if (cursor1.moveToFirst()) {
                    do {
                        M_COBRANZA= Funciones.isNullColumn(cursor1,"M_COBRANZA",0.0);
                    } while (cursor1.moveToNext());
                }
            } catch (Exception ex) {
            }

            String SQL2="SELECT IFNULL(SUM(SALDO),0) AS SALDO FROM FACTCOBTEMP " +
                    " WHERE CODUNC_LOCAL=" + documentos_cobra_cabBE.getCODUNC_LOCAL().toString();

            Cursor cursor2 = null;
            cursor2= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
            Double SALDO=0.0;
            try{
                if (cursor2.moveToFirst()) {
                    do {
                        SALDO= Funciones.isNullColumn(cursor2,"SALDO",0.0);
                    } while (cursor2.moveToNext());
                }
            } catch (Exception ex) {
            }
            sharedSettings=context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
            editor_Shared= context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

            if(M_COBRANZA!=0.0 && SALDO!=0 && M_COBRANZA>=SALDO) {
                editor_Shared.putString("VALIDASALDO", "1");
            }else{
                editor_Shared.putString("VALIDASALDO", "0");
            }
            editor_Shared.putString("ID_COBRANZA",ID_COBRANZA.toString());
            editor_Shared.commit();

            if(sharedSettings.getString("VALIDASALDO", "0").toString().trim().equals("0")) {
                ContentValues cv = new ContentValues();
                cv.put("ID_COBRANZA", ID_COBRANZA);
                cv.put("COD_CLIENTE", documentos_cobra_cabBE.getCOD_CLIENTE());
                cv.put("N_RECIBO", documentos_cobra_cabBE.getN_RECIBO());
                cv.put("N_SERIE_RECIBO", documentos_cobra_cabBE.getN_SERIE_RECIBO());
                cv.put("FPAGO", documentos_cobra_cabBE.getFPAGO());
                cv.put("ID_COBRADOR", documentos_cobra_cabBE.getID_COBRADOR());
                cv.put("M_COBRANZA", documentos_cobra_cabBE.getM_COBRANZA());
                cv.put("M_COBRANZA_D", documentos_cobra_cabBE.getM_COBRANZA_D());
                cv.put("SALDO", documentos_cobra_cabBE.getSALDO());
                cv.put("FECHA", documentos_cobra_cabBE.getFECHA());
                cv.put("NUMCHEQ", documentos_cobra_cabBE.getNUMCHEQ());
                cv.put("FECCHEQ", documentos_cobra_cabBE.getFECCHEQ());
                cv.put("ID_BANCO", documentos_cobra_cabBE.getID_BANCO());
                cv.put("CTACORRIENTE_BANCO", documentos_cobra_cabBE.getCTACORRIENTE_BANCO());
                cv.put("NRO_OPERACION", documentos_cobra_cabBE.getNRO_OPERACION());
                cv.put("FECHA_DEPOSITO", documentos_cobra_cabBE.getFECHA_DEPOSITO());
                cv.put("COMENTARIO", documentos_cobra_cabBE.getCOMENTARIO());
                cv.put("ID_EMPRESA", documentos_cobra_cabBE.getID_EMPRESA());
                cv.put("ID_LOCAL", documentos_cobra_cabBE.getID_LOCAL());
                cv.put("ESTADO", documentos_cobra_cabBE.getESTADO());
                cv.put("FECHA_REGISTRO", documentos_cobra_cabBE.getFECHA_REGISTRO());
                cv.put("FECHA_MODIFICACION", documentos_cobra_cabBE.getFECHA_MODIFICACION());
                cv.put("USUARIO_REGISTRO", documentos_cobra_cabBE.getUSUARIO_REGISTRO());
                cv.put("USUARIO_MODIFICACION", documentos_cobra_cabBE.getUSUARIO_MODIFICACION());
                cv.put("PC_REGISTRO", documentos_cobra_cabBE.getPC_REGISTRO());
                cv.put("PC_MODIFICACION", documentos_cobra_cabBE.getPC_MODIFICACION());
                cv.put("IP_REGISTRO", documentos_cobra_cabBE.getIP_REGISTRO());
                cv.put("IP_MODIFICACION", documentos_cobra_cabBE.getIP_MODIFICACION());
                cv.put("ITEM", documentos_cobra_cabBE.getITEM());
                cv.put("ESTADO_CONCILIADO", documentos_cobra_cabBE.getESTADO_CONCILIADO());
                cv.put("SERIE_PLANILLA", documentos_cobra_cabBE.getSERIE_PLANILLA());
                cv.put("N_PLANILLA", documentos_cobra_cabBE.getN_PLANILLA());
                cv.put("C_PACKING", documentos_cobra_cabBE.getC_PACKING());
                cv.put("ID_MOV_BANCO", documentos_cobra_cabBE.getID_MOV_BANCO());
                cv.put("ESTADO_PROCESO", documentos_cobra_cabBE.getESTADO_PROCESO());
                cv.put("T_CAMBIO_TIENDA", documentos_cobra_cabBE.getT_CAMBIO_TIENDA());
                cv.put("N_TARJETA", documentos_cobra_cabBE.getN_TARJETA());
                cv.put("ID_MOV_BANCO_ABONO", documentos_cobra_cabBE.getID_MOV_BANCO_ABONO());
                cv.put("FECHA_DEPOSITO_ABONO", documentos_cobra_cabBE.getFECHA_DEPOSITO_ABONO());
                cv.put("LOTE", documentos_cobra_cabBE.getLOTE());
                cv.put("FLAG_COBRANZA", documentos_cobra_cabBE.getFLAG_COBRANZA());
                cv.put("CODUNC_LOCAL", documentos_cobra_cabBE.getCODUNC_LOCAL());
                cv.put("GUARDADO", "0");
                cv.put("SINCRONIZADO", "0");
                DataBaseHelper.myDataBase.insert("S_CCM_DOCUMENTOS_COBRA_CAB", null, cv);
            }

            sMensaje="";
        }catch (Exception ex){
            editor_Shared.putString("ID_COBRANZA","0");
            editor_Shared.commit();
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_COBRANZA",documentos_cobra_cabBE.getID_COBRANZA());
            cv.put("COD_CLIENTE",documentos_cobra_cabBE.getCOD_CLIENTE());
            cv.put("N_RECIBO",documentos_cobra_cabBE.getN_RECIBO());
            cv.put("N_SERIE_RECIBO",documentos_cobra_cabBE.getN_SERIE_RECIBO());
            cv.put("FPAGO",documentos_cobra_cabBE.getFPAGO());
            cv.put("ID_COBRADOR",documentos_cobra_cabBE.getID_COBRADOR());
            cv.put("M_COBRANZA",documentos_cobra_cabBE.getM_COBRANZA());
            cv.put("M_COBRANZA_D",documentos_cobra_cabBE.getM_COBRANZA_D());
            cv.put("SALDO",documentos_cobra_cabBE.getSALDO());
            cv.put("FECHA",documentos_cobra_cabBE.getFECHA());
            cv.put("NUMCHEQ",documentos_cobra_cabBE.getNUMCHEQ());
            cv.put("FECCHEQ",documentos_cobra_cabBE.getFECCHEQ());
            cv.put("ID_BANCO",documentos_cobra_cabBE.getID_BANCO());
            cv.put("CTACORRIENTE_BANCO",documentos_cobra_cabBE.getCTACORRIENTE_BANCO());
            cv.put("NRO_OPERACION",documentos_cobra_cabBE.getNRO_OPERACION());
            cv.put("FECHA_DEPOSITO",documentos_cobra_cabBE.getFECHA_DEPOSITO());
            cv.put("COMENTARIO",documentos_cobra_cabBE.getCOMENTARIO());
            cv.put("ID_EMPRESA",documentos_cobra_cabBE.getID_EMPRESA());
            cv.put("ID_LOCAL",documentos_cobra_cabBE.getID_LOCAL());
            cv.put("ESTADO",documentos_cobra_cabBE.getESTADO());
            cv.put("FECHA_REGISTRO",documentos_cobra_cabBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",documentos_cobra_cabBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",documentos_cobra_cabBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",documentos_cobra_cabBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",documentos_cobra_cabBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",documentos_cobra_cabBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",documentos_cobra_cabBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",documentos_cobra_cabBE.getIP_MODIFICACION());
            cv.put("ITEM",documentos_cobra_cabBE.getITEM());
            cv.put("ESTADO_CONCILIADO",documentos_cobra_cabBE.getESTADO_CONCILIADO());
            cv.put("SERIE_PLANILLA",documentos_cobra_cabBE.getSERIE_PLANILLA());
            cv.put("N_PLANILLA",documentos_cobra_cabBE.getN_PLANILLA());
            cv.put("C_PACKING",documentos_cobra_cabBE.getC_PACKING());
            cv.put("ID_MOV_BANCO",documentos_cobra_cabBE.getID_MOV_BANCO());
            cv.put("ESTADO_PROCESO",documentos_cobra_cabBE.getESTADO_PROCESO());
            cv.put("T_CAMBIO_TIENDA",documentos_cobra_cabBE.getT_CAMBIO_TIENDA());
            cv.put("N_TARJETA",documentos_cobra_cabBE.getN_TARJETA());
            cv.put("ID_MOV_BANCO_ABONO",documentos_cobra_cabBE.getID_MOV_BANCO_ABONO());
            cv.put("FECHA_DEPOSITO_ABONO",documentos_cobra_cabBE.getFECHA_DEPOSITO_ABONO());
            cv.put("LOTE",documentos_cobra_cabBE.getLOTE());
            cv.put("FLAG_COBRANZA",documentos_cobra_cabBE.getFLAG_COBRANZA());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB",cv,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getM_COBRANZA_D())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String updateEstado(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="";
        try{
            //CABECERA
            ContentValues cv_cab = new ContentValues();
            cv_cab.put("GUARDADO",documentos_cobra_cabBE.getGUARDADO());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB",cv_cab,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});
            //DETALLE
            ContentValues cv_det = new ContentValues();
            cv_det.put("GUARDADO",documentos_cobra_cabBE.getGUARDADO());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_DET",cv_det,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});


            ContentValues cv_recibo = new ContentValues();
            cv_recibo.put("NUMERO",documentos_cobra_cabBE.getN_RECIBO());
            DataBaseHelper.myDataBase.update("CCM_RECIBOS_COBRANZA",cv_recibo,"C_RECEPTOR = ? AND N_SERIE = ? AND AUTOMATICO = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRADOR()), String.valueOf(documentos_cobra_cabBE.getN_SERIE_RECIBO()),"S"});


            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


    public String updateDeposito(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="",sFormaPago=documentos_cobra_cabBE.getFPAGO().trim();
        try{
            ContentValues cv = new ContentValues();
            ////TARJETAS DE CRÉDITO
            if(sFormaPago.equals("D") || sFormaPago.equals("V") || sFormaPago.equals("M")  || sFormaPago.equals("S")  || sFormaPago.equals("I") || sFormaPago.equals("H")  ) {
                cv.put("FECHA_DEPOSITO",documentos_cobra_cabBE.getFECHA_DEPOSITO());
                cv.put("N_TARJETA",documentos_cobra_cabBE.getN_TARJETA());
                cv.put("CTACORRIENTE_BANCO",documentos_cobra_cabBE.getCTACORRIENTE_BANCO());

                //Quita valores
                //Deposito
                cv.put("NRO_OPERACION","");
                //Cheque
                cv.put("FECCHEQ","");
                cv.put("NUMCHEQ","");
                cv.put("ID_BANCO","0");
            }
            //DEPOSITO EN BANCO
            if(sFormaPago.equals("P")) {
                cv.put("FECHA_DEPOSITO",documentos_cobra_cabBE.getFECHA_DEPOSITO());
                cv.put("NRO_OPERACION",documentos_cobra_cabBE.getNRO_OPERACION());
                cv.put("CTACORRIENTE_BANCO",documentos_cobra_cabBE.getCTACORRIENTE_BANCO());

                //Quita valores
                //Tarjeta
                cv.put("N_TARJETA","");
                //Cheque
                cv.put("FECCHEQ","");
                cv.put("NUMCHEQ","");
                cv.put("ID_BANCO","0");

            }
            //CHEQUE
            if(sFormaPago.equals("C") ) {
                cv.put("FECCHEQ",documentos_cobra_cabBE.getFECCHEQ());
                cv.put("NUMCHEQ",documentos_cobra_cabBE.getNUMCHEQ());
                cv.put("ID_BANCO",documentos_cobra_cabBE.getID_BANCO());

                //Quita valores
                //Tarjeta
                cv.put("N_TARJETA","");
                //
                cv.put("FECHA_DEPOSITO","");
                cv.put("NRO_OPERACION","");
                cv.put("CTACORRIENTE_BANCO","0");
            }

            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB",cv,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String UpdateCobranzaReasignar(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="";
        String fPago="";
        try{
            ContentValues cv = new ContentValues();
            fPago=documentos_cobra_cabBE.getFPAGO().toString().trim();
            cv.put("FPAGO",fPago);
            cv.put("GUARDADO",documentos_cobra_cabBE.getGUARDADO());

            if(fPago.toString().trim().equals("E")){
                cv.put("CTACORRIENTE_BANCO","0");
                cv.put("NRO_OPERACION","");
                cv.put("FECHA_DEPOSITO","");
                cv.put("NUMCHEQ","");
                cv.put("FECCHEQ","");
                cv.put("ID_BANCO","");
                cv.put("N_TARJETA","");
            }
            //CHEQUE
            if(fPago.equals("C") ) {
                cv.put("NUMCHEQ", documentos_cobra_cabBE.getNUMCHEQ().toString());
                cv.put("FECCHEQ",documentos_cobra_cabBE.getFECCHEQ().toString());
                cv.put("ID_BANCO",documentos_cobra_cabBE.getID_BANCO().toString());

                cv.put("CTACORRIENTE_BANCO","0");
                cv.put("NRO_OPERACION","");
                cv.put("FECHA_DEPOSITO","");

                cv.put("N_TARJETA","");
            }
            //DEPOSITO
            if(fPago.equals("P") ) {
                cv.put("CTACORRIENTE_BANCO",documentos_cobra_cabBE.getCTACORRIENTE_BANCO().toString());
                cv.put("NRO_OPERACION",documentos_cobra_cabBE.getNRO_OPERACION().toString());
                cv.put("FECHA_DEPOSITO",documentos_cobra_cabBE.getFECHA_DEPOSITO().toString());

                cv.put("NUMCHEQ","");
                cv.put("FECCHEQ","");
                cv.put("ID_BANCO","");

                cv.put("N_TARJETA","");
            }

            //VISA
            if(fPago.equals("D") || fPago.equals("V") || fPago.equals("M")  || fPago.equals("S")  || fPago.equals("I")|| fPago.equals("H")  ) {
                cv.put("CTACORRIENTE_BANCO",documentos_cobra_cabBE.getCTACORRIENTE_BANCO().toString());
                cv.put("FECHA_DEPOSITO",documentos_cobra_cabBE.getFECHA_DEPOSITO().toString());
                cv.put("N_TARJETA",documentos_cobra_cabBE.getN_TARJETA().toString());

                cv.put("NUMCHEQ","");
                cv.put("FECCHEQ","");
                cv.put("ID_BANCO","");
            }

            DataBaseHelper.myDataBase.beginTransaction();
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB",cv,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

            ContentValues cv2 = new ContentValues();
            cv2.put("FPAGO",fPago);
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_DET",cv2,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();

            sMensaje="";
        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


    public String delete(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="";
        try{
            //SI YA ESTA ENVIADO LA COBRANZA AL ORACLE
            if(documentos_cobra_cabBE.getSINCRONIZADO()==1) {
                Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
                String sURLCobranza_Cab = ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Delete;

                //SI YA ESTA EN EL REMOTO, ENTONCES LO ELIMINAMOS
                documentos_cobra_cabBL.DeleteRest(
                        documentos_cobra_cabBE.getID_COBRANZA().toString(),
                        documentos_cobra_cabBE.getCODUNC_LOCAL().toString(),
                        sURLCobranza_Cab);
            }else{
                DataBaseHelper.myDataBase.beginTransaction();
                DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_CAB","ID_COBRANZA = ?",
                        new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

                DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET","ID_COBRANZA = ?",
                        new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

                DataBaseHelper.myDataBase.delete("FACTCOBTEMP","ID_COBRANZA = ?",
                        new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

                DataBaseHelper.myDataBase.setTransactionSuccessful();
                DataBaseHelper.myDataBase.endTransaction();
            }

            sMensaje="";
        }catch (Exception ex){
           if(documentos_cobra_cabBE.getSINCRONIZADO()==0){
               DataBaseHelper.myDataBase.endTransaction();
           }

            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }finally {

        }
        return sMensaje;
    }

    public String deleteTemp(){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.beginTransaction();
            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_CAB","GUARDADO = ?",
                    new String[]{"0"});

            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET","GUARDADO = ?",
                    new String[]{"0"});

            DataBaseHelper.myDataBase.delete("FACTCOBTEMP",null,null);

            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();

            sMensaje="";
        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }finally {

        }
        return sMensaje;
    }

    public void getDepositosBy(String iID_EMPRESA,String iID_LOCAL,String iFECHA,String iID_COBRADOR,String iESTADO,String iN_PLANILLA,String iC_PACKING) {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            if ( iC_PACKING == null || iC_PACKING.toString().equals("")) {
                iC_PACKING="0";
            }
            if (iN_PLANILLA  == null || iN_PLANILLA.toString().equals("")) {
                iN_PLANILLA="0";
            }

            String SQL=
                    "SELECT \n" +
                            "C.NRO_OPERACION, SUM(C.M_COBRANZA) AS M_COBRANZA,  SUM(C.M_COBRANZA_D) AS M_COBRANZA_D \n"+
                            "FROM S_CCM_DOCUMENTOS_COBRA_CAB C \n" +
                            "WHERE (C.ID_COBRADOR ="+ iID_COBRADOR + " OR ( " + iID_COBRADOR + " IN(8719,15737) AND C.C_PACKING>0) )\n" +
                            " AND (  substr(C.FECHA,7,4) || substr(C.FECHA,4,2) || substr(C.FECHA,1,2)  BETWEEN '"+ iFECHA +"' AND '" + iFECHA +"'" + " OR '" + iFECHA + "' = '' )" +
                            "AND (C.ESTADO =" +  iESTADO + " OR " +  iESTADO + "= 0)\n" +
                            "AND C.ESTADO <> 40002 \n" +
                            "AND (C.C_PACKING ="+  iC_PACKING + " OR " + iC_PACKING + "= 0)\n" +
                            "AND C.ID_EMPRESA =" +  iID_EMPRESA + "\n" +
                            "AND C.ID_LOCAL =" +  iID_LOCAL + "\n" +
                            "AND (C.N_PLANILLA ="+ iN_PLANILLA + " OR " + iN_PLANILLA + "=0) \n" +
                            "AND C.GUARDADO IN(2,3) \n" +
                            " AND C.FPAGO = 'P' " + //SOLO DEPOSITOS
                            " GROUP BY C.NRO_OPERACION ";
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setNRO_OPERACION (Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getByRecibo(String pSerie, String pNumero, String iID_EMPRESA)
    {
        Cursor cursor = null;
        Documentos_Cobra_CabBE documentos_cobra_cabBE = null;
        try {
            String SQLRECIBOS=" SELECT \n" +
                    " ID_COBRANZA, COD_CLIENTE, N_RECIBO, N_SERIE_RECIBO, FPAGO, ID_COBRADOR, FECHA, M_COBRANZA, M_COBRANZA_D, SALDO, NUMCHEQ, FECCHEQ, ID_BANCO, CTACORRIENTE_BANCO, NRO_OPERACION, FECHA_DEPOSITO, COMENTARIO, ID_EMPRESA, ID_LOCAL, ESTADO, ITEM, ESTADO_CONCILIADO, SERIE_PLANILLA, N_PLANILLA, C_PACKING, ID_MOV_BANCO, ESTADO_PROCESO, T_CAMBIO_TIENDA, N_TARJETA, ID_MOV_BANCO_ABONO " +
                    " FROM S_CCM_DOCUMENTOS_COBRA_CAB A \n" +
                    " WHERE N_RECIBO = " + pNumero + "   AND N_SERIE_RECIBO = " + pSerie + "   AND ID_EMPRESA = " + iID_EMPRESA + "   AND N_RECIBO>0   AND ESTADO <> 40002   AND N_RECIBO IS NOT NULL ";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQLRECIBOS, null);
            lst = new ArrayList<Documentos_Cobra_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {

                    documentos_cobra_cabBE = new Documentos_Cobra_CabBE();
                    documentos_cobra_cabBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_cabBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    documentos_cobra_cabBE.setN_RECIBO(Funciones.isNullColumn(cursor,"N_RECIBO",""));
                    documentos_cobra_cabBE.setN_SERIE_RECIBO(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO",""));
                    documentos_cobra_cabBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_cabBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    documentos_cobra_cabBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_cabBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_cabBE.setM_COBRANZA_D(Funciones.isNullColumn(cursor,"M_COBRANZA_D",0.0));
                    documentos_cobra_cabBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_cabBE.setNUMCHEQ(Funciones.isNullColumn(cursor,"NUMCHEQ",""));
                    documentos_cobra_cabBE.setFECCHEQ(Funciones.isNullColumn(cursor,"FECCHEQ",""));
                    documentos_cobra_cabBE.setID_BANCO(Funciones.isNullColumn(cursor,"ID_BANCO",0));
                    documentos_cobra_cabBE.setCTACORRIENTE_BANCO(Funciones.isNullColumn(cursor,"CTACORRIENTE_BANCO",""));
                    documentos_cobra_cabBE.setNRO_OPERACION(Funciones.isNullColumn(cursor,"NRO_OPERACION",""));
                    documentos_cobra_cabBE.setFECHA_DEPOSITO(Funciones.isNullColumn(cursor,"FECHA_DEPOSITO",""));
                    documentos_cobra_cabBE.setCOMENTARIO(Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    documentos_cobra_cabBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_cabBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    documentos_cobra_cabBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    documentos_cobra_cabBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",""));
                    documentos_cobra_cabBE.setESTADO_CONCILIADO(Funciones.isNullColumn(cursor,"ESTADO_CONCILIADO",""));
                    documentos_cobra_cabBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    documentos_cobra_cabBE.setN_PLANILLA(Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    documentos_cobra_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO",""));
                    documentos_cobra_cabBE.setESTADO_PROCESO(Funciones.isNullColumn(cursor,"ESTADO_PROCESO",""));
                    documentos_cobra_cabBE.setT_CAMBIO_TIENDA(Funciones.isNullColumn(cursor,"T_CAMBIO_TIENDA",""));
                    documentos_cobra_cabBE.setN_TARJETA(Funciones.isNullColumn(cursor,"N_TARJETA",""));
                    documentos_cobra_cabBE.setID_MOV_BANCO_ABONO(Funciones.isNullColumn(cursor,"ID_MOV_BANCO_ABONO",0));


                    lst.add(documentos_cobra_cabBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public String UpdateCobranzaAnular(Documentos_Cobra_CabBE documentos_cobra_cabBE){
        String sMensaje="";
        try{

            if(documentos_cobra_cabBE.getSINCRONIZADO()==1) {
                Documentos_Cobra_CabBL documentos_cobra_cabBL = new Documentos_Cobra_CabBL();
                String sURLCobranza_Cab = ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.bldocumentos_cobra_cab_Anula;

                //SI YA ESTA EN EL REMOTO, ENTONCES LO ANULAMOS
                documentos_cobra_cabBL.AnulaRest(
                        documentos_cobra_cabBE.getCOMENTARIO(),
                        documentos_cobra_cabBE.getID_COBRANZA().toString(),
                        documentos_cobra_cabBE.getCODUNC_LOCAL().toString(),
                        sURLCobranza_Cab);
            }


            ContentValues cv = new ContentValues();

            cv.put("ESTADO","40002");
            cv.put("GUARDADO",documentos_cobra_cabBE.getGUARDADO());

            DataBaseHelper.myDataBase.beginTransaction();
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB",cv,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

            ContentValues cv2 = new ContentValues();
            cv2.put("ESTADO","40002");
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_DET",cv2,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_cabBE.getID_COBRANZA())});

            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();

            sMensaje="";
        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}


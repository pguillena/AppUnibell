package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.FactCobBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class FactCobDAO {
    public ArrayList<FactCobBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        FactCobBE factCobBE = null;
        try {
            String SQL="SELECT " +
                    "COD_CLIENTE,TIPDOC,SERIE_NUM,NUMERO,FECHA," +
                    "F_VENCTO,F_ACEPTACION,F_TRANSFE,ANO,MES," +
                    "LIBRO,VOUCHER,ITEM,TIPO_REFERENCIA,SERIE_REF," +
                    "NRO_REFERENCIA,CONCEPTO,SISTEMA_ORIGEN,VENDED,BANCO," +
                    "L_AGENCIA,L_REFBCO,L_CONDLE,MONEDA,IMPORTE," +
                    "TCAM_IMP,SALDO,TCAM_SAL,NUMERO_CANJE,ESTADO," +
                    "CTACTBLE,F_RECEPCION,C_USUARIO,C_PERFIL,C_CPU," +
                    "FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD,C_CPU_MOD," +
                    "N_SERIE_RECIBO_COBRA,N_RECIBO_COBRA,ANO_PROVISION,MES_CSTGO,ANO_CSTGO," +
                    "LIBRO_CSTGO,VOUCHER_CSTGO "+
                    "FROM CtaBnco ORDER BY CODIGO";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<FactCobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    factCobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC",""));
                    factCobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM",""));
                    factCobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    factCobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    factCobBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO",""));
                    factCobBE.setF_ACEPTACION(Funciones.isNullColumn(cursor,"F_ACEPTACION",""));
                    factCobBE.setF_TRANSFE(Funciones.isNullColumn(cursor,"F_TRANSFE",""));
                    factCobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
                    factCobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    factCobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO",""));
                    factCobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER",""));
                    factCobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
                    factCobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA",""));
                    factCobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
                    factCobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA",""));
                    factCobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO",""));
                    factCobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN",""));
                    factCobBE.setVENDED(Funciones.isNullColumn(cursor,"VENDED",""));
                    factCobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO",""));
                    factCobBE.setL_AGENCIA(Funciones.isNullColumn(cursor,"L_AGENCIA",""));
                    factCobBE.setL_REFBCO(Funciones.isNullColumn(cursor,"L_REFBCO",""));
                    factCobBE.setL_CONDLE(Funciones.isNullColumn(cursor,"L_CONDLE",""));
                    factCobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    factCobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    factCobBE.setTCAM_IMP(Funciones.isNullColumn(cursor,"TCAM_IMP",0.0));
                    factCobBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    factCobBE.setTCAM_SAL(Funciones.isNullColumn(cursor,"TCAM_SAL",0));
                    factCobBE.setNUMERO_CANJE(Funciones.isNullColumn(cursor,"NUMERO_CANJE",""));
                    factCobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    factCobBE.setCTACTBLE(Funciones.isNullColumn(cursor,"CTACTBLE",""));
                    factCobBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION",""));
                    factCobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    factCobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    factCobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    factCobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    factCobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    factCobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    factCobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
                    factCobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    factCobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA",""));
                    factCobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
                    factCobBE.setANO_PROVISION(Funciones.isNullColumn(cursor,"ANO_PROVISION",0));
                    factCobBE.setMES_CSTGO(Funciones.isNullColumn(cursor,"MES_CSTGO",0));
                    factCobBE.setANO_CSTGO(Funciones.isNullColumn(cursor,"ANO_CSTGO",0));
                    factCobBE.setLIBRO_CSTGO(Funciones.isNullColumn(cursor,"LIBRO_CSTGO",""));
                    factCobBE.setVOUCHER_CSTGO(Funciones.isNullColumn(cursor,"VOUCHER_CSTGO",0));
                    lst.add(factCobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getEstadoCuentaCliente(String CODCLI,String iTIPDOC,String iSERIE_NUM,String iNUMERO,String iCONDICION) {
        Cursor cursor = null;
        FactCobBE factCobBE = null;
        try {
            String SQL="SELECT  DISTINCT \n" +
                    "  A.COD_CLIENTE, A.TIPDOC, SUBSTR('0000000000' || A.SERIE_NUM, -4, 4) AS SERIE_NUM, A.NUMERO, A.FECHA, A.F_VENCTO,\n" +
                    "  A.F_ACEPTACION, A.F_TRANSFE, A.ANO, A.MES, A.LIBRO, A.VOUCHER,\n" +
                    "  A.ITEM, A.TIPO_REFERENCIA, A.SERIE_REF, A.NRO_REFERENCIA, A.CONCEPTO, A.SISTEMA_ORIGEN,\n" +
                    "  A.VENDED, A.BANCO, A.L_AGENCIA, A.L_REFBCO, A.L_CONDLE, A.MONEDA,\n" +
                    "  A.IMPORTE, A.TCAM_IMP, A.SALDO, A.TCAM_SAL, A.NUMERO_CANJE, A.ESTADO,\n" +
                    "  A.CTACTBLE, A.F_RECEPCION, A.C_USUARIO, A.C_PERFIL, A.C_CPU, A.FEC_REG,\n" +
                    "  A.C_USUARIO_MOD, A.C_PERFIL_MOD, A.FEC_MOD, A.C_CPU_MOD, A.N_SERIE_RECIBO_COBRA, A.N_RECIBO_COBRA\n" +
                    ",(B.NOMBRES ||' ' ||B.APELLIDO_PATERNO ||' ' || B.APELLIDO_MATERNO)  AS NOMBREVENDEDOR,AUX.ABREVIADA AS NOMBRETIPODOC,\n" +
                    " D2.URL_PDF,D2.URL_XML,D2.I_RESPUESTA,D2.COND_PAG AS COND_PAG ,D2.COD_VENDE AS COD_VEND_ORIGEN,"+
                    " IFNULL(A.DIAS,0) AS DIAS,0 AS COBRANZA,IFNULL(AGREGADO,0) AS AGREGADO " +
                    " FROM FACTCOB A \n" +
                  //" LEFT JOIN DOCUVENT D ON (D.TIPODOC=A.TIPDOC AND D.SERIE = A.SERIE_NUM AND D.NUMERO = A.NUMERO AND D.COD_CLIENTE = A.COD_CLIENTE) "+
                    " LEFT JOIN DOCUVENT D2 ON (D2.TIPODOC=A.TIPDOC AND D2.SERIE = A.SERIE_NUM AND D2.NUMERO = A.NUMERO AND D2.TIPODOC IN('08','07','01','03') AND D2.COD_CLIENTE = A.COD_CLIENTE)"+
                    " LEFT JOIN MVENDEDOR B ON(A.VENDED=B.C_VENDEDOR)  \n" +
                    " LEFT JOIN TABLAS_AUXILIARES AUX ON(A.TIPDOC = AUX.CODIGO AND AUX.TIPO = 2) \n" +
                    " WHERE (A.COD_CLIENTE='" + CODCLI + "' OR '" + CODCLI + "'='XXX')" +
                    "  AND (A.TIPDOC='" + iTIPDOC + "' OR '" + iTIPDOC + "'='XXX')\n" +
                    "  AND (A.SERIE_NUM='" + iSERIE_NUM + "' OR '" + iSERIE_NUM + "'='XXX')\n" +
                    "  AND (A.NUMERO='" + iNUMERO + "'OR '" +  iNUMERO + "'='XXX')\n" +
                    "  AND A.TIPDOC<>'41'\n" +
                    "  AND A.TIPDOC <>'PF' \n" +
                    "  AND A.TIPDOC<>'PB'\n" +
                    "  AND A.SALDO <>0\n" +
                    "  AND A.VENDED <> '989' \n" +
                    "  ORDER BY A.FECHA ASC, A.SALDO ASC;";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<FactCobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE","").replace("null",""));
                    factCobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC","").replace("null",""));
                    factCobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM","").replace("null",""));
                    factCobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    factCobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA","").replace("null",""));
                    factCobBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO","").replace("null",""));
                    factCobBE.setF_ACEPTACION(Funciones.isNullColumn(cursor,"F_ACEPTACION","").replace("null",""));
                    factCobBE.setF_TRANSFE(Funciones.isNullColumn(cursor,"F_TRANSFE","").replace("null",""));
                    factCobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
                    factCobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    factCobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO","").replace("null",""));
                    factCobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER","").replace("null",""));
                    factCobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
                    factCobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA","").replace("null",""));
                    factCobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
                    factCobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA","").replace("null",""));
                    factCobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO","").replace("null",""));
                    factCobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN","").replace("null",""));
                    factCobBE.setVENDED(Funciones.isNullColumn(cursor,"VENDED","").replace("null",""));
                    factCobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO","").replace("null",""));
                    factCobBE.setL_AGENCIA(Funciones.isNullColumn(cursor,"L_AGENCIA","").replace("null",""));
                    factCobBE.setL_REFBCO(Funciones.isNullColumn(cursor,"L_REFBCO","").replace("null",""));
                    factCobBE.setL_CONDLE(Funciones.isNullColumn(cursor,"L_CONDLE","").replace("null",""));
                    factCobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    factCobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    factCobBE.setTCAM_IMP(Funciones.isNullColumn(cursor,"TCAM_IMP",0.0));
                    factCobBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    factCobBE.setTCAM_SAL(Funciones.isNullColumn(cursor,"TCAM_SAL",0));
                    factCobBE.setNUMERO_CANJE(Funciones.isNullColumn(cursor,"NUMERO_CANJE","").replace("null",""));
                    factCobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO","").replace("null",""));
                    factCobBE.setCTACTBLE(Funciones.isNullColumn(cursor,"CTACTBLE","").replace("null",""));
                    factCobBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION","").replace("null",""));
                    factCobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO","").replace("null",""));
                    factCobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL","").replace("null",""));
                    factCobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU","").replace("null",""));
                    factCobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG","").replace("null",""));
                    factCobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD","").replace("null",""));
                    factCobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD","").replace("null",""));
                    factCobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD","").replace("null",""));
                    factCobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD","").replace("null",""));
                    factCobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA","").replace("null",""));
                    factCobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
                    factCobBE.setNOMBREVENDEDOR(Funciones.isNullColumn(cursor,"NOMBREVENDEDOR","").replace("null",""));
                    factCobBE.setNOMBRETIPODOC(Funciones.isNullColumn(cursor,"NOMBRETIPODOC","").replace("null",""));
                    factCobBE.setURL_PDF(Funciones.isNullColumn(cursor,"URL_PDF","").replace("null",""));
                    factCobBE.setURL_XML(Funciones.isNullColumn(cursor,"URL_XML","").replace("null",""));
                    factCobBE.setI_RESPUESTA(Funciones.isNullColumn(cursor,"I_RESPUESTA","").replace("null",""));
                    factCobBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG","").replace("null",""));
                    factCobBE.setCOD_VEND_ORIGEN(Funciones.isNullColumn(cursor,"COD_VEND_ORIGEN","").replace("null",""));
                    factCobBE.setDIAS(Funciones.isNullColumn(cursor,"DIAS",0));
                    factCobBE.setCOBRANZA(Funciones.isNullColumn(cursor,"COBRANZA",0.0));
                    factCobBE.setVAMORTIZADO(0.0);
                    factCobBE.setAGREGADO(Funciones.isNullColumn(cursor,"AGREGADO",0));
                    lst.add(factCobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getCobranza(String CODCLI, String iTIPDOC,String iSERIE_NUM,String iNUMERO,String iCODUNC_LOCAL,String iID_COBRANZA) {
        Cursor cursor = null;
        Cursor cursorValida = null;
        FactCobBE factCobBE = null;
        Integer ID_COBRANZA=0;
        String SQL1,SQL2;
        try {
         String SQL_COBRANZA="select IFNULL(max(ID_COBRANZA),0) AS ID_COBRANZA FROM S_CCM_DOCUMENTOS_COBRA_CAB " +
                 " WHERE CODUNC_LOCAL="+iCODUNC_LOCAL + " AND ID_COBRANZA<>" + iID_COBRANZA;

            cursorValida= DataBaseHelper.myDataBase.rawQuery(SQL_COBRANZA, null);
            if (cursorValida.moveToFirst()) {
                do {
                    ID_COBRANZA=  Funciones.isNullColumn(cursorValida,"ID_COBRANZA",0);
                } while (cursorValida.moveToNext());
            }

         //Tabla Temporal
            //SUBSTR('0000000000' || A.SERIE_NUM, -4, 4) AS SERIE_NUM
                SQL1 = "SELECT \n" +
                       "  A.COD_CLIENTE, A.TIPDOC, A.SERIE_NUM, A.NUMERO, A.FECHA, A.F_VENCTO,\n" +
                       "  A.F_ACEPTACION, A.F_TRANSFE, A.ANO, A.MES, A.LIBRO, A.VOUCHER,\n" +
                       "  A.ITEM, A.TIPO_REFERENCIA, A.SERIE_REF, A.NRO_REFERENCIA, A.CONCEPTO, A.SISTEMA_ORIGEN,\n" +
                       "  A.VENDED, A.BANCO, A.L_AGENCIA, A.L_REFBCO, A.L_CONDLE, A.MONEDA,\n" +
                       "  A.IMPORTE, A.TCAM_IMP, A.SALDO, A.TCAM_SAL, A.NUMERO_CANJE, A.ESTADO,\n" +
                       "  A.CTACTBLE, A.F_RECEPCION, A.C_USUARIO, A.C_PERFIL, A.C_CPU, A.FEC_REG,\n" +
                       "  A.C_USUARIO_MOD, A.C_PERFIL_MOD, A.FEC_MOD, A.C_CPU_MOD, A.N_SERIE_RECIBO_COBRA, A.N_RECIBO_COBRA,\n" +
                       "  A.ANO_PROVISION,A.MES_CSTGO,A.ANO_CSTGO,A.LIBRO_CSTGO,A.VOUCHER_CSTGO," +
                       " (B.NOMBRES ||' ' ||B.APELLIDO_PATERNO ||' ' || B.APELLIDO_MATERNO)  AS NOMBREVENDEDOR,AUX.ABREVIADA AS NOMBRETIPODOC,\n" +
                       " D.URL_PDF,D.URL_XML,D.I_RESPUESTA,D2.COND_PAG AS COND_PAG ,D2.COD_VENDE AS COD_VEND_ORIGEN," +
                       " IFNULL(A.DIAS,0) AS DIAS,0 AS COBRANZA,IFNULL(AGREGADO,0) AS AGREGADO,C.ID_COBRANZA " +
                       " FROM FACTCOBTEMP A \n" +
                       " LEFT  JOIN DOCUVENT D ON D.TIPODOC=A.TIPDOC AND D.SERIE = A.SERIE_NUM AND D.NUMERO = A.NUMERO " +
                       " LEFT JOIN DOCUVENT D2 ON D2.TIPODOC=A.TIPDOC AND D2.SERIE = A.SERIE_NUM AND D2.NUMERO = A.NUMERO AND D2.TIPODOC IN('08','07','01','03')" +
                       " LEFT JOIN MVENDEDOR B ON(A.VENDED=B.C_VENDEDOR)  \n" +
                       " LEFT JOIN TABLAS_AUXILIARES AUX ON(A.TIPDOC = AUX.CODIGO AND AUX.TIPO = 2) \n" +
                       " INNER JOIN S_CCM_DOCUMENTOS_COBRA_CAB C ON C.ID_COBRANZA=A.ID_COBRANZA \n" +
                       " WHERE A.SALDO <>0 AND C.CODUNC_LOCAL=" + iCODUNC_LOCAL + " AND C.ID_COBRANZA=" + ID_COBRANZA.toString() +
                       "  ORDER BY A.FECHA ASC, A.SALDO ASC";

            //Tabla de Cobranzas Real
            //SUBSTR('0000000000' || A.SERIE_NUM, -4, 4) AS SERIE_NUM
            SQL2="SELECT \n" +
                    "  A.COD_CLIENTE, A.TIPDOC,A.SERIE_NUM, A.NUMERO, A.FECHA, A.F_VENCTO,\n" +
                    "  A.F_ACEPTACION, A.F_TRANSFE, A.ANO, A.MES, A.LIBRO, A.VOUCHER,\n" +
                    "  A.ITEM, A.TIPO_REFERENCIA, A.SERIE_REF, A.NRO_REFERENCIA, A.CONCEPTO, A.SISTEMA_ORIGEN,\n" +
                    "  A.VENDED, A.BANCO, A.L_AGENCIA, A.L_REFBCO, A.L_CONDLE, A.MONEDA,\n" +
                    "  A.IMPORTE, A.TCAM_IMP,A.SALDO AS SALDO, A.TCAM_SAL, A.NUMERO_CANJE, A.ESTADO,\n" +
                    "  A.CTACTBLE, A.F_RECEPCION, A.C_USUARIO, A.C_PERFIL, A.C_CPU, A.FEC_REG,\n" +
                    "  A.C_USUARIO_MOD, A.C_PERFIL_MOD, A.FEC_MOD, A.C_CPU_MOD, A.N_SERIE_RECIBO_COBRA, A.N_RECIBO_COBRA,\n" +
                    "  A.ANO_PROVISION,A.MES_CSTGO,A.ANO_CSTGO,A.LIBRO_CSTGO,A.VOUCHER_CSTGO,"+
                    " (B.NOMBRES ||' ' ||B.APELLIDO_PATERNO ||' ' || B.APELLIDO_MATERNO)  AS NOMBREVENDEDOR,AUX.ABREVIADA AS NOMBRETIPODOC,\n" +
                    " D.URL_PDF,D.URL_XML,D.I_RESPUESTA,D2.COND_PAG AS COND_PAG ,D2.COD_VENDE AS COD_VEND_ORIGEN,"+
                    " IFNULL(A.DIAS,0) AS DIAS,IFNULL(A.COBRANZA,0) AS COBRANZA,IFNULL(AGREGADO,0) AS AGREGADO,0 as ID_COBRANZA " +
                    " FROM FACTCOB A \n" +
                    " LEFT  JOIN DOCUVENT D ON D.TIPODOC=A.TIPDOC AND D.SERIE = A.SERIE_NUM AND D.NUMERO = A.NUMERO "+
                    " LEFT JOIN DOCUVENT D2 ON D2.TIPODOC=A.TIPDOC AND D2.SERIE = A.SERIE_NUM AND D2.NUMERO = A.NUMERO AND D2.TIPODOC IN('08','07','01','03')"+
                    " LEFT JOIN MVENDEDOR B ON(A.VENDED=B.C_VENDEDOR)  \n" +
                    " LEFT JOIN TABLAS_AUXILIARES AUX ON(A.TIPDOC = AUX.CODIGO AND AUX.TIPO = 2) \n" +
                    " WHERE (A.COD_CLIENTE='" + CODCLI + "' OR '" + CODCLI + "'='XXX')" +
                    "  AND (A.TIPDOC='" + iTIPDOC + "' OR '" + iTIPDOC + "'='XXX')\n" +
                    "  AND (A.SERIE_NUM='" + iSERIE_NUM + "' OR '" + iSERIE_NUM + "'='XXX')\n" +
                    "  AND (A.NUMERO='" + iNUMERO + "'OR '" +  iNUMERO + "'='XXX')\n" +
                    "  AND A.TIPDOC<>'41'\n" +
                    "  AND A.TIPDOC <>'PF' \n" +
                    "  AND A.TIPDOC<>'PB'\n" +
                    "  AND A.SALDO <>0 \n" +
                    "  AND A.VENDED <> '989' \n" +
                    "  ORDER BY A.FECHA ASC, A.SALDO ASC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL1, null);
            if(cursor.getCount()==0){
                cursor= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
            }
            lst = new ArrayList<FactCobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE","").replace("null",""));
                    factCobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC","").replace("null",""));
                    factCobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM","").replace("null",""));
                    factCobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    factCobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA","").replace("null",""));
                    factCobBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO","").replace("null",""));
                    factCobBE.setF_ACEPTACION(Funciones.isNullColumn(cursor,"F_ACEPTACION","").replace("null",""));
                    factCobBE.setF_TRANSFE(Funciones.isNullColumn(cursor,"F_TRANSFE","").replace("null",""));
                    factCobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
                    factCobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    factCobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO","").replace("null",""));
                    factCobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER","").replace("null",""));
                    factCobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
                    factCobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA","").replace("null",""));
                    factCobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
                    factCobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA","").replace("null",""));
                    factCobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO","").replace("null",""));
                    factCobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN","").replace("null",""));
                    factCobBE.setVENDED(Funciones.isNullColumn(cursor,"VENDED","").replace("null",""));
                    factCobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO","").replace("null",""));
                    factCobBE.setL_AGENCIA(Funciones.isNullColumn(cursor,"L_AGENCIA","").replace("null",""));
                    factCobBE.setL_REFBCO(Funciones.isNullColumn(cursor,"L_REFBCO","").replace("null",""));
                    factCobBE.setL_CONDLE(Funciones.isNullColumn(cursor,"L_CONDLE","").replace("null",""));
                    factCobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    factCobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    factCobBE.setTCAM_IMP(Funciones.isNullColumn(cursor,"TCAM_IMP",0.0));
                    factCobBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    factCobBE.setTCAM_SAL(Funciones.isNullColumn(cursor,"TCAM_SAL",0));
                    factCobBE.setNUMERO_CANJE(Funciones.isNullColumn(cursor,"NUMERO_CANJE","").replace("null",""));
                    factCobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO","").replace("null",""));
                    factCobBE.setCTACTBLE(Funciones.isNullColumn(cursor,"CTACTBLE","").replace("null",""));
                    factCobBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION","").replace("null",""));
                    factCobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO","").replace("null",""));
                    factCobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL","").replace("null",""));
                    factCobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU","").replace("null",""));
                    factCobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG","").replace("null",""));
                    factCobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD","").replace("null",""));
                    factCobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD","").replace("null",""));
                    factCobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD","").replace("null",""));
                    factCobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD","").replace("null",""));
                    factCobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA","").replace("null",""));
                    factCobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
                    factCobBE.setNOMBREVENDEDOR(Funciones.isNullColumn(cursor,"NOMBREVENDEDOR","").replace("null",""));
                    factCobBE.setNOMBRETIPODOC(Funciones.isNullColumn(cursor,"NOMBRETIPODOC","").replace("null",""));
                    factCobBE.setURL_PDF(Funciones.isNullColumn(cursor,"URL_PDF","").replace("null",""));
                    factCobBE.setURL_XML(Funciones.isNullColumn(cursor,"URL_XML","").replace("null",""));
                    factCobBE.setI_RESPUESTA(Funciones.isNullColumn(cursor,"I_RESPUESTA","").replace("null",""));
                    factCobBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG","").replace("null",""));
                    factCobBE.setCOD_VEND_ORIGEN(Funciones.isNullColumn(cursor,"COD_VEND_ORIGEN","").replace("null",""));
                    factCobBE.setDIAS(Funciones.isNullColumn(cursor,"DIAS",0));
                    factCobBE.setCOBRANZA(Funciones.isNullColumn(cursor,"COBRANZA",0.0));
                    factCobBE.setANO_PROVISION(Funciones.isNullColumn(cursor,"ANO_PROVISION",0));
                    factCobBE.setMES_CSTGO(Funciones.isNullColumn(cursor,"MES_CSTGO",0));
                    factCobBE.setANO_CSTGO(Funciones.isNullColumn(cursor,"ANO_CSTGO",0));
                    factCobBE.setLIBRO_CSTGO(Funciones.isNullColumn(cursor,"LIBRO_CSTGO",""));
                    factCobBE.setVOUCHER_CSTGO(Funciones.isNullColumn(cursor,"VOUCHER_CSTGO",0));
                    factCobBE.setVAMORTIZADO(0.0);
                    factCobBE.setAGREGADO(Funciones.isNullColumn(cursor,"AGREGADO",0));
                    factCobBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));

                    lst.add(factCobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getCobranzaTemp(String iCODUNC_LOCAL,String iID_COBRANZA) {
        Cursor cursor = null;
        FactCobBE factCobBE = null;
        try {
            // SUBSTR('0000000000' || A.SERIE_NUM, -4, 4) AS SERIE_NUM
            String SQL="SELECT \n" +
                    "  A.COD_CLIENTE, A.TIPDOC,A.SERIE_NUM, A.NUMERO, A.FECHA, A.F_VENCTO,\n" +
                    "  A.F_ACEPTACION, A.F_TRANSFE, A.ANO, A.MES, A.LIBRO, A.VOUCHER,\n" +
                    "  A.ITEM, A.TIPO_REFERENCIA, A.SERIE_REF, A.NRO_REFERENCIA, A.CONCEPTO, A.SISTEMA_ORIGEN,\n" +
                    "  A.VENDED, A.BANCO, A.L_AGENCIA, A.L_REFBCO, A.L_CONDLE, A.MONEDA,\n" +
                    "  A.IMPORTE, A.TCAM_IMP, A.SALDO, A.TCAM_SAL, A.NUMERO_CANJE, A.ESTADO,\n" +
                    "  A.CTACTBLE, A.F_RECEPCION, A.C_USUARIO, A.C_PERFIL, A.C_CPU, A.FEC_REG,\n" +
                    "  A.C_USUARIO_MOD, A.C_PERFIL_MOD, A.FEC_MOD, A.C_CPU_MOD, A.N_SERIE_RECIBO_COBRA, A.N_RECIBO_COBRA,\n" +
                    "  A.ANO_PROVISION,A.MES_CSTGO,A.ANO_CSTGO,A.LIBRO_CSTGO,A.VOUCHER_CSTGO,"+
                    " (B.NOMBRES ||' ' ||B.APELLIDO_PATERNO ||' ' || B.APELLIDO_MATERNO)  AS NOMBREVENDEDOR,AUX.ABREVIADA AS NOMBRETIPODOC,\n" +
                    " D.URL_PDF,D.URL_XML,D.I_RESPUESTA,D2.COND_PAG AS COND_PAG ,D2.COD_VENDE AS COD_VEND_ORIGEN,"+
                    " IFNULL(A.DIAS,0) AS DIAS,IFNULL(A.COBRANZA,0) AS COBRANZA,IFNULL(AGREGADO,0) AS AGREGADO,C.ID_COBRANZA " +
                    " FROM FACTCOBTEMP A \n" +
                    " LEFT  JOIN DOCUVENT D ON D.TIPODOC=A.TIPDOC AND D.SERIE = A.SERIE_NUM AND D.NUMERO = A.NUMERO "+
                    " LEFT JOIN DOCUVENT D2 ON D2.TIPODOC=A.TIPDOC AND D2.SERIE = A.SERIE_NUM AND D2.NUMERO = A.NUMERO AND D2.TIPODOC IN('08','07','01','03')"+
                    " LEFT JOIN MVENDEDOR B ON(A.VENDED=B.C_VENDEDOR)  \n" +
                    " LEFT JOIN TABLAS_AUXILIARES AUX ON(A.TIPDOC = AUX.CODIGO AND AUX.TIPO = 2) \n" +
                    " INNER JOIN S_CCM_DOCUMENTOS_COBRA_CAB C ON C.ID_COBRANZA=A.ID_COBRANZA \n"+
                    " WHERE C.CODUNC_LOCAL=" + iCODUNC_LOCAL + " AND C.ID_COBRANZA="+ iID_COBRANZA +
                    "  ORDER BY A.FECHA ASC, A.SALDO ASC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<FactCobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE","").replace("null",""));
                    factCobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC","").replace("null",""));
                    factCobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM","").replace("null",""));
                    factCobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    factCobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA","").replace("null",""));
                    factCobBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO","").replace("null",""));
                    factCobBE.setF_ACEPTACION(Funciones.isNullColumn(cursor,"F_ACEPTACION","").replace("null",""));
                    factCobBE.setF_TRANSFE(Funciones.isNullColumn(cursor,"F_TRANSFE","").replace("null",""));
                    factCobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
                    factCobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    factCobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO","").replace("null",""));
                    factCobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER","").replace("null",""));
                    factCobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
                    factCobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA","").replace("null",""));
                    factCobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
                    factCobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA","").replace("null",""));
                    factCobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO","").replace("null",""));
                    factCobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN","").replace("null",""));
                    factCobBE.setVENDED(Funciones.isNullColumn(cursor,"VENDED","").replace("null",""));
                    factCobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO","").replace("null",""));
                    factCobBE.setL_AGENCIA(Funciones.isNullColumn(cursor,"L_AGENCIA","").replace("null",""));
                    factCobBE.setL_REFBCO(Funciones.isNullColumn(cursor,"L_REFBCO","").replace("null",""));
                    factCobBE.setL_CONDLE(Funciones.isNullColumn(cursor,"L_CONDLE","").replace("null",""));
                    factCobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    factCobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    factCobBE.setTCAM_IMP(Funciones.isNullColumn(cursor,"TCAM_IMP",0.0));
                    factCobBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    factCobBE.setTCAM_SAL(Funciones.isNullColumn(cursor,"TCAM_SAL",0));
                    factCobBE.setNUMERO_CANJE(Funciones.isNullColumn(cursor,"NUMERO_CANJE","").replace("null",""));
                    factCobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO","").replace("null",""));
                    factCobBE.setCTACTBLE(Funciones.isNullColumn(cursor,"CTACTBLE","").replace("null",""));
                    factCobBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION","").replace("null",""));
                    factCobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO","").replace("null",""));
                    factCobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL","").replace("null",""));
                    factCobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU","").replace("null",""));
                    factCobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG","").replace("null",""));
                    factCobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD","").replace("null",""));
                    factCobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD","").replace("null",""));
                    factCobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD","").replace("null",""));
                    factCobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD","").replace("null",""));
                    factCobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA","").replace("null",""));
                    factCobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
                    factCobBE.setNOMBREVENDEDOR(Funciones.isNullColumn(cursor,"NOMBREVENDEDOR","").replace("null",""));
                    factCobBE.setNOMBRETIPODOC(Funciones.isNullColumn(cursor,"NOMBRETIPODOC","").replace("null",""));
                    factCobBE.setURL_PDF(Funciones.isNullColumn(cursor,"URL_PDF","").replace("null",""));
                    factCobBE.setURL_XML(Funciones.isNullColumn(cursor,"URL_XML","").replace("null",""));
                    factCobBE.setI_RESPUESTA(Funciones.isNullColumn(cursor,"I_RESPUESTA","").replace("null",""));
                    factCobBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG","").replace("null",""));
                    factCobBE.setCOD_VEND_ORIGEN(Funciones.isNullColumn(cursor,"COD_VEND_ORIGEN","").replace("null",""));
                    factCobBE.setDIAS(Funciones.isNullColumn(cursor,"DIAS",0));
                    factCobBE.setCOBRANZA(Funciones.isNullColumn(cursor,"COBRANZA",0.0));
                    factCobBE.setANO_PROVISION(Funciones.isNullColumn(cursor,"ANO_PROVISION",0));
                    factCobBE.setMES_CSTGO(Funciones.isNullColumn(cursor,"MES_CSTGO",0));
                    factCobBE.setANO_CSTGO(Funciones.isNullColumn(cursor,"ANO_CSTGO",0));
                    factCobBE.setLIBRO_CSTGO(Funciones.isNullColumn(cursor,"LIBRO_CSTGO",""));
                    factCobBE.setVOUCHER_CSTGO(Funciones.isNullColumn(cursor,"VOUCHER_CSTGO",0));
                    factCobBE.setVAMORTIZADO(0.0);
                    factCobBE.setAGREGADO(Funciones.isNullColumn(cursor,"AGREGADO",0));
                    factCobBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    lst.add(factCobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getPagosBy(String sID_EMPRESA,String sID_LOCAL,String sID_COBRANZA,String sID_COBRADOR,String sCOD_CLIENTE,String sN_RECIBO,String sN_SERIE_RECIBO,String sFPAGO) {
        Cursor cursor = null;
        FactCobBE factCobBE = null;
        try {
            String SQLPAGOS="SELECT \n" +
                    " A.COD_CLIENTE,A.TIPDOC,A.SERIE_NUM,A.NUMERO,A.FECHA,A.F_VENCTO,A.F_ACEPTACION,A.F_TRANSFE,\n" +
                    " A.ANO,A.MES,A.LIBRO,A.VOUCHER,A.ITEM,A.TIPO_REFERENCIA,A.SERIE_REF,A.NRO_REFERENCIA,\n" +
                    " A.CONCEPTO,A.SISTEMA_ORIGEN,A.VENDED,A.BANCO,A.L_AGENCIA,A.L_REFBCO,A.L_CONDLE,A.MONEDA,\n" +
                    " A.IMPORTE,A.TCAM_IMP,A.SALDO,A.TCAM_SAL,A.NUMERO_CANJE,A.ESTADO,A.CTACTBLE,A.F_RECEPCION,\n" +
                    " A.C_USUARIO,A.C_PERFIL,A.C_CPU,A.FEC_REG,A.C_USUARIO_MOD,A.C_PERFIL_MOD,A.FEC_MOD,A.C_CPU_MOD,\n" +
                    " A.N_SERIE_RECIBO_COBRA, A.N_RECIBO_COBRA,(B.NOMBRES ||' ' ||B.APELLIDO_PATERNO ||' ' || B.APELLIDO_MATERNO)  AS NOMBREVENDEDOR,\n" +
                    " AUX.ABREVIADA AS NOMBRETIPODOC,D1.URL_PDF,D1.URL_XML,D1.I_RESPUESTA,D2.COND_PAG,D2.COD_VENDE\n" +
                    " FROM FACTCOB A LEFT JOIN MVENDEDOR B ON(A.VENDED=B.C_VENDEDOR) \n" +
                    " LEFT JOIN DOCUVENT D1 ON D1.TIPODOC = A.TIPDOC AND D1.SERIE = A.SERIE_NUM AND D1.NUMERO = A.NUMERO\n" +
                    " LEFT JOIN DOCUVENT D2 ON D2.TIPODOC = A.TIPDOC AND D2.SERIE = A.SERIE_NUM AND D2.NUMERO = A.NUMERO AND D2.TIPODOC IN('08','07','01','03')\n" +
                    " LEFT JOIN TABLAS_AUXILIARES AUX ON(A.TIPDOC = AUX.CODIGO AND AUX.TIPO = 2) \n" +
                    " WHERE (A.COD_CLIENTE='"+ sCOD_CLIENTE+ "' OR '" + sCOD_CLIENTE +"'='XXX')\n" +
                    " AND (A.TIPDOC='XXX' OR 'XXX'='XXX')\n" +
                    " AND (A.SERIE_NUM='XXX' OR 'XXX'='XXX')\n" +
                    " AND (A.NUMERO='XXX' OR 'XXX'='XXX')\n" +
                    " AND A.TIPDOC<>'41'\n" +
                    " AND A.TIPDOC <>'PF'\n" +
                    " AND A.TIPDOC<>'PB'\n" +
                    " AND A.SALDO <>0\n" +
                    " AND A.VENDED <> '989'\n" +
                    " ORDER BY A.FECHA ASC, A.SALDO ASC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQLPAGOS, null);
            lst = new ArrayList<FactCobBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    factCobBE = new FactCobBE();
                    factCobBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE","").replace("null",""));
                    factCobBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC","").replace("null",""));
                    factCobBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM","").replace("null",""));
                    factCobBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",0));
                    factCobBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA","").replace("null",""));
                    factCobBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO","").replace("null",""));
                    factCobBE.setF_ACEPTACION(Funciones.isNullColumn(cursor,"F_ACEPTACION","").replace("null",""));
                    factCobBE.setF_TRANSFE(Funciones.isNullColumn(cursor,"F_TRANSFE","").replace("null",""));
                    factCobBE.setANO(Funciones.isNullColumn(cursor,"ANO",0));
                    factCobBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    factCobBE.setLIBRO(Funciones.isNullColumn(cursor,"LIBRO","").replace("null",""));
                    factCobBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER","").replace("null",""));
                    factCobBE.setITEM(Funciones.isNullColumn(cursor,"ITEM",0));
                    factCobBE.setTIPO_REFERENCIA(Funciones.isNullColumn(cursor,"TIPO_REFERENCIA","").replace("null",""));
                    factCobBE.setSERIE_REF(Funciones.isNullColumn(cursor,"SERIE_REF",""));
                    factCobBE.setNRO_REFERENCIA(Funciones.isNullColumn(cursor,"NRO_REFERENCIA","").replace("null",""));
                    factCobBE.setCONCEPTO(Funciones.isNullColumn(cursor,"CONCEPTO","").replace("null",""));
                    factCobBE.setSISTEMA_ORIGEN(Funciones.isNullColumn(cursor,"SISTEMA_ORIGEN","").replace("null",""));
                    factCobBE.setVENDED(Funciones.isNullColumn(cursor,"VENDED","").replace("null",""));
                    factCobBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO","").replace("null",""));
                    factCobBE.setL_AGENCIA(Funciones.isNullColumn(cursor,"L_AGENCIA","").replace("null",""));
                    factCobBE.setL_REFBCO(Funciones.isNullColumn(cursor,"L_REFBCO","").replace("null",""));
                    factCobBE.setL_CONDLE(Funciones.isNullColumn(cursor,"L_CONDLE","").replace("null",""));
                    factCobBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    factCobBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    factCobBE.setTCAM_IMP(Funciones.isNullColumn(cursor,"TCAM_IMP",0.0));
                    factCobBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    factCobBE.setTCAM_SAL(Funciones.isNullColumn(cursor,"TCAM_SAL",0));
                    factCobBE.setNUMERO_CANJE(Funciones.isNullColumn(cursor,"NUMERO_CANJE","").replace("null",""));
                    factCobBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO","").replace("null",""));
                    factCobBE.setCTACTBLE(Funciones.isNullColumn(cursor,"CTACTBLE","").replace("null",""));
                    factCobBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION","").replace("null",""));
                    factCobBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO","").replace("null",""));
                    factCobBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL","").replace("null",""));
                    factCobBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU","").replace("null",""));
                    factCobBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG","").replace("null",""));
                    factCobBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD","").replace("null",""));
                    factCobBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD","").replace("null",""));
                    factCobBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD","").replace("null",""));
                    factCobBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD","").replace("null",""));
                    factCobBE.setN_SERIE_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_SERIE_RECIBO_COBRA","").replace("null",""));
                    factCobBE.setN_RECIBO_COBRA(Funciones.isNullColumn(cursor,"N_RECIBO_COBRA",0));
                    factCobBE.setNOMBREVENDEDOR(Funciones.isNullColumn(cursor,"NOMBREVENDEDOR","").replace("null",""));
                    factCobBE.setNOMBRETIPODOC(Funciones.isNullColumn(cursor,"NOMBRETIPODOC","").replace("null",""));
                    factCobBE.setURL_PDF(Funciones.isNullColumn(cursor,"URL_PDF","").replace("null",""));
                    factCobBE.setURL_XML(Funciones.isNullColumn(cursor,"URL_XML","").replace("null",""));
                    factCobBE.setI_RESPUESTA(Funciones.isNullColumn(cursor,"I_RESPUESTA","").replace("null",""));
                    factCobBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG","").replace("null",""));
                    factCobBE.setCOD_VEND_ORIGEN(Funciones.isNullColumn(cursor,"COD_VENDE","").replace("null",""));
                    lst.add(factCobBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(FactCobBE factCobBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("COD_CLIENTE",factCobBE.getCOD_CLIENTE());
            cv.put("TIPDOC",factCobBE.getTIPDOC());
            cv.put("SERIE_NUM",factCobBE.getSERIE_NUM());
            cv.put("NUMERO",factCobBE.getNUMERO().toString());
            cv.put("FECHA",factCobBE.getFECHA());
            cv.put("F_VENCTO",factCobBE.getF_VENCTO());
            cv.put("F_ACEPTACION",factCobBE.getF_ACEPTACION());
            cv.put("F_TRANSFE",factCobBE.getF_TRANSFE());
            cv.put("ANO",factCobBE.getANO());
            cv.put("MES",factCobBE.getMES());
            cv.put("LIBRO",factCobBE.getLIBRO());
            cv.put("VOUCHER",factCobBE.getVOUCHER());
            cv.put("ITEM",factCobBE.getITEM());
            cv.put("TIPO_REFERENCIA",factCobBE.getTIPO_REFERENCIA());
            cv.put("SERIE_REF",factCobBE.getSERIE_REF());
            cv.put("NRO_REFERENCIA",factCobBE.getNRO_REFERENCIA());
            cv.put("CONCEPTO",factCobBE.getCONCEPTO());
            cv.put("SISTEMA_ORIGEN",factCobBE.getSISTEMA_ORIGEN());
            cv.put("VENDED",factCobBE.getVENDED());
            cv.put("BANCO",factCobBE.getBANCO());
            cv.put("L_AGENCIA",factCobBE.getL_AGENCIA());
            cv.put("L_REFBCO",factCobBE.getL_REFBCO());
            cv.put("L_CONDLE",factCobBE.getL_CONDLE());
            cv.put("MONEDA",factCobBE.getMONEDA().toString());
            cv.put("IMPORTE",factCobBE.getIMPORTE().toString());
            cv.put("TCAM_IMP",factCobBE.getTCAM_IMP().toString());
            cv.put("SALDO",factCobBE.getSALDO().toString());
            cv.put("TCAM_SAL",factCobBE.getTCAM_SAL().toString());
            cv.put("NUMERO_CANJE",factCobBE.getNUMERO_CANJE());
            cv.put("ESTADO",factCobBE.getESTADO());
            cv.put("CTACTBLE",factCobBE.getCTACTBLE());
            cv.put("F_RECEPCION",factCobBE.getF_RECEPCION());
            cv.put("C_USUARIO",factCobBE.getC_USUARIO());
            cv.put("C_PERFIL",factCobBE.getC_PERFIL());
            cv.put("C_CPU",factCobBE.getC_CPU());
            cv.put("FEC_REG",factCobBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",factCobBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",factCobBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",factCobBE.getFEC_MOD());
            cv.put("C_CPU_MOD",factCobBE.getC_CPU_MOD());
            cv.put("N_SERIE_RECIBO_COBRA",factCobBE.getN_SERIE_RECIBO_COBRA());
            cv.put("N_RECIBO_COBRA",factCobBE.getN_RECIBO_COBRA().toString());
            cv.put("ANO_PROVISION",factCobBE.getANO_PROVISION().toString());
            cv.put("MES_CSTGO",factCobBE.getMES_CSTGO().toString());
            cv.put("ANO_CSTGO",factCobBE.getANO_CSTGO().toString());
            cv.put("LIBRO_CSTGO",factCobBE.getLIBRO_CSTGO());
            cv.put("VOUCHER_CSTGO",factCobBE.getVOUCHER_CSTGO().toString());
            DataBaseHelper.myDataBase.insert("FACTCOB",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String insertTemp(FactCobBE factCobBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("COD_CLIENTE",factCobBE.getCOD_CLIENTE());
            cv.put("TIPDOC",factCobBE.getTIPDOC());
            cv.put("SERIE_NUM",factCobBE.getSERIE_NUM());
            cv.put("NUMERO",factCobBE.getNUMERO().toString());
            cv.put("FECHA",factCobBE.getFECHA());
            cv.put("F_VENCTO",factCobBE.getF_VENCTO());
            cv.put("F_ACEPTACION",factCobBE.getF_ACEPTACION());
            cv.put("F_TRANSFE",factCobBE.getF_TRANSFE());
            cv.put("ANO",factCobBE.getANO());
            cv.put("MES",factCobBE.getMES());
            cv.put("LIBRO",factCobBE.getLIBRO());
            cv.put("VOUCHER",factCobBE.getVOUCHER());
            cv.put("ITEM",factCobBE.getITEM());
            cv.put("TIPO_REFERENCIA",factCobBE.getTIPO_REFERENCIA());
            cv.put("SERIE_REF",factCobBE.getSERIE_REF());
            cv.put("NRO_REFERENCIA",factCobBE.getNRO_REFERENCIA());
            cv.put("CONCEPTO",factCobBE.getCONCEPTO());
            cv.put("SISTEMA_ORIGEN",factCobBE.getSISTEMA_ORIGEN());
            cv.put("VENDED",factCobBE.getVENDED());
            cv.put("BANCO",factCobBE.getBANCO());
            cv.put("L_AGENCIA",factCobBE.getL_AGENCIA());
            cv.put("L_REFBCO",factCobBE.getL_REFBCO());
            cv.put("L_CONDLE",factCobBE.getL_CONDLE());
            cv.put("MONEDA",factCobBE.getMONEDA().toString());
            cv.put("IMPORTE",factCobBE.getIMPORTE().toString());
            cv.put("TCAM_IMP",factCobBE.getTCAM_IMP().toString());
            cv.put("SALDO",factCobBE.getSALDO().toString());
            cv.put("TCAM_SAL",factCobBE.getTCAM_SAL().toString());
            cv.put("NUMERO_CANJE",factCobBE.getNUMERO_CANJE());
            cv.put("ESTADO",factCobBE.getESTADO());
            cv.put("CTACTBLE",factCobBE.getCTACTBLE());
            cv.put("F_RECEPCION",factCobBE.getF_RECEPCION());
            cv.put("C_USUARIO",factCobBE.getC_USUARIO());
            cv.put("C_PERFIL",factCobBE.getC_PERFIL());
            cv.put("C_CPU",factCobBE.getC_CPU());
            cv.put("FEC_REG",factCobBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",factCobBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",factCobBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",factCobBE.getFEC_MOD());
            cv.put("C_CPU_MOD",factCobBE.getC_CPU_MOD());
            cv.put("N_SERIE_RECIBO_COBRA",factCobBE.getN_SERIE_RECIBO_COBRA());
            cv.put("N_RECIBO_COBRA",factCobBE.getN_RECIBO_COBRA().toString());
            cv.put("ANO_PROVISION",factCobBE.getANO_PROVISION().toString());
            cv.put("MES_CSTGO",factCobBE.getMES_CSTGO().toString());
            cv.put("ANO_CSTGO",factCobBE.getANO_CSTGO().toString());
            cv.put("LIBRO_CSTGO",factCobBE.getLIBRO_CSTGO());
            cv.put("VOUCHER_CSTGO",factCobBE.getVOUCHER_CSTGO().toString());
            cv.put("ID_COBRANZA",factCobBE.getID_COBRANZA().toString());
            cv.put("CODUNC_LOCAL",factCobBE.getCODUNC_LOCAL().toString());
            cv.put("COBRANZA",factCobBE.getCOBRANZA().toString());

            DataBaseHelper.myDataBase.insert("FACTCOBTEMP",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String updateAgregado(FactCobBE factCobBE){
        String sMensaje="";
        Cursor cursor = null;
        try{
            String SQL="UPDATE FACTCOB SET AGREGADO=" + factCobBE.getAGREGADO() + " " +
                        " WHERE COD_CLIENTE='" + factCobBE.getCOD_CLIENTE() + "'"+
                           "AND SERIE_NUM='"+ factCobBE.getSERIE_NUM() + "'"+
                           "AND NUMERO='"+ factCobBE.getNUMERO() + "'";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(FactCobBE factCobBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("COD_CLIENTE",factCobBE.getCOD_CLIENTE());
            cv.put("TIPDOC",factCobBE.getTIPDOC());
            cv.put("SERIE_NUM",factCobBE.getSERIE_NUM());
            cv.put("NUMERO",factCobBE.getNUMERO().toString());
            cv.put("FECHA",factCobBE.getFECHA());
            cv.put("F_VENCTO",factCobBE.getF_VENCTO());
            cv.put("F_ACEPTACION",factCobBE.getF_ACEPTACION());
            cv.put("F_TRANSFE",factCobBE.getF_TRANSFE());
            cv.put("ANO",factCobBE.getANO());
            cv.put("MES",factCobBE.getMES());
            cv.put("LIBRO",factCobBE.getLIBRO());
            cv.put("VOUCHER",factCobBE.getVOUCHER());
            cv.put("ITEM",factCobBE.getITEM());
            cv.put("TIPO_REFERENCIA",factCobBE.getTIPO_REFERENCIA());
            cv.put("SERIE_REF",factCobBE.getSERIE_REF());
            cv.put("NRO_REFERENCIA",factCobBE.getNRO_REFERENCIA());
            cv.put("CONCEPTO",factCobBE.getCONCEPTO());
            cv.put("SISTEMA_ORIGEN",factCobBE.getSISTEMA_ORIGEN());
            cv.put("VENDED",factCobBE.getVENDED());
            cv.put("BANCO",factCobBE.getBANCO());
            cv.put("L_AGENCIA",factCobBE.getL_AGENCIA());
            cv.put("L_REFBCO",factCobBE.getL_REFBCO());
            cv.put("L_CONDLE",factCobBE.getL_CONDLE());
            cv.put("MONEDA",factCobBE.getMONEDA().toString());
            cv.put("IMPORTE",factCobBE.getIMPORTE().toString());
            cv.put("TCAM_IMP",factCobBE.getTCAM_IMP().toString());
            cv.put("SALDO",factCobBE.getSALDO().toString());
            cv.put("TCAM_SAL",factCobBE.getTCAM_SAL().toString());
            cv.put("NUMERO_CANJE",factCobBE.getNUMERO_CANJE());
            cv.put("ESTADO",factCobBE.getESTADO());
            cv.put("CTACTBLE",factCobBE.getCTACTBLE());
            cv.put("F_RECEPCION",factCobBE.getF_RECEPCION());
            cv.put("C_USUARIO",factCobBE.getC_USUARIO());
            cv.put("C_PERFIL",factCobBE.getC_PERFIL());
            cv.put("C_CPU",factCobBE.getC_CPU());
            cv.put("FEC_REG",factCobBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",factCobBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",factCobBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",factCobBE.getFEC_MOD());
            cv.put("C_CPU_MOD",factCobBE.getC_CPU_MOD());
            cv.put("N_SERIE_RECIBO_COBRA",factCobBE.getN_SERIE_RECIBO_COBRA());
            cv.put("N_RECIBO_COBRA",factCobBE.getN_RECIBO_COBRA().toString());
            cv.put("ANO_PROVISION",factCobBE.getANO_PROVISION().toString());
            cv.put("MES_CSTGO",factCobBE.getMES_CSTGO().toString());
            cv.put("ANO_CSTGO",factCobBE.getANO_CSTGO().toString());
            cv.put("LIBRO_CSTGO",factCobBE.getLIBRO_CSTGO());
            cv.put("VOUCHER_CSTGO",factCobBE.getVOUCHER_CSTGO().toString());

            DataBaseHelper.myDataBase.update("FactCob",cv,"COD_CLIENTE = ?",
                    new String[]{String.valueOf(factCobBE.getCOD_CLIENTE())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(FactCobBE factCobBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("FactCob","COD_CLIENTE = ?", new String[]{String.valueOf(factCobBE.getCOD_CLIENTE())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.ClientesBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class ClientesDAO {
    public ArrayList<ClientesBE> lst = null;

    public void getAll(String ptm1,String ptm2,String ptm3,String ptm4,String ptm5) {
        Cursor cursor = null;
        ClientesBE clientesBE = null;
        try {
            String SQL="SELECT COD_CLIENTE,NOMBRE,DIRECCION,CIUDAD,DISTRITO,PAIS," +
                    "ZONA,VENDEDOR,COBRADOR,GIRO,TELEFONO,FAX," +
                    "FECHA_ING,COND_PAG,CALIFICACION,LIMITE_CRED,ESTADO,RUC," +
                    "DSCTO_1,DSCTO_2,CTACONTS,CTACONTD,CLIENTE_AFECTO,DSCTO_MAX," +
                    "LISTA_PRECIO,NIVEL_CP,CRED_USADO,LETRA_PRT,CHEQ_DEV,GRUPO," +
                    "DIREC_FACT,INTERES,COMI_PREDET,ALM_CONS,COD_UBC,DOCIDEN," +
                    "RUC_NEW,UBIGEO,DIA_VISITA,ORD_VISITA,FRC_VISITA,ANIVERSARIO," +
                    "R_CUMPLEANOS,L_TAMANO,L_DECORACION,L_TECNOLOGIA,NRO_EMPLEADOS,NRO_EXIBIDOR," +
                    "NRO_VISITAS,CANEMP,CEOCLIE,PROFESO,CARNET,REFERENCIA," +
                    "COD_STROE,GIRO_CLI,R_SEXO,R_LUGNAC,R_ESTCIV,R_HOBBIE," +
                    "R_GENERO,R_REVIST,R_PERIOD,RETENCION,CALIF_VTA,CALIF_CRED," +
                    "PLAZO_PAGO,I_SITUACION,C_CANAL,C_CLIENTE,C_ETAPA,FLG_CONSIGNA_VTA," +
                    "FLG_CONSIGNA_PROM,C_COBRADOR_EXT,C_DIA_VISITA_EXT,N_ORD_VISITA_EXT,C_FRC_VISITA_EXT,C_RUTA_DISTRIBUCION," +
                    "E_MAIL,N_ARCHIVO,C_CLIENTE_PAGO,C_NIVEL,I_CLIENTE_INT_EXT,M_CUOTA_SEMANAL," +
                    "C_FRC_COB,C_SUC_EMP,C_GRUPO_CANAL_VTA,C_FUERZA_VTA,I_DIR_FISCAL,C_REGION_VTA," +
                    "C_COBRADOR,I_ORIGEN,I_DI,I_AGENTE_RET,I_AGENTE_PER,I_PERCEPCION," +
                    "COD_DPT,COD_PVC,DIRECCION_VSTA,ID_NIVEL,I_RELACION,F_FACT_AUTOM," +
                    "C_USUARIO,C_PERFIL,C_CPU,FEC_REG,I_REGISTRO_CASTIGO,OBSERVACION,ID_LOCAL " +
                    "FROM CLIENTES  " +
                    "WHERE COD_CLIENTE LIKE '%" + ptm1 + "%' "+
                     " AND NOMBRE LIKE '%"+ ptm2 + "%'"+
                     " AND RUC LIKE '%"+ ptm3 + "%'"+
                     " AND DOCIDEN LIKE '%"+ ptm4 + "%'"+
                     " AND GRUPO LIKE '%"+ ptm5 + "%'";
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<ClientesBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    clientesBE = new ClientesBE();
                    clientesBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    clientesBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));
                    clientesBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    clientesBE.setCIUDAD(Funciones.isNullColumn(cursor,"CIUDAD",""));
                    clientesBE.setDISTRITO(Funciones.isNullColumn(cursor,"DISTRITO",""));
                    clientesBE.setPAIS(Funciones.isNullColumn(cursor,"PAIS",""));
                    clientesBE.setZONA(Funciones.isNullColumn(cursor,"ZONA",""));
                    clientesBE.setVENDEDOR(Funciones.isNullColumn(cursor,"VENDEDOR",""));
                    clientesBE.setCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
                    clientesBE.setGIRO(Funciones.isNullColumn(cursor,"GIRO",""));
                    clientesBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    clientesBE.setFAX(Funciones.isNullColumn(cursor,"FAX",""));
                    clientesBE.setFECHA_ING(Funciones.isNullColumn(cursor,"FECHA_ING",""));
                    clientesBE.setCOND_PAG(Funciones.isNullColumn(cursor,"COND_PAG",""));
                    clientesBE.setCALIFICACION(Funciones.isNullColumn(cursor,"CALIFICACION",""));
                    clientesBE.setLIMITE_CRED(Funciones.isNullColumn(cursor,"LIMITE_CRED",""));
                    clientesBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    clientesBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                   /* clientesBE.setDSCTO_1(Funciones.isNullColumn(cursor,"DSCTO_1",0));
                    clientesBE.setDSCTO_2(Funciones.isNullColumn(cursor,"DSCTO_2",0));
                    clientesBE.setCTACONTS(Funciones.isNullColumn(cursor,"CTACONTS",""));
                    clientesBE.setCTACONTD(Funciones.isNullColumn(cursor,"CTACONTD",""));
                    clientesBE.setCLIENTE_AFECTO(Funciones.isNullColumn(cursor,"CLIENTE_AFECTO",""));
                    clientesBE.setDSCTO_MAX(Funciones.isNullColumn(cursor,"DSCTO_MAX",0));
                    clientesBE.setLISTA_PRECIO(Funciones.isNullColumn(cursor,"LISTA_PRECIO",""));
                    clientesBE.setNIVEL_CP(Funciones.isNullColumn(cursor,"NIVEL_CP",0));
                    clientesBE.setCRED_USADO(Funciones.isNullColumn(cursor,"CRED_USADO",0));
                    clientesBE.setLETRA_PRT(Funciones.isNullColumn(cursor,"LETRA_PRT",""));
                    clientesBE.setCHEQ_DEV(Funciones.isNullColumn(cursor,"CHEQ_DEV",""));
                    clientesBE.setGRUPO(Funciones.isNullColumn(cursor,"GRUPO",""));
                    clientesBE.setDIREC_FACT(Funciones.isNullColumn(cursor,"DIREC_FACT",""));
                    clientesBE.setINTERES(Funciones.isNullColumn(cursor,"INTERES",""));
                    clientesBE.setCOMI_PREDET(Funciones.isNullColumn(cursor,"COMI_PREDET",""));
                    clientesBE.setALM_CONS(Funciones.isNullColumn(cursor,"ALM_CONS",""));
                    clientesBE.setCOD_UBC(Funciones.isNullColumn(cursor,"COD_UBC",""));
                    clientesBE.setDOCIDEN(Funciones.isNullColumn(cursor,"DOCIDEN",""));
                    clientesBE.setRUC_NEW(Funciones.isNullColumn(cursor,"RUC_NEW",""));
                    clientesBE.setUBIGEO(Funciones.isNullColumn(cursor,"UBIGEO",""));
                    clientesBE.setDIA_VISITA(Funciones.isNullColumn(cursor,"DIA_VISITA",0));
                    clientesBE.setORD_VISITA(Funciones.isNullColumn(cursor,"ORD_VISITA",0));
                    clientesBE.setFRC_VISITA(Funciones.isNullColumn(cursor,"FRC_VISITA",""));
                    clientesBE.setANIVERSARIO(Funciones.isNullColumn(cursor,"ANIVERSARIO",""));
                    clientesBE.setR_CUMPLEANOS(Funciones.isNullColumn(cursor,"R_CUMPLEANOS",""));
                    clientesBE.setL_TAMANO(Funciones.isNullColumn(cursor,"L_TAMANO",""));
                    clientesBE.setL_DECORACION(Funciones.isNullColumn(cursor,"L_DECORACION",""));
                    clientesBE.setL_TECNOLOGIA(Funciones.isNullColumn(cursor,"L_TECNOLOGIA",""));
                    clientesBE.setNRO_EMPLEADOS(Funciones.isNullColumn(cursor,"NRO_EMPLEADOS",""));
                    clientesBE.setNRO_EXIBIDOR(Funciones.isNullColumn(cursor,"NRO_EXIBIDOR",""));
                    clientesBE.setNRO_VISITAS(Funciones.isNullColumn(cursor,"NRO_VISITAS",""));
                    clientesBE.setCANEMP(Funciones.isNullColumn(cursor,"CANEMP",0));
                    clientesBE.setCEOCLIE(Funciones.isNullColumn(cursor,"CEOCLIE",""));
                    clientesBE.setPROFESO(Funciones.isNullColumn(cursor,"PROFESO",""));
                    clientesBE.setCARNET(Funciones.isNullColumn(cursor,"CARNET",""));
                    clientesBE.setREFERENCIA(Funciones.isNullColumn(cursor,"REFERENCIA",""));
                    clientesBE.setCOD_STROE(Funciones.isNullColumn(cursor,"COD_STROE",""));
                    clientesBE.setGIRO_CLI(Funciones.isNullColumn(cursor,"GIRO_CLI",""));
                    clientesBE.setR_SEXO(Funciones.isNullColumn(cursor,"R_SEXO",""));
                    clientesBE.setR_LUGNAC(Funciones.isNullColumn(cursor,"R_LUGNAC",""));
                    clientesBE.setR_ESTCIV(Funciones.isNullColumn(cursor,"R_ESTCIV",""));
                    clientesBE.setR_HOBBIE(Funciones.isNullColumn(cursor,"R_HOBBIE",""));
                    clientesBE.setR_GENERO(Funciones.isNullColumn(cursor,"R_GENERO",""));
                    clientesBE.setR_REVIST(Funciones.isNullColumn(cursor,"R_REVIST",""));
                    clientesBE.setR_PERIOD(Funciones.isNullColumn(cursor,"R_PERIOD",""));
                    clientesBE.setRETENCION(Funciones.isNullColumn(cursor,"RETENCION",""));
                    clientesBE.setCALIF_VTA(Funciones.isNullColumn(cursor,"CALIF_VTA",""));
                    clientesBE.setCALIF_CRED(Funciones.isNullColumn(cursor,"CALIF_CRED",""));
                    clientesBE.setPLAZO_PAGO(Funciones.isNullColumn(cursor,"PLAZO_PAGO",""));
                    clientesBE.setI_SITUACION(Funciones.isNullColumn(cursor,"I_SITUACION",""));
                    clientesBE.setC_CANAL(Funciones.isNullColumn(cursor,"C_CANAL",""));
                    clientesBE.setC_CLIENTE(Funciones.isNullColumn(cursor,"C_CLIENTE",""));
                    clientesBE.setC_ETAPA(Funciones.isNullColumn(cursor,"C_ETAPA",""));
                    clientesBE.setFLG_CONSIGNA_VTA(Funciones.isNullColumn(cursor,"FLG_CONSIGNA_VTA",""));
                    clientesBE.setFLG_CONSIGNA_PROM(Funciones.isNullColumn(cursor,"FLG_CONSIGNA_PROM",""));
                    clientesBE.setC_COBRADOR_EXT(Funciones.isNullColumn(cursor,"C_COBRADOR_EXT",""));
                    clientesBE.setC_DIA_VISITA_EXT(Funciones.isNullColumn(cursor,"C_DIA_VISITA_EXT",""));
                    clientesBE.setN_ORD_VISITA_EXT(Funciones.isNullColumn(cursor,"N_ORD_VISITA_EXT",""));
                    clientesBE.setC_FRC_VISITA_EXT(Funciones.isNullColumn(cursor,"C_FRC_VISITA_EXT",""));
                    clientesBE.setC_RUTA_DISTRIBUCION(Funciones.isNullColumn(cursor,"C_RUTA_DISTRIBUCION",""));
                    clientesBE.setE_MAIL(Funciones.isNullColumn(cursor,"E_MAIL",""));
                    clientesBE.setN_ARCHIVO(Funciones.isNullColumn(cursor,"N_ARCHIVO",0));
                    clientesBE.setC_CLIENTE_PAGO(Funciones.isNullColumn(cursor,"C_CLIENTE_PAGO",""));
                    clientesBE.setC_NIVEL(Funciones.isNullColumn(cursor,"C_NIVEL",""));
                    clientesBE.setI_CLIENTE_INT_EXT(Funciones.isNullColumn(cursor,"I_CLIENTE_INT_EXT",""));
                    clientesBE.setM_CUOTA_SEMANAL(Funciones.isNullColumn(cursor,"M_CUOTA_SEMANAL",0));
                    clientesBE.setC_FRC_COB(Funciones.isNullColumn(cursor,"C_FRC_COB",""));
                    clientesBE.setC_SUC_EMP(Funciones.isNullColumn(cursor,"C_SUC_EMP",""));
                    clientesBE.setC_GRUPO_CANAL_VTA(Funciones.isNullColumn(cursor,"C_GRUPO_CANAL_VTA",""));
                    clientesBE.setC_FUERZA_VTA(Funciones.isNullColumn(cursor,"C_FUERZA_VTA",""));
                    clientesBE.setI_DIR_FISCAL(Funciones.isNullColumn(cursor,"I_DIR_FISCAL",""));
                    clientesBE.setC_REGION_VTA(Funciones.isNullColumn(cursor,"C_REGION_VTA",""));
                    clientesBE.setC_COBRADOR(Funciones.isNullColumn(cursor,"C_COBRADOR",""));
                    clientesBE.setI_ORIGEN(Funciones.isNullColumn(cursor,"I_ORIGEN",""));
                    clientesBE.setI_DI(Funciones.isNullColumn(cursor,"I_DI",""));
                    clientesBE.setI_AGENTE_RET(Funciones.isNullColumn(cursor,"I_AGENTE_RET",""));
                    clientesBE.setI_AGENTE_PER(Funciones.isNullColumn(cursor,"I_AGENTE_PER",""));
                    clientesBE.setI_PERCEPCION(Funciones.isNullColumn(cursor,"I_PERCEPCION",""));
                    clientesBE.setCOD_DPT(Funciones.isNullColumn(cursor,"COD_DPT",""));
                    clientesBE.setCOD_PVC(Funciones.isNullColumn(cursor,"COD_PVC",""));
                    clientesBE.setDIRECCION_VSTA(Funciones.isNullColumn(cursor,"DIRECCION_VSTA",""));
                    clientesBE.setID_NIVEL(Funciones.isNullColumn(cursor,"ID_NIVEL",""));
                    clientesBE.setI_RELACION(Funciones.isNullColumn(cursor,"I_RELACION",""));
                    clientesBE.setF_FACT_AUTOM(Funciones.isNullColumn(cursor,"F_FACT_AUTOM",""));
                    clientesBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    clientesBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    clientesBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    clientesBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    clientesBE.setI_REGISTRO_CASTIGO(Funciones.isNullColumn(cursor,"I_REGISTRO_CASTIGO",""));
                    clientesBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    clientesBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",""));*/
                    lst.add(clientesBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getAllBy(String iID_VENDEDOR,
                         String iRAZON_SOCIAL,
                         String iRUC,
                         String iDNI,
                         String iCODIGO_ANTIGUO,
                         String iID_GRUPO,
                         String iROL,
                         String iC_PACKING,
                         String iID_EMPRESA,
                         String iID_LOCAL) {

        Cursor cursor = null;
        if (iID_GRUPO.equals("")){
            iID_GRUPO="0";
        }
        if (iC_PACKING.equals("")){
            iC_PACKING="0";
        }
        Integer iIngreso=0;
        ClientesBE clientesBE = null;
        String SQL="",SQL_TIPO_VENDEDOR="",TIPO_VENDEDOR="80001";
        try {
            SQL_TIPO_VENDEDOR = "SELECT TIPO_VENDEDOR  FROM S_GEM_VENDEDOR WHERE ID_PERSONA=" + iID_VENDEDOR;
            cursor = DataBaseHelper.myDataBase.rawQuery(SQL_TIPO_VENDEDOR, null);
            if (cursor.moveToFirst()) {
             do {
                    TIPO_VENDEDOR = Funciones.isNullColumn(cursor, "TIPO_VENDEDOR", "80001");
                }
              while (cursor.moveToNext()) ;
             }
            if (cursor != null) {
                cursor.close();
            }
            //SI ROL=SUPERVISOR
            if (iROL.equals("130014")) {
                SQL="SELECT DISTINCT A.ID_CLIENTE, A.RAZON_SOCIAL,A.ID_CANAL,IFNULL(CA.CODIGO_ANTIGUO,'') AS CODIGO_ANTIGUO,\n"+
                "0 AS M_PAE,A.I_CANC_ANTIGUO \n"+
                " FROM S_GEM_CLIENTE A  INNER JOIN S_GEA_VENDEDOR_CLIENTE J \n"+
                " ON(A.ID_CLIENTE = J.ID_CLIENTE) INNER JOIN S_GEA_VENDEDOR_SUPERVISOR V \n"+
                " ON(J.ID_VENDEDOR = V.ID_VENDEDOR) INNER JOIN S_GEM_CLIENTE_CODIGO_ANT CA \n"+
                " ON(A.ID_CLIENTE=CA.ID_CLIENTE AND CA.FLAG_VIGENCIA=1)"+
                "  WHERE (V.ID_SUPERVISOR =" + iID_VENDEDOR + " OR " +  iID_VENDEDOR + "=0) \n"+
                " AND IFNULL(A.RUC,'') LIKE '%" + iRUC + "%' \n"+
                " AND IFNULL(A.DNI,'') LIKE '%" + iDNI + "%' \n"+
                " AND UPPER(ifnull(A.RAZON_SOCIAL,'')) LIKE UPPER('% " + iRAZON_SOCIAL + "%') \n"+
                " AND UPPER(ifnull(CA.CODIGO_ANTIGUO,'')) LIKE UPPER('%" + iCODIGO_ANTIGUO + "%') \n"+
                " AND (A.ID_GRUPO ="+  iID_GRUPO + " OR " + iID_GRUPO + "= 0) \n" +
                " AND A.ESTADO <> 40002  LIMIT 50";
                iIngreso=1;
            }
            if (iROL.equals("130019") || iROL.equals("130008")  &&  iIngreso==0) {
                    SQL="SELECT  " +
                            "A.ID_CLIENTE,CLI.RAZON_SOCIAL,CLI.ID_CANAL,D.COD_CLIENTE AS CODIGO_ANTIGUO,\n" +
                            "IFNULL(M_PAE,0)AS M_PAE,'N' AS I_CANC_ANTIGUO\n" +
                            " FROM DPM_PACKING_CAB C \n" +
                            " INNER JOIN DPM_PACKING_DET D ON (C.C_PACKING = D.C_PACKING) \n" +
                            " INNER JOIN DOCUVENT DO ON (D.TIPODOC = DO.TIPODOC AND D.SERIE = DO.SERIE AND D.NUMERO = DO.NUMERO) \n" +
                            " INNER JOIN S_GEM_CLIENTE_CODIGO_ANT A ON (D.COD_CLIENTE = A.CODIGO_ANTIGUO AND A.FLAG_VIGENCIA > 0) \n" +
                            " INNER JOIN S_GEM_CLIENTE CLI ON (A.ID_CLIENTE = CLI.ID_CLIENTE) \n" +
                            " WHERE (C.C_PACKING =" + iC_PACKING +" OR " + iC_PACKING + "=0) \n" +
                            " AND IFNULL(CLI.RUC, '') LIKE '%" + iRUC + "%' \n" +
                            " AND IFNULL(CLI.DNI, '') LIKE '%" + iDNI + "%' \n" +
                            " AND IFNULL(CLI.RAZON_SOCIAL, '') LIKE '%" +  iRAZON_SOCIAL + "%' \n" +
                            " AND UPPER(D.COD_CLIENTE) LIKE '%" + iCODIGO_ANTIGUO + "%'\n" +
                            " AND (CLI.ID_GRUPO =" +  iID_GRUPO + " OR "  + iID_GRUPO + "= 0)\n" +
                            " AND (CASE " +
                            "  WHEN  D.C_EMPRESA='U' THEN 1 \n" +
                            "  WHEN  D.C_EMPRESA='T' THEN 2 \n" +
                            "  WHEN  D.C_EMPRESA='R' THEN 9 \n" +
                            "    ELSE 0 END)  = " + iID_EMPRESA +
                            " AND D.TIPODOC IN ('01','03') \n" +
                            " AND DO.ESTADO<>'9' \n" +
                            " AND (CASE WHEN " + iID_EMPRESA + "=2 THEN 3 WHEN  D.SERIE IN('F030','B030') THEN 2 \n" +
                            "   ELSE 1 END)=" +  iID_LOCAL +
                            " AND DO.ID_EMPRESA="+ iID_EMPRESA +
                            " GROUP BY A.ID_CLIENTE," +
                            " CLI.RAZON_SOCIAL," +
                            " CLI.ID_CANAL," +
                            " D.COD_CLIENTE" +
                            " ORDER BY CLI.RAZON_SOCIAL ASC LIMIT 50";
                iIngreso=1;
            }
            if (TIPO_VENDEDOR.equals("80001") &&  iIngreso==0){
               SQL="SELECT DISTINCT " +
                   " A.ID_CLIENTE, A.RAZON_SOCIAL,A.ID_CANAL," +
                   "  IFNULL(CA.CODIGO_ANTIGUO,'') AS CODIGO_ANTIGUO,0 AS M_PAE,A.I_CANC_ANTIGUO" +
                   " FROM S_GEM_CLIENTE A " +
                   " INNER JOIN S_GEA_VENDEDOR_CLIENTE J ON(A.ID_CLIENTE = J.ID_CLIENTE)" +
                   " INNER JOIN S_GEM_CLIENTE_CODIGO_ANT CA" +
                   " ON(A.ID_CLIENTE=CA.ID_CLIENTE AND CA.FLAG_VIGENCIA=1)" +
                   " WHERE (J.ID_VENDEDOR =" + iID_VENDEDOR + " OR " + iID_VENDEDOR + "=0"  +  " OR " +  iID_VENDEDOR + "= 306)" +
                   " AND IFNULL(A.RUC,'') LIKE '%" + iRUC + "%'" +
                   " AND IFNULL(A.DNI,'') LIKE '%" + iDNI + "%'" +
                   " AND IFNULL(A.RAZON_SOCIAL,' ') LIKE '%" + iRAZON_SOCIAL + "%' " +
                   " AND UPPER(IFNULL(CA.CODIGO_ANTIGUO,' ')) LIKE '%'||UPPER('" + iCODIGO_ANTIGUO +  "') ||'%'" +
                   " AND (A.ID_GRUPO = 0 OR 0 = 0)";
                iIngreso=1;
            }
            if (TIPO_VENDEDOR.equals("80002") &&  iIngreso==0){
                SQL="SELECT DISTINCT " +
                    " A.ID_CLIENTE, A.RAZON_SOCIAL,A.ID_CANAL,IFNULL(CA.CODIGO_ANTIGUO,'') AS CODIGO_ANTIGUO,\n" +
                    " 0 AS M_PAE,A.I_CANC_ANTIGUO \n" +
                    " FROM S_GEM_CLIENTE A \n" +
                    " INNER JOIN S_GEM_CLIENTE_CODIGO_ANT CA ON(A.ID_CLIENTE=CA.ID_CLIENTE AND CA.FLAG_VIGENCIA=1)\n" +
                    " INNER JOIN CLIENTES LS ON(CA.CODIGO_ANTIGUO = LS.COD_CLIENTE) \n" +
                    " INNER JOIN VEM_COBRADOR_ZONA CZ ON(CZ.UBIGEO = CASE WHEN (LS.COD_UBC)='070100' THEN '070101' ELSE LS.COD_UBC END )\n" +
                    " WHERE (CZ.COBRADOR ='" +  iID_VENDEDOR + "' OR '" +  iID_VENDEDOR + "'='0' OR CA.CODIGO_ANTIGUO IN( '018726I','10030133','45945156') OR '" + iID_VENDEDOR + "' IN('347','306','332'))\n"+
                    " AND IFNULL(A.RUC,'') LIKE '%" + iRUC +  "%'\n" +
                    " AND IFNULL(A.DNI,'') LIKE '%" + iDNI +  "%'\n" +
                    " AND IFNULL(A.RAZON_SOCIAL,'') LIKE '%"+ iRAZON_SOCIAL + "%'\n" +
                    " AND UPPER(IFNULL(CA.CODIGO_ANTIGUO,'')) LIKE '%" + iCODIGO_ANTIGUO+ "%'\n" +
                    " AND (A.ID_GRUPO =" + iID_GRUPO + " OR " + iID_GRUPO + "= 0)";

                iIngreso=1;
            }
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<ClientesBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    clientesBE = new ClientesBE();
                     clientesBE.setID_CLIENTE(Funciones.isNullColumn(cursor,"ID_CLIENTE","").toString());
                     clientesBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL","").toString());
                     clientesBE.setID_CANAL(Funciones.isNullColumn(cursor,"ID_CANAL","").toString());
                     clientesBE.setCODIGO_ANTIGUO(Funciones.isNullColumn(cursor,"CODIGO_ANTIGUO","").toString());
                     clientesBE.setM_PAE(Funciones.isNullColumn(cursor,"M_PAE","").toString());
                     clientesBE.setI_CANC_ANTIGUO(Funciones.isNullColumn(cursor,"I_CANC_ANTIGUO","").toString());
                    lst.add(clientesBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }



    public String insert(ClientesBE clientesBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("COD_CLIENTE",clientesBE.getCOD_CLIENTE());
            cv.put("NOMBRE",clientesBE.getNOMBRE());
            cv.put("DIRECCION",clientesBE.getDIRECCION());
            cv.put("CIUDAD",clientesBE.getCIUDAD());
            cv.put("DISTRITO",clientesBE.getDISTRITO());
            cv.put("PAIS",clientesBE.getPAIS());
            cv.put("ZONA",clientesBE.getZONA());
            cv.put("VENDEDOR",clientesBE.getVENDEDOR());
            cv.put("COBRADOR",clientesBE.getCOBRADOR());
            cv.put("GIRO",clientesBE.getGIRO());
            cv.put("TELEFONO",clientesBE.getTELEFONO());
            cv.put("FAX",clientesBE.getFAX());
            cv.put("FECHA_ING",clientesBE.getFECHA_ING());
            cv.put("COND_PAG",clientesBE.getCOND_PAG());
            cv.put("CALIFICACION",clientesBE.getCALIFICACION());
            cv.put("LIMITE_CRED",clientesBE.getLIMITE_CRED());
            cv.put("ESTADO",clientesBE.getESTADO());
            cv.put("RUC",clientesBE.getRUC());
            cv.put("DSCTO_1",clientesBE.getDSCTO_1().toString());
            cv.put("DSCTO_2",clientesBE.getDSCTO_2().toString());
            cv.put("CTACONTS",clientesBE.getCTACONTS());
            cv.put("CTACONTD",clientesBE.getCTACONTD());
            cv.put("CLIENTE_AFECTO",clientesBE.getCLIENTE_AFECTO());
            cv.put("DSCTO_MAX",clientesBE.getDSCTO_MAX().toString());
            cv.put("LISTA_PRECIO",clientesBE.getLISTA_PRECIO());
            cv.put("NIVEL_CP",clientesBE.getNIVEL_CP().toString());
            cv.put("CRED_USADO",clientesBE.getCRED_USADO().toString());
            cv.put("LETRA_PRT",clientesBE.getLETRA_PRT());
            cv.put("CHEQ_DEV",clientesBE.getCHEQ_DEV());
            cv.put("GRUPO",clientesBE.getGRUPO());
            cv.put("DIREC_FACT",clientesBE.getDIREC_FACT());
            cv.put("INTERES",clientesBE.getINTERES());
            cv.put("COMI_PREDET",clientesBE.getCOMI_PREDET());
            cv.put("ALM_CONS",clientesBE.getALM_CONS());
            cv.put("COD_UBC",clientesBE.getCOD_UBC());
            cv.put("DOCIDEN",clientesBE.getDOCIDEN());
            cv.put("RUC_NEW",clientesBE.getRUC_NEW());
            cv.put("UBIGEO",clientesBE.getUBIGEO());
            cv.put("DIA_VISITA",clientesBE.getDIA_VISITA().toString());
            cv.put("ORD_VISITA",clientesBE.getORD_VISITA().toString());
            cv.put("FRC_VISITA",clientesBE.getFRC_VISITA());
            cv.put("ANIVERSARIO",clientesBE.getANIVERSARIO());
            cv.put("R_CUMPLEANOS",clientesBE.getR_CUMPLEANOS());
            cv.put("L_TAMANO",clientesBE.getL_TAMANO());
            cv.put("L_DECORACION",clientesBE.getL_DECORACION());
            cv.put("L_TECNOLOGIA",clientesBE.getL_TECNOLOGIA());
            cv.put("NRO_EMPLEADOS",clientesBE.getNRO_EMPLEADOS());
            cv.put("NRO_EXIBIDOR",clientesBE.getNRO_EXIBIDOR());
            cv.put("NRO_VISITAS",clientesBE.getNRO_VISITAS());
            cv.put("CANEMP",clientesBE.getCANEMP().toString());
            cv.put("CEOCLIE",clientesBE.getCEOCLIE());
            cv.put("PROFESO",clientesBE.getPROFESO());
            cv.put("CARNET",clientesBE.getCARNET());
            cv.put("REFERENCIA",clientesBE.getREFERENCIA());
            cv.put("COD_STROE",clientesBE.getCOD_STROE());
            cv.put("GIRO_CLI",clientesBE.getGIRO_CLI());
            cv.put("R_SEXO",clientesBE.getR_SEXO());
            cv.put("R_LUGNAC",clientesBE.getR_LUGNAC());
            cv.put("R_ESTCIV",clientesBE.getR_ESTCIV());
            cv.put("R_HOBBIE",clientesBE.getR_HOBBIE());
            cv.put("R_GENERO",clientesBE.getR_GENERO());
            cv.put("R_REVIST",clientesBE.getR_REVIST());
            cv.put("R_PERIOD",clientesBE.getR_PERIOD());
            cv.put("RETENCION",clientesBE.getRETENCION());
            cv.put("CALIF_VTA",clientesBE.getCALIF_VTA());
            cv.put("CALIF_CRED",clientesBE.getCALIF_CRED());
            cv.put("PLAZO_PAGO",clientesBE.getPLAZO_PAGO());
            cv.put("I_SITUACION",clientesBE.getI_SITUACION());
            cv.put("C_CANAL",clientesBE.getC_CANAL());
            cv.put("C_CLIENTE",clientesBE.getC_CLIENTE());
            cv.put("C_ETAPA",clientesBE.getC_ETAPA());
            cv.put("FLG_CONSIGNA_VTA",clientesBE.getFLG_CONSIGNA_VTA());
            cv.put("FLG_CONSIGNA_PROM",clientesBE.getFLG_CONSIGNA_PROM());
            cv.put("C_COBRADOR_EXT",clientesBE.getC_COBRADOR_EXT());
            cv.put("C_DIA_VISITA_EXT",clientesBE.getC_DIA_VISITA_EXT());
            cv.put("N_ORD_VISITA_EXT",clientesBE.getN_ORD_VISITA_EXT());
            cv.put("C_FRC_VISITA_EXT",clientesBE.getC_FRC_VISITA_EXT());
            cv.put("C_RUTA_DISTRIBUCION",clientesBE.getC_RUTA_DISTRIBUCION());
            cv.put("E_MAIL",clientesBE.getE_MAIL());
            cv.put("N_ARCHIVO",clientesBE.getN_ARCHIVO().toString());
            cv.put("C_CLIENTE_PAGO",clientesBE.getC_CLIENTE_PAGO());
            cv.put("C_NIVEL",clientesBE.getC_NIVEL());
            cv.put("I_CLIENTE_INT_EXT",clientesBE.getI_CLIENTE_INT_EXT());
            cv.put("M_CUOTA_SEMANAL",clientesBE.getM_CUOTA_SEMANAL().toString());
            cv.put("C_FRC_COB",clientesBE.getC_FRC_COB());
            cv.put("C_SUC_EMP",clientesBE.getC_SUC_EMP());
            cv.put("C_GRUPO_CANAL_VTA",clientesBE.getC_GRUPO_CANAL_VTA());
            cv.put("C_FUERZA_VTA",clientesBE.getC_FUERZA_VTA());
            cv.put("I_DIR_FISCAL",clientesBE.getI_DIR_FISCAL());
            cv.put("C_REGION_VTA",clientesBE.getC_REGION_VTA());
            cv.put("C_COBRADOR",clientesBE.getC_COBRADOR());
            cv.put("I_ORIGEN",clientesBE.getI_ORIGEN());
            cv.put("I_DI",clientesBE.getI_DI());
            cv.put("I_AGENTE_RET",clientesBE.getI_AGENTE_RET());
            cv.put("I_AGENTE_PER",clientesBE.getI_AGENTE_PER());
            cv.put("I_PERCEPCION",clientesBE.getI_PERCEPCION());
            cv.put("COD_DPT",clientesBE.getCOD_DPT());
            cv.put("COD_PVC",clientesBE.getCOD_PVC());
            cv.put("DIRECCION_VSTA",clientesBE.getDIRECCION_VSTA());
            cv.put("ID_NIVEL",clientesBE.getID_NIVEL());
            cv.put("I_RELACION",clientesBE.getI_RELACION());
            cv.put("F_FACT_AUTOM",clientesBE.getF_FACT_AUTOM());
            cv.put("C_USUARIO",clientesBE.getC_USUARIO());
            cv.put("C_PERFIL",clientesBE.getC_PERFIL());
            cv.put("C_CPU",clientesBE.getC_CPU());
            cv.put("FEC_REG",clientesBE.getFEC_REG());
            cv.put("I_REGISTRO_CASTIGO",clientesBE.getI_REGISTRO_CASTIGO());
            cv.put("OBSERVACION",clientesBE.getOBSERVACION());
            cv.put("ID_LOCAL",clientesBE.getID_LOCAL());
            DataBaseHelper.myDataBase.insert("CLIENTES",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }





}

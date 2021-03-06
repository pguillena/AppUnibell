package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.VisitaDetBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class VisitaDetDAO {
    public ArrayList<VisitaDetBE> lst = null;

    public void getRecorrido( String iID_EMPRESA,
                              String iID_VENDEDOR,
                              String iFECHA,
                              String iCOD_CLIENTE,
                              String iRAZON_SOCIAL,
                              String iDISTRITO) {

        Cursor cursor = null;
        if (iCOD_CLIENTE.equals("")){
            iCOD_CLIENTE="XXX";
        }

        Integer iIngreso=0;
        VisitaDetBE visitaDetBE = null;
        String SQL = "",SQL_TIPO_VENDEDOR="", TIPO_VENDEDOR="", CODIGO_VENDEDOR="";
        String OrderBY = "";

        try {
            SQL_TIPO_VENDEDOR = "SELECT B.I_TIPO_VENDEDOR, A.CODIGO_ANTIGUO  FROM S_GEM_VENDEDOR_CODIGO_ANT A INNER JOIN MVENDEDOR B ON (A.CODIGO_ANTIGUO = B.C_VENDEDOR) WHERE A.FLAG_VIGENCIA = 1 AND A.ID_VENDEDOR = "+iID_VENDEDOR;

            cursor = DataBaseHelper.myDataBase.rawQuery(SQL_TIPO_VENDEDOR, null);
            if (cursor.moveToFirst()) {
                do {
                    TIPO_VENDEDOR = Funciones.isNullColumn(cursor, "I_TIPO_VENDEDOR", "");
                    CODIGO_VENDEDOR = Funciones.isNullColumn(cursor, "CODIGO_ANTIGUO", "");
                }
                while (cursor.moveToNext()) ;
            }
            if (cursor != null) {
                cursor.close();
            }

                SQL="SELECT     A.F_VISITA,  \n" +
                        "       CLI.COD_UBC, \n" +
                        "            CASE \n" +
                        "              WHEN CLI.I_SITUACION = 'A' THEN 1\n" +
                        "              WHEN CLI.I_SITUACION = 'I' THEN 2 \n" +
                        "              WHEN CLI.I_SITUACION = 'F' THEN 3\n" +
                        "              ELSE  999\n" +
                        "              END I_SITUACION,       \n" +
                        "       IFNULL(AUX2.DESCRIPCION,'SIN SITUACION') AS SITUACION,\n" +
                        "       D.DIAS,\n" +
                        "       D.TOTAL_DEUDA,\n" +
                        "       D.C_CLIENTE,\n" +
                        "       CLI.NOMBRE AS RAZON_SOCIAL,\n" +
                        "       CLI.DIRECCION,\n" +
                        "       AUX.DESCRIPCION AS FRECUENCIA_VISITA,\n" +
                        "       VC.N_ORD_VISITA AS ORDEN_VISITA,\n" +
                        "       A.N_INFORME,\n" +
                        "       D.N_SEQUENCIA,\n" +
                        "       D.HORA_I, \n" +
                        "       (SELECT COUNT(*)\n" +
                        "          FROM VEM_VISITA_DET\n" +
                        "         WHERE N_INFORME = D.N_INFORME\n" +
                        "           AND N_SEQUENCIA = D.N_SEQUENCIA\n" +
                        "           AND C_CLIENTE = D.C_CLIENTE\n" +
                        "           AND HORA_S <> '' ) VISITADO\n"+
                        "  FROM VEM_VISITA_CAB A\n" +
                        " INNER JOIN VEM_VISITA_DET D\n" +
                        "    ON (A.N_INFORME = D.N_INFORME)\n" +
                        " INNER JOIN CLIENTES CLI\n" +
                        "    ON (D.C_CLIENTE = CLI.COD_CLIENTE)\n" +
                        "  LEFT JOIN VEM_CLIENTE_VENDEDOR VC\n" +
                        "    ON (CLI.COD_CLIENTE = VC.C_CLIENTE AND A.C_VENDEDOR = VC.C_VENDEDOR)\n" +
                        "  LEFT JOIN TABLAS_AUXILIARES AUX\n" +
                        "    ON (VC.C_FRC_VISITA = AUX.CODIGO AND AUX.TIPO = 22)\n" +
                        "  LEFT JOIN TABLAS_AUXILIARES AUX2\n" +
                        "    ON (CLI.I_SITUACION = AUX2.CODIGO AND AUX2.TIPO = 54)\n" +
                        " WHERE A.C_VENDEDOR = '"+CODIGO_VENDEDOR+"' \n" +
                        "   AND (D.C_CLIENTE = '"+iCOD_CLIENTE+"' OR '"+iCOD_CLIENTE+"' = 'XXX')\n" +
                        "   AND (CLI.NOMBRE LIKE '%"+iRAZON_SOCIAL+"%' OR '" + iRAZON_SOCIAL + "' = 'XXX')  \n" +
                        "   AND  (CLI.COD_UBC = '" +iDISTRITO+"' OR '" + iDISTRITO +"' = 'XXX' ) \n" +
                        "   AND substr(A.F_VISITA,7,4) || substr(A.F_VISITA,4,2) || substr(A.F_VISITA,1,2) = '" + iFECHA+"' \n";

            //SI ROL=SUPERVISOR
            if (TIPO_VENDEDOR.equals("C")) {
                SQL = "SELECT * FROM ( " + SQL + " ) K ORDER BY K.COD_UBC ASC, K.I_SITUACION ASC, K.DIAS DESC, K.TOTAL_DEUDA DESC, K.DIRECCION ASC";
            }
            else
            {
                SQL =  SQL + " ORDER BY VC.N_ORD_VISITA ASC ";
            }




            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<VisitaDetBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    visitaDetBE = new VisitaDetBE();
                    visitaDetBE.setN_INFORME(Funciones.isNullColumn(cursor,"N_INFORME",0));
                    visitaDetBE.setC_CLIENTE(Funciones.isNullColumn(cursor,"C_CLIENTE","").toString());
                    visitaDetBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL","").toString());
                    visitaDetBE.setCOD_UBC(Funciones.isNullColumn(cursor,"COD_UBC","").toString());
                    visitaDetBE.setI_SITUACION(Funciones.isNullColumn(cursor,"I_SITUACION",0));
                    visitaDetBE.setSITUACION(Funciones.isNullColumn(cursor,"SITUACION","").toString());
                    visitaDetBE.setDIAS(Funciones.isNullColumn(cursor,"DIAS",0));
                    visitaDetBE.setTOTAL_DEUDA(Funciones.isNullColumn(cursor,"TOTAL_DEUDA",0.0));
                    visitaDetBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION","").toString());
                    visitaDetBE.setFRECUENCIA_VISITA(Funciones.isNullColumn(cursor,"FRECUENCIA_VISITA","").toString());
                    visitaDetBE.setORDEN_VISITA(Funciones.isNullColumn(cursor,"ORDEN_VISITA",0));
                    visitaDetBE.setN_SEQUENCIA(Funciones.isNullColumn(cursor,"N_SEQUENCIA",0));
                    visitaDetBE.setHORA_I(Funciones.isNullColumn(cursor,"HORA_I",""));
                    visitaDetBE.setVISITADO(Funciones.isNullColumn(cursor,"VISITADO",0));
                    visitaDetBE.setF_VISITA(Funciones.isNullColumn(cursor,"F_VISITA",""));

                    lst.add(visitaDetBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }




    public void getVisita(    String iN_INFORME,
                              String iN_SEQUENCIA) {

        Cursor cursor = null;

        VisitaDetBE visitaDetBE = null;
        String SQL = "";

        try {


            SQL="SELECT     A.N_INFORME,\n" +
                    "       A.N_SEQUENCIA,\n" +
                    "       A.C_CLIENTE,\n" +
                    "       S.NOMBRE AS NOMBRE_CLIENTE,\n" +
                    "       A.CONTACTO,\n" +
                    "       A.HORA_I,\n" +
                    "       A.HORA_S,\n" +
                    "       A.GESTION_V,\n" +
                    "       A.GESTION_C,\n" +
                    "       A.RESULTADO_V,\n" +
                    "       A.RESULTADO_C,\n" +
                    "       A.OBJECION_V,\n" +
                    "       A.OBJECION_C,\n" +
                    "       A.OBJECION_O,\n" +
                    "       A.C_VENDEDOR,\n" +
                    "       A.C_USUARIO,\n" +
                    "       A.C_PERFIL,\n" +
                    "       A.C_CPU,\n" +
                    "       A.F_REGISTRO,\n" +
                    "       A.C_USUARIO_MOD,\n" +
                    "       A.C_PERFIL_MOD,\n" +
                    "       A.F_MODIFICA,\n" +
                    "       A.C_CPU_MOD,\n" +
                    "       A.GESTION_O,\n" +
                    "       A.OBSERVACION,\n" +
                    "       A.M_PEDIDO,\n" +
                    "       A.M_COBRANZA,\n" +
                    "       A.NUEVO_DIA_VISITA,\n" +
                    "       A.M_FACTURADO,\n" +
                    "       A.NUEVO_DIA_COBRANZA\n" +
                    "  FROM VEM_VISITA_DET A INNER JOIN CLIENTES S \n" +
                    "    ON(A.C_CLIENTE = S.COD_CLIENTE) \n" +
                    " WHERE N_INFORME = "+iN_INFORME+" AND  N_SEQUENCIA = "+iN_SEQUENCIA;


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<VisitaDetBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    visitaDetBE = new VisitaDetBE();
                    visitaDetBE.setN_INFORME(Funciones.isNullColumn(cursor,"N_INFORME",0));
                    visitaDetBE.setN_SEQUENCIA(Funciones.isNullColumn(cursor,"N_SEQUENCIA",0));
                    visitaDetBE.setC_CLIENTE(Funciones.isNullColumn(cursor,"C_CLIENTE",""));
                    visitaDetBE.setNOMBRE_CLIENTE(Funciones.isNullColumn(cursor,"NOMBRE_CLIENTE",""));
                    visitaDetBE.setCONTACTO(Funciones.isNullColumn(cursor,"CONTACTO",""));
                    visitaDetBE.setHORA_I(Funciones.isNullColumn(cursor,"HORA_I",""));
                    visitaDetBE.setHORA_S(Funciones.isNullColumn(cursor,"HORA_S",""));
                    visitaDetBE.setGESTION_V(Funciones.isNullColumn(cursor,"GESTION_V",""));
                    visitaDetBE.setGESTION_C(Funciones.isNullColumn(cursor,"GESTION_C",""));
                    visitaDetBE.setRESULTADO_V(Funciones.isNullColumn(cursor,"RESULTADO_V",""));
                    visitaDetBE.setRESULTADO_C(Funciones.isNullColumn(cursor,"RESULTADO_C",""));
                    visitaDetBE.setOBJECION_V(Funciones.isNullColumn(cursor,"OBJECION_V",""));
                    visitaDetBE.setOBJECION_C(Funciones.isNullColumn(cursor,"OBJECION_C",""));
                    visitaDetBE.setOBJECION_O(Funciones.isNullColumn(cursor,"OBJECION_O",""));
                    visitaDetBE.setC_VENDEDOR(Funciones.isNullColumn(cursor,"C_VENDEDOR",""));
                    visitaDetBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    visitaDetBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    visitaDetBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    visitaDetBE.setF_REGISTRO(Funciones.isNullColumn(cursor,"F_REGISTRO",""));
                    visitaDetBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    visitaDetBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    visitaDetBE.setF_MODIFICA(Funciones.isNullColumn(cursor,"F_MODIFICA",""));
                    visitaDetBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    visitaDetBE.setGESTION_O(Funciones.isNullColumn(cursor,"GESTION_O",""));
                    visitaDetBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    visitaDetBE.setM_PEDIDO(Funciones.isNullColumn(cursor,"M_PEDIDO",0));
                    visitaDetBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0));
                    visitaDetBE.setNUEVO_DIA_VISITA(Funciones.isNullColumn(cursor,"NUEVO_DIA_VISITA",""));
                    visitaDetBE.setM_FACTURADO(Funciones.isNullColumn(cursor,"M_FACTURADO",0));
                    visitaDetBE.setNUEVO_DIA_COBRANZA(Funciones.isNullColumn(cursor,"NUEVO_DIA_COBRANZA",""));



                    lst.add(visitaDetBE);
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

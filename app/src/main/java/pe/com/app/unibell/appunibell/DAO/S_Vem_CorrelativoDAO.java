package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Vem_CorrelativoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Vem_CorrelativoDAO {

    public ArrayList<S_Vem_CorrelativoBE> lst = null;

    public void getAll(int iTIPO_COMPROBANTE, int iID_CANAL, int iID_EMPRESA, int iID_LOCAL, int iESTADO, int iAUTOMATICO) {
        Cursor cursor = null;
        S_Vem_CorrelativoBE s_vem_correlativoBE = null;
        Double dTipoCambio=0.0;
        try {
            String SQL="SELECT C.TIPO_COMPROBANTE,\n" +
                    "       C.ID_CANAL,\n" +
                    "       C.NRO_SERIE,\n" +
                    "       C.NRO,\n" +
                    "       C.ID_EMPRESA,\n" +
                    "       C.ID_LOCAL,\n" +
                    "       C.ESTADO,\n" +
                    "       C.FECHA_REGISTRO,\n" +
                    "       C.FECHA_MODIFICACION,\n" +
                    "       C.USUARIO_REGISTRO,\n" +
                    "       C.USUARIO_MODIFICACION,\n" +
                    "       C.PC_REGISTRO,\n" +
                    "       C.PC_MODIFICACION,\n" +
                    "       C.IP_REGISTRO,\n" +
                    "       C.IP_MODIFICACION,\n" +
                    "       C.AUTOMATICO,\n" +
                    "       C.ID_USUARIO,\n" +
                    "       (D.APELLIDO_PATERNO || ' ' || D.APELLIDO_MATERNO || ' ' || D.NOMBRES) AS NOMBRE_USUARIO\n" +
                    "  FROM S_VEM_CORRELATIVO C\n" +
                    " INNER JOIN PARTABLA T\n" +
                    "    ON (C.TIPO_COMPROBANTE = T.IDTABLA) LEFT JOIN S_GEM_PERSONA D\n" +
                    "    ON(C.ID_USUARIO=D.ID_PERSONA)\n" +
                    " WHERE T.GRUPO = "+ iTIPO_COMPROBANTE +" \n" +
                    "   AND (C.ID_CANAL = "+iID_CANAL +" OR " + iID_CANAL +" = 0)\n" +
                    "   AND C.ID_EMPRESA = "+ iID_EMPRESA +"\n" +
                    "   AND (C.ID_LOCAL = " + iID_LOCAL + " OR "+ iID_LOCAL + "=0)\n" +
                    "   AND C.ESTADO = "+ iESTADO +"\n" +
                    "   AND C.AUTOMATICO = " + iAUTOMATICO +"\n" +
                    " ORDER BY NRO_SERIE;";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Vem_CorrelativoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_vem_correlativoBE = new S_Vem_CorrelativoBE();
                    s_vem_correlativoBE.setTIPO_COMPROBANTE(Funciones.isNullColumn(cursor,"TIPO_COMPROBANTE",0));
                    s_vem_correlativoBE.setID_CANAL(Funciones.isNullColumn(cursor,"ID_CANAL",0));
                    s_vem_correlativoBE.setNRO_SERIE(Funciones.isNullColumn(cursor,"NRO_SERIE",""));
                    s_vem_correlativoBE.setNRO(Funciones.isNullColumn(cursor,"NRO",0));
                    s_vem_correlativoBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_vem_correlativoBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    s_vem_correlativoBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_vem_correlativoBE.setAUTOMATICO(Funciones.isNullColumn(cursor,"AUTOMATICO",0));
                    s_vem_correlativoBE.setID_USUARIO(Funciones.isNullColumn(cursor,"ID_USUARIO",0));

                    lst.add(s_vem_correlativoBE);
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

package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gem_VendedorBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_VendedorDAO {
    public ArrayList<S_Gem_VendedorBE> lst = null;

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Gem_VendedorBE s_gem_vendedorBE = null;
        try {
            String SQL="SELECT  V.ID_PERSONA,V.ID_EMPRESA,V.ID_LOCAL,V.TIPO_VENDEDOR,V.FECHA_CESE,V.ESTADO, V.VISIBLE,V.VALIDA_RECIBO,V.ID_CANAL, \n" +
                    " (P.APELLIDO_PATERNO || ' ' || P.APELLIDO_MATERNO || ' ' || P.NOMBRES) AS NOMBRE_COMPLETO  \n" +
                    " FROM S_GEM_VENDEDOR V INNER JOIN S_GEM_PERSONA P \n" +
                    " ON(V.ID_PERSONA = P.ID_PERSONA) \n" +
                    " WHERE (V.ID_PERSONA ="+pID_PERSONA+" OR " + pID_PERSONA + "= 0 ) \n" +
                    " AND V.ID_PERSONA < 800 AND V.ESTADO = 40001 \n" +
                    " ORDER BY P.APELLIDO_PATERNO ASC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_VendedorBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_vendedorBE = new S_Gem_VendedorBE();
                    s_gem_vendedorBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                    s_gem_vendedorBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_gem_vendedorBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    s_gem_vendedorBE.setTIPO_VENDEDOR(Funciones.isNullColumn(cursor,"TIPO_VENDEDOR",0));
                    s_gem_vendedorBE.setFECHA_CESE(Funciones.isNullColumn(cursor,"FECHA_CESE",""));
                    s_gem_vendedorBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_gem_vendedorBE.setVISIBLE(Funciones.isNullColumn(cursor,"VISIBLE",0));
                    s_gem_vendedorBE.setVALIDA_RECIBO(Funciones.isNullColumn(cursor,"VALIDA_RECIBO",0));
                    s_gem_vendedorBE.setID_CANAL(Funciones.isNullColumn(cursor,"ID_CANAL",0));
                    s_gem_vendedorBE.setNOMBRE_COMPLETO(Funciones.isNullColumn(cursor,"NOMBRE_COMPLETO",""));
                   lst.add(s_gem_vendedorBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Gem_VendedorBE s_gem_vendedorBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_PERSONA",s_gem_vendedorBE.getID_PERSONA());
            cv.put("ID_EMPRESA",s_gem_vendedorBE.getID_EMPRESA());
            cv.put("ID_LOCAL",s_gem_vendedorBE.getID_LOCAL());
            cv.put("TIPO_VENDEDOR",s_gem_vendedorBE.getTIPO_VENDEDOR());
            cv.put("FECHA_CESE",s_gem_vendedorBE.getFECHA_CESE());
            cv.put("ESTADO",s_gem_vendedorBE.getESTADO());
            cv.put("FECHA_REGISTRO",s_gem_vendedorBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_gem_vendedorBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_gem_vendedorBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_gem_vendedorBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_gem_vendedorBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_gem_vendedorBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_gem_vendedorBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_gem_vendedorBE.getIP_MODIFICACION());
            cv.put("VISIBLE",s_gem_vendedorBE.getVISIBLE());
            cv.put("VALIDA_RECIBO",s_gem_vendedorBE.getVALIDA_RECIBO());
            cv.put("ID_CANAL",s_gem_vendedorBE.getID_CANAL());
            DataBaseHelper.myDataBase.insert("S_GEM_PERSONA_DIRECCION ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

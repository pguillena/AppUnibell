package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gem_Persona_DireccionBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_Persona_DireccionDAO {
    public ArrayList<S_Gem_Persona_DireccionBE> lst = null;

    public void getAll(String pID_DIRECCION) {
        Cursor cursor = null;
        S_Gem_Persona_DireccionBE s_gem_persona_direccionBE = null;
        try {
            String SQL="SELECT ID_DIRECCION,ID_PERSONA,TIPO_DIRECCION,FACTURACION,ID_VIA," +
                    "NOMBRE_VIA,NUMERO,INTERIOR,ID_ZONA,NOMBRE_ZONA,KILOMETRO," +
                    "MANZANA,LOTE,REFERENCIA,OBSERVACION,UBIGEO,DEPARTAMENTO," +
                    "BLOQUE,ETAPA,ESTADO,FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO," +
                    "USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION," +
                    "DIRECCION_COMPLETA,TELEFONO "+
                    "FROM S_GEM_PERSONA_DIRECCION   WHERE (" + pID_DIRECCION + "=-1 OR ID_DIRECCION=" + pID_DIRECCION + ") ORDER BY ID_DIRECCION";


                    
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_Persona_DireccionBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_persona_direccionBE = new S_Gem_Persona_DireccionBE();
                    s_gem_persona_direccionBE.setID_DIRECCION(Funciones.isNullColumn(cursor,"ID_DIRECCION",0));
                    s_gem_persona_direccionBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                    s_gem_persona_direccionBE.setTIPO_DIRECCION(Funciones.isNullColumn(cursor,"TIPO_DIRECCION",0));
                    s_gem_persona_direccionBE.setFACTURACION(Funciones.isNullColumn(cursor,"FACTURACION",0));
                    s_gem_persona_direccionBE.setID_VIA(Funciones.isNullColumn(cursor,"ID_VIA",0));
                    s_gem_persona_direccionBE.setNOMBRE_VIA(Funciones.isNullColumn(cursor,"NOMBRE_VIA",""));
                    s_gem_persona_direccionBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    s_gem_persona_direccionBE.setINTERIOR(Funciones.isNullColumn(cursor,"INTERIOR",""));
                    s_gem_persona_direccionBE.setID_ZONA(Funciones.isNullColumn(cursor,"ID_ZONA",""));
                    s_gem_persona_direccionBE.setNOMBRE_ZONA(Funciones.isNullColumn(cursor,"NOMBRE_ZONA",""));
                    s_gem_persona_direccionBE.setKILOMETRO(Funciones.isNullColumn(cursor,"KILOMETRO",""));
                    s_gem_persona_direccionBE.setMANZANA(Funciones.isNullColumn(cursor,"MANZANA",""));
                    s_gem_persona_direccionBE.setLOTE(Funciones.isNullColumn(cursor,"LOTE",""));
                    s_gem_persona_direccionBE.setREFERENCIA(Funciones.isNullColumn(cursor,"REFERENCIA",""));
                    s_gem_persona_direccionBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    s_gem_persona_direccionBE.setUBIGEO(Funciones.isNullColumn(cursor,"UBIGEO",""));
                    s_gem_persona_direccionBE.setDEPARTAMENTO(Funciones.isNullColumn(cursor,"DEPARTAMENTO",""));
                    s_gem_persona_direccionBE.setBLOQUE(Funciones.isNullColumn(cursor,"BLOQUE",""));
                    s_gem_persona_direccionBE.setETAPA(Funciones.isNullColumn(cursor,"ETAPA",""));
                    s_gem_persona_direccionBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_gem_persona_direccionBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_gem_persona_direccionBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_gem_persona_direccionBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_gem_persona_direccionBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_gem_persona_direccionBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_gem_persona_direccionBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_gem_persona_direccionBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_gem_persona_direccionBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_gem_persona_direccionBE.setDIRECCION_COMPLETA(Funciones.isNullColumn(cursor,"DIRECCION_COMPLETA",""));
                    s_gem_persona_direccionBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO","")); 
                    lst.add(s_gem_persona_direccionBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Gem_Persona_DireccionBE s_gem_persona_direccionBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("ID_DIRECCION",s_gem_persona_direccionBE.getID_DIRECCION());
             cv.put("ID_PERSONA",s_gem_persona_direccionBE.getID_PERSONA());
             cv.put("TIPO_DIRECCION",s_gem_persona_direccionBE.getTIPO_DIRECCION());
             cv.put("FACTURACION",s_gem_persona_direccionBE.getFACTURACION());
             cv.put("ID_VIA",s_gem_persona_direccionBE.getID_VIA());
             cv.put("NOMBRE_VIA",s_gem_persona_direccionBE.getNOMBRE_VIA());
             cv.put("NUMERO",s_gem_persona_direccionBE.getNUMERO());
             cv.put("INTERIOR",s_gem_persona_direccionBE.getINTERIOR());
             cv.put("ID_ZONA",s_gem_persona_direccionBE.getID_ZONA());
             cv.put("NOMBRE_ZONA",s_gem_persona_direccionBE.getNOMBRE_ZONA());
             cv.put("KILOMETRO",s_gem_persona_direccionBE.getKILOMETRO());
             cv.put("MANZANA",s_gem_persona_direccionBE.getMANZANA());
             cv.put("LOTE",s_gem_persona_direccionBE.getLOTE());
             cv.put("REFERENCIA",s_gem_persona_direccionBE.getREFERENCIA());
             cv.put("OBSERVACION",s_gem_persona_direccionBE.getOBSERVACION());
             cv.put("UBIGEO",s_gem_persona_direccionBE.getUBIGEO());
             cv.put("DEPARTAMENTO",s_gem_persona_direccionBE.getDEPARTAMENTO());
             cv.put("BLOQUE",s_gem_persona_direccionBE.getBLOQUE());
             cv.put("ETAPA",s_gem_persona_direccionBE.getETAPA());
             cv.put("ESTADO",s_gem_persona_direccionBE.getESTADO());
             cv.put("FECHA_REGISTRO",s_gem_persona_direccionBE.getFECHA_REGISTRO());
             cv.put("FECHA_MODIFICACION",s_gem_persona_direccionBE.getFECHA_MODIFICACION());
             cv.put("USUARIO_REGISTRO",s_gem_persona_direccionBE.getUSUARIO_REGISTRO());
             cv.put("USUARIO_MODIFICACION",s_gem_persona_direccionBE.getUSUARIO_MODIFICACION());
             cv.put("PC_REGISTRO",s_gem_persona_direccionBE.getPC_REGISTRO());
             cv.put("PC_MODIFICACION",s_gem_persona_direccionBE.getPC_MODIFICACION());
             cv.put("IP_REGISTRO",s_gem_persona_direccionBE.getIP_REGISTRO());
             cv.put("IP_MODIFICACION",s_gem_persona_direccionBE.getIP_MODIFICACION());
             cv.put("DIRECCION_COMPLETA",s_gem_persona_direccionBE.getDIRECCION_COMPLETA());
             cv.put("TELEFONO",s_gem_persona_direccionBE.getTELEFONO());
            DataBaseHelper.myDataBase.insert("S_GEM_PERSONA_DIRECCION ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

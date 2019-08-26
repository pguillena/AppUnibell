package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gem_PersonaBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_PersonaDAO {
    public ArrayList<S_Gem_PersonaBE> lst = null;

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Gem_PersonaBE s_gem_personaBE = null;
        try {
            String SQL="SELECT ID_PERSONA,TIPO_PERSONA,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,DIRECCION," +
                    "RUC,DISTRITO,TELEFONO,SEXO,FECHA_NATAL,ESTADO_CIVIL," +
                    "TIPO_DOC,NRO_DOC,CELULAR,CORREO,ESTADO "+
                    "FROM S_GEM_PERSONA  WHERE (" + pID_PERSONA + "=-1 OR ID_PERSONA=" + pID_PERSONA + ") ORDER BY ID_PERSONA";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_PersonaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                s_gem_personaBE = new S_Gem_PersonaBE();
                s_gem_personaBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                s_gem_personaBE.setTIPO_PERSONA(Funciones.isNullColumn(cursor,"TIPO_PERSONA",0));
                s_gem_personaBE.setAPELLIDO_PATERNO(Funciones.isNullColumn(cursor,"APELLIDO_PATERNO",""));
                s_gem_personaBE.setAPELLIDO_MATERNO(Funciones.isNullColumn(cursor,"APELLIDO_MATERNO",""));
                s_gem_personaBE.setNOMBRES(Funciones.isNullColumn(cursor,"NOMBRES",""));
                s_gem_personaBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                s_gem_personaBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                s_gem_personaBE.setDISTRITO(Funciones.isNullColumn(cursor,"DISTRITO",""));
                s_gem_personaBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                s_gem_personaBE.setSEXO(Funciones.isNullColumn(cursor,"SEXO",0));
                s_gem_personaBE.setFECHA_NATAL(Funciones.isNullColumn(cursor,"FECHA_NATAL",""));
                s_gem_personaBE.setESTADO_CIVIL(Funciones.isNullColumn(cursor,"ESTADO_CIVIL",0));
                s_gem_personaBE.setTIPO_DOC(Funciones.isNullColumn(cursor,"TIPO_DOC",0));
                s_gem_personaBE.setNRO_DOC(Funciones.isNullColumn(cursor,"NRO_DOC",""));
                s_gem_personaBE.setCELULAR(Funciones.isNullColumn(cursor,"CELULAR",""));
                s_gem_personaBE.setCORREO(Funciones.isNullColumn(cursor,"CORREO",""));
                s_gem_personaBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                lst.add(s_gem_personaBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Gem_PersonaBE s_gem_personaBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_PERSONA",s_gem_personaBE.getID_PERSONA());
            cv.put("TIPO_PERSONA",s_gem_personaBE.getTIPO_PERSONA());
            cv.put("APELLIDO_PATERNO",s_gem_personaBE.getAPELLIDO_PATERNO());
            cv.put("APELLIDO_MATERNO",s_gem_personaBE.getAPELLIDO_MATERNO());
            cv.put("NOMBRES",s_gem_personaBE.getNOMBRES());
            cv.put("DIRECCION",s_gem_personaBE.getDIRECCION());
            cv.put("RUC",s_gem_personaBE.getRUC());
            cv.put("DISTRITO",s_gem_personaBE.getDISTRITO());
            cv.put("TELEFONO",s_gem_personaBE.getTELEFONO());
            cv.put("SEXO",s_gem_personaBE.getSEXO());
            cv.put("FECHA_NATAL",s_gem_personaBE.getFECHA_NATAL());
            cv.put("ESTADO_CIVIL",s_gem_personaBE.getESTADO_CIVIL());
            cv.put("TIPO_DOC",s_gem_personaBE.getTIPO_DOC());
            cv.put("NRO_DOC",s_gem_personaBE.getNRO_DOC());
            cv.put("CELULAR",s_gem_personaBE.getCELULAR());
            cv.put("CORREO",s_gem_personaBE.getCORREO());
            cv.put("ESTADO",s_gem_personaBE.getESTADO());
            DataBaseHelper.myDataBase.insert("S_GEM_PERSONA",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

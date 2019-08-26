package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.BE.S_Sem_LocalBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sem_LocalDAO {

    public ArrayList<S_Sem_LocalBE> lst = null;

    public void getAllBY(String pID_EMPRESA,String iID_VENDEDOR) {
        Cursor cursor = null;
        S_Sem_LocalBE s_sem_localBE = null;
        try {
            String SQL="SELECT L.ID_EMPRESA,L.ID_LOCAL,NOMBRE "+
                    " FROM S_Sem_Local L" +
                    " INNER JOIN S_SEA_USUARIO_LOCAL U ON U.ID_EMPRESA=L.ID_EMPRESA AND U.ID_LOCAL=L.ID_LOCAL" +
                    " WHERE U.ID_EMPRESA=" + pID_EMPRESA + " AND U.ID_PERSONA=" + iID_VENDEDOR;

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sem_LocalBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sem_localBE = new S_Sem_LocalBE();
                    s_sem_localBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_sem_localBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    s_sem_localBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));
                   /* s_sem_localBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    s_sem_localBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    s_sem_localBE.setFAX(Funciones.isNullColumn(cursor,"FAX",""));
                    s_sem_localBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sem_localBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_sem_localBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_sem_localBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_sem_localBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_sem_localBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_sem_localBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_sem_localBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_sem_localBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_sem_localBE.setPRINCIPAL(Funciones.isNullColumn(cursor,"PRINCIPAL",0));
                    s_sem_localBE.setESPROVINCIA(Funciones.isNullColumn(cursor,"ESPROVINCIA",0));
                    s_sem_localBE.setUBIGEO(Funciones.isNullColumn(cursor,"UBIGEO",""));
                    s_sem_localBE.setC_SUC_EMP(Funciones.isNullColumn(cursor,"C_SUC_EMP",""));*/
                    lst.add(s_sem_localBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sem_LocalBE s_sem_localBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_EMPRESA",s_sem_localBE.getID_EMPRESA());
            cv.put("ID_LOCAL",s_sem_localBE.getID_LOCAL());
            cv.put("NOMBRE",s_sem_localBE.getNOMBRE());
            cv.put("DIRECCION",s_sem_localBE.getDIRECCION());
            cv.put("TELEFONO",s_sem_localBE.getTELEFONO());
            cv.put("FAX",s_sem_localBE.getFAX());
            cv.put("ESTADO",s_sem_localBE.getESTADO());
            cv.put("FECHA_REGISTRO",s_sem_localBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_localBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_localBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_localBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_localBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_localBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_localBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_localBE.getIP_MODIFICACION());
            cv.put("PRINCIPAL",s_sem_localBE.getPRINCIPAL());
            cv.put("ESPROVINCIA",s_sem_localBE.getESPROVINCIA());
            cv.put("UBIGEO",s_sem_localBE.getUBIGEO());
            cv.put("C_SUC_EMP",s_sem_localBE.getC_SUC_EMP());
            DataBaseHelper.myDataBase.insert("S_Sem_Local",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(S_Sem_LocalBE s_sem_localBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_EMPRESA",s_sem_localBE.getID_EMPRESA());
            cv.put("ID_LOCAL",s_sem_localBE.getID_LOCAL());
            cv.put("NOMBRE",s_sem_localBE.getNOMBRE());
            cv.put("DIRECCION",s_sem_localBE.getDIRECCION());
            cv.put("TELEFONO",s_sem_localBE.getTELEFONO());
            cv.put("FAX",s_sem_localBE.getFAX());
            cv.put("ESTADO",s_sem_localBE.getESTADO());
            cv.put("FECHA_REGISTRO",s_sem_localBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_localBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_localBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_localBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_localBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_localBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_localBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_localBE.getIP_MODIFICACION());
            cv.put("PRINCIPAL",s_sem_localBE.getPRINCIPAL());
            cv.put("ESPROVINCIA",s_sem_localBE.getESPROVINCIA());
            cv.put("UBIGEO",s_sem_localBE.getUBIGEO());
            cv.put("C_SUC_EMP",s_sem_localBE.getC_SUC_EMP());
            DataBaseHelper.myDataBase.update("S_Sem_Local",cv,"ID_EMPRESA = ?",
                    new String[]{String.valueOf(s_sem_localBE.getID_EMPRESA())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(S_Sem_LocalBE s_sem_localBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("S_Sem_Local","ID_EMPRESA = ?", new String[]{String.valueOf(s_sem_localBE.getID_EMPRESA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

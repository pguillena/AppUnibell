package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sea_Usuario_LocalBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sea_Usuario_LocalDAO {
    public ArrayList<S_Sea_Usuario_LocalBE> lst = null;

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Sea_Usuario_LocalBE s_sea_usuario_localBE = null;
        try {
            String SQL="SELECT ID_PERSONA,ID_EMPRESA,ID_LOCAL,C_PERFIL,ROL,ESTADO," +
                    "FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
                    "IP_REGISTRO,IP_MODIFICACION,PRINCIPAL "+
                    "FROM S_SEA_USUARIO_LOCAL WHERE (" + pID_PERSONA + "=-1 OR ID_PERSONA=" + pID_PERSONA + ") ORDER BY ID_PERSONA";
                  
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sea_Usuario_LocalBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sea_usuario_localBE = new S_Sea_Usuario_LocalBE(); 
                    s_sea_usuario_localBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                    s_sea_usuario_localBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_sea_usuario_localBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    s_sea_usuario_localBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    s_sea_usuario_localBE.setROL(Funciones.isNullColumn(cursor,"ROL",0));
                    s_sea_usuario_localBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sea_usuario_localBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_sea_usuario_localBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_sea_usuario_localBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_sea_usuario_localBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_sea_usuario_localBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_sea_usuario_localBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_sea_usuario_localBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_sea_usuario_localBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_sea_usuario_localBE.setPRINCIPAL(Funciones.isNullColumn(cursor,"PRINCIPAL",0));                            
                    lst.add(s_sea_usuario_localBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sea_Usuario_LocalBE s_sea_usuario_localBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("ID_PERSONA",s_sea_usuario_localBE.getID_PERSONA());
             cv.put("ID_EMPRESA",s_sea_usuario_localBE.getID_EMPRESA());
             cv.put("ID_LOCAL",s_sea_usuario_localBE.getID_LOCAL());
             cv.put("C_PERFIL",s_sea_usuario_localBE.getC_PERFIL());
             cv.put("ROL",s_sea_usuario_localBE.getROL());
             cv.put("ESTADO",s_sea_usuario_localBE.getESTADO());
             cv.put("FECHA_REGISTRO",s_sea_usuario_localBE.getFECHA_REGISTRO());
             cv.put("FECHA_MODIFICACION",s_sea_usuario_localBE.getFECHA_MODIFICACION());
             cv.put("USUARIO_REGISTRO",s_sea_usuario_localBE.getUSUARIO_REGISTRO());
             cv.put("USUARIO_MODIFICACION",s_sea_usuario_localBE.getUSUARIO_MODIFICACION());
             cv.put("PC_REGISTRO",s_sea_usuario_localBE.getPC_REGISTRO());
             cv.put("PC_MODIFICACION",s_sea_usuario_localBE.getPC_MODIFICACION());
             cv.put("IP_REGISTRO",s_sea_usuario_localBE.getIP_REGISTRO());
             cv.put("IP_MODIFICACION",s_sea_usuario_localBE.getIP_MODIFICACION());
             cv.put("PRINCIPAL",s_sea_usuario_localBE.getPRINCIPAL());
            DataBaseHelper.myDataBase.insert("S_SEA_USUARIO_LOCAL ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sem_PerfilBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sem_PerfilDAO {
    public ArrayList<S_Sem_PerfilBE> lst = null;

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Sem_PerfilBE s_sem_perfilBE = null;
        try {
            String SQL="SELECT C_PERFIL,NOMBRE_PERFIL,COMENTARIO,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                    "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,FECHA_REGISTRO,FECHA_MODIFICACION,ESTADO," +
                    "ID_EMPRESA "+
                    "FROM S_SEM_PERFIL WHERE (" + pID_PERSONA + "=-1 OR ID_PERSONA=" + pID_PERSONA + ") ORDER BY ID_PERSONA";


                    
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sem_PerfilBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sem_perfilBE = new S_Sem_PerfilBE();
                    s_sem_perfilBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    s_sem_perfilBE.setNOMBRE_PERFIL(Funciones.isNullColumn(cursor,"NOMBRE_PERFIL",""));
                    s_sem_perfilBE.setCOMENTARIO(Funciones.isNullColumn(cursor,"COMENTARIO",""));
                    s_sem_perfilBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_sem_perfilBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_sem_perfilBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_sem_perfilBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_sem_perfilBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_sem_perfilBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_sem_perfilBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_sem_perfilBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_sem_perfilBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sem_perfilBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    lst.add(s_sem_perfilBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sem_PerfilBE s_sem_perfilBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("C_PERFIL",s_sem_perfilBE.getC_PERFIL());
             cv.put("NOMBRE_PERFIL",s_sem_perfilBE.getNOMBRE_PERFIL());
             cv.put("COMENTARIO",s_sem_perfilBE.getCOMENTARIO());
             cv.put("USUARIO_REGISTRO",s_sem_perfilBE.getUSUARIO_REGISTRO());
             cv.put("USUARIO_MODIFICACION",s_sem_perfilBE.getUSUARIO_MODIFICACION());
             cv.put("PC_REGISTRO",s_sem_perfilBE.getPC_REGISTRO());
             cv.put("PC_MODIFICACION",s_sem_perfilBE.getPC_MODIFICACION());
             cv.put("IP_REGISTRO",s_sem_perfilBE.getIP_REGISTRO());
             cv.put("IP_MODIFICACION",s_sem_perfilBE.getIP_MODIFICACION());
             cv.put("FECHA_REGISTRO",s_sem_perfilBE.getFECHA_REGISTRO());
             cv.put("FECHA_MODIFICACION",s_sem_perfilBE.getFECHA_MODIFICACION());
             cv.put("ESTADO",s_sem_perfilBE.getESTADO());
             cv.put("ID_EMPRESA",s_sem_perfilBE.getID_EMPRESA());
            DataBaseHelper.myDataBase.insert("S_SEM_PERFIL",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

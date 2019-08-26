package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sem_UsuarioBE;
import pe.com.app.unibell.appunibell.BE.UsuarioBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sem_UsuarioDAO {

    public ArrayList<S_Sem_UsuarioBE> lst = null;
    private Funciones funciones=new Funciones();

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Sem_UsuarioBE s_sem_usuarioBE = null;
        try {
            String SQL="SELECT ID_PERSONA ,CREDENCIAL,CLAVE,ESTADO,RESETEADO," +
                    " FECHA_REGISTRO,FECHA_MODIFICACION,USUARIO_REGISTRO, USUARIO_MODIFICACION,PC_REGISTRO," +
                    " PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,NOMBRE_CORTO,PC_USUARIO"+
                    " FROM S_SEM_USUARIO WHERE (" + pID_PERSONA + "=-1 OR ID_PERSONA=" + pID_PERSONA + ") ORDER BY ID_PERSONA";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sem_UsuarioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                     s_sem_usuarioBE = new S_Sem_UsuarioBE();
                     s_sem_usuarioBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                     s_sem_usuarioBE.setCREDENCIAL(Funciones.isNullColumn(cursor,"CREDENCIAL",""));
                     s_sem_usuarioBE.setCLAVE(Funciones.isNullColumn(cursor,"CLAVE",""));
                     s_sem_usuarioBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                     s_sem_usuarioBE.setRESETEADO(Funciones.isNullColumn(cursor,"RESETEADO",""));
                     s_sem_usuarioBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                     s_sem_usuarioBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                     s_sem_usuarioBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                     s_sem_usuarioBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                     s_sem_usuarioBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                     s_sem_usuarioBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                     s_sem_usuarioBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                     s_sem_usuarioBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                     s_sem_usuarioBE.setNOMBRE_CORTO(Funciones.isNullColumn(cursor,"NOMBRE_CORTO",""));
                     s_sem_usuarioBE.setPC_USUARIO(Funciones.isNullColumn(cursor,"PC_USUARIO",""));
                    lst.add(s_sem_usuarioBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sem_UsuarioBE s_sem_usuarioBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_PERSONA",s_sem_usuarioBE.getID_PERSONA());
            cv.put("CREDENCIAL",s_sem_usuarioBE.getCREDENCIAL());
            cv.put("CLAVE",s_sem_usuarioBE.getCLAVE());
            cv.put("ESTADO",s_sem_usuarioBE.getESTADO().toString());
            cv.put("RESETEADO",s_sem_usuarioBE.getRESETEADO());
            cv.put("FECHA_REGISTRO",s_sem_usuarioBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_usuarioBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_usuarioBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_usuarioBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_usuarioBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_usuarioBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_usuarioBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_usuarioBE.getIP_MODIFICACION());
            cv.put("NOMBRE_CORTO",s_sem_usuarioBE.getNOMBRE_CORTO());
            cv.put("PC_USUARIO",s_sem_usuarioBE.getPC_USUARIO());
            DataBaseHelper.myDataBase.insert("S_Sem_Usuario",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(S_Sem_UsuarioBE s_sem_usuarioBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_PERSONA",s_sem_usuarioBE.getID_PERSONA());
            cv.put("CREDENCIAL",s_sem_usuarioBE.getCREDENCIAL());
            cv.put("CLAVE",s_sem_usuarioBE.getCLAVE());
            cv.put("ESTADO",s_sem_usuarioBE.getESTADO().toString());
            cv.put("RESETEADO",s_sem_usuarioBE.getRESETEADO());
            cv.put("FECHA_REGISTRO",s_sem_usuarioBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_usuarioBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_usuarioBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_usuarioBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_usuarioBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_usuarioBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_usuarioBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_usuarioBE.getIP_MODIFICACION());
            cv.put("NOMBRE_CORTO",s_sem_usuarioBE.getNOMBRE_CORTO());
            cv.put("PC_USUARIO",s_sem_usuarioBE.getPC_USUARIO());
            DataBaseHelper.myDataBase.update("S_Sem_Usuario",cv,"ID_PERSONA = ?",
                    new String[]{String.valueOf(s_sem_usuarioBE.getID_PERSONA())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(S_Sem_UsuarioBE s_sem_usuarioBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("S_Sem_Usuario","ID_PERSONA = ?", new String[]{String.valueOf(s_sem_usuarioBE.getID_PERSONA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public void getLogin(String USU_NOMBRE, String CLAVE) {
        Cursor cursor = null;
        S_Sem_UsuarioBE s_sem_usuarioBE = null;
        try {
            String SQL="SELECT A.ID_PERSONA,CREDENCIAL,CLAVE,A.ESTADO,RESETEADO,B.ID_EMPRESA,B.ID_LOCAL,B.C_PERFIL,B.ROL,V.VALIDA_RECIBO, P.NOMBRES, P.APELLIDO_PATERNO, P.APELLIDO_MATERNO " +
                    " FROM S_SEM_USUARIO A " +
                    " LEFT JOIN S_GEM_PERSONA P ON(A.ID_PERSONA = P.ID_PERSONA) " +
                    "LEFT JOIN S_SEA_USUARIO_LOCAL B ON(A.ID_PERSONA=B.ID_PERSONA) " +
                    "INNER JOIN S_GEM_VENDEDOR V ON V.ID_PERSONA=A.ID_PERSONA " +
                    "WHERE Upper(CREDENCIAL)='" + USU_NOMBRE.toUpperCase()  + "'";
                     //" AND CLAVE=" + funciones.md5(CLAVE)
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);

            lst = new ArrayList<S_Sem_UsuarioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sem_usuarioBE = new S_Sem_UsuarioBE();
                    s_sem_usuarioBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                    s_sem_usuarioBE.setCREDENCIAL(Funciones.isNullColumn(cursor,"CREDENCIAL",""));
                    s_sem_usuarioBE.setCLAVE(Funciones.isNullColumn(cursor,"CLAVE",""));
                    s_sem_usuarioBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sem_usuarioBE.setRESETEADO(Funciones.isNullColumn(cursor,"RESETEADO",""));
                    s_sem_usuarioBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",""));
                    s_sem_usuarioBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",""));
                    s_sem_usuarioBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    s_sem_usuarioBE.setROL(Funciones.isNullColumn(cursor,"ROL",""));
                    s_sem_usuarioBE.setVALIDA_RECIBO(Funciones.isNullColumn(cursor,"VALIDA_RECIBO",0));
                    s_sem_usuarioBE.setNOMBRES(Funciones.isNullColumn(cursor,"NOMBRES",""));
                    s_sem_usuarioBE.setAPELLIDO_PATERNO(Funciones.isNullColumn(cursor,"APELLIDO_PATERNO",""));
                    s_sem_usuarioBE.setAPELLIDO_MATERNO(Funciones.isNullColumn(cursor,"APELLIDO_MATERNO",""));
                    lst.add(s_sem_usuarioBE);
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

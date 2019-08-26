package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.UsuarioBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

/**
 * Created by rgalvez on 19/08/2016.
 */
public class UsuarioDAO {
    public ArrayList<UsuarioBE> lisUsuario = null;

    public void getAllSQLite() {
        Cursor cursor = null;
        UsuarioBE usuarioBE = null;
        try {
            String[] campos = new String[] {"USU_CORREL","USU_NOMBRE","USU_PASSWO","ROL_CORREL","USU_ESTADO"};
            cursor = DataBaseHelper.myDataBase.query("REST_USUARIO", null, null,null, null, null, "USU_NOMBRE");
            lisUsuario = new ArrayList<UsuarioBE>();
            lisUsuario.clear();
            if (cursor.moveToFirst()) {
                do {
                    usuarioBE = new UsuarioBE();
                    usuarioBE.setUSU_CORREL(Funciones.isNullColumn(cursor,"USU_CORREL",0));
                    usuarioBE.setUSU_NOMBRE(Funciones.isNullColumn(cursor,"USU_NOMBRE",""));
                    usuarioBE.setUSU_PASSWO(Funciones.isNullColumn(cursor,"USU_PASSWO",""));
                    usuarioBE.setROL_CORREL(Funciones.isNullColumn(cursor,"ROL_CORREL",0));
                    usuarioBE.setUSU_ESTADO(Funciones.isNullColumn(cursor,"USU_ESTADO",0));

                    lisUsuario.add(new UsuarioBE(usuarioBE.getUSU_CORREL(),usuarioBE.getUSU_NOMBRE(),usuarioBE.getUSU_PASSWO(),usuarioBE.getROL_CORREL(),usuarioBE.getUSU_ESTADO()));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getAllSQLitePerfil() {
        Cursor cursor = null;
        UsuarioBE usuarioBE = null;
        try {
            String SQL="SELECT U.USU_CORREL,U.USU_NOMBRE,U.USU_PASSWO,U.ROL_CORREL,U.USU_ESTADO,P.TIU_NOMBRE FROM REST_USUARIO U INNER JOIN REST_USUARIO_TIPO P ON P.TIU_CORREL= U.ROL_CORREL";
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            //String[] campos = new String[] {"USU_CORREL","USU_NOMBRE","USU_PASSWO","ROL_CORREL","USU_ESTADO"};
            //cursor = DataBaseHelper.myDataBase.query("REST_USUARIO", null, null,null, null, null, "USU_NOMBRE");
            lisUsuario = new ArrayList<UsuarioBE>();
            lisUsuario.clear();
            if (cursor.moveToFirst()) {
                do {
                    usuarioBE = new UsuarioBE();
                    usuarioBE.setUSU_CORREL(Funciones.isNullColumn(cursor,"USU_CORREL",0));
                    usuarioBE.setUSU_NOMBRE(Funciones.isNullColumn(cursor,"USU_NOMBRE",""));
                    usuarioBE.setUSU_PASSWO(Funciones.isNullColumn(cursor,"USU_PASSWO",""));
                    usuarioBE.setROL_CORREL(Funciones.isNullColumn(cursor,"ROL_CORREL",0));
                    usuarioBE.setUSU_ESTADO(Funciones.isNullColumn(cursor,"USU_ESTADO",0));
                    usuarioBE.setTIU_NOMBRE(Funciones.isNullColumn(cursor,"TIU_NOMBRE",""));
                    lisUsuario.add(new UsuarioBE(usuarioBE.getUSU_CORREL(),usuarioBE.getUSU_NOMBRE(),usuarioBE.getUSU_PASSWO(),usuarioBE.getROL_CORREL(),usuarioBE.getUSU_ESTADO(),usuarioBE.getTIU_NOMBRE()));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getSQLiteByUsuario(String USU_NOMBRE, String CLAVE) {
        Cursor cursor = null;
        UsuarioBE usuarioBE = null;
        try {
            String SQL="SELECT  FROM REST_USUARIO" +
                    " INNER JOIN REST_USUARIO_TIPO ON  REST_USUARIO_TIPO.TIU_CORREL=ROL_CORREL WHERE USU_ESTADO=1 AND Upper(USU_NOMBRE)='" + USU_NOMBRE.toUpperCase() + "' AND Upper(USU_PASSWO)='"+ CLAVE.toUpperCase() + "'" ;
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);

            /*String[] campos = new String[] {"USU_CORREL","USU_NOMBRE","USU_PASSWO","ROL_CORREL","USU_ESTADO"};
            cursor = DataBaseHelper.myDataBase.query("REST_USUARIO", null, "Upper(USU_NOMBRE)=? and Upper(USU_PASSWO)=? and USU_ESTADO=1",new String[]{USU_NOMBRE,CLAVE}, null, null, "USU_NOMBRE");
            */
            lisUsuario = new ArrayList<UsuarioBE>();
            lisUsuario.clear();
            if (cursor.moveToFirst()) {
                do {
                    usuarioBE = new UsuarioBE();
                    usuarioBE.setUSU_CORREL(Funciones.isNullColumn(cursor,"USU_CORREL",0));
                    usuarioBE.setUSU_NOMBRE(Funciones.isNullColumn(cursor,"USU_NOMBRE",""));
                    usuarioBE.setUSU_PASSWO(Funciones.isNullColumn(cursor,"USU_PASSWO",""));
                    usuarioBE.setROL_CORREL(Funciones.isNullColumn(cursor,"ROL_CORREL",0));
                    usuarioBE.setUSU_ESTADO(Funciones.isNullColumn(cursor,"USU_ESTADO",0));
                    usuarioBE.setTIU_NOMBRE(Funciones.isNullColumn(cursor,"TIU_NOMBRE",""));
                    lisUsuario.add(usuarioBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public String insertUsuario(UsuarioBE usuarioBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("USU_CORREL",sistemaDAO.MAX_REGISTRO("REST_USUARIO","USU_CORREL"));
            cv.put("USU_NOMBRE",usuarioBE.getUSU_NOMBRE());
            cv.put("USU_PASSWO",usuarioBE.getUSU_PASSWO());
            cv.put("ROL_CORREL",usuarioBE.getROL_CORREL());
            cv.put("USU_ESTADO",usuarioBE.getUSU_ESTADO());
            DataBaseHelper.myDataBase.insert("REST_USUARIO",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String updateUsuario(UsuarioBE usuarioBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("USU_NOMBRE",usuarioBE.getUSU_NOMBRE());
            cv.put("USU_PASSWO",usuarioBE.getUSU_PASSWO());
            cv.put("ROL_CORREL",usuarioBE.getROL_CORREL());
            cv.put("USU_ESTADO",usuarioBE.getUSU_ESTADO());
            DataBaseHelper.myDataBase.update("REST_USUARIO",cv,"USU_CORREL= ?",
                    new String[]{String.valueOf(usuarioBE.getUSU_CORREL())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String deleteUsuario(UsuarioBE usuarioBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("REST_USUARIO","USU_CORREL = ?", new String[]{String.valueOf(usuarioBE.getUSU_CORREL())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}


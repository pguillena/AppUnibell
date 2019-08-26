package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Rol_PermisoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

/**
 * Created by rgalvez on 19/08/2016.
 */
public class Rol_PermisoDAO {
    public ArrayList<Rol_PermisoBE> lisCocinaG = null;

    public void getAllSQLite() {
        Cursor cursor = null;
        Rol_PermisoBE rol_permisoBE = null;
        try {
            String[] campos = new String[] {"ROL_CORREL","ROL_NOMBRE","ROL_FCION","ROL_VSIBL","ROL_ADMIN"};
            cursor = DataBaseHelper.myDataBase.query("REST_ROL_PERMISO", null, null,null, null, null, "ROL_NOMBRE");
            lisCocinaG = new ArrayList<Rol_PermisoBE>();
            lisCocinaG.clear();
            if (cursor.moveToFirst()) {
                do {
                    rol_permisoBE = new Rol_PermisoBE();
                    rol_permisoBE.setROL_CORREL(Funciones.isNullColumn(cursor,"ROL_CORREL",0));
                    rol_permisoBE.setROL_NOMBRE(Funciones.isNullColumn(cursor,"ROL_NOMBRE",""));
                    rol_permisoBE.setROL_FCION(Funciones.isNullColumn(cursor,"ROL_FCION",0));
                    rol_permisoBE.setROL_VSIBL(Funciones.isNullColumn(cursor,"ROL_VSIBL",0));
                    rol_permisoBE.setROL_ADMIN(Funciones.isNullColumn(cursor,"ROL_ADMIN",0));

                    lisCocinaG.add(new Rol_PermisoBE(rol_permisoBE.getROL_CORREL(),rol_permisoBE.getROL_NOMBRE(),rol_permisoBE.getROL_FCION(),rol_permisoBE.getROL_VSIBL(),rol_permisoBE.getROL_ADMIN()));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }


    public String insertRol(Rol_PermisoBE rol_permisoBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ROL_CORREL",sistemaDAO.MAX_REGISTRO("REST_ROL_PERMISO","ROL_CORREL"));
            cv.put("ROL_NOMBRE",rol_permisoBE.getROL_NOMBRE());
            cv.put("ROL_FCION",rol_permisoBE.getROL_FCION());
            cv.put("ROL_VSIBL",rol_permisoBE.getROL_VSIBL());
            cv.put("ROL_ADMIN",rol_permisoBE.getROL_ADMIN());
            DataBaseHelper.myDataBase.insert("REST_ROL_PERMISO",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String updateRol(Rol_PermisoBE rol_permisoBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ROL_NOMBRE",rol_permisoBE.getROL_NOMBRE());
            cv.put("ROL_FCION",rol_permisoBE.getROL_FCION());
            cv.put("ROL_VSIBL",rol_permisoBE.getROL_VSIBL());
            cv.put("ROL_ADMIN",rol_permisoBE.getROL_ADMIN());
            DataBaseHelper.myDataBase.update("REST_ROL_PERMISO",cv,"ROL_CORREL= ?",
                    new String[]{String.valueOf(rol_permisoBE.getROL_CORREL())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String deleteRol(String ROL_NOMBRE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("REST_ROL_PERMISO","ROL_NOMBRE = ?", new String[]{ROL_NOMBRE});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }
}


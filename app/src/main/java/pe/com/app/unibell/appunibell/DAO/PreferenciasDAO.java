package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.PreferenciasBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

/**
 * Created by rgalvez on 31/07/2017.
 */

public class PreferenciasDAO {
    public ArrayList<PreferenciasBE> lstPreferencias = null;

    public void getAllSQLite() {
        Cursor cursor = null;
        PreferenciasBE preferenciasBE = null;
        try {
            String[] campos = new String[] {"PRE_KEYNOM","PRE_KEYVAL"};
            cursor = DataBaseHelper.myDataBase.query("REST_PREFERENCIAS", null, null,null, null, null, "PRE_KEYNOM");
            lstPreferencias = new ArrayList<PreferenciasBE>();
            lstPreferencias.clear();
            if (cursor.moveToFirst()) {
                do {
                    preferenciasBE = new PreferenciasBE();
                    preferenciasBE.setPRE_KEYNOM(Funciones.isNullColumn(cursor,"PRE_KEYNOM",""));
                    preferenciasBE.setPRE_KEYVAL(Funciones.isNullColumn(cursor,"PRE_KEYVAL",""));
                    lstPreferencias.add(preferenciasBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String update(PreferenciasBE preferenciasBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("PRE_KEYNOM",preferenciasBE.getPRE_KEYNOM());
            cv.put("PRE_KEYVAL",preferenciasBE.getPRE_KEYVAL());
            DataBaseHelper.myDataBase.update("REST_PREFERENCIAS",cv,"PRE_KEYNOM = ?",
                    new String[]{String.valueOf(preferenciasBE.getPRE_KEYNOM())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

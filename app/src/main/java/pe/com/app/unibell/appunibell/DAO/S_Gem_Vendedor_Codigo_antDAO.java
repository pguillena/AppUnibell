package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Gem_Cliente_Codigo_AntBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_Vendedor_Codigo_AntBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_Vendedor_Codigo_antDAO {

    public ArrayList<S_Gem_Vendedor_Codigo_AntBE> lst = null;

    public void getAll(String ID_VENDEDOR) {
        Cursor cursor = null;
        S_Gem_Vendedor_Codigo_AntBE s_gem_vendedor_codigo_antBE  = null;
        try {
            String SQL="SELECT ID_VENDEDOR,CODIGO_ANTIGUO,FLAG_VIGENCIA " +
                    "FROM S_Gem_Vendedor_Codigo_Ant WHERE  ID_VENDEDOR='" +ID_VENDEDOR +"'";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_Vendedor_Codigo_AntBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_vendedor_codigo_antBE = new S_Gem_Vendedor_Codigo_AntBE();
                    s_gem_vendedor_codigo_antBE.setID_VENDEDOR(Funciones.isNullColumn(cursor,"ID_VENDEDOR",0));
                    s_gem_vendedor_codigo_antBE.setCODIGO_ANTIGUO(Funciones.isNullColumn(cursor,"CODIGO_ANTIGUO",""));
                    s_gem_vendedor_codigo_antBE.setFLAG_VIGENCIA(Funciones.isNullColumn(cursor,"FLAG_VIGENCIA",0));
                    lst.add(s_gem_vendedor_codigo_antBE);
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

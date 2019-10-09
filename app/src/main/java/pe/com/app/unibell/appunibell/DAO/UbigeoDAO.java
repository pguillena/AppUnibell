package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.UbigeoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class UbigeoDAO {

    public ArrayList<UbigeoBE> lst = null;

    public void getAll(String pCODUBIGEO, String pNOMBRE) {
        Cursor cursor = null;
        UbigeoBE ubigeoBE = null;
        try {
            String SQL="SELECT CODUBIGEO, NOMBRE FROM S_GEM_UBIGEO WHERE (CODUBIGEO = 'XXX' OR CODUBIGEO = '" + pCODUBIGEO +"') AND " + " NOMBRE LIKE '%" + pNOMBRE + "%''";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<UbigeoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    ubigeoBE = new UbigeoBE();
                    ubigeoBE.setCODUBIGEO(Funciones.isNullColumn(cursor,"CODUBIGEO",""));
                    ubigeoBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));

                    lst.add(ubigeoBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getDistritos(String pCODUBIGEO) {
        Cursor cursor = null;
        UbigeoBE ubigeoBE = null;
        try {
            String SQL="SELECT CODUBIGEO, NOMBRE FROM S_GEM_UBIGEO WHERE CODUBIGEO LIKE '" + pCODUBIGEO.substring(0,4) +"%' AND CODUBIGEO NOT LIKE '%00'";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<UbigeoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    ubigeoBE = new UbigeoBE();
                    ubigeoBE.setCODUBIGEO(Funciones.isNullColumn(cursor,"CODUBIGEO",""));
                    ubigeoBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));

                    lst.add(ubigeoBE);
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

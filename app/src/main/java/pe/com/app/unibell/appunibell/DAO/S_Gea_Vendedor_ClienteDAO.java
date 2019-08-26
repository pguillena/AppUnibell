package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gea_Vendedor_ClienteBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gea_Vendedor_ClienteDAO {
    public ArrayList<S_Gea_Vendedor_ClienteBE> lst = null;


    public void getAll(String pTIPDOC) {
        Cursor cursor = null;
        S_Gea_Vendedor_ClienteBE s_gea_vendedor_clienteBE  = null;
        try {
            String SQL="SELECT ID_VENDEDOR,ID_CLIENTE " +
                    "FROM S_Gea_Vendedor_Cliente";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gea_Vendedor_ClienteBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gea_vendedor_clienteBE = new S_Gea_Vendedor_ClienteBE();
                    s_gea_vendedor_clienteBE.setID_VENDEDOR(Funciones.isNullColumn(cursor,"ID_VENDEDOR",0));
                    s_gea_vendedor_clienteBE.setID_CLIENTE(Funciones.isNullColumn(cursor,"ID_CLIENTE",0));
                    lst.add(s_gea_vendedor_clienteBE);
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

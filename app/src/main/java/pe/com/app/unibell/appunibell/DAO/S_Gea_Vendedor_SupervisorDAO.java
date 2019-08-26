package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gea_Vendedor_SupervisorBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gea_Vendedor_SupervisorDAO {
    public ArrayList<S_Gea_Vendedor_SupervisorBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        S_Gea_Vendedor_SupervisorBE s_gea_vendedor_supervisorBE = null;
        try {
            String SQL="SELECT ID_VENDEDOR,ID_SUPERVISOR"+
                    "FROM S_GEA_VENDEDOR_SUPERVISOR";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gea_Vendedor_SupervisorBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gea_vendedor_supervisorBE = new S_Gea_Vendedor_SupervisorBE();
                    s_gea_vendedor_supervisorBE.setID_VENDEDOR(Funciones.isNullColumn(cursor,"ID_VENDEDOR",0));
                    s_gea_vendedor_supervisorBE.setID_SUPERVISOR(Funciones.isNullColumn(cursor,"ID_SUPERVISOR",0));
                    lst.add(s_gea_vendedor_supervisorBE);
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

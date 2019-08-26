package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_Gem_Cliente_Codigo_AntBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_Cliente_Codigo_AntDAO {
    public ArrayList<S_Gem_Cliente_Codigo_AntBE> lst = null;

    private Integer  ID_CLIENTE;
    private String  CODIGO_ANTIGUO;
    private Integer FLAG_VIGENCIA;

    public void getAll(String pTIPDOC) {
        Cursor cursor = null;
        S_Gem_Cliente_Codigo_AntBE s_gem_cliente_codigo_antBE  = null;
        try {
            String SQL="SELECT ID_CLIENTE,CODIGO_ANTIGUO,FLAG_VIGENCIA " +
                    "FROM s_gea_vendedor_cliente";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_Cliente_Codigo_AntBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_cliente_codigo_antBE = new S_Gem_Cliente_Codigo_AntBE();
                    s_gem_cliente_codigo_antBE.setID_CLIENTE(Funciones.isNullColumn(cursor,"ID_CLIENTE",0));
                    s_gem_cliente_codigo_antBE.setCODIGO_ANTIGUO(Funciones.isNullColumn(cursor,"CODIGO_ANTIGUO",""));
                    s_gem_cliente_codigo_antBE.setFLAG_VIGENCIA(Funciones.isNullColumn(cursor,"FLAG_VIGENCIA",0));
                    lst.add(s_gem_cliente_codigo_antBE);
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

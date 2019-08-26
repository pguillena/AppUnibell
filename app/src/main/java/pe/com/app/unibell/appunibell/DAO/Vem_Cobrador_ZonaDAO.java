package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;

import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Vem_Cobrador_ZonaBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Vem_Cobrador_ZonaDAO {
    public ArrayList<Vem_Cobrador_ZonaBE> lst = null;

    public void getAll() {
        Cursor cursor = null;
        Vem_Cobrador_ZonaBE vem_cobrador_zonaBE = null;
        try {
            String SQL="SELECT COBRADOR,COD_VENDE,DIA_VISITA,UBIGEO,ZONA," +
                    "C_CANAL,C_SUBCANAL "+
                    "FROM VEM_COBRADOR_ZONA";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Vem_Cobrador_ZonaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    vem_cobrador_zonaBE = new Vem_Cobrador_ZonaBE();
                    vem_cobrador_zonaBE.setCOBRADOR(Funciones.isNullColumn(cursor,"COBRADOR",""));
                    vem_cobrador_zonaBE.setCOD_VENDE(Funciones.isNullColumn(cursor,"COD_VENDE",""));
                    vem_cobrador_zonaBE.setDIA_VISITA(Funciones.isNullColumn(cursor,"DIA_VISITA",""));
                    vem_cobrador_zonaBE.setUBIGEO(Funciones.isNullColumn(cursor,"UBIGEO",""));
                    vem_cobrador_zonaBE.setZONA(Funciones.isNullColumn(cursor,"ZONA",""));
                    vem_cobrador_zonaBE.setC_CANAL(Funciones.isNullColumn(cursor,"C_CANAL",""));
                    vem_cobrador_zonaBE.setC_SUBCANAL(Funciones.isNullColumn(cursor,"C_SUBCANAL",""));
                    lst.add(vem_cobrador_zonaBE);
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

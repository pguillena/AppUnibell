package pe.com.app.unibell.appunibell.DAO;

import android.arch.core.util.Function;
import android.database.Cursor;

import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.S_gem_TipoCambioBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_gem_TipoCambioDAO {

    public ArrayList<S_gem_TipoCambioBE> lst = null;

    public Double getAll(String pFECHA) {
        Cursor cursor = null;
        S_gem_TipoCambioBE s_gem_tipoCambioBE = null;
        Double dTipoCambio=0.0;
        try {
            String SQL="SELECT FECHA,TIPO_CAMBIO,TIPO_MONEDA,IMPORT_CAM FROM S_GEM_TIPOCAMBIO " +
                    "WHERE substr(FECHA,7,4) || substr(FECHA,4,2) || substr(FECHA,1,2)  BETWEEN '"+ pFECHA +"' AND '" + pFECHA +"'";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_gem_TipoCambioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_tipoCambioBE = new S_gem_TipoCambioBE();
                    s_gem_tipoCambioBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    s_gem_tipoCambioBE.setTIPO_CAMBIO(Funciones.isNullColumn(cursor,"TIPO_CAMBIO",""));
                    s_gem_tipoCambioBE.setTIPO_MONEDA(Funciones.isNullColumn(cursor,"TIPO_MONEDA",0));
                    s_gem_tipoCambioBE.setIMPORT_CAM(Funciones.isNullColumn(cursor,"IMPORT_CAM",0.0));
                    dTipoCambio=Double.valueOf(Funciones.FormatDecimal(Funciones.isNullColumn(cursor,"IMPORT_CAM",0.0).toString()));
                    lst.add(s_gem_tipoCambioBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
           return dTipoCambio;
    }
    }

}

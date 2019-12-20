package pe.com.app.unibell.appunibell.DAO;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.M_ARTICULOSBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class M_ArticulosDAO {

    public ArrayList<M_ARTICULOSBE> lst = null;

    public void getAllByCodigoBarras(String pCodigoBarras, String pTipo) {
        Cursor cursor = null;
        M_ARTICULOSBE marticulosBE = null;
        try {
            String SQL="SELECT COD_ART, DESCRIPCION, C_ARTICULO, TP_ART  FROM M_ARTICULOS WHERE C_ARTICULO = '" + pCodigoBarras + "' AND (TP_ART = '" + pTipo +"' OR '"+ pTipo +"' = 'XXX')";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<M_ARTICULOSBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    marticulosBE = new M_ARTICULOSBE();
                    marticulosBE.setCOD_ART(Funciones.isNullColumn(cursor,"COD_ART",""));
                    marticulosBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    marticulosBE.setC_ARTICULO(Funciones.isNullColumn(cursor,"C_ARTICULO",""));
                    marticulosBE.setTP_ART(Funciones.isNullColumn(cursor,"TP_ART",""));

                    lst.add(marticulosBE);
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



package pe.com.app.unibell.appunibell.DAO;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.Util.Funciones;
import pe.com.app.unibell.appunibell.BE.S_Inv_InventarioBE;

public class S_Inv_InventarioDAO {

    public ArrayList<S_Inv_InventarioBE> lst = null;

    public void getAll(String pCONTEO,String pCODIGO_BARRA, String pUBICACION, String pMES, String pANIO) {
        Cursor cursor = null;
        S_Inv_InventarioBE inventarioBE = null;
        try {
            String SQL="SELECT * FROM S_INV_INVENTARIO WHERE (CODIGO_BARRA = '"+ pCODIGO_BARRA +"' OR '"+ pCODIGO_BARRA +"' = 'XXX' ) AND (UBICACION = '" + pUBICACION + "' OR '"+pUBICACION+"' = 'XXX' )"+
            " AND (CONTEO =  " + pCONTEO.toString() + " OR " + pCONTEO.toString()  + " = 0 ) " +
            " AND (MES =  " + pMES.toString() + " OR " + pMES.toString()  + " = 0 ) " +
            " AND (ANIO =  " + pANIO.toString() + " OR " + pANIO.toString()  + " = 0 ) " +
            " AND ESTADO = 40003 " +
            " ORDER BY UBICACION ASC, COD_ART ASC";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Inv_InventarioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    inventarioBE = new S_Inv_InventarioBE();
                    inventarioBE.setCONTEO(Funciones.isNullColumn(cursor,"CONTEO",0));
                    inventarioBE.setCODIGO_BARRA(Funciones.isNullColumn(cursor,"CODIGO_BARRA",""));
                    inventarioBE.setUBICACION(Funciones.isNullColumn(cursor,"UBICACION",""));
                    inventarioBE.setMES(Funciones.isNullColumn(cursor,"MES",0));
                    inventarioBE.setANIO(Funciones.isNullColumn(cursor,"ANIO",0));
                    inventarioBE.setCANTIDAD(Funciones.isNullColumn(cursor,"CANTIDAD",0));
                    inventarioBE.setCOD_ART(Funciones.isNullColumn(cursor,"COD_ART",""));
                    inventarioBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    inventarioBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));

                    lst.add(inventarioBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getGroupUbicacion() {
        Cursor cursor = null;
        S_Inv_InventarioBE inventarioBE = null;
        try {
            String SQL="SELECT UBICACION FROM S_INV_INVENTARIO" +
                    " WHERE ESTADO = 40003 " +
                    " GROUP BY UBICACION " +
                    " ORDER BY UBICACION ASC";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Inv_InventarioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    inventarioBE = new S_Inv_InventarioBE();
                    inventarioBE.setUBICACION(Funciones.isNullColumn(cursor,"UBICACION",""));
                    lst.add(inventarioBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String grabar(S_Inv_InventarioBE inventarioBE){

        getAll(inventarioBE.getCONTEO().toString(),inventarioBE.getCODIGO_BARRA(), inventarioBE.getUBICACION(), inventarioBE.getMES().toString(), inventarioBE.getANIO().toString());

        if (lst!=null && lst.size()>0)
        {
            inventarioBE.setCANTIDAD(lst.get(0).getCANTIDAD()+inventarioBE.getCANTIDAD());
        return  update(inventarioBE);
        }
        else
        {
         return insert(inventarioBE);
        }

    }

    public String insert(S_Inv_InventarioBE inventarioBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("CONTEO",inventarioBE.getCONTEO());
            cv.put("CODIGO_BARRA",inventarioBE.getCODIGO_BARRA());
            cv.put("UBICACION",inventarioBE.getUBICACION());
            cv.put("MES",inventarioBE.getMES());
            cv.put("ANIO",inventarioBE.getANIO());
            cv.put("CANTIDAD",inventarioBE.getCANTIDAD());
            cv.put("COD_ART",inventarioBE.getCOD_ART());
            cv.put("DESCRIPCION",inventarioBE.getDESCRIPCION());
            cv.put("ESTADO",inventarioBE.getESTADO());
            cv.put("FECHA_REGISTRO",inventarioBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",inventarioBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",inventarioBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",inventarioBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",inventarioBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",inventarioBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",inventarioBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",inventarioBE.getIP_MODIFICACION());

            DataBaseHelper.myDataBase.insert("S_INV_INVENTARIO",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(S_Inv_InventarioBE inventarioBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();

            cv.put("CANTIDAD",inventarioBE.getCANTIDAD());
            cv.put("COD_ART",inventarioBE.getCOD_ART());
            cv.put("DESCRIPCION",inventarioBE.getDESCRIPCION());
            cv.put("ESTADO",inventarioBE.getESTADO());
            cv.put("FECHA_MODIFICACION",inventarioBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_MODIFICACION",inventarioBE.getUSUARIO_MODIFICACION());
            cv.put("PC_MODIFICACION",inventarioBE.getPC_MODIFICACION());
            cv.put("IP_MODIFICACION",inventarioBE.getIP_MODIFICACION());

            DataBaseHelper.myDataBase.update("S_INV_INVENTARIO",cv,"CONTEO = ? AND CODIGO_BARRA = ?  AND UBICACION = ?  AND MES = ?  AND ANIO = ?",
                    new String[]{String.valueOf(inventarioBE.getCONTEO()),inventarioBE.getCODIGO_BARRA(), inventarioBE.getUBICACION(), String.valueOf(inventarioBE.getMES()), String.valueOf(inventarioBE.getANIO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(S_Inv_InventarioBE inventarioBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("S_INV_INVENTARIO","CONTEO = ? AND CODIGO_BARRA = ? AND UBICACION = ? AND MES = ? AND ANIO = ?", new String[]{inventarioBE.getCONTEO().toString(),inventarioBE.getCODIGO_BARRA(), inventarioBE.getUBICACION(), inventarioBE.getMES().toString(), inventarioBE.getANIO().toString()});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public void getMaxConteo(String pMES, String pANIO) {
        Cursor cursor = null;
        S_Inv_InventarioBE inventarioBE = null;
        try {
            String SQL="SELECT MAX(CONTEO) AS CONTEO FROM S_INV_INVENTARIO WHERE " +
                    "  (MES =  " + pMES.toString() + ") " +
                    " AND (ANIO =  " + pANIO.toString() + ") " +
                    " AND ESTADO = 40003 ";


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Inv_InventarioBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    inventarioBE = new S_Inv_InventarioBE();
                    inventarioBE.setCONTEO(Funciones.isNullColumn(cursor,"CONTEO",0));
                    lst.add(inventarioBE);
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

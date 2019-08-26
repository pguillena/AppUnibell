package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.SucursalesBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class SucursalesDAO {
    public ArrayList<SucursalesBE> lst = null;

    public void getAll(String pCOD_CLIENTE) {
        Cursor cursor = null;
        SucursalesBE sucursalesBE = null;
        try {
            String SQL="SELECT  COD_CLIENTE,NRO_SUCUR,DIRECCION,TELEFONO,RESPONSABLE,DISTRITO," +
                    "CIUDAD,ALM_CONS,VENDEDOR,NOMBRE_SUCURSAL,COD_UBC,ORD_REPARTO," +
                    "ZONA,ESTADO "+
                    "FROM SUCURSALES WHERE (" + pCOD_CLIENTE + "=-1 OR COD_CLIENTE=" + pCOD_CLIENTE + ") ORDER BY COD_CLIENTE";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<SucursalesBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    sucursalesBE = new SucursalesBE();
                    sucursalesBE.setCOD_CLIENTE(Funciones.isNullColumn(cursor,"COD_CLIENTE",""));
                    sucursalesBE.setNRO_SUCUR(Funciones.isNullColumn(cursor,"NRO_SUCUR",""));
                    sucursalesBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    sucursalesBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    sucursalesBE.setRESPONSABLE(Funciones.isNullColumn(cursor,"RESPONSABLE",""));
                    sucursalesBE.setDISTRITO(Funciones.isNullColumn(cursor,"DISTRITO",""));
                    sucursalesBE.setCIUDAD(Funciones.isNullColumn(cursor,"CIUDAD",""));
                    sucursalesBE.setALM_CONS(Funciones.isNullColumn(cursor,"ALM_CONS",""));
                    sucursalesBE.setVENDEDOR(Funciones.isNullColumn(cursor,"ID_PERSONA",""));
                    sucursalesBE.setNOMBRE_SUCURSAL(Funciones.isNullColumn(cursor,"NOMBRE_SUCURSAL",""));
                    sucursalesBE.setCOD_UBC(Funciones.isNullColumn(cursor,"COD_UBC",""));
                    sucursalesBE.setORD_REPARTO(Funciones.isNullColumn(cursor,"ORD_REPARTO",0));
                    sucursalesBE.setZONA(Funciones.isNullColumn(cursor,"ZONA",""));
                    sucursalesBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));                   
                    lst.add(sucursalesBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(SucursalesBE sucursalesBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("COD_CLIENTE",sucursalesBE.getCOD_CLIENTE());
             cv.put("NRO_SUCUR",sucursalesBE.getNRO_SUCUR());
             cv.put("DIRECCION",sucursalesBE.getDIRECCION());
             cv.put("TELEFONO",sucursalesBE.getTELEFONO());
             cv.put("RESPONSABLE",sucursalesBE.getRESPONSABLE());
             cv.put("DISTRITO",sucursalesBE.getDISTRITO());
             cv.put("CIUDAD",sucursalesBE.getCIUDAD());
             cv.put("ALM_CONS",sucursalesBE.getALM_CONS());
             cv.put("VENDEDOR",sucursalesBE.getVENDEDOR());
             cv.put("NOMBRE_SUCURSAL",sucursalesBE.getNOMBRE_SUCURSAL());
             cv.put("COD_UBC",sucursalesBE.getCOD_UBC());
             cv.put("ORD_REPARTO",sucursalesBE.getORD_REPARTO());
             cv.put("ZONA",sucursalesBE.getZONA());
             cv.put("ESTADO",sucursalesBE.getESTADO());
            DataBaseHelper.myDataBase.insert("SUCURSALES ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

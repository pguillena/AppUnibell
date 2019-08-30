package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.SincronizarBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class SincronizaDAO {
    public ArrayList<SincronizarBE> lstSincroniza = null;

    public void getAllSQLite() {
        Cursor cursor = null;
        SincronizarBE sincronizarBE = null;
        try {
            //String SQL="SELECT ID,PROCESO,FECHA,HORA,ESTADO,USUARIO,DESCRIPCION FROM SINCRONIZAR";
            String SQL="SELECT ID,PROCESO,HORA,USUARIO,DESCRIPCION,FECHA_ACTUAL,FECHAVAL,CASE WHEN FECHA_ACTUAL=FECHAVAL THEN FECHA ELSE '' END FECHA, \n" +
                    " CASE WHEN FECHA_ACTUAL=FECHAVAL THEN ESTADO ELSE 'KO' END ESTADO\n" +
                    " FROM(SELECT ID,PROCESO,FECHA,HORA,USUARIO,ESTADO,DESCRIPCION,  (strftime('%d','now') ||'-'|| strftime('%m','now') ||'-' ||strftime('%Y','now')) AS FECHA_ACTUAL,substr(FECHA,1,10) AS FECHAVAL  \n" +
                    " FROM SINCRONIZAR) TEMPS ";

            lstSincroniza = new ArrayList<SincronizarBE>();
            lstSincroniza.clear();

           /* sincronizarBE = new SincronizarBE();
            sincronizarBE.setID(0);
            sincronizarBE.setPROCESO("TODOS");
            sincronizarBE.setFECHA("");
            sincronizarBE.setHORA("");
            sincronizarBE.setESTADO("");
            sincronizarBE.setUSUARIO("");
            sincronizarBE.setDESCRIPCION("TODOS");
            lstSincroniza.add(sincronizarBE);*/

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            if (cursor.moveToFirst()) {
                do {
                    sincronizarBE = new SincronizarBE();
                    sincronizarBE.setID(Funciones.isNullColumn(cursor,"ID",0));
                    sincronizarBE.setPROCESO(Funciones.isNullColumn(cursor,"PROCESO",""));
                    sincronizarBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    sincronizarBE.setHORA(Funciones.isNullColumn(cursor,"HORA",""));
                    sincronizarBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",""));
                    sincronizarBE.setUSUARIO(Funciones.isNullColumn(cursor,"USUARIO",""));
                    sincronizarBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    lstSincroniza.add(sincronizarBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String update(SincronizarBE sincronizarBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("FECHA",sincronizarBE.getFECHA());
            cv.put("HORA",sincronizarBE.getHORA());
            cv.put("ESTADO",sincronizarBE.getESTADO());
            DataBaseHelper.myDataBase.update("SINCRONIZAR",cv,"PROCESO= ?",
                    new String[]{String.valueOf(sincronizarBE.getPROCESO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }
}

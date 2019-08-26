package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by RENAN on 18/08/2016.
 */
public class SistemaDAO {

    //generador de codigo interno de una determinada tabla
    public int MAX_REGISTRO(String TableName, String ColumName) {
        Cursor cursor = null;
        Integer MaxID=0;
        Integer iValorTrans=0;
        String SQL="SELECT ifnull(MAX(" + ColumName +"),0)AS MAXREG FROM UNIBELL_SECUENCIAS  WHERE TABLA='" + TableName + "'";
        try{
            cursor = DataBaseHelper.myDataBase.rawQuery(SQL,null);
            if(cursor.moveToFirst()) {
                do {
                    MaxID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("MAXREG")));
                    MaxID=MaxID+1;
                } while (cursor.moveToNext());
            }
            ContentValues cv_det = new ContentValues();
            cv_det.put(ColumName,MaxID);
            DataBaseHelper.myDataBase.update("UNIBELL_SECUENCIAS",cv_det,"TABLA = ?",new String[]{String.valueOf(TableName)});

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return MaxID;
    }


}

package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Gem_BancoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Gem_BancoDAO {
    public ArrayList<Gem_BancoBE> lst = null;

    public void getAll(String pID_BANCO) {
        Cursor cursor = null;
        Gem_BancoBE gem_bancoBE = null;
        try {
            String SQL="SELECT ID_BANCO,BANCO,ESTADO,ID_EMPRESA,FECHA_REGISTRO," +
                    "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO," +
                    "PC_MODIFICACION,IP_REGISTRO,IP_MODIFICACION,ABREVIADO,CODIGO_LOGIX," +
                    "VISIBLE "+
                    "FROM Gem_Banco WHERE (" + pID_BANCO + "=-1 OR ID_BANCO=" + pID_BANCO + ") ORDER BY ID_BANCO";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Gem_BancoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    gem_bancoBE = new Gem_BancoBE();
                    gem_bancoBE.setID_BANCO(Funciones.isNullColumn(cursor,"ID_BANCO",0));
                    gem_bancoBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO",""));
                    gem_bancoBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    gem_bancoBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    gem_bancoBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    gem_bancoBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    gem_bancoBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    gem_bancoBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    gem_bancoBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    gem_bancoBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    gem_bancoBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    gem_bancoBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    gem_bancoBE.setABREVIADO(Funciones.isNullColumn(cursor,"ABREVIADO",""));
                    gem_bancoBE.setCODIGO_LOGIX(Funciones.isNullColumn(cursor,"CODIGO_LOGIX",""));
                    gem_bancoBE.setVISIBLE(Funciones.isNullColumn(cursor,"VISIBLE",""));
                    lst.add(gem_bancoBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(Gem_BancoBE gem_bancoBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_BANCO",gem_bancoBE.getID_BANCO());
            cv.put("ESTADO",gem_bancoBE.getESTADO());
            cv.put("ID_EMPRESA",gem_bancoBE.getID_EMPRESA());
            cv.put("FECHA_REGISTRO",gem_bancoBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",gem_bancoBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",gem_bancoBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",gem_bancoBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",gem_bancoBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",gem_bancoBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",gem_bancoBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",gem_bancoBE.getIP_MODIFICACION());
            cv.put("ABREVIADO",gem_bancoBE.getABREVIADO());
            cv.put("CODIGO_LOGIX",gem_bancoBE.getCODIGO_LOGIX());
            cv.put("VISIBLE",gem_bancoBE.getVISIBLE());
            DataBaseHelper.myDataBase.insert("Gem_Banco",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(Gem_BancoBE gem_bancoBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_BANCO",gem_bancoBE.getID_BANCO());
            cv.put("ESTADO",gem_bancoBE.getESTADO());
            cv.put("ID_EMPRESA",gem_bancoBE.getID_EMPRESA());
            cv.put("FECHA_REGISTRO",gem_bancoBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",gem_bancoBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",gem_bancoBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",gem_bancoBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",gem_bancoBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",gem_bancoBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",gem_bancoBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",gem_bancoBE.getIP_MODIFICACION());
            cv.put("ABREVIADO",gem_bancoBE.getABREVIADO());
            cv.put("CODIGO_LOGIX",gem_bancoBE.getCODIGO_LOGIX());
            cv.put("VISIBLE",gem_bancoBE.getVISIBLE());

            DataBaseHelper.myDataBase.update("Gem_banco",cv,"ID_BANCO = ?",
                    new String[]{String.valueOf(gem_bancoBE.getID_BANCO())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(Gem_BancoBE gem_bancoBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("Gem_banco","ID_BANCO = ?", new String[]{String.valueOf(gem_bancoBE.getID_BANCO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

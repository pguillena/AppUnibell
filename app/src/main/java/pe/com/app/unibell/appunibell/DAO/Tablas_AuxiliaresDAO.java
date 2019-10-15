package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Tablas_AuxiliaresBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Tablas_AuxiliaresDAO {
    public ArrayList<Tablas_AuxiliaresBE> lst = null;

    public void getAll(String pTIPO, String pROL) {
        Cursor cursor = null;
        Tablas_AuxiliaresBE tablas_auxiliaresBE = null;
        String SQL="";
        try {
            if(pTIPO.toString().trim().equals("14")) {
                SQL="SELECT TIPO,CODIGO,DESCRIPCION,ABREVIADA,VALOR1," +
                        "VALOR2,VALOR3,INDICADOR1,INDICADOR2,INDICADOR3," +
                        " INDICADOR4,ID_EMPRESA "+
                        " FROM Tablas_Auxiliares WHERE VALOR2='1' and (" + pTIPO + "=-1 OR TIPO=" + pTIPO + ") ORDER BY TIPO";
            }
            else  if(pTIPO.toString().trim().equals("44"))
            {
                if(pROL.toString().trim().equals("130013")) //Si es cobrador
                {
                    SQL="SELECT TIPO,CODIGO,DESCRIPCION,ABREVIADA,VALOR1," +
                        "VALOR2,VALOR3,INDICADOR1,INDICADOR2,INDICADOR3," +
                        " INDICADOR4,ID_EMPRESA "+
                        " FROM Tablas_Auxiliares WHERE INDICADOR1 = 'C' AND TIPO =" + pTIPO+ " ORDER BY TIPO ";

                }
                else
                {
                    SQL="SELECT TIPO,CODIGO,DESCRIPCION,ABREVIADA,VALOR1," +
                            "VALOR2,VALOR3,INDICADOR1,INDICADOR2,INDICADOR3," +
                            " INDICADOR4,ID_EMPRESA "+
                            " FROM Tablas_Auxiliares WHERE INDICADOR2 = 'V' AND TIPO =" + pTIPO+ " ORDER BY TIPO ";

                }

            }
            else{
                SQL="SELECT TIPO,CODIGO,DESCRIPCION,ABREVIADA,VALOR1," +
                        "VALOR2,VALOR3,INDICADOR1,INDICADOR2,INDICADOR3," +
                        " INDICADOR4,ID_EMPRESA "+
                        " FROM Tablas_Auxiliares WHERE (" + pTIPO + "=-1 OR TIPO=" + pTIPO + ") ORDER BY TIPO";
            }




            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Tablas_AuxiliaresBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    tablas_auxiliaresBE = new Tablas_AuxiliaresBE();
                    tablas_auxiliaresBE.setTIPO(Funciones.isNullColumn(cursor,"TIPO",0));
                    tablas_auxiliaresBE.setCODIGO(Funciones.isNullColumn(cursor,"CODIGO","").toUpperCase());
                    tablas_auxiliaresBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION","").toUpperCase());
                    tablas_auxiliaresBE.setABREVIADA(Funciones.isNullColumn(cursor,"ABREVIADA",""));
                    tablas_auxiliaresBE.setVALOR1(Funciones.isNullColumn(cursor,"VALOR1",0));
                    tablas_auxiliaresBE.setVALOR2(Funciones.isNullColumn(cursor,"VALOR2",0));
                    tablas_auxiliaresBE.setVALOR3(Funciones.isNullColumn(cursor,"VALOR3",0));
                    tablas_auxiliaresBE.setINDICADOR1(Funciones.isNullColumn(cursor,"INDICADOR1",""));
                    tablas_auxiliaresBE.setINDICADOR2(Funciones.isNullColumn(cursor,"INDICADOR2",""));
                    tablas_auxiliaresBE.setINDICADOR3(Funciones.isNullColumn(cursor,"INDICADOR3",""));
                    tablas_auxiliaresBE.setINDICADOR4(Funciones.isNullColumn(cursor,"INDICADOR4",""));
                    tablas_auxiliaresBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));

                    lst.add(tablas_auxiliaresBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(Tablas_AuxiliaresBE tablas_auxiliaresBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("TIPO",tablas_auxiliaresBE.getTIPO());
            cv.put("CODIGO",tablas_auxiliaresBE.getCODIGO());
            cv.put("DESCRIPCION",tablas_auxiliaresBE.getDESCRIPCION());
            cv.put("ABREVIADA",tablas_auxiliaresBE.getABREVIADA());
            cv.put("VALOR1",tablas_auxiliaresBE.getVALOR1().toString());
            cv.put("VALOR2",tablas_auxiliaresBE.getVALOR2().toString());
            cv.put("VALOR3",tablas_auxiliaresBE.getVALOR3().toString());
            cv.put("INDICADOR1",tablas_auxiliaresBE.getINDICADOR1());
            cv.put("INDICADOR2",tablas_auxiliaresBE.getINDICADOR2());
            cv.put("INDICADOR3",tablas_auxiliaresBE.getINDICADOR3());
            cv.put("INDICADOR4",tablas_auxiliaresBE.getINDICADOR4());
            DataBaseHelper.myDataBase.insert("Tablas_Auxiliares",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(Tablas_AuxiliaresBE tablas_auxiliaresBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("TIPO",tablas_auxiliaresBE.getTIPO());
            cv.put("CODIGO",tablas_auxiliaresBE.getCODIGO());
            cv.put("DESCRIPCION",tablas_auxiliaresBE.getDESCRIPCION());
            cv.put("ABREVIADA",tablas_auxiliaresBE.getABREVIADA());
            cv.put("VALOR1",tablas_auxiliaresBE.getVALOR1().toString());
            cv.put("VALOR2",tablas_auxiliaresBE.getVALOR2().toString());
            cv.put("VALOR3",tablas_auxiliaresBE.getVALOR3().toString());
            cv.put("INDICADOR1",tablas_auxiliaresBE.getINDICADOR1());
            cv.put("INDICADOR2",tablas_auxiliaresBE.getINDICADOR2());
            cv.put("INDICADOR3",tablas_auxiliaresBE.getINDICADOR3());
            cv.put("INDICADOR4",tablas_auxiliaresBE.getINDICADOR4());

            DataBaseHelper.myDataBase.update("Tablas_Auxiliares",cv,"TIPO = ?",
                    new String[]{String.valueOf(tablas_auxiliaresBE.getTIPO())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(Tablas_AuxiliaresBE tablas_auxiliaresBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("Tablas_Auxiliares","TIPO = ?", new String[]{String.valueOf(tablas_auxiliaresBE.getTIPO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }
    
}

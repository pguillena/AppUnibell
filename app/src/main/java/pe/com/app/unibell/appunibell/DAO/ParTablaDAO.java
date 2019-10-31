package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.ParTablaBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class ParTablaDAO {

    public ArrayList<ParTablaBE> lst = null;

    public void getAll(String pOpcion,String pROL) {
        Cursor cursor = null;
        ParTablaBE parTablaBE = null;
        String SQL="";
        try {
            if(pOpcion.equals("0")) {
                SQL = "SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                        "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                        "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR " +
                        "FROM ParTabla where grupo='40000' and idtabla in ('40003','40005','40007','40002') " +
                        " and semuestra =1 and estado='40001' ORDER BY IDTABLA";
            }

            if(pOpcion.equals("1")) {
                //TESORERIA
                if(pROL.equals("130018")) {
                    SQL = "SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                            "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                            "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR " +
                            "FROM ParTabla where grupo='40000' and idtabla in ('40004','40005') " +
                            " and semuestra =1 and estado='40001' ORDER BY IDTABLA";
                }else{
                    SQL="SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                            "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                            "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR "+
                            "FROM ParTabla where grupo='40000' and idtabla in ('40003','40004','40005') " +
                            " and semuestra =1 and estado='40001' ORDER BY IDTABLA";
                }
            }

            if(pOpcion.equals("2")) {
                SQL = "SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                        "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                        "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR " +
                        "FROM ParTabla where grupo='140000' and idtabla in ('140001', '140002') " +
                        "  ORDER BY IDTABLA";
            }

            if(pOpcion.equals("3")) {
                SQL = "SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                        "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                        "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR " +
                        "FROM ParTabla where grupo='20000' and idtabla in ('20001', '20002') " +
                        "  ORDER BY IDTABLA";
            }


            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);

            lst = new ArrayList<ParTablaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    parTablaBE = new ParTablaBE();
                    parTablaBE.setIDTABLA(Funciones.isNullColumn(cursor,"IDTABLA",0));
                    parTablaBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    parTablaBE.setABREVIADO(Funciones.isNullColumn(cursor,"ABREVIADO",""));
                    parTablaBE.setVALOR1(Funciones.isNullColumn(cursor,"VALOR1",""));
                    parTablaBE.setVALOR2(Funciones.isNullColumn(cursor,"VALOR2",""));
                    parTablaBE.setVALOR3(Funciones.isNullColumn(cursor,"VALOR3",""));
                    parTablaBE.setINDICADOR1(Funciones.isNullColumn(cursor,"INDICADOR1",""));
                    parTablaBE.setINDICADOR2(Funciones.isNullColumn(cursor,"INDICADOR2",""));
                    parTablaBE.setINDICADOR3(Funciones.isNullColumn(cursor,"INDICADOR3",""));
                    parTablaBE.setVALORSUNAT(Funciones.isNullColumn(cursor,"VALORSUNAT",""));
                    parTablaBE.setGRUPO(Funciones.isNullColumn(cursor,"GRUPO",0));
                    parTablaBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    parTablaBE.setSEMUESTRA(Funciones.isNullColumn(cursor,"SEMUESTRA",0));
                    parTablaBE.setGRUPO_SUPERIOR(Funciones.isNullColumn(cursor,"GRUPO_SUPERIOR",0));
                    lst.add(parTablaBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(ParTablaBE parTablaBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("IDTABLA",parTablaBE.getIDTABLA());
             cv.put("DESCRIPCION",parTablaBE.getDESCRIPCION());
             cv.put("ABREVIADO",parTablaBE.getABREVIADO());
             cv.put("VALOR1",parTablaBE.getVALOR1());
             cv.put("VALOR2",parTablaBE.getVALOR2());
             cv.put("VALOR3",parTablaBE.getVALOR3());
             cv.put("INDICADOR1",parTablaBE.getINDICADOR1());
             cv.put("INDICADOR2",parTablaBE.getINDICADOR2());
             cv.put("INDICADOR3",parTablaBE.getINDICADOR3());
             cv.put("VALORSUNAT",parTablaBE.getVALORSUNAT());
             cv.put("GRUPO",parTablaBE.getGRUPO().toString());
             cv.put("ESTADO",parTablaBE.getESTADO().toString());
             cv.put("SEMUESTRA",parTablaBE.getESTADO().toString());
             cv.put("GRUPO_SUPERIOR",parTablaBE.getESTADO().toString());
            DataBaseHelper.myDataBase.insert("ParTabla",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(ParTablaBE parTablaBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("IDTABLA",parTablaBE.getIDTABLA());
            cv.put("DESCRIPCION",parTablaBE.getDESCRIPCION());
            cv.put("ABREVIADO",parTablaBE.getABREVIADO());
            cv.put("VALOR1",parTablaBE.getVALOR1());
            cv.put("VALOR2",parTablaBE.getVALOR2());
            cv.put("VALOR3",parTablaBE.getVALOR3());
            cv.put("INDICADOR1",parTablaBE.getINDICADOR1());
            cv.put("INDICADOR2",parTablaBE.getINDICADOR2());
            cv.put("INDICADOR3",parTablaBE.getINDICADOR3());
            cv.put("VALORSUNAT",parTablaBE.getVALORSUNAT());
            cv.put("GRUPO",parTablaBE.getGRUPO().toString());
            cv.put("ESTADO",parTablaBE.getESTADO().toString());
            cv.put("SEMUESTRA",parTablaBE.getESTADO().toString());
            cv.put("GRUPO_SUPERIOR",parTablaBE.getESTADO().toString());
            DataBaseHelper.myDataBase.update("ParTabla",cv,"IDTABLA = ?",
                    new String[]{String.valueOf(parTablaBE.getIDTABLA())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(ParTablaBE parTablaBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("ParTabla","IDTABLA = ?", new String[]{String.valueOf(parTablaBE.getIDTABLA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


    public void getByGroup(String pGroup){
        getByGroup(pGroup,"0");
    }

    public void getByGroup(String pGroup, String pIdTabla) {
        Cursor cursor = null;
        ParTablaBE parTablaBE = null;
        String SQL="";
        try {
                SQL = "SELECT IDTABLA,DESCRIPCION,ABREVIADO,VALOR1,VALOR2," +
                        "VALOR3,INDICADOR1,INDICADOR2,INDICADOR3,VALORSUNAT," +
                        "GRUPO,ESTADO,SEMUESTRA,GRUPO_SUPERIOR " +
                        "FROM ParTabla where grupo=" + pGroup +
                        " AND (IdTabla = "+ pIdTabla + " OR "+ pIdTabla + " =  0)" +
                        " and estado=40001 ORDER BY IDTABLA";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);

            lst = new ArrayList<ParTablaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    parTablaBE = new ParTablaBE();
                    parTablaBE.setIDTABLA(Funciones.isNullColumn(cursor,"IDTABLA",0));
                    parTablaBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    parTablaBE.setABREVIADO(Funciones.isNullColumn(cursor,"ABREVIADO",""));
                    parTablaBE.setVALOR1(Funciones.isNullColumn(cursor,"VALOR1",""));
                    parTablaBE.setVALOR2(Funciones.isNullColumn(cursor,"VALOR2",""));
                    parTablaBE.setVALOR3(Funciones.isNullColumn(cursor,"VALOR3",""));
                    parTablaBE.setINDICADOR1(Funciones.isNullColumn(cursor,"INDICADOR1",""));
                    parTablaBE.setINDICADOR2(Funciones.isNullColumn(cursor,"INDICADOR2",""));
                    parTablaBE.setINDICADOR3(Funciones.isNullColumn(cursor,"INDICADOR3",""));
                    parTablaBE.setVALORSUNAT(Funciones.isNullColumn(cursor,"VALORSUNAT",""));
                    parTablaBE.setGRUPO(Funciones.isNullColumn(cursor,"GRUPO",0));
                    parTablaBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    parTablaBE.setSEMUESTRA(Funciones.isNullColumn(cursor,"SEMUESTRA",0));
                    parTablaBE.setGRUPO_SUPERIOR(Funciones.isNullColumn(cursor,"GRUPO_SUPERIOR",0));
                    lst.add(parTablaBE);
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

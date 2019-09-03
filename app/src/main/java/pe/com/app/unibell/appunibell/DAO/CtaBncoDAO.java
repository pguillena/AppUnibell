package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.CtaBncoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class CtaBncoDAO {

    public ArrayList<CtaBncoBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        CtaBncoBE ctaBncoBE = null;
        try {
            String SQL="SELECT CODIGO,DESCRIPCION,CUENTA,MONEDA,BANCO," +
                    "TIPO_CUENTA,IND,SECTORISTA,TELEFONO,NUMCHEI," +
                    "NUMCHEF,VOUCHER,N_CTA_CORRIENTE,C_SUC_EMP,ID_LOCAL "+
                    "FROM CtaBnco ORDER BY CODIGO";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<CtaBncoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    ctaBncoBE = new CtaBncoBE();
                    ctaBncoBE.setCODIGO(Funciones.isNullColumn(cursor,"CODIGO",""));
                    ctaBncoBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"DESCRIPCION",""));
                    ctaBncoBE.setCUENTA(Funciones.isNullColumn(cursor,"CUENTA",""));
                    ctaBncoBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    ctaBncoBE.setBANCO(Funciones.isNullColumn(cursor,"BANCO",""));
                    ctaBncoBE.setTIPO_CUENTA(Funciones.isNullColumn(cursor,"TIPO_CUENTA",""));
                    ctaBncoBE.setIND(Funciones.isNullColumn(cursor,"IND",""));
                    ctaBncoBE.setSECTORISTA(Funciones.isNullColumn(cursor,"SECTORISTA",""));
                    ctaBncoBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    ctaBncoBE.setNUMCHEI(Funciones.isNullColumn(cursor,"NUMCHEI",""));
                    ctaBncoBE.setNUMCHEF(Funciones.isNullColumn(cursor,"NUMCHEF",""));
                    ctaBncoBE.setVOUCHER(Funciones.isNullColumn(cursor,"VOUCHER",0));
                    ctaBncoBE.setN_CTA_CORRIENTE(Funciones.isNullColumn(cursor,"N_CTA_CORRIENTE",""));
                    ctaBncoBE.setC_SUC_EMP(Funciones.isNullColumn(cursor,"C_SUC_EMP",""));
                    ctaBncoBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    lst.add(ctaBncoBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getAllBY(String iid_empresa,
                         String iid_local,
                         String iid_banco ,
                         String iabreviado,
                         String scodfpago) {
        Cursor cursor = null;
        CtaBncoBE ctaBncoBE = null;
        try {
            String iestado="40001";
            String Sql="select '' AS ID_BANCO, '' AS BANCO  WHERE 1=2";

            if(scodfpago.trim().equalsIgnoreCase("P")) {
                Sql="select a.codigo AS ID_BANCO,el.descripcion AS BANCO, a.MONEDA AS MONEDA  from ctabnco a"+
                        " inner join ctabnco_empresa_local el"+
                        " on (a.codigo = el.codigo and el.id_empresa =" +  iid_empresa + " and el.id_local = " + iid_local +")" +
                        " where el.id_empresa =" + iid_empresa + " and el.id_local =" + iid_local + " " +
                        " and a.codigo <> '32' Order by el.descripcion ";
            }
            if(scodfpago.trim().equalsIgnoreCase("V") ||
                    scodfpago.trim().equalsIgnoreCase("D")||
                    scodfpago.trim().equalsIgnoreCase("M")||
                    scodfpago.trim().equalsIgnoreCase("S")||
                    scodfpago.trim().equalsIgnoreCase("I")||
                    scodfpago.trim().equalsIgnoreCase("H")) {

                Sql="select a.codigo AS ID_BANCO,el.descripcion AS BANCO, a.MONEDA AS MONEDA " +
                        " from ctabnco a inner join ctabnco_empresa_local el" +
                        " on (a.codigo = el.codigo and el.id_empresa =" + iid_empresa + " and el.id_local ="+ iid_local + ")" +
                        " where el.id_empresa ="+ iid_empresa  +
                        "   and el.id_local =" + iid_local +
                        "   and a.codigo = '32' Order by el.descripcion ";
            }

            if(scodfpago.trim().equalsIgnoreCase("C")) {
                Sql= "select a.id_banco,a.banco, '' AS MONEDA from s_gem_banco a" +
                        " where (a.id_empresa =" + iid_empresa + " or " +  iid_empresa +"=0)" +
                        "   and (a.estado =" +  iestado   +" or " + iestado + "= 0)" +
                        "   and (a.id_banco =" + iid_banco + " or " + iid_banco + "= 0)" +
                        "   and a.visible = 1 order by a.banco";
            }

            cursor= DataBaseHelper.myDataBase.rawQuery(Sql, null);

            lst = new ArrayList<CtaBncoBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    ctaBncoBE = new CtaBncoBE();
                    ctaBncoBE.setCODIGO(Funciones.isNullColumn(cursor,"ID_BANCO",""));
                    ctaBncoBE.setDESCRIPCION(Funciones.isNullColumn(cursor,"BANCO",""));
                    ctaBncoBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    lst.add(ctaBncoBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(CtaBncoBE ctaBncoBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("CODIGO",ctaBncoBE.getCODIGO());
            cv.put("DESCRIPCION",ctaBncoBE.getDESCRIPCION());
            cv.put("CUENTA",ctaBncoBE.getCUENTA());
            cv.put("MONEDA",ctaBncoBE.getMONEDA());
            cv.put("BANCO",ctaBncoBE.getBANCO());
            cv.put("TIPO_CUENTA",ctaBncoBE.getTIPO_CUENTA());
            cv.put("IND",ctaBncoBE.getIND());
            cv.put("SECTORISTA",ctaBncoBE.getSECTORISTA());
            cv.put("TELEFONO",ctaBncoBE.getTELEFONO());
            cv.put("NUMCHEI",ctaBncoBE.getNUMCHEI());
            cv.put("NUMCHEF",ctaBncoBE.getNUMCHEF());
            cv.put("VOUCHER",ctaBncoBE.getVOUCHER().toString());
            cv.put("N_CTA_CORRIENTE",ctaBncoBE.getN_CTA_CORRIENTE());
            cv.put("C_SUC_EMP",ctaBncoBE.getC_SUC_EMP());
            cv.put("ID_LOCAL",ctaBncoBE.getID_LOCAL().toString());

            DataBaseHelper.myDataBase.insert("CtaBnco",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(CtaBncoBE ctaBncoBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("CODIGO",ctaBncoBE.getCODIGO());
            cv.put("DESCRIPCION",ctaBncoBE.getDESCRIPCION());
            cv.put("CUENTA",ctaBncoBE.getCUENTA());
            cv.put("MONEDA",ctaBncoBE.getMONEDA());
            cv.put("BANCO",ctaBncoBE.getBANCO());
            cv.put("TIPO_CUENTA",ctaBncoBE.getTIPO_CUENTA());
            cv.put("IND",ctaBncoBE.getIND());
            cv.put("SECTORISTA",ctaBncoBE.getSECTORISTA());
            cv.put("TELEFONO",ctaBncoBE.getTELEFONO());
            cv.put("NUMCHEI",ctaBncoBE.getNUMCHEI());
            cv.put("NUMCHEF",ctaBncoBE.getNUMCHEF());
            cv.put("VOUCHER",ctaBncoBE.getVOUCHER().toString());
            cv.put("N_CTA_CORRIENTE",ctaBncoBE.getN_CTA_CORRIENTE());
            cv.put("C_SUC_EMP",ctaBncoBE.getC_SUC_EMP());
            cv.put("ID_LOCAL",ctaBncoBE.getID_LOCAL().toString());

            DataBaseHelper.myDataBase.update("CtaBnco",cv,"CODIGO = ?",
                    new String[]{String.valueOf(ctaBncoBE.getCODIGO())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(CtaBncoBE ctaBncoBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("CtaBnco","CODIGO = ?", new String[]{String.valueOf(ctaBncoBE.getCODIGO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

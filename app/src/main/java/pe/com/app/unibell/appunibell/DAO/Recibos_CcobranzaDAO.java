package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Recibos_CcobranzaBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Recibos_CcobranzaDAO {
    public ArrayList<Recibos_CcobranzaBE> lst = null;

    public void getAll(String pN_SERIE) {
        Cursor cursor = null;
        Recibos_CcobranzaBE recibos_ccobranzaBE = null;
        try {
            String SQL="SELECT N_SERIE,N_NUMINI,N_NUMFIN,C_TIPO_REC,C_RECEPTOR," +
                    "F_RECEPCION,F_DEVOLUCION,VIGENCIA,C_USUARIO,C_PERFIL," +
                    "C_CPU,FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD," +
                    "C_CPU_MOD,OBSERVACION,C_ESTADO  "+
                    "FROM CCM_RECIBOS_COBRANZA WHERE (" + pN_SERIE + "=-1 OR N_SERIE=" + pN_SERIE + ") ORDER BY N_SERIE";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Recibos_CcobranzaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    recibos_ccobranzaBE = new Recibos_CcobranzaBE();
                    recibos_ccobranzaBE.setN_SERIE(Funciones.isNullColumn(cursor,"N_SERIE",""));
                    recibos_ccobranzaBE.setN_NUMINI(Funciones.isNullColumn(cursor,"N_NUMINI",""));
                    recibos_ccobranzaBE.setN_NUMFIN(Funciones.isNullColumn(cursor,"N_NUMFIN",""));
                    recibos_ccobranzaBE.setC_TIPO_REC(Funciones.isNullColumn(cursor,"C_TIPO_REC",""));
                    recibos_ccobranzaBE.setC_RECEPTOR(Funciones.isNullColumn(cursor,"C_RECEPTOR",""));
                    recibos_ccobranzaBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION",""));
                    recibos_ccobranzaBE.setF_DEVOLUCION(Funciones.isNullColumn(cursor,"F_DEVOLUCION",""));
                    recibos_ccobranzaBE.setVIGENCIA(Funciones.isNullColumn(cursor,"VIGENCIA",""));
                    recibos_ccobranzaBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    recibos_ccobranzaBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    recibos_ccobranzaBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    recibos_ccobranzaBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    recibos_ccobranzaBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    recibos_ccobranzaBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    recibos_ccobranzaBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
                    recibos_ccobranzaBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    recibos_ccobranzaBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    recibos_ccobranzaBE.setC_ESTADO(Funciones.isNullColumn(cursor,"C_ESTADO",""));
                    lst.add(recibos_ccobranzaBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getByRangoRecibos(String iID_VENDEDOR,String pN_SERIE,String pN_NUMERO) {
        Cursor cursor = null;
        Recibos_CcobranzaBE recibos_ccobranzaBE = null;
        try {
            String SQL="SELECT N_SERIE,N_NUMINI,N_NUMFIN,C_TIPO_REC,C_RECEPTOR," +
                    "F_RECEPCION,F_DEVOLUCION,VIGENCIA,C_USUARIO,C_PERFIL," +
                    "C_CPU,FEC_REG,C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD," +
                    "C_CPU_MOD,OBSERVACION,C_ESTADO  "+
                    "FROM CCM_RECIBOS_COBRANZA WHERE VIGENCIA = 'A' AND N_SERIE=" + pN_SERIE + " AND C_RECEPTOR =" + iID_VENDEDOR ;

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Recibos_CcobranzaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    recibos_ccobranzaBE = new Recibos_CcobranzaBE();
                    recibos_ccobranzaBE.setN_SERIE(Funciones.isNullColumn(cursor,"N_SERIE",""));
                    recibos_ccobranzaBE.setN_NUMINI(Funciones.isNullColumn(cursor,"N_NUMINI",""));
                    recibos_ccobranzaBE.setN_NUMFIN(Funciones.isNullColumn(cursor,"N_NUMFIN",""));
                    recibos_ccobranzaBE.setC_TIPO_REC(Funciones.isNullColumn(cursor,"C_TIPO_REC",""));
                    recibos_ccobranzaBE.setC_RECEPTOR(Funciones.isNullColumn(cursor,"C_RECEPTOR",""));
                    recibos_ccobranzaBE.setF_RECEPCION(Funciones.isNullColumn(cursor,"F_RECEPCION",""));
                    recibos_ccobranzaBE.setF_DEVOLUCION(Funciones.isNullColumn(cursor,"F_DEVOLUCION",""));
                    recibos_ccobranzaBE.setVIGENCIA(Funciones.isNullColumn(cursor,"VIGENCIA",""));
                    recibos_ccobranzaBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    recibos_ccobranzaBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    recibos_ccobranzaBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    recibos_ccobranzaBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    recibos_ccobranzaBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    recibos_ccobranzaBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    recibos_ccobranzaBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
                    recibos_ccobranzaBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    recibos_ccobranzaBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    recibos_ccobranzaBE.setC_ESTADO(Funciones.isNullColumn(cursor,"C_ESTADO",""));
                    lst.add(recibos_ccobranzaBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public Integer getValidar(String iID_VENDEDOR,String pN_SERIE,String pN_NUMERO) {
        Cursor cursor = null;
        Integer Ivalor=0;
        try {
            String SQL="SELECT COUNT(*) AS iVALIDA_NRO_RECIBO\n" +
                    " FROM(\n" +
                    " SELECT C.N_SERIE, C.N_NUMINI, C.N_NUMFIN\n" +
                    " FROM CCM_RECIBOS_COBRANZA C WHERE\n" +
                    " C_RECEPTOR =" + iID_VENDEDOR + " AND C.F_DEVOLUCION='') V " +
                    " WHERE V.N_SERIE=" + pN_SERIE + " AND " + pN_NUMERO + " BETWEEN V.N_NUMINI AND V.N_NUMFIN ";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Recibos_CcobranzaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    Ivalor=Funciones.isNullColumn(cursor,"iVALIDA_NRO_RECIBO",0);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return Ivalor;
    }



    public String insert(Recibos_CcobranzaBE recibos_ccobranzaBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("N_SERIE",recibos_ccobranzaBE.getN_SERIE());
            cv.put("N_NUMINI",recibos_ccobranzaBE.getN_NUMINI());
            cv.put("N_NUMFIN",recibos_ccobranzaBE.getN_NUMFIN());
            cv.put("C_TIPO_REC",recibos_ccobranzaBE.getC_TIPO_REC());
            cv.put("C_RECEPTOR",recibos_ccobranzaBE.getC_RECEPTOR());
            cv.put("F_RECEPCION",recibos_ccobranzaBE.getF_RECEPCION());
            cv.put("F_DEVOLUCION",recibos_ccobranzaBE.getF_DEVOLUCION());
            cv.put("VIGENCIA",recibos_ccobranzaBE.getVIGENCIA());
            cv.put("C_USUARIO",recibos_ccobranzaBE.getC_USUARIO());
            cv.put("C_PERFIL",recibos_ccobranzaBE.getC_PERFIL());
            cv.put("C_CPU",recibos_ccobranzaBE.getC_CPU());
            cv.put("FEC_REG",recibos_ccobranzaBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",recibos_ccobranzaBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",recibos_ccobranzaBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",recibos_ccobranzaBE.getFEC_MOD());
            cv.put("C_CPU_MOD",recibos_ccobranzaBE.getC_CPU_MOD());
            cv.put("OBSERVACION",recibos_ccobranzaBE.getOBSERVACION());
            cv.put("C_ESTADO",recibos_ccobranzaBE.getC_ESTADO());
            DataBaseHelper.myDataBase.insert("CCM_RECIBOS_COBRANZA",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(Recibos_CcobranzaBE recibos_ccobranzaBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("N_SERIE",recibos_ccobranzaBE.getN_SERIE());
            cv.put("N_NUMINI",recibos_ccobranzaBE.getN_NUMINI());
            cv.put("N_NUMFIN",recibos_ccobranzaBE.getN_NUMFIN());
            cv.put("C_TIPO_REC",recibos_ccobranzaBE.getC_TIPO_REC());
            cv.put("C_RECEPTOR",recibos_ccobranzaBE.getC_RECEPTOR());
            cv.put("F_RECEPCION",recibos_ccobranzaBE.getF_RECEPCION());
            cv.put("F_DEVOLUCION",recibos_ccobranzaBE.getF_DEVOLUCION());
            cv.put("VIGENCIA",recibos_ccobranzaBE.getVIGENCIA());
            cv.put("C_USUARIO",recibos_ccobranzaBE.getC_USUARIO());
            cv.put("C_PERFIL",recibos_ccobranzaBE.getC_PERFIL());
            cv.put("C_CPU",recibos_ccobranzaBE.getC_CPU());
            cv.put("FEC_REG",recibos_ccobranzaBE.getFEC_REG());
            cv.put("C_USUARIO_MOD",recibos_ccobranzaBE.getC_USUARIO_MOD());
            cv.put("C_PERFIL_MOD",recibos_ccobranzaBE.getC_PERFIL_MOD());
            cv.put("FEC_MOD",recibos_ccobranzaBE.getFEC_MOD());
            cv.put("C_CPU_MOD",recibos_ccobranzaBE.getC_CPU_MOD());
            cv.put("OBSERVACION",recibos_ccobranzaBE.getOBSERVACION());
            cv.put("C_ESTADO",recibos_ccobranzaBE.getC_ESTADO());
            DataBaseHelper.myDataBase.update("CCM_RECIBOS_COBRANZA",cv,"N_SERIE = ?",
                    new String[]{String.valueOf(recibos_ccobranzaBE.getN_SERIE())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(Recibos_CcobranzaBE recibos_ccobranzaBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("CCM_RECIBOS_COBRANZA","N_SERIE = ?", new String[]{String.valueOf(recibos_ccobranzaBE.getN_SERIE())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


    
}

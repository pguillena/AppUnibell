package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.CabfcobBE;
import pe.com.app.unibell.appunibell.BE.MvendedorBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class MvendedorDAO {
    public ArrayList<MvendedorBE> lst = null;

    public void getAll(String pC_VENDEDOR) {
        Cursor cursor = null;
        MvendedorBE mvendedorBE = null;
        try {
            String SQL="SELECT C_VENDEDOR,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,C_CANAL,C_SUBCANAL,"+
                    "I_VENDEDOR,C_EMPRESA,F_INGRESO,F_CESE,F_REGISTRO,C_USUARIO_REGISTRO,"+
                    "F_ACTUALIZACION,C_USUARIO_ACTUALIZACION,C_GERENCIA,I_TIPO_VENDEDOR,C_SUC_EMP,N_CELULAR,"+
                    "I_SIGA,F_ENTREGA_TABLET,OBSERVACION,I_RUTA,C_CODIGO,ID_LOCAL,"+
                    "C_VENDEDOR,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRES,C_CANAL,C_SUBCANAL,"+
                    "I_VENDEDOR,C_EMPRESA,F_INGRESO,F_CESE,F_REGISTRO,C_USUARIO_REGISTRO,"+
                    "F_ACTUALIZACION,C_USUARIO_ACTUALIZACION,C_GERENCIA,I_TIPO_VENDEDOR,C_SUC_EMP,N_CELULAR,"+
                    "I_SIGA,F_ENTREGA_TABLET,OBSERVACION,I_RUTA,C_CODIGO,ID_LOCAL"+
                    "FROM MVENDEDOR  WHERE (" + pC_VENDEDOR + "=-1 OR C_VENDEDOR=" + pC_VENDEDOR + ") ORDER BY C_VENDEDOR";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<MvendedorBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    mvendedorBE = new MvendedorBE();
                    mvendedorBE.setC_VENDEDOR(Funciones.isNullColumn(cursor,"C_VENDEDOR",""));
                    mvendedorBE.setAPELLIDO_PATERNO(Funciones.isNullColumn(cursor,"APELLIDO_PATERNO",""));
                    mvendedorBE.setAPELLIDO_MATERNO(Funciones.isNullColumn(cursor,"APELLIDO_MATERNO",""));
                    mvendedorBE.setNOMBRES(Funciones.isNullColumn(cursor,"NOMBRES",""));
                    mvendedorBE.setC_CANAL(Funciones.isNullColumn(cursor,"C_CANAL",""));
                    mvendedorBE.setC_SUBCANAL(Funciones.isNullColumn(cursor,"C_SUBCANAL",""));
                    mvendedorBE.setI_VENDEDOR(Funciones.isNullColumn(cursor,"I_VENDEDOR",""));
                    mvendedorBE.setC_EMPRESA(Funciones.isNullColumn(cursor,"C_EMPRESA",""));
                    mvendedorBE.setF_INGRESO(Funciones.isNullColumn(cursor,"F_INGRESO",""));
                    mvendedorBE.setF_CESE(Funciones.isNullColumn(cursor,"F_CESE",""));
                    mvendedorBE.setF_REGISTRO(Funciones.isNullColumn(cursor,"F_REGISTRO",""));
                    mvendedorBE.setC_USUARIO_REGISTRO(Funciones.isNullColumn(cursor,"C_USUARIO_REGISTRO",""));
                    mvendedorBE.setF_ACTUALIZACION(Funciones.isNullColumn(cursor,"F_ACTUALIZACION",""));
                    mvendedorBE.setC_USUARIO_ACTUALIZACION(Funciones.isNullColumn(cursor,"C_GERENCIA",""));
                    mvendedorBE.setC_GERENCIA(Funciones.isNullColumn(cursor,"C_GERENCIA",""));
                    mvendedorBE.setI_TIPO_VENDEDOR(Funciones.isNullColumn(cursor,"I_TIPO_VENDEDOR",""));
                    mvendedorBE.setC_SUC_EMP(Funciones.isNullColumn(cursor,"C_SUC_EMP",""));
                    mvendedorBE.setN_CELULAR(Funciones.isNullColumn(cursor,"N_CELULAR",""));
                    mvendedorBE.setI_SIGA(Funciones.isNullColumn(cursor,"I_SIGA",0));
                    mvendedorBE.setF_ENTREGA_TABLET(Funciones.isNullColumn(cursor,"F_ENTREGA_TABLET",""));
                    mvendedorBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    mvendedorBE.setI_RUTA(Funciones.isNullColumn(cursor,"I_RUTA",""));
                    mvendedorBE.setC_CODIGO(Funciones.isNullColumn(cursor,"C_CODIGO",""));
                    mvendedorBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    lst.add(mvendedorBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(MvendedorBE mvendedorBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("C_VENDEDOR",mvendedorBE. getC_VENDEDOR());
            cv.put("APELLIDO_PATERNO",mvendedorBE. getAPELLIDO_PATERNO());
            cv.put("APELLIDO_MATERNO",mvendedorBE. getAPELLIDO_MATERNO());
            cv.put("NOMBRES",mvendedorBE. getNOMBRES());
            cv.put("C_CANAL",mvendedorBE. getC_CANAL());
            cv.put("C_SUBCANAL",mvendedorBE. getC_SUBCANAL());
            cv.put("I_VENDEDOR",mvendedorBE. getI_VENDEDOR());
            cv.put("C_EMPRESA",mvendedorBE. getC_EMPRESA());
            cv.put("F_INGRESO",mvendedorBE. getF_INGRESO());
            cv.put("F_CESE",mvendedorBE. getF_CESE());
            cv.put("F_REGISTRO",mvendedorBE. getF_REGISTRO());
            cv.put("C_USUARIO_REGISTRO",mvendedorBE. getC_USUARIO_REGISTRO());
            cv.put("F_ACTUALIZACION",mvendedorBE. getF_ACTUALIZACION());
            cv.put("C_USUARIO_ACTUALIZACION",mvendedorBE. getC_USUARIO_ACTUALIZACION());
            cv.put("C_GERENCIA",mvendedorBE. getC_GERENCIA());
            cv.put("I_TIPO_VENDEDOR",mvendedorBE. getI_TIPO_VENDEDOR());
            cv.put("C_SUC_EMP",mvendedorBE. getC_SUC_EMP());
            cv.put("N_CELULAR",mvendedorBE. getN_CELULAR());
            cv.put("I_SIGA",mvendedorBE. getI_SIGA());
            cv.put("F_ENTREGA_TABLET",mvendedorBE. getF_ENTREGA_TABLET());
            cv.put("OBSERVACION",mvendedorBE. getOBSERVACION());
            cv.put("I_RUTA",mvendedorBE. getI_RUTA());
            cv.put("C_CODIGO",mvendedorBE. getC_CODIGO());
            cv.put("ID_LOCAL",mvendedorBE. getID_LOCAL());
            DataBaseHelper.myDataBase.insert("Mvendedor",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

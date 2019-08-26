package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sea_Usuario_AccionBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sea_Usuario_AccionDAO {
    public ArrayList<S_Sea_Usuario_AccionBE> lst = null;

    public void getAll(String pID_PERSONA) {
        Cursor cursor = null;
        S_Sea_Usuario_AccionBE s_sea_usuario_accionBE = null;
        try {
            String SQL="SELECT ID_PERSONA,ID_ACCION,ID_EMPRESA,ID_LOCAL,ESTADO,FECHA_REGISTRO," +
                    "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO," +
                    "IP_MODIFICACION  "+
                    "FROM S_SEA_USUARIO_ACCION WHERE (" + pID_PERSONA + "=-1 OR ID_PERSONA=" + pID_PERSONA + ") ORDER BY ID_PERSONA";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sea_Usuario_AccionBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                     s_sea_usuario_accionBE = new S_Sea_Usuario_AccionBE();
                     s_sea_usuario_accionBE.setID_PERSONA(Funciones.isNullColumn(cursor,"ID_PERSONA",0));
                     s_sea_usuario_accionBE.setID_ACCION(Funciones.isNullColumn(cursor,"ID_ACCION",0));
                     s_sea_usuario_accionBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                     s_sea_usuario_accionBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                     s_sea_usuario_accionBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                     s_sea_usuario_accionBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                     s_sea_usuario_accionBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                     s_sea_usuario_accionBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                     s_sea_usuario_accionBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                     s_sea_usuario_accionBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                     s_sea_usuario_accionBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                     s_sea_usuario_accionBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                     s_sea_usuario_accionBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));                   
                    lst.add(s_sea_usuario_accionBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sea_Usuario_AccionBE s_sea_usuario_accionBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("ID_PERSONA",s_sea_usuario_accionBE.getID_PERSONA());
             cv.put("ID_ACCION",s_sea_usuario_accionBE.getID_ACCION());
             cv.put("ID_EMPRESA",s_sea_usuario_accionBE.getID_EMPRESA());
             cv.put("ID_LOCAL",s_sea_usuario_accionBE.getID_LOCAL());
             cv.put("ESTADO",s_sea_usuario_accionBE.getESTADO());
             cv.put("FECHA_REGISTRO",s_sea_usuario_accionBE.getFECHA_REGISTRO());
             cv.put("FECHA_MODIFICACION",s_sea_usuario_accionBE.getFECHA_MODIFICACION());
             cv.put("USUARIO_REGISTRO",s_sea_usuario_accionBE.getUSUARIO_REGISTRO());
             cv.put("USUARIO_MODIFICACION",s_sea_usuario_accionBE.getUSUARIO_MODIFICACION());
             cv.put("PC_REGISTRO",s_sea_usuario_accionBE.getPC_REGISTRO());
             cv.put("PC_MODIFICACION",s_sea_usuario_accionBE.getPC_MODIFICACION());
             cv.put("IP_REGISTRO",s_sea_usuario_accionBE.getIP_REGISTRO());
             cv.put("IP_MODIFICACION",s_sea_usuario_accionBE.getIP_MODIFICACION());
            DataBaseHelper.myDataBase.insert("S_SEA_USUARIO_ACCION ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

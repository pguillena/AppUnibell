package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_MovBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Documentos_Cobra_MovDAO {
    public ArrayList<Documentos_Cobra_MovBE> lst = null;

    public void getAll(String pID_DOCUMENTO_MOVIMIENTO) {
        Cursor cursor = null;
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        try {
            String SQL="SELECT ID_DOCUMENTO_MOVIMIENTO,SERIE_PLANILLA,N_PLANILLA,ID_USUARIO_REGISTRO,D_ROL_USUARIO_REGISTRO,"+
                   "FECHA_RECEPCION,ID_USUARIO_DERIVAR,ID_ROL_USUARIO_DERIVAR,FECHA_DERIVAR,FECHA_MOVIMIENTO,"+
                   "FECHA_ACCION,ESTADO_MOVIMIENTO,ID_EMPRESA,ID_LOCAL "+
            "FROM S_CCM_DOCUMENTOS_COBRA_MOV WHERE (" + pID_DOCUMENTO_MOVIMIENTO + "=-1 OR ID_DOCUMENTO_MOVIMIENTO=" + pID_DOCUMENTO_MOVIMIENTO + ") ORDER BY ID_DOCUMENTO_MOVIMIENTO";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                    documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(Funciones.isNullColumn(cursor,"ID_DOCUMENTO_MOVIMIENTO",0));
                    documentos_cobra_movBE.setSERIE_PLANILLA(Funciones.isNullColumn(cursor,"SERIE_PLANILLA",""));
                    documentos_cobra_movBE.setN_PLANILLA(Funciones.isNullColumn(cursor,"N_PLANILLA",""));
                    documentos_cobra_movBE.setID_USUARIO_REGISTRO(Funciones.isNullColumn(cursor,"ID_USUARIO_REGISTRO",0));
                    documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO (Funciones.isNullColumn(cursor,"ID_ROL_USUARIO_REGISTRO",0));
                    documentos_cobra_movBE.setFECHA_RECEPCION (Funciones.isNullColumn(cursor,"FECHA_RECEPCION",""));
                    documentos_cobra_movBE.setID_USUARIO_DERIVAR (Funciones.isNullColumn(cursor,"ID_USUARIO_DERIVAR",0));
                    documentos_cobra_movBE.setID_ROL_USUARIO_DERIVAR (Funciones.isNullColumn(cursor,"ID_ROL_USUARIO_DERIVAR",0));
                    documentos_cobra_movBE.setFECHA_DERIVAR (Funciones.isNullColumn(cursor,"FECHA_DERIVAR",""));
                    documentos_cobra_movBE.setFECHA_MOVIMIENTO (Funciones.isNullColumn(cursor,"FECHA_MOVIMIENTO",""));
                    documentos_cobra_movBE.setFECHA_ACCION (Funciones.isNullColumn(cursor,"FECHA_ACCION",""));
                    documentos_cobra_movBE.setESTADO_MOVIMIENTO (Funciones.isNullColumn(cursor,"ESTADO_MOVIMIENTO",0));
                    documentos_cobra_movBE.setID_EMPRESA (Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_movBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    lst.add(documentos_cobra_movBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getAllPrueba() {
        Cursor cursor = null;
        Documentos_Cobra_MovBE documentos_cobra_movBE = null;
        try {
            lst = new ArrayList<Documentos_Cobra_MovBE>();
            lst.clear();
            for (int i=0;i<20;i++){
                documentos_cobra_movBE = new Documentos_Cobra_MovBE();
                documentos_cobra_movBE.setID_DOCUMENTO_MOVIMIENTO(i);
                documentos_cobra_movBE.setSERIE_PLANILLA("00001");
                documentos_cobra_movBE.setN_PLANILLA("152266");
                documentos_cobra_movBE.setID_USUARIO_REGISTRO(1);
                documentos_cobra_movBE.setID_ROL_USUARIO_REGISTRO (0);
                documentos_cobra_movBE.setFECHA_RECEPCION ("07/05/2019");
                documentos_cobra_movBE.setID_USUARIO_DERIVAR (1);
                documentos_cobra_movBE.setID_ROL_USUARIO_DERIVAR (1);
                documentos_cobra_movBE.setFECHA_DERIVAR ("07/05/2019");
                documentos_cobra_movBE.setFECHA_MOVIMIENTO ("07/05/2019");
                documentos_cobra_movBE.setFECHA_ACCION ("07/05/2019");
                documentos_cobra_movBE.setESTADO_MOVIMIENTO (1);
                documentos_cobra_movBE.setID_EMPRESA (1);
                documentos_cobra_movBE.setID_LOCAL(1);
                lst.add(documentos_cobra_movBE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }



    public String insert(Documentos_Cobra_MovBE documentosCobraMovBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_DOCUMENTO_MOVIMIENTO",documentosCobraMovBE.getID_DOCUMENTO_MOVIMIENTO());
            cv.put("SERIE_PLANILLA",documentosCobraMovBE.getSERIE_PLANILLA());
            cv.put("N_PLANILLA",documentosCobraMovBE.getN_PLANILLA());
            cv.put("ID_USUARIO_REGISTRO",documentosCobraMovBE.getID_USUARIO_REGISTRO());
            cv.put("ID_ROL_USUARIO_REGISTRO ",documentosCobraMovBE.getID_ROL_USUARIO_REGISTRO());
            cv.put("FECHA_RECEPCION ",documentosCobraMovBE.getFECHA_RECEPCION());
            cv.put("ID_USUARIO_DERIVAR ",documentosCobraMovBE.getID_USUARIO_DERIVAR());
            cv.put("ID_ROL_USUARIO_DERIVAR ",documentosCobraMovBE.getID_ROL_USUARIO_DERIVAR());
            cv.put("FECHA_DERIVAR ",documentosCobraMovBE.getFECHA_DERIVAR());
            cv.put("FECHA_MOVIMIENTO ",documentosCobraMovBE.getFECHA_MOVIMIENTO());
            cv.put("FECHA_ACCION ",documentosCobraMovBE.getFECHA_ACCION());
            cv.put("ESTADO_MOVIMIENTO ",documentosCobraMovBE.getESTADO_MOVIMIENTO());
            cv.put("ID_EMPRESA ",documentosCobraMovBE.getID_EMPRESA());
            cv.put("ID_LOCAL",documentosCobraMovBE.getID_LOCAL());
            cv.put("GUARDADO", "0");
            cv.put("SINCRONIZADO", "0");
            DataBaseHelper.myDataBase.insert("S_CCM_DOCUMENTOS_COBRA_MOV",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(Documentos_Cobra_MovBE documentosCobraMovBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_DOCUMENTO_MOVIMIENTO",documentosCobraMovBE.getID_DOCUMENTO_MOVIMIENTO());
            cv.put("SERIE_PLANILLA",documentosCobraMovBE.getSERIE_PLANILLA());
            cv.put("N_PLANILLA",documentosCobraMovBE.getN_PLANILLA());
            cv.put("ID_USUARIO_REGISTRO",documentosCobraMovBE.getID_USUARIO_REGISTRO());
            cv.put("ID_ROL_USUARIO_REGISTRO ",documentosCobraMovBE.getID_ROL_USUARIO_REGISTRO());
            cv.put("FECHA_RECEPCION ",documentosCobraMovBE.getFECHA_RECEPCION());
            cv.put("ID_USUARIO_DERIVAR ",documentosCobraMovBE.getID_USUARIO_DERIVAR());
            cv.put("ID_ROL_USUARIO_DERIVAR ",documentosCobraMovBE.getID_ROL_USUARIO_DERIVAR());
            cv.put("FECHA_DERIVAR ",documentosCobraMovBE.getFECHA_DERIVAR());
            cv.put("FECHA_MOVIMIENTO ",documentosCobraMovBE.getFECHA_MOVIMIENTO());
            cv.put("FECHA_ACCION ",documentosCobraMovBE.getFECHA_ACCION());
            cv.put("ESTADO_MOVIMIENTO ",documentosCobraMovBE.getESTADO_MOVIMIENTO());
            cv.put("ID_EMPRESA ",documentosCobraMovBE.getID_EMPRESA());
            cv.put("ID_LOCAL",documentosCobraMovBE.getID_LOCAL());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_MOV",cv,"ID_DOCUMENTO_MOVIMIENTO = ?",
                    new String[]{String.valueOf(documentosCobraMovBE.getID_DOCUMENTO_MOVIMIENTO())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(Documentos_Cobra_MovBE documentosCobraMovBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("Documentos_Cobra_Mov","ID_DOCUMENTO_MOVIMIENTO = ?", new String[]{String.valueOf(documentosCobraMovBE.getID_DOCUMENTO_MOVIMIENTO())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }



}

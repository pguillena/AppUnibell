package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sea_AccesosBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sea_AccesosDAO {
    public ArrayList<S_Sea_AccesosBE> lst = null;

    public void getAll(String pC_PERFIL) {
        Cursor cursor = null;
        S_Sea_AccesosBE s_sea_accesosBE = null;
        try {
            String SQL="SELECT C_PERFIL,C_MENU,VISIBLE,NUEVO,EDITAR,GRABAR," +
                    "BUSCAR,ANULAR,ENVIAR,AGREGAR,ELIMINAR,EXPORTAR," +
                    "IMPRIMIR,CERRAR,EX_WORD,EX_EXCEL,EX_PDF,EMAIL," +
                    "ICONO,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO," +
                    "IP_MODIFICACION,FECHA_REGISTRO,FECHA_MODIFICACION,ESTADO,ID_EMPRESA "+
                    "FROM S_SEA_ACCESOS WHERE (" + pC_PERFIL + "=-1 OR C_PERFIL=" + pC_PERFIL + ") ORDER BY C_PERFIL";

                    
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sea_AccesosBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sea_accesosBE = new S_Sea_AccesosBE();
                    s_sea_accesosBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    s_sea_accesosBE.setC_MENU(Funciones.isNullColumn(cursor,"C_MENU",""));
                    s_sea_accesosBE.setVISIBLE(Funciones.isNullColumn(cursor,"VISIBLE",""));
                    s_sea_accesosBE.setNUEVO(Funciones.isNullColumn(cursor,"NUEVO",0));
                    s_sea_accesosBE.setEDITAR(Funciones.isNullColumn(cursor,"EDITAR",0));
                    s_sea_accesosBE.setGRABAR(Funciones.isNullColumn(cursor,"GRABAR",0));
                    s_sea_accesosBE.setBUSCAR(Funciones.isNullColumn(cursor,"BUSCAR",0));
                    s_sea_accesosBE.setANULAR(Funciones.isNullColumn(cursor,"ANULAR",0));
                    s_sea_accesosBE.setENVIAR(Funciones.isNullColumn(cursor,"ENVIAR",0));
                    s_sea_accesosBE.setAGREGAR(Funciones.isNullColumn(cursor,"AGREGAR",0));
                    s_sea_accesosBE.setELIMINAR(Funciones.isNullColumn(cursor,"ELIMINAR",0));
                    s_sea_accesosBE.setEXPORTAR(Funciones.isNullColumn(cursor,"EXPORTAR",0));
                    s_sea_accesosBE.setIMPRIMIR(Funciones.isNullColumn(cursor,"IMPRIMIR",0));
                    s_sea_accesosBE.setCERRAR(Funciones.isNullColumn(cursor,"CERRAR",0));
                    s_sea_accesosBE.setEX_WORD(Funciones.isNullColumn(cursor,"EX_WORD",0));
                    s_sea_accesosBE.setEX_EXCEL(Funciones.isNullColumn(cursor,"EX_EXCEL",0));
                    s_sea_accesosBE.setEX_PDF(Funciones.isNullColumn(cursor,"EX_PDF",0));
                    s_sea_accesosBE.setEMAIL(Funciones.isNullColumn(cursor,"EMAIL",0));
                    s_sea_accesosBE.setICONO(Funciones.isNullColumn(cursor,"ICONO",0));
                    s_sea_accesosBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_sea_accesosBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_sea_accesosBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_sea_accesosBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_sea_accesosBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_sea_accesosBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_sea_accesosBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_sea_accesosBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_sea_accesosBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sea_accesosBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));                  
                    lst.add(s_sea_accesosBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Sea_AccesosBE s_sea_accesosBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("C_PERFIL",s_sea_accesosBE.getC_PERFIL());
            cv.put("C_MENU",s_sea_accesosBE.getC_MENU());
            cv.put("VISIBLE",s_sea_accesosBE.getVISIBLE());
            cv.put("NUEVO",s_sea_accesosBE.getNUEVO());
            cv.put("EDITAR",s_sea_accesosBE.getEDITAR());
            cv.put("GRABAR",s_sea_accesosBE.getGRABAR());
            cv.put("BUSCAR",s_sea_accesosBE.getBUSCAR());
            cv.put("ANULAR",s_sea_accesosBE.getANULAR());
            cv.put("ENVIAR",s_sea_accesosBE.getENVIAR());
            cv.put("AGREGAR",s_sea_accesosBE.getAGREGAR());
            cv.put("ELIMINAR",s_sea_accesosBE.getELIMINAR());
            cv.put("EXPORTAR",s_sea_accesosBE.getEXPORTAR());
            cv.put("IMPRIMIR",s_sea_accesosBE.getIMPRIMIR());
            cv.put("CERRAR",s_sea_accesosBE.getCERRAR());
            cv.put("EX_WORD",s_sea_accesosBE.getEX_WORD());
            cv.put("EX_EXCEL",s_sea_accesosBE.getEX_EXCEL());
            cv.put("EX_PDF",s_sea_accesosBE.getEX_PDF());
            cv.put("EMAIL",s_sea_accesosBE.getEMAIL());
            cv.put("ICONO",s_sea_accesosBE.getICONO());
            cv.put("USUARIO_REGISTRO",s_sea_accesosBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sea_accesosBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sea_accesosBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sea_accesosBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sea_accesosBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sea_accesosBE.getIP_MODIFICACION());
            cv.put("FECHA_REGISTRO",s_sea_accesosBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sea_accesosBE.getFECHA_MODIFICACION());
            cv.put("ESTADO",s_sea_accesosBE.getESTADO());
            cv.put("ID_EMPRESA",s_sea_accesosBE.getID_EMPRESA());
            DataBaseHelper.myDataBase.insert("S_SEA_ACCESOS ",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    
}

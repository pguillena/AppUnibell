package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.S_Sem_EmpresaBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Sem_EmpresaDAO {

    public ArrayList<S_Sem_EmpresaBE> lst = null;

    public void getAllBY(String pID_VENDEDOR) {
        Cursor cursor = null;
        S_Sem_EmpresaBE s_sem_empresaBE = null;
        try {
            String SQL="SELECT ID_EMPRESA,NOMBRE,RAZON_SOCIAL,DIRECCION,RUC," +
            "TELEFONO,FAX,REPRESENTANTE,ESTADO,FECHA_REGISTRO," +
            "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION," +
            "IP_REGISTRO,IP_MODIFICACION,C_EMPRESA,AGENTE_PERCEPCION,AGENTE_RETENCION," +
            "BUEN_CONTRIBUYENTE,UBIGEO,RESOLUCION_SUNAT_FE,PAGINA_WEB "+
            "FROM S_Sem_Empresa " +
             " WHERE ID_EMPRESA IN(SELECT ID_EMPRESA FROM S_SEA_USUARIO_LOCAL WHERE ID_PERSONA=" + pID_VENDEDOR + ")";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Sem_EmpresaBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_sem_empresaBE = new S_Sem_EmpresaBE();
                    s_sem_empresaBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_sem_empresaBE.setNOMBRE(Funciones.isNullColumn(cursor,"NOMBRE",""));
                    s_sem_empresaBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL",""));
                    s_sem_empresaBE.setDIRECCION(Funciones.isNullColumn(cursor,"DIRECCION",""));
                    s_sem_empresaBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                    s_sem_empresaBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    s_sem_empresaBE.setFAX(Funciones.isNullColumn(cursor,"FAX",""));
                    s_sem_empresaBE.setREPRESENTANTE(Funciones.isNullColumn(cursor,"REPRESENTANTE",""));
                    s_sem_empresaBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_sem_empresaBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_sem_empresaBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_sem_empresaBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_sem_empresaBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_sem_empresaBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_sem_empresaBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_sem_empresaBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_sem_empresaBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_sem_empresaBE.setC_EMPRESA(Funciones.isNullColumn(cursor,"C_EMPRESA",""));
                    s_sem_empresaBE.setAGENTE_PERCEPCION(Funciones.isNullColumn(cursor,"AGENTE_PERCEPCION",0));
                    s_sem_empresaBE.setAGENTE_RETENCION(Funciones.isNullColumn(cursor,"AGENTE_RETENCION",0));
                    s_sem_empresaBE.setBUEN_CONTRIBUYENTE(Funciones.isNullColumn(cursor,"BUEN_CONTRIBUYENTE",0));
                    s_sem_empresaBE.setUBIGEO(Funciones.isNullColumn(cursor,"UBIGEO",""));
                    s_sem_empresaBE.setRESOLUCION_SUNAT_FE(Funciones.isNullColumn(cursor,"RESOLUCION_SUNAT_FE",""));
                    s_sem_empresaBE.setPAGINA_WEB(Funciones.isNullColumn(cursor,"PAGINA_WEB",""));
                    lst.add(s_sem_empresaBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }




    public String insert(S_Sem_EmpresaBE s_sem_empresaBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
            cv.put("ID_EMPRESA",s_sem_empresaBE.getID_EMPRESA());
            cv.put("NOMBRE",s_sem_empresaBE.getNOMBRE());
            cv.put("RAZON_SOCIAL",s_sem_empresaBE.getRAZON_SOCIAL());
            cv.put("DIRECCION",s_sem_empresaBE.getDIRECCION());
            cv.put("RUC",s_sem_empresaBE.getRUC());
            cv.put("TELEFONO",s_sem_empresaBE.getTELEFONO());
            cv.put("FAX",s_sem_empresaBE.getFAX());
            cv.put("REPRESENTANTE",s_sem_empresaBE.getREPRESENTANTE());
            cv.put("ESTADO",s_sem_empresaBE.getESTADO());
            cv.put("FECHA_REGISTRO",s_sem_empresaBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_empresaBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_empresaBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_empresaBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_empresaBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_empresaBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_empresaBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_empresaBE.getIP_MODIFICACION());
            cv.put("C_EMPRESA",s_sem_empresaBE.getC_EMPRESA());
            cv.put("AGENTE_PERCEPCION",s_sem_empresaBE.getAGENTE_PERCEPCION());
            cv.put("AGENTE_RETENCION",s_sem_empresaBE.getAGENTE_RETENCION());
            cv.put("BUEN_CONTRIBUYENTE",s_sem_empresaBE.getBUEN_CONTRIBUYENTE());
            cv.put("UBIGEO",s_sem_empresaBE.getUBIGEO());
            cv.put("RESOLUCION_SUNAT_FE",s_sem_empresaBE.getRESOLUCION_SUNAT_FE());
            cv.put("PAGINA_WEB",s_sem_empresaBE.getPAGINA_WEB());

            DataBaseHelper.myDataBase.insert("S_Sem_Empresa",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String update(S_Sem_EmpresaBE s_sem_empresaBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_EMPRESA",s_sem_empresaBE.getID_EMPRESA());
            cv.put("NOMBRE",s_sem_empresaBE.getNOMBRE());
            cv.put("RAZON_SOCIAL",s_sem_empresaBE.getRAZON_SOCIAL());
            cv.put("DIRECCION",s_sem_empresaBE.getDIRECCION());
            cv.put("RUC",s_sem_empresaBE.getRUC());
            cv.put("TELEFONO",s_sem_empresaBE.getTELEFONO());
            cv.put("FAX",s_sem_empresaBE.getFAX());
            cv.put("REPRESENTANTE",s_sem_empresaBE.getREPRESENTANTE());
            cv.put("ESTADO",s_sem_empresaBE.getESTADO());
            cv.put("FECHA_REGISTRO",s_sem_empresaBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",s_sem_empresaBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",s_sem_empresaBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",s_sem_empresaBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",s_sem_empresaBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",s_sem_empresaBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",s_sem_empresaBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",s_sem_empresaBE.getIP_MODIFICACION());
            cv.put("C_EMPRESA",s_sem_empresaBE.getC_EMPRESA());
            cv.put("AGENTE_PERCEPCION",s_sem_empresaBE.getAGENTE_PERCEPCION());
            cv.put("AGENTE_RETENCION",s_sem_empresaBE.getAGENTE_RETENCION());
            cv.put("BUEN_CONTRIBUYENTE",s_sem_empresaBE.getBUEN_CONTRIBUYENTE());
            cv.put("UBIGEO",s_sem_empresaBE.getUBIGEO());
            cv.put("RESOLUCION_SUNAT_FE",s_sem_empresaBE.getRESOLUCION_SUNAT_FE());
            cv.put("PAGINA_WEB",s_sem_empresaBE.getPAGINA_WEB());

            DataBaseHelper.myDataBase.update("S_Sem_Empresa",cv,"ID_EMPRESA = ?",
                    new String[]{String.valueOf(s_sem_empresaBE.getID_EMPRESA())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String delete(S_Sem_EmpresaBE s_sem_empresaBE){
        String sMensaje="";
        try{
            DataBaseHelper.myDataBase.delete("S_Sem_Empresa","ID_EMPRESA = ?", new String[]{String.valueOf(s_sem_empresaBE.getID_EMPRESA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

}

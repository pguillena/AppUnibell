package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.MvendedorBE;
import pe.com.app.unibell.appunibell.BE.S_Gem_ClienteBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class S_Gem_ClienteDAO {

    public ArrayList<S_Gem_ClienteBE> lst = null;

    public void getAll(String pID_CLIENTE) {
        Cursor cursor = null;
        S_Gem_ClienteBE s_gem_clienteBE = null;
        try {
            String SQL="SELECT ID_CLIENTE,CODIGO,RAZON_SOCIAL,ID_ZONA,TIPO_GIRO,TELEFONO," +
                    "FAX,FECHA_INGRES,TIPO_CALIFICACION,ESTADO,RUC,CLIENTE_AFECTO," +
                    "UBICACION,DNI,DIA_VISITA,FRC_VISITA,FLAG_RETENCION,CALIF_VTA," +
                    "CALIF_CRED,PLAZO_PAGO,ID_CANAL,CORREO,ID_NIVEL,ID_FUERZA_VTA," +
                    "ID_EMPRESA,ID_LOCAL,COND_PAGO,ID_GRUPO,ID_LISTA_DESCUENTO,ID_CANAL_DETALLE," +
                    "LIM_CRED,PROM_MAX_DIA_PAGO,PORC_MAX_DEUDA,MONT_MAX_DEUDA,FLAG_PERCEPCION,FECHA_REGISTRO," +
                    "FECHA_MODIFICACION,USUARIO_REGISTRO,USUARIO_MODIFICACION,PC_REGISTRO,PC_MODIFICACION,IP_REGISTRO," +
                    "IP_MODIFICACION,CLIENTE_ESPECIAL,ID_COBRADOR,ID_COBRADOR_EXT,CUOTA_SEMANAL,COD_ALM_CONSIG," +
                    "FLAG_FE,LISTA_PRECIO "+
                    "FROM S_GEM_CLIENTE  WHERE (" + pID_CLIENTE + "=-1 OR ID_CLIENTE=" + pID_CLIENTE + ") ORDER BY ID_CLIENTE";
                    
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<S_Gem_ClienteBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    s_gem_clienteBE = new S_Gem_ClienteBE();
                    s_gem_clienteBE.setID_CLIENT(Funciones.isNullColumn(cursor,"ID_CLIENT",0));
                    s_gem_clienteBE.setCODIGO(Funciones.isNullColumn(cursor,"CODIGO",""));
                    s_gem_clienteBE.setRAZON_SOCIAL(Funciones.isNullColumn(cursor,"RAZON_SOCIAL",""));
                    s_gem_clienteBE.setID_ZONA(Funciones.isNullColumn(cursor,"ID_ZONA",0));
                    s_gem_clienteBE.setTIPO_GIRO(Funciones.isNullColumn(cursor,"TIPO_GIRO",0));
                    s_gem_clienteBE.setTELEFONO(Funciones.isNullColumn(cursor,"TELEFONO",""));
                    s_gem_clienteBE.setFAX(Funciones.isNullColumn(cursor,"FAX",""));
                    s_gem_clienteBE.setFECHA_INGRES(Funciones.isNullColumn(cursor,"FECHA_INGRES",""));
                    s_gem_clienteBE.setTIPO_CALIFICACION(Funciones.isNullColumn(cursor,"TIPO_CALIFICACION",0));
                    s_gem_clienteBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    s_gem_clienteBE.setRUC(Funciones.isNullColumn(cursor,"RUC",""));
                    s_gem_clienteBE.setCLIENTE_AFECTO(Funciones.isNullColumn(cursor,"CLIENTE_AFECTO",0));
                    s_gem_clienteBE.setUBICACION(Funciones.isNullColumn(cursor,"UBICACION",""));
                    s_gem_clienteBE.setDNI(Funciones.isNullColumn(cursor,"DNI",""));
                    s_gem_clienteBE.setDIA_VISITA(Funciones.isNullColumn(cursor,"DIA_VISITA",0.0));
                    s_gem_clienteBE.setFRC_VISITA(Funciones.isNullColumn(cursor,"FRC_VISITA",0.0));
                    s_gem_clienteBE.setFLAG_RETENCION(Funciones.isNullColumn(cursor,"FLAG_RETENCION",""));
                    s_gem_clienteBE.setCALIF_VTA(Funciones.isNullColumn(cursor,"CALIF_VTA",0));
                    s_gem_clienteBE.setCALIF_CRED(Funciones.isNullColumn(cursor,"CALIF_CRED",0));
                    s_gem_clienteBE.setPLAZO_PAGO(Funciones.isNullColumn(cursor,"PLAZO_PAGO",0.0));
                    s_gem_clienteBE.setID_CANAL(Funciones.isNullColumn(cursor,"ID_CANAL",0));
                    s_gem_clienteBE.setCORREO(Funciones.isNullColumn(cursor,"CORREO",""));
                    s_gem_clienteBE.setID_NIVEL(Funciones.isNullColumn(cursor,"ID_NIVEL",0));
                    s_gem_clienteBE.setID_FUERZA_VTA(Funciones.isNullColumn(cursor,"ID_FUERZA_VTA",0));
                    s_gem_clienteBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    s_gem_clienteBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    s_gem_clienteBE.setCOND_PAGO(Funciones.isNullColumn(cursor,"COND_PAGO",0.0));
                    s_gem_clienteBE.setID_GRUPO(Funciones.isNullColumn(cursor,"ID_GRUPO",0));
                    s_gem_clienteBE.setID_LISTA_DESCUENTO(Funciones.isNullColumn(cursor,"ID_LISTA_DESCUENTO",0));
                    s_gem_clienteBE.setID_CANAL_DETALLE(Funciones.isNullColumn(cursor,"ID_CANAL_DETALLE",0));
                    s_gem_clienteBE.setLIM_CRED(Funciones.isNullColumn(cursor,"LIM_CRED",0.0));
                    s_gem_clienteBE.setPROM_MAX_DIA_PAGO(Funciones.isNullColumn(cursor,"PROM_MAX_DIA_PAGO",0.0));
                    s_gem_clienteBE.setPORC_MAX_DEUDA(Funciones.isNullColumn(cursor,"PORC_MAX_DEUDA",0.0));
                    s_gem_clienteBE.setMONT_MAX_DEUDA(Funciones.isNullColumn(cursor,"MONT_MAX_DEUDA",0.0));
                    s_gem_clienteBE.setFLAG_PERCEPCION(Funciones.isNullColumn(cursor,"FLAG_PERCEPCION",0));
                    s_gem_clienteBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    s_gem_clienteBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    s_gem_clienteBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    s_gem_clienteBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    s_gem_clienteBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    s_gem_clienteBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    s_gem_clienteBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    s_gem_clienteBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    s_gem_clienteBE.setCLIENTE_ESPECIAL(Funciones.isNullColumn(cursor,"CLIENTE_ESPECIAL",0));
                    s_gem_clienteBE.setID_COBRADOR(Funciones.isNullColumn(cursor,"ID_COBRADOR",0));
                    s_gem_clienteBE.setID_COBRADOR_EXT(Funciones.isNullColumn(cursor,"ID_COBRADOR_EXT",0));
                    s_gem_clienteBE.setCUOTA_SEMANAL(Funciones.isNullColumn(cursor,"CUOTA_SEMANAL",0));
                    s_gem_clienteBE.setCOD_ALM_CONSIG(Funciones.isNullColumn(cursor,"COD_ALM_CONSIG",""));
                    s_gem_clienteBE.setFLAG_FE(Funciones.isNullColumn(cursor,"FLAG_FE",0));
                    s_gem_clienteBE.setLISTA_PRECIO(Funciones.isNullColumn(cursor,"LISTA_PRECIO",0.0));                            
                    lst.add(s_gem_clienteBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public String insert(S_Gem_ClienteBE s_gem_clienteBE){
        String sMensaje="";

        try{
            ContentValues cv = new ContentValues();
            SistemaDAO sistemaDAO=new SistemaDAO();
             cv.put("ID_CLIENTE",s_gem_clienteBE.getID_CLIENT());
             cv.put("CODIGO",s_gem_clienteBE.getCODIGO());
             cv.put("RAZON_SOCIAL",s_gem_clienteBE.getRAZON_SOCIAL());
             cv.put("ID_ZONA",s_gem_clienteBE.getID_ZONA());
             cv.put("TIPO_GIRO",s_gem_clienteBE.getTIPO_GIRO());
             cv.put("TELEFONO",s_gem_clienteBE.getTELEFONO());
             cv.put("FAX",s_gem_clienteBE.getFAX());
             cv.put("FECHA_INGRES",s_gem_clienteBE.getFECHA_INGRES());
             cv.put("TIPO_CALIFICACION",s_gem_clienteBE.getTIPO_CALIFICACION());
             cv.put("ESTADO",s_gem_clienteBE.getESTADO());
             cv.put("RUC",s_gem_clienteBE.getRUC());
             cv.put("CLIENTE_AFECTO",s_gem_clienteBE.getCLIENTE_AFECTO());
             cv.put("UBICACION",s_gem_clienteBE.getUBICACION());
             cv.put("DNI",s_gem_clienteBE.getDNI());
             cv.put("DIA_VISITA",s_gem_clienteBE.getDIA_VISITA());
             cv.put("FRC_VISITA",s_gem_clienteBE.getFRC_VISITA());
             cv.put("FLAG_RETENCION",s_gem_clienteBE.getFLAG_RETENCION());
             cv.put("CALIF_VTA",s_gem_clienteBE.getCALIF_VTA());
             cv.put("CALIF_CRED",s_gem_clienteBE.getCALIF_CRED());
             cv.put("PLAZO_PAGO",s_gem_clienteBE.getPLAZO_PAGO());
             cv.put("ID_CANAL",s_gem_clienteBE.getID_CANAL());
             cv.put("CORREO ",s_gem_clienteBE.getCORREO());
             cv.put("ID_NIVEL",s_gem_clienteBE.getID_NIVEL());
             cv.put("ID_FUERZA_VTA",s_gem_clienteBE.getID_FUERZA_VTA());
             cv.put("ID_EMPRESA",s_gem_clienteBE.getID_EMPRESA());
             cv.put("ID_LOCAL",s_gem_clienteBE.getID_LOCAL());
             cv.put("COND_PAGO",s_gem_clienteBE.getCOND_PAGO());
             cv.put("ID_GRUPO",s_gem_clienteBE.getID_GRUPO());
             cv.put("ID_LISTA_DESCUENTO",s_gem_clienteBE.getID_LISTA_DESCUENTO());
             cv.put("ID_CANAL_DETALLE",s_gem_clienteBE.getID_CANAL_DETALLE());
             cv.put("LIM_CRED",s_gem_clienteBE.getLIM_CRED());
             cv.put("PROM_MAX_DIA_PAGO",s_gem_clienteBE.getPROM_MAX_DIA_PAGO());
             cv.put("PORC_MAX_DEUDA",s_gem_clienteBE.getPORC_MAX_DEUDA());
             cv.put("MONT_MAX_DEUDA",s_gem_clienteBE.getMONT_MAX_DEUDA());
             cv.put("FLAG_PERCEPCION",s_gem_clienteBE.getFLAG_PERCEPCION());
             cv.put("FECHA_REGISTRO",s_gem_clienteBE.getFECHA_REGISTRO());
             cv.put("FECHA_MODIFICACION",s_gem_clienteBE.getFECHA_MODIFICACION());
             cv.put("USUARIO_REGISTRO",s_gem_clienteBE.getUSUARIO_REGISTRO());
             cv.put("USUARIO_MODIFICACION",s_gem_clienteBE.getUSUARIO_MODIFICACION());
             cv.put("PC_REGISTRO",s_gem_clienteBE.getPC_REGISTRO());
             cv.put("PC_MODIFICACION",s_gem_clienteBE.getPC_MODIFICACION());
             cv.put("IP_REGISTRO",s_gem_clienteBE.getIP_REGISTRO());
             cv.put("IP_MODIFICACION",s_gem_clienteBE.getIP_MODIFICACION());
             cv.put("CLIENTE_ESPECIAL",s_gem_clienteBE.getCLIENTE_ESPECIAL());
             cv.put("ID_COBRADOR",s_gem_clienteBE.getID_COBRADOR());
             cv.put("ID_COBRADOR_EXT",s_gem_clienteBE.getID_COBRADOR_EXT());
             cv.put("CUOTA_SEMANAL",s_gem_clienteBE.getCUOTA_SEMANAL());
             cv.put("COD_ALM_CONSIG",s_gem_clienteBE.getCOD_ALM_CONSIG());
             cv.put("FLAG_FE",s_gem_clienteBE.getFLAG_FE());
             cv.put("LISTA_PRECIO",s_gem_clienteBE.getLISTA_PRECIO());

            DataBaseHelper.myDataBase.insert("S_GEM_CLIENTE",null,cv);
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


}

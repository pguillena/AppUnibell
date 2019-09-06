package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Dpm_Packing_CabBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Dpm_Packing_CabDAO {
    public ArrayList<Dpm_Packing_CabBE> lst = null;

    public void getAll(String pCODIGO) {
        Cursor cursor = null;
        Dpm_Packing_CabBE dpm_packing_cabBE = null;
        try {
            String SQL=" SELECT C_PACKING,F_PACKING,F_SALIDA,F_RETORNO,C_EMPTRANS,C_VEHICULO, " +
                    " C_CHOFER,C_REPARTIDOR,PLACA,HORA_SALIDA,HORA_RETORNO,OBSERVACION, " +
                    " F_CIERRE,C_USUARIO_CIE,C_USUARIO,C_PERFIL,C_CPU,FEC_REG, " +
                    " C_USUARIO_MOD,C_PERFIL_MOD,FEC_MOD,C_ESTADO,C_CPU_MOD,ANULADO, " +
                    " C_DESPACHADOR,C_CHEQUEADOR,C_CONTROLADOR,C_USUARIO_RECCRE,C_PERFIL_RECCRE,C_CPU_RECCRE, " +
                    " F_REGISTRO_RECCRE,I_VERIFICA_RECCRE,C_TUSUARIO_RECCRE,C_TPERFIL_RECCRE,C_TCPU_RECCRE,F_TREGISTRO_RECCRE, " +
                    " I_TVERIFICA_RECCRE,ID_LOCAL "+
                    " FROM DPM_PACKING_CAB " +
                    " WHERE C_PACKING = "+pCODIGO;

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Dpm_Packing_CabBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    dpm_packing_cabBE = new Dpm_Packing_CabBE();
                    dpm_packing_cabBE.setC_PACKING(Funciones.isNullColumn(cursor,"C_PACKING",0));
                    dpm_packing_cabBE.setF_PACKING(Funciones.isNullColumn(cursor,"F_PACKING",""));
                    dpm_packing_cabBE.setF_SALIDA(Funciones.isNullColumn(cursor,"F_SALIDA",""));
                    dpm_packing_cabBE.setF_RETORNO(Funciones.isNullColumn(cursor,"F_RETORNO",""));
                    dpm_packing_cabBE.setC_EMPTRANS(Funciones.isNullColumn(cursor,"C_EMPTRANS",""));
                    dpm_packing_cabBE.setC_VEHICULO(Funciones.isNullColumn(cursor,"C_VEHICULO",""));
                    dpm_packing_cabBE.setC_CHOFER(Funciones.isNullColumn(cursor,"C_CHOFER",""));
                    dpm_packing_cabBE.setC_REPARTIDOR(Funciones.isNullColumn(cursor,"C_REPARTIDOR",""));
                    dpm_packing_cabBE.setPLACA(Funciones.isNullColumn(cursor,"PLACA",""));
                    dpm_packing_cabBE.setHORA_SALIDA(Funciones.isNullColumn(cursor,"HORA_SALIDA",""));
                    dpm_packing_cabBE.setHORA_RETORNO(Funciones.isNullColumn(cursor,"HORA_RETORNO",""));
                    dpm_packing_cabBE.setOBSERVACION(Funciones.isNullColumn(cursor,"OBSERVACION",""));
                    dpm_packing_cabBE.setF_CIERRE(Funciones.isNullColumn(cursor,"F_CIERRE",""));
                    dpm_packing_cabBE.setC_USUARIO_CIE(Funciones.isNullColumn(cursor,"C_USUARIO_CIE",""));
                    dpm_packing_cabBE.setC_USUARIO(Funciones.isNullColumn(cursor,"C_USUARIO",""));
                    dpm_packing_cabBE.setC_PERFIL(Funciones.isNullColumn(cursor,"C_PERFIL",""));
                    dpm_packing_cabBE.setC_CPU(Funciones.isNullColumn(cursor,"C_CPU",""));
                    dpm_packing_cabBE.setFEC_REG(Funciones.isNullColumn(cursor,"FEC_REG",""));
                    dpm_packing_cabBE.setC_USUARIO_MOD(Funciones.isNullColumn(cursor,"C_USUARIO_MOD",""));
                    dpm_packing_cabBE.setC_PERFIL_MOD(Funciones.isNullColumn(cursor,"C_PERFIL_MOD",""));
                    dpm_packing_cabBE.setFEC_MOD(Funciones.isNullColumn(cursor,"FEC_MOD",""));
                    dpm_packing_cabBE.setC_ESTADO(Funciones.isNullColumn(cursor,"C_ESTADO",""));
                    dpm_packing_cabBE.setC_CPU_MOD(Funciones.isNullColumn(cursor,"C_CPU_MOD",""));
                    dpm_packing_cabBE.setANULADO(Funciones.isNullColumn(cursor,"ANULADO",""));
                    dpm_packing_cabBE.setC_DESPACHADOR(Funciones.isNullColumn(cursor,"C_DESPACHADOR",""));
                    dpm_packing_cabBE.setC_CHEQUEADOR(Funciones.isNullColumn(cursor,"C_CHEQUEADOR",""));
                    dpm_packing_cabBE.setC_CONTROLADOR(Funciones.isNullColumn(cursor,"C_CONTROLADOR",""));
                    dpm_packing_cabBE.setC_USUARIO_RECCRE(Funciones.isNullColumn(cursor,"C_USUARIO_RECCRE",""));
                    dpm_packing_cabBE.setC_PERFIL_RECCRE(Funciones.isNullColumn(cursor,"C_PERFIL_RECCRE",""));
                    dpm_packing_cabBE.setC_CPU_RECCRE(Funciones.isNullColumn(cursor,"C_CPU_RECCRE",""));
                    dpm_packing_cabBE.setF_REGISTRO_RECCRE(Funciones.isNullColumn(cursor,"F_REGISTRO_RECCRE",""));
                    dpm_packing_cabBE.setI_VERIFICA_RECCRE(Funciones.isNullColumn(cursor,"I_VERIFICA_RECCRE",""));
                    dpm_packing_cabBE.setC_TUSUARIO_RECCRE(Funciones.isNullColumn(cursor,"C_TUSUARIO_RECCRE",""));
                    dpm_packing_cabBE.setC_TPERFIL_RECCRE(Funciones.isNullColumn(cursor,"C_TPERFIL_RECCRE",""));
                    dpm_packing_cabBE.setC_TCPU_RECCRE(Funciones.isNullColumn(cursor,"C_TCPU_RECCRE",""));
                    dpm_packing_cabBE.setF_TREGISTRO_RECCRE(Funciones.isNullColumn(cursor,"F_TREGISTRO_RECCRE",""));
                    dpm_packing_cabBE.setI_TVERIFICA_RECCRE(Funciones.isNullColumn(cursor,"I_TVERIFICA_RECCRE",""));
                    dpm_packing_cabBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    lst.add(dpm_packing_cabBE);
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

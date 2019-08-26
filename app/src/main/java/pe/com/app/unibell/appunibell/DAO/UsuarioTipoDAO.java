package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Usuario_TipoBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

/**
 * Created by rgalvez on 12/10/2016.
 */
public class UsuarioTipoDAO {
    public ArrayList<Usuario_TipoBE> lisUsuariotipo = null;

        public void getAllSQLite() {
            Cursor cursor = null;
            Usuario_TipoBE usuario_tipoBE = null;
            try {
                String[] campos = new String[] {"TIU_CORREL","TIU_NOMBRE","TIU_ESTADO"};
                cursor = DataBaseHelper.myDataBase.query("REST_USUARIO_TIPO", null, null,null, null, null, "TIU_NOMBRE");
                lisUsuariotipo = new ArrayList<Usuario_TipoBE>();
                lisUsuariotipo.clear();
                if (cursor.moveToFirst()) {
                    do {

                        usuario_tipoBE = new Usuario_TipoBE();
                        usuario_tipoBE.setTIU_CORREL(Funciones.isNullColumn(cursor,"TIU_CORREL",0));
                        usuario_tipoBE.setTIU_NOMBRE(Funciones.isNullColumn(cursor,"TIU_NOMBRE",""));
                        usuario_tipoBE.setTIU_ESTADO(Funciones.isNullColumn(cursor,"TIU_ESTADO",0));
                        lisUsuariotipo.add(new Usuario_TipoBE(usuario_tipoBE.getTIU_CORREL(),usuario_tipoBE.getTIU_NOMBRE(),usuario_tipoBE.getTIU_ESTADO()));
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        public String insertUsuarioTipo(Usuario_TipoBE usuario_tipoBE){
            String sMensaje="";
            try{
                ContentValues cv = new ContentValues();
                SistemaDAO sistemaDAO=new SistemaDAO();
                cv.put("TIU_CORREL",sistemaDAO.MAX_REGISTRO("REST_USUARIO_TIPO","TIU_CORREL"));
                cv.put("TIU_NOMBRE",usuario_tipoBE.getTIU_NOMBRE());
                cv.put("TIU_ESTADO",usuario_tipoBE.getTIU_ESTADO());

                DataBaseHelper.myDataBase.insert("REST_USUARIO_TIPO",null,cv);
                sMensaje="";
            }catch (Exception ex){
                sMensaje="Error:" + ex.getMessage().toString();
                ex.printStackTrace();
            }
            return sMensaje;
        }

        public String updateUsuarioTipo(Usuario_TipoBE usuario_tipoBE){
            String sMensaje="";
            try{
                ContentValues cv = new ContentValues();
                cv.put("TIU_NOMBRE",usuario_tipoBE.getTIU_NOMBRE());
                cv.put("TIU_ESTADO",usuario_tipoBE.getTIU_ESTADO());
                DataBaseHelper.myDataBase.update("REST_USUARIO_TIPO",cv,"TIU_CORREL= ?",
                        new String[]{String.valueOf(usuario_tipoBE.getTIU_CORREL())});
                sMensaje="";
            }catch (Exception ex){
                sMensaje="Error:" + ex.getMessage().toString();
                ex.printStackTrace();
            }
            return sMensaje;
        }

        public String deleteUsuarioTipo(Usuario_TipoBE usuario_tipoBE){
            String sMensaje="";
            try{
                DataBaseHelper.myDataBase.delete("REST_USUARIO_TIPO","USU_CORREL = ?", new String[]{String.valueOf(usuario_tipoBE.getTIU_CORREL())});
                sMensaje="";
            }catch (Exception ex){
                sMensaje="Error:" + ex.getMessage().toString();
                ex.printStackTrace();
            }
            return sMensaje;
        }

}
package pe.com.app.unibell.appunibell.DAO;

import android.database.Cursor;



import java.util.ArrayList;

import pe.com.app.unibell.appunibell.BE.Menu_StringBE;
import pe.com.app.unibell.appunibell.Util.Funciones;

/**
 * Created by rgalvez on 7/10/2016.
 */
public class Menu_StringDAO {

    public ArrayList<Menu_StringBE> lisMenu = null;

    public void getAllSQLite() {
        Cursor cursor = null;
        Menu_StringBE menu_stringBE = null;
        try {
            lisMenu = new ArrayList<Menu_StringBE>();
            lisMenu.clear();
          /*
            String SQL="SELECT IDCORREL,MNUNOM,MNUDES,MNUCHEK,MNUACTI,MENUEST,MNUORDEN FROM REST_MENUSTRING WHERE MNUCHEK=1" ;
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lisMenu = new ArrayList<Menu_StringBE>();
            lisMenu.clear();
            if (cursor.moveToFirst()) {
                do {
                    menu_stringBE = new Menu_StringBE();
                    menu_stringBE.setIDCORREL(Funciones.isNullColumn(cursor,"IDCORREL",0));
                    menu_stringBE.setMNUNOM(Funciones.isNullColumn(cursor,"MNUNOM",""));
                    menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"MNUDES",""));
                    menu_stringBE.setMNUCHEK(Funciones.isNullColumn(cursor,"MNUCHEK",0));
                    menu_stringBE.setMNUACTI(Funciones.isNullColumn(cursor,"MNUACTI",0));
                    menu_stringBE.setMENUEST(Funciones.isNullColumn(cursor,"MENUEST",0));
                    menu_stringBE.setMNUORDEN(Funciones.isNullColumn(cursor,"MNUORDEN",0));
                    menu_stringBE.setMNUPERMISO(false);
                    lisMenu.add(menu_stringBE);
                } while (cursor.moveToNext());
            }
            */

            //Agregamos la opcion Acerca De
            menu_stringBE = new Menu_StringBE();
            menu_stringBE.setIDCORREL(100);
            menu_stringBE.setMNUNOM("SMNU_ACERCADE");
            menu_stringBE.setMNUDES("Acerca De...");
            menu_stringBE.setMNUCHEK(0);
            menu_stringBE.setMNUACTI(1);
            menu_stringBE.setMENUEST(1);
            menu_stringBE.setMNUORDEN(100);
            menu_stringBE.setMNUPERMISO(true);
            lisMenu.add(menu_stringBE);


            //Agregamos la opcion de cerrar sesión
            menu_stringBE = new Menu_StringBE();
            menu_stringBE.setIDCORREL(100);
            menu_stringBE.setMNUNOM("SMNU_CERRAR");
            menu_stringBE.setMNUDES("Cerrar sesión");
            menu_stringBE.setMNUCHEK(0);
            menu_stringBE.setMNUACTI(1);
            menu_stringBE.setMENUEST(1);
            menu_stringBE.setMNUORDEN(100);
            menu_stringBE.setMNUPERMISO(true);
            lisMenu.add(menu_stringBE);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getAllSQLiteByRol(String ROL_NOMBRE) {
        Cursor cursor = null;
        Menu_StringBE menu_stringBE = null;
        try {
            String SQL="SELECT IDCORREL,MNUNOM,MNUDES,MNUCHEK,MNUACTI,MENUEST,MNUORDEN,ifnull(ROL_VSIBL,0) AS MNUPERMISO " +
                    " FROM REST_MENUSTRING M " +
                    "LEFT JOIN REST_ROL_PERMISO P ON  M.IDCORREL=P.ROL_FCION AND P.ROL_NOMBRE=" +ROL_NOMBRE ;
            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lisMenu = new ArrayList<Menu_StringBE>();
            lisMenu.clear();
            if (cursor.moveToFirst()) {
                do {
                    menu_stringBE = new Menu_StringBE();
                    menu_stringBE.setIDCORREL(Funciones.isNullColumn(cursor,"IDCORREL",0));
                    menu_stringBE.setMNUNOM(Funciones.isNullColumn(cursor,"MNUNOM",""));
                    menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"MNUDES",""));
                    menu_stringBE.setMNUCHEK(Funciones.isNullColumn(cursor,"MNUCHEK",0));
                    menu_stringBE.setMNUACTI(Funciones.isNullColumn(cursor,"MNUACTI",0));
                    menu_stringBE.setMENUEST(Funciones.isNullColumn(cursor,"MENUEST",0));
                    menu_stringBE.setMNUORDEN(Funciones.isNullColumn(cursor,"MNUORDEN",0));
                    menu_stringBE.setMNUPERMISO((Funciones.isNullColumn(cursor,"MNUPERMISO",0) == 1 ? true: false));
                    lisMenu.add(menu_stringBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void getallBy(String iEmpresa,String sPerfil) {
        Cursor cursor = null;
        Menu_StringBE menu_stringBE = null;
        Integer iOpcion1=0,iOpcion2=0,iOpcion3=0,iOpcion4=0,iOpcion5=0;
        try {
            lisMenu = new ArrayList<Menu_StringBE>();
            lisMenu.clear();
            String SQL = "SELECT DISTINCT A.C_MENU,A.NOMBRE_MENU,A.NIVEL,A.COLUMNA,A.PARIENTE,\n" +
                    "   A.ORDEN,A.FORMULARIO,A.TIPO_FORMULARIO,A.RUTA_IMAGEN,\n" +
                    "   A.ESTADO,C.ICONO,A.ID_EMPRESA\n" +
                    "  FROM S_SEM_MENU A \n" +
                    "  INNER JOIN S_SEA_ACCESOS C ON(A.C_MENU = C.C_MENU) \n" +
                    "  INNER JOIN   S_SEM_PERFIL B ON(C.C_PERFIL = B.C_PERFIL)\n" +
                    " WHERE B.C_PERFIL = '"+ sPerfil + "' \n" +
                    " AND C.VISIBLE = 1   \n" +
                    " AND A.ESTADO = 40001\n" +
                    " AND C.ESTADO = 40001\n" +
                    " AND B.ESTADO = 40001\n" +
                    " AND A.TIPO_FORMULARIO = 360003 " +
                    " AND C.VISIBLE <> '0'" +
                    " order by A.ORDEN ";
            lisMenu = new ArrayList<Menu_StringBE>();
            lisMenu.clear();
            cursor = DataBaseHelper.myDataBase.rawQuery(SQL, null);
            String sNOMBRE_MENU="";
            if (cursor.moveToFirst()) {
                do {
                    sNOMBRE_MENU=Funciones.isNullColumn(cursor,"FORMULARIO","");
                    switch (sNOMBRE_MENU.trim()) {
/*                        case "SMNU_EMPRESA":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(1);
                            menu_stringBE.setMNUNOM("SMNU_EMPRESA");
                            menu_stringBE.setMNUDES("EMPRESA");
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(0);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                          //  lisMenu.add(menu_stringBE);
                            break;
*/
                        case "SMNU_SINCRONIZAR":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(2);
                            menu_stringBE.setMNUNOM("SMNU_SINCRONIZAR");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/sincronizar_small");
                            lisMenu.add(menu_stringBE);
                            break;

    /*                    case "SMNU_CLIENTES":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(3);
                            menu_stringBE.setMNUNOM("SMNU_CLIENTES");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/cobranza_small");
                            lisMenu.add(menu_stringBE);
                            break;
*/
  /*                      case "SMNU_PROCESOS":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(4);
                            menu_stringBE.setMNUNOM("SMNU_PROCESOS");
                            menu_stringBE.setMNUDES("PROCESOS");
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(0);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                         //   lisMenu.add(menu_stringBE);
                        break;
*/
                       case "SMNU_COBRANZAS":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(5);
                            menu_stringBE.setMNUNOM("SMNU_COBRANZAS");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/cobranza_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_LINQCOBRANZA":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(6);
                            menu_stringBE.setMNUNOM("SMNU_LINQCOBRANZA");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/liquidacion_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_APROBACIONPLA":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(7);
                            menu_stringBE.setMNUNOM("SMNU_APROBACIONPLA");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/aprobacion_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_REPORTES":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(8);
                            menu_stringBE.setMNUNOM("SMNU_REPORTES");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/reporte_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_CAMBIO":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(9);
                            menu_stringBE.setMNUNOM("SMNU_CAMBIO");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/change_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_CERRAR":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(10);
                            menu_stringBE.setMNUNOM("SMNU_CERRAR");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/logout_small");
                            lisMenu.add(menu_stringBE);
                            break;

                        case "SMNU_RECORRIDO":
                            menu_stringBE = new Menu_StringBE();
                            menu_stringBE.setIDCORREL(10);
                            menu_stringBE.setMNUNOM("SMNU_RECORRIDO");
                            menu_stringBE.setMNUDES(Funciones.isNullColumn(cursor,"NOMBRE_MENU",""));
                            menu_stringBE.setMNUCHEK(0);
                            menu_stringBE.setMNUACTI(1);
                            menu_stringBE.setMENUEST(1);
                            menu_stringBE.setMNUORDEN(1);
                            menu_stringBE.setMNUPERMISO(true);
                            menu_stringBE.setRUTA_ICONO("@drawable/logout_small");
                            lisMenu.add(menu_stringBE);
                            break;
                    }

                } while (cursor.moveToNext());
            }

            //Agregamos la opcion Cambiar Sede y Local
        /*    menu_stringBE = new Menu_StringBE();
            menu_stringBE.setIDCORREL(100);
            menu_stringBE.setMNUNOM("SMNU_LOCAL");
            menu_stringBE.setMNUDES("Cambiar empresa o local");
            menu_stringBE.setMNUCHEK(0);
            menu_stringBE.setMNUACTI(1);
            menu_stringBE.setMENUEST(1);
            menu_stringBE.setMNUORDEN(100);
            menu_stringBE.setMNUPERMISO(true);
            menu_stringBE.setRUTA_ICONO("@drawable/change_small");
            lisMenu.add(menu_stringBE);




            //Agregamos la opcion de cerrar sesión
            menu_stringBE = new Menu_StringBE();
            menu_stringBE.setIDCORREL(101);
            menu_stringBE.setMNUNOM("SMNU_CERRAR");
            menu_stringBE.setMNUDES("Cerrar Sesión");
            menu_stringBE.setMNUCHEK(0);
            menu_stringBE.setMNUACTI(0);
            menu_stringBE.setMENUEST(1);
            menu_stringBE.setMNUORDEN(101);
            menu_stringBE.setRUTA_ICONO("@drawable/logout_small");
            menu_stringBE.setMNUPERMISO(true);
            lisMenu.add(menu_stringBE);*/


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

}

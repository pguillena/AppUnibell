package pe.com.app.unibell.appunibell.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.math.BigDecimal;
import java.util.ArrayList;
import pe.com.app.unibell.appunibell.BE.Documentos_Cobra_DetBE;
import pe.com.app.unibell.appunibell.BE.DocuventBE;
import pe.com.app.unibell.appunibell.BE.ParTablaBE;
import pe.com.app.unibell.appunibell.BL.Documentos_Cobra_CabBL;
import pe.com.app.unibell.appunibell.Util.Funciones;

public class Documentos_Cobra_DetDAO {
    public ArrayList<Documentos_Cobra_DetBE> lst = null;
    Funciones funciones = new Funciones();

    public void getByID_Cobranza(String pID_COBRANZA) {
        Cursor cursor = null;
        Documentos_Cobra_DetBE documentos_cobra_detBE = null;
        String SQL="";
        try {

            SQL="SELECT " +
                    "A.ID_COBRANZA, A.FPAGO, A.TIPDOC, A.SERIE_NUM, A.NUMERO, A.IMPORTE,\n" +
                    "A.MONEDA, (A.SALDO_INICIAL-A.M_COBRANZA) as SALDO, A.M_COBRANZA, A.ID_EMPRESA, A.ID_LOCAL, A.ESTADO,\n" +
                    "A.FECHA_REGISTRO, A.FECHA_MODIFICACION, A.USUARIO_REGISTRO,\n" +
                    "A.USUARIO_MODIFICACION,A.PC_REGISTRO,A.PC_MODIFICACION,A.IP_REGISTRO,A.IP_MODIFICACION ,\n" +
                    "A.ID_VENDEDOR, A.SALDO_INICIAL,TA.ABREVIADA AS NOMBRETIPODOC,\n" +
                    "CASE WHEN A.TIPDOC='A1' THEN CAB.FECHA ELSE FAC.FECHA END FECHA,\n" +
                    "CASE WHEN A.TIPDOC='A1' THEN CAB.FECHA ELSE FAC.F_VENCTO END F_VENCTO,\n" +
                    "FAC.TIPDOC AS TIPDOCFAC,FAC.SERIE_NUM AS SERIE_NUMFAC,FAC.NUMERO AS NUMEROFAC,IFNULL(FAC.DIAS,0) AS DIAS "+
                    "FROM S_CCM_DOCUMENTOS_COBRA_CAB CAB \n" +
                    "INNER JOIN S_CCM_DOCUMENTOS_COBRA_DET A ON(CAB.ID_COBRANZA = A.ID_COBRANZA) \n" +
                    "LEFT JOIN TABLAS_AUXILIARES TA ON(A.TIPDOC = TA.CODIGO AND TA.TIPO = 2) \n" +
                    "LEFT JOIN FACTCOB FAC ON(A.TIPDOC = FAC.TIPDOC AND A.SERIE_NUM = FAC.SERIE_NUM AND A.NUMERO = FAC.NUMERO)" +
                    "WHERE A.ID_COBRANZA=" + pID_COBRANZA + " ORDER BY IFNULL(A.M_COBRANZA,0) ";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_DetBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_detBE = new Documentos_Cobra_DetBE();
                    documentos_cobra_detBE.setID_COBRANZA(Funciones.isNullColumn(cursor,"ID_COBRANZA",0));
                    documentos_cobra_detBE.setFPAGO(Funciones.isNullColumn(cursor,"FPAGO",""));
                    documentos_cobra_detBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC",""));
                    documentos_cobra_detBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM",""));
                    documentos_cobra_detBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    documentos_cobra_detBE.setIMPORTE(Funciones.isNullColumn(cursor,"IMPORTE",0.0));
                    documentos_cobra_detBE.setMONEDA(Funciones.isNullColumn(cursor,"MONEDA",""));
                    documentos_cobra_detBE.setSALDO(Funciones.isNullColumn(cursor,"SALDO",0.0));
                    documentos_cobra_detBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    documentos_cobra_detBE.setID_EMPRESA(Funciones.isNullColumn(cursor,"ID_EMPRESA",0));
                    documentos_cobra_detBE.setID_LOCAL(Funciones.isNullColumn(cursor,"ID_LOCAL",0));
                    documentos_cobra_detBE.setESTADO(Funciones.isNullColumn(cursor,"ESTADO",0));
                    documentos_cobra_detBE.setFECHA_REGISTRO(Funciones.isNullColumn(cursor,"FECHA_REGISTRO",""));
                    documentos_cobra_detBE.setFECHA_MODIFICACION(Funciones.isNullColumn(cursor,"FECHA_MODIFICACION",""));
                    documentos_cobra_detBE.setUSUARIO_REGISTRO(Funciones.isNullColumn(cursor,"USUARIO_REGISTRO",""));
                    documentos_cobra_detBE.setUSUARIO_MODIFICACION(Funciones.isNullColumn(cursor,"USUARIO_MODIFICACION",""));
                    documentos_cobra_detBE.setPC_REGISTRO(Funciones.isNullColumn(cursor,"PC_REGISTRO",""));
                    documentos_cobra_detBE.setPC_MODIFICACION(Funciones.isNullColumn(cursor,"PC_MODIFICACION",""));
                    documentos_cobra_detBE.setIP_REGISTRO(Funciones.isNullColumn(cursor,"IP_REGISTRO",""));
                    documentos_cobra_detBE.setIP_MODIFICACION(Funciones.isNullColumn(cursor,"IP_MODIFICACION",""));
                    documentos_cobra_detBE.setID_VENDEDOR(Funciones.isNullColumn(cursor,"ID_VENDEDOR",0));
                    documentos_cobra_detBE.setSALDO_INICIAL(Funciones.isNullColumn(cursor,"SALDO_INICIAL",0.0));
                    documentos_cobra_detBE.setNOMBRETIPODOC(Funciones.isNullColumn(cursor,"NOMBRETIPODOC",""));
                    documentos_cobra_detBE.setFECHA(Funciones.isNullColumn(cursor,"FECHA",""));
                    documentos_cobra_detBE.setF_VENCTO(Funciones.isNullColumn(cursor,"F_VENCTO",""));
                    documentos_cobra_detBE.setDIAS(Funciones.isNullColumn(cursor,"DIAS",0));
                    lst.add(documentos_cobra_detBE);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }



    //SALDO POR COBRANZA-CABECERA
    public Double SaldoCobranza_By_ID_Cobranza_Cabecera(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        try{
            //SALDO TOTAL DE COBRANZA
            String SQL1="SELECT " +
                    " CASE WHEN IFNULL(M_COBRANZA_D,0)>0 THEN  " +
                    " (IFNULL(M_COBRANZA_D,0) * IFNULL(T_CAMBIO_TIENDA,0)) ELSE IFNULL(M_COBRANZA,0) END AS M_COBRANZA FROM S_CCM_DOCUMENTOS_COBRA_CAB " +
                    " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString();

            Cursor cursor1 = null;
            cursor1= DataBaseHelper.myDataBase.rawQuery(SQL1, null);
            Double M_COBRANZA=0.0;

                if (cursor1.moveToFirst()) {
                    do {
                        dValor= Funciones.isNullColumn(cursor1,"M_COBRANZA",0.0);
                    } while (cursor1.moveToNext());
                }
            } catch (Exception ex) {
            dValor=-1.0;
            }
        return dValor;
    }
   //MONTO COBRADO X COBRANZA-TEMPORAL
    public Double MontoCobrado_By_ID_Cobranza_Temporal(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        //ObTenemos el Total Cobrado
        String SQL2="SELECT IFNULL(SUM(COBRANZA),0) AS COBRANZA FROM FACTCOBTEMP " +
                " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString();
        Cursor cursor2 = null;
        cursor2= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
        try{
            if (cursor2.moveToFirst()) {
                do {
                    dValor= Funciones.isNullColumn(cursor2,"COBRANZA",0.0);
                } while (cursor2.moveToNext());
            }
        } catch (Exception ex) {
            dValor=0.0;
        }

        return dValor;
    }

    //SALDO x DOCUMENTO DETALLE
    public Double SaldoCobranza_By_Documento_Detalle(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        //ObTenemos el Total Cobrado
        String SQL1="SELECT IFNULL(SUM(SALDO),0) AS SALDO FROM S_CCM_DOCUMENTOS_COBRA_DET " +
                " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString() +
                " AND TIPDOC='"+documentos_cobra_detBE.getTIPDOC().toString()+ "'" +
                " AND SERIE_NUM='"+documentos_cobra_detBE.getSERIE_NUM().toString()+ "'" +
                " AND NUMERO="+ documentos_cobra_detBE.getNUMERO().toString() ;

        Cursor cursor1 = null;
        cursor1= DataBaseHelper.myDataBase.rawQuery(SQL1, null);
        try{
            if (cursor1.moveToFirst()) {
                do {
                    dValor= Funciones.isNullColumn(cursor1,"SALDO",0.0);
                } while (cursor1.moveToNext());
            }
        } catch (Exception ex) {
            dValor=0.0;
        }
        return dValor;
    }

    //MONTO COBRADO X Documento -DETALLE
    public Double MontoCobrado_By_Documento_Detalle(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        try{
            String SQL2="SELECT IFNULL(SUM(M_COBRANZA),0) AS COBRANZA FROM S_CCM_DOCUMENTOS_COBRA_DET " +
                    " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString() +
                    " AND TIPDOC='"+documentos_cobra_detBE.getTIPDOC().toString()+ "'" +
                    " AND SERIE_NUM='"+documentos_cobra_detBE.getSERIE_NUM().toString()+ "'" +
                    " AND NUMERO="+ documentos_cobra_detBE.getNUMERO().toString() ;

            Cursor cursor2 = null;
            cursor2= DataBaseHelper.myDataBase.rawQuery(SQL2, null);

            if (cursor2.moveToFirst()) {
                do {
                    dValor= Funciones.isNullColumn(cursor2,"COBRANZA",0.0);
                } while (cursor2.moveToNext());
            }
        } catch (Exception ex) {
            dValor=0.0;
        }
        return dValor;
    }

   //MONTO COBRADO X COBRANZA -DETALLE
    public Double MontoCobrado_By_ID_Cobranza_Detalle(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        //ObTenemos el Total Cobrado
        String SQL2="SELECT IFNULL(SUM(M_COBRANZA),0) AS COBRANZA FROM S_CCM_DOCUMENTOS_COBRA_DET " +
                " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString();
        Cursor cursor2 = null;
        cursor2= DataBaseHelper.myDataBase.rawQuery(SQL2, null);
        try{
            if (cursor2.moveToFirst()) {
                do {
                    dValor= Funciones.isNullColumn(cursor2,"COBRANZA",0.0);
                } while (cursor2.moveToNext());
            }
        } catch (Exception ex) {
            dValor=0.0;
        }

        return dValor;
    }



    //SALDO X DOCUEMNTO -TEMPORAL
    public Double Saldo_By_Documento_Temporal(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
            //ObTenemos el Total Cobrado
            String SQL1="SELECT IFNULL(SUM(SALDO),0) AS SALDO FROM FACTCOBTEMP " +
                    " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString() +
                    " AND TIPDOC='"+documentos_cobra_detBE.getTIPDOC().toString()+ "'" +
                    " AND SERIE_NUM='"+documentos_cobra_detBE.getSERIE_NUM().toString()+ "'" +
                    " AND NUMERO="+ documentos_cobra_detBE.getNUMERO().toString() ;

            Cursor cursor1 = null;
            cursor1= DataBaseHelper.myDataBase.rawQuery(SQL1, null);
            try{
                if (cursor1.moveToFirst()) {
                    do {
                        dValor= Funciones.isNullColumn(cursor1,"SALDO",0.0);
                    } while (cursor1.moveToNext());
                }
            } catch (Exception ex) {
                dValor=0.0;
            }
        return dValor;
    }

    //MONTO COBRADO X DOCUMENTO -TEMPORAL
    public Double MontoCobradoBy_Documento_Temporal(Documentos_Cobra_DetBE documentos_cobra_detBE){
        Double dValor=0.0;
        try{
            String SQL2="SELECT IFNULL(SUM(COBRANZA),0) AS COBRANZA FROM FACTCOBTEMP " +
                    " WHERE ID_COBRANZA=" + documentos_cobra_detBE.getID_COBRANZA().toString() +
                    " AND TIPDOC='"+documentos_cobra_detBE.getTIPDOC().toString()+ "'" +
                    " AND SERIE_NUM='"+documentos_cobra_detBE.getSERIE_NUM().toString()+ "'" +
                    " AND NUMERO="+ documentos_cobra_detBE.getNUMERO().toString() ;

            Cursor cursor2 = null;
            cursor2= DataBaseHelper.myDataBase.rawQuery(SQL2, null);

            if (cursor2.moveToFirst()) {
                do {
                    dValor= Funciones.isNullColumn(cursor2,"COBRANZA",0.0);
                } while (cursor2.moveToNext());
            }
        } catch (Exception ex) {
            dValor=0.0;
        }

        return dValor;
    }


    public String insert(Documentos_Cobra_DetBE documentos_cobra_detBE){
        String sMensaje="";
        Integer iError=0;
        //SALDO CABECERA,TOTAL COBRADO,SALDO CABECERA ACTUAL
        Double dSALDOCAB=0.0, dCOBRANZA=0.0,dSALDOCABACT=0.0;

        try {
            //OBTENEMOS EL SALDO DE LA CABECERA DE LA COBRANZA
            dSALDOCAB = SaldoCobranza_By_ID_Cobranza_Cabecera(documentos_cobra_detBE);
            //OBTENEMOS EL TOTAL COBRADO
            dCOBRANZA = MontoCobrado_By_ID_Cobranza_Temporal(documentos_cobra_detBE);

            //ANTICIPO
            if(!documentos_cobra_detBE.getTIPDOC().toString().equals("A1")) {
            //dSALDOCABACT = (dSALDOCAB - (dCOBRANZA + Double.valueOf(documentos_cobra_detBE.getM_COBRANZA().toString())));

                dSALDOCABACT = Double.valueOf(new Funciones().restar(new Funciones().restar(dSALDOCAB , dCOBRANZA), documentos_cobra_detBE.getM_COBRANZA()));

            if (dSALDOCABACT < 0) {
                sMensaje = "Valor a amortizar es mayor al saldo ingresado.";
                iError = 1;
            }

            if (Double.valueOf(documentos_cobra_detBE.getM_COBRANZA().toString()) > Double.valueOf(documentos_cobra_detBE.getSALDO().toString())) {
                sMensaje = "Valor a amortizar es mayor al saldo del documento.";
                iError = 2;
            }
           }

            if( iError==0) {
                DataBaseHelper.myDataBase.beginTransaction();
                ContentValues cv = new ContentValues();
                cv.put("ID_COBRANZA", documentos_cobra_detBE.getID_COBRANZA());
                cv.put("FPAGO", documentos_cobra_detBE.getFPAGO());
                cv.put("TIPDOC", documentos_cobra_detBE.getTIPDOC());
                cv.put("SERIE_NUM", documentos_cobra_detBE.getSERIE_NUM());
                cv.put("NUMERO", documentos_cobra_detBE.getNUMERO());
                cv.put("IMPORTE", documentos_cobra_detBE.getIMPORTE().toString());
                cv.put("MONEDA", documentos_cobra_detBE.getMONEDA().toString());
                cv.put("SALDO", documentos_cobra_detBE.getSALDO().toString());
                cv.put("M_COBRANZA", documentos_cobra_detBE.getM_COBRANZA().toString());
                cv.put("ID_EMPRESA", documentos_cobra_detBE.getID_EMPRESA().toString());
                cv.put("ID_LOCAL", documentos_cobra_detBE.getID_LOCAL().toString());
                cv.put("ESTADO", documentos_cobra_detBE.getESTADO().toString());
                cv.put("FECHA_REGISTRO", documentos_cobra_detBE.getFECHA_REGISTRO());
                cv.put("FECHA_MODIFICACION", documentos_cobra_detBE.getFECHA_MODIFICACION());
                cv.put("USUARIO_REGISTRO", documentos_cobra_detBE.getUSUARIO_REGISTRO());
                cv.put("USUARIO_MODIFICACION", documentos_cobra_detBE.getUSUARIO_MODIFICACION());
                cv.put("PC_REGISTRO", documentos_cobra_detBE.getPC_REGISTRO());
                cv.put("PC_MODIFICACION", documentos_cobra_detBE.getPC_MODIFICACION());
                cv.put("IP_REGISTRO", documentos_cobra_detBE.getIP_REGISTRO());
                cv.put("IP_MODIFICACION", documentos_cobra_detBE.getIP_MODIFICACION());
                cv.put("ID_VENDEDOR ", documentos_cobra_detBE.getID_VENDEDOR());
                cv.put("SALDO_INICIAL", documentos_cobra_detBE.getSALDO_INICIAL().toString());
                cv.put("CODUNC_LOCAL", documentos_cobra_detBE.getCODUNC_LOCAL().toString());
                cv.put("GUARDADO", "0");
                cv.put("SINCRONIZADO", "0");
                DataBaseHelper.myDataBase.insert("S_CCM_DOCUMENTOS_COBRA_DET", null, cv);

                Double dM_COBRANZA = Double.valueOf(documentos_cobra_detBE.getM_COBRANZA().toString());
                Double dSALDO= funciones.restar( Saldo_By_Documento_Temporal(documentos_cobra_detBE), dM_COBRANZA);

                //ACTUALIZAMOS EL SALDO EN EL DETALLE
                //SI ES DIFERENTE A ANTICIPO
                if(!documentos_cobra_detBE.getTIPDOC().toString().equals("A1")) {
                    ContentValues cv2 = new ContentValues();
                    cv2.put("SALDO", Funciones.FormatDecimal(dSALDO.toString()));
                    cv2.put("COBRANZA ", Funciones.FormatDecimal(dM_COBRANZA.toString()));
                    DataBaseHelper.myDataBase.update("FACTCOBTEMP", cv2, "ID_COBRANZA=? AND TIPDOC=? AND SERIE_NUM=? AND NUMERO=?",
                            new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA().toString()),
                                    documentos_cobra_detBE.getTIPDOC().toString(),
                                    documentos_cobra_detBE.getSERIE_NUM().toString(),
                                    documentos_cobra_detBE.getNUMERO().toString()});

                }
                //ACTUALIZAMOS EL SALDO EN LA CABECERA
                ContentValues cv4 = new ContentValues();
                cv4.put("SALDO",Funciones.FormatDecimal(dSALDOCABACT.toString()));
                DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB", cv4, "ID_COBRANZA=?",
                        new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA().toString())});

                DataBaseHelper.myDataBase.setTransactionSuccessful();
                DataBaseHelper.myDataBase.endTransaction();
                sMensaje="";
            }

        }catch (Exception ex){
            if (iError==0){
                DataBaseHelper.myDataBase.endTransaction();
            }
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }finally {
            iError=0;
        }
        return sMensaje;
    }

    public String update(Documentos_Cobra_DetBE documentos_cobra_detBE){
        String sMensaje="";
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID_COBRANZA",documentos_cobra_detBE.getID_COBRANZA());
            cv.put("FPAGO",documentos_cobra_detBE.getFPAGO());
            cv.put("TIPDOC",documentos_cobra_detBE.getTIPDOC());
            cv.put("SERIE_NUM",documentos_cobra_detBE.getSERIE_NUM());
            cv.put("NUMERO",documentos_cobra_detBE.getNUMERO());
            cv.put("IMPORTE",documentos_cobra_detBE.getIMPORTE().toString());
            cv.put("MONEDA",documentos_cobra_detBE.getMONEDA().toString());
            cv.put("SALDO",documentos_cobra_detBE.getSALDO().toString());
            cv.put("M_COBRANZA",documentos_cobra_detBE.getM_COBRANZA().toString());
            cv.put("ID_EMPRESA",documentos_cobra_detBE.getID_EMPRESA().toString());
            cv.put("ID_LOCAL",documentos_cobra_detBE.getID_LOCAL().toString());
            cv.put("ESTADO",documentos_cobra_detBE.getESTADO().toString());
            cv.put("FECHA_REGISTRO",documentos_cobra_detBE.getFECHA_REGISTRO());
            cv.put("FECHA_MODIFICACION",documentos_cobra_detBE.getFECHA_MODIFICACION());
            cv.put("USUARIO_REGISTRO",documentos_cobra_detBE.getUSUARIO_REGISTRO());
            cv.put("USUARIO_MODIFICACION",documentos_cobra_detBE.getUSUARIO_MODIFICACION());
            cv.put("PC_REGISTRO",documentos_cobra_detBE.getPC_REGISTRO());
            cv.put("PC_MODIFICACION",documentos_cobra_detBE.getPC_MODIFICACION());
            cv.put("IP_REGISTRO",documentos_cobra_detBE.getIP_REGISTRO());
            cv.put("IP_MODIFICACION",documentos_cobra_detBE.getIP_MODIFICACION());
            cv.put("ID_VENDEDOR ",documentos_cobra_detBE.getID_VENDEDOR());
            cv.put("SALDO_INICIAL",documentos_cobra_detBE.getSALDO_INICIAL().toString());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_DET",cv,"ID_COBRANZA = ?",
                    new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA())});
            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }

    public String EditDetalle(Documentos_Cobra_DetBE documentos_cobra_detBE,Integer iOpcion){
        String sMensaje="";
        Double M_COBRANZA=0.0,COBRANZA=0.0,SALDO=0.0,SALDOREAL=0.0;
        try{
            //Obtenemos El Saldo por documento
            SALDO=SaldoCobranza_By_Documento_Detalle(documentos_cobra_detBE);
            //Obtenemos la cobranza por documento
            COBRANZA=MontoCobrado_By_Documento_Detalle(documentos_cobra_detBE);
            //Sumamos el Saldo Real

            ContentValues cv = new ContentValues();
            cv.put("ID_COBRANZA",documentos_cobra_detBE.getID_COBRANZA());
            cv.put("SERIE_NUM",documentos_cobra_detBE.getSERIE_NUM());
            cv.put("NUMERO",documentos_cobra_detBE.getNUMERO());

            if(iOpcion==1) {
                SALDOREAL= SALDO - documentos_cobra_detBE.getM_COBRANZA();
                cv.put("SALDO",SALDOREAL);
                cv.put("M_COBRANZA", documentos_cobra_detBE.getM_COBRANZA().toString());
            }else{
                SALDOREAL= SALDO + COBRANZA;
                cv.put("SALDO",SALDOREAL);
                cv.put("M_COBRANZA",0.0);
            }
            cv.put("ESTADO",documentos_cobra_detBE.getESTADO().toString());
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_DET",cv,"ID_COBRANZA=? AND TIPDOC=? AND SERIE_NUM=? AND NUMERO=?",
                    new String[]{String.valueOf(
                            documentos_cobra_detBE.getID_COBRANZA().toString()),
                            documentos_cobra_detBE.getTIPDOC().toString(),
                            documentos_cobra_detBE.getSERIE_NUM().toString(),
                            documentos_cobra_detBE.getNUMERO().toString()});

            //Optenemos el Total Agregado
            M_COBRANZA=SaldoCobranza_By_ID_Cobranza_Cabecera(documentos_cobra_detBE);
            //ObTenemos el Total Cobrado
            COBRANZA=0.0;
            COBRANZA=MontoCobrado_By_ID_Cobranza_Detalle(documentos_cobra_detBE);

            Double dSaldoCab=0.0;
            dSaldoCab= (M_COBRANZA-COBRANZA);

            ContentValues cv3 = new ContentValues();
            cv3.put("SALDO", Funciones.FormatDecimal(dSaldoCab.toString()));
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB", cv3, "ID_COBRANZA=?",
                    new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA().toString())});

            sMensaje="";
        }catch (Exception ex){
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }


    public String delete(Documentos_Cobra_DetBE documentos_cobra_detBE){
        String sMensaje="";
        Double SALDO=0.0,COBRANZA=0.0;
        try{
            //Obtenemos El Saldo por documento
            SALDO=Saldo_By_Documento_Temporal(documentos_cobra_detBE);
            //Obtenemos la cobranza por documento
            COBRANZA=MontoCobradoBy_Documento_Temporal(documentos_cobra_detBE);
            //Sumamos el Saldo Real
            Double SALDOREAL= SALDO + COBRANZA;

            DataBaseHelper.myDataBase.beginTransaction();

            //SI ES ANTICIPO ENTONCES ELIMINAMOS
            if(documentos_cobra_detBE.getTIPDOC().equals("A1")) {
                DataBaseHelper.myDataBase.delete("FACTCOBTEMP",
                        "ID_COBRANZA=? AND TIPDOC=? AND SERIE_NUM=? AND NUMERO=?",
                        new String[]{String.valueOf(
                                documentos_cobra_detBE.getID_COBRANZA().toString()),
                                documentos_cobra_detBE.getTIPDOC().toString(),
                                documentos_cobra_detBE.getSERIE_NUM().toString(),
                                documentos_cobra_detBE.getNUMERO().toString()});
            }else{
                ContentValues cv2 = new ContentValues();
                cv2.put("SALDO", SALDOREAL.toString());
                cv2.put("COBRANZA ", "0");
                DataBaseHelper.myDataBase.update("FACTCOBTEMP", cv2, "ID_COBRANZA=? AND TIPDOC=? AND SERIE_NUM=? AND NUMERO=?",
                        new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA().toString()),
                                documentos_cobra_detBE.getTIPDOC().toString(),
                                documentos_cobra_detBE.getSERIE_NUM().toString(),
                                documentos_cobra_detBE.getNUMERO().toString()});
            }

            DataBaseHelper.myDataBase.delete("S_CCM_DOCUMENTOS_COBRA_DET",
                                       "ID_COBRANZA=? AND TIPDOC=? AND SERIE_NUM=? AND NUMERO=?",
                    new String[]{String.valueOf(
                            documentos_cobra_detBE.getID_COBRANZA().toString()),
                            documentos_cobra_detBE.getTIPDOC().toString(),
                            documentos_cobra_detBE.getSERIE_NUM().toString(),
                            documentos_cobra_detBE.getNUMERO().toString()});

            //ACTUALIZMAOS EL SALDO EN LA CABECERA
            //Optenemos el Total Agregado
            Double M_COBRANZA=0.0;
            M_COBRANZA=SaldoCobranza_By_ID_Cobranza_Cabecera(documentos_cobra_detBE);

            //ObTenemos el Total Cobrado
            COBRANZA=0.0;
            COBRANZA=MontoCobrado_By_ID_Cobranza_Temporal(documentos_cobra_detBE);

            Double dSaldoCab=0.0;
            dSaldoCab= (M_COBRANZA-COBRANZA);
            ContentValues cv3 = new ContentValues();
            cv3.put("SALDO", Funciones.FormatDecimal(dSaldoCab.toString()));
            DataBaseHelper.myDataBase.update("S_CCM_DOCUMENTOS_COBRA_CAB", cv3, "ID_COBRANZA=?",
                    new String[]{String.valueOf(documentos_cobra_detBE.getID_COBRANZA().toString())});

            DataBaseHelper.myDataBase.setTransactionSuccessful();
            DataBaseHelper.myDataBase.endTransaction();
            sMensaje="";
        }catch (Exception ex){
            DataBaseHelper.myDataBase.endTransaction();
            sMensaje="Error:" + ex.getMessage().toString();
            ex.printStackTrace();
        }
        return sMensaje;
    }



    public void getByMontosRegistrados(String pCodCliente, String pIdCobranza) {
        Cursor cursor = null;
        Documentos_Cobra_DetBE documentos_cobra_detBE = null;
        String SQL="";
        try {

            SQL="SELECT D.TIPDOC, D.SERIE_NUM, D.NUMERO, SUM(D.M_COBRANZA) AS M_COBRANZA \n" +
                    "  FROM S_CCM_DOCUMENTOS_COBRA_CAB C \n" +
                    " INNER JOIN S_CCM_DOCUMENTOS_COBRA_DET D \n" +
                    "    ON (C.ID_COBRANZA = D.ID_COBRANZA) \n" +
                    " WHERE  C.ESTADO IN (40003, 40007) \n" +
                    "   AND C.COD_CLIENTE = '"+pCodCliente+"' \n" +
                    "   AND D.TIPDOC IN('01','03') \n" +
                    "   AND C.ID_COBRANZA <> "+pIdCobranza+" \n" +
                    " GROUP BY D.TIPDOC, D.SERIE_NUM, D.NUMERO";

            cursor= DataBaseHelper.myDataBase.rawQuery(SQL, null);
            lst = new ArrayList<Documentos_Cobra_DetBE>();
            lst.clear();
            if (cursor.moveToFirst()) {
                do {
                    documentos_cobra_detBE = new Documentos_Cobra_DetBE();
                    documentos_cobra_detBE.setTIPDOC(Funciones.isNullColumn(cursor,"TIPDOC",""));
                    documentos_cobra_detBE.setSERIE_NUM(Funciones.isNullColumn(cursor,"SERIE_NUM",""));
                    documentos_cobra_detBE.setNUMERO(Funciones.isNullColumn(cursor,"NUMERO",""));
                    documentos_cobra_detBE.setM_COBRANZA(Funciones.isNullColumn(cursor,"M_COBRANZA",0.0));
                    lst.add(documentos_cobra_detBE);
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

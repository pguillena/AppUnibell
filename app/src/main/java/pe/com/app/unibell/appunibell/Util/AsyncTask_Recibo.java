package pe.com.app.unibell.appunibell.Util;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;


import pe.com.app.unibell.appunibell.BL.FactCobBL;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;

import static android.content.Context.MODE_PRIVATE;

public class AsyncTask_Recibo extends AsyncTask<String,String,Boolean> {
    public Context context;
    ProgressDialog progressDialog;

    public AsyncTask_Recibo(Context context){
        this.context =context;
    }
    static String GENERADOS = "PDF";
    String nombre_completo="";
    Document document;
    String SOPCION="0";
    String CONTENIDO = "";


    private Documentos_Cobra_CabDAO documentos_cobra_cabDAO = new Documentos_Cobra_CabDAO();
    private Funciones funciones=new Funciones();

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;
    private FactCobBL factCobBL = new FactCobBL();

    @Override
    protected void onPreExecute() {
       /* progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Recibo"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);*/

    }


    @Override
    protected Boolean doInBackground(String... argumento) {
        //Parametros desde la actividad
        String NOMBRE_CARPETA_APP = argumento[0];
        String NOMBRE_ARCHIVO = argumento[1];
        String TITLE = argumento[2];
        SOPCION=argumento[3];
        //Fin Parametros
        try {
            String tarjetaSD = Environment.getExternalStorageDirectory().toString();
            File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);

            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }
            File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
            if (!pdfSubDir.exists()) {
                pdfSubDir.mkdir();
            }
            nombre_completo = Environment.getExternalStorageDirectory() +
                    File.separator + NOMBRE_CARPETA_APP +
                    File.separator + GENERADOS
                    + File.separator
                    + NOMBRE_ARCHIVO;

            File outputfile = new File(nombre_completo);
            if (outputfile.exists()) {
                outputfile.delete();
            }

            document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));

            document.open();
            document.addAuthor("Unibell");
            document.addCreator("Unibell");
            document.addSubject("Reportes");
            document.addCreationDate();
            document.addTitle(TITLE);
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            try {


                sharedSettings =context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                String iID_EMPRESA= sharedSettings.getString("iID_EMPRESA", "0").toString();
                String iID_LOCAL= sharedSettings.getString("iID_LOCAL", "0").toString();
                String iN_SERIE_RECIBO= sharedSettings.getString("REP_SER_RECIBO", "0").toString();
                String iN_RECIBO= sharedSettings.getString("REP_NUM_RECIBO", "").toString();
                String iCOD_CLIENTE = sharedSettings.getString("CODIGO_ANTIGUO", "").toString();

                documentos_cobra_cabDAO.getReciboElectronico(iID_EMPRESA,iID_LOCAL,iN_SERIE_RECIBO,iN_RECIBO);

                String consultaFactCobRest =  ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blfactcob_estado_cuenta + "/" +
                        iCOD_CLIENTE + "/" +
                        "XXX/" +
                        "XXX/" +
                        "XXX/" +
                        "1/" +
                        iID_EMPRESA;

                factCobBL.getEstadoCuenta(consultaFactCobRest);

                CONTENIDO=GeneraRecibo();

                worker.parseXHtml(pdfWriter, document, new StringReader(CONTENIDO));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean exist) {
        try {
            if(exist){
              /*  if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/

                DocumentoCreado(SOPCION,nombre_completo);

            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String GeneraRecibo(){
        String sHTML="";
        try{
            String htmToCab1="",htmToCab2="",htmToDet="",sResumen="",htmPie="";
            String htmlSolesCabecera="", htmlSolesTitulo="", htmlSolesDet="", htmlSolesFooter="";

            Bitmap bitMap=null;

            if(sharedSettings.getString("iID_EMPRESA", "0").toString().equals("1"))//Unibell
            {
                bitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo); // Default Unibell
            }
           else if(sharedSettings.getString("iID_EMPRESA", "0").toString().equals("2"))//Unibell
            {
                bitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_cosmotrade); //Colocar Logo Cosmotrade
            }
          else if(sharedSettings.getString("iID_EMPRESA", "0").toString().equals("9"))//Unibell
            {
                bitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_rmg); //Colocar Logo Cosmotrade
            }



            File mFile1 = Environment.getExternalStorageDirectory();

            String fileName ="img"+sharedSettings.getString("iID_EMPRESA", "0")+".jpg";

            File mFile2 = new File(mFile1,fileName);
            try {
                FileOutputStream outStream;

                outStream = new FileOutputStream(mFile2);
                bitMap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

                outStream.flush();

                outStream.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String sdPath = mFile1.getAbsolutePath().toString()+"/"+fileName;

            double totalSoles = 0.0;
            double totalDolares = 0.0;

            Double totalDeudor = 0.0;

            if(factCobBL.lst!=null && factCobBL.lst.size()>0)
            {
                for(int y = 0; y<factCobBL.lst.size(); y++)
                {
                    totalDeudor = funciones.sumar(totalDeudor, factCobBL.lst.get(y).getSALDO());
                }
            }

            for (int j = 0; j < documentos_cobra_cabDAO.lst.size(); j++) {

                totalSoles = funciones.sumar(totalSoles, documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA());
                totalDolares = funciones.sumar(totalDolares, documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA_D());
            }


                if(documentos_cobra_cabDAO.lst!=null && documentos_cobra_cabDAO.lst.size()>0) {
                String Splanilla = "";

                if(!documentos_cobra_cabDAO.lst.get(0).getPLANILLA().equals("") && !documentos_cobra_cabDAO.lst.get(0).getPLANILLA().trim().equals("-"))
                {
                    Splanilla = "Planilla de cobranza N° "+documentos_cobra_cabDAO.lst.get(0).getPLANILLA();
                }

                htmToCab1 = "<table width='100%'>" +
                        "<tr width='100%'>" +
                        "<td valign='top' width='25%'>" +
                        "<table width='100%'>" +
                        "<tr><td><img src='"+sdPath+"' /></td></tr>" +
                        "<tr><td style='font-size: 10px'>PRODUCTOS DE BELLEZA</td></tr>" +
                        "<tr><td style='font-size: 10px'>"+documentos_cobra_cabDAO.lst.get(0).getDIRECCION()+"</td></tr>" +
                        "<tr><td style='font-size: 10px'>TELEFONO. "+documentos_cobra_cabDAO.lst.get(0).getTELEFONO()+"</td></tr>" +
                        "</table>" +
                        "</td>" +
                        "<td valign='top' width='50%' style='font-size: 25px' align='center' >"+Splanilla+"</td>" +
                        "<td valign='top' width='25%'>" +
                        "<table width='100%' border='1' cellspacing='0' cellpadding='0' bordercolor='#000000'><tr><td>" +
                        "<table width='100%'>" +
                        "<tr><td align='center' style='font-size: 25px'>RUC. "+documentos_cobra_cabDAO.lst.get(0).getRUC()+"</td></tr>" +
                        "<tr><td align='center' style='font-size: 25px; font-weight: bold;'>RECIBO</td></tr>" +
                        "<tr><td align='center' style='font-size: 25px; color: #FF0000; font-weight: bold;'>"+documentos_cobra_cabDAO.lst.get(0).getN_SERIE_RECIBO() +"-" +documentos_cobra_cabDAO.lst.get(0).getN_RECIBO() +"</td></tr>" +
                        "</table>" +
                        "</td></tr></table>" +
                        "</td>" +
                        "</tr>" +
                        "<tr><td colspan='3'> <hr style='color: #b52e31;' size='10'/></td></tr>"+
                        "</table>";

                    if (totalDolares > 0)
                    {
                        htmlSolesCabecera = "<td width='5%'><b>CLIENTE</b></td><td><b>:</b></td><td width='60%'>"+ documentos_cobra_cabDAO.lst.get(0).getCOD_CLIENTE().toString() +" - "+ documentos_cobra_cabDAO.lst.get(0).getRAZON_SOCIAL().toString()+" </td><td width='15%'><b>TIPO CAMBIO</b></td><td><b>:</b></td><td width='15%'> S/ "+ funciones.FormatDecimal(String.valueOf(documentos_cobra_cabDAO.lst.get(0).getT_CAMBIO_TIENDA()).trim().replace(",","")) +"</td></tr> ";
                        htmlSolesTitulo = "<td width='6%'  align='center' style='font-size:10px;font-weight:bold'>CAMBIO</td>";

                    }
                    else if(totalSoles>0)
                    {

                        htmlSolesCabecera =   "<td width='5%'><b>CLIENTE</b></td><td><b>:</b></td><td width='60%'>"+ documentos_cobra_cabDAO.lst.get(0).getCOD_CLIENTE().toString() +" - "+ documentos_cobra_cabDAO.lst.get(0).getRAZON_SOCIAL().toString()+" </td><td width='15%'><b>DEUDA TOTAL</b></td><td><b>:</b></td><td width='15%'> S/ "+ funciones.FormatDecimal(totalDeudor.toString())  +"</td></tr> ";
                        htmlSolesTitulo = "";

                    }

                htmToCab2 =
                        "<html>" +
                                "<head>" +
                                "<body>" + htmToCab1 +
                                "<table width='100%' cellpadding='4px'>" +
                                "<tr width='100%'>" +
                                // "<td width='80%'  style='font-weight:bold'><b>COBRADOR:</b>" + documentos_cobra_cabDAO.lst.get(0).getNOMCOBRADOR().toString() + "</td><td width='20%'><b>FECHA:</b>" + documentos_cobra_cabDAO.lst.get(0).getFECHA().toString() + "</td></tr>" +
                                "<td width='5%' ><b>COBRADOR</b></td><td><b>:</b></td><td width='60%'>"+ documentos_cobra_cabDAO.lst.get(0).getNOMCOBRADOR().toString() +" </td><td width='15%'><b>FECHA</b></td><td><b>:</b></td><td width='15%'>"+documentos_cobra_cabDAO.lst.get(0).getFECHA_RECIBO().toString()+"</td></tr> "+
                                "<tr width='100%'>" +
                                //"<td width='80%'><b>CLIENTE:</b>" + documentos_cobra_cabDAO.lst.get(0).getRAZON_SOCIAL().toString() + "</td><td width='20%'><b>TIPO CAMBIO:</b>" + documentos_cobra_cabDAO.lst.get(0).getT_CAMBIO_TIENDA().toString() + "</td></tr>" +
                                htmlSolesCabecera+
                                "</table>" +
                                "<table width='100%' border='0.01'  cellpadding='5px' bordercolor='666633'>" +
                                "<tr><td width='2%' align='center' style='font-size:10px;font-weight:bold' >N°</td>" +
                                "<td width='24%' align='center' style='font-size:10px;font-weight:bold'>VENDEDOR</td>" +
                                "<td width='6%'  align='center' style='font-size:10px;font-weight:bold'>TIPO</td>" +
                                "<td width='8%'  align='center' style='font-size:10px;font-weight:bold'>DOC</td>" +
                                "<td width='8%'  align='center' style='font-size:10px;font-weight:bold'>FPAGO</td>" +
                                "<td width='18%' align='center' style='font-size:10px;font-weight:bold' >ENTIDAD</td>" +
                                "<td width='9%'  align='center' style='font-size:10px;font-weight:bold' >CONSTANCIA</td>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold' >FECHA</td>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold'>DEUDA</td>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold'>ABONO</td>" +
                                htmlSolesTitulo+
                                "<td width='6%'  align='center' style='font-size:10px;font-weight:bold'>SALDO</td>" +
                                "</tr>";

                Double dCobranza = 0.0;
                Double dCobranzaDolares = 0.0;
                for (int j = 0; j < documentos_cobra_cabDAO.lst.size(); j++) {
                    dCobranza += documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA();

                    Double cobranzaDolares = documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA() / Double.valueOf(documentos_cobra_cabDAO.lst.get(j).getT_CAMBIO_TIENDA());
                    dCobranzaDolares +=cobranzaDolares;


                    if(!htmlSolesTitulo.equals(""))
                    {
                        htmlSolesDet = "<td width='6%'  style='font-size:10px' align='right'>" + "$ " + funciones.FormatDecimal(cobranzaDolares.toString()) + "</td>" ;
                    }
                    else {
                        htmlSolesDet ="";
                    }
                    htmToDet = htmToDet + "" +
                            "<tr><td width='2%' style='font-size:10px' align='left' >" + (j+1) + "</td>" +
                            "<td width='24%' style='font-size:10px'>" + documentos_cobra_cabDAO.lst.get(j).getVENDEDOR().toString() + "</td>" +
                            "<td width='6%'  style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getTIPODOC().toString() + "</td>" +
                            "<td width='8%'  style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getNUMERO().toString() + "</td>" +
                            "<td width='8%'  style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getFPAGO().toString() + "</td>" +
                            "<td width='18%' style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getENTIDAD().toString() + "</td>" +
                            "<td width='9%'  style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getCONSTANCIA().toString() + "</td>" +
                            "<td width='7%'  style='font-size:10px' align='center'>" + documentos_cobra_cabDAO.lst.get(j).getFECHA().toString() + "</td>" +
                            "<td width='7%'  style='font-size:10px' align='right'>" + "S/ " + funciones.FormatDecimal(documentos_cobra_cabDAO.lst.get(j).getSALDO_INICIAL().toString()) + "</td>" +
                            "<td width='7%'  style='font-size:10px' align='right'>" + "S/ " +funciones.FormatDecimal(documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA().toString()) + "</td>" +
                            htmlSolesDet+
                            "<td width='6%'  style='font-size:10px' align='right'>" + "S/ "+ documentos_cobra_cabDAO.lst.get(j).getSALDO().toString() + "</td>" +
                            "</tr>";
                }

                    if(!htmlSolesTitulo.equals(""))
                    {
                        htmlSolesFooter = "<td width='6%' style='font-size:12px;font-weight:bold' align='right'>" + "$ "  + funciones.FormatDecimal(dCobranzaDolares.toString()) + "</td>";
                    }
                    else {
                        htmlSolesFooter ="";
                    }

                htmToDet = htmToDet + "<tr>" +
                        "<td colspan='9' style='font-weight:bold' align='right' >TOTAL RECIBO</td>" +
                        "<td width='7%' style='font-size:12px;font-weight:bold' align='right'>" + "S/ " + funciones.FormatDecimal(dCobranza.toString()) + "</td>" +
                        htmlSolesFooter+
                        "</tr></table>";

                sResumen =
                        "<table width='100%' cellpadding='4px' >" +
                               "<tr><td colspan='3'> <br /><br /></td></tr>" +
                              /*
                                "<tr width='100%'>" +
                                "<td width='40%' style='font-weight:bold;' align='center'>_________________________________________</td>" +
                                "<td width='20%' style='font-weight:bold;'></td>" +
                                "<td width='40%' style='font-weight:bold;' align='center'>_________________________________________</td></tr>" +
                                "<tr width='100%'>" +
                                "<td width='40%' style='font-weight:bold;' align='center'>"+  documentos_cobra_cabDAO.lst.get(0).getRAZON_SOCIAL().toString() +"</td>" +
                                "<td width='20%' style='font-weight:bold;'></td>" +
                                "<td width='40%' style='font-weight:bold;' align='center'>"+  documentos_cobra_cabDAO.lst.get(0).getNOMCOBRADOR().toString() +"</td></tr>" +
                              */
                                "<tr><td align='left' style='font-size: 10px'><b>Usuario: </b>"+ sharedSettings.getString("USUARIO", "").toString() +"</td><td></td><td align='right' style='font-size: 10px'><b> Fecha Impresión:</b> " + funciones.FechaActualNow() + "</td></tr>" +
                                "</table>";

                htmPie = "</body>" +
                        "</head>" +
                        "</html>";

                sHTML = htmToCab2 + htmToDet + sResumen + htmPie;
            }else{
                sHTML="<html><body>NO SE PUDO CARGAR EL REPORTE</body></html>";
            }
        }catch (Exception e){
        }
        return sHTML;
    }

    private void DocumentoCreado(String sOpcion,String phat){
        try {
            String to="galvezbr@gmail.com",cc="rgalvez_b@hotmail.com",asunto="Adjunto el Recibo 12333",mensaje="Mensaje de Prueba...";
            File file = new File(phat);
            //ABRIMOS EL DOCUMENTO
            if(sOpcion.equals("0")){
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            //HABILITAMOS LA SELECCION A DONDE VAMOS A ENVIAR EL RECIBO
            if(sOpcion.equals("1")){
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                //emailIntent.setType("message/rfc822");
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_CC, cc);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                emailIntent.setType("application/pdf");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                context.startActivity(Intent.createChooser(emailIntent, "Enviar "));
            }

            //ABRIMOS A GMAIL POR DEFAULT
            if(sOpcion.equals("2")){
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_CC, cc);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                emailIntent.setType("application/pdf"); //indicamos el tipo de dato
                emailIntent.setPackage("com.google.android.gm");
                if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(Intent.createChooser(emailIntent, "Email "));
                }else {
                    Toast.makeText(context,"Por favor instale email",Toast.LENGTH_LONG).show();
                }
            }
            //ABRIMOS EL WHATSAPP POR DEAFUL
            if(sOpcion.equals("3")){
                Intent whatsappsend = new Intent();
                whatsappsend.setAction(Intent.ACTION_SEND);
                whatsappsend.putExtra(Intent.EXTRA_TEXT, mensaje);
                whatsappsend.putExtra("jid", "941895433" +"@s.whatsapp.net");
                whatsappsend.setType("application/pdf"); //indicamos el tipo de dato
                whatsappsend.setType("text/plain");
                whatsappsend.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                whatsappsend.setPackage("com.whatsapp");
                if (whatsappsend.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(whatsappsend);
                }else {
                    Toast.makeText(context,"Por favor instale whatsapp",Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e){
            Toast.makeText(context,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

}
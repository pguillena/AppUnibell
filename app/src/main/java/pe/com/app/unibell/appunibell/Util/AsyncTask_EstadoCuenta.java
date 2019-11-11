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


import pe.com.app.unibell.appunibell.BL.CabfcobBL;
import pe.com.app.unibell.appunibell.BL.FactCobBL;
import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.Dialogs.Dialog_Fragment_Progress;
import pe.com.app.unibell.appunibell.R;

import static android.content.Context.MODE_PRIVATE;

public class AsyncTask_EstadoCuenta extends AsyncTask<String,String,Boolean> {
    public Context context;
    ProgressDialog progressDialog;


    public AsyncTask_EstadoCuenta(Context context){
        this.context =context;
    }
    static String GENERADOS = "PDF";
    String nombre_completo="";
    Document document;
    String SOPCION="0";
    String CONTENIDO = "";

    private Funciones funciones=new Funciones();
    private FactCobBL factCobBL = new FactCobBL();
    private CabfcobBL cabfcobBL = new CabfcobBL();

    private SharedPreferences sharedSettings;
    private SharedPreferences.Editor editor_Shared;

    @Override
    protected void onPreExecute() {

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
            document.addSubject("EstadoCuenta");
            document.addCreationDate();
            document.addTitle(TITLE);
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            try {

                sharedSettings =context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                String iID_EMPRESA = sharedSettings.getString("iID_EMPRESA", "0").toString();
                String iCOD_CLIENTE = sharedSettings.getString("CODIGO_ANTIGUO", "").toString();

             String consultaFactCobRest =  ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blfactcob_estado_cuenta + "/" +
                     iCOD_CLIENTE + "/" +
                      "XXX/" +
                     "XXX/" +
                     "XXX/" +
                     "1/" +
                     iID_EMPRESA;

                String consultaCabfCobRest =  ConstantsLibrary.RESTFUL_URL + ConstantsLibrary.blcabfcob_x_Documento + "/" +
                        iID_EMPRESA + "/"+
                        "XXX/" +
                        "XXX/" +
                        "XXX/" +
                        iCOD_CLIENTE;

                factCobBL.getEstadoCuenta(consultaFactCobRest);
                cabfcobBL.getDocumentRest(consultaCabfCobRest);
                CONTENIDO=GenerarEstadoCuenta();

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

    private String GenerarEstadoCuenta(){
        String sHTML="";
        try{
            String htmToCab1="",htmToCab2="",htmToDet="",sResumen="",htmPie="";



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


            if(factCobBL.lst!=null && factCobBL.lst.size()>0) {


                htmToCab1 = "<table width='100%'>" +
                        "<tr width='100%'>" +
                        "<td valign='top' width='25%'>" +
                        "<table width='100%'>" +
                        "<tr><td><img src='" + sdPath + "' /></td></tr>" +
                        "</table>" +
                        "</td>" +
                        "<td valign='top' width='50%' style='font-size: 25px' align='center' >ESTADO DE CUENTA</td>" +
                        "<td valign='top' width='25%'>" +
                        "</td>" +
                        "</tr>" +
                        "<tr><td colspan='3'> <hr style='color: #b52e31;' size='10'/></td></tr>" +
                        "</table>";

                htmToCab2 =
                        "<html>" +
                                "<head>" +
                                "<body>" +
                                "<table width='100%' cellpadding='4px'>" +
                                "<tr width='100%'>" +
                                // "<td width='80%'  style='font-weight:bold'><b>COBRADOR:</b>" + documentos_cobra_cabDAO.lst.get(0).getNOMCOBRADOR().toString() + "</td><td width='20%'><b>FECHA:</b>" + documentos_cobra_cabDAO.lst.get(0).getFECHA().toString() + "</td></tr>" +
                                "<td width='5%' ><b>COBRADOR</b></td><td><b>:</b></td><td width='60%'>" + factCobBL.lst.get(0).getNOMBREVENDEDOR().toString() + " </td><td width='15%'><b>FECHA</b></td><td><b>:</b></td><td width='15%'>" + funciones.FechaActualNow() + "</td></tr> " +
                                "<tr width='100%'>" +
                                //"<td width='80%'><b>CLIENTE:</b>" + documentos_cobra_cabDAO.lst.get(0).getRAZON_SOCIAL().toString() + "</td><td width='20%'><b>TIPO CAMBIO:</b>" + documentos_cobra_cabDAO.lst.get(0).getT_CAMBIO_TIENDA().toString() + "</td></tr>" +
                                "<td width='5%'><b>CLIENTE</b></td><td><b>:</b></td><td width='60%'>" + factCobBL.lst.get(0).getCOD_CLIENTE().toString() + " - " + factCobBL.lst.get(0).getCONCEPTO().toString() + " </td><td width='15%'><b></b></td><td><b></b></td><td width='15%'>" + "" + "</td></tr> " +
                                "</table>" +
                                "<table width='100%' border='0.01'  cellpadding='5px' bordercolor='666633'>" +
                                "<tr><td width='8%' align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >VENDEDOR ORIGEN</td>" +
                                "<td width='30%' align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>VENDEDOR</td>" +
                                "<td width='6%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>C.PAGO</td>" +
                                "<td width='8%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>T.DOC</td>" +
                                "<td width='8%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>SERIE</td>" +
                                "<td width='8%' align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >NUMERO</td>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >F.DOC</td>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >F.VCTO</td>" +
                                "<td width='5%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>DIAS</td>" +
                                "<td width='5%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>MN</td>" +
                                "<td width='10%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>IMPORTE</td>" +
                                "<td width='10%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>SALDO</td>" +
                                "</tr>";

                Double dCobranza = 0.0;
                Double dCobranzaDolares = 0.0;
                Double dTotalBoleta = 0.0;
                Double dTotalFactura = 0.0;
                Double dTotalNC = 0.0;
                Double dTotalND = 0.0;
                Double dTotalVencido = 0.0;
                Double dTotalPorVencer = 0.0;

                String Simbolo ="";
                for (int j = 0; j < factCobBL.lst.size(); j++) {
                    String htmlDetCabfcob="";
                    if(factCobBL.lst.get(j).getMONEDA().equals("S")) {
                        dCobranza = funciones.sumar(dCobranza ,factCobBL.lst.get(j).getSALDO());
                        Simbolo = "S/ ";
                    }
                    else if(factCobBL.lst.get(j).getMONEDA().equals("D"))
                    {
                        dCobranzaDolares  = funciones.sumar(dCobranzaDolares, factCobBL.lst.get(j).getSALDO());
                        Simbolo = "$ ";
                    }

                    if (factCobBL.lst.get(j).getTIPDOC().equals("03"))
                    {
                        dTotalBoleta = funciones.sumar(dTotalBoleta, factCobBL.lst.get(j).getSALDO());
                    }
                    else if(factCobBL.lst.get(j).getTIPDOC().equals("01"))
                    {
                        dTotalFactura = funciones.sumar( dTotalFactura, factCobBL.lst.get(j).getSALDO());
                    }
                    else if(factCobBL.lst.get(j).getTIPDOC().equals("07"))
                    {
                        dTotalNC = funciones.sumar(dTotalNC,  factCobBL.lst.get(j).getSALDO());
                    }
                    else if(factCobBL.lst.get(j).getTIPDOC().equals("08"))
                    {
                        dTotalND = funciones.sumar(dTotalND, factCobBL.lst.get(j).getSALDO());
                    }

                    if(factCobBL.lst.get(j).getDIAS()>0)
                    {
                        dTotalVencido  =funciones.sumar( dTotalVencido, factCobBL.lst.get(j).getSALDO());
                    }
                    else
                    {
                        dTotalPorVencer  =funciones.sumar( dTotalPorVencer, factCobBL.lst.get(j).getSALDO());
                    }


                    for(int h=0; h<cabfcobBL.lst.size(); h++)
                    {
                        if(factCobBL.lst.get(j).getTIPDOC().equals(cabfcobBL.lst.get(h).getTIPDOC()) &&  factCobBL.lst.get(j).getSERIE_NUM().equals(cabfcobBL.lst.get(h).getSERIE_NUM())&&  factCobBL.lst.get(j).getNUMERO().toString().equals(cabfcobBL.lst.get(h).getNUMERO()))
                        {
                            htmlDetCabfcob = htmlDetCabfcob +
                                    "<tr>" +
                                    "<td width='6%'  style='font-size:10px' align='center'>" + cabfcobBL.lst.get(h).getFECHA().toString() + "</td>" +
                                    "<td width='6%'  style='font-size:10px' align='center'>" + cabfcobBL.lst.get(h).getMONEDA().toString() + "</td>" +
                                    "<td width='6%'  style='font-size:10px' align='center'>" + cabfcobBL.lst.get(h).getIMPORTE() + "</td>" +
                                    "</tr>";


                        }
                    }

                    if (!htmlDetCabfcob.equals(""))
                    {
                        htmlDetCabfcob =
                                "<tr> <td colspan='12' align='right'>"+
                                "<table width='20%' border='0.01'  cellpadding='5px' bordercolor='666633'>" +
                                        "<tr>" +
                                        "<td colspan='3'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >DETALLE DE PAGOS</td>" +

                                        "</tr>"+
                                "<tr>" +
                                "<td width='7%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD' >FECHA</td>" +
                                "<td width='5%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>MN</td>" +
                                "<td width='10%'  align='center' style='font-size:10px;font-weight:bold; background:#FFDDDD'>IMPORTE</td>" +
                                "</tr>"+
                                htmlDetCabfcob +
                                "</table>" +
                                "</td></tr>";

                    }


                    htmToDet = htmToDet + "" +
                            "<tr>" +
                            "<td width='8%' style='font-size:10px' align='left' >" + factCobBL.lst.get(j).getCOD_VEND_ORIGEN().toString() + "</td>" +
                            "<td width='30%' style='font-size:10px'>" + factCobBL.lst.get(j).getNOMBREVENDEDOR().toString() + "</td>" +
                            "<td width='6%'  style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getCOND_PAG().toString() + "</td>" +
                            "<td width='8%'  style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getNOMBRETIPODOC().toString() + "</td>" +
                            "<td width='8%'  style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getSERIE_NUM().toString() + "</td>" +
                            "<td width='8%' style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getNUMERO().toString() + "</td>" +
                            "<td width='7%'  style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getFECHA().toString() + "</td>" +
                            "<td width='7%'  style='font-size:10px' align='center'>" + factCobBL.lst.get(j).getF_VENCTO().toString() + "</td>" +
                            "<td width='5%'  style='font-size:10px' align='right'>" + factCobBL.lst.get(j).getDIAS().toString()  + "</td>" +
                            "<td width='5%'  style='font-size:10px' align='right'>" + factCobBL.lst.get(j).getMONEDA().toString() + "</td>" +
                            "<td width='10%'  style='font-size:10px' align='right'>" + Simbolo + funciones.FormatDecimal(factCobBL.lst.get(j).getIMPORTE().toString()) + "</td>" +
                            "<td width='10%'  style='font-size:10px;font-weight:bold' align='right'>" + Simbolo + funciones.FormatDecimal(factCobBL.lst.get(j).getSALDO().toString()) + "</td>" +
                            "</tr>" +
                            htmlDetCabfcob;
                }

                if(dCobranza>0) {
                    htmToDet = htmToDet + "<tr>" +
                            "<td colspan='11' style='font-weight:bold' align='right' >SALDO TOTAL SOLES</td>" +
                            "<td width='10%' style='font-size:12px;font-weight:bold' align='right'>" + "S/ " + funciones.FormatDecimal(dCobranza.toString()) + "</td>" + "</tr>";
                }

                if(dCobranzaDolares>0)
                {
                    htmToDet = htmToDet + "<tr>" +
                            "<td colspan='11' style='font-weight:bold' align='right' >SALDO TOTAL DOLARES</td>" +
                            "<td width='10%' style='font-size:12px;font-weight:bold' align='right'>" + "$ "  + funciones.FormatDecimal(dCobranzaDolares.toString()) + "</td>" + "</tr>";
                }
                htmToDet = htmToDet + "</table>";

                sResumen =      "<table><tr><td><br/> <br/></td></tr></table>"+
                                "<table width='100%' border='0.01' cellpadding='5px' bordercolor='666633' >\n" +
                                "<tr>\n" +
                                "<td width='50%' colspan='2' align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD'>\n" +
                                "SALDO DEUDA VENCIDA\n" +
                                "</td>\n" +
                                "<td width='50%' colspan='2' align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD'>\n" +
                                "SALDO DEUDA POR VENCER\n" +
                                "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "\n" +
                                "<td width='50%'  colspan='2' align='center' style='font-size:12px'>\n" +
                                dTotalVencido.toString() +
                                "</td>\n" +
                                "<td width='50%'  colspan='2' align='center' style='font-size:12px'>\n" +
                                dTotalPorVencer.toString() +
                                "</td>\n" +
                                "</tr>\n" +
                                "\n" +
                                "<tr>\n" +
                                "<td align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD '>\n" +
                                "TOTAL BOLETAS\n" +
                                "</td>\n" +
                                "<td align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD'>\n" +
                                "TOTAL FACTURAS\n" +
                                "</td>\n" +
                                "<td align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD'>\n" +
                                "TOTAL NOTA DE CREDITO\n" +
                                "</td>\n" +
                                "<td align='center' style='font-size:12px;font-weight:bold; background:#FFDDDD'>\n" +
                                "TOTAL NOTA DE DEBITO\n" +
                                "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td align='center' style='font-size:12px'>\n" +
                                dTotalBoleta.toString() +
                                "</td>\n" +
                                "<td align='center' style='font-size:12px'>\n" +
                                dTotalFactura.toString() +
                                "</td>\n" +
                                "<td align='center' style='font-size:12px'>\n" +
                                dTotalNC.toString()+
                                "</td>\n" +
                                "<td align='center' style='font-size:12px'>\n" +
                                dTotalND.toString() +
                                "</td>\n" +
                                "</tr>\n" +
                                "</table>\n";

                htmPie = "</body>" +
                        "</head>" +
                        "</html>";

                sHTML =htmToCab1 + htmToCab2 + htmToDet + sResumen + htmPie;
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
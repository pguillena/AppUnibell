package pe.com.app.unibell.appunibell.Util;

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

import pe.com.app.unibell.appunibell.DAO.Documentos_Cobra_CabDAO;
import pe.com.app.unibell.appunibell.R;

import static android.content.Context.MODE_PRIVATE;

public class AsyncTask_Liquidacion extends AsyncTask<String,String,Boolean> {
    public Context context;

    public AsyncTask_Liquidacion(Context context){
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
                //LOGO
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                Image imagen = Image.getInstance(stream.toByteArray());
                imagen.scaleToFit(160, 200);
                imagen.setBorderColor(BaseColor.WHITE);
                imagen.setBackgroundColor(BaseColor.WHITE);
                imagen.setAlignment(Chunk.ALIGN_LEFT);
                document.add(imagen);

                sharedSettings =context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE);
                editor_Shared = context.getSharedPreferences(String.valueOf(R.string.UNIBELL_PREF), MODE_PRIVATE).edit();

                String iID_EMPRESA= sharedSettings.getString("iID_EMPRESA", "0").toString();
                String iID_LOCAL= sharedSettings.getString("iID_LOCAL", "0").toString();
                String LINQ_FECHA= sharedSettings.getString("LINQ_FECHA", "").toString();
                String iID_VENDEDOR= sharedSettings.getString("iID_VENDEDOR", "0").toString();
                String LINQ_ESTADO= sharedSettings.getString("LINQ_ESTADO", "").toString();
                String LIQ_N_PLANILLA= sharedSettings.getString("LIQ_N_PLANILLA", "").toString();
                String LIQ_C_PACKING= sharedSettings.getString("LIQ_C_PACKING", "").toString();

                documentos_cobra_cabDAO.getLiquidacionBy(iID_EMPRESA,iID_LOCAL,LINQ_FECHA,iID_VENDEDOR,LINQ_ESTADO,LIQ_N_PLANILLA,LIQ_C_PACKING);
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
                DocumentoCreado(SOPCION,nombre_completo);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String GeneraRecibo(){
        String sHTML="";
        try{
            String htmToCab="",htmToDet="",htmTotalGeneral="",sResumen="",htmPie="";
            if(documentos_cobra_cabDAO!=null) {
                htmToCab =
                        "<html>" +
                                "<head>" +
                                "<body>" +
                                "<table width='100%'>" +
                                "<tr width='100%'>" +
                                "<td width='80%' style='font-weight:bold'>PLANILLA LIQUIDACIÓN DE COBRANZA " + documentos_cobra_cabDAO.lst.get(0).getPLANILLA().toString() + "</td><td width='20%'><b>Usuario:</b>" + sharedSettings.getString("Usuario", "").toString() + "</td></tr>" +
                                "<tr width='100%'>" +
                                "<td width='80%'></td><td width='20%'><b>Fecha:</b>" + funciones.FechaActualNow() + "</td></tr>" +
                                "<tr width='100%'>" +
                                "<td width='80%'><b>Fecha Cobranza:</b>" + documentos_cobra_cabDAO.lst.get(0).getFECHA().toString() +  "</td><td width='20%'><b>Cobrador:</b>" +  documentos_cobra_cabDAO.lst.get(0).getNOMCOBRADOR().toString() + "</td></tr>" +
                                "<tr width='100%' style='background:#FFDDDD'>" +
                                "<td width='80%' ><b>PALNILLA:</b>" + documentos_cobra_cabDAO.lst.get(0).getPLANILLA().toString() + "</td><td width='20%'></td></tr>" +
                                "</table>" +
                                "<table width='100%' border=0.01 cellspacing=0 cellpadding=5 bordercolor='666633'>" +
                                "<tr><td width='7.5%' align='left' style='font-size:10px;font-weight:bold' >Código</td>" +
                                "<td width='7.5%' style='font-size:10px;font-weight:bold'>RUC/DNI</td>" +
                                "<td width='15%' style='font-size:10px;font-weight:bold'>Nombre/Razon Social</td>" +
                                "<td width='5%' style='font-size:10px;font-weight:bold'>Tipo Doc</td>" +
                                "<td width='10%' style='font-size:10px;font-weight:bold'>N°.Doc</td>" +
                                "<td width='6%' style='font-size:10px;font-weight:bold' >Forma Pago</td>" +
                                "<td width='10%' style='font-size:10px;font-weight:bold' >Efectivo/Baco/Cuenta Corriente</td>" +
                                "<td width='10%' style='font-size:10px;font-weight:bold' >Fecha Transacción</td>" +
                                "<td width='7.5%' style='font-size:10px;font-weight:bold'>N° OP/N° Cheque</td>" +
                                "<td width='4%' style='font-size:10px;font-weight:bold'>Md</td>" +
                                "<td width='7.5%' style='font-size:10px;font-weight:bold'>Importe</td>" +
                                "<td width='5%' style='font-size:10px;font-weight:bold'>Recibo</td>" +
                                "<td width='5%' style='font-size:10px;font-weight:bold'>Aprob</td>" +
                                "</tr>";



                Double dCobranza=0.0;
                for (int j = 0; j < documentos_cobra_cabDAO.lst.size(); j++) {
                    dCobranza +=documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA();
                    htmToDet = htmToDet + "<tr><td width='7.5%' style='font-size:10px' align='left' >" + documentos_cobra_cabDAO.lst.get(j).getCOD_CLIENTE().toString() + "</td>" +
                            "<td width='7.5%' style='font-size:10px'>" + documentos_cobra_cabDAO.lst.get(j).getRUC().toString() + "</td>" +
                            "<td width='15%' style='font-size:10px'>" +  documentos_cobra_cabDAO.lst.get(j).getRAZON_SOCIAL().toString() + "</td>" +
                            "<td width='5%' style='font-size:10px'>" +   documentos_cobra_cabDAO.lst.get(j).getTIPODOC().toString() + "</td>" +
                            "<td width='10%' style='font-size:10px'>" +  documentos_cobra_cabDAO.lst.get(j).getNUMERO().toString() + "</td>" +
                            "<td width='6%' style='font-size:10px'>" +   documentos_cobra_cabDAO.lst.get(j).getFPAGODESC().toString() + "</td>" +
                            "<td width='10%' style='font-size:10px'>" +  documentos_cobra_cabDAO.lst.get(j).getNOMCTACORRIENTE().toString() + "</td>" +
                            "<td width='10%' style='font-size:10px' >" + documentos_cobra_cabDAO.lst.get(j).getFECHA().toString() + "</td>" +
                            "<td width='7.5%' style='font-size:10px'>" + documentos_cobra_cabDAO.lst.get(j).getNUMCHEQ().toString() + "</td>" +
                            "<td width='4%' style='font-size:10px'>" +   documentos_cobra_cabDAO.lst.get(j).getMONEDA().toString() + "</td>" +
                            "<td width='7.5%' style='font-size:10px'>" + funciones.FormatDecimal(documentos_cobra_cabDAO.lst.get(j).getM_COBRANZA().toString()) + "</td>" +
                            "<td width='5%' style='font-size:10px'>" +   documentos_cobra_cabDAO.lst.get(j).getRECIBO().toString() + "</td>" +
                            "<td width='5%' style='font-size:10px'>" +   documentos_cobra_cabDAO.lst.get(j).getID_COBRADOR().toString() + "</td>" +
                            "</tr>";
                }
                htmToDet = htmToDet + "<tr><td colspan='10' style='font-weight:bold' align='right' >TOTAL DE PLANILLA" + documentos_cobra_cabDAO.lst.get(0).getPLANILLA().toString() + "</td>" + "<td width='7.5%' style='font-size:12px'>" +  funciones.FormatDecimal(dCobranza.toString()) + "</td>" + "</tr>";

                htmTotalGeneral = "<table width='88.5%' border=0 cellspacing=0 bordercolor='666633'>" +
                        "<tr><td  style='font-weight:bold;background:#FFDDDD' align='center' >TOTAL GENERAL</td>" + "<td style='font-weight:bold;background:#FFDDDD' width='7.5%'>1600.20</td>" + "</tr></table>";

                sResumen =
                        "<table width='100%'>" +
                                "<tr width='100%'><td></td></tr>" +
                                "<tr width='100%'>" +
                                "<td width='100%' style='font-weight:bold;align='center''>RESUMEN DE COBRANZAS</td></tr>" +
                                "</table>" +
                                "<table width='100%' border=0.01 cellspacing=0 cellpadding=5 bordercolor='666633'>" +
                                "<tr><td width='7.5%' align='left' style='font-size:10px;font-weight:bold' >PLANILLA</td>" +
                                "<td width='12%' style='font-size:10px;font-weight:bold'>FORMA PAGO</td>" +
                                "<td width='40%' style='font-size:10px;font-weight:bold'>EFECTIVO/BANCO/CUENTA CORRIENTE</td>" +
                                "<td width='12%' style='font-size:10px;font-weight:bold'>FECHA OP/CHEQUE</td>" +
                                "<td width='12%' style='font-size:10px;font-weight:bold'>N°.OP/N°CHEQUE</td>" +
                                "<td width='12%' style='font-size:10px;font-weight:bold' >VOUCHER</td>" +
                                "<td width='12%' style='font-size:10px;font-weight:bold' >MONTO TOTAL</td>" +
                                "</tr></table>";

                htmPie = "</table>" +
                        "</body>" +
                        "</head>" +
                        "</html>";

                sHTML = htmToCab + htmToDet + htmPie + htmTotalGeneral + sResumen;
            }else{
                sHTML="<html><head><body>ERROR AL GENERAR EL REPORTE</body>/head></html>";
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
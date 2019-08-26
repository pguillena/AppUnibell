package pe.com.app.unibell.appunibell.Util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import pe.com.app.unibell.appunibell.R;

public class ItextLibrary extends AsyncTask<String,String,Boolean> {
    public Context context;
    public ItextLibrary(Context context){
        this.context =context;
    }
    static String GENERADOS = "PDF";
    String nombre_completo="";
    Document document;
    String SOPCION="0";

    @Override
    protected Boolean doInBackground(String... argumento) {
        String NOMBRE_CARPETA_APP = argumento[0];
        String NOMBRE_ARCHIVO = argumento[1];
        String TITLE = argumento[2];
        String CONTENIDO = argumento[3];
        SOPCION=argumento[4];
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
                worker.parseXHtml(pdfWriter, document, new StringReader(CONTENIDO));

                String XXX="1";
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



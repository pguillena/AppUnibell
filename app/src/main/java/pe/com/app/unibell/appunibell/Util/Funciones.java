package pe.com.app.unibell.appunibell.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import pe.com.app.unibell.appunibell.R;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.POWER_SERVICE;

/**
 * Created by RENAN on 18/08/2016.
 */
public class Funciones {

    public boolean ValidacionPermisos(Context context) {
        Boolean iValor=true;
        Integer iAlmacen=1,iTelefono=1;
        //CAMARAS
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //ALMACENAMIENTO-PDF,EXCEL,TEXT,WORD, ETC...
            int hasWriteContactsPermission_Almacen = 0;
            hasWriteContactsPermission_Almacen = context.checkSelfPermission(WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission_Almacen != PackageManager.PERMISSION_GRANTED) {
                iAlmacen=0;
            }
            //TELEFONO
            int hasWriteContactsPermission_Telefono = 0;
            hasWriteContactsPermission_Telefono = context.checkSelfPermission(READ_PHONE_STATE);

            if (hasWriteContactsPermission_Telefono != PackageManager.PERMISSION_GRANTED) {
                iTelefono=0;
            }
        }
        if(iAlmacen==0 || iTelefono==0){
            iValor=false;
            Recomendacion(context);
        }
        return iValor;
    }

    private void Recomendacion(final Context context){
        AlertDialog.Builder dialogo=new AlertDialog.Builder(context);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{
                            WRITE_EXTERNAL_STORAGE,
                            READ_PHONE_STATE
                            //  SEND_SMS
                    },100);

                }
            }
        });
        dialogo.show();
    }

    public Boolean Valid_InactivaOptimatBateria(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
                return false;
            }
        }
        return true;
    }



    public String FormatDolar(String valor) {
        String Resultado;
        Locale ES_PE =new Locale("es","pe"); //Español Perú
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols(ES_PE);
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_dolar = new DecimalFormat("'$'###,###.##", simbolo);
        Resultado = String.valueOf(formato_dolar.format((Double.valueOf(valor))));
        return Resultado;
    }

    public String FormatSoles(String valor) {
        String Resultado;
        Locale ES_PE =new Locale("es","pe"); //Español Perú
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols(ES_PE);
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_dolar = new DecimalFormat("'S/ '###,###.##", simbolo);
        Resultado = String.valueOf(formato_dolar.format((Double.valueOf(valor))));
        return Resultado;
    }

    public String FormatEntero(String valor) {
        String Resultado;
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_entero = new DecimalFormat("###,###", simbolo);
        Resultado = String.valueOf(formato_entero.format((Double.valueOf(valor))));
        return Resultado;
    }



 /*   public String FormatDecimal(String valor) {
        String Resultado;
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_entero = new DecimalFormat("###,###.00", simbolo);
        Resultado = String.valueOf(formato_entero.format((Double.valueOf(valor))));
        return Resultado;
    }*/

    public static String FormatDecimal(String valor) {
       String Resultado;
    /*    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_entero = new DecimalFormat("###,###.00", simbolo);
        Resultado = String.valueOf(formato_entero.format((Double.valueOf(valor))));
*/
        Resultado = String.valueOf((float)Math.round(Double.valueOf(valor) * 100) / 100);

        return Resultado;
    }

    public static String ObtienMsqOracle(String s) {
        String searchString="|";
        String substring="";
        Integer starIndex,endIndex;
        if(s.contains(searchString)){
            starIndex=s.indexOf(searchString);
            searchString="*";
            endIndex=s.indexOf(searchString);
            substring=s.substring(starIndex+1,endIndex);
        }


        return substring;
    }



    public String FormatPorcentaje(String valor) {
        String Resultado;
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_porcen = new DecimalFormat("###,###.##'%'", simbolo);
        Resultado = String.valueOf(formato_porcen.format((Double.valueOf(valor))));
        return Resultado;
    }

    public String FechaActual() {
        String Resultado = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        Resultado = String.valueOf(fecha.format(c.getTime()));
        return Resultado;
    }

    public String HoraActual() {
        String Resultado = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm:ss a");
        Resultado = String.valueOf(hora.format(c.getTime()));
        return Resultado;
    }

    public String FechaActualNow() {
        String Resultado = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Resultado = String.valueOf(fecha.format(c.getTime()));
        return Resultado;
    }


    public String HoraActual24() {
        String Resultado = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        Resultado = String.valueOf(hora.format(c.getTime()));
        return Resultado;
    }

    public String Year(String FechaString) {
        String year = "";
        try {
            SimpleDateFormat fechaformat = new SimpleDateFormat("dd-MM-yyyy");
            Date Fecha = fechaformat.parse(FechaString.toString());
            year  = (String) android.text.format.DateFormat.format("yyyy", Fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }

    public String Month(String FechaString) {
        String mes = "";
        try {
            SimpleDateFormat fechaformat = new SimpleDateFormat("dd-MM-yyyy");
            Date Fecha = fechaformat.parse(FechaString.toString());
            mes  = (String) android.text.format.DateFormat.format("MM", Fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mes;
    }

    public String Day(String FechaString) {
        String dia = "";
        try {
            SimpleDateFormat fechaformat = new SimpleDateFormat("dd-MM-yyyy");
            Date Fecha = fechaformat.parse(FechaString.toString());
            dia  = (String) android.text.format.DateFormat.format("dd", Fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }

    public static Date ConvertToDate(String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }


    //BASE 64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    Context mContext;
    private String getPhoneNumber(){
        TelephonyManager mTelephonyManager;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getLine1Number();
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static String isNullColumn(Cursor cursor, String column, String obvio){
        return cursor.isNull(cursor.getColumnIndex(column)) ? obvio : cursor.getString(cursor.getColumnIndex(column));
    }

    public static int isNullColumn(Cursor cursor, String column, int obvio){
        return cursor.isNull(cursor.getColumnIndex(column)) ? obvio : cursor.getInt(cursor.getColumnIndex(column));
    }

    public static Double isNullColumn(Cursor cursor, String column, Double obvio){
        return cursor.isNull(cursor.getColumnIndex(column)) ? obvio : cursor.getDouble(cursor.getColumnIndex(column));
    }

    public static float isNullColumn(Cursor cursor, String column, float obvio){
        return cursor.isNull(cursor.getColumnIndex(column)) ? obvio : cursor.getFloat(cursor.getColumnIndex(column));
    }



    public static String isNull(String Valor){
        String ivalor="";
        if (Valor == null || Valor.isEmpty() || Valor.trim().toUpperCase().equals("NULL")) {
            ivalor = "";
        }else{
            ivalor = Valor;
        }
        return ivalor;
    }

    private static String cursorToString(Cursor crs) {
        JSONArray arr = new JSONArray();
        crs.moveToFirst();
        while (!crs.isAfterLast()) {
            int nColumns = crs.getColumnCount();
            JSONObject row = new JSONObject();
            for (int i = 0 ; i < nColumns ; i++) {
                String colName = crs.getColumnName(i);
                if (colName != null) {
                    String val = "";
                    try {
                        switch (crs.getType(i)) {
                            case Cursor.FIELD_TYPE_BLOB   : row.put(colName, crs.getBlob(i).toString()); break;
                            case Cursor.FIELD_TYPE_FLOAT  : row.put(colName, crs.getDouble(i))         ; break;
                            case Cursor.FIELD_TYPE_INTEGER: row.put(colName, crs.getLong(i))           ; break;
                            case Cursor.FIELD_TYPE_NULL   : row.put(colName, null)                     ; break;
                            case Cursor.FIELD_TYPE_STRING : row.put(colName, crs.getString(i))         ; break;
                        }
                    } catch (JSONException e) {
                    }
                }
            }
            arr.put(row);
            if (!crs.moveToNext())
                break;
        }
        return arr.toString();
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    public String getMD5Digest(byte[] buffer) {
        String resultHash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] result = new byte[md5.getDigestLength()];
            md5.reset();
            md5.update(buffer);
            result = md5.digest();

            StringBuffer buf = new StringBuffer(result.length * 2);

            for (int i = 0; i < result.length; i++) {
                int intVal = result[i] & 0xff;
                if (intVal < 0x10) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(intVal));
            }

            resultHash = buf.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return resultHash;
    }



    public static String LetraCapital(String str) {

        if (str == null || str.isEmpty()) {
            return str;
        }
        else {
            String nuevoStr = "";
            str = str.replace("  "," ");
            String[] lista = str.split(" ");

            if (lista != null && lista.length > 0) {
                for (int i = 0; i < lista.length; i++) {

                    if(!lista[i].equals(""))
                    {
                        nuevoStr = nuevoStr + " " + lista[i].substring(0, 1).toUpperCase() + lista[i].toLowerCase().substring(1);
                    }

                }

            }

            return  nuevoStr.trim();
        }

    }



    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer))
        {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }
        private static String capitalize(String str)
        { if (TextUtils.isEmpty(str))
        { return str; }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr)
        {
            if (capitalizeNext && Character.isLetter(c))
        {
            phrase += Character.toUpperCase(c);
        capitalizeNext = false;
        continue;
        }
        else if (Character.isWhitespace(c))
        {
            capitalizeNext = true;
        }
        phrase += c;
        }
        return phrase; }




    public void addTextChangedListener(final EditText textView, final int activo, final int inactivo){

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!textView.getText().toString().trim().equals("")) {

                    textView.setBackgroundResource(activo);
                }
                else
                {
                    textView.setBackgroundResource(inactivo);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void addTextChangedListener(final TextView textView, final int activo, final int inactivo){

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!textView.getText().toString().trim().equals("")) {

                    textView.setBackgroundResource(activo);
                }
                else
                {
                    textView.setBackgroundResource(inactivo);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    static public Boolean CheckBoolean(String value)
    {
        boolean salida = false;

        if (value!=null && (value.equals("1") || value.trim().toUpperCase().equals("TRUE") || value.trim().toUpperCase().equals("SI")))
        {
            salida = true;
        }
        else
        {
            salida = false;

        }
        return salida;
    }

    public String restar(String valor1,String valor2){
        BigDecimal deci1=new BigDecimal (Double.parseDouble(valor1));
        BigDecimal deci2=new BigDecimal (Double.parseDouble(valor2));
        deci1=deci1.setScale(2,BigDecimal.ROUND_HALF_UP);
        deci2=deci2.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal resultado=deci1.subtract(deci2);

        return resultado.toString();
    }


    static public  String AgregarCeros(String string, int largo)
    {
        String ceros = "";

        int cantidad = largo - string.length();

        if (cantidad >= 1)
        {
            for(int i=0;i<cantidad;i++)
            {
                ceros += "0";
            }

            return (ceros + string);
        }
        else
            return string;
    }


}

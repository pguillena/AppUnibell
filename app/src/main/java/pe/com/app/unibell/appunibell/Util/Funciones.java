package pe.com.app.unibell.appunibell.Util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

/**
 * Created by RENAN on 18/08/2016.
 */
public class Funciones {
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
        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formato_entero = new DecimalFormat("###,###.00", simbolo);
        Resultado = String.valueOf(formato_entero.format((Double.valueOf(valor))));
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
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
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
        String ivalor="0";
        if (Valor.isEmpty()) {
            ivalor = "0";
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
            String[] lista = str.split(" ");

            if (lista != null && lista.length > 0) {
                for (int i = 0; i < lista.length; i++) {

                    nuevoStr = nuevoStr +" "+ lista[i].substring(0, 1).toUpperCase() + lista[i].toLowerCase().substring(1);

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


}

package pe.com.app.unibell.appunibell.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rgalvez on 15/11/2016.
 */
public class RestClientLibrary {
    //TIEMPO ESPERA DE CONEXION
    private static final int CONNECTION_TIMEOUT = 8000;
    //TIEMPO ESPERA DE DATA
    private static final int SOCKET_TIMEOUT = 30000;

    private AbstractHttpClient httpClient;
    public RestClientLibrary() {
        setupHttpClient();
    }
    private void setupHttpClient() {
        //CREACION HTTPARAMS PARA SETEAR TIEMPO DE ESPERA PARA CONECTAR AL SERVICIO
        HttpParams httpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParams,"UTF-8");
        HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
        httpClient = new DefaultHttpClient(httpParams);
    }
    public String get(String newURL) {
        String strResult="";
        HttpEntity httpEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            //CREACION DEL REQUEST GET
            HttpGet httpGet = new HttpGet(newURL);
            //SETEAR PARAMETROS HEADER DEL REQUEST
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Accept", "application/json");

            //EXECUCION DEL CLIENT Y RECEPCION CON LA CLASE HTTPRESPONSE
            HttpResponse response = this.httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                //EXTRACCION DEL HTTPENTITY DEL OBJETO HTTRESPONSE
                httpEntity = response.getEntity();
                //CONVERSION HTTPENTITY A STRING PARA LA SALIDA STRRESULT
                //strResult = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                strResult = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            }else{
                jsonObject.accumulate("status", 0);
                jsonObject.accumulate("message", "Error al obtener los registros.");
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            }
        } catch (Exception ex) { //CONTROL DE EXCEPCIONES
            //CREACION DE UN JSONOBJECT  CON DATOS DEL POSIBLE ERROR A NIVEL DE APLICACION

            try {
                jsonObject.accumulate("status", 0);
                jsonObject.accumulate("message", ex.getMessage());
                //jsonObject.accumulate("message","Error al conectarse al servidor,\nValide por favor su conexión a internet\n");
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (this.httpClient != null) {//EXPIRAMOS LA CONEXION DEL HTTPCLIENT
                this.httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
        /*DEVOLVEMOS UN JSONOBJECT EN STRING
        EJEMPLO DE RESULTADOS
        {"status":true, "message":"5 REGISTROS ENCONTRADOS", "usuarios":[{}]}
        {"status":false, "message":"TIME OUT"}*/
        return strResult;
    }
    public String post(String newURL, JSONObject newJSONObject) {
        String strResult="";
        HttpEntity httpEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            HttpPost httpPost = new HttpPost(newURL);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            String json = newJSONObject.toString();
            StringEntity stringEntity = new StringEntity(json, HTTP.UTF_8);
            httpPost.setEntity(stringEntity);

            //EXECUCION DEL CLIENT Y RECEPCION CON LA CLASE HTTPRESPONSE
            HttpResponse response = this.httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                //EXTRACCION DEL HTTPENTITY DEL OBJETO HTTRESPONSE
                httpEntity = response.getEntity();
                //CONVERSION HTTPENTITY A STRING PARA LA SALIDA STRRESULT
                strResult = EntityUtils.toString(httpEntity, HTTP.UTF_8);
            }else{
                jsonObject.accumulate("status", 0);
                jsonObject.accumulate("message", "Error al enviar los registros.");
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            }

            //6002
        } catch (Exception ex) {
            //CREACION DE UN JSONOBJECT  CON DATOS DEL POSIBLE ERROR A NIVEL DE APLICACION

            try {
                jsonObject.accumulate("status", false);
                jsonObject.accumulate("message", ex.getMessage());
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (httpClient != null) {//EXPIRAMOS LA CONEXION DEL HTTPCLIENT
                httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
        return strResult;
    }
    public String put(String newURL, JSONObject newJSONObject){
        String strResult="";
        HttpEntity httpEntity = null;
        JSONObject jsonObject = new JSONObject();
        try{
            HttpPut httpPut = new HttpPut(newURL);
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setHeader("Accept", "application/json");
            String json = newJSONObject.toString();
            StringEntity stringEntity = new StringEntity(json, HTTP.UTF_8);
            httpPut.setEntity(stringEntity);

            //EXECUCION DEL CLIENT Y RECEPCION CON LA CLASE HTTPRESPONSE
            HttpResponse response = this.httpClient.execute(httpPut);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                //EXTRACCION DEL HTTPENTITY DEL OBJETO HTTRESPONSE
                httpEntity = response.getEntity();
                //CONVERSION HTTPENTITY A STRING PARA LA SALIDA STRRESULT
                strResult = EntityUtils.toString(httpEntity,HTTP.UTF_8);
            }else{
                jsonObject.accumulate("status", 0);
                jsonObject.accumulate("message", "Error al enviar los registros.");
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            }

        } catch (Exception ex) {
            //CREACION DE UN JSONOBJECT  CON DATOS DEL POSIBLE ERROR A NIVEL DE APLICACION

            try {
                jsonObject.accumulate("status", 15);
                jsonObject.accumulate("message", ex.getMessage());
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (httpClient != null) {//EXPIRAMOS LA CONEXION DEL HTTPCLIENT
                httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
        return strResult;
    }
    public String del(String newURL) {
        String strResult="";
        HttpEntity httpEntity = null;

        try {
            //CREACION DEL REQUEST DELETE
            HttpDelete httpDelete = new HttpDelete(newURL);
            //SETEAR PARAMETROS HEADER DELETE REQUEST
            httpDelete.addHeader("Content-Type", "application/json");
            httpDelete.setHeader("Accept", "application/json");
            //EXECUCION DEL CLIENT Y RECEPCION CON LA CLASE HTTPRESPONSE
            HttpResponse response = httpClient.execute(httpDelete);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                //EXTRACCION DEL HTTPENTITY DEL OBJETO HTTRESPONSE
                httpEntity = response.getEntity();
                //CONVERSION HTTPENTITY A STRING PARA LA SALIDA STRRESULT
                strResult = EntityUtils.toString(httpEntity,HTTP.UTF_8);
            }
        } catch (Exception ex) {
            //CREACION DE UN JSONOBJECT  CON DATOS DEL POSIBLE ERROR A NIVEL DE APLICACION
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("status", false);
                jsonObject.accumulate("message", ex.getMessage());
                //CONVERSION JSONOBJECT A STRING PARA LA SALIDA STRRESULT
                strResult = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } finally {
            if (httpClient != null) {//EXPIRAMOS LA CONEXION DEL HTTPCLIENT
                httpClient.getConnectionManager().closeExpiredConnections();
            }
        }
        return strResult;
    }

}


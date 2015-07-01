package com.example.proyecto_final;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;

public class DevuelveJSON {
	public JSONArray getJSONfromURL(String url,ArrayList<NameValuePair> parametros) {
		InputStream is = null;
		String result = "";
		JSONArray jArray = null;
		// CONECTAMOS CON HTTP VIA POST
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(parametros));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("ERROR => ", "Error en conexion http : " + e.toString());
			e.printStackTrace();
		}
		// SI EL SERVICIO NOS DEVUELVE DATOS EN EL InputStream is
		if (is != null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("ERROR => ", "En datos devueltos por el Servicio POST : "	+ e.toString());
				e.printStackTrace();
			}
		}
		// RECOGEMOS LOS DATOS DEVUELTOS POR EL POST Y CONVERTIDOS A STRING Y
		// LOS DEVOLVEMOS COMO UN OBJETO JSONArray
		try {
			jArray = new JSONArray(result);
			return jArray;
		} catch (JSONException e) {
			Log.e("ERROR => ",
					"Error convirtiendo los datos a JSON : " + e.toString());
			e.printStackTrace();
			return null;
		}
	}
}

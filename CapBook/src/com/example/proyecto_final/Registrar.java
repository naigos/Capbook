package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar extends Activity {
	
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	JSONArray jsonarray;
	String ip = "";
	EditText nombre;
	EditText pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar);
		
		pass = (EditText) findViewById(R.id.cPass);
		nombre = (EditText) findViewById(R.id.cNombre);
	}
	
	public void registrar(View v){		
		SharedPreferences pref = getSharedPreferences("com.example.proyecto_final_preferences", MODE_PRIVATE);
    	ip = pref.getString("prefIp", "1");
		if(!nombre.getText().toString().equals("") && !pass.getText().toString().equals("") && !ip.equals("1") && pass.length() > 7 && nombre.length() > 3){
			Reg nuevo = new Reg();
			nuevo.execute();
		}else if(ip.equals("1")){
			Toast.makeText(getApplicationContext(), "Debes poner la direccion ip", Toast.LENGTH_LONG).show();
		}else if(pass.length() < 8){
			Toast.makeText(getApplicationContext(), "La contraseña debe ser de al menos de 8 caracteres", Toast.LENGTH_LONG).show();
		}else if(nombre.length() < 4){
			Toast.makeText(getApplicationContext(), "El nombre debe ser de al menos de 4 caracteres", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
		}
		
	}
	
	class Reg extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Registrar.this);
			pDialog.setMessage("Comprobando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			// doInBackground DEVUELVE un String
			// CADENA DE CONEXION HTTP AL SERVICIO WEB

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/Registro.php";
			
			Log.v("1111111", "1111111111");

			// PARAMETROS QUE PASAMOS MEDIANTE POST

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", nombre
					.getText().toString()));
			parametrosPost.add(new BasicNameValuePair("password", pass
					.getText().toString()));
			
			Log.v("222222222", "2222222222");
			
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);
			
			Log.v("3333333333", "33333333333");

			// HACEMOS LA CONEXION HTTP MEDIANTE POST CON SUS PARAMETROS Y
			// RECIBIMOS LOS DATOS EN UN JSONARRAY QUE TRATAREMOS A POSTERIORI

			try {
				if (jsonarray.getJSONObject(0).getString("logstatus").equals("1")) {
					estado = "bien";
				} else {
					estado = jsonarray.getJSONObject(0).getString("logstatus");
				}
			} catch (JSONException e1) {
				Log.v("aaaa", "aaaaa");
				Toast.makeText(getApplicationContext(),
						"Error al leer datos del servidor", Toast.LENGTH_LONG)
						.show();
			}

			return estado;
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();
			if (result.equals("bien")) {				
				Toast.makeText(getApplicationContext(),
						"Registrado con éxito", Toast.LENGTH_LONG).show();
				Intent i = new Intent(Registrar.this, MainActivity.class); 
				startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(),
						"Error, " + result, Toast.LENGTH_LONG).show();
			}
		}
	}

}

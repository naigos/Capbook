package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Configuracion extends Activity {
	
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	String ip, id, nombre;
	JSONArray jsonarray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		
		ip = getIntent().getStringExtra("ip");
		id = getIntent().getStringExtra("usuario");
	}
	
	//Cambio de perfil, Dar de baja, logout

	@Override
	public void onBackPressed() {
		// nada
	}
	
	public void finalizar(View v){
		startActivity(new Intent(getBaseContext(), MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
		finish();
	}
	

	public void borrarCuenta(View v){	
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("¿Estas seguro de borrar tu cuenta?");

		alert.setPositiveButton("Me doy de baja", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			BorrarCuenta nuevo = new BorrarCuenta();
			nuevo.execute();
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  Toast.makeText(getApplicationContext(),"Cancelado", Toast.LENGTH_LONG).show();
		  }
		});

		alert.show();			
	}
	
	public void cambiarNombre(View v){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Introduce nuevo nombre");
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Acepto", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			nombre = input.getText().toString();
			CambioNombre nuevo = new CambioNombre();
			nuevo.execute();
		  }
		});

		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  Toast.makeText(getApplicationContext(),"Cancelado", Toast.LENGTH_LONG).show();
		  }
		});

		alert.show();
	}
	
	
	class BorrarCuenta extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Configuracion.this);
			pDialog.setMessage("Eliminando cuenta...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/BorrarCuenta.php";

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", id));
			
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);

			try {
				if (jsonarray.getJSONObject(0).getString("logstatus").equals("1")) {
					estado = "borrado";
				} else {
					estado = jsonarray.getJSONObject(0).getString("logstatus");
				}
			} catch (JSONException e1) {
				Toast.makeText(getApplicationContext(),
						"Error al leer datos del servidor", Toast.LENGTH_LONG)
						.show();
			}

			return estado;
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();
			if (result.equals("borrado")) {				
				Toast.makeText(getApplicationContext(),"Tu cuenta ha sido borrada", Toast.LENGTH_LONG).show();
				View v = null;
				finalizar(v);
			} else {
				Toast.makeText(getApplicationContext(),"Error al borrar" + result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	///////////////////////////////////////////
	/////CAMBIO DE NOMBRE
	//////////////////////////////////////////
	
	class CambioNombre extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Configuracion.this);
			pDialog.setMessage("Comprobando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/Cambio.php";

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", id));
			parametrosPost.add(new BasicNameValuePair("nombre", nombre)); 
			
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);

			try {
				if (jsonarray.getJSONObject(0).getString("logstatus").equals("1")) {
					estado = "bien";
				} else {
					estado = jsonarray.getJSONObject(0).getString("logstatus");
				}
			} catch (JSONException e1) {
				Toast.makeText(getApplicationContext(),
						"Error al leer datos del servidor", Toast.LENGTH_LONG)
						.show();
			}

			return estado;
		}

		protected void onPostExecute(String result) {
			pDialog.dismiss();
			if (result.equals("bien")) {				
				Toast.makeText(getApplicationContext(),"Cambiado con éxito", Toast.LENGTH_LONG).show();
				View v = null;
				finalizar(v);
			} else {
				Toast.makeText(getApplicationContext(), "Error, " + result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
}

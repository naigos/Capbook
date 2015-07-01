package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Buscar extends ListActivity {
	
	Adaptador adapt;
	JSONArray jsonarray;
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	String id, ip, name, idseg, texto;
	EditText cBuscar;
	ArrayList<String> lista;
	ArrayList<String> listaId = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar);
		
		ip = getIntent().getStringExtra("ip");
		id = getIntent().getStringExtra("id");
		texto = getIntent().getStringExtra("texto");
		
		cBuscar = (EditText) findViewById(R.id.editText1);
		
		RecuperarBusqueda nuevo = new RecuperarBusqueda();
		nuevo.execute();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		Intent i = new Intent(Buscar.this, Principal.class);
		i.putExtra("ip", ip); 
		i.putExtra("usuario", id); 
		startActivity(i);
	}
	
	public void buscar(View v){
		if(cBuscar.getText().toString().length() > 1){
			finish();
			Intent i = new Intent(Buscar.this, Buscar.class);
			i.putExtra("ip", ip); 
			i.putExtra("id", id); 
			i.putExtra("texto", cBuscar.getText().toString()); 
			startActivity(i);
		}else{
			Toast.makeText(getApplicationContext(),	"La búsqueda debe contener más de un caracter", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		name = lista.get(position);
		idseg = listaId.get(position);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Quieres seguir a " + name);
		
		//Dejar de seguir a
		alert.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Seguir nuevo = new Seguir();
			nuevo.execute();
		  }
		});
		//Cancelado
		alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
			  Toast.makeText(getApplicationContext(),"Cancelado", Toast.LENGTH_LONG).show();
		  }
		});

		alert.show();
	}
	
	
	class RecuperarBusqueda extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Buscar.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/Buscar.php";
			
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("id", id));
			parametrosPost.add(new BasicNameValuePair("texto", texto));
			Log.v("0000000", texto + " --- " + ip + " --- " + id);
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);
			return estado;
		}

		protected void onPostExecute(String result) {

			lista = new ArrayList<String>();
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject job;
				try {
					job = jsonarray.getJSONObject(i);
					lista.add(job.getString("nombre"));
					listaId.add(job.getString("id"));
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(),
							"Error al crear objeto json", Toast.LENGTH_LONG)
							.show();
				}
			}

			adapt = new Adaptador(Buscar.this, lista);
			setListAdapter(adapt);
			pDialog.dismiss();

		}
	}
	
	class Seguir extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Buscar.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/Seguir.php";
			
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("id", id));
			parametrosPost.add(new BasicNameValuePair("idseg", idseg));
			Log.v("0000000", id + " --- " + idseg);
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
				Toast.makeText(getApplicationContext(),"Ahora sigues a " + name, Toast.LENGTH_LONG).show();
				finish();
				Intent i = new Intent(Buscar.this, Principal.class);
				i.putExtra("ip", ip); 
				i.putExtra("usuario", id); 
				startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(),"Error, " + result, Toast.LENGTH_LONG).show();
			}

		}
	}

}

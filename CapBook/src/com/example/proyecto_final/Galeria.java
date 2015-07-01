package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class Galeria extends Activity {
	
	Adaptador2 adapt; 
	JSONArray jsonarray;
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	String idusuario, ip, name, idfoto;
	ObjetoFoto foto;
	ArrayList<ObjetoFoto> lista;
	ArrayList<String> listaId = new ArrayList<String>();
	GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_galeria);
		
		ip = getIntent().getStringExtra("ip");
		idusuario = getIntent().getStringExtra("usuario");
		setTitle("   Bienvenido " + idusuario);
			
		gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {            	
            	foto = lista.get(position);
            	Intent i = new Intent(Galeria.this, AbrirFoto.class);
		    	i.putExtra("ip", ip); 
		    	i.putExtra("id", idusuario);
		    	i.putExtra("foto", foto);
		    	startActivity(i);		    	
            }
        });
        
        RecuperarMisFotos nuevo = new RecuperarMisFotos();
        nuevo.execute();
	}
	
	
	class RecuperarMisFotos extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(Galeria.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/RecuperarMisFotos.php"; 
			
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("id", idusuario));
			Log.v("0000000", idusuario + " --- " + ip);
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);
			return estado;
		}

		protected void onPostExecute(String result) {

			lista = new ArrayList<ObjetoFoto>();
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject job;
				try {
					job = jsonarray.getJSONObject(i);
					ObjetoFoto nuevo = new ObjetoFoto();
					nuevo.setIdfoto(Integer.parseInt(job.getString("id")));
					nuevo.setIdusuario(Integer.parseInt(job.getString("idusuario")));
					nuevo.setTitulo(job.getString("titulo"));
					nuevo.setNombre(idusuario);
					nuevo.setUrl("http://" + ip + job.getString("url"));
					nuevo.setFecha(job.getString("fecha"));
					lista.add(nuevo);
					listaId.add(job.getString("id"));
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(),
							"Error al crear objeto json", Toast.LENGTH_LONG)
							.show();
				}
			}

			//CARGAR ADAPTADOR
			gridview.setAdapter(new ImageAdapter(Galeria.this,lista));
			//
			pDialog.dismiss();

		}
	}

}

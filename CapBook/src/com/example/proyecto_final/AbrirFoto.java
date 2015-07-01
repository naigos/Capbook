package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class AbrirFoto extends ListActivity {

	ObjetoFoto nuevaFoto;
	String idusuario, ip, comentario, valoracion, idfoto;
	double media;
	TextView tx, tx2, tx4;
	ImageView img;
	
	Adaptador3 adapt; 
	JSONArray jsonarray, jsonarray2;
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	ArrayList<ObjetoComentario> lista;
	ArrayList<String> listaId = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abrir_foto);		

		ip = getIntent().getStringExtra("ip");
		idusuario = getIntent().getStringExtra("id");
		setTitle("   Bienvenido " + idusuario);

		tx = (TextView) findViewById(R.id.textView1);
		tx2 = (TextView) findViewById(R.id.textView2);
		tx4 = (TextView) findViewById(R.id.textView4); 
		img = (ImageView) findViewById(R.id.imageView1);

		Bundle extras;
		extras = getIntent().getExtras();
		nuevaFoto = (ObjetoFoto) extras.getSerializable("foto");
		
		idfoto = String.valueOf(nuevaFoto.getIdfoto());

		tx.setText(nuevaFoto.getTitulo().toString());
		String nombre = nuevaFoto.getNombre();	
		String fecha = nuevaFoto.getFecha();
		tx2.setText("Foto subida por " + nombre + " el " + fecha);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// UNIVERSAL IMAGE LOADER SETUP
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this.getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache())
				.discCacheSize(100 * 1024 * 1024).build();

		ImageLoader.getInstance().init(config);
		// END - UNIVERSAL IMAGE LOADER SETUP

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(nuevaFoto.getUrl().toString(), img);
		
		RecuperarComentariosValoraciones nuevo = new RecuperarComentariosValoraciones();
		nuevo.execute();
		
	}
	
	public void comentar(View v){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Comentario");
		final EditText input = new EditText(this);
		int maxLength = 100;    
		input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
		alert.setView(input);

		alert.setPositiveButton("Comentar", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			comentario = input.getText().toString();
			Comentar nuevo = new Comentar();
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
	
	public void valorar(View v){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Valorar");
		final Spinner sp = new Spinner(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Valoraciones, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		alert.setView(sp);

		alert.setPositiveButton("Valorar", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			valoracion = sp.getSelectedItem().toString();
			Valorar nuevo = new Valorar();
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
	
	class RecuperarComentariosValoraciones extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(AbrirFoto.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/RecuperarComentarios.php"; 
			String URL_connect2 = "http://" + IP_Server + "/RecuperarValoraciones.php"; 
			
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("idfoto", idfoto));

			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);
			jsonarray2 = djson.getJSONfromURL(URL_connect2, parametrosPost);
			return estado;
		}

		protected void onPostExecute(String result) { 

			lista = new ArrayList<ObjetoComentario>();
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject job;
				try {
					job = jsonarray.getJSONObject(i);
					ObjetoComentario nuevo = new ObjetoComentario();
					nuevo.setTexto(job.getString("texto"));
					nuevo.setNombre(job.getString("nombre"));
					nuevo.setFecha(job.getString("fecha"));
					lista.add(nuevo);
					listaId.add(job.getString("idfoto"));
				} catch (JSONException e) {
				}
			}

			adapt = new Adaptador3(AbrirFoto.this, lista);
			setListAdapter(adapt);
			
			int n=0;
			media=0;
			for (int i = 0; i < jsonarray2.length(); i++) {
				JSONObject job;
				try {
					job = jsonarray2.getJSONObject(i);	
					media += job.getInt("valoracion");
				} catch (JSONException e) {
				}
				n++;
			}
			
			if(n > 0){
				media = media / n;	
			}			

			tx4.setText("Valoracion media: " + media);			
			pDialog.dismiss();

		}
	}	
	
	class Comentar extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(AbrirFoto.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/InsertarComentario.php";

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", idusuario));
			parametrosPost.add(new BasicNameValuePair("comentario", comentario));
			parametrosPost.add(new BasicNameValuePair("idfoto", idfoto));
			
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
				Toast.makeText(getApplicationContext(),"Comentado con éxito", Toast.LENGTH_LONG).show();
				finish();
				startActivity(getIntent());
			} else {
				Toast.makeText(getApplicationContext(), "Error, " + result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class Valorar extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(AbrirFoto.this);
			pDialog.setMessage("Cargando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/InsertarValoracion.php";

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", idusuario));
			parametrosPost.add(new BasicNameValuePair("valoracion", valoracion));
			parametrosPost.add(new BasicNameValuePair("idfoto", idfoto));
			
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
				Toast.makeText(getApplicationContext(),"Valorado con éxito", Toast.LENGTH_LONG).show();
				finish();
				startActivity(getIntent());
			} else {
				Toast.makeText(getApplicationContext(), "Error, " + result, Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public void onBackPressed() {
		finish();
	}
}

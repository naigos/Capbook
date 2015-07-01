package com.example.proyecto_final;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button b;
	ProgressDialog pDialog;
	DevuelveJSON djson = new DevuelveJSON();
	EditText login;
	EditText pass;
	JSONArray jsonarray;
	String ip = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		login = (EditText) findViewById(R.id.cPass);
		pass = (EditText) findViewById(R.id.cNombre);
		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				SharedPreferences pref = getSharedPreferences("com.example.proyecto_final_preferences", MODE_PRIVATE);
		    	ip = pref.getString("prefIp", "1");
				if(!login.getText().toString().equals("") && !pass.getText().toString().equals("") && !ip.equals("1")){
					Login nuevo = new Login();
					nuevo.execute();
				}else if(ip.equals("1")){
					Toast.makeText(getApplicationContext(), "Debes poner la direccion ip", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
				}				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Clase que conecta con el servidor

	class Login extends AsyncTask<Void, String, String> {

		protected void onPreExecute() {
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Comprobando...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setCancelable(true);
			pDialog.setProgress(0);
			pDialog.show();
		}

		protected String doInBackground(Void... params) {
			String estado = "";

			String IP_Server = ip;
			String URL_connect = "http://" + IP_Server + "/LoginPF.php";

			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();
			parametrosPost.add(new BasicNameValuePair("usuario", login
					.getText().toString()));
			parametrosPost.add(new BasicNameValuePair("password", pass
					.getText().toString()));
			
			jsonarray = djson.getJSONfromURL(URL_connect, parametrosPost);

			try {
				if (jsonarray.getJSONObject(0).getString("logstatus").equals("1")) {
					estado = "existe";
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
			if (result.equals("existe")) {				
				 Intent i = new Intent(MainActivity.this, Principal.class);
				 i.putExtra("ip", ip); 
				 i.putExtra("usuario", login.getText().toString()); 
				 startActivity(i);
			} else {
				Toast.makeText(getApplicationContext(),"Error en usuario / contraseña", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	//Abrir preferencias
	
	public void irPreferenciasMenu(MenuItem item){
    	Intent i = new Intent(this, Preferencias.class);
    	startActivity(i);
    }
	
	//Registrar
	
	public void registrar(View v){
    	Intent i = new Intent(this, Registrar.class);
    	startActivity(i);
    }

}

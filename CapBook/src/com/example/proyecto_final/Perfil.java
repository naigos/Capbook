package com.example.proyecto_final;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Perfil extends Activity {

	private ImageView image;
	private Button uploadButton,selectImageButton,galeriaButton;
	private Bitmap bitmap;
	private static final int PICK_IMAGE = 1;
	private EditText edtx;
	boolean subir = false;
	String picturePath;
	String id, ip, foto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);

		ip = getIntent().getStringExtra("ip");
		id = getIntent().getStringExtra("usuario");

		edtx = (EditText) findViewById(R.id.editText1);
		image = (ImageView) findViewById(R.id.uploadImage);
		uploadButton = (Button) findViewById(R.id.uploadButton);
		selectImageButton = (Button) findViewById(R.id.selectImageButton);
		galeriaButton = (Button) findViewById(R.id.galeria);
		
		selectImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImageFromGallery();
			}
		});

		uploadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(subir){
					new UploaderFoto().execute(picturePath);
				}else{
					Toast.makeText(getApplicationContext(),"Debes elegir la foto a subir", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		galeriaButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				Intent i = new Intent(Perfil.this, Galeria.class);
		    	i.putExtra("ip", ip); 
		    	i.putExtra("usuario", id);
		    	startActivity(i);
			}		
		});
		
	}

	public void selectImageFromGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				PICK_IMAGE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);
			cursor.close();

			decodeFile(picturePath);

		}
	}
	
	public void decodeFile(String filePath) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, o);

		// The new size we want to scale to
		final int REQUIRED_SIZE = 512;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		bitmap = BitmapFactory.decodeFile(filePath, o2);

		image.setImageBitmap(bitmap);
		subir=true;
	}
	
	/*
	 * Clase asincrona para subir la foto
	 */
	class UploaderFoto extends AsyncTask<String, Void, Void>{

		ProgressDialog pDialog;
		String miFoto = "";
		
		String IP_Server = ip;
		String URL_connect = "http://" + IP_Server + "/Up.php";
		
		int nslash = picturePath.lastIndexOf('/');
		String namefoto = picturePath.substring(nslash);
		
		@Override
		protected Void doInBackground(String... params) {
			miFoto = params[0];
			try { 
				
				Log.v("0000000", URL_connect + " --- " + namefoto);
				Log.v("0000000", ip + " --- " + picturePath);
				
				HttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
				HttpPost httppost = new HttpPost(URL_connect);
				
				MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);		
				
				Bitmap bmp = BitmapFactory.decodeFile(miFoto);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bmp.compress(CompressFormat.JPEG, 70, bos);
				InputStream in = new ByteArrayInputStream(bos.toByteArray());
				ContentBody foto = new InputStreamBody(in, "image/jpeg", namefoto);			
				
				mpEntity.addPart("fotoUp", foto);
				mpEntity.addPart("usuario",new StringBody(id));
				mpEntity.addPart("titulo",new StringBody(edtx.getText().toString()));
				httppost.setEntity(mpEntity);
				httpclient.execute(httppost);
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Perfil.this);
	        pDialog.setMessage("Subiendo la imagen, espere." );
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setCancelable(true);
	        pDialog.show();
		}
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(),"Imagen subida con éxito", Toast.LENGTH_LONG).show();
			subir=false;
			image.setImageBitmap(null);
			edtx.setText("");
		}
	}
	
	public void irAGaleria(){
		
	}
	
	@Override
	public void onBackPressed() {
		// nada
	}

}

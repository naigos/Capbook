package com.example.proyecto_final;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class Adaptador2 extends BaseAdapter {	

	Activity actividad;
	ArrayList<ObjetoFoto> lista;
	
	public Adaptador2(Activity act, ArrayList<ObjetoFoto> list){
		this.actividad=act;
		this.lista=list;
	}
	
	@Override
	public int getCount() {		
		return lista.size();
	}

	@Override
	public Object getItem(int pos) {		
		return lista.get(pos);
	}

	@Override
	public long getItemId(int pos) {		
		return pos;
	}

	@Override
	public View getView(int pos, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = actividad.getLayoutInflater();
		View v = inflater.inflate(R.layout.elemento2, null, true);	

		String titulo = lista.get(pos).getTitulo();		
		TextView txtitulo = (TextView) v.findViewById(R.id.nuevo);		
		txtitulo.setText(titulo);
		
		String nombre = lista.get(pos).getNombre();	
		String fecha = lista.get(pos).getFecha();	
		TextView txnombre = (TextView) v.findViewById(R.id.textView1);		
		txnombre.setText("Foto subida por " + nombre + " el " + fecha);		

		String url = lista.get(pos).getUrl();
		ImageView img = (ImageView) v.findViewById(R.id.imageView1);

	 	// UNIVERSAL IMAGE LOADER SETUP
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();	
		
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				actividad.getApplicationContext())
					.defaultDisplayImageOptions(defaultOptions)
					.memoryCache(new WeakMemoryCache())
					.discCacheSize(100 * 1024 * 1024).build();

		ImageLoader.getInstance().init(config);	
		// END - UNIVERSAL IMAGE LOADER SETUP
		
		ImageLoader imageLoader = ImageLoader.getInstance();        
	
		// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view 
		//  which implements ImageAware interface)
		imageLoader.displayImage(url, img);
		// Load image, decode it to Bitmap and return Bitmap to callback
		imageLoader.loadImage(url, new SimpleImageLoadingListener() {
	    @Override
	    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
	        // Do whatever you want with Bitmap
	    }
		});

		return v;
	}

}
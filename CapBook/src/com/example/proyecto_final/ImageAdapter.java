package com.example.proyecto_final;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ObjetoFoto> lista;
    int tam;

    public ImageAdapter(Context c, ArrayList<ObjetoFoto> list) {
        mContext = c;
        this.lista=list;
    }

    public int getCount() {
    	return lista.size();
    }

    public Object getItem(int position) {
    	return lista.get(position);
    }

    public long getItemId(int position) {
    	return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        tam = (int) displayMetrics.widthPixels/3;
    	
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(tam, tam));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        
        String url = lista.get(position).getUrl();
        
        	// UNIVERSAL IMAGE LOADER SETUP
     		@SuppressWarnings("deprecation")
     		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
     				.cacheOnDisc(true).cacheInMemory(true)
     				.imageScaleType(ImageScaleType.EXACTLY)
     				.displayer(new FadeInBitmapDisplayer(300)).build();

     		@SuppressWarnings("deprecation")
     		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
     				mContext.getApplicationContext())
     				.defaultDisplayImageOptions(defaultOptions)
     				.memoryCache(new WeakMemoryCache())
     				.discCacheSize(100 * 1024 * 1024).build();

     		ImageLoader.getInstance().init(config);
     		// END - UNIVERSAL IMAGE LOADER SETUP

     		ImageLoader imageLoader = ImageLoader.getInstance();
     		imageLoader.displayImage(url, imageView);

       // imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
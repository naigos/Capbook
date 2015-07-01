package com.example.proyecto_final;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador extends BaseAdapter {	

	Activity actividad;
	ArrayList<String> lista;
	
	public Adaptador(Activity act, ArrayList<String> list){
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
		View v = inflater.inflate(R.layout.elemento, null, true);
		
		String c = lista.get(pos);
		
		TextView nombre = (TextView) v.findViewById(R.id.nuevo);
		
		nombre.setText(c);
		
		return v;
	}

}
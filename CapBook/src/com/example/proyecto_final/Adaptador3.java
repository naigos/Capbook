package com.example.proyecto_final;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador3 extends BaseAdapter {	

	Activity actividad;
	ArrayList<ObjetoComentario> lista;
	
	public Adaptador3(Activity act, ArrayList<ObjetoComentario> list){
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
		View v = inflater.inflate(R.layout.elemento3, null, true);
		
		String usuario = lista.get(pos).getNombre();
		String texto = lista.get(pos).getTexto();
		String fecha = lista.get(pos).getFecha();
		
		TextView nombre = (TextView) v.findViewById(R.id.nuevo);		
		nombre.setText(usuario + " escribió:" );
		
		TextView text = (TextView) v.findViewById(R.id.textView1); 
		text.setText(texto);
		
		TextView fech = (TextView) v.findViewById(R.id.textView2); 
		fech.setText(fecha);
		
		return v;
	}

}
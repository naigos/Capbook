package com.example.proyecto_final;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class Principal extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String ip = getIntent().getStringExtra("ip");
		String id = getIntent().getStringExtra("usuario");
		
		setTitle("   Bienvenido " + id);
		
		//creamos el contenedor de Tabs
    	TabHost host = getTabHost();
    	
    	//creamos los intent
    	Intent inicio = new Intent(Principal.this, Inicio.class);
    	inicio.putExtra("ip", ip); 
    	inicio.putExtra("usuario", id);
    	
    	Intent perfil = new Intent(Principal.this, Perfil.class);
    	perfil.putExtra("ip", ip); 
    	perfil.putExtra("usuario", id);
    	
    	Intent personas = new Intent(Principal.this, Personas.class);
    	personas.putExtra("ip", ip); 
    	personas.putExtra("usuario", id);
    	
    	Intent configuracion = new Intent(Principal.this, Configuracion.class);
    	configuracion.putExtra("ip", ip); 
    	configuracion.putExtra("usuario", id);
    	
    	Resources res = getResources();
    	
    	//Añadimos cada tab, que al ser pulsadas abren sus respectivas Activities
    	/*host.addTab(host.newTabSpec("Inicio").setIndicator("Inicio").setContent(inicio));
    	host.addTab(host.newTabSpec("Personas").setIndicator("Personas").setContent(personas));
    	host.addTab(host.newTabSpec("Configuracion").setIndicator("Configuracion").setContent(configuracion));*/
    	
    	host.setup();
    	 
    	TabHost.TabSpec spec=host.newTabSpec("Inicio");
    	spec.setContent(inicio);
    	spec.setIndicator("",res.getDrawable(R.drawable.eye));
    	host.addTab(spec);
    	
    	spec=host.newTabSpec("Perfil");
    	spec.setContent(perfil);
    	spec.setIndicator("",res.getDrawable(R.drawable.profle));
    	host.addTab(spec);
    	 
    	spec=host.newTabSpec("Personas");
    	spec.setContent(personas);
    	spec.setIndicator("",res.getDrawable(R.drawable.shoeprints));
    	host.addTab(spec);
    	
    	spec=host.newTabSpec("Configuracion");
    	spec.setContent(configuracion);
    	spec.setIndicator("",res.getDrawable(R.drawable.tools));
    	host.addTab(spec);
    	 
    	host.setCurrentTab(0);
	}
	
	@Override
	public void onBackPressed() {
		//nada
	}
}
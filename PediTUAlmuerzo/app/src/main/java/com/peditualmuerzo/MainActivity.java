package com.peditualmuerzo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;

import com.peditualmuerzo.dataAccess.PedidoDataFirebase;

public class MainActivity extends Activity {

	private TextView  mensajeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Firebase.setAndroidContext(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void redireccionar(View view){
		Intent intent = new Intent(this, ConfirmarSolicitudAlimentacionActivity.class);
		startActivity(intent);
	}

	public void redireccionarHistorial(View view){
		Intent intent = new Intent(this, HistorialActivity.class);
		startActivity(intent);
	}
}

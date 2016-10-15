package com.peditualmuerzo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;

import static android.widget.Toast.makeText;

public class ConfirmarSolicitudAlimentacionActivity extends Activity {

	private TextView tvPlatoElegido;
	private EditText etCantidad;
	private EditText etComentarios;
	private Button btnSolicitar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmar_solicitud_alimentacion);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//Iniciar objetos de vistas
		tvPlatoElegido = (TextView) findViewById(R.id.tvPlatoASolicitar);
		etCantidad = (EditText) findViewById(R.id.etCantidad);
		etComentarios = (EditText) findViewById(R.id.etObservaciones);
		btnSolicitar = (Button) findViewById(R.id.btnSolicitar);
		//Toma el argumento de la actividad como contexto
		Firebase.setAndroidContext(this);

		btnSolicitar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Firebase ref = new Firebase(PedidoDataFirebase.FIREBASE_URL);

				String platoSeleccionado = tvPlatoElegido.getText().toString().trim();
				String cantidad = etCantidad.getText().toString().trim();
				String comentarios = etComentarios.getText().toString();

				ItemPedido itemPedido = new ItemPedido();
				itemPedido.setCantidad(Integer.parseInt(cantidad));
				itemPedido.setComentarios(comentarios);
				Plato plato = new Plato();
				plato.setNombrePlato(platoSeleccionado);
				itemPedido.getPlatos().add(plato);

				Pedido pedido = new Pedido();
				pedido.getItems().add(itemPedido);

				ref.child("Pedidos").push().setValue(pedido);

				Toast.makeText(ConfirmarSolicitudAlimentacionActivity.this, "Pedido realizado", Toast.LENGTH_SHORT).show();
				Toast.makeText(ConfirmarSolicitudAlimentacionActivity.this, "Cancelalo, en caso de no poder retirarlo. ;)", Toast.LENGTH_LONG).show();

				etCantidad.setText("");
				etComentarios.setText("");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_solicitud_alimentacion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}

		switch (item.getItemId()) {
        case android.R.id.home:
            this.finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
		}


	}


}

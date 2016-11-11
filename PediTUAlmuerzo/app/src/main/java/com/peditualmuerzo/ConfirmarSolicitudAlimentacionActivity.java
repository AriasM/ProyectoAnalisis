package com.peditualmuerzo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.peditualmuerzo.dataAccess.PedidoData;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.dominio.UsuarioServicio;
import com.peditualmuerzo.presenter.SolicitudPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.makeText;

public class ConfirmarSolicitudAlimentacionActivity extends Activity {

	private TextView tvPlatoElegido, tvPlatoOpcionalElegido;
	private EditText etCantidad, etCantidadOpcional;
	private EditText etComentarios, etComentarioOpcional;
	private Button btnSolicitar;
	private SolicitudPresenter presenter;
	private List<Plato> platos;
	private Plato platoPrincipal;
	private LinearLayout layoutPlatoOpcional;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmar_solicitud_alimentacion);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		layoutPlatoOpcional = (LinearLayout) findViewById(R.id.linearLayoutPlatoSeleccionado);
		platoPrincipal = new Plato();
		platoPrincipal.setNombrePlato("Carne en salsa");

		platos = (List<Plato>) getIntent().getExtras().getSerializable("platos");

		if(platos.size() > 0)
		{
			for (Plato platoActual : platos){
				if(!platoActual.isOpcional()){
					tvPlatoElegido = (TextView) findViewById(R.id.tvPlatoASolicitar);
					tvPlatoElegido.setText(platoActual.getNombrePlato());
				}
				else{
					//layoutPlatoOpcional.setVisibility(View.VISIBLE);
					tvPlatoOpcionalElegido = (TextView) findViewById(R.id.tvPlatoASolicitarOpcional);
					tvPlatoOpcionalElegido.setText(platoActual.getNombrePlato());
				}
			}
		}

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

	public void solicitarPedido(View view){
		etCantidad = (EditText) findViewById(R.id.etCantidad);
		etCantidadOpcional = (EditText) findViewById(R.id.etCantidadPlatoOpcional);
		etComentarios = (EditText) findViewById(R.id.etObservaciones);
		etComentarioOpcional = (EditText) findViewById(R.id.etObservacionesPlatoOpcional);
		btnSolicitar = (Button) findViewById(R.id.btnSolicitar);

		PedidoData solicitudAlimentacion = new PedidoDataFirebase(this);
		presenter = new SolicitudPresenter(solicitudAlimentacion);

		String cantidad = etCantidad.getText().toString().trim();
		String comentarios = etComentarios.getText().toString();

		if(cantidad.equalsIgnoreCase("")){
			etCantidad.setError("Debe ingresar una cantidad");
			etCantidad.requestFocus();

		}
		else{
			//TODO agregar el usuario cuando ya se tengan
			UsuarioServicio user = new UsuarioServicio();
			user.setNombreUsuario("user1");

			Pedido pedido = new Pedido();

			if(platos.size()==1){
				if(platos.get(0).isOpcional()){
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setCantidad(Integer.parseInt(etCantidadOpcional.getText().toString()));
					itemPedido.setComentarios(etComentarioOpcional.getText().toString());
					Plato plato = new Plato();
					plato.setIdPlato(platos.get(0).getIdPlato());
					itemPedido.setPlato(plato);
					pedido.getItems().add(itemPedido);
				}else{
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setCantidad(Integer.parseInt(etCantidad.getText().toString()));
					itemPedido.setComentarios(etComentarios.getText().toString());
					Plato plato = new Plato();
					plato.setIdPlato(platos.get(0).getIdPlato());
					itemPedido.setPlato(plato);
					pedido.getItems().add(itemPedido);
				}

			}
			else{
				for(Plato platoActual : platos){
					if(platoActual.isOpcional()){
						ItemPedido itemPedido = new ItemPedido();
						itemPedido.setCantidad(Integer.parseInt(etCantidadOpcional.getText().toString()));
						itemPedido.setComentarios(etComentarioOpcional.getText().toString());
						Plato platoAgregar = new Plato();
						platoAgregar.setIdPlato(platoActual.getIdPlato());
						itemPedido.setPlato(platoAgregar);
						pedido.getItems().add(itemPedido);
					}
					else{
						ItemPedido itemPedido = new ItemPedido();
						itemPedido.setCantidad(Integer.parseInt(etCantidad.getText().toString()));
						itemPedido.setComentarios(etComentarios.getText().toString());
						Plato platoAgregar = new Plato();
						platoAgregar.setIdPlato(platoActual.getIdPlato());
						itemPedido.setPlato(platoAgregar);
						pedido.getItems().add(itemPedido);
					}
				}
			}

			pedido.setEstado("Pendiente");
			pedido.setUsuario(user);
			pedido.setFechaPedido(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

			String resultado = presenter.solicitarServicio(pedido);

			Toast.makeText(ConfirmarSolicitudAlimentacionActivity.this, resultado, Toast.LENGTH_SHORT).show();
			Toast.makeText(ConfirmarSolicitudAlimentacionActivity.this, "Cancelalo, en caso de no poder retirarlo. ;)", Toast.LENGTH_LONG).show();

			etCantidad.setText("");
			etComentarios.setText("");
		}


	}




}

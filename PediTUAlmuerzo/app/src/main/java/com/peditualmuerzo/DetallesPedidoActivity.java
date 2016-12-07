package com.peditualmuerzo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peditualmuerzo.dataAccess.PedidoData;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.ItemPlatoComponente;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.presenter.SolicitudPresenter;

import java.util.Iterator;
import java.util.List;

public class DetallesPedidoActivity extends Activity {

    private ListView historialListView;
    private TextView tvFechaPedido;
    private TextView tvEstadoPedido;
    private Pedido pedido;
    private DatabaseReference ref;
    private DatabaseReference mensajeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);
        historialListView = (ListView) findViewById(R.id.historialView);
        tvFechaPedido = (TextView) findViewById(R.id.tvFechaPedido);
        tvEstadoPedido = (TextView) findViewById(R.id.tvEstadoPedido);
        ref = FirebaseDatabase.getInstance().getReference();
        mensajeRef = ref.child("Platos");
        pedido = (Pedido) getIntent().getExtras().getSerializable("pedido");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalles_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.action_detallesDelPedidos:
                Intent intent = new Intent(this, ModificarPedidoRealizadoActivity.class);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
                return true;
            case R.id.action_cancelarPedido:
                AlertDialog.Builder mensajeConfirmacion = new AlertDialog.Builder(this);
                mensajeConfirmacion.setTitle("Importante");
                mensajeConfirmacion.setMessage("¿De verdad desea cancelar el pedido?");
                mensajeConfirmacion.setCancelable(false);
                mensajeConfirmacion.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast t = Toast.makeText(DetallesPedidoActivity.this, "¡Perfecto! Excelente decisión", Toast.LENGTH_SHORT);
                        t.show();
                    }
                });
                mensajeConfirmacion.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PedidoData pedidoData = new PedidoDataFirebase(DetallesPedidoActivity.this);
                        SolicitudPresenter sPresenter = new SolicitudPresenter(pedidoData);
                        sPresenter.cancelarPedido(pedido);

                        Toast t = Toast.makeText(DetallesPedidoActivity.this, "Pedido cancelado exitosamente", Toast.LENGTH_SHORT);
                        t.show();

                        Intent intent = new Intent(DetallesPedidoActivity.this, HistorialActivity.class);
                        startActivity(intent);
                    }
                });

                mensajeConfirmacion.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onStart (){

        super.onStart();

        mensajeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> ite = dataSnapshot.getChildren().iterator();

                while(ite.hasNext()){

                    DataSnapshot data =ite.next();
                    Plato plato  = data.getValue(Plato.class);
                    plato.setIdPlato(data.getKey());

                    for (ItemPedido itemLista:pedido.getItems()) {

                        if(itemLista.getPlato().getIdPlato().equals(plato.getIdPlato())){

                            itemLista.setPlato(plato);
                        }

                    }
                }
                ItemPedidoAdapter itemPedidoAdapteredidoAdapter = new ItemPedidoAdapter(getApplicationContext(), R.layout.item_platillo_pedido_row, pedido.getItems());
                historialListView.setAdapter(itemPedidoAdapteredidoAdapter);
                tvFechaPedido.setText("Fecha del pedido: "+pedido.getFechaPedido());
                tvEstadoPedido.setText("Estado del pedido: "+pedido.getEstado());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(DetallesPedidoActivity.this, "No se ha podido contactar con el catálogo de platos", Toast.LENGTH_LONG).show();
            }
        });
    }
    public class ItemPedidoAdapter extends ArrayAdapter {

        private List<ItemPedido> itemsPedido;
        private int resources;
        private LayoutInflater inflater;
        private TextView tvNombrePlato;
        private TextView tvComponentes;
        private TextView tvCantidad;
        private TextView tvPrecioPlato;
        private TextView tvComentarios;

        public ItemPedidoAdapter(Context context, int resource, List<ItemPedido> objects) {

            super(context, resource, objects);
            itemsPedido = objects;
            resources = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){

                convertView = inflater.inflate(resources, null);
            }

            tvComentarios = (TextView) convertView.findViewById(R.id.tvComentarios);
            tvComponentes = (TextView) convertView.findViewById(R.id.tvComponentes);
            tvCantidad = (TextView) convertView.findViewById(R.id.tvCantidad);
            tvNombrePlato = (TextView) convertView.findViewById(R.id.tvNombrePlato);
            tvPrecioPlato = (TextView) convertView.findViewById(R.id.tvPrecioPlato);

            tvComentarios.setText("Comentarios adicionales: "+itemsPedido.get(position).getComentarios());
            tvPrecioPlato.setText("Precio: "+itemsPedido.get(position).getPrecioPlato());
            tvNombrePlato.setText("Platillo: "+itemsPedido.get(position).getPlato().getNombrePlato());
            tvCantidad.setText("Cantidad: "+itemsPedido.get(position).getCantidad());

            String componentes = "Componentes del plato: ";

            for (ItemPlatoComponente item:itemsPedido.get(position).getPlato().getItems()) {

                componentes += (item.getComponente().getNombreComponentePlato()+", ");
            }

            tvComponentes.setText(componentes);

            return convertView;
        }
    }
}
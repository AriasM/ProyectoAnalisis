package com.peditualmuerzo;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.ItemPlatoComponente;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;

import java.util.Iterator;
import java.util.List;

public class DetallesPedidoActivity extends Activity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mensajeRef = ref.child("platillos");
    private ListView historialListView;
    private TextView tvFechaPedido;
    private TextView tvEstadoPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);
        historialListView = (ListView) findViewById(R.id.historialView);
        tvFechaPedido = (TextView) findViewById(R.id.tvFechaPedido);
        tvEstadoPedido = (TextView) findViewById(R.id.tvEstadoPedido);
    }

    public void onStart (){

        super.onStart();

        final Pedido pedido = (Pedido) getIntent().getExtras().getSerializable("pedido");

        mensajeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> ite = dataSnapshot.getChildren().iterator();

                while(ite.hasNext()){

                    DataSnapshot data =ite.next();
                    Plato plato  = data.getValue(Plato.class);
                    plato.setIdPlatillo(data.getKey());

                    for (ItemPedido itemLista:pedido.getItems()) {

                        if(itemLista.getPlato().getIdPlatillo().equals(plato.getIdPlatillo())){

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
            tvPrecioPlato.setText("Precio: "+itemsPedido.get(position).getPrecioPlatillo());
            tvNombrePlato.setText("Platillo: "+itemsPedido.get(position).getPlato().getNombrePlatillo());
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
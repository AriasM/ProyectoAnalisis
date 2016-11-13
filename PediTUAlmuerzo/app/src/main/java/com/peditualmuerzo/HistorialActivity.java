package com.peditualmuerzo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peditualmuerzo.dominio.ComponentePlato;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.ItemPlatoComponente;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.dominio.Usuario;
import com.peditualmuerzo.dominio.UsuarioServicio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HistorialActivity extends Activity {

    private ListView historialListView;
    private List<Pedido> pedidos;
    private int posicion;
    private DatabaseReference ref;
    private DatabaseReference mensajeRef;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        historialListView = (ListView) findViewById(R.id.historialView);
        ref = FirebaseDatabase.getInstance().getReference();
        mensajeRef = ref.child("Pedidos");
    }

    public void onStart (){

        super.onStart();
        pedidos = new ArrayList<Pedido>();

        listener = mensajeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pedidos.clear();

                Iterator<DataSnapshot> ite = dataSnapshot.getChildren().iterator();

                while(ite.hasNext()){

                    DataSnapshot data = ite.next();
                    Pedido pedido  = data.getValue(Pedido.class);
                    pedido.setIdPedido(data.getKey());
                    pedidos.add(pedido);
                }

                PedidoAdapter pedidoAdapter = new PedidoAdapter(getApplicationContext(), R.layout.row, pedidos);
                historialListView.setAdapter(pedidoAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClose(){
        mensajeRef.removeEventListener(listener);
    }

    public void redireccionar(View view){
        Intent intent = new Intent(this, DetallesPedidoActivity.class);
        intent.putExtra("pedido", pedidos.get(posicion));
        startActivity(intent);
    }

    public class PedidoAdapter extends ArrayAdapter {

        private List<Pedido> pedidos;
        private int resources;
        private LayoutInflater inflater;
        private ImageView imagenPlatoPedido;
        private TextView fechaPedido;
        private TextView estadoPedido;
        private TextView totalPedido;
        private Button btnDetallesPedido;

        public PedidoAdapter(Context context, int resource, List<Pedido> objects) {

            super(context, resource, objects);
            pedidos = objects;
            resources = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null){

                convertView = inflater.inflate(resources, null);
            }

            imagenPlatoPedido = (ImageView) convertView.findViewById(R.id.imagenPlatoPedido);
            fechaPedido = (TextView) convertView.findViewById(R.id.fechaPedido);
            estadoPedido = (TextView) convertView.findViewById(R.id.estadoPedido);
            totalPedido = (TextView) convertView.findViewById(R.id.totalPedido);
            btnDetallesPedido = (Button) convertView.findViewById(R.id.btnDetallesPedido);

            fechaPedido.setText("Fecha del pedido: "+pedidos.get(position).getFechaPedido());
            estadoPedido.setText("Estados del pedido: "+pedidos.get(position).getEstado());

            float costoPedido = 0;

            for (ItemPedido item:pedidos.get(position).getItems()) {

                costoPedido += (item.getCantidad()*item.getPrecioPlato());
            }

            totalPedido.setText("Costo del pedido: "+costoPedido);

            btnDetallesPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    posicion = position;
                    redireccionar(view);
                }
            });
            return convertView;
        }
    }
}
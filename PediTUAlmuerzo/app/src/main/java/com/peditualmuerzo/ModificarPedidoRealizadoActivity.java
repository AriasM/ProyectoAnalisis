package com.peditualmuerzo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peditualmuerzo.dataAccess.PedidoData;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.presenter.SolicitudPresenter;

public class ModificarPedidoRealizadoActivity extends Activity {

    private EditText etComentariosPrincipal, etCantidadPrincipal, etComentariosOpcional, etCantidadOpcional;
    private TextView tvPlatoModificar, tvPlatoOpcionalModificar;
    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pedido_realizado);

        etCantidadOpcional = (EditText) findViewById(R.id.etCantidadPlatoOpcional);
        etCantidadPrincipal = (EditText) findViewById(R.id.etCantidad);
        etComentariosPrincipal = (EditText) findViewById(R.id.etObservaciones);
        etComentariosOpcional = (EditText) findViewById(R.id.etObservacionesPlatoOpcional);
        tvPlatoModificar = (TextView) findViewById(R.id.tvPlatoASolicitar);
        tvPlatoOpcionalModificar = (TextView) findViewById(R.id.tvPlatoASolicitarOpcional);

        //TODO traer el pedido del historial
        pedido = new Pedido();
        pedido.setIdPedido("-KWKF6m2B56-RFY-vRoj");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCantidad(2);
        itemPedido.setComentarios("Prueba 4");
        Plato plato = new Plato();
        plato.setOpcional(false);
        plato.setNombrePlato("Arroz con pollo");
        plato.setIdPlato("bajsibfia");
        itemPedido.setPlato(plato);
        pedido.getItems().add(itemPedido);

        for(ItemPedido itemPedidoActual : pedido.getItems()){
            if(!itemPedidoActual.getPlato().isOpcional()){
                tvPlatoModificar.setText(itemPedido.getPlato().getNombrePlato());
                etCantidadPrincipal.setText(itemPedidoActual.getCantidad()+"", TextView.BufferType.EDITABLE);
                etComentariosPrincipal.setText(itemPedidoActual.getComentarios());
            }
            else{
                tvPlatoOpcionalModificar.setText(itemPedido.getPlato().getNombrePlato());
                etCantidadOpcional.setText(itemPedidoActual.getCantidad()+"", TextView.BufferType.EDITABLE);
                etComentariosOpcional.setText(itemPedidoActual.getComentarios());
            }
        }
    }

    public void modificarPedido(View view){
        if(etCantidadPrincipal.getText().toString().equals("")){
            etCantidadPrincipal.setError("Debe ingresar una cantidad");
            etCantidadPrincipal.requestFocus();
        }
        else{
            for(ItemPedido itemPedidoActual : pedido.getItems()){
                if(!itemPedidoActual.getPlato().isOpcional()){
                    itemPedidoActual.setCantidad(Integer.parseInt(etCantidadPrincipal.getText().toString()));
                    itemPedidoActual.setComentarios(etComentariosPrincipal.getText().toString());
                }else{
                    itemPedidoActual.setCantidad(Integer.parseInt(etCantidadOpcional.getText().toString()));
                    itemPedidoActual.setComentarios(etComentariosOpcional.getText().toString());
                }
            }

            PedidoData pedidoData = new PedidoDataFirebase(this);
            SolicitudPresenter modificarPedidoPresenter = new SolicitudPresenter(pedidoData);
            modificarPedidoPresenter.modificarPedido(pedido);

            Toast.makeText(ModificarPedidoRealizadoActivity.this, "¡Excelente! Pedido modificado", Toast.LENGTH_SHORT).show();
            etComentariosPrincipal.setText("");
            etCantidadPrincipal.setText("");
            etCantidadOpcional.setText("");
            etComentariosOpcional.setText("");


        }
    }

}

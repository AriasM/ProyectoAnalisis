package com.peditualmuerzo.presenter;

import com.peditualmuerzo.dataAccess.PedidoData;
import com.peditualmuerzo.dominio.Pedido;

/**
 * Created by Manuel on 08/11/2016.
 */

public class SolicitudPresenter {

    public final PedidoData pedidoDataView;

    public SolicitudPresenter(PedidoData pedidoDataView){
        this.pedidoDataView = pedidoDataView;
    }

    public String solicitarServicio(Pedido pedido){
        return  pedidoDataView.solicitarPedido(pedido);
    }

    public void modificarPedido(Pedido pedido){
        pedidoDataView.modificarPedido(pedido);
    }

    public void cancelarPedido(Pedido pedido){
        pedidoDataView.cancelarPedido(pedido);
    }

}

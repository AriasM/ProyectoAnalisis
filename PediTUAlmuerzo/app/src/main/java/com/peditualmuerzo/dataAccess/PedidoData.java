package com.peditualmuerzo.dataAccess;

import com.peditualmuerzo.dominio.Pedido;

public interface PedidoData {
	public String solicitarPedido(Pedido pedido);
	public void modificarPedido(Pedido pedido);
	public void cancelarPedido(Pedido pedido);
}

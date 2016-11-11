package com.peditualmuerzo;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

public class ModificarPedidoRealizadoActivity extends Activity {

    private EditText etComentariosPrincipal, etCantidadPrincipal, etComentariosOpcional, etCantidadOpcional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pedido_realizado);

        etCantidadOpcional = (EditText) findViewById(R.id.etCantidadPlatoOpcional);
        etCantidadPrincipal = (EditText) findViewById(R.id.etCantidad);
        etComentariosPrincipal = (EditText) findViewById(R.id.etObservaciones);
        etComentariosOpcional = (EditText) findViewById(R.id.etObservacionesPlatoOpcional);
    }

}

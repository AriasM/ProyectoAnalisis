package com.peditualmuerzo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.peditualmuerzo.dataAccess.PedidoData;
import com.peditualmuerzo.dataAccess.PedidoDataFirebase;
import com.peditualmuerzo.dataAccess.UsuarioData;
import com.peditualmuerzo.dataAccess.UsuarioDataFireBase;
import com.peditualmuerzo.dominio.ItemPedido;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Plato;
import com.peditualmuerzo.dominio.Usuario;
import com.peditualmuerzo.dominio.UsuarioServicio;
import com.peditualmuerzo.presenter.SolicitudPresenter;
import com.peditualmuerzo.presenter.UsuarioPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegistroActivity extends Activity {

    private EditText etNombre;
    private EditText etPrimerApellido;
    private EditText etSegundoApellido;
    private EditText etCorreoElectronico;
    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.etNombre = (EditText) findViewById(R.id.etNombre);
        this.etPrimerApellido = (EditText) findViewById(R.id.etPrimerApellido);
        this.etSegundoApellido = (EditText) findViewById(R.id.etSegundoApellido);
        this.etCorreoElectronico = (EditText) findViewById(R.id.etCorreoElectronico);
        this.etUsuario = (EditText) findViewById(R.id.etUsuario);
        this.etContrasena = (EditText) findViewById(R.id.etContrasena);
        this.btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
    }

    public void nuevoUsuarioServicio(View view){

        UsuarioDataFireBase usuarioData = new UsuarioDataFireBase(this);
        UsuarioPresenter usuarioPresenter = new UsuarioPresenter(usuarioData);

        UsuarioServicio usuarioServicio = new UsuarioServicio();

        usuarioServicio.setNombre(etNombre.getText().toString().trim());
        usuarioServicio.setPrimerApellido(etPrimerApellido.getText().toString().trim());
        usuarioServicio.setSegundoApellido(etSegundoApellido.getText().toString().trim());
        usuarioServicio.setCorreo(etCorreoElectronico.getText().toString().trim());
        usuarioServicio.setNombreUsuario(etUsuario.getText().toString().trim());
        usuarioServicio.setContrasena(etContrasena.getText().toString().trim());

        String resultado = usuarioPresenter.nuevoUsuario(usuarioServicio);
        Toast.makeText(RegistroActivity.this,resultado, Toast.LENGTH_LONG).show();

        etNombre.setText("");
        etPrimerApellido.setText("");
        etSegundoApellido.setText("");
        etCorreoElectronico.setText("");
        etUsuario.setText("");
        etContrasena.setText("");
    }

}

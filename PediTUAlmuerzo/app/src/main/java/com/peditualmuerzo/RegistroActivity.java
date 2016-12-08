package com.peditualmuerzo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
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

public class RegistroActivity extends Activity implements Validator.ValidationListener{


    @NotEmpty(message = "Debe ingresar su nombre")
    private EditText etNombre;
    @NotEmpty(message = "Debe ingresar su primer apellido")
    private EditText etPrimerApellido;
    @NotEmpty(message = "Debe ingresar su segundo apellido")
    private EditText etSegundoApellido;
    @Email(message = "Debe ingresar un correo válido")
    private EditText etCorreoElectronico;
    @NotEmpty(message = "Debe ingresar su nombre de usuario")
    private EditText etUsuario;
    @NotEmpty(message = "Debe ingresar su contraseña")
    private EditText etContrasena;
    private Button btnRegistrar;
    private Validator validator;

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
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
        this.btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        this.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public void nuevoUsuarioServicio(){

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
    @Override
    public void onValidationSucceeded() {

        nuevoUsuarioServicio();

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

        Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
    }
}

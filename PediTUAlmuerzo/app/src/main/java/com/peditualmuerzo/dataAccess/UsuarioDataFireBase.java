package com.peditualmuerzo.dataAccess;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peditualmuerzo.HistorialActivity;
import com.peditualmuerzo.RegistroActivity;
import com.peditualmuerzo.dominio.Pedido;
import com.peditualmuerzo.dominio.Usuario;
import com.peditualmuerzo.dominio.UsuarioServicio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by victo on 20/11/2016.
 */

public class UsuarioDataFireBase implements UsuarioData {

    private DatabaseReference ref;
    private DatabaseReference mensajeRef;
    private String estadoTransaccion;
    private ValueEventListener listener;
    private Context context;
    private boolean existeUsuario;

    public UsuarioDataFireBase(Context context){

        ref = FirebaseDatabase.getInstance().getReference();
        mensajeRef = ref.child("UsuarioServicio");
        this.context = context;
    }

    @Override
    public String nuevoUsuario(final UsuarioServicio usuarioServicio) {

        existeUsuario = false;

        listener = mensajeRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> ite = dataSnapshot.getChildren().iterator();

                while (ite.hasNext()) {

                    DataSnapshot data = ite.next();
                    UsuarioServicio usuarioServicioData = data.getValue(UsuarioServicio.class);
                    usuarioServicioData.setIdUsuario(data.getKey());

                    if(usuarioServicioData.getNombreUsuario().equals(usuarioServicio.getNombreUsuario())){

                        existeUsuario = true;
                        break;
                    }
                }

                if(!existeUsuario){

                    mensajeRef.push().setValue(usuarioServicio);
                    estadoTransaccion = "El usuario ha sido agregado exitosamente";
                }
                else{

                    estadoTransaccion = "El nombre de usuario '"+usuarioServicio.getNombreUsuario()+"' ya está en uso";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                estadoTransaccion = "No se ha podido contactar con el catálogo de usuarios";
            }
        });

        return estadoTransaccion;
    }
}
